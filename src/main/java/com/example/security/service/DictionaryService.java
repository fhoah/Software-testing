package com.example.security.service;

import com.example.security.rule.LevenshteinDistance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DictionaryService {
    private final Set<String> words = new HashSet<>();

    public DictionaryService() {
        // seed with common weak passwords / words
        String[] seed = new String[]{"password", "123456", "qwerty", "letmein", "admin", "welcome", "iloveyou"};
        for (String w : seed) words.add(w);
    }

    public boolean containsExact(String token) {
        if (token == null) return false;
        return words.contains(token.toLowerCase());
    }

    public boolean containsLeetNormalized(String token) {
        if (token == null) return false;
        String norm = normalizeLeet(token);
        return words.contains(norm.toLowerCase());
    }

    public boolean fuzzyMatch(String token, double threshold) {
        if (token == null) return false;
        token = token.toLowerCase();
        for (String w : words) {
            double nd = LevenshteinDistance.normalizedDistance(token, w);
            if (nd <= threshold) return true;
        }
        return false;
    }

    private String normalizeLeet(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            switch (c) {
                case '@':
                    sb.append('a');
                    break;
                case '$':
                    sb.append('s');
                    break;
                case '0':
                    sb.append('o');
                    break;
                case '1':
                    sb.append('i');
                    break;
                case '3':
                    sb.append('e');
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    // expose for tests/expansion
    public Set<String> getWords() {
        return new HashSet<>(words);
    }

    public void addWord(String w) {
        if (w != null) words.add(w.toLowerCase());
    }

    // New API
    public void loadFromFile(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) words.add(line.toLowerCase());
            }
        } finally {
            br.close();
        }
    }

    public boolean isWeak(String token) {
        if (token == null) return false;
        String t = token.toLowerCase();
        if (containsExact(t)) return true;
        if (containsLeetNormalized(t)) return true;
        if (fuzzyMatch(t, 0.25)) return true;
        return false;
    }

    public boolean isSimilar(String token, double threshold) {
        if (token == null) return false;
        token = token.toLowerCase();
        for (String w : words) {
            double nd = LevenshteinDistance.normalizedDistance(token, w);
            if (nd <= threshold) return true;
        }
        return false;
    }

    public String leetNormalize(String token) {
        return normalizeLeet(token).toLowerCase();
    }
}
