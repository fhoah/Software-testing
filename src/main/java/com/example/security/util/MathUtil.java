package com.example.security.util;

import com.example.security.model.TrendStatus;

import java.util.List;

public class MathUtil {
    // math helpers for entropy / statistics

    public static double log2(double x) {
        return Math.log(x) / Math.log(2);
    }

    public static double safeDivide(double a, double b) {
        if (b == 0) return 0.0;
        return a / b;
    }

    public static double average(double[] values) {
        if (values == null || values.length == 0) return 0.0;
        double sum = 0.0;
        for (double v : values) sum += v;
        return sum / values.length;
    }

    public static double average(List<Double> values) {
        if (values == null || values.isEmpty()) return 0.0;
        double sum = 0.0;
        for (Double d : values) sum += d == null ? 0.0 : d.doubleValue();
        return sum / values.size();
    }

    public static double standardDeviation(double[] values) {
        if (values == null || values.length == 0) return 0.0;
        double avg = average(values);
        double sumsq = 0.0;
        for (double v : values) {
            double d = v - avg;
            sumsq += d * d;
        }
        return Math.sqrt(sumsq / values.length);
    }

    public static double standardDeviation(List<Double> values) {
        if (values == null || values.isEmpty()) return 0.0;
        double avg = average(values);
        double sumsq = 0.0;
        for (Double dv : values) {
            double v = dv == null ? 0.0 : dv.doubleValue();
            double d = v - avg;
            sumsq += d * d;
        }
        return Math.sqrt(sumsq / values.size());
    }

    /**
     * Detect trend using simple linear regression slope over the provided values.
     * Returns IMPROVING if slope > threshold, DECLINING if slope < -threshold, else STABLE.
     */
    public static TrendStatus detectTrend(List<Double> values, double threshold) {
        if (values == null || values.size() < 3) return TrendStatus.STABLE;
        int n = values.size();
        // compute means of x (0..n-1) and y
        double meanX = (n - 1) / 2.0;
        double meanY = average(values);
        double num = 0.0;
        double den = 0.0;
        for (int i = 0; i < n; i++) {
            double x = i - meanX;
            double y = (values.get(i) == null) ? 0.0 : values.get(i) - meanY;
            num += x * y;
            den += x * x;
        }
        if (den == 0.0) return TrendStatus.STABLE;
        double slope = num / den;
        if (slope > threshold) return TrendStatus.IMPROVING;
        if (slope < -threshold) return TrendStatus.DECLINING;
        return TrendStatus.STABLE;
    }
}
