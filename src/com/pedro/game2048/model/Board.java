package com.pedro.game2048.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private final int SIZE = 4;
    private final int[][] grid;
    private final Random random;
    private int score;

    public Board() {
        grid = new int[SIZE][SIZE];
        random = new Random();
        score = 0;
        spawnTile();
        spawnTile();
    }

    public int getSize() {
        return SIZE;
    }

    public int getTile(int row, int col) {
        return grid[row][col];
    }

    public int[][] getGridCopy() {
        int[][] copy = new int[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) System.arraycopy(grid[r], 0, copy[r], 0, SIZE);
        return copy;
    }

    public int getScore() {
        return score;
    }

    private void spawnTile() {
        List<int[]> empty = new ArrayList<>();
        for (int r = 0; r < SIZE; r++)
            for (int c = 0; c < SIZE; c++)
                if (grid[r][c] == 0) empty.add(new int[]{r, c});
        if (empty.isEmpty()) return;
        int[] cell = empty.get(random.nextInt(empty.size()));
        grid[cell[0]][cell[1]] = random.nextDouble() < 0.9 ? 2 : 4;
    }

    public boolean moveLeft() {
        boolean moved = false;
        for (int row = 0; row < SIZE; row++) {
            int[] current = grid[row];
            int[] newRow = new int[SIZE];
            int pos = 0;
            for (int i = 0; i < SIZE; i++) if (current[i] != 0) newRow[pos++] = current[i];

            for (int i = 0; i < SIZE - 1; i++) {
                if (newRow[i] != 0 && newRow[i] == newRow[i + 1]) {
                    newRow[i] *= 2;
                    score += newRow[i];
                    newRow[i + 1] = 0;
                    moved = true;
                }
            }

            int[] finalRow = new int[SIZE];
            pos = 0;
            for (int i = 0; i < SIZE; i++) if (newRow[i] != 0) finalRow[pos++] = newRow[i];

            for (int i = 0; i < SIZE; i++) {
                if (grid[row][i] != finalRow[i]) moved = true;
                grid[row][i] = finalRow[i];
            }
        }
        if (moved) spawnTile();
        return moved;
    }

    public boolean moveRight() {
        boolean moved = false;
        for (int row = 0; row < SIZE; row++) {
            int[] cur = grid[row];
            int[] rev = new int[SIZE];
            for (int i = 0; i < SIZE; i++) rev[i] = cur[SIZE - 1 - i];

            int[] newRow = new int[SIZE];
            int pos = 0;
            for (int i = 0; i < SIZE; i++) if (rev[i] != 0) newRow[pos++] = rev[i];

            for (int i = 0; i < SIZE - 1; i++) {
                if (newRow[i] != 0 && newRow[i] == newRow[i + 1]) {
                    newRow[i] *= 2;
                    score += newRow[i];
                    newRow[i + 1] = 0;
                    moved = true;
                }
            }

            int[] finalRow = new int[SIZE];
            pos = 0;
            for (int i = 0; i < SIZE; i++) if (newRow[i] != 0) finalRow[pos++] = newRow[i];

            for (int i = 0; i < SIZE; i++) {
                int val = finalRow[SIZE - 1 - i];
                if (grid[row][i] != val) moved = true;
                grid[row][i] = val;
            }
        }
        if (moved) spawnTile();
        return moved;
    }

    public boolean moveUp() {
        boolean moved = false;
        for (int col = 0; col < SIZE; col++) {
            int[] column = new int[SIZE];
            for (int r = 0; r < SIZE; r++) column[r] = grid[r][col];

            int[] newCol = new int[SIZE];
            int pos = 0;
            for (int i = 0; i < SIZE; i++) if (column[i] != 0) newCol[pos++] = column[i];

            for (int i = 0; i < SIZE - 1; i++) {
                if (newCol[i] != 0 && newCol[i] == newCol[i + 1]) {
                    newCol[i] *= 2;
                    score += newCol[i];
                    newCol[i + 1] = 0;
                    moved = true;
                }
            }

            int[] finalCol = new int[SIZE];
            pos = 0;
            for (int i = 0; i < SIZE; i++) if (newCol[i] != 0) finalCol[pos++] = newCol[i];

            for (int r = 0; r < SIZE; r++) {
                if (grid[r][col] != finalCol[r]) moved = true;
                grid[r][col] = finalCol[r];
            }
        }
        if (moved) spawnTile();
        return moved;
    }

    public boolean moveDown() {
        boolean moved = false;
        for (int col = 0; col < SIZE; col++) {
            int[] column = new int[SIZE];
            for (int r = 0; r < SIZE; r++) column[r] = grid[SIZE - 1 - r][col];

            int[] newCol = new int[SIZE];
            int pos = 0;
            for (int i = 0; i < SIZE; i++) if (column[i] != 0) newCol[pos++] = column[i];

            for (int i = 0; i < SIZE - 1; i++) {
                if (newCol[i] != 0 && newCol[i] == newCol[i + 1]) {
                    newCol[i] *= 2;
                    score += newCol[i];
                    newCol[i + 1] = 0;
                    moved = true;
                }
            }

            int[] finalCol = new int[SIZE];
            pos = 0;
            for (int i = 0; i < SIZE; i++) if (newCol[i] != 0) finalCol[pos++] = newCol[i];

            for (int r = 0; r < SIZE; r++) {
                int val = finalCol[SIZE - 1 - r];
                if (grid[r][col] != val) moved = true;
                grid[r][col] = val;
            }
        }
        if (moved) spawnTile();
        return moved;
    }

    public boolean canMove() {
        for (int r = 0; r < SIZE; r++) for (int c = 0; c < SIZE; c++) if (grid[r][c] == 0) return true;
        for (int r = 0; r < SIZE; r++) for (int c = 0; c < SIZE - 1; c++) if (grid[r][c] == grid[r][c + 1]) return true;
        for (int c = 0; c < SIZE; c++) for (int r = 0; r < SIZE - 1; r++) if (grid[r][c] == grid[r + 1][c]) return true;
        return false;
    }

    public boolean hasWon() {
        for (int r = 0; r < SIZE; r++) for (int c = 0; c < SIZE; c++) if (grid[r][c] == 2048) return true;
        return false;
    }

    public void reset() {
        for (int r = 0; r < SIZE; r++) for (int c = 0; c < SIZE; c++) grid[r][c] = 0;
        score = 0;
        spawnTile();
        spawnTile();
    }

}
