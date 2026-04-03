package ru.javarush.morgunov.cryptoanalizer;

public class BruteForce {

    private final Cipher cipher = new Cipher();
    private final int alphabetSize = Constants.ALPHABET.length;

    public String bruteForceDecrypt(String encryptedText) {
        String bestResult = null;
        int maxScore = -1;
        for (int key = 0; key < alphabetSize; key++) {
            String candidate = cipher.decrypt(encryptedText, key);
            int score = calculateReadability(candidate);
            if (score > maxScore) {
                maxScore = score;
                bestResult = candidate;
            }
        }
        return bestResult;
    }

    private int calculateReadability(String text) {
        int score = 0;
        for (char c : text.toCharArray()) {
            if (Constants.ALPHABET_LIST.contains(c)) {
                score++;
            }
        }
        return score;
    }
}