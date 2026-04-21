package ru.javarush.morgunov.cryptoanalyzer;

import java.util.HashMap;
import java.util.Map;

public class BruteForce {
    private final Cipher cipher = new Cipher();
    private final int alphabetSize = Constants.ALPHABET.length;

    private static final double[] RUSSIAN_FREQUENCIES = {
            8.01, 1.59, 4.54, 1.70, 2.98, 8.45, 0.04, 0.94, 1.65, 7.35,
            1.21, 3.49, 4.40, 3.21, 6.70, 10.97, 2.81, 4.73, 5.47, 6.26,
            2.62, 0.26, 0.97, 0.48, 1.44, 0.73, 0.36, 0.04, 1.90, 0.17,
            3.20, 0.64, 1.10
    };

    public String bruteForceDecrypt(String encryptedText) {
        double bestScore = -1;
        String bestResult = null;
        int bestKey = 0;

        for (int key = 0; key < alphabetSize; key++) {
            String candidate = cipher.decrypt(encryptedText, key);
            double score = calculateFrequencyScore(candidate, RUSSIAN_FREQUENCIES);
            if (score > bestScore) {
                bestScore = score;
                bestResult = candidate;
                bestKey = key;
            }
        }
        System.out.println("Brute force выбрал ключ: " + bestKey + " (оценка: " + bestScore + ")");
        return bestResult;
    }

    public String bruteForceWithSample(String encryptedText, String sampleText) {
        double[] sampleFreq = extractFrequencies(sampleText);
        double bestScore = -1;
        String bestResult = null;
        int bestKey = 0;

        for (int key = 0; key < alphabetSize; key++) {
            String candidate = cipher.decrypt(encryptedText, key);
            double score = calculateFrequencyScore(candidate, sampleFreq);
            if (score > bestScore) {
                bestScore = score;
                bestResult = candidate;
                bestKey = key;
            }
        }
        System.out.println("Brute force (с образцом) выбрал ключ: " + bestKey + " (оценка: " + bestScore + ")");
        return bestResult;
    }

    private double[] extractFrequencies(String text) {
        Map<Character, Integer> counts = new HashMap<>();
        int totalLetters = 0;
        for (char c : text.toLowerCase().toCharArray()) {
            int idx = Constants.ALPHABET_LIST.indexOf(c);
            if (idx >= 0 && idx < 33) {
                counts.put(c, counts.getOrDefault(c, 0) + 1);
                totalLetters++;
            }
        }
        double[] freqs = new double[33];
        if (totalLetters == 0) return freqs;
        for (int i = 0; i < 33; i++) {
            char letter = Constants.ALPHABET[i];
            int cnt = counts.getOrDefault(letter, 0);
            freqs[i] = (double) cnt / totalLetters * 100;
        }
        return freqs;
    }

    private double calculateFrequencyScore(String text, double[] targetFreqs) {
        Map<Character, Integer> counts = new HashMap<>();
        int totalLetters = 0;
        for (char c : text.toLowerCase().toCharArray()) {
            int idx = Constants.ALPHABET_LIST.indexOf(c);
            if (idx >= 0 && idx < 33) {
                counts.put(c, counts.getOrDefault(c, 0) + 1);
                totalLetters++;
            }
        }
        if (totalLetters == 0) return 0;

        double totalScore = 0;
        for (int i = 0; i < 33; i++) {
            char letter = Constants.ALPHABET[i];
            int actualCount = counts.getOrDefault(letter, 0);
            double actualFreq = (double) actualCount / totalLetters * 100;
            double expectedFreq = targetFreqs[i];
            double diff = Math.abs(actualFreq - expectedFreq);
            totalScore += 1.0 / (1 + diff);
        }
        return totalScore;
    }
}