import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Demineur extends JFrame implements ActionListener, MouseListener {
    int lignes = 10;
    int colonnes = 10;
    int nombresMines = 10;
    GridLayout layout = new GridLayout(lignes, colonnes);
    boolean[] mines = new boolean[lignes * colonnes];
    boolean[] clickable = new boolean[lignes * colonnes];
    boolean lost = false;
    boolean win = false;
    int[] nombres = new int[lignes * colonnes];
    JButton[] buttons = new JButton[lignes * colonnes];
    boolean[] clickdone = new boolean[lignes * colonnes];
    JMenuItem newGameButton = new JMenuItem("Nouvelle partie");
    JMenuItem difficulty = new JMenuItem("Options");
    JLabel mineLabel = new JLabel("Mines: " + nombresMines + " marqué(s): 0");
    JPanel jPanel = new JPanel();

    public Demineur() {
        jPanel.setLayout(layout);
        initialisation();
        for (int i = 0; i < (lignes * colonnes); i++) {
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
        for (int x = 0; x < lignes; x++) {
            for (int y = 0; y < colonnes; y++) {
                mines[(lignes * y) + x] = false;
                clickdone[(lignes * y) + x] = false;
                clickable[(lignes * y) + x] = true;
                buttons[(lignes * y) + x] = new JButton();
                buttons[(lignes * y) + x].setPreferredSize(new Dimension(
                        45, 45));
                buttons[(lignes * y) + x].addActionListener(this);
                buttons[(lignes * y) + x].addMouseListener(this);
            }
        }
        Remplir.remplirMines(lignes, colonnes, nombresMines, mines);
        Remplir.remplirNombre(lignes, colonnes, mines, nombres);
    }

    public void initialisation2() {
        this.remove(jPanel);
        jPanel = new JPanel();
        layout = new GridLayout(lignes, colonnes);
        jPanel.setLayout(layout);
        buttons = new JButton[lignes * colonnes];
        mines = new boolean[lignes * colonnes];
        clickdone = new boolean[lignes * colonnes];
        clickable = new boolean[lignes * colonnes];
        nombres = new int[lignes * colonnes];
        initialisation();
        for (int i = 0; i < (lignes * colonnes); i++) {
            jPanel.add(buttons[i]);
        }
        this.add(jPanel);
        this.pack();
        Remplir.remplirMines(lignes, colonnes, nombresMines, mines);
        Remplir.remplirNombre(lignes, colonnes, mines, nombres);
    }

    public void setup() {
        for (int x = 0; x < lignes; x++) {
            for (int y = 0; y < colonnes; y++) {
                mines[(lignes * y) + x] = false;
                clickdone[(lignes * y) + x] = false;
                clickable[(lignes * y) + x] = true;
                buttons[(lignes * y) + x].setEnabled(true);
                buttons[(lignes * y) + x].setText("");
            }
        }
        Remplir.remplirMines(lignes, colonnes, nombresMines, mines);
        Remplir.remplirNombre(lignes, colonnes, mines, nombres);
        lost = false;
        mineLabel.setText("mines: " + nombresMines + " marqué(s): 0");
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == difficulty) {
            lignes = Integer.parseInt((String) JOptionPane.showInputDialog(
                    this, "Lignes", "Lignes", JOptionPane.PLAIN_MESSAGE, null,
                    null, 10));
            colonnes = Integer.parseInt((String) JOptionPane.showInputDialog(
                    this, "Columns", "Columns", JOptionPane.PLAIN_MESSAGE,
                    null, null, 10));
            nombresMines = Integer.parseInt((String) JOptionPane.showInputDialog(this, "Mines", "Mines",
                    JOptionPane.PLAIN_MESSAGE, null, null, 10));
            initialisation2();
        }
        if (!win) {
            for (int x = 0; x < lignes; x++) {
                for (int y = 0; y < colonnes; y++) {
                    if (e.getSource() == buttons[(lignes * y) + x]
                            && !win && clickable[(lignes * y) + x]) {
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
            for (int x = 0; x < lignes; x++) {
                for (int y = 0; y < colonnes; y++) {
                    if (e.getSource() == buttons[(lignes * y) + x]) {
                        clickable[(lignes * y) + x] = !clickable[(lignes * y)
                                + x];
                    }
                    if (!clickdone[(lignes * y) + x]) {
                        if (!clickable[(lignes * y) + x]) {
                            buttons[(lignes * y) + x].setText("X");
                            n++;
                        } else {
                            buttons[(lignes * y) + x].setText("");
                        }
                        mineLabel.setText("mines: " + nombresMines + " marqué(s): "
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
        int cur = (lignes * y) + x;
        boolean l = (x - 1) >= 0;
        boolean r = (x + 1) < lignes;
        boolean u = (y - 1) >= 0;
        boolean d = (y + 1) < colonnes;
        int left = (lignes * (y)) + (x - 1);
        int right = (lignes * (y)) + (x + 1);
        int up = (lignes * (y - 1)) + (x);
        int upleft = (lignes * (y - 1)) + (x - 1);
        int upright = (lignes * (y - 1)) + (x + 1);
        int down = (lignes * (y + 1)) + (x);
        int downleft = (lignes * (y + 1)) + (x - 1);
        int downright = (lignes * (y + 1)) + (x + 1);

        clickdone[cur] = true;
        buttons[cur].setEnabled(false);
        if (nombres[cur] == 0 && !mines[cur] && !lost && !win) {
            if (u && !win) {
                if (!clickdone[up] && !mines[up]) {
                    clickdone[up] = true;
                    buttons[up].doClick();
                }
                if (l && !win) {
                    if (!clickdone[upleft] && nombres[upleft] != 0
                            && !mines[upleft]) {
                        clickdone[upleft] = true;
                        buttons[upleft].doClick();
                    }
                }
                if (r && !win) {
                    if (!clickdone[upright] && nombres[upright] != 0
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
                    if (!clickdone[downleft] && nombres[downleft] != 0
                            && !mines[downleft]) {
                        clickdone[downleft] = true;
                        buttons[downleft].doClick();
                    }
                }
                if (r && !win) {
                    if (!clickdone[downright]
                            && nombres[downright] != 0
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
            buttons[cur].setText("" + nombres[cur]);
            if (!mines[cur] && nombres[cur] == 0) {
                buttons[cur].setText("");
            }
        }
        if (mines[cur] && !win) {
            buttons[cur].setText("0");
            doLose();
        }
    }

    public void checkWin() {
        for (int x = 0; x < lignes; x++) {
            for (int y = 0; y < colonnes; y++) {
                int cur = (lignes * y) + x;
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
            for (int i = 0; i < lignes * colonnes; i++) {
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