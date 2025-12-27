package com.example.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ServiceSmokeTest {

    @Test
    void servicesShouldBeLoadable() {
        assertDoesNotThrow(() -> Class.forName("com.example.security.service.PasswordGeneratorService"));
        assertDoesNotThrow(() -> Class.forName("com.example.security.service.HistoryService"));
        assertDoesNotThrow(() -> Class.forName("com.example.security.service.BatchProcessingService"));
        assertDoesNotThrow(() -> Class.forName("com.example.security.service.DictionaryService"));
    }
}

