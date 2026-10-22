package com.ignpurple.wordle.ui.handler;

import com.ignpurple.wordle.ui.WordleUI;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputHandler extends KeyAdapter {
    private final WordleUI ui;

    public InputHandler(final WordleUI ui) {
        this.ui = ui;
    }

    @Override
    public void keyPressed(final KeyEvent event) {
        this.ui.handleKeyPress(event);
    }
}
