package com.example.security;

import com.example.security.model.PasswordConfig;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AppInvokeSmokeTest {

    @Test
    void invokeAppPrivateMethodsShouldNotThrow() {
        assertDoesNotThrow(() -> {
            Class<?> app = Class.forName("com.example.security.App");

            Method buildDefault = app.getDeclaredMethod("buildDefaultCustomPolicy");
            buildDefault.setAccessible(true);
            PasswordConfig cfg = (PasswordConfig) buildDefault.invoke(null);

            Method printFive = app.getDeclaredMethod("printFiveFunctions", PasswordConfig.class);
            printFive.setAccessible(true);
            printFive.invoke(null, cfg);

            Method display = app.getDeclaredMethod("displayPolicy", PasswordConfig.class);
            display.setAccessible(true);
            display.invoke(null, cfg);

            Method analyze = app.getDeclaredMethod("analyzeAndPrint", String.class, PasswordConfig.class);
            analyze.setAccessible(true);
            analyze.invoke(null, "Abc123!xyz", cfg);

            Method containsUpper = app.getDeclaredMethod("containsUpper", String.class);
            containsUpper.setAccessible(true);
            containsUpper.invoke(null, "Abc");

            Method containsLower = app.getDeclaredMethod("containsLower", String.class);
            containsLower.setAccessible(true);
            containsLower.invoke(null, "aBC");

            Method containsDigit = app.getDeclaredMethod("containsDigit", String.class);
            containsDigit.setAccessible(true);
            containsDigit.invoke(null, "12");

            Method containsSymbol = app.getDeclaredMethod("containsSymbol", String.class);
            containsSymbol.setAccessible(true);
            containsSymbol.invoke(null, "!@");
        });
    }
}

