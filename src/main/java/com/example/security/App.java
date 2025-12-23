package com.example.security;

import com.example.security.model.PasswordConfig;

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

        System.out.println("=== 密碼強度分析系統 ===");

        PasswordConfig securityPolicy = buildConfigFromArgs(args);
        displayPolicy(securityPolicy);

        System.out.println("系統已就緒，等待輸入密碼進行分析...");
    }

    private static void printUsage() {
        System.out.println("用法:");
        System.out.println("  java App policy");
        System.out.println("  java App strong");
        System.out.println("  java App minLength=10 requireSymbols=false");
    }

    // ⭐ 這就是你要「截圖給組員用」的五大功能（中文、好看）
    private static void printFiveFunctions(PasswordConfig config) {
        System.out.println("==============================================");
        System.out.println("      密碼強度分析系統－五大功能");
        System.out.println("==============================================");
        System.out.println();
        System.out.println("1. 最小長度限制");
        System.out.println("   密碼長度至少需為 " + config.getMinLength() + " 個字元");
        System.out.println();
        System.out.println("2. 大寫字母檢查");
        System.out.println("   是否要求包含大寫字母："
                + (config.isRequireUpper() ? "是" : "否"));
        System.out.println();
        System.out.println("3. 小寫字母檢查");
        System.out.println("   是否要求包含小寫字母："
                + (config.isRequireLower() ? "是" : "否"));
        System.out.println();
        System.out.println("4. 數字檢查");
        System.out.println("   是否要求包含數字："
                + (config.isRequireDigits() ? "是" : "否"));
        System.out.println();
        System.out.println("5. 特殊符號檢查");
        System.out.println("   是否要求包含特殊符號："
                + (config.isRequireSymbols() ? "是" : "否"));
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
