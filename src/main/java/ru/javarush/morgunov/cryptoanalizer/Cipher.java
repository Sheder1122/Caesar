package ru.javarush.morgunov.cryptoanalizer;

import java.util.List;

public class Cipher {

    private final List<Character> alphabet = Constants.ALPHABET_LIST;
    private final int alphabetSize = alphabet.size();

    public String encrypt(String text, int shift) {
        shift = shift % alphabetSize;
        return transform(text, shift);
    }

    public String decrypt(String text, int shift) {
        shift = shift % alphabetSize;
        return transform(text, alphabetSize - shift);
    }

    private String transform(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            int index = alphabet.indexOf(c);
            if (index != -1) {
                int newIndex = (index + shift) % alphabetSize;
                result.append(alphabet.get(newIndex));
            } else {
                // символ не из алфавита – оставляем как есть
                result.append(c);
            }
        }
        return result.toString();
    }
}