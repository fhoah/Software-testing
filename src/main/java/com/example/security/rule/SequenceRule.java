package com.example.security.rule;

import java.util.Arrays;
import java.util.List;

public class SequenceRule implements Rule {

    private static final List KEYBOARD_SEQS = Arrays.asList(new String[]{"qwerty", "asdfgh", "zxcvbn"});

    @Override
    public RuleResult evaluate(String password) {
        if (password == null || password.length() < 3) {
            return RuleResult.ok(getName(), 0, "No sequences (too short)");
        }
        int penalty = 0;
        StringBuilder msg = new StringBuilder();

        int alpha = longestAlphaSequence(password);
        int num = longestNumericSequence(password);
        int kb = longestKeyboardSequence(password.toLowerCase());

        if (alpha >= 3) {
            int p = calculatePenalty(alpha);
            penalty -= p;
            msg.append(String.format("alpha seq len=%d(-%d)", alpha, p));
        }
        if (num >= 3) {
            int p = calculatePenalty(num);
            penalty -= p;
            if (msg.length()>0) msg.append("; ");
            msg.append(String.format("numeric seq len=%d(-%d)", num, p));
        }
        if (kb >= 3) {
            int p = calculatePenalty(kb + 1);
            penalty -= p;
            if (msg.length()>0) msg.append("; ");
            msg.append(String.format("keyboard seq len=%d(-%d)", kb, p));
        }

        if (penalty == 0) return RuleResult.ok(getName(), 0, "No significant sequences");
        return RuleResult.warn(getName(), penalty, msg.toString());
    }

    @Override
    public String getName() {
        return "SequenceRule";
    }

    private int calculatePenalty(int length) {
        // non-linear: length 3 -> 2, 4 -> 4, 5 -> 7, 6 -> 11
        return Math.max(2, (length * (length - 2)) / 1);
    }

    private int longestAlphaSequence(String s) {
        int best = 1;
        int cur = 1;
        for (int i = 1; i < s.length(); i++) {
            char prev = Character.toLowerCase(s.charAt(i - 1));
            char c = Character.toLowerCase(s.charAt(i));
            if (Character.isLetter(prev) && Character.isLetter(c) && (c - prev == 1)) {
                cur++;
            } else {
                best = Math.max(best, cur);
                cur = 1;
            }
        }
        best = Math.max(best, cur);
        return best;
    }

    private int longestNumericSequence(String s) {
        int best = 1;
        int cur = 1;
        for (int i = 1; i < s.length(); i++) {
            char prev = s.charAt(i - 1);
            char c = s.charAt(i);
            if (Character.isDigit(prev) && Character.isDigit(c) && (c - prev == 1)) {
                cur++;
            } else {
                best = Math.max(best, cur);
                cur = 1;
            }
        }
        best = Math.max(best, cur);
        return best;
    }

    private int longestKeyboardSequence(String s) {
        int best = 0;
        for (Object seqObj : KEYBOARD_SEQS) {
            String seq = (String) seqObj;
            for (int len = seq.length(); len >= 3; len--) {
                for (int i = 0; i + len <= seq.length(); i++) {
                    String sub = seq.substring(i, i + len);
                    if (s.indexOf(sub) >= 0) return Math.max(best, len);
                }
            }
        }
        return best;
    }
}
