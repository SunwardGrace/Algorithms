/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final boolean OPEN = true;
    private static final boolean CLOSED = false;
    private WeightedQuickUnionUF uf;
    private boolean[] state;
    private int side;
    private int openNum;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        side = n;
        openNum = 0;
        uf = new WeightedQuickUnionUF(n * n + 2);
        state = new boolean[n * n + 2];
        state[0] = OPEN;
        state[n * n + 1] = OPEN;
        for (int i = 1; i < n * n + 1; i++) {
            state[i] = CLOSED;
        }
    }

    private int getIndex(int row, int col) {
        return (row - 1) * side + col;
    }

    private boolean outGrid(int row, int col) {
        if (row <= 0 || row > side || col <= 0 || col > side) return true;
        return false;
    }

    private void connectNeighbors(int row, int col) {
        connect(row, col, row - 1, col); // 上
        connect(row, col, row + 1, col); // 下
        connect(row, col, row, col - 1); // 左
        connect(row, col, row, col + 1); // 右
    }

    private void connect(int row, int col, int newRow, int newCol) {
        if (!outGrid(newRow, newCol) && isOpen(newRow, newCol)) {
            uf.union(getIndex(row, col), getIndex(newRow, newCol));
        }
    }

    public void open(int row, int col) {
        if (outGrid(row, col)) {
            throw new IllegalArgumentException();
        }
        int idx = getIndex(row, col);
        if (state[idx] == OPEN) return; // 避免重复打开
        state[idx] = OPEN;
        openNum++;
        if (row == 1) {
            uf.union(idx, 0);
        }
        if (row == side) {
            uf.union(idx, side * side + 1);
        }
        connectNeighbors(row, col);
    }

    public boolean isOpen(int row, int col) {
        if (outGrid(row, col)) {
            throw new IllegalArgumentException();
        }
        return state[getIndex(row, col)];
    }

    public boolean isFull(int row, int col) {
        if (outGrid(row, col)) {
            throw new IllegalArgumentException();
        }
        return (uf.find(getIndex(row, col)) == uf.find(0));
    }

    public int numberOfOpenSites() {
        return openNum;
    }

    public boolean percolates() {
        return (uf.find(0) == uf.find(side * side + 1));
    }
}
