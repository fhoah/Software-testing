package com.example.security.rule;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class RepetitionRuleTest {

    @Test
    void no_long_repetition() {
        RepetitionRule r = new RepetitionRule();
        RuleResult res = r.evaluate("abcabc123");
        assertThat(res.getScore()).isEqualTo(0);
    }

    @Test
    void detect_repetition_run() {
        RepetitionRule r = new RepetitionRule();
        RuleResult res = r.evaluate("aaabbbbcc");
        assertThat(res.getScore()).isLessThan(0);
    }

    @Test
    void detect_single_char_long_run() {
        RepetitionRule r = new RepetitionRule();
        RuleResult res = r.evaluate("1111111");
        assertThat(res.getScore()).isLessThan(0);
    }
}
