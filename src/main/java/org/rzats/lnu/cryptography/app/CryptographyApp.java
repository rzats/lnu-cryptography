package org.rzats.lnu.cryptography.app;

import org.rzats.lnu.cryptography.ciphers.CaesarCipher;
import org.rzats.lnu.cryptography.ciphers.Cipher;
import org.rzats.lnu.cryptography.ciphers.OneTimePadCipher;
import org.rzats.lnu.cryptography.ciphers.RailFenceCipher;
import org.rzats.lnu.cryptography.ciphers.VigenereCipher;
import org.rzats.lnu.cryptography.common.MappingUtilities;
import org.rzats.lnu.cryptography.common.MathUtilities;
import org.rzats.lnu.cryptography.cryptosystem.DiffieHellmanParty;
import org.rzats.lnu.cryptography.cryptosystem.RSACryptosystem;

public class CryptographyApp {
    private CryptographyApp() {

    }

    /**
     * A generalized method to showcase a {@code Cipher} implementation.
     *
     * @param cipher The {@code Cipher} to be used.
     * @param text   The initial plaintext string.
     */
    private static void demo(Cipher cipher, String text) {
        int[] plaintext = MappingUtilities.toASCIIArray(text);
        int[] ciphertext = cipher.encrypt(plaintext);
        int[] decryptedPlaintext = cipher.decrypt(ciphertext);

        System.out.print(String.format("Plaintext: %s%nCiphertext: %s%nDecrypted plaintext: %s%n",
                text,
                MappingUtilities.toText(ciphertext),
                MappingUtilities.toText(decryptedPlaintext)));
    }

    /**
     * The main entry point for the application.
     * Demonstrates all the existing ciphers, using verifiable (Wikipedia) data if possible.
     *
     * @param args The console arguments.
     */
    public static void main(String[] args) {
        // Ciphers

        System.out.println("Caesar cipher:");
        Cipher caesar = new CaesarCipher(23);
        demo(caesar, "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG");

        System.out.println("Rail-fence cipher:");
        Cipher railFence = new RailFenceCipher(3);
        demo(railFence, "WEAREDISCOVEREDFLEEATONCE");

        System.out.println("Vigenere cipher:");
        Cipher vigenere = new VigenereCipher(MappingUtilities.toASCIIArray("LEMON"));
        demo(vigenere, "ATTACK AT DAWN");

        System.out.println("One-time pad:");
        int[] pad = MathUtilities.random(5, 65, 90);
        System.out.println("Using (pseudo-)randomly generated one-time pad: " + MappingUtilities.toText(pad));
        Cipher oneTimePad = new OneTimePadCipher(pad);
        demo(oneTimePad, "HELLO");

        // Cryptosystems

        System.out.println("RSA:");
        RSACryptosystem rsa = new RSACryptosystem(61, 53, 17);
        int plaintext = 65;
        int ciphertext = rsa.encrypt(plaintext);
        int decryptedPlaintext = rsa.decrypt(ciphertext);
        System.out.print(String.format("Plaintext: %s%nCiphertext: %s%nDecrypted plaintext: %s%n",
                plaintext,
                ciphertext,
                decryptedPlaintext));

        System.out.println("Diffie-Hellman:");
        DiffieHellmanParty alice = new DiffieHellmanParty(23, 5);
        DiffieHellmanParty bob = new DiffieHellmanParty(23, 5);
        alice.setPrivateKey(6);
        bob.setPrivateKey(15);

        alice.sendPublicKey(bob);
        bob.sendPublicKey(alice);

        System.out.println("Alice's shared secret: " + alice.getSecret());
        System.out.print("Bob's shared secret: " + bob.getSecret());
    }
}
