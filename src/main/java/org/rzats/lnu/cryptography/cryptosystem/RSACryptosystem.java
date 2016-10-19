package org.rzats.lnu.cryptography.cryptosystem;

import org.rzats.lnu.cryptography.common.MathUtilities;

import java.util.Arrays;

/**
 * A public-key cryptosystem used for symmetric cryptography.
 */
public class RSACryptosystem {
    /**
     * A product of two distinct prime numbers (p*q).
     */
    private int modulus;
    /**
     * The public key exponent.
     */
    private int publicKey;
    /**
     * The private key exponent.
     */
    private int privateKey;

    public RSACryptosystem(int p, int q, int publicKey) {
        // Compute n = p*q.
        this.modulus = p * q;

        // Compute the public key.
        this.publicKey = publicKey;

        // Compute Euler's totient function.
        int phi = (p - 1) * (q - 1);

        // Determine the private key.
        this.privateKey = MathUtilities.modularMultiplicativeInverse(publicKey, phi);
    }

    public int encrypt(int plaintext) {
        return MathUtilities.modularExponentiation(plaintext, this.publicKey, this.modulus);
    }

    public String encrypt(String plaintext) {
        int[] hashedPlaintext = hash(plaintext);
        for (int i = 0; i < hashedPlaintext.length; i++) {
            hashedPlaintext[i] = encrypt(hashedPlaintext[i]);
        }
        return reverseHash(hashedPlaintext);
    }

    public int decrypt(int ciphertext) {
        return MathUtilities.modularExponentiation(ciphertext, this.privateKey, this.modulus);
    }

    public String decrypt(String ciphertext) {
        int[] decryptedCiphertext = hash(ciphertext);
        for (int i = 0; i < decryptedCiphertext.length; i++) {
            decryptedCiphertext[i] = decrypt(decryptedCiphertext[i]);
        }
        return reverseHash(decryptedCiphertext);
    }

    private int[] hash(String text) {
        char[] cArray = text.toCharArray();
        int[] hash = new int[text.length()];
        for (int i = 0; i < cArray.length; i++) {
            hash[i] = cArray[i] - 'a';
        }
        return hash;
    }

    private String reverseHash(int[] hash) {
        char[] cArray = new char[hash.length];
        for (int i = 0; i < hash.length; i++) {
            cArray[i] = (char) ('a' + hash[i]);
        }
        return new String(cArray);
    }
}
