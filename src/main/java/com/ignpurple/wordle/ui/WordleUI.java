package com.ignpurple.wordle.ui;

import com.ignpurple.wordle.WordleMain;import com.ignpurple.wordle.ui.component.GuessBox;
import com.ignpurple.wordle.ui.handler.InputHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class WordleUI {
    private final WordleMain wordle;
    private final JFrame ui;
    private GuessBox guessBox;

    public WordleUI(final WordleMain wordle) {
        this.wordle = wordle;
        this.ui = this.initialize();
    }

    public void start() {
        this.ui.setVisible(true);
        this.guessBox = new GuessBox(this.wordle.getGame(), this.ui);
        this.ui.add(this.guessBox.getPanel());
    }

    public void handleKeyPress(final KeyEvent event) {
        final int keyCode = event.getKeyCode();
        if (keyCode == KeyEvent.VK_BACK_SPACE) {
            this.guessBox.removeCharacter();
            return;
        }

        if (keyCode == KeyEvent.VK_ENTER) {
            this.guessBox.guess();
            return;
        }

        if (keyCode < KeyEvent.VK_A || keyCode > KeyEvent.VK_Z) return;

        this.guessBox.addCharacter(event.getKeyChar());
    }

    private JFrame initialize() {
        final JFrame ui = new JFrame();
        ui.setTitle("Wordle");
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setLayout(null);
        ui.setBackground(Color.WHITE);
        ui.setResizable(false);
        ui.setSize(600, 800);
        ui.addKeyListener(new InputHandler(this));
        return ui;
    }
}
