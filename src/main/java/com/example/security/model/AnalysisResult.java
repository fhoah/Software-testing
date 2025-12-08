package com.example.security.model;

import com.example.security.rule.RuleResult;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnalysisResult {
    private final String password;
    private final int score;
    private final double entropy;
    private final List<RuleResult> ruleResults;
    private final StrengthLevel strengthLevel;
    private final Instant timestamp;

    public AnalysisResult(String password, int score, double entropy, List<RuleResult> ruleResults, StrengthLevel strengthLevel) {
        this.password = password;
        this.score = score;
        this.entropy = entropy;
        this.ruleResults = ruleResults == null ? Collections.<RuleResult>emptyList() : new ArrayList<RuleResult>(ruleResults);
        this.strengthLevel = strengthLevel;
        this.timestamp = Instant.now();
    }

    public String getPassword() {
        return password;
    }

    public int getScore() {
        return score;
    }

    public double getEntropy() {
        return entropy;
    }

    public List<RuleResult> getRuleResults() {
        return new ArrayList<RuleResult>(ruleResults);
    }

    public StrengthLevel getStrengthLevel() {
        return strengthLevel;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("AnalysisResult(password=%s, score=%d, entropy=%.3f, strength=%s)", password, score, entropy, strengthLevel);
    }
}
