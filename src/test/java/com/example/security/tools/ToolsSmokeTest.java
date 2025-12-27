package com.example.security.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ToolsSmokeTest {

    @Test
    void toolsShouldBeLoadable() {
        assertDoesNotThrow(() -> Class.forName("com.example.security.tools.SvgToPngConverter"));
    }
}
