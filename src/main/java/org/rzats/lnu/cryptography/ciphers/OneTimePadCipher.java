package org.rzats.lnu.cryptography.ciphers;

/**
 * A "perfect" encryption method.
 * Pairs a plaintext with a random secret key (one-time pad) used in the Vigenere cipher.
 */
public class OneTimePadCipher extends VigenereCipher {
    /**
     * True if this one-time pad has already been used, false otherwise.
     */
    private boolean used;

    public OneTimePadCipher(int[] key) {
        super(key);
        this.used = false;
    }

    @Override
    public int[] encrypt(int[] plaintext) {
        if (this.key.length != plaintext.length) {
            throw new IllegalArgumentException("The key should be exactly as long as the message being encrypted!");
        }
        if (this.used) {
            throw new SecurityException("This key has already been used and should not be reused!");
        }

        this.used = true;
        return super.encrypt(plaintext);
    }

    @Override
    public int[] decrypt(int[] ciphertext) {
        if (this.key.length != ciphertext.length) {
            throw new IllegalArgumentException("The key should be exactly as long as the message being encrypted!");
        }

        return super.decrypt(ciphertext);
    }
}
