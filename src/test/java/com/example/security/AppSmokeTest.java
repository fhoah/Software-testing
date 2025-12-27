package com.example.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AppSmokeTest {

    @Test
    void classesShouldBeLoadable() {
        assertDoesNotThrow(() -> Class.forName("com.example.security.App"));
        assertDoesNotThrow(() -> Class.forName("com.example.security.service.PasswordAnalyzerService"));
        assertDoesNotThrow(() -> Class.forName("com.example.security.rule.LengthRule"));
    }
}

