package com.example.security.rule;

public interface Rule {
    /**
     * Evaluate the provided password and return a RuleResult.
     */
    RuleResult evaluate(String password);

    /**
     * Return the human name of the rule (implementations should return simple class name).
     */
    String getName();
}
