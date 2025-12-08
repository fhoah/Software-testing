package com.example.security.service;

import com.example.security.model.AnalysisResult;
import com.example.security.model.StrengthLevel;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class PasswordAnalyzerServiceTest {

    @Test
    void analyze_weak_password() {
        PasswordAnalyzerService svc = new PasswordAnalyzerService();
        AnalysisResult res = svc.analyze("12345");
        assertThat(res.getStrengthLevel()).isEqualTo(StrengthLevel.WEAK);
    }

    @Test
    void analyze_strong_password() {
        PasswordAnalyzerService svc = new PasswordAnalyzerService();
        AnalysisResult res = svc.analyze("Aq3$9tR8!kL0z");
        assertThat(res.getStrengthLevel()).isNotNull();
        assertThat(res.getRuleResults()).isNotEmpty();
    }

    @Test
    void analyze_returns_rule_results() {
        PasswordAnalyzerService svc = new PasswordAnalyzerService();
        AnalysisResult res = svc.analyze("password");
        assertThat(res.getRuleResults()).isNotEmpty();
    }
}
