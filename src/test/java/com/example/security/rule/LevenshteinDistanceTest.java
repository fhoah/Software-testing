package com.example.security.rule;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class LevenshteinDistanceTest {

    @Test
    void distance_empty_both() {
        assertThat(LevenshteinDistance.distance("", "")).isEqualTo(0);
    }

    @Test
    void distance_empty_vs_nonempty() {
        assertThat(LevenshteinDistance.distance("", "abc")).isEqualTo(3);
        assertThat(LevenshteinDistance.distance("abc", "")).isEqualTo(3);
    }

    @Test
    void distance_equal_strings() {
        assertThat(LevenshteinDistance.distance("password", "password")).isEqualTo(0);
    }

    @Test
    void distance_simple_substitution() {
        assertThat(LevenshteinDistance.distance("kitten", "sitten")).isEqualTo(1);
    }

    @Test
    void distance_insertion_and_deletion() {
        assertThat(LevenshteinDistance.distance("abc", "abxc")).isEqualTo(1);
        assertThat(LevenshteinDistance.distance("abcd", "acd")).isEqualTo(1);
    }

    @Test
    void normalized_distance_bounds() {
        double nd = LevenshteinDistance.normalizedDistance("abc", "abd");
        assertThat(nd).isGreaterThanOrEqualTo(0.0).isLessThanOrEqualTo(1.0);
    }

    @Test
    void long_mismatch_heuristic() {
        // ensure the large mismatch branch returns length difference when very different
        String a = "a";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) sb.append('x');
        String b = sb.toString();
        int d = LevenshteinDistance.distance(a, b);
        assertThat(d).isEqualTo(Math.abs(b.length() - a.length()));
    }
}
