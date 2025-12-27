package com.example.security.tools;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

public class SvgToPngConverterTest {

    @Test
    void classShouldBeLoadableAndHaveExpectedMethods() {
        assertDoesNotThrow(() -> {
            Class<?> cls = Class.forName("com.example.security.tools.SvgToPngConverter");
            // main method exists
            Method main = cls.getMethod("main", String[].class);
            assertTrue(Modifier.isPublic(main.getModifiers()));
            assertTrue(Modifier.isStatic(main.getModifiers()));

            // convert method exists and is public static
            Method convert = cls.getMethod("convert", File.class, File.class);
            assertTrue(Modifier.isPublic(convert.getModifiers()));
            assertTrue(Modifier.isStatic(convert.getModifiers()));

            // do not invoke main or convert to avoid System.exit or heavy I/O/transcoding
        });
    }

    @Test
    void convertMethodSignatureShouldThrowExceptionType() throws Exception {
        Class<?> cls = Class.forName("com.example.security.tools.SvgToPngConverter");
        Method convert = cls.getMethod("convert", File.class, File.class);
        // ensure declared exceptions include Exception (broad)
        Class<?>[] ex = convert.getExceptionTypes();
        boolean declaresException = false;
        for (Class<?> e : ex) {
            if (e.equals(Exception.class)) declaresException = true;
        }
        assertTrue(declaresException, "convert should declare throwing Exception");
    }
}

