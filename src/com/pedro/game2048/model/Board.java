package com.pedro.game2048.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {

    public static final int SIZE = 4;

    private final Tile[][] grid = new Tile[SIZE][SIZE];
    private final Random random = new Random();
    private int score = 0;

    public static class Move {
        public final Tile tile;
        public final int fromR, fromC;
        public final int toR, toC;

        public Move(Tile tile, int fromR, int fromC, int toR, int toC) {
            this.tile = tile;
            this.fromR = fromR;
            this.fromC = fromC;
            this.toR = toR;
            this.toC = toC;
        }
    }

    private final List<Move> lastMoves = new ArrayList<>();

    public List<Move> getLastMoves() {
        return lastMoves;
    }

    public Board() {
        spawnTile();
        spawnTile();

    }

    public Tile getTile(int r, int c) {
        return grid[r][c];
    }

    public int getScore() {
        return score;
    }

    private void spawnTile() {
        List<int[]> empty = new ArrayList<>();
        for (int r = 0; r < SIZE; r++)
            for (int c = 0; c < SIZE; c++)
                if (grid[r][c] == null)
                    empty.add(new int[]{r, c});

        if (empty.isEmpty()) return;

        int[] pos = empty.get(random.nextInt(empty.size()));
        grid[pos[0]][pos[1]] = new Tile(random.nextDouble() < 0.9 ? 2 : 4);
    }

    private boolean move(int dr, int dc) {
        lastMoves.clear();
        boolean moved = false;

        int startR = dr > 0 ? SIZE - 1 : 0;
        int startC = dc > 0 ? SIZE - 1 : 0;
        int endR   = dr > 0 ? -1 : SIZE;
        int endC   = dc > 0 ? -1 : SIZE;

        boolean[][] merged = new boolean[SIZE][SIZE];

        for (int r = startR; r != endR; r -= dr == 0 ? -1 : dr) {
            for (int c = startC; c != endC; c -= dc == 0 ? -1 : dc) {

                boolean mergedThisTile = false;

                Tile tile = grid[r][c];
                if (tile == null) continue;

                int nr = r, nc = c;

                while (true) {
                    int tr = nr + dr;
                    int tc = nc + dc;

                    if (tr < 0 || tr >= SIZE || tc < 0 || tc >= SIZE)
                        break;

                    if (grid[tr][tc] == null) {
                        nr = tr;
                        nc = tc;
                    }
                    else if (!merged[tr][tc] &&
                            grid[tr][tc].getValue() == tile.getValue()) {

                        grid[tr][tc].setValue(tile.getValue() * 2);
                        score += grid[tr][tc].getValue();
                        merged[tr][tc] = true;

                        lastMoves.add(new Move(tile, r, c, tr, tc));

                        grid[r][c] = null;
                        mergedThisTile = true;
                        moved = true;
                        break;
                    }
                    else {
                        break;
                    }
                }

                if (!mergedThisTile && (nr != r || nc != c)) {
                    lastMoves.add(new Move(tile, r, c, nr, nc));
                    grid[nr][nc] = tile;
                    grid[r][c] = null;
                    moved = true;
                }
            }
        }

        if (moved) spawnTile();
        return moved;
    }

    public boolean moveLeft()  { return move(0, -1); }
    public boolean moveRight() { return move(0,  1); }
    public boolean moveUp()    { return move(-1, 0); }
    public boolean moveDown()  { return move(1,  0); }

    public boolean canMove() {
        for (int r = 0; r < SIZE; r++)
            for (int c = 0; c < SIZE; c++)
                if (grid[r][c] == null) return true;

        for (int r = 0; r < SIZE; r++)
            for (int c = 0; c < SIZE - 1; c++)
                if (grid[r][c].getValue() == grid[r][c + 1].getValue())
                    return true;

        for (int c = 0; c < SIZE; c++)
            for (int r = 0; r < SIZE - 1; r++)
                if (grid[r][c].getValue() == grid[r + 1][c].getValue())
                    return true;

        return false;
    }

    public boolean hasWon() {
        for (int r = 0; r < SIZE; r++)
            for (int c = 0; c < SIZE; c++)
                if (grid[r][c] != null && grid[r][c].getValue() == 2048)
                    return true;
        return false;
    }

    public void reset() {
        for (int r = 0; r < SIZE; r++)
            for (int c = 0; c < SIZE; c++)
                grid[r][c] = null;

        score = 0;
        spawnTile();
        spawnTile();
    }
}