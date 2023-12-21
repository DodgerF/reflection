package com.example.reflection;

public class CharDecoder {

    public static char getChar(String input) {
        if (!input.matches("\\p{XDigit}+")) {
            return input.charAt(0);
        }
        int codePoint = Integer.parseInt(input, 16);
        return  (char) codePoint;
    }
}
