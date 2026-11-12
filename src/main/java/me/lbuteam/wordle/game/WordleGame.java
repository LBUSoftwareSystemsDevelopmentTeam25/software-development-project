package me.lbuteam.wordle.game;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class WordleGame {

    private final String word;

    public WordleGame() {
        List<String> words = new ArrayList<>();

        try(InputStream stream = getClass().getResourceAsStream("/words.txt")){

            if (stream == null){
                throw new IllegalStateException("words.txt not found");
            }

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    String candidate = line.trim().toLowerCase();

                    if (candidate.matches("[a-z]{5}") && !words.contains(candidate)){
                        words.add(candidate);
                    }
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load words", e);
        }
        if (words.isEmpty()) {
            throw new IllegalStateException("No valid words found");
        }

        Random random = new Random();
        this.word = words.get(random.nextInt(words.size()));

        System.out.println("Randomly selected word: " + this.word);
    }

    public String getWord() {
        return this.word;
    }
}

