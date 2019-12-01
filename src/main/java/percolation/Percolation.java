package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF uf2;
    private final int top;
    private final int bottom;
    private final boolean[] opened;
    private int openedCount;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        this.n = n;

        uf = new WeightedQuickUnionUF(n * n + 2);
        uf2 = new WeightedQuickUnionUF(n * n + 1);
        opened = new boolean[n * n];

        top = flat(n, n) + 1;
        bottom = flat(n, n) + 2;
    }

    public void open(int row, int col) {
        if (outside(row) || outside(col)) throw new IllegalArgumentException();

        final int i = flat(row, col);
        if (opened[i]) return;

        opened[i] = true;
        openedCount++;

        if (row == 1) {
            uf.union(top, i);
            uf2.union(top, i);
        }

        if (row == n) {
            uf.union(bottom, i);
        }

        if (inside(row - 1)) connect(flat(row - 1, col), i);
        if (inside(row + 1)) connect(flat(row + 1, col), i);
        if (inside(col - 1)) connect(flat(row, col - 1), i);
        if (inside(col + 1)) connect(flat(row, col + 1), i);
    }

    private void connect(int i, int j) {
        if (opened[i]) {
            uf.union(i, j);
            uf2.union(i, j);
        }
    }

    public boolean isOpen(int row, int col) {
        if (outside(row) || outside(col)) throw new IllegalArgumentException();

        return opened[flat(row, col)];
    }

    public boolean isFull(int row, int col) {
        if (outside(row) || outside(col)) throw new IllegalArgumentException();

        return uf2.connected(top, flat(row, col));
    }

    public int numberOfOpenSites() {
        return openedCount;
    }

    public boolean percolates() {
        return uf.connected(top, bottom);
    }

    // test client (optional)
    public static void main(String[] args) {
        int n  = 5;
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {
            int row = StdRandom.uniform(1, n + 1);
            int col = StdRandom.uniform(1, n + 1);
            percolation.open(row, col);
        }
        System.out.println(percolation.numberOfOpenSites());
    }

    private boolean outside(int index) {
        return index < 1 || index > n;
    }

    private boolean inside(int index) {
        return !outside(index);
    }

    private int flat(int row, int col) {
        return (row - 1) * n + (col - 1);
    }
}
