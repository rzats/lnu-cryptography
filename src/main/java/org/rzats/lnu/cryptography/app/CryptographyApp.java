package org.rzats.lnu.cryptography.app;

import org.rzats.lnu.cryptography.ciphers.CaesarCipher;
import org.rzats.lnu.cryptography.ciphers.Cipher;
import org.rzats.lnu.cryptography.ciphers.OneTimePadCipher;
import org.rzats.lnu.cryptography.ciphers.RailFenceCipher;
import org.rzats.lnu.cryptography.ciphers.SimplifiedDESCipher;
import org.rzats.lnu.cryptography.ciphers.VigenereCipher;
import org.rzats.lnu.cryptography.common.ArrayUtilities;
import org.rzats.lnu.cryptography.common.MathUtilities;
import org.rzats.lnu.cryptography.cryptanalysis.TextUtilities;
import org.rzats.lnu.cryptography.cryptosystem.DiffieHellmanParty;
import org.rzats.lnu.cryptography.cryptosystem.RSACryptosystem;

import java.util.Arrays;
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
        int[] plaintext = ArrayUtilities.toASCIIArray(text);
        int[] ciphertext = cipher.encrypt(plaintext);
        int[] decryptedPlaintext = cipher.decrypt(ciphertext);

        System.out.print(String.format("Plaintext: %s%nCiphertext: %s%nDecrypted plaintext: %s%n",
            text,
            ArrayUtilities.toText(ciphertext),
            ArrayUtilities.toText(decryptedPlaintext)));
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
        Cipher vigenere = new VigenereCipher(ArrayUtilities.toASCIIArray("LEMON"));
        demo(vigenere, "ATTACK AT DAWN");

        System.out.println("--- One-time pad: --- ");
        int[] pad = MathUtilities.random(5, 65, 90);
        System.out.println("Using (pseudo-)randomly generated one-time pad: " + ArrayUtilities.toText(pad));
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
        RSACryptosystem rsa = new RSACryptosystem(3, 11, 7);
        String rsaPlaintext = "helloworld";
        String rsaCiphertext = rsa.encrypt(rsaPlaintext);
        String rsaDecryptedPlaintext = rsa.decrypt(rsaCiphertext);
        System.out.print(String.format("Plaintext: %s%nCiphertext: %s%nDecrypted plaintext: %s%n",
            rsaPlaintext,
            rsaCiphertext,
            rsaDecryptedPlaintext));

        System.out.println("--- Diffie-Hellman key exchange: --- ");
        DiffieHellmanParty alice = new DiffieHellmanParty(23, 5);
        DiffieHellmanParty bob = new DiffieHellmanParty(23, 5);
        alice.setPrivateKey(6);
        bob.setPrivateKey(15);

        alice.sendPublicKey(bob);
        bob.sendPublicKey(alice);

        System.out.println("Alice's shared secret: " + alice.getSecret());
        System.out.println("Bob's shared secret: " + bob.getSecret());

        // Simplified DES
        Cipher sdes = new SimplifiedDESCipher(new int[]{1, 1, 0, 0, 1, 1, 0, 0, 0, 1});
        int[] sdesPlaintext = {1, 1, 1, 1, 1, 1, 1, 1};
        int[] sdesCiphertext = sdes.encrypt(sdesPlaintext);
        int[] sdesDecryptedPlaintext = sdes.decrypt(sdesCiphertext);

        System.out.println("--- Simplified DES: --- ");
        System.out.print(String.format("Plaintext: %s%nCiphertext: %s%nDecrypted plaintext: %s%n",
            Arrays.toString(sdesPlaintext),
            Arrays.toString(sdesCiphertext),
            Arrays.toString(sdesDecryptedPlaintext)));
    }
}
