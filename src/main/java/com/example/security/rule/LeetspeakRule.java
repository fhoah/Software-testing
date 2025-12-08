package com.example.security.rule;

import com.example.security.service.DictionaryService;

public class LeetspeakRule implements Rule {

    private final DictionaryService dictionaryService;

    public LeetspeakRule() {
        this.dictionaryService = new DictionaryService();
    }

    public LeetspeakRule(DictionaryService service) {
        this.dictionaryService = service;
    }

    @Override
    public RuleResult evaluate(String password) {
        if (password == null || password.length() == 0) return RuleResult.ok(getName(), 0, "No leetspeak issues (empty)");
        String norm = normalize(password);
        if (dictionaryService.containsExact(norm)) {
            return RuleResult.critical(getName(), -30, "Leet-normalized exact dictionary match");
        }
        // if normalized is similar fuzzy
        if (dictionaryService.fuzzyMatch(norm, 0.2)) {
            return RuleResult.warn(getName(), -15, "Leet-normalized fuzzy match");
        }
        return RuleResult.ok(getName(), 0, "No leetspeak dictionary match");
    }

    @Override
    public String getName() {
        return "LeetspeakRule";
    }

    private String normalize(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<s.length();i++) {
            char c = s.charAt(i);
            switch (c) {
                case '@': sb.append('a'); break;
                case '$': sb.append('s'); break;
                case '0': sb.append('o'); break;
                case '1': sb.append('l'); break;
                case '3': sb.append('e'); break;
                default: sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }
}
