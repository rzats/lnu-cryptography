package org.rzats.lnu.cryptography.app;

import org.rzats.lnu.cryptography.ciphers.CaesarCipher;
import org.rzats.lnu.cryptography.ciphers.Cipher;
import org.rzats.lnu.cryptography.ciphers.OneTimePadCipher;
import org.rzats.lnu.cryptography.ciphers.RailFenceCipher;
import org.rzats.lnu.cryptography.ciphers.VigenereCipher;
import org.rzats.lnu.cryptography.common.MappingUtilities;
import org.rzats.lnu.cryptography.common.MathUtilities;
import org.rzats.lnu.cryptography.cryptanalysis.TextUtilities;
import org.rzats.lnu.cryptography.cryptosystem.DiffieHellmanParty;
import org.rzats.lnu.cryptography.cryptosystem.RSACryptosystem;

import java.util.HashMap;

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

        System.out.println("--- Caesar cipher: ---");
        Cipher caesar = new CaesarCipher(23);
        demo(caesar, "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG");

        System.out.println("--- Rail-fence cipher: ---");
        Cipher railFence = new RailFenceCipher(3);
        demo(railFence, "WEAREDISCOVEREDFLEEATONCE");

        System.out.println("--- Vigenere cipher: ---");
        Cipher vigenere = new VigenereCipher(MappingUtilities.toASCIIArray("LEMON"));
        demo(vigenere, "ATTACK AT DAWN");

        System.out.println("--- One-time pad: --- ");
        int[] pad = MathUtilities.random(5, 65, 90);
        System.out.println("Using (pseudo-)randomly generated one-time pad: " + MappingUtilities.toText(pad));
        Cipher oneTimePad = new OneTimePadCipher(pad);
        demo(oneTimePad, "HELLO");

        // Cryptanalysis

        System.out.println("--- Frequency tables: --- ");
        System.out.println("English frequency table: " + new HashMap<Character, Double>() {{
            put('A', 0.08167);
            put('B', 0.01492);
            put('C', 0.02782);
            put('D', 0.04253);
            put('E', 0.12702);
            put('F', 0.02228);
            put('G', 0.02015);
            put('H', 0.06094);
            put('I', 0.06966);
            put('J', 0.00154);
            put('K', 0.00772);
            put('L', 0.04025);
            put('M', 0.02406);
            put('N', 0.06749);
            put('O', 0.07507);
            put('P', 0.01929);
            put('Q', 0.00095);
            put('R', 0.05987);
            put('S', 0.06327);
            put('T', 0.09056);
            put('U', 0.02758);
            put('V', 0.00978);
            put('W', 0.02351);
            put('X', 0.00150);
            put('Y', 0.01974);
            put('Z', 0.00074);
        }});
        System.out.println("Example frequency table: " + TextUtilities.frequencyDistribution("Hereupon Legrand arose, with a grave and stately air, and brought me the beetle\n" +
                "from a glass case in which it was enclosed. It was a beautiful scarabaeus, and, at\n" +
                "that time, unknown to naturalists—of course a great prize in a scientific point\n" +
                "of view. There were two round black spots near one extremity of the back, and a\n" +
                "long one near the other. The scales were exceedingly hard and glossy, with all the\n" +
                "appearance of burnished gold. The weight of the insect was very remarkable, and,\n" +
                "taking all things into consideration, I could hardly blame Jupiter for his opinion\n" +
                "respecting it."));

        // Cryptosystems

        System.out.println("--- RSA: --- ");
        RSACryptosystem rsa = new RSACryptosystem(61, 53, 17);
        int plaintext = 65;
        int ciphertext = rsa.encrypt(plaintext);
        int decryptedPlaintext = rsa.decrypt(ciphertext);
        System.out.print(String.format("Plaintext: %s%nCiphertext: %s%nDecrypted plaintext: %s%n",
                plaintext,
                ciphertext,
                decryptedPlaintext));

        System.out.println("--- Diffie-Hellman key exchange: --- ");
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
