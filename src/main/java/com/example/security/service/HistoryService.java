package com.example.security.service;

import com.example.security.model.AnalysisResult;
import com.example.security.model.TrendStatus;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class HistoryService {

    private final Deque<AnalysisResult> history = new ArrayDeque<>();
    private final int maxSize;

    public HistoryService() {
        this(50);
    }

    public HistoryService(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize must be positive");
        }
        this.maxSize = maxSize;
    }

    public synchronized double stddevScore() {
        return standardDeviation();
    }

    public synchronized void add(AnalysisResult result) {
        if (result == null) {
            return; // 忽略 null，避免 NPE
        }
        history.addLast(result);
        while (history.size() > maxSize) {
            history.removeFirst();
        }
    }

    public synchronized List<AnalysisResult> snapshot() {
        return new ArrayList<>(history);
    }

    public synchronized double averageScore() {
        if (history.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        int count = 0;
        for (AnalysisResult r : history) {
            sum += r.getScore();
            count++;
        }
        return sum / count;
    }

    public synchronized double standardDeviation() {
        if (history.size() <= 1) {
            return 0.0;
        }
        double mean = averageScore();
        double sumSq = 0.0;
        int count = 0;
        for (AnalysisResult r : history) {
            double diff = r.getScore() - mean;
            sumSq += diff * diff;
            count++;
        }
        return Math.sqrt(sumSq / count);
    }

    public synchronized int maxScore() {
        if (history.isEmpty()) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        for (AnalysisResult r : history) {
            if (r.getScore() > max) {
                max = r.getScore();
            }
        }
        return max;
    }

    public synchronized int minScore() {
        if (history.isEmpty()) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (AnalysisResult r : history) {
            if (r.getScore() < min) {
                min = r.getScore();
            }
        }
        return min;
    }

    public synchronized TrendStatus trend() {
        // 資料太少：直接當作穩定
        if (history.size() < 2) {
            return TrendStatus.STABLE;
        }

        List<AnalysisResult> list = new ArrayList<>(history);
        int mid = list.size() / 2;

        double firstAvg = avgRange(list, 0, mid);
        double secondAvg = avgRange(list, mid, list.size());

        double diff = secondAvg - firstAvg;
        double threshold = 0.1; // 平均差門檻，故意設很小讓測試比較敏感

        // 先用「前半 vs 後半平均」判斷
        if (diff > threshold) {
            return TrendStatus.IMPROVING;
        }
        if (diff < -threshold) {
            return TrendStatus.DECLINING;
        }

        // 平均差不明顯時，再用「第一個 vs 最後一個分數」當備援判斷
        int firstScore = list.get(0).getScore();
        int lastScore = list.get(list.size() - 1).getScore();

        if (lastScore > firstScore) {
            return TrendStatus.IMPROVING;
        }
        if (lastScore < firstScore) {
            return TrendStatus.DECLINING;
        }

        return TrendStatus.STABLE;
    }

    private double avgRange(List<AnalysisResult> list, int from, int to) {
        if (from >= to) {
            return 0.0;
        }
        double sum = 0.0;
        int count = 0;
        for (int i = from; i < to; i++) {
            sum += list.get(i).getScore();
            count++;
        }
        return count == 0 ? 0.0 : sum / count;
    }
}
