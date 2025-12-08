package com.example.security.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGeneratorService {
    private final SecureRandom random = new SecureRandom();
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_+=<>?";

    // simple consonant/vowel sets for pronounceable generation
    private static final char[] CONSONANTS = "bcdfghjklmnpqrstvwxyz".toCharArray();
    private static final char[] VOWELS = "aeiou".toCharArray();

    public String generate(int length, boolean useUpper, boolean useLower, boolean useDigits, boolean useSymbols, boolean pronounceable) {
        if (length <= 0) throw new IllegalArgumentException("length must be > 0");

        List<Character> chars = new ArrayList<Character>();
        List<String> pools = new ArrayList<String>();
        if (useUpper) pools.add(UPPER);
        if (useLower) pools.add(LOWER);
        if (useDigits) pools.add(DIGITS);
        if (useSymbols) pools.add(SYMBOLS);

        if (pools.isEmpty()) {
            // default to lower only
            pools.add(LOWER);
            useLower = true;
        }

        // guarantee at least one from each selected pool
        for (String pool : pools) {
            chars.add(randomCharFrom(pool));
        }

        if (pronounceable) {
            // build pronounceable pattern until reaching length
            while (chars.size() < length) {
                appendPronounceableSyllable(chars);
            }
        } else {
            // fill remaining with random picks across pools
            while (chars.size() < length) {
                String p = pools.get(random.nextInt(pools.size()));
                chars.add(randomCharFrom(p));
            }
        }

        // If we added too many (pronounceable syllable may exceed), trim
        if (chars.size() > length) {
            chars = new ArrayList<Character>(chars.subList(0, length));
        }

        // ensure uppercase/lowercase/digit/symbol presence for pools that were flagged
        enforceCategoryPresence(chars, useUpper, useLower, useDigits, useSymbols);

        // shuffle to avoid predictable placement
        Collections.shuffle(chars, random);

        StringBuilder sb = new StringBuilder();
        for (char c : chars) sb.append(c);
        return sb.toString();
    }

    private void enforceCategoryPresence(List<Character> chars, boolean useUpper, boolean useLower, boolean useDigits, boolean useSymbols) {
        boolean hasUpper=false, hasLower=false, hasDigit=false, hasSymbol=false;
        for (char c : chars) {
            if (Character.isUpperCase(c)) hasUpper=true;
            else if (Character.isLowerCase(c)) hasLower=true;
            else if (Character.isDigit(c)) hasDigit=true;
            else hasSymbol=true;
        }
        // replace random positions if missing
        if (useUpper && !hasUpper) replaceRandom(chars, UPPER.charAt(random.nextInt(UPPER.length())));
        if (useLower && !hasLower) replaceRandom(chars, LOWER.charAt(random.nextInt(LOWER.length())));
        if (useDigits && !hasDigit) replaceRandom(chars, DIGITS.charAt(random.nextInt(DIGITS.length())));
        if (useSymbols && !hasSymbol) replaceRandom(chars, SYMBOLS.charAt(random.nextInt(SYMBOLS.length())));
    }

    private void replaceRandom(List<Character> chars, char newChar) {
        int pos = random.nextInt(chars.size());
        chars.set(pos, newChar);
    }

    private void appendPronounceableSyllable(List<Character> chars) {
        // CVC or VCV patterns randomly
        boolean startWithConsonant = random.nextBoolean();
        if (startWithConsonant) {
            chars.add(randomFromArray(CONSONANTS));
            if (chars.size() < Integer.MAX_VALUE) chars.add(randomFromArray(VOWELS));
            if (random.nextBoolean()) chars.add(randomFromArray(CONSONANTS));
        } else {
            chars.add(randomFromArray(VOWELS));
            if (random.nextBoolean()) chars.add(randomFromArray(CONSONANTS));
            if (chars.size() < Integer.MAX_VALUE) chars.add(randomFromArray(VOWELS));
        }
    }

    private char randomFromArray(char[] arr) {
        return arr[random.nextInt(arr.length)];
    }

    private char randomCharFrom(String pool) {
        return pool.charAt(random.nextInt(pool.length()));
    }
}
