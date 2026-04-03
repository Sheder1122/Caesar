package ru.javarush.morgunov.cryptoanalizer;

import java.util.HashMap;
import java.util.Map;

public class StatisticalAnalyzer {

    private final Cipher cipher = new Cipher();
    private final int alphabetSize = Constants.ALPHABET.length;

    public int findMostLikelyShift(String encryptedText, String representativeText) {
        Map<Character, Integer> repFreq = getFrequency(representativeText);
        char mostFrequentRep = getMostFrequentChar(repFreq);

        Map<Character, Integer> encFreq = getFrequency(encryptedText);
        char mostFrequentEnc = getMostFrequentChar(encFreq);

        int repIndex = Constants.ALPHABET_LIST.indexOf(mostFrequentRep);
        int encIndex = Constants.ALPHABET_LIST.indexOf(mostFrequentEnc);
        if (repIndex == -1 || encIndex == -1) return 0;
        return (encIndex - repIndex + alphabetSize) % alphabetSize;
    }

    private Map<Character, Integer> getFrequency(String text) {
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : text.toCharArray()) {
            if (Constants.ALPHABET_LIST.contains(c)) {
                freq.put(c, freq.getOrDefault(c, 0) + 1);
            }
        }
        return freq;
    }

    private char getMostFrequentChar(Map<Character, Integer> freq) {
        char mostFreq = ' ';
        int maxCount = 0;
        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFreq = entry.getKey();
            }
        }
        return mostFreq;
    }
}