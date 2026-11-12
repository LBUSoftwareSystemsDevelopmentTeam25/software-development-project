package me.lbuteam.wordle.ui.component;

import me.lbuteam.wordle.WordleMain;
import me.lbuteam.wordle.ui.WordleUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GuessComponent {
    private final WordleMain wordle;
    private final WordleUI ui;
    private int guess = 0;
    private final List<GuessBox> guessBoxes;

    public GuessComponent(final WordleMain wordle, final WordleUI ui) {
        this.wordle = wordle;
        this.ui = ui;
        this.guessBoxes = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            this.guessBoxes.add(new GuessBox(this.wordle, this, this.ui.getFrame()));
        }

        this.initialize();
    }

    private void initialize() {
        for (int index = 0; index < this.guessBoxes.size(); index++) {
            final GuessBox guessBox = this.guessBoxes.get(index);
            final JPanel panel = guessBox.getPanel();
            final Rectangle rectangle = panel.getBounds();
            panel.setBounds((int) rectangle.getX(), index * 90, (int) rectangle.getWidth(), (int) rectangle.getHeight());
            this.ui.add(panel);
        }
    }

    public void addCharacter(final char character) {
        this.guessBoxes.get(this.guess).addCharacter(character);
    }

    public void removeCharacter() {
        this.guessBoxes.get(this.guess).removeCharacter();
    }

    public void guessWord() {
        this.guessBoxes.get(this.guess).guess();
    }

    public void incrementGuess() {
        if (this.guess + 1 >= this.guessBoxes.size()) {
            return;
        }

        this.guess++;
    }
}
