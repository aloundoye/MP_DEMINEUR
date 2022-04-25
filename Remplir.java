public class Remplir {
    public static void remplirMines(int lignes, int colonnes, int numMines, boolean[] mines) {
        int needed = numMines;
        while (needed > 0) {
            int x = (int) Math.floor(Math.random() * lignes);
            int y = (int) Math.floor(Math.random() * colonnes);
            if (!mines[(lignes * y) + x]) {
                mines[(lignes * y) + x] = true;
                needed--;
            }
        }
    }

    public static void remplirNombre(int lignes, int colonnes, boolean[] mines, int[] nombres) {
        for (int x = 0; x < lignes; x++) {
            for (int y = 0; y < colonnes; y++) {
                int cur = (lignes * y) + x;
                if (mines[cur]) {
                    nombres[cur] = 0;
                    continue;
                }
                int temp = 0;
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
                if (u) {
                    if (mines[up]) {
                        temp++;
                    }
                    if (l) {
                        if (mines[upleft]) {
                            temp++;
                        }
                    }
                    if (r) {
                        if (mines[upright]) {
                            temp++;
                        }
                    }
                }
                if (d) {
                    if (mines[down]) {
                        temp++;
                    }
                    if (l) {
                        if (mines[downleft]) {
                            temp++;
                        }
                    }
                    if (r) {
                        if (mines[downright]) {
                            temp++;
                        }
                    }
                }
                if (l) {
                    if (mines[left]) {
                        temp++;
                    }
                }
                if (r) {
                    if (mines[right]) {
                        temp++;
                    }
                }
                nombres[cur] = temp;
            }
        }
    }
}
