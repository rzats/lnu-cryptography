package org.rzats.lnu.cryptography.ciphers;

import org.rzats.lnu.cryptography.common.ArrayUtilities;

import java.util.Arrays;

/**
 * A simplified version of the Data Encryption Standard (DES) algorithm.
 * <p>
 * Works with bit arrays, though uses the Cipher interface for consistency.
 */
public class SimplifiedDESCipher implements Cipher {
    private static final int INPUT_LENGTH = 8;
    private static final int KEY_LENGTH = 10;

    private static final int[] P10 = {3, 5, 2, 7, 4, 10, 1, 9, 8, 6};
    private static final int[] P8 = {6, 3, 7, 4, 8, 5, 10, 9};
    private static final int[] IP = {2, 6, 3, 1, 4, 8, 5, 7};
    private static final int[] EP = {4, 1, 2, 3, 2, 3, 4, 1};
    private static final int[] P4 = {2, 4, 3, 1};
    private static final int[] IP_REVERSE = {4, 1, 3, 5, 7, 2, 8, 6};

    private static final String[][] S0 = {
        {"01", "00", "11", "10"},
        {"11", "10", "01", "00"},
        {"00", "10", "01", "11"},
        {"11", "01", "11", "10"}
    };
    private static final String[][] S1 = {
        {"00", "01", "10", "11"},
        {"10", "00", "01", "11"},
        {"11", "00", "01", "00"},
        {"10", "01", "00", "11"}
    };

    /**
     * The 8-bit K1 subkey.
     */
    private int[] K1;
    /**
     * The 8-bit K2 subkey.
     */
    private int[] K2;

    public SimplifiedDESCipher(int[] key) {
        int[][] subkeys = generateSubkeys(key);
        this.K1 = subkeys[0];
        this.K2 = subkeys[1];
    }

    @Override
    public int[] encrypt(int[] plaintext) {
        return transform(plaintext, true);
    }

    @Override
    public int[] decrypt(int[] ciphertext) {
        return transform(ciphertext, false);
    }

    /**
     * Performs the SDES algorithm on a given input array.
     *
     * @param input   The input array.
     * @param encrypt Whether the array is a plaintext (and should be encrypted)
     *                as opposed to a ciphertext (should be decrypted).
     * @return The transformed array.
     */
    private int[] transform(int[] input, boolean encrypt) {
        if (input.length != INPUT_LENGTH) {
            throw new IllegalArgumentException("SDES requires 10-bit plaintext/ciphertext");
        }
        if (!ArrayUtilities.isBitArray(input)) {
            throw new IllegalArgumentException("The input must only contain 0/1 values");
        }

        // Apply initial IP permutation to the input.
        int[] buffer = ArrayUtilities.permute(input, IP);

        // Separate the resulting array into two 4-bit subarrays (and store them).
        int[] leftArray = new int[INPUT_LENGTH / 2];
        System.arraycopy(buffer, 0, leftArray, 0, INPUT_LENGTH / 2);
        int[] leftArrayCopy = Arrays.copyOf(leftArray, INPUT_LENGTH / 2);

        int[] rightArray = new int[INPUT_LENGTH / 2];
        System.arraycopy(buffer, INPUT_LENGTH / 2, rightArray, 0, INPUT_LENGTH - 4);
        int[] rightArrayCopy = Arrays.copyOf(rightArray, INPUT_LENGTH / 2);

        // Apply expansion/permutation to the right array and perform binary XOR (with K1 if encrypting, K2 if decrypting)
        buffer = ArrayUtilities.permute(rightArray, EP);
        buffer = ArrayUtilities.xor(buffer, encrypt ? K1 : K2);

        leftArray = new int[INPUT_LENGTH / 2];
        System.arraycopy(buffer, 0, leftArray, 0, INPUT_LENGTH / 2);
        rightArray = new int[INPUT_LENGTH / 2];
        System.arraycopy(buffer, INPUT_LENGTH / 2, rightArray, 0, INPUT_LENGTH - 4);

        // Determine a row and column number from above XOR result.
        String row = leftArray[0] + "" + leftArray[3];
        int rowNumber = getNumber(row);
        String column = leftArray[1] + "" + leftArray[2];
        int columnNumber = getNumber(column);

        // Store the two bits located at the given row&column in S0.
        String lArraySub0 = S0[rowNumber][columnNumber];

        // Repeat for S1.
        row = rightArray[0] + "" + rightArray[3];
        rowNumber = getNumber(row);
        column = rightArray[1] + "" + rightArray[2];
        columnNumber = getNumber(column);

        String rArraySub1 = S1[rowNumber][columnNumber];

        // Apply the four bits to the buffer.
        buffer = new int[INPUT_LENGTH / 2];
        buffer[0] = Integer.parseInt(lArraySub0.charAt(0) + "");
        buffer[1] = Integer.parseInt(lArraySub0.charAt(1) + "");
        buffer[2] = Integer.parseInt(rArraySub1.charAt(0) + "");
        buffer[3] = Integer.parseInt(rArraySub1.charAt(1) + "");

        // Apply P4 permutation to above result and combine with the left 4 bits of the first result.
        buffer = ArrayUtilities.permute(buffer, P4);
        buffer = ArrayUtilities.xor(leftArrayCopy, buffer);

        // Encryption round 2: repeat the above procedure to develop the last 4 replacement bits.
        int[] round2buffer = new int[INPUT_LENGTH];
        System.arraycopy(rightArrayCopy, 0, round2buffer, 0, INPUT_LENGTH / 2);
        System.arraycopy(buffer, 0, round2buffer, 4, INPUT_LENGTH / 2);
        rightArray = new int[INPUT_LENGTH / 2];
        System.arraycopy(round2buffer, INPUT_LENGTH / 2, rightArray, 0, INPUT_LENGTH - 4);

        buffer = ArrayUtilities.permute(rightArray, EP);
        buffer = ArrayUtilities.xor(buffer, encrypt ? K2 : K1);

        leftArray = new int[INPUT_LENGTH / 2];
        System.arraycopy(buffer, 0, leftArray, 0, INPUT_LENGTH / 2);
        rightArray = new int[INPUT_LENGTH / 2];
        System.arraycopy(buffer, INPUT_LENGTH / 2, rightArray, 0, INPUT_LENGTH - 4);

        row = leftArray[0] + "" + leftArray[3];
        rowNumber = getNumber(row);
        column = leftArray[1] + "" + leftArray[2];
        columnNumber = getNumber(column);

        lArraySub0 = S0[rowNumber][columnNumber];

        row = rightArray[0] + "" + rightArray[3];
        rowNumber = getNumber(row);
        column = rightArray[1] + "" + rightArray[2];
        columnNumber = getNumber(column);

        rArraySub1 = S1[rowNumber][columnNumber];

        buffer = new int[INPUT_LENGTH / 2];
        buffer[0] = Integer.parseInt(lArraySub0.charAt(0) + "");
        buffer[1] = Integer.parseInt(lArraySub0.charAt(1) + "");
        buffer[2] = Integer.parseInt(rArraySub1.charAt(0) + "");
        buffer[3] = Integer.parseInt(rArraySub1.charAt(1) + "");

        buffer = ArrayUtilities.permute(buffer, P4);

        int[] swapBufferLArray = new int[INPUT_LENGTH / 2];
        System.arraycopy(round2buffer, 0, swapBufferLArray, 0, INPUT_LENGTH / 2);

        buffer = ArrayUtilities.xor(swapBufferLArray, buffer);

        int[] swapBufferRArray = new int[INPUT_LENGTH / 2];
        System.arraycopy(round2buffer, INPUT_LENGTH / 2, swapBufferRArray, 0, INPUT_LENGTH - 4);

        int[] round2Array = new int[INPUT_LENGTH];
        System.arraycopy(buffer, 0, round2Array, 0, INPUT_LENGTH / 2);
        System.arraycopy(swapBufferRArray, 0, round2Array, INPUT_LENGTH / 2, INPUT_LENGTH - 4);

        // Apply reverse of initial permutation IP to the final result.
        buffer = ArrayUtilities.permute(round2Array, IP_REVERSE);
        return buffer;
    }

    /**
     * Generates the K1/K2 subkeys from a given key.
     *
     * @param key The 10-bit key.
     * @return An array of K1/K2 subkeys.
     */
    private int[][] generateSubkeys(int[] key) {
        if (key.length != KEY_LENGTH) {
            throw new IllegalArgumentException("SDES requires 8-bit keys");
        }
        if (!ArrayUtilities.isBitArray(key)) {
            throw new IllegalArgumentException("The key must only contain 0/1 values");
        }

        int[][] result = new int[2][];

        // Apply permutation P10 to the initial key.
        int[] buffer = ArrayUtilities.permute(key, P10);

        // Separate the key into two halves.
        int[] leftArray = new int[KEY_LENGTH / 2];
        System.arraycopy(buffer, 0, leftArray, 0, KEY_LENGTH / 2);
        int[] rightArray = new int[KEY_LENGTH / 2];
        System.arraycopy(buffer, KEY_LENGTH / 2, rightArray, 0, KEY_LENGTH - 5);

        // Left-shift the halves by 1 separately.
        leftArray = ArrayUtilities.leftShift(leftArray, 1);
        rightArray = ArrayUtilities.leftShift(rightArray, 1);

        // Concatenate them.
        System.arraycopy(leftArray, 0, buffer, 0, KEY_LENGTH / 2);
        System.arraycopy(rightArray, 0, buffer, KEY_LENGTH / 2, KEY_LENGTH - 5);

        // Apply P8 and store the result as the first subkey.
        buffer = ArrayUtilities.permute(buffer, P8);
        result[0] = Arrays.copyOf(buffer, KEY_LENGTH);

        // Repeat the above procedure, except left-shifting by 2.
        leftArray = ArrayUtilities.leftShift(leftArray, 2);
        rightArray = ArrayUtilities.leftShift(rightArray, 2);

        buffer = new int[KEY_LENGTH];
        System.arraycopy(leftArray, 0, buffer, 0, KEY_LENGTH / 2);
        System.arraycopy(rightArray, 0, buffer, KEY_LENGTH / 2, KEY_LENGTH - 5);

        buffer = ArrayUtilities.permute(buffer, P8);
        result[1] = Arrays.copyOf(buffer, KEY_LENGTH);
        return result;
    }

    private int getNumber(String n) {
        if (n.equals("00")) return 0;
        if (n.equals("01")) return 1;
        if (n.equals("10")) return 2;
        if (n.equals("11")) return 3;
        return -1;
    }
}
