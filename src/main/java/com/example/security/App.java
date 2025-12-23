package com.example.security;

import com.example.security.model.PasswordConfig;

import java.security.SecureRandom;
import java.util.Scanner;

public class App {

    // ===== 密碼產生器用字元集 =====
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGIT = "0123456789";
    private static final String SYMBOL = "!@#$%^&*()-_=+[]{};:,.<>?";

    public static void main(String[] args) {

        // help
        if (args != null && args.length > 0) {
            String a0 = args[0].trim().toLowerCase();
            if ("--help".equals(a0) || "help".equals(a0) || "-h".equals(a0)) {
                printUsage();
                return;
            }
        }

        // 只顯示五大功能（方便截圖）
        if (args != null && args.length > 0) {
            String a0 = args[0].trim().toLowerCase();
            if ("policy".equals(a0) || "--policy".equals(a0) || "-p".equals(a0)) {
                // policy 模式下也用「預設自訂策略範例」來顯示
                PasswordConfig cfg = buildDefaultCustomPolicy();
                printFiveFunctions(cfg);
                return;
            }
        }

        // ✅【唯一策略來源】強制進入「自訂安全策略」
        Scanner scanner = new Scanner(System.in);
        PasswordConfig securityPolicy = buildCustomPolicy(scanner);
        displayPolicy(securityPolicy);

        // ✅ 互動模式
        System.out.println();
        System.out.println("系統已就緒，請輸入密碼進行分析（gen 產生高強度密碼 / exit 離開）");

        while (true) {
            System.out.print("請輸入密碼（或 gen / exit）： ");
            String input = scanner.nextLine();

            if (input == null) input = "";
            String cmd = input.trim();

            if ("exit".equalsIgnoreCase(cmd)) {
                System.out.println("已結束分析。");
                break;
            }

            // 產生高強度密碼
            if ("gen".equalsIgnoreCase(cmd) || "generate".equalsIgnoreCase(cmd)) {
                String generated = generateStrongPassword(securityPolicy);
                System.out.println("系統產生的高強度密碼： " + generated);
                analyzeAndPrint(generated, securityPolicy);
                System.out.println();
                continue;
            }

            // 分析輸入密碼
            analyzeAndPrint(input, securityPolicy);
            System.out.println();
        }
    }

    private static void printUsage() {
        System.out.println("用法:");
        System.out.println("  java App policy    （顯示系統五大功能）");
        System.out.println("  java App           （啟動後自訂安全策略）");
        System.out.println();
        System.out.println("互動指令：");
        System.out.println("  gen      產生符合自訂策略的高強度密碼");
        System.out.println("  exit     離開系統");
    }

    // ⭐ 截圖給組員用：五大功能
    private static void printFiveFunctions(PasswordConfig config) {
        System.out.println("==============================================");
        System.out.println("      密碼強度分析系統－五大功能");
        System.out.println("==============================================");
        System.out.println("1. 最小長度限制（>= " + config.getMinLength() + "）");
        System.out.println("2. 大寫字母檢查：" + (config.isRequireUpper() ? "啟用" : "停用"));
        System.out.println("3. 小寫字母檢查：" + (config.isRequireLower() ? "啟用" : "停用"));
        System.out.println("4. 數字檢查：" + (config.isRequireDigits() ? "啟用" : "停用"));
        System.out.println("5. 特殊符號檢查：" + (config.isRequireSymbols() ? "啟用" : "停用"));
        System.out.println("==============================================");
    }

    private static void displayPolicy(PasswordConfig config) {
        System.out.println("[目前使用的自訂安全策略]");
        System.out.println("- 最小長度: " + config.getMinLength());
        System.out.println("- 大寫字母: " + (config.isRequireUpper() ? "需要" : "不需要"));
        System.out.println("- 小寫字母: " + (config.isRequireLower() ? "需要" : "不需要"));
        System.out.println("- 數字: " + (config.isRequireDigits() ? "需要" : "不需要"));
        System.out.println("- 特殊符號: " + (config.isRequireSymbols() ? "需要" : "不需要"));
    }

    // ✅【核心】建立自訂策略
    private static PasswordConfig buildCustomPolicy(Scanner sc) {
        System.out.println("==============================================");
        System.out.println("請設定您的密碼安全策略");
        System.out.println("==============================================");

        int minLen = askInt(sc, "請輸入最小長度（建議 12 以上）： ", 1, 128);
        boolean needUpper = askYesNo(sc, "是否需要大寫字母？（y/n）： ");
        boolean needLower = askYesNo(sc, "是否需要小寫字母？（y/n）： ");
        boolean needDigit = askYesNo(sc, "是否需要數字？（y/n）： ");
        boolean needSymbol = askYesNo(sc, "是否需要特殊符號？（y/n）： ");

        return PasswordConfig.builder()
                .minLength(minLen)
                .requireUpper(needUpper)
                .requireLower(needLower)
                .requireDigits(needDigit)
                .requireSymbols(needSymbol)
                .build();
    }

    // policy 模式顯示用（不影響主流程）
    private static PasswordConfig buildDefaultCustomPolicy() {
        return PasswordConfig.builder()
                .minLength(12)
                .requireUpper(true)
                .requireLower(true)
                .requireDigits(true)
                .requireSymbols(true)
                .build();
    }

    private static int askInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = Integer.parseInt(sc.nextLine().trim());
                if (v < min || v > max) {
                    System.out.println("請輸入 " + min + " ~ " + max + " 的整數。");
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("請輸入有效的整數。");
            }
        }
    }

    private static boolean askYesNo(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim().toLowerCase();
            if (s.equals("y") || s.equals("yes") || s.equals("是")) return true;
            if (s.equals("n") || s.equals("no") || s.equals("否")) return false;
            System.out.println("請輸入 y 或 n。");
        }
    }

    // ===== 分析與產生邏輯（與你原本一致） =====

    private static void analyzeAndPrint(String password, PasswordConfig cfg) {
        boolean hasUpper = containsUpper(password);
        boolean hasLower = containsLower(password);
        boolean hasDigit = containsDigit(password);
        boolean hasSymbol = containsSymbol(password);

        boolean okLength = password.length() >= cfg.getMinLength();
        boolean okUpper = !cfg.isRequireUpper() || hasUpper;
        boolean okLower = !cfg.isRequireLower() || hasLower;
        boolean okDigit = !cfg.isRequireDigits() || hasDigit;
        boolean okSymbol = !cfg.isRequireSymbols() || hasSymbol;

        boolean pass = okLength && okUpper && okLower && okDigit && okSymbol;

        System.out.println("----------------------------------------------");
        System.out.println("分析結果：");
        System.out.println("- 長度：" + (okLength ? "符合" : "不足"));
        System.out.println("- 大寫字母：" + (okUpper ? "符合" : "缺少"));
        System.out.println("- 小寫字母：" + (okLower ? "符合" : "缺少"));
        System.out.println("- 數字：" + (okDigit ? "符合" : "缺少"));
        System.out.println("- 特殊符號：" + (okSymbol ? "符合" : "缺少"));
        System.out.println("結論：" + (pass ? "此密碼符合安全策略" : "此密碼不符合安全策略"));
        System.out.println("----------------------------------------------");
    }

    private static boolean containsUpper(String s) {
        for (char c : s.toCharArray()) if (Character.isUpperCase(c)) return true;
        return false;
    }

    private static boolean containsLower(String s) {
        for (char c : s.toCharArray()) if (Character.isLowerCase(c)) return true;
        return false;
    }

    private static boolean containsDigit(String s) {
        for (char c : s.toCharArray()) if (Character.isDigit(c)) return true;
        return false;
    }

    private static boolean containsSymbol(String s) {
        for (char c : s.toCharArray()) if (!Character.isLetterOrDigit(c)) return true;
        return false;
    }

    private static String generateStrongPassword(PasswordConfig cfg) {
        StringBuilder pool = new StringBuilder();
        StringBuilder pwd = new StringBuilder();

        if (cfg.isRequireUpper()) {
            pwd.append(randomChar(UPPER));
            pool.append(UPPER);
        }
        if (cfg.isRequireLower()) {
            pwd.append(randomChar(LOWER));
            pool.append(LOWER);
        }
        if (cfg.isRequireDigits()) {
            pwd.append(randomChar(DIGIT));
            pool.append(DIGIT);
        }
        if (cfg.isRequireSymbols()) {
            pwd.append(randomChar(SYMBOL));
            pool.append(SYMBOL);
        }

        if (pool.length() == 0) {
            pool.append(UPPER).append(LOWER).append(DIGIT).append(SYMBOL);
        }

        while (pwd.length() < cfg.getMinLength()) {
            pwd.append(randomChar(pool.toString()));
        }

        return shuffle(pwd.toString());
    }

    private static char randomChar(String s) {
        return s.charAt(RANDOM.nextInt(s.length()));
    }

    private static String shuffle(String s) {
        char[] arr = s.toCharArray();
        for (int i = arr.length - 1; i > 0; i--) {
            int j = RANDOM.nextInt(i + 1);
            char t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;
        }
        return new String(arr);
    }
}
