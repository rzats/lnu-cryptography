package org.rzats.lnu.cryptography.cryptosystem;

/**
 * A party of the Diffie-Hellman Key Exchange that communicates over privateKey public network.
 * <p>
 * {@code long} values used here because of overflow bugs, even on small examples.
 */
public class DiffieHellmanParty {
    /**
     * An algorithm parameter shared between two parties.
     */
    private long modulus;

    /**
     * An algorithm parameter shared between two parties.
     */
    private long base;

    /**
     * A private key known only to this party.
     */
    private long privateKey;

    /**
     * A secret shared between two parties.
     */
    private long sharedSecret;

    public DiffieHellmanParty(long modulus, long base) {
        this.modulus = modulus;
        this.base = base;
    }

    /**
     * FOR DEMONSTRATION PURPOSES ONLY - a party should choose (or randomly generate) its' own key.
     *
     * @param privateKey The value to set privateKey to.
     */
    public void setPrivateKey(long privateKey) {
        this.privateKey = privateKey;
    }

    /**
     * Sends this party's public key to the other party to calculate the shared secret.
     *
     * @param other The party to send the public key to.
     */
    public void sendPublicKey(DiffieHellmanParty other) {
        other.receivePublicKey((long) Math.pow((double) base, (double) privateKey) % modulus);
    }

    /**
     * Receive a public key from the other party and calculate the shared secret.
     *
     * @param publicKey The other party's public key.
     */
    public void receivePublicKey(long publicKey) {
        this.sharedSecret = (long) Math.pow((double) publicKey, (double) privateKey) % modulus;
    }

    /**
     * FOR DEMONSTRATION PURPOSES ONLY - the secret key should obviously be kept secret.
     *
     * @return
     */
    public long getSecret() {
        return this.sharedSecret;
    }
}
