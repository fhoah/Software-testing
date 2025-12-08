package com.example.security.service;

import com.example.security.model.AnalysisResult;
import com.example.security.model.StrengthLevel;
import com.example.security.rule.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PasswordAnalyzerService {

    private final List<Rule> rules;
    private final EntropyRule entropyRule;

    public PasswordAnalyzerService() {
        this.entropyRule = new EntropyRule();
        // order: dictionary/leet checks early, then length/charset/entropy/sequence/repetition
        this.rules = Arrays.asList(
                new LeetspeakRule(),
                new DictionaryRule(),
                new LengthRule(),
                new CharsetRule(),
                entropyRule,
                new SequenceRule(),
                new RepetitionRule()
        );
    }

    public AnalysisResult analyze(String password) {
        List<RuleResult> results = new ArrayList<RuleResult>();
        int totalScore = 0;
        double entropy = 0.0;

        for (Rule r : rules) {
            RuleResult res = r.evaluate(password);
            results.add(res);
            totalScore += res.getScore();
            if (r instanceof EntropyRule) {
                // try to extract entropy numeric value from message if present
                try {
                    String msg = res.getMessage();
                    if (msg != null && msg.startsWith("Entropy=")) {
                        String[] parts = msg.split("=");
                        if (parts.length >= 2) {
                            String num = parts[1].split(" ")[0];
                            entropy = Double.parseDouble(num);
                        }
                    }
                } catch (Exception ignored) {}
            }
        }

        StrengthLevel level = mapStrength(totalScore);
        return new AnalysisResult(password, totalScore, entropy, results, level);
    }

    private StrengthLevel mapStrength(int score) {
        if (score < 40) return StrengthLevel.WEAK;
        if (score < 60) return StrengthLevel.MEDIUM;
        if (score < 80) return StrengthLevel.STRONG;
        return StrengthLevel.VERY_STRONG;
    }

    public List<Rule> getRules() {
        return new ArrayList<Rule>(rules);
    }
}
