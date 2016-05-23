package org.rzats.lnu.cryptography.ciphers;

import org.rzats.lnu.cryptography.common.CryptoConstants;
import org.rzats.lnu.cryptography.common.MathUtilities;

/**
 * A simple polyalphabetic substitution cipher.
 * Uses a series of different Caesar ciphers based on the letters of a keyword.
 */
public class VigenereCipher implements Cipher {
    /**
     * The key for the cipher.
     */
    protected int[] key;

    public VigenereCipher(int[] key) {
        // Convert the key to a positional, case-insensitive representation (a/A -> 0, b/B -> 1...)
        for (int i = 0; i < key.length; i++) {
            if (Character.isAlphabetic(key[i])) {
                if (Character.isUpperCase(key[i])) {
                    key[i] = key[i] - CryptoConstants.ASCII_A_UPPERCASE;
                } else {
                    key[i] = key[i] - CryptoConstants.ASCII_A_LOWERCASE;
                }
            } else {
                throw new IllegalArgumentException("Vigenere key can only contain Latin letters!");
            }
        }

        this.key = key;
    }

    @Override
    public int[] encrypt(int[] plaintext) {
        int[] ciphertext = new int[plaintext.length];

        int k = 0;
        for (int i = 0; i < plaintext.length; i++) {
            // If the character is alphabetic, encrypt it, otherwise add it to the ciphertext unchanged
            if (Character.isAlphabetic(plaintext[i])) {
                if (Character.isUpperCase(plaintext[i])) {
                    ciphertext[i] = CryptoConstants.ASCII_A_UPPERCASE
                            + MathUtilities.modulo(
                            plaintext[i] - CryptoConstants.ASCII_A_UPPERCASE + key[MathUtilities.modulo(k, key.length)],
                            CryptoConstants.ALPHABET_SIZE);
                } else {
                    ciphertext[i] = CryptoConstants.ASCII_A_LOWERCASE
                            + MathUtilities.modulo(
                            plaintext[i] - CryptoConstants.ASCII_A_LOWERCASE + key[MathUtilities.modulo(k, key.length)],
                            CryptoConstants.ALPHABET_SIZE);
                }
                k++;
            } else {
                ciphertext[i] = plaintext[i];
            }
        }

        return ciphertext;
    }

    @Override
    public int[] decrypt(int[] ciphertext) {
        int[] plaintext = new int[ciphertext.length];

        int k = 0;
        for (int i = 0; i < ciphertext.length; i++) {
            // If the character is alphabetic, decrypt it, otherwise add it to the plaintext unchanged
            if (Character.isAlphabetic(ciphertext[i])) {
                if (Character.isUpperCase(ciphertext[i])) {
                    plaintext[i] = CryptoConstants.ASCII_A_UPPERCASE
                            + MathUtilities.modulo(
                            ciphertext[i] - CryptoConstants.ASCII_A_UPPERCASE - key[MathUtilities.modulo(k, key.length)],
                            CryptoConstants.ALPHABET_SIZE);
                } else {
                    plaintext[i] = CryptoConstants.ASCII_A_LOWERCASE + MathUtilities.modulo(
                            ciphertext[i] - CryptoConstants.ASCII_A_LOWERCASE - key[MathUtilities.modulo(k, key.length)],
                            CryptoConstants.ALPHABET_SIZE);
                }
                k++;
            } else {
                plaintext[i] = ciphertext[i];
            }
        }

        return plaintext;
    }
}
