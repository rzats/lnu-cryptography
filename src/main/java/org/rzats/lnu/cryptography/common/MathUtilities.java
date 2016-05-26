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
     * Generates an array of random integer values within a specific range.
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

    /**
     * Calculates the modular multiplicative inverse of an integer a modulo m.
     * <p>
     * Uses the extended Euclidean algorithm.
     *
     * @param a A positive integer.
     * @param m A positive integer (co-prime to a).
     * @return An integer such that ax ≅ 1 (mod m).
     */
    public static int modularMultiplicativeInverse(int a, int m) {
        return modulo(extendedEuclideanAlgorithm(m, a)[2], m);
    }


    /**
     * Recursively calculates gcd(a, b) and integers x, y such that:
     * <p>
     * ax + by = gcd(a,b)
     *
     * @param a A positive integer.
     * @param b A positive integer;
     * @return A sequence of of gcd(a,b), x and y.
     */
    private static int[] extendedEuclideanAlgorithm(int a, int b) {
        // A series of remainders (ri) and two other sequences (si, ti).
        int[] sequences = new int[3];

        // qi.
        int q;

        if (b == 0) {
            // Declare the initial values for r, s and t
            sequences[0] = a;
            sequences[1] = 1;
            sequences[2] = 0;
        } else {
            q = a / b;
            sequences = extendedEuclideanAlgorithm(b, a % b);

            int temp = sequences[1] - sequences[2] * q;
            sequences[1] = sequences[2];
            sequences[2] = temp;
        }

        return sequences;
    }


    /**
     * Performs modular exponentiation over a modulus.
     *
     * @param d The base.
     * @param e The exponent.
     * @param m The divisor.
     * @return d^e (mod m)
     */
    public static int modularExponentiation(int d, int e, int m) {
        int c = 1;

        for (int i = 0; i < e; i++) {
            c = ((c * d) % m);
        }

        return c;
    }

}
