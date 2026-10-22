package com.ignpurple.wordle;

import com.ignpurple.wordle.game.WordleGame;
import com.ignpurple.wordle.ui.WordleUI;

public class WordleMain {
    private final WordleGame game;
    private final WordleUI ui;

    public WordleMain() {
        this.game = new WordleGame();
        this.ui = new WordleUI(this);
    }

    public WordleGame getGame() {
        return this.game;
    }

    public void start() {
        this.ui.start();
    }

    public static void main(String[] args) {
        new WordleMain().start();
    }
}