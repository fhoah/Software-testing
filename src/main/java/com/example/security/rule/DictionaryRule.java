package com.example.security.rule;

import com.example.security.service.DictionaryService;

public class DictionaryRule implements Rule {

    private final DictionaryService dictionaryService;

    public DictionaryRule() {
        this.dictionaryService = new DictionaryService();
    }

    // allow injection for tests
    public DictionaryRule(DictionaryService service) {
        this.dictionaryService = service;
    }

    @Override
    public RuleResult evaluate(String password) {
        if (password == null || password.length() == 0) return RuleResult.ok(getName(), 0, "No dictionary match (empty)");
        String pw = password.toLowerCase();

        if (dictionaryService.containsExact(pw)) {
            return RuleResult.critical(getName(), -40, "Exact dictionary match");
        }
        if (dictionaryService.containsLeetNormalized(password)) {
            return RuleResult.critical(getName(), -30, "Leetspeak normalized match");
        }
        // fuzzy match threshold: normalized distance <= 0.25
        if (dictionaryService.fuzzyMatch(pw, 0.25)) {
            return RuleResult.warn(getName(), -20, "Fuzzy dictionary match");
        }
        return RuleResult.ok(getName(), 0, "No dictionary match");
    }

    @Override
    public String getName() {
        return "DictionaryRule";
    }
}
