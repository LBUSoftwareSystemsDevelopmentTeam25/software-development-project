package me.lbuteam.wordle;

import me.lbuteam.wordle.game.WordleGame;
import me.lbuteam.wordle.ui.WordleUI;

public class WordleMain {
    private final WordleGame game;
    private final WordleUI ui;

    public WordleMain() {
        this.game = new WordleGame(this);
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