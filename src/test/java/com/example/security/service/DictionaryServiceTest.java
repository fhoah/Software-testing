package com.example.security.service;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;

import static org.assertj.core.api.Assertions.*;

public class DictionaryServiceTest {

    @Test
    void is_weak_exact_and_leet() throws Exception {
        DictionaryService ds = new DictionaryService();
        ds.addWord("admin");
        assertThat(ds.isWeak("admin")).isTrue();
        assertThat(ds.isWeak("@dm1n")).isTrue();
    }

    @Test
    void is_similar() {
        DictionaryService ds = new DictionaryService();
        ds.addWord("password");
        assertThat(ds.isSimilar("passw0rd", 0.3)).isTrue();
    }

    @Test
    void load_from_file() throws Exception {
        File tmp = File.createTempFile("dict", ".txt");
        FileWriter fw = new FileWriter(tmp);
        try {
            fw.write("foobar\n");
        } finally { fw.close(); }
        DictionaryService ds = new DictionaryService();
        ds.loadFromFile(tmp);
        assertThat(ds.isWeak("foobar")).isTrue();
        tmp.delete();
    }
}
