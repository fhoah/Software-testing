package com.example.security.model;

public class PasswordConfig {
    private final int minLength;
    private final boolean requireUpper;
    private final boolean requireLower;
    private final boolean requireDigits;
    private final boolean requireSymbols;
    private final boolean pronounceable;

    public PasswordConfig(int minLength, boolean requireUpper, boolean requireLower, boolean requireDigits, boolean requireSymbols, boolean pronounceable) {
        if (minLength < 1) throw new IllegalArgumentException("minLength must be >= 1");
        this.minLength = minLength;
        this.requireUpper = requireUpper;
        this.requireLower = requireLower;
        this.requireDigits = requireDigits;
        this.requireSymbols = requireSymbols;
        this.pronounceable = pronounceable;
    }

    public int getMinLength() {
        return minLength;
    }

    public boolean isRequireUpper() {
        return requireUpper;
    }

    public boolean isRequireLower() {
        return requireLower;
    }

    public boolean isRequireDigits() {
        return requireDigits;
    }

    public boolean isRequireSymbols() {
        return requireSymbols;
    }

    public boolean isPronounceable() {
        return pronounceable;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int minLength = 8;
        private boolean requireUpper = true;
        private boolean requireLower = true;
        private boolean requireDigits = true;
        private boolean requireSymbols = false;
        private boolean pronounceable = false;

        public Builder minLength(int l) { this.minLength = l; return this; }
        public Builder requireUpper(boolean b) { this.requireUpper = b; return this; }
        public Builder requireLower(boolean b) { this.requireLower = b; return this; }
        public Builder requireDigits(boolean b) { this.requireDigits = b; return this; }
        public Builder requireSymbols(boolean b) { this.requireSymbols = b; return this; }
        public Builder pronounceable(boolean b) { this.pronounceable = b; return this; }

        public PasswordConfig build() {
            return new PasswordConfig(minLength, requireUpper, requireLower, requireDigits, requireSymbols, pronounceable);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PasswordConfig)) return false;
        PasswordConfig that = (PasswordConfig) o;
        return minLength == that.minLength && requireUpper == that.requireUpper && requireLower == that.requireLower && requireDigits == that.requireDigits && requireSymbols == that.requireSymbols && pronounceable == that.pronounceable;
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(minLength);
        result = 31 * result + Boolean.hashCode(requireUpper);
        result = 31 * result + Boolean.hashCode(requireLower);
        result = 31 * result + Boolean.hashCode(requireDigits);
        result = 31 * result + Boolean.hashCode(requireSymbols);
        result = 31 * result + Boolean.hashCode(pronounceable);
        return result;
    }

    @Override
    public String toString() {
        return "PasswordConfig{" +
                "minLength=" + minLength +
                ", requireUpper=" + requireUpper +
                ", requireLower=" + requireLower +
                ", requireDigits=" + requireDigits +
                ", requireSymbols=" + requireSymbols +
                ", pronounceable=" + pronounceable +
                '}';
    }
}
