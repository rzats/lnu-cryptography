package org.rzats.lnu.cryptography.cryptosystem;

import org.rzats.lnu.cryptography.common.MathUtilities;

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

    public int decrypt(int ciphertext) {
        return MathUtilities.modularExponentiation(ciphertext, this.privateKey, this.modulus);
    }
}
