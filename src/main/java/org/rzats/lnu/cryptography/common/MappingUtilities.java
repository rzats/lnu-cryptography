package org.rzats.lnu.cryptography.common;

public class MappingUtilities {
    private MappingUtilities() {

    }

    /**
     * Transforms a string into an array of ASCII integers.
     *
     * @param text The string to transform.
     * @return An array of integers representing the string.
     */
    public static int[] toASCIIArray(String text) {
        int[] array = new int[text.length()];

        for (int i = 0; i < text.length(); i++) {
            array[i] = text.codePointAt(i);
        }

        return array;
    }

    /**
     * Transforms an array of ASCII integers into a string.
     *
     * @param array The array to transform.
     * @return The original string.
     */
    public static String toText(int[] array) {
        StringBuilder text = new StringBuilder(array.length);

        for (int i = 0; i < array.length; i++) {
            text.append((char) array[i]);
        }

        return text.toString();
    }
}
