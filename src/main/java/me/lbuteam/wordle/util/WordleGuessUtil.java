package me.lbuteam.wordle.util;

import java.util.*;

public class WordleGuessUtil {
    private static final int NONE_CORRECT = 0;
    private static final int CONTAINS = 1;
    private static final int CORRECT = 2;

    public static int[] guess(final String guess, final String word) {
        final char[] guessLetters = guess.toUpperCase().toCharArray();
        final char[] wordLetters = word.toUpperCase().toCharArray();

        final Set<Character> remainingWordLetters = new HashSet<>();
        final int[] result = new int[] { NONE_CORRECT, NONE_CORRECT, NONE_CORRECT, NONE_CORRECT, NONE_CORRECT };
        for (int index = 0; index < word.length(); index++) {
            final char guessLetter = guessLetters[index];
            final char wordLetter = wordLetters[index];
            if (guessLetter != wordLetter) {
                remainingWordLetters.add(wordLetter);
                continue;
            }

            result[index] = CORRECT;
        }

        if (remainingWordLetters.isEmpty()) return result;

        for (int index = 0; index < guess.length(); index++) {
            final char guessLetter = guessLetters[index];
            if (!remainingWordLetters.remove(guessLetter)) continue;

            result[index] = CONTAINS;
        }

        return result;
    }
}
