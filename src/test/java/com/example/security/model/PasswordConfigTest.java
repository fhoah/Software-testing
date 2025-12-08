package com.example.security.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class PasswordConfigTest {

    @Test
    void builder_defaults() {
        PasswordConfig cfg = PasswordConfig.builder().build();
        assertThat(cfg.getMinLength()).isEqualTo(8);
        assertThat(cfg.isRequireUpper()).isTrue();
        assertThat(cfg.isRequireLower()).isTrue();
        assertThat(cfg.isRequireDigits()).isTrue();
        assertThat(cfg.isRequireSymbols()).isFalse();
    }

    @Test
    void custom_builder_and_getters() {
        PasswordConfig cfg = PasswordConfig.builder()
                .minLength(12)
                .requireSymbols(true)
                .requireDigits(false)
                .pronounceable(true)
                .build();
        assertThat(cfg.getMinLength()).isEqualTo(12);
        assertThat(cfg.isRequireSymbols()).isTrue();
        assertThat(cfg.isRequireDigits()).isFalse();
        assertThat(cfg.isPronounceable()).isTrue();
    }

    @Test
    void invalid_min_length_throws() {
        assertThatThrownBy(() -> new PasswordConfig(0, true, true, true, false, false))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("minLength");
    }

    @Test
    void equals_hashcode_and_toString() {
        PasswordConfig a = PasswordConfig.builder().minLength(10).requireSymbols(true).build();
        PasswordConfig b = PasswordConfig.builder().minLength(10).requireSymbols(true).build();
        PasswordConfig c = PasswordConfig.builder().minLength(11).build();
        assertThat(a).isEqualTo(b);
        assertThat(a.hashCode()).isEqualTo(b.hashCode());
        assertThat(a).isNotEqualTo(c);
        assertThat(a.toString()).contains("minLength=10").contains("requireSymbols=true");
    }
}
