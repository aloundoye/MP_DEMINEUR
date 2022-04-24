public class Fill {
    public static void fillMines(int rows, int cols, int numMines, boolean[] mines) {
        int needed = numMines;
        while (needed > 0) {
            int x = (int) Math.floor(Math.random() * rows);
            int y = (int) Math.floor(Math.random() * cols);
            if (!mines[(rows * y) + x]) {
                mines[(rows * y) + x] = true;
                needed--;
            }
        }
    }

    public static void fillNumbers(int rows, int cols, boolean[] mines, int[] numbers) {
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                int cur = (rows * y) + x;
                if (mines[cur]) {
                    numbers[cur] = 0;
                    continue;
                }
                int temp = 0;
                boolean l = (x - 1) >= 0;
                boolean r = (x + 1) < rows;
                boolean u = (y - 1) >= 0;
                boolean d = (y + 1) < cols;
                int left = (rows * (y)) + (x - 1);
                int right = (rows * (y)) + (x + 1);
                int up = (rows * (y - 1)) + (x);
                int upleft = (rows * (y - 1)) + (x - 1);
                int upright = (rows * (y - 1)) + (x + 1);
                int down = (rows * (y + 1)) + (x);
                int downleft = (rows * (y + 1)) + (x - 1);
                int downright = (rows * (y + 1)) + (x + 1);
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
                numbers[cur] = temp;
            }
        }
    }
}
