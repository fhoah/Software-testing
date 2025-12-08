package com.example.security.rule;

public class CharsetRule implements Rule {

    @Override
    public RuleResult evaluate(String password) {
        if (password == null || password.length() == 0) {
            return RuleResult.critical(getName(), -20, "Empty password");
        }

        boolean hasUpper = hasUpper(password);
        boolean hasLower = hasLower(password);
        boolean hasDigit = hasDigit(password);
        boolean hasSymbol = hasSymbol(password);

        int types = countTrue(hasUpper, hasLower, hasDigit, hasSymbol);
        int score = 0;
        StringBuilder msg = new StringBuilder();

        switch (types) {
            case 4:
                score += 10;
                msg.append("All 4 charsets present");
                break;
            case 3:
                score += 6;
                msg.append("3 charsets present");
                break;
            case 2:
                score += 2;
                msg.append("2 charsets present");
                break;
            case 1:
                score -= 6;
                msg.append("Only one charset present");
                break;
            default:
                score -= 10;
                msg.append("No charset detected");
        }

        // branching: digits-only or letters-only provide specific penalties
        if (isDigitsOnly(password)) {
            score -= 8;
            msg.append("; digits only");
        }
        if (isLettersOnly(password)) {
            score -= 4;
            msg.append("; letters only");
        }

        // reward presence of mixed-case and symbols together
        if (hasUpper && hasLower && hasSymbol) {
            score += 3;
            msg.append("; mixed-case+symbol bonus");
        }

        return score < 0 ? RuleResult.warn(getName(), score, msg.toString())
                : RuleResult.ok(getName(), score, msg.toString());
    }

    @Override
    public String getName() {
        return "CharsetRule";
    }

    private boolean hasUpper(String s) {
        for (int i = 0; i < s.length(); i++)
            if (Character.isUpperCase(s.charAt(i)))
                return true;
        return false;
    }

    private boolean hasLower(String s) {
        for (int i = 0; i < s.length(); i++)
            if (Character.isLowerCase(s.charAt(i)))
                return true;
        return false;
    }

    private boolean hasDigit(String s) {
        for (int i = 0; i < s.length(); i++)
            if (Character.isDigit(s.charAt(i)))
                return true;
        return false;
    }

    private boolean hasSymbol(String s) {
        for (int i = 0; i < s.length(); i++)
            if (!Character.isLetterOrDigit(s.charAt(i)))
                return true;
        return false;
    }

    private int countTrue(boolean... vals) {
        int ct = 0;
        for (boolean v : vals)
            if (v)
                ct++;
        return ct;
    }

    private boolean isDigitsOnly(String s) {
        for (int i = 0; i < s.length(); i++)
            if (!Character.isDigit(s.charAt(i)))
                return false;
        return true;
    }

    private boolean isLettersOnly(String s) {
        for (int i = 0; i < s.length(); i++)
            if (!Character.isLetter(s.charAt(i)))
                return false;
        return true;
    }
}
