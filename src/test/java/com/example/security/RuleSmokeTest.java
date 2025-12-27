package com.example.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class RuleSmokeTest {

    @Test
    void rulesAndUtilsShouldBeLoadable() {
        assertDoesNotThrow(() -> Class.forName("com.example.security.rule.LengthRule"));
        assertDoesNotThrow(() -> Class.forName("com.example.security.rule.EntropyRule"));
        assertDoesNotThrow(() -> Class.forName("com.example.security.rule.DictionaryRule"));
        assertDoesNotThrow(() -> Class.forName("com.example.security.rule.LeetspeakRule"));
        assertDoesNotThrow(() -> Class.forName("com.example.security.rule.LevenshteinDistance"));
        assertDoesNotThrow(() -> Class.forName("com.example.security.util.MathUtil"));
    }
}

