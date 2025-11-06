package com.pedro.game2048;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int SIZE = 4;
    private final int[][] grid;
    private final Random random;

    public Board() {
        grid = new int[SIZE][SIZE];
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
        String horizontalLine = "+----+----+----+----+";

        for (int row = 0; row < SIZE; row++){
            System.out.println(horizontalLine);
            for(int col = 0; col < SIZE; col++){
                if (grid[row][col] == 0){
                    System.out.print("|    ");
                } else {
                    System.out.printf("|%4d", grid[row][col]);
                }
            }
            System.out.println("|");
        }
        System.out.println(horizontalLine);

    }

    public boolean moveLeft(){
        boolean moved = false;

        for(int row = 0; row < SIZE; row++){
            int[] currentRow = grid[row];
            int[] newRow = new int[SIZE];
            int pos = 0;

            for(int i = 0; i < SIZE; i++){
                if(currentRow[i] != 0){
                    newRow[pos++] = currentRow[i];
                }
            }

            for(int i = 0; i < SIZE - 1; i++){
                if (newRow[i] != 0 && newRow[i] == newRow[i + 1]) {
                    newRow[i] *= 2;
                    newRow[i + 1] = 0;
                    moved = true;
                }
            }

            int[] finalRow = new int[SIZE];
            pos = 0;
            for(int i = 0; i < SIZE; i++){
                if(newRow[i] != 0){
                    finalRow[pos++] = newRow[i];
                }
            }

            for(int i = 0; i < SIZE; i++){
                if(grid[row][i] != finalRow[i]) {
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

}
