package me.lbuteam.wordle.ui.component;

import me.lbuteam.wordle.WordleMain;
import me.lbuteam.wordle.game.WordleGame;
import me.lbuteam.wordle.ui.UIConstant;
import me.lbuteam.wordle.util.WordleGuessUtil;
import me.lbuteam.wordle.util.WordValidator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GuessBox {
    private static final WordValidator validator = new WordValidator();
    private final WordleGame game;
    private final GuessComponent guessComponent;
    private final JFrame parent;
    private final JPanel panel;
    private int index = 0;
    private final List<GuessLetterBox> letterBoxes;

    public GuessBox(final WordleMain wordleMain, final GuessComponent guessComponent, final JFrame parent) {
        this.game = wordleMain.getGame();
        this.guessComponent = guessComponent;
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

        if (!validator.isValidWord(guess)) {
            JOptionPane.showMessageDialog(
                    this.parent,
                    "Not a valid word! Please try again.",
                    "Invalid Word",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        this.guessComponent.incrementGuess();
        new Thread(() -> {
            try {
                final int[] result = WordleGuessUtil.guess(guess, this.game.getWord());
                for (int index = 0; index < 5; index++) {
                    final GuessLetterBox letterBox = this.letterBoxes.get(index);
                    letterBox.inner().setBackground(
                            result[index] == 0
                                    ? UIConstant.LETTER_INCORRECT_COLOR
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

        this.game.guessWord(guess);
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
        panel.setLayout(null);

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