package org.rzats.lnu.cryptography.common;

public class ArrayUtilities {
    private ArrayUtilities() {

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

    /**
     * Checks if a given integer array is a bit array, i.e. only contains 0/1 values.
     *
     * @param array The input array.
     * @return Whether the array only contains values in the set {0, 1}.
     */
    public static boolean isBitArray(int[] array) {
        for (int element : array) {
            if (element != 0 && element != 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Re-arranges the elements of an array according to a fixed permutation array.
     *
     * @param array       The input array.
     * @param permutation The permutation array (e.g. P10, P8).
     * @return The array with the permutation applied.
     */
    public static int[] permute(int[] array, int[] permutation) {
        int[] result = new int[permutation.length];
        for (int i = 0; i < permutation.length; i++) {
            result[i] = array[permutation[i] - 1];
        }
        return result;
    }

    /**
     * Shifts all the elements left by a given distance.
     *
     * @param array The input array.
     * @param shift The shift distance.
     * @return The array with the elements shifted left by a given distance.
     */
    public static int[] leftShift(int[] array, int shift) {
        int[] result = new int[array.length];
        int index;
        for (int i = 0; i < array.length; i++) {
            index = (array.length + i - shift) % array.length;
            result[index] = array[i];
        }
        return result;
    }

    /**
     * Applies a XOR over two arrays.
     *
     * @param first  The first array.
     * @param second The second array.
     * @return The result of applying XOR over two arrays.
     */
    public static int[] xor(int[] first, int[] second) {
        int[] result = new int[first.length];
        for (int i = 0; i < first.length; i++) {
            result[i] = second[i] ^ first[i];
        }
        return result;
    }
}
