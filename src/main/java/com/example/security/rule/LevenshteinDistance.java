package com.example.security.rule;

public class LevenshteinDistance {

    public static int distance(String a, String b) {
        if (a == null) a = "";
        if (b == null) b = "";
        // branch: empty strings
        if (a.length() == 0) return b.length();
        if (b.length() == 0) return a.length();

        // make sure a is the shorter for potential micro-optimizations
        if (a.length() > b.length()) {
            String tmp = a; a = b; b = tmp;
        }

        int n = a.length();
        int m = b.length();

        // branch: length mismatch big difference -> quick bound
        if (m - n > 50) {
            // if lengths differ a lot, treat as mostly different to avoid expensive compute
            return Math.abs(m - n);
        }

        int[] prev = new int[m + 1];
        int[] curr = new int[m + 1];

        // initialize first row
        for (int j = 0; j <= m; j++) prev[j] = j;

        // dynamic matrix fill
        for (int i = 1; i <= n; i++) {
            curr[0] = i;
            char ca = a.charAt(i - 1);
            for (int j = 1; j <= m; j++) {
                char cb = b.charAt(j - 1);
                int cost = (ca == cb) ? 0 : 1;
                // substitution, insertion, deletion
                int sub = prev[j - 1] + cost;
                int ins = curr[j - 1] + 1;
                int del = prev[j] + 1;
                int best = sub < ins ? sub : ins;
                best = best < del ? best : del;
                curr[j] = best;
            }
            // swap
            int[] tmp = prev; prev = curr; curr = tmp;
        }

        return prev[m];
    }

    public static double normalizedDistance(String a, String b) {
        int d = distance(a, b);
        int max = Math.max(a == null ? 0 : a.length(), b == null ? 0 : b.length());
        if (max == 0) return 0.0;
        return (double) d / (double) max;
    }
}
