package org.rzats.lnu.cryptography.ciphers;

/**
 * Designates a class that can encrypt a {@code int[]} message and decrypt it into its' original form.
 * <p>
 * Additional variables (keys, etc.) can be defined in the class constructors for this interface's implementations.
 */
public interface Cipher {
    /**
     * Encrypts a given {@code int[]} message
     *
     * @param plaintext The message to encrypt
     * @return The encrypted plaintext
     */
    public int[] encrypt(int[] plaintext);

    /**
     * Decrypts a given {@code int[]} cipher
     *
     * @param ciphertext The cipher to decrypt
     * @return The original plaintext
     */
    public int[] decrypt(int[] ciphertext);
}
