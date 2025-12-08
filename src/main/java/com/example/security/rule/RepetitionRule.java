package com.example.security.rule;

public class RepetitionRule implements Rule {

    @Override
    public RuleResult evaluate(String password) {
        if (password == null || password.length() == 0) return RuleResult.ok(getName(), 0, "No repetition (empty)");
        int maxRun = longestRepetition(password);
        int penalty = 0;
        String msg;
        if (maxRun <= 2) {
            msg = "No long repetition";
        } else {
            penalty = computePenalty(maxRun);
            msg = String.format("Max repetition=%d", maxRun);
        }
        return penalty > 0 ? RuleResult.warn(getName(), -penalty, msg) : RuleResult.ok(getName(), 0, msg);
    }

    @Override
    public String getName() {
        return "RepetitionRule";
    }

    private int longestRepetition(String s) {
        int best = 1; int cur = 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                cur++;
            } else {
                best = Math.max(best, cur);
                cur = 1;
            }
        }
        best = Math.max(best, cur);
        return best;
    }

    private int computePenalty(int run) {
        // progressive penalties: 3->2,4->5,5->9,6->14,7->20
        int base = (run - 2) * (run - 1) / 2;
        return base + run;
    }
}
