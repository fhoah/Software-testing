package com.example.security.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class PasswordGeneratorServiceTest {

    @Test
    void generates_with_categories() {
        PasswordGeneratorService gen = new PasswordGeneratorService();
        String pw = gen.generate(12, true, true, true, true, false);
        assertThat(pw).hasSize(12);
        assertThat(pw.chars().anyMatch(Character::isUpperCase)).isTrue();
        assertThat(pw.chars().anyMatch(Character::isLowerCase)).isTrue();
        assertThat(pw.chars().anyMatch(Character::isDigit)).isTrue();
        assertThat(pw.chars().anyMatch(ch -> "!@#$%^&*()-_+=<>?".indexOf(ch) >= 0)).isTrue();
    }

    @Test
    void pronounceable_mode() {
        PasswordGeneratorService gen = new PasswordGeneratorService();
        String pw = gen.generate(10, false, true, false, false, true);
        assertThat(pw).hasSize(10);
        // ensure mostly letters
        assertThat(pw.chars().allMatch(ch -> Character.isLetter(ch))).isTrue();
    }

    @Test
    void guarantees_at_least_one_category() {
        PasswordGeneratorService gen = new PasswordGeneratorService();
        String pw = gen.generate(4, true, false, false, false, false);
        assertThat(pw.chars().anyMatch(Character::isUpperCase)).isTrue();
    }

    @Test
    void length_one_symbol() {
        PasswordGeneratorService gen = new PasswordGeneratorService();
        String pw = gen.generate(1, false, false, false, true, false);
        assertThat(pw).hasSize(1);
        assertThat(pw.chars().anyMatch(ch -> "!@#$%^&*()-_+=<>?".indexOf(ch) >= 0)).isTrue();
    }

    @Test
    void only_lower_default_when_none_selected() {
        PasswordGeneratorService gen = new PasswordGeneratorService();
        String pw = gen.generate(5, false, false, false, false, false);
        assertThat(pw.chars().allMatch(ch -> Character.isLowerCase(ch))).isTrue();
    }
}
