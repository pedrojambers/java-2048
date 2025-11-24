package com.pedro.game2048;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int SIZE = 4;
    private final int[][] grid;
    private final Random random;
    private int score;

    public Board() {
        grid = new int[SIZE][SIZE];
        score = 0;
        random = new Random();
        spawnTile();
        spawnTile();
    }

    private void spawnTile() {
        List<int[]> emptyCells = new ArrayList<>();

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (grid[row][col] == 0) {
                    emptyCells.add(new int[]{row, col});
                }
            }
        }

        if (emptyCells.isEmpty()) return;

        int[] chosenCell = emptyCells.get(random.nextInt(emptyCells.size()));

        grid[chosenCell[0]][chosenCell[1]] = random.nextDouble() < 0.9 ? 2 : 4;
    }

    public void printBoard() {
        System.out.println("\nScore: " + score);
        String horizontalLine = "+----+----+----+----+";

        for (int row = 0; row < SIZE; row++) {
            System.out.println(horizontalLine);
            for (int col = 0; col < SIZE; col++) {
                if (grid[row][col] == 0) {
                    System.out.print("|    ");
                } else {
                    System.out.printf("|%4d", grid[row][col]);
                }
            }
            System.out.println("|");
        }
        System.out.println(horizontalLine);

    }

    public boolean moveLeft() {
        boolean moved = false;

        for (int row = 0; row < SIZE; row++) {
            int[] currentRow = grid[row];
            int[] newRow = new int[SIZE];
            int pos = 0;

            for (int i = 0; i < SIZE; i++) {
                if (currentRow[i] != 0) {
                    newRow[pos++] = currentRow[i];
                }
            }

            for (int i = 0; i < SIZE - 1; i++) {
                if (newRow[i] != 0 && newRow[i] == newRow[i + 1]) {
                    newRow[i] *= 2;
                    newRow[i + 1] = 0;
                    moved = true;
                    score += newRow[i];
                }
            }

            int[] finalRow = new int[SIZE];
            pos = 0;
            for (int i = 0; i < SIZE; i++) {
                if (newRow[i] != 0) {
                    finalRow[pos++] = newRow[i];
                }
            }

            for (int i = 0; i < SIZE; i++) {
                if (grid[row][i] != finalRow[i]) {
                    moved = true;
                }
                grid[row][i] = finalRow[i];
            }
        }

        if (moved) {
            spawnTile();
        }

        return moved;
    }

    public boolean moveRight() {
        boolean moved = false;

        for (int row = 0; row < SIZE; row++) {
            int[] currentRow = grid[row];
            int[] reversedRow = new int[SIZE];

            for (int i = 0; i < SIZE; i++) {
                reversedRow[i] = currentRow[SIZE - 1 - i];
            }

            int[] newRow = new int[SIZE];
            int pos = 0;

            for (int i = 0; i < SIZE; i++) {
                if (reversedRow[i] != 0) {
                    newRow[pos++] = reversedRow[i];
                }
            }

            for (int i = 0; i < SIZE - 1; i++) {
                if (newRow[i] != 0 && newRow[i] == newRow[i + 1]) {
                    newRow[i] *= 2;
                    newRow[i + 1] = 0;
                    moved = true;
                    score += newRow[i];
                }
            }
            int[] finalRow = new int[SIZE];
            pos = 0;
            for (int i = 0; i < SIZE; i++) {
                if (newRow[i] != 0) {
                    finalRow[pos++] = newRow[i];
                }
            }

            for (int i = 0; i < SIZE; i++) {
                int newValue = finalRow[SIZE - 1 - i];
                if (grid[row][i] != newValue) {
                    moved = true;
                }
                grid[row][i] = newValue;
            }
        }

        if (moved) {
            spawnTile();
        }

        return moved;
    }

    public boolean moveUp() {
        boolean moved = false;

        for (int col = 0; col < SIZE; col++) {
            int[] column = new int[SIZE];
            for (int row = 0; row < SIZE; row++) {
                column[row] = grid[row][col];
            }

            int[] newColumn = new int[SIZE];
            int pos = 0;

            for (int i = 0; i < SIZE; i++) {
                if (column[i] != 0) {
                    newColumn[pos++] = column[i];
                }
            }

            for (int i = 0; i < SIZE - 1; i++) {
                if (newColumn[i] != 0 && newColumn[i] == newColumn[i + 1]) {
                    newColumn[i] *= 2;
                    newColumn[i + 1] = 0;
                    moved = true;
                    score += newColumn[i];
                }
            }

            int[] finalColumn = new int[SIZE];
            pos = 0;
            for (int i = 0; i < SIZE; i++) {
                if (newColumn[i] != 0) {
                    finalColumn[pos++] = newColumn[i];
                }
            }

            for (int row = 0; row < SIZE; row++) {
                if (grid[row][col] != finalColumn[row]) {
                    moved = true;
                }
                grid[row][col] = finalColumn[row];
            }
        }

        if (moved) {
            spawnTile();
        }

        return moved;
    }

    public boolean moveDown() {
        boolean moved = false;

        for (int col = 0; col < SIZE; col++) {
            int[] column = new int[SIZE];
            for (int row = 0; row < SIZE; row++) {
                column[row] = grid[SIZE - 1 - row][col];
            }

            int[] newColumn = new int[SIZE];
            int pos = 0;

            for (int i = 0; i < SIZE; i++) {
                if (column[i] != 0) {
                    newColumn[pos++] = column[i];
                }
            }

            for (int i = 0; i < SIZE - 1; i++) {
                if (newColumn[i] != 0 && newColumn[i] == newColumn[i + 1]) {
                    newColumn[i] *= 2;
                    newColumn[i + 1] = 0;
                    moved = true;
                    score += newColumn[i];
                }
            }

            int[] finalColumn = new int[SIZE];
            pos = 0;
            for (int i = 0; i < SIZE; i++) {
                if (newColumn[i] != 0) {
                    finalColumn[pos++] = newColumn[i];
                }
            }

            for (int row = 0; row < SIZE; row++) {
                int newValue = finalColumn[SIZE - 1 - row];
                if (grid[row][col] != newValue) {
                    moved = true;
                }
                grid[row][col] = newValue;
            }
        }

        if (moved) {
            spawnTile();
        }

        return moved;
    }

    public int getScore(){
        return score;
    }

    public boolean hasWon(){
        for (int row = 0; row < SIZE; row++){
            for (int col = 0; col < SIZE; col++){
                if (grid[row][col] == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canMove(){
        for (int row = 0; row < SIZE; row++){
            for (int col = 0; col < SIZE; col++){
                if (grid[row][col] == 0){
                    return true;
                }
            }
        }

        for (int row = 0; row < SIZE; row++){
            for (int col = 0; col < SIZE - 1; col++){
                if (grid[row][col] == grid[row][col + 1]){
                    return true;
                }
            }
        }

        for (int col = 0; col < SIZE - 1; col++){
            for (int row = 0; row < SIZE - 1; row++){
                if (grid[row][col] == grid[row + 1][col]){
                    return true;
                }
            }
        }

        return false;
    }

}
