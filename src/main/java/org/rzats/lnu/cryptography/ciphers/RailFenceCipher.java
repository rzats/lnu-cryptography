package org.rzats.lnu.cryptography.ciphers;

/**
 * A simple transposition cipher.
 * <p>
 * Transposes the message onto an imaginary fence then reads it off its' "rails".
 */
public class RailFenceCipher implements Cipher {
    /**
     * The amount of the fence's "rails" - acts as a key
     */
    private int fenceHeight;

    public RailFenceCipher(int fenceHeight) {
        this.fenceHeight = fenceHeight;
    }

    @Override
    public int[] encrypt(int[] plaintext) {
        int[][] fence = new int[fenceHeight][plaintext.length];

        // Fill the "fence" array
        int rail = 0;
        boolean movingUp = false;
        for (int i = 0; i < plaintext.length; i++) {
            fence[rail][i] = plaintext[i];

            // Move up when the bottom rail is reached
            if (movingUp) {
                if (rail == 0) {
                    rail = 1;
                    movingUp = false;
                } else {
                    rail--;
                }
            } else {
                if (rail == fence.length - 1) {
                    rail = fence.length - 2;
                    movingUp = true;
                } else {
                    rail++;
                }
            }
        }

        // Read the fence off rows to get the ciphertext
        int[] ciphertext = new int[plaintext.length];
        int k = 0;
        for (int i = 0; i < fence.length; i++) {
            for (int j = 0; j < plaintext.length; j++) {
                if (fence[i][j] > 0) {
                    ciphertext[k] = fence[i][j];
                    k++;
                }
            }
        }

        return ciphertext;
    }

    @Override
    public int[] decrypt(int[] ciphertext) {
        int[][] fence = new int[fenceHeight][ciphertext.length];

        //Fill the "fence" array
        int targetRow = 0;
        int index = 0;
        do {
            int rail = 0;
            boolean movingUp = false;

            for (int i = 0; i < ciphertext.length; i++) {
                if (rail == targetRow) {
                    fence[rail][i] = ciphertext[index];
                    index++;
                }

                // Move up when the bottom rail is reached
                if (movingUp) {
                    if (rail == 0) {
                        rail = 1;
                        movingUp = false;
                    } else {
                        rail--;
                    }
                } else {
                    if (rail == fence.length - 1) {
                        rail = fence.length - 2;
                        movingUp = true;
                    } else {
                        rail++;
                    }
                }
            }

            targetRow++;
        } while (targetRow < fence.length);

        // Read the reconstructed fence (zigzagging) to get the plaintext
        int[] plaintext = new int[ciphertext.length];
        int rail = 0;
        boolean movingUp = false;
        for (int col = 0; col < fence[rail].length; col++) {
            plaintext[col] = fence[rail][col];

            // Move up when the bottom rail is reached
            if (movingUp) {
                if (rail == 0) {
                    rail = 1;
                    movingUp = false;
                } else {
                    rail--;
                }
            } else {
                if (rail == fence.length - 1) {
                    rail = fence.length - 2;
                    movingUp = true;
                } else {
                    rail++;
                }
            }
        }

        return plaintext;
    }
}
