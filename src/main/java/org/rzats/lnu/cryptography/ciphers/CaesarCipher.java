package org.rzats.lnu.cryptography.ciphers;

import org.rzats.lnu.cryptography.common.MathUtilities;

/**
 * A simple substitution cipher.
 * Each letter in the plaintext is replaced by a letter some fixed number of positions down the alphabet.
 */
public class CaesarCipher implements Cipher {
    /**
     * The size of the Latin alphabet - used in the modulo operator
     */
    private static final int ALPHABET_SIZE = 26;
    /**
     * The ASCII code of 'A'
     */
    private static final int ASCII_A_UPPERCASE = 65;
    /**
     * The ASCII code of 'a'
     */
    private static final int ASCII_A_LOWERCASE = 97;

    /**
     * The right shift parameter - acts as a jey
     */
    private int shift;

    public CaesarCipher(int shift) {
        this.shift = shift;
    }

    @Override
    public int[] encrypt(int[] plaintext) {
        int[] ciphertext = new int[plaintext.length];

        for (int i = 0; i < plaintext.length; i++) {
            // If the character is alphabetic, encrypt it, otherwise add it to the ciphertext unchanged
            if (Character.isAlphabetic(plaintext[i])) {
                if (Character.isUpperCase(plaintext[i])) {
                    ciphertext[i] = ASCII_A_UPPERCASE + MathUtilities.modulo(plaintext[i] - ASCII_A_UPPERCASE + shift, ALPHABET_SIZE);
                } else {
                    ciphertext[i] = ASCII_A_LOWERCASE + MathUtilities.modulo(plaintext[i] - ASCII_A_LOWERCASE + shift, ALPHABET_SIZE);
                }
            } else {
                ciphertext[i] = plaintext[i];
            }
        }

        return ciphertext;
    }

    @Override
    public int[] decrypt(int[] ciphertext) {
        int[] plaintext = new int[ciphertext.length];

        for (int i = 0; i < ciphertext.length; i++) {
            // If the character is alphabetic, decrypt it, otherwise add it to the plaintext unchanged
            if (Character.isAlphabetic(ciphertext[i])) {
                if (Character.isUpperCase(ciphertext[i])) {
                    plaintext[i] = ASCII_A_UPPERCASE + MathUtilities.modulo(ciphertext[i] - ASCII_A_UPPERCASE - shift, ALPHABET_SIZE);
                } else {
                    plaintext[i] = ASCII_A_LOWERCASE + MathUtilities.modulo(ciphertext[i] - ASCII_A_LOWERCASE - shift, ALPHABET_SIZE);
                }
            } else {
                plaintext[i] = ciphertext[i];
            }
        }

        return plaintext;
    }
}
