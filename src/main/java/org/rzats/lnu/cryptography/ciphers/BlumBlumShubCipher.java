package org.rzats.lnu.cryptography.ciphers;

/**
 * A pseudorandom number generator.
 * <p>
 * Encrypts the ith element in the input array using a bitwise XOR with the ith element of the BBS sequence.
 */
public class BlumBlumShubCipher implements Cipher {
    /**
     * A product of two distinct prime numbers (p*q), where p ≡ q ≡ 3 mod 4.
     */
    private int modulus;
    /**
     * The seed for the generator.
     */
    private int seed;

    public BlumBlumShubCipher(int p, int q, int x) {
        // Compute n = p*q.
        this.modulus = p * q;
        this.seed = x;
    }

    @Override
    public int[] encrypt(int[] plaintext) {
        int[] result = generateSequence(plaintext.length);

        for (int i = 0; i < plaintext.length; i++) {
            result[i] ^= plaintext[i];
        }

        return result;
    }

    @Override
    public int[] decrypt(int[] ciphertext) {
        int[] result = generateSequence(ciphertext.length);

        for (int i = 0; i < ciphertext.length; i++) {
            result[i] ^= ciphertext[i];
        }

        return result;
    }

    /**
     * Create a pseudorandom sequence of bits using the BBS formula.
     *
     * @param length The length of the sequence.
     * @return The sequence.
     */
    public int[] generateSequence(int length) {
        int[] result = new int[length];
        int x = (int) Math.pow(seed, 2) % modulus;

        for (int i = 0; i < length; i++) {
            x = (int) Math.pow(x, 2) % modulus;
            result[i] = x % 2;
        }

        return result;
    }
}
