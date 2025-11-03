// Minesweeper logic assistance from ChatGPT.
// I adapted & modified code for learning purposes.

package com.example.minesweeper.model;

import java.util.Random;

public class Board {

    public boolean[][] mines;
    public boolean[][] revealed;
    public boolean[][] flagged;
    public int[][] adj;
    private int rows, cols;
    private int revealedCount = 0;
    private int totalSafe;

    public Board(int r, int c, double minePercent) {
        rows = r;
        cols = c;

        mines = new boolean[r][c];
        revealed = new boolean[r][c];
        flagged = new boolean[r][c];
        adj = new int[r][c];

        int total = r * c;
        int mineCount = (int)(total * minePercent);
        totalSafe = total - mineCount;

        Random rand = new Random();
        while (mineCount > 0) {
            int rr = rand.nextInt(r);
            int cc = rand.nextInt(c);
            if (!mines[rr][cc]) {
                mines[rr][cc] = true;
                mineCount--;
            }
        }

        // compute adjacencies
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (!mines[i][j]) {
                    adj[i][j] = countAdj(i, j);
                }
            }
        }
    }

    private int countAdj(int r, int c) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int nr = r + i, nc = c + j;
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && mines[nr][nc]) {
                    count++;
                }
            }
        }
        return count;
    }

    public void reveal(int r, int c) {
        if (revealed[r][c] || flagged[r][c]) return;

        revealed[r][c] = true;
        if (!mines[r][c]) revealedCount++;

        if (adj[r][c] == 0 && !mines[r][c]) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int nr = r + i, nc = c + j;
                    if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                        reveal(nr, nc);
                    }
                }
            }
        }
    }

    public void toggleFlag(int r, int c) {
        if (!revealed[r][c])
            flagged[r][c] = !flagged[r][c];
    }

    public boolean isMine(int r, int c) {
        return mines[r][c];
    }

    public boolean isWin() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!mines[r][c] && !revealed[r][c]) {
                    return false; // still hidden safe cell
                }
            }
        }
        return true;
    }

}
