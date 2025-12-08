package com.example.security.rule;

public class RuleResult {
    public enum Severity { INFO, WARNING, CRITICAL }

    private final String ruleName;
    private final int score; // positive score increases strength, negative decreases
    private final String message;
    private final Severity severity;

    public RuleResult(String ruleName, int score, String message, Severity severity) {
        this.ruleName = ruleName;
        this.score = score;
        this.message = message;
        this.severity = severity;
    }

    public static RuleResult ok(String ruleName, int score, String message) {
        return new RuleResult(ruleName, score, message, Severity.INFO);
    }

    public static RuleResult warn(String ruleName, int score, String message) {
        return new RuleResult(ruleName, score, message, Severity.WARNING);
    }

    public static RuleResult critical(String ruleName, int score, String message) {
        return new RuleResult(ruleName, score, message, Severity.CRITICAL);
    }

    public String getRuleName() {
        return ruleName;
    }

    public int getScore() {
        return score;
    }

    public String getMessage() {
        return message;
    }

    public Severity getSeverity() {
        return severity;
    }

    @Override
    public String toString() {
        return String.format("%s: %s (score=%d, severity=%s)", ruleName, message, score, severity);
    }
}
