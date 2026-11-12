package me.lbuteam.wordle.ui;

import me.lbuteam.wordle.WordleMain;
import me.lbuteam.wordle.ui.component.GuessComponent;
import me.lbuteam.wordle.ui.handler.InputHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class WordleUI {
    private final WordleMain wordle;
    private final JFrame ui;
    private GuessComponent guessComponent;

    public WordleUI(final WordleMain wordle) {
        this.wordle = wordle;
        this.ui = this.initialize();
        this.guessComponent = new GuessComponent(this.wordle, this);
    }

    public JFrame getFrame() {
        return this.ui;
    }

    public void add(final JComponent component) {
        this.ui.add(component);
    }

    public void start() {
        this.ui.setVisible(true);
    }

    public void handleKeyPress(final KeyEvent event) {
        if (this.wordle.getGame().isDone()) return;

        final int keyCode = event.getKeyCode();
        if (keyCode == KeyEvent.VK_BACK_SPACE) {
            this.guessComponent.removeCharacter();
            return;
        }

        if (keyCode == KeyEvent.VK_ENTER) {
            this.guessComponent.guessWord();
            return;
        }

        if (keyCode < KeyEvent.VK_A || keyCode > KeyEvent.VK_Z) return;

        this.guessComponent.addCharacter(event.getKeyChar());
    }

    private JFrame initialize() {
        final JFrame ui = new JFrame();
        ui.setTitle("Wordle");
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setLayout(null);
        ui.setBackground(UIConstant.INITIAL_BACKGROUND_COLOR);
        ui.setResizable(false);
        ui.setSize(600, 800);
        ui.addKeyListener(new InputHandler(this));
        return ui;
    }
}
