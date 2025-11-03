package com.example.minesweeper.ui;

public class ColorItem {
    public final String name;
    public final int color;

    public ColorItem(String name, int color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public String toString() {
        return name;
    }
}
