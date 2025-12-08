package com.example.security.rule;

import com.example.security.service.DictionaryService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class DictionaryRuleTest {

    @Test
    void exact_match_detected() {
        DictionaryService ds = new DictionaryService();
        ds.addWord("letmein");
        DictionaryRule r = new DictionaryRule(ds);
        RuleResult res = r.evaluate("letmein");
        assertThat(res.getSeverity()).isEqualTo(RuleResult.Severity.CRITICAL);
    }

    @Test
    void leet_normalized_detected() {
        DictionaryService ds = new DictionaryService();
        ds.addWord("password");
        DictionaryRule r = new DictionaryRule(ds);
        RuleResult res = r.evaluate("p@ssw0rd");
        assertThat(res.getSeverity()).isEqualTo(RuleResult.Severity.CRITICAL);
    }

    @Test
    void fuzzy_detected() {
        DictionaryService ds = new DictionaryService();
        ds.addWord("welcome");
        DictionaryRule r = new DictionaryRule(ds);
        RuleResult res = r.evaluate("welc0me");
        // could be leet-normalized; still expect critical or warn
        assertThat(res.getScore()).isLessThanOrEqualTo(0);
    }
}
