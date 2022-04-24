import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Demineur extends JFrame implements ActionListener, MouseListener {
    int rows = 10;
    int columns = 10;
    int numMines = 10;
    GridLayout layout = new GridLayout(rows, columns);
    boolean[] mines = new boolean[rows * columns];
    boolean[] clickable = new boolean[rows * columns];
    boolean lost = false;
    boolean win = false;
    int[] numbers = new int[rows * columns];
    JButton[] buttons = new JButton[rows * columns];
    boolean[] clickdone = new boolean[rows * columns];
    JMenuItem newGameButton = new JMenuItem("Nouvelle partie");
    JMenuItem difficulty = new JMenuItem("Options");
    JLabel mineLabel = new JLabel("Mines: " + numMines + " marqué(s): 0");
    JPanel jPanel = new JPanel();

    public Demineur() {
        jPanel.setLayout(layout);
        initialisation();
        for (int i = 0; i < (rows * columns); i++) {
            jPanel.add(buttons[i]);
        }
        JMenuBar jMenuBar = new JMenuBar();
        JMenu jMenu = new JMenu("Fichier");
        newGameButton.addActionListener(this);
        jMenu.add(newGameButton);
        difficulty.addActionListener(this);
        jMenu.add(difficulty);
        jMenuBar.add(jMenu);
        this.setJMenuBar(jMenuBar);
        this.add(jPanel);
        this.add(mineLabel, BorderLayout.SOUTH);
        this.pack();
        this.setVisible(true);
    }



    public void initialisation() {
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                mines[(rows * y) + x] = false;
                clickdone[(rows * y) + x] = false;
                clickable[(rows * y) + x] = true;
                buttons[(rows * y) + x] = new JButton( /*"" + ( x * y )*/);
                buttons[(rows * y) + x].setPreferredSize(new Dimension(
                        45, 45));
                buttons[(rows * y) + x].addActionListener(this);
                buttons[(rows * y) + x].addMouseListener(this);
            }
        }
        Fill.fillMines(rows, columns, numMines, mines);
        Fill.fillNumbers(rows, columns, mines, numbers);
    }

    public void initialisation2() {
        this.remove(jPanel);
        jPanel = new JPanel();
        layout = new GridLayout(rows, columns);
        jPanel.setLayout(layout);
        buttons = new JButton[rows * columns];
        mines = new boolean[rows * columns];
        clickdone = new boolean[rows * columns];
        clickable = new boolean[rows * columns];
        numbers = new int[rows * columns];
        initialisation();
        for (int i = 0; i < (rows * columns); i++) {
            jPanel.add(buttons[i]);
        }
        this.add(jPanel);
        this.pack();
        Fill.fillMines(rows, columns, numMines, mines);
        Fill.fillNumbers(rows, columns, mines, numbers);
    }

    public void setup() {
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                mines[(rows * y) + x] = false;
                clickdone[(rows * y) + x] = false;
                clickable[(rows * y) + x] = true;
                buttons[(rows * y) + x].setEnabled(true);
                buttons[(rows * y) + x].setText("");
            }
        }
        Fill.fillMines(rows, columns, numMines, mines);
        Fill.fillNumbers(rows, columns, mines, numbers);
        lost = false;
        mineLabel.setText("mines: " + numMines + " marqué(s): 0");
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == difficulty) {
            rows = Integer.parseInt((String) JOptionPane.showInputDialog(
                    this, "Lignes", "Lignes", JOptionPane.PLAIN_MESSAGE, null,
                    null, 10));
            columns = Integer.parseInt((String) JOptionPane.showInputDialog(
                    this, "Columns", "Columns", JOptionPane.PLAIN_MESSAGE,
                    null, null, 10));
            numMines = Integer.parseInt((String) JOptionPane.showInputDialog(this, "Mines", "Mines",
                    JOptionPane.PLAIN_MESSAGE, null, null, 10));
            initialisation2();
        }
        if (!win) {
            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < columns; y++) {
                    if (e.getSource() == buttons[(rows * y) + x]
                            && !win && clickable[(rows * y) + x]) {
                        doCheck(x, y);
                        break;
                    }
                }
            }
        }
        if (e.getSource() == newGameButton) {
            setup();
            win = false;
            return;

        }
        checkWin();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 3) {
            int n = 0;
            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < columns; y++) {
                    if (e.getSource() == buttons[(rows * y) + x]) {
                        clickable[(rows * y) + x] = !clickable[(rows * y)
                                + x];
                    }
                    if (!clickdone[(rows * y) + x]) {
                        if (!clickable[(rows * y) + x]) {
                            buttons[(rows * y) + x].setText("X");
                            n++;
                        } else {
                            buttons[(rows * y) + x].setText("");
                        }
                        mineLabel.setText("mines: " + numMines + " marqué(s): "
                                + n);
                    }
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void doCheck(int x, int y) {
        int cur = (rows * y) + x;
        boolean l = (x - 1) >= 0;
        boolean r = (x + 1) < rows;
        boolean u = (y - 1) >= 0;
        boolean d = (y + 1) < columns;
        int left = (rows * (y)) + (x - 1);
        int right = (rows * (y)) + (x + 1);
        int up = (rows * (y - 1)) + (x);
        int upleft = (rows * (y - 1)) + (x - 1);
        int upright = (rows * (y - 1)) + (x + 1);
        int down = (rows * (y + 1)) + (x);
        int downleft = (rows * (y + 1)) + (x - 1);
        int downright = (rows * (y + 1)) + (x + 1);

        clickdone[cur] = true;
        buttons[cur].setEnabled(false);
        if (numbers[cur] == 0 && !mines[cur] && !lost && !win) {
            if (u && !win) {
                if (!clickdone[up] && !mines[up]) {
                    clickdone[up] = true;
                    buttons[up].doClick();
                }
                if (l && !win) {
                    if (!clickdone[upleft] && numbers[upleft] != 0
                            && !mines[upleft]) {
                        clickdone[upleft] = true;
                        buttons[upleft].doClick();
                    }
                }
                if (r && !win) {
                    if (!clickdone[upright] && numbers[upright] != 0
                            && !mines[upright]) {
                        clickdone[upright] = true;
                        buttons[upright].doClick();
                    }
                }
            }
            if (d && !win) {
                if (!clickdone[down] && !mines[down]) {
                    clickdone[down] = true;
                    buttons[down].doClick();
                }
                if (l && !win) {
                    if (!clickdone[downleft] && numbers[downleft] != 0
                            && !mines[downleft]) {
                        clickdone[downleft] = true;
                        buttons[downleft].doClick();
                    }
                }
                if (r && !win) {
                    if (!clickdone[downright]
                            && numbers[downright] != 0
                            && !mines[downright]) {
                        clickdone[downright] = true;
                        buttons[downright].doClick();
                    }
                }
            }
            if (l && !win) {
                if (!clickdone[left] && !mines[left]) {
                    clickdone[left] = true;
                    buttons[left].doClick();
                }
            }
            if (r && !win) {
                if (!clickdone[right] && !mines[right]) {
                    clickdone[right] = true;
                    buttons[right].doClick();
                }
            }
        } else {
            buttons[cur].setText("" + numbers[cur]);
            if (!mines[cur] && numbers[cur] == 0) {
                buttons[cur].setText("");
            }
        }
        if (mines[cur] && !win) {
            buttons[cur].setText("0");
            doLose();
        }
    }

    public void checkWin() {
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                int cur = (rows * y) + x;
                if (!clickdone[cur]) {
                    if (mines[cur]) {
                        continue;
                    } else {
                        return;
                    }
                }
            }
        }

        doWin();
    }

    public void doWin() {
        if (!lost && !win) {
            win = true;
            JOptionPane.showMessageDialog(null,
    "Vous avez gagné!\nUne nouvelle partie commence", "Vous avez gagné",                    JOptionPane.INFORMATION_MESSAGE);
            newGameButton.doClick();
        }
    }

    public void doLose() {
        if (!lost && !win) {
            lost = true;
            for (int i = 0; i < rows * columns; i++) {
                if (!clickdone[i]) {
                    buttons[i].doClick(0);
                }
            }
            JOptionPane.showMessageDialog(null,
                    "Vous avez perdu!\nUne nouvelle partie commence", "Vous avez perdu",
                    JOptionPane.ERROR_MESSAGE);
            setup();
        }
    }
}