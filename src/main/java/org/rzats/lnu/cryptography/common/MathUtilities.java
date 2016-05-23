package org.rzats.lnu.cryptography.common;

import java.util.Random;

public class MathUtilities {
    private MathUtilities() {

    }

    /**
     * An extension to Java's % operator that handles negative numbers.
     *
     * @param i The dividend.
     * @param j The divisor.
     * @return The remainder.
     */
    public static int modulo(int i, int j) {
        while (i < 0) {
            i += j;
        }

        return i % j;
    }

    /**
     * Generate an array of random integer values within a specific range.
     *
     * @param size     The size of the array.
     * @param minValue The minimum value of an item in the array.
     * @param maxValue The maximum value of an item in the array.
     * @return The randomly generated array.
     */
    public static int[] random(int size, int minValue, int maxValue) {
        Random random = new Random();
        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = random.nextInt(maxValue - minValue) + minValue;
        }

        return result;
    }
}
