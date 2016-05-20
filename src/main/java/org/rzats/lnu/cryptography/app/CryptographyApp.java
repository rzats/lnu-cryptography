package org.rzats.lnu.cryptography.app;

import org.rzats.lnu.cryptography.ciphers.CaesarCipher;
import org.rzats.lnu.cryptography.ciphers.Cipher;
import org.rzats.lnu.cryptography.ciphers.RailFenceCipher;
import org.rzats.lnu.cryptography.common.MappingUtilities;

public class CryptographyApp {
    private CryptographyApp() {

    }

    /**
     * A generalized method to showcase a {@code Cipher} implementation
     *
     * @param cipher The {@code Cipher} to be used
     * @param text   The initial plaintext string
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
     * Demonstrates all the existing ciphers, using verifiable (Wikipedia) data if possible
     *
     * @param args The console arguments
     */
    public static void main(String[] args) {
        System.out.println("Caesar cipher:");
        Cipher caesar = new CaesarCipher(23);
        demo(caesar, "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG");

        System.out.println("Rail-fence cipher:");
        Cipher railFence = new RailFenceCipher(3);
        demo(railFence, "WEAREDISCOVEREDFLEEATONCE");
    }
}
