package com.example.security.rule;

import com.example.security.service.DictionaryService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class LeetspeakRuleTest {

    @Test
    void leet_normalization_exact_match() {
        DictionaryService ds = new DictionaryService();
        ds.addWord("password");
        LeetspeakRule r = new LeetspeakRule(ds);
        RuleResult res = r.evaluate("p@ssw0rd");
        assertThat(res.getScore()).isLessThan(0);
        assertThat(res.getMessage()).contains("Leet");
    }

    @Test
    void leet_no_match() {
        LeetspeakRule r = new LeetspeakRule();
        RuleResult res = r.evaluate("uniquepw123$");
        assertThat(res.getScore()).isEqualTo(0);
    }
}
