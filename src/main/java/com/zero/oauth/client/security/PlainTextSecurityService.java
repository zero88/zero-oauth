package com.zero.oauth.client.security;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

import com.zero.oauth.client.utils.Strings;

/**
 * Use plain text to generate random token.
 */
public class PlainTextSecurityService extends RandomSecurityService implements SecurityService {

    static final SecurityService INSTANCE = new PlainTextSecurityService();

    private PlainTextSecurityService() {
        this.registerMachine(new RandomText(SecurityService.loadRandomTextMaxLength(),
                                            SecurityService.loadRandomTextSymbols()));
    }

    static class RandomText implements RandomMachine {

        private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        private static final String LOWER = UPPER.toLowerCase(Locale.ENGLISH);
        private static final String DIGITS = "0123456789";
        private static final String ALPHANUMERIC = UPPER + LOWER + DIGITS;
        private static final int MIN_LENGTH = 8;
        private final Random random;
        private final int maxLength;
        private final char[] symbols;

        RandomText(int maxLength, String symbols) {
            if (maxLength < MIN_LENGTH) {
                throw new IllegalArgumentException("Invalid max length. Must be greater than 8.");
            }
            this.random = new SecureRandom();
            this.maxLength = maxLength;
            String s = Strings.isBlank(symbols)
                       ? ALPHANUMERIC
                       : Strings.requiredMinLength(Strings.optimizeNoSpace(symbols), MIN_LENGTH);
            this.symbols = s.toCharArray();
        }

        @Override
        public String getRandom() {
            char[] buf = new char[this.random.nextInt(this.maxLength - MIN_LENGTH) + MIN_LENGTH];
            for (int idx = 0; idx < buf.length; ++idx) {
                buf[idx] = symbols[random.nextInt(symbols.length)];
            }
            return new String(buf);
        }

    }

}
