package com.ignpurple.wordle.ui.component;

import com.ignpurple.wordle.game.WordleGame;
import com.ignpurple.wordle.ui.UIConstant;
import com.ignpurple.wordle.util.WordleGuessUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class GuessBox {
    private final WordleGame game;
    private final JFrame parent;
    private final JPanel panel;
    private int index = 0;
    private final List<GuessLetterBox> letterBoxes;

    public GuessBox(final WordleGame game, final JFrame parent) {
        this.game = game;
        this.parent = parent;
        this.letterBoxes = new ArrayList<>();
        this.panel = this.initialize();
    }

    public JPanel getPanel() {
        return this.panel;
    }

    public void guess() {
        final String guess = this.letterBoxes.stream()
                .map((box) -> box.label().getText())
                .collect(Collectors.joining());
        if (guess.strip().length() != 5) return;

        new Thread(() -> {
            try {
                final int[] result = WordleGuessUtil.guess(guess, this.game.getWord());
                for (int index = 0; index < 5; index++) {
                    final GuessLetterBox letterBox = this.letterBoxes.get(index);
                    letterBox.inner().setBackground(
                            result[index] == 0
                                    ? UIConstant.LETTER_BACKGROUND_COLOR
                                    : (result[index] == 1
                                       ? UIConstant.LETTER_CONTAINING_COLOR
                                       : UIConstant.LETTER_CORRECT_COLOR
                            )
                    );
                    this.panel.repaint();
                    Thread.sleep(200);
                }
            } catch (final Exception exception) {
                exception.printStackTrace();
            }
        }).start();
    }

    public void removeCharacter() {
        if (this.index <= 0) return;

        this.index--;
        final GuessLetterBox letterBox = this.letterBoxes.get(this.index);
        letterBox.label().setText(" ");
        this.panel.repaint();
    }

    public void addCharacter(final char character) {
        if (this.index >= this.letterBoxes.size()) return;

        final GuessLetterBox letterBox = this.letterBoxes.get(this.index);
        letterBox.label().setText(String.valueOf(character).toUpperCase());

        this.index++;
        this.panel.repaint();
    }

    private JPanel createLetterBox() {
        final JPanel outline = new JPanel();
        outline.setLayout(null);
        outline.setPreferredSize(new Dimension(UIConstant.LETTER_BOX_WIDTH, UIConstant.LETTER_BOX_HEIGHT));
        outline.setSize(UIConstant.LETTER_BOX_WIDTH, UIConstant.LETTER_BOX_HEIGHT);
        outline.setBackground(Color.BLACK);

        final JPanel inner = new JPanel();
        inner.setLayout(new GridBagLayout());
        inner.setBounds(4, 4, UIConstant.LETTER_BOX_WIDTH - 8, UIConstant.LETTER_BOX_HEIGHT - 8);
        inner.setBackground(UIConstant.LETTER_BACKGROUND_COLOR);

        final JLabel label = new JLabel(" ");
        final Font font = new Font("Consolas", Font.BOLD, 48);
        label.setFont(font);
        label.setForeground(Color.BLACK);

        this.letterBoxes.add(new GuessLetterBox(outline, inner, label));
        inner.add(label);
        outline.add(inner);

        return outline;
    }

    private JPanel initialize() {
        final JPanel panel = new JPanel();
        panel.setLayout(null); // Fix: Deactivate layouts to allow absolute pixel placement via setBounds

        final int parentWidth = this.parent.getWidth();
        final int width = 450;
        panel.setBounds((parentWidth - width) / 2, 0, width, 80);

        final int letters = 4;
        final int xAlignmentPerLetter = (width - UIConstant.LETTER_BOX_WIDTH) / letters;

        for (int i = 0; i < 5; i++) {
            final JPanel letterBox = this.createLetterBox();
            letterBox.setBounds(xAlignmentPerLetter * i, 0, UIConstant.LETTER_BOX_WIDTH, UIConstant.LETTER_BOX_HEIGHT);
            panel.add(letterBox);
        }

        return panel;
    }
}