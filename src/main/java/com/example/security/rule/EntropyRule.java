package com.example.security.rule;

import com.example.security.util.MathUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EntropyRule implements Rule {

    @Override
    public RuleResult evaluate(String password) {
        if (password == null || password.length() == 0) {
            return RuleResult.critical(getName(), -30, "Empty password");
        }
        double entropy = computeShannonEntropy(password);
        int score = scoreFromEntropy(entropy, password.length());
        String msg = String.format("Entropy=%.3f bits", entropy);
        if (score < 0) return RuleResult.warn(getName(), score, msg);
        return RuleResult.ok(getName(), score, msg);
    }

    @Override
    public String getName() {
        return "EntropyRule";
    }

    private double computeShannonEntropy(String s) {
        Map freq = new HashMap();
        for (int i = 0; i < s.length(); i++) {
            Character c = Character.valueOf(s.charAt(i));
            Integer v = (Integer) freq.get(c);
            if (v == null) v = Integer.valueOf(0);
            freq.put(c, Integer.valueOf(v.intValue() + 1));
        }
        double h = 0.0;
        int n = s.length();
        for (Iterator it = freq.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry e = (Map.Entry) it.next();
            double p = ((Integer) e.getValue()).doubleValue() / (double) n;
            h -= p * MathUtil.log2(p);
        }
        return h;
    }

    private int scoreFromEntropy(double entropy, int length) {
        // branching complexity
        if (length <= 4) return -10;
        if (entropy < 2.0) return -8;
        if (entropy < 3.5) return -3;
        if (entropy < 4.5) return 1 + Math.min(3, length / 6);
        // high entropy: reward non-linearly
        return 5 + (int) Math.round((entropy - 4.5) * 2) + (length >= 12 ? 3 : 0);
    }
}
