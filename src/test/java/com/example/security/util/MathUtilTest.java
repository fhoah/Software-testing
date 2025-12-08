package com.example.security.util;

import com.example.security.model.TrendStatus;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MathUtilTest {

    @Test
    void average_and_stddev_array() {
        double[] arr = new double[]{1.0, 2.0, 3.0};
        assertThat(MathUtil.average(arr)).isEqualTo(2.0);
        assertThat(MathUtil.standardDeviation(arr)).isGreaterThan(0.8);
    }

    @Test
    void average_and_stddev_list() {
        List<Double> l = Arrays.asList(2.0, 4.0, 6.0);
        assertThat(MathUtil.average(l)).isEqualTo((2.0+4.0+6.0)/3.0);
        assertThat(MathUtil.standardDeviation(l)).isGreaterThan(1.5);
    }

    @Test
    void detect_trend_basic() {
        List<Double> up = Arrays.asList(1.0, 2.0, 3.0, 4.0);
        assertThat(MathUtil.detectTrend(up, 0.1)).isEqualTo(TrendStatus.IMPROVING);
        List<Double> down = Arrays.asList(5.0, 4.0, 3.0, 2.0);
        assertThat(MathUtil.detectTrend(down, 0.1)).isEqualTo(TrendStatus.DECLINING);
    }
}
