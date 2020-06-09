package ru.job4j.util;

public class LexicalOrder {

    public static int compare(String firstWord, String secondWord) {
        int size = Math.min(firstWord.length(), secondWord.length());
        for (int i = 0; i < size; i++) {
            int result = Character.compare(firstWord.charAt(i), secondWord.charAt(i));
            if (result != 0) {
                return result;
            }
        }
        return Integer.compare(firstWord.length(), secondWord.length());
    }
}
