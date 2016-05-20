package org.rzats.lnu.cryptography.common;

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
}
