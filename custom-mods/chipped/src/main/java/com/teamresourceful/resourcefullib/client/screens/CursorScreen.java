package com.teamresourceful.resourcefullib.client.screens;

public abstract class CursorScreen {
    public enum Cursor {
        DEFAULT,
        ARROW,
        HAND,
        CROSSHAIR
    }

    public Cursor getCursor() {
        return Cursor.DEFAULT;
    }
}
