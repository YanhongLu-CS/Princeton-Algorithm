/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;
    private boolean[][] isOpen;
    private int opensites;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufForFull;
    private int[] dx = { 1, 0, 0, -1 };
    private int[] dy = { 0, 1, -1, 0 };


    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive.");
        }
        size = n;
        opensites = 0;
        isOpen = new boolean[size + 1][size + 1];
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                isOpen[i][j] = false;
            }
        }
        uf = new WeightedQuickUnionUF(n * n + 2);
        ufForFull = new WeightedQuickUnionUF(n * n + 1);
    }

    public void open(int row, int col) {
        if (!validLocation(row, col)) throw new IllegalArgumentException("location out of range");
        if (isOpen(row, col)) return;
        isOpen[row][col] = true;
        opensites += 1;
        connectWithNeighbor(row, col);
        if (row == 1) {
            uf.union(location(row, col), 0);
            ufForFull.union(location(row, col), 0);
        }
        if (row == size) uf.union(location(row, col), size * size + 1);
    }

    private void connectWithNeighbor(int row, int col) {
        int curLocatioon = location(row, col);
        for (int i = 0; i < 4; i++) {
            int neighborLocation = location(row + dx[i], col + dy[i]);
            if (!validLocation(row + dx[i], col + dy[i])) continue;
            if (!isOpen(row + dx[i], col + dy[i])) continue;
            uf.union(curLocatioon, neighborLocation);
            ufForFull.union(curLocatioon, neighborLocation);
        }
    }

    private boolean validLocation(int row, int col) {
        return (row > 0 && row <= size && col > 0 && col <= size);
    }

    private int location(int row, int col) {
        return (row - 1) * size + col;
    }

    public boolean isOpen(int row, int col) {
        if (!validLocation(row, col)) throw new IllegalArgumentException("location out of range");
        return (isOpen[row][col]);
    }

    public boolean isFull(int row, int col) {
        if (!validLocation(row, col)) throw new IllegalArgumentException("location out of range");
        return (ufForFull.find(location(row, col)) == ufForFull.find(0));
    }

    public int numberOfOpenSites() {
        return opensites;
    }

    public boolean percolates() {
        return (uf.find(0) == uf.find(size * size + 1));
    }

    public static void main(String[] args) {

    }
}
