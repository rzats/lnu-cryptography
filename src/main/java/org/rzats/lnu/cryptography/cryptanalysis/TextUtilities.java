package org.rzats.lnu.cryptography.cryptanalysis;

import org.rzats.lnu.cryptography.common.MathUtilities;

import java.util.HashMap;
import java.util.Map;

public class TextUtilities {
    private TextUtilities() {

    }

    /**
     * Build a character frequency distribution table for a given string.
     *
     * @param text The string to build a frequency distribution for.
     * @return A frequency distribution table for the given string.
     */
    public static Map<Character, Double> frequencyDistribution(String text) {
        // Remove non-alphabetic characters and convert to uppercase
        text = text.replaceAll("[^a-zA-Z]", "").toUpperCase();
        Map<Character, Double> frequencyDistribution = new HashMap<>(26);
        for (char c = 'A'; c <= 'Z'; c++) {
            frequencyDistribution.put(c, 0.0);
        }
        for (Character c : text.toCharArray()) {
            frequencyDistribution.put(c, frequencyDistribution.get(c) + 1);
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            frequencyDistribution.put(c, MathUtilities.round(frequencyDistribution.get(c) / text.length(), 5));
        }
        return frequencyDistribution;
    }
}
