package me.lbuteam.wordle.ui;

import me.lbuteam.wordle.WordleMain;import me.lbuteam.wordle.ui.component.GuessBox;
import me.lbuteam.wordle.ui.handler.InputHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class WordleUI {
    private final WordleMain wordle;
    private final JFrame ui;
    private int guess = 0;
    private final List<GuessBox> guessBoxes;

    public WordleUI(final WordleMain wordle) {
        this.wordle = wordle;
        this.ui = this.initialize();
        this.guessBoxes = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            this.guessBoxes.add(new GuessBox(this.wordle, this, this.ui));
        }
    }

    public void start() {
        this.ui.setVisible(true);
        for (int index = 0; index < this.guessBoxes.size(); index++) {
            final GuessBox guessBox = this.guessBoxes.get(index);
            final JPanel panel = guessBox.getPanel();
            final Rectangle rectangle = panel.getBounds();
            panel.setBounds((int) rectangle.getX(), index * 90, (int) rectangle.getWidth(), (int) rectangle.getHeight());
            this.ui.add(panel);
        }
    }

    public void incrementGuess() {
        this.guess++;
    }

    public void handleKeyPress(final KeyEvent event) {
        final int keyCode = event.getKeyCode();
        if (keyCode == KeyEvent.VK_BACK_SPACE) {
            this.guessBoxes.get(this.guess).removeCharacter();
            return;
        }

        if (keyCode == KeyEvent.VK_ENTER) {
            this.guessBoxes.get(this.guess).guess();
            return;
        }

        if (keyCode < KeyEvent.VK_A || keyCode > KeyEvent.VK_Z) return;

        this.guessBoxes.get(this.guess).addCharacter(event.getKeyChar());
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
