package com.pedro.game2048.model;

public class Tile {
    private static int NEXT_ID = 1;

    private final int id;
    private int value;

    public Tile(int value) {
        this.id = NEXT_ID++;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
