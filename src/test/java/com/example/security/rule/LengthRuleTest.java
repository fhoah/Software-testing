package com.example.security.rule;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class LengthRuleTest {

    @Test
    void empty_password_critical() {
        LengthRule r = new LengthRule();
        RuleResult res = r.evaluate("");
        assertThat(res.getSeverity()).isEqualTo(RuleResult.Severity.CRITICAL);
    }

    @Test
    void very_short_penalty() {
        LengthRule r = new LengthRule();
        RuleResult res = r.evaluate("abc");
        assertThat(res.getScore()).isLessThan(0);
    }

    @Test
    void long_bonus() {
        LengthRule r = new LengthRule();
        RuleResult res = r.evaluate("averyverylongpasswordindeed");
        assertThat(res.getScore()).isGreaterThan(0);
    }
}

