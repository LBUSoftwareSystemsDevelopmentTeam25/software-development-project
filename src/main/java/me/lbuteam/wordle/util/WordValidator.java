package me.lbuteam.wordle.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class WordValidator {

    private final Set<String> validWords = new HashSet<>();

    public WordValidator() {
        loadWords("/userwords.txt");
        loadWords("/words.txt");
    }

    private void loadWords(String filename) {
        try (InputStream stream = getClass().getResourceAsStream(filename)) {

            if (stream == null){
                throw new IllegalStateException(filename + "not found");
            }

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stream, StandardCharsets.UTF_8))) {

                String line;

                while ((line = reader.readLine()) != null) {
                    String word = line.trim().toLowerCase();

                    if (word.matches("[a-z]{5}")) {
                        validWords.add(word);
                    }
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load " + filename, e);
        }
    }
    public boolean isValidWord(String word) {
        if (word == null) {
            return false;
        }
        return validWords.contains(word.trim().toLowerCase());
    }
}
