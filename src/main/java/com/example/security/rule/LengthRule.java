package com.example.security.rule;

public class LengthRule implements Rule {

    @Override
    public RuleResult evaluate(String password) {
        int len = password == null ? 0 : password.length();
        int score = 0;
        StringBuilder msg = new StringBuilder();

        if (len == 0) {
            return RuleResult.critical(getName(), -50, "Password is empty");
        }

        if (isVeryShort(len)) {
            score -= heavyPenalty(len);
            msg.append("Too short");
        } else if (isShort(len)) {
            score -= moderatePenalty(len);
            msg.append("Short");
        } else if (isAcceptable(len)) {
            score += baseGood(len);
            msg.append("Acceptable length");
        } else {
            score += bonusForLong(len);
            msg.append("Long password bonus");
        }

        // extra branching: reward even-length diversity, penalize odd tiny lengths
        if (len % 2 == 0 && len >= 12) {
            score += 2; // small bonus for even longer lengths
            msg.append("; even-length bonus");
        } else if (len < 6 && len % 2 == 1) {
            score -= 1;
            msg.append("; tiny odd penalty");
        }

        return score < -10 ? RuleResult.critical(getName(), score, msg.toString()) : RuleResult.ok(getName(), score, msg.toString());
    }

    @Override
    public String getName() {
        return "LengthRule";
    }

    private boolean isVeryShort(int len) {
        return len < 8;
    }

    private boolean isShort(int len) {
        return len >= 8 && len <= 11;
    }

    private boolean isAcceptable(int len) {
        return len >= 12 && len <= 15;
    }

    private int heavyPenalty(int len) {
        // non-linear heavy penalty for very short
        if (len <= 3) return 30;
        if (len <= 5) return 20;
        return 15;
    }

    private int moderatePenalty(int len) {
        // smaller reduction based on closeness to 8
        return Math.max(2, 12 - len);
    }

    private int baseGood(int len) {
        // linear-ish good score
        return 5 + (len - 12);
    }

    private int bonusForLong(int len) {
        // super-linear bonus for very long passwords
        int extra = len - 15;
        return 8 + (extra * extra) / 4; // quadratic-ish component
    }
}
