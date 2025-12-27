package com.example.security;

import com.example.security.model.AnalysisResult;
import com.example.security.model.PasswordConfig;
import com.example.security.rule.RuleResult;
import com.example.security.rule.RuleResult.Severity;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ModelSmokeTest {

    @Test
    void modelObjectsShouldBehave() {
        assertDoesNotThrow(() -> {
            PasswordConfig cfg = PasswordConfig.builder().minLength(10).requireSymbols(true).build();
            cfg.toString();
            PasswordConfig cfg2 = PasswordConfig.builder().minLength(10).requireSymbols(true).build();
            cfg.equals(cfg2);
            cfg.hashCode();

            RuleResult r1 = RuleResult.ok("len", 10, "ok");
            RuleResult r2 = RuleResult.warn("dict", -20, "weak");
            r1.toString(); r2.toString(); r1.getSeverity(); r2.getScore();

            AnalysisResult ar = new AnalysisResult("pwd", 50, 35.5, Arrays.asList(r1, r2), com.example.security.model.StrengthLevel.MEDIUM);
            ar.toString(); ar.getPassword(); ar.getRuleResults(); ar.getScore(); ar.getEntropy(); ar.getStrengthLevel(); ar.getTimestamp();
        });
    }
}

