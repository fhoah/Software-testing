package com.example.security.rule;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class CharsetRuleTest {

    @Test
    void only_lower_penalty() {
        CharsetRule r = new CharsetRule();
        RuleResult res = r.evaluate("passwordonly");
        assertThat(res.getScore()).isLessThan(0);
    }

    @Test
    void mixed_case_symbols_bonus() {
        CharsetRule r = new CharsetRule();
        RuleResult res = r.evaluate("Abc$DefG");
        assertThat(res.getScore()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void digits_only_heavy_penalty() {
        CharsetRule r = new CharsetRule();
        RuleResult res = r.evaluate("12345678");
        assertThat(res.getScore()).isLessThan(0);
    }
}
