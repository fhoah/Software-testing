package com.example.security.service;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BatchProcessingServiceTest {

    @Test
    void process_file_creates_csv() throws Exception {
        File in = File.createTempFile("pwlist", ".txt");
        File out = File.createTempFile("report", ".csv");

        try {
            // 寫入輸入檔
            try (Writer fw = new FileWriter(in)) {
                fw.write("password\n12345\n");
            }

            PasswordAnalyzerService svc = new PasswordAnalyzerService();
            BatchProcessingService bps = new BatchProcessingService(svc);
            bps.processFile(in, out);

            // 讀輸出檔並檢查行數與格式 (header + 2 密碼行)
            List<String> lines = new ArrayList<String>();
            try (BufferedReader br = new BufferedReader(new FileReader(out))) {
                String l;
                while ((l = br.readLine()) != null) {
                    lines.add(l);
                }
            }

            // 預期 header + 2 條有效記錄
            assertThat(lines).hasSize(3);
            String header = lines.get(0);
            assertThat(header).isEqualTo("password,score,entropy,strength,rules");

            // 每條資料行至少包含 4 個逗號（password,score,entropy,strength,rules）
            for (int i = 1; i < lines.size(); i++) {
                String data = lines.get(i);
                int commaCount = 0;
                for (char c : data.toCharArray()) if (c == ',') commaCount++;
                assertThat(commaCount).isGreaterThanOrEqualTo(4);
            }
        } finally {
            // 確保刪除臨時檔（即使測試失敗）
            try { in.delete(); } catch (Exception ignored) {}
            try { out.delete(); } catch (Exception ignored) {}
        }
    }

    @Test
    void process_file_ignores_empty_lines() throws Exception {
        File in = File.createTempFile("pwlist2", ".txt");
        File out = File.createTempFile("report2", ".csv");

        try {
            try (Writer fw = new FileWriter(in)) {
                fw.write("\n\n123\n\n");
            }

            PasswordAnalyzerService svc = new PasswordAnalyzerService();
            BatchProcessingService bps = new BatchProcessingService(svc);
            bps.processFile(in, out);

            List<String> lines = new ArrayList<String>();
            try (BufferedReader br = new BufferedReader(new FileReader(out))) {
                String l;
                while ((l = br.readLine()) != null) lines.add(l);
            }

            // 預期 header + 1 條有效記錄
            assertThat(lines).hasSize(2);
            assertThat(lines.get(0)).isEqualTo("password,score,entropy,strength,rules");

            // 檢查資料行格式
            String data = lines.get(1);
            int commaCount = 0;
            for (char c : data.toCharArray()) if (c == ',') commaCount++;
            assertThat(commaCount).isGreaterThanOrEqualTo(4);
        } finally {
            try { in.delete(); } catch (Exception ignored) {}
            try { out.delete(); } catch (Exception ignored) {}
        }
    }
}
