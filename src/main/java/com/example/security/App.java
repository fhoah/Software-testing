package com.example.security;

import com.example.security.model.PasswordConfig;

import java.util.Scanner;

public class App {

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
                PasswordConfig cfg = buildConfigFromArgs(
                        args.length > 1
                                ? java.util.Arrays.copyOfRange(args, 1, args.length)
                                : new String[0]
                );
                printFiveFunctions(cfg);
                return;
            }
        }


        // 讀取策略（preset 或預設）
        PasswordConfig securityPolicy = buildConfigFromArgs(args);
        displayPolicy(securityPolicy);

        // ✅ 真的讓使用者輸入密碼
        System.out.println();
        System.out.println("系統已就緒，請輸入密碼進行分析（輸入 exit 可離開）");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("請輸入密碼： ");
            String password = scanner.nextLine();

            if (password == null) password = "";
            if ("exit".equalsIgnoreCase(password.trim())) {
                System.out.println("已結束分析。");
                break;
            }

            // ✅ 分析並輸出
            analyzeAndPrint(password, securityPolicy);
            System.out.println();
        }
    }

    private static void printUsage() {
        System.out.println("用法:");
        System.out.println("  java App policy                     （只顯示五大功能，方便截圖）");
        System.out.println("  java App strong                     （使用 strong 策略）");
        System.out.println("  java App weak                       （使用 weak 策略）");
        System.out.println("  java App                            （使用預設策略）");
        System.out.println();
        System.out.println("執行後可在 Console 輸入密碼分析，輸入 exit 離開。");
    }

    // ⭐ 截圖給組員用：五大功能（中文）
    private static void printFiveFunctions(PasswordConfig config) {
        System.out.println("==============================================");
        System.out.println("      密碼強度分析系統－五大功能");
        System.out.println("==============================================");
        System.out.println();
        System.out.println("1. 最小長度限制");
        System.out.println("   密碼長度至少需為 " + config.getMinLength() + " 個字元");
        System.out.println();
        System.out.println("2. 大寫字母檢查");
        System.out.println("   是否要求包含大寫字母：" + (config.isRequireUpper() ? "是" : "否"));
        System.out.println();
        System.out.println("3. 小寫字母檢查");
        System.out.println("   是否要求包含小寫字母：" + (config.isRequireLower() ? "是" : "否"));
        System.out.println();
        System.out.println("4. 數字檢查");
        System.out.println("   是否要求包含數字：" + (config.isRequireDigits() ? "是" : "否"));
        System.out.println();
        System.out.println("5. 特殊符號檢查");
        System.out.println("   是否要求包含特殊符號：" + (config.isRequireSymbols() ? "是" : "否"));
        System.out.println();
        System.out.println("==============================================");
    }

    private static void displayPolicy(PasswordConfig config) {
        System.out.println("[目前載入的安全策略]");
        System.out.println("- 最小長度: " + config.getMinLength());
        System.out.println("- 大寫字母: " + (config.isRequireUpper() ? "需要" : "不需要"));
        System.out.println("- 小寫字母: " + (config.isRequireLower() ? "需要" : "不需要"));
        System.out.println("- 數字: " + (config.isRequireDigits() ? "需要" : "不需要"));
        System.out.println("- 特殊符號: " + (config.isRequireSymbols() ? "需要" : "不需要"));
    }

    // ✅ 分析密碼是否符合五大規則 + 顯示缺少項目（適合 demo）
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
        System.out.println("- 密碼長度: " + password.length() + "（需求 >= " + cfg.getMinLength() + "）" );
        System.out.println("- 大寫字母: " + (hasUpper ? "有" : "無") );
        System.out.println("- 小寫字母: " + (hasLower ? "有" : "無") );
        System.out.println("- 數字:     " + (hasDigit ? "有" : "無") );
        System.out.println("- 特殊符號: " + (hasSymbol ? "有" : "無") );
        System.out.println();

        if (pass) {
            System.out.println("此密碼符合目前安全策略。");
        } else {
            System.out.println("此密碼不符合目前安全策略。");
            System.out.println("缺少/未達條件：");
            if (!okLength) System.out.println("1) 長度不足（至少 " + cfg.getMinLength() + " 字元）");
            if (!okUpper) System.out.println("2) 缺少大寫字母");
            if (!okLower) System.out.println("3) 缺少小寫字母");
            if (!okDigit) System.out.println("4) 缺少數字");
            if (!okSymbol) System.out.println("5) 缺少特殊符號");
        }
        System.out.println("----------------------------------------------");
    }

    private static boolean containsUpper(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isUpperCase(s.charAt(i))) return true;
        }
        return false;
    }

    private static boolean containsLower(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isLowerCase(s.charAt(i))) return true;
        }
        return false;
    }

    private static boolean containsDigit(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) return true;
        }
        return false;
    }

    // 簡單判斷：非英數就視為符號（夠 demo 用）
    private static boolean containsSymbol(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!Character.isLetterOrDigit(c)) return true;
        }
        return false;
    }

    // 依 CLI 參數建立策略（維持你原本的 weak/strong/default）
    private static PasswordConfig buildConfigFromArgs(String[] args) {
        if (args == null || args.length == 0) {
            return PasswordConfig.builder()
                    .minLength(12)
                    .requireUpper(true)
                    .requireLower(true)
                    .requireDigits(true)
                    .requireSymbols(true)
                    .build();
        }

        String first = args[0].toLowerCase();
        PasswordConfig.Builder builder;

        switch (first) {
            case "weak":
                builder = PasswordConfig.builder()
                        .minLength(6)
                        .requireUpper(false)
                        .requireLower(true)
                        .requireDigits(false)
                        .requireSymbols(false);
                break;
            case "strong":
                builder = PasswordConfig.builder()
                        .minLength(12)
                        .requireUpper(true)
                        .requireLower(true)
                        .requireDigits(true)
                        .requireSymbols(true);
                break;
            default:
                // 不是 preset 就走預設（你的原版邏輯）
                builder = PasswordConfig.builder()
                        .minLength(12)
                        .requireUpper(true)
                        .requireLower(true)
                        .requireDigits(true)
                        .requireSymbols(true);
        }

        return builder.build();
    }
}
