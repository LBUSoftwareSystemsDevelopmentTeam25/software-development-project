package me.lbuteam.wordle.util;

import java.util.*;

public class WordleGuessUtil {
    private static final int NONE_CORRECT = 0;
    private static final int CONTAINS = 1;
    private static final int CORRECT = 2;

    public static int[] guess(final String guess, final String word) {
        final char[] guessLetters = guess.toUpperCase().toCharArray();
        final char[] wordLetters = word.toUpperCase().toCharArray();

        final int[] result = new int[wordLetters.length];
        final int[] letterCounts = new int[26];

        for (int index = 0; index < wordLetters.length; index++) {
            if (guessLetters[index] == wordLetters[index]) {
                result[index] = CORRECT;
                continue;
            }

            letterCounts[wordLetters[index] - 'A']++;
        }

        for (int index = 0; index < guessLetters.length; index++) {
            if (result[index] == CORRECT) {
                continue;
            }

            final int letterIdx = guessLetters[index] - 'A';
            if (letterIdx < 0 || letterIdx >= 26 || letterCounts[letterIdx] <= 0) {
                continue;
            }

            result[index] = CONTAINS;
            letterCounts[letterIdx]--;
        }

        return result;
    }
}
