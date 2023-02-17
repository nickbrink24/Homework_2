package com.example.gridclick;

public class GameModel {

    private int empty_row;
    private int empty_col;

    public GameModel() {
    }

    public int getEmptyRow() {
        return empty_row;
    }

    public int getEmptyCol() {
        return empty_col;
    }

    public void setEmptyRow(int i) {
        empty_row = i;
    }

    public void setEmptyCol(int i) {
        empty_col = i;
    }
}
