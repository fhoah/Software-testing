package com.example.security.service;

import com.example.security.model.AnalysisResult;
import com.example.security.model.StrengthLevel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;

public class HistoryServiceTest {

    private AnalysisResult make(int score) {
        return new AnalysisResult("pw", score, 1.0, new ArrayList<com.example.security.rule.RuleResult>(), StrengthLevel.WEAK);
    }

    @Test
    void average_and_stddev_basic() {
        HistoryService h = new HistoryService(10);
        h.add(make(10));
        h.add(make(20));
        h.add(make(30));
        assertThat(h.averageScore()).isEqualTo((10 + 20 + 30) / 3.0);
        assertThat(h.stddevScore()).isGreaterThan(0.0);
    }

    @Test
    void trend_improving_and_declining() {
        HistoryService h = new HistoryService(10);
        h.add(make(10)); h.add(make(12)); h.add(make(14)); h.add(make(18));
        assertThat(h.trend()).isEqualTo(com.example.security.model.TrendStatus.IMPROVING);

        HistoryService h2 = new HistoryService(10);
        h2.add(make(30)); h2.add(make(20)); h2.add(make(10));
        assertThat(h2.trend()).isEqualTo(com.example.security.model.TrendStatus.DECLINING);
    }
}
