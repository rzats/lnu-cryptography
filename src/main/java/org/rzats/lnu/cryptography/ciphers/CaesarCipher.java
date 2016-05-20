package org.rzats.lnu.cryptography.ciphers;

import org.rzats.lnu.cryptography.common.CryptoConstants;
import org.rzats.lnu.cryptography.common.MathUtilities;

/**
 * A simple substitution cipher.
 * Each letter in the plaintext is replaced by a letter some fixed number of positions down the alphabet.
 */
public class CaesarCipher implements Cipher {
    /**
     * The right shift parameter - acts as a key.
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
                    ciphertext[i] = CryptoConstants.ASCII_A_UPPERCASE
                            + MathUtilities.modulo(plaintext[i] - CryptoConstants.ASCII_A_UPPERCASE + shift, CryptoConstants.ALPHABET_SIZE);
                } else {
                    ciphertext[i] = CryptoConstants.ASCII_A_LOWERCASE
                            + MathUtilities.modulo(plaintext[i] - CryptoConstants.ASCII_A_LOWERCASE + shift, CryptoConstants.ALPHABET_SIZE);
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
                    plaintext[i] = CryptoConstants.ASCII_A_UPPERCASE
                            + MathUtilities.modulo(ciphertext[i] - CryptoConstants.ASCII_A_UPPERCASE - shift, CryptoConstants.ALPHABET_SIZE);
                } else {
                    plaintext[i] = CryptoConstants.ASCII_A_LOWERCASE
                            + MathUtilities.modulo(ciphertext[i] - CryptoConstants.ASCII_A_LOWERCASE - shift, CryptoConstants.ALPHABET_SIZE);
                }
            } else {
                plaintext[i] = ciphertext[i];
            }
        }

        return plaintext;
    }
}
