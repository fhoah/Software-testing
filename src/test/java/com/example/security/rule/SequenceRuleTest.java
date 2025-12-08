package com.example.security.rule;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class SequenceRuleTest {

    @Test
    void no_sequence() {
        SequenceRule r = new SequenceRule();
        RuleResult res = r.evaluate("x9Z!pRt");
        assertThat(res.getScore()).isEqualTo(0);
    }

    @Test
    void alpha_sequence_detected() {
        SequenceRule r = new SequenceRule();
        RuleResult res = r.evaluate("xyzabc");
        assertThat(res.getScore()).isLessThan(0);
    }

    @Test
    void numeric_sequence_detected() {
        SequenceRule r = new SequenceRule();
        RuleResult res = r.evaluate("pass12345word");
        assertThat(res.getScore()).isLessThan(0);
    }

    @Test
    void keyboard_sequence_detected() {
        SequenceRule r = new SequenceRule();
        RuleResult res = r.evaluate("qwerty12");
        assertThat(res.getScore()).isLessThan(0);
    }
}
