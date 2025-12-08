package com.example.security.service;

import com.example.security.model.AnalysisResult;

import java.io.*;
import java.util.List;

public class BatchProcessingService {
    private final PasswordAnalyzerService analyzer;

    public BatchProcessingService(PasswordAnalyzerService analyzer) {
        this.analyzer = analyzer;
    }

    public void processFile(File inputFile, File outputCsv) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputCsv));
        try {
            // write header
            bw.write("password,score,entropy,strength,rules\n");
            String line;
            while ((line = br.readLine()) != null) {
                String password = line.trim();
                if (password.length() == 0) continue;
                AnalysisResult res = analyzer.analyze(password);
                String rulesSummary = summarizeRules(res);
                // escape commas in password
                String pwEsc = password.replaceAll(",", "\\,");
                bw.write(String.format("%s,%d,%.3f,%s,%s\n", pwEsc, res.getScore(), res.getEntropy(), res.getStrengthLevel(), rulesSummary));
            }
        } finally {
            try { br.close(); } catch (IOException ignored) {}
            try { bw.close(); } catch (IOException ignored) {}
        }
    }

    private String summarizeRules(AnalysisResult res) {
        StringBuilder sb = new StringBuilder();
        List rules = res.getRuleResults();
        for (int i = 0; i < rules.size(); i++) {
            Object o = rules.get(i);
            if (o instanceof com.example.security.rule.RuleResult) {
                com.example.security.rule.RuleResult rr = (com.example.security.rule.RuleResult) o;
                if (i > 0) sb.append(";");
                sb.append(rr.getRuleName()).append(":").append(rr.getScore());
            }
        }
        return sb.toString();
    }
}
