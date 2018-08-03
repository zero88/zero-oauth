package com.zero.oauth.client.security;

import java.util.Random;

/**
 * Use Unix epoch timestamp to generate random token.
 */
public final class TimestampSecurityService extends RandomSecurityService implements SecurityService {

    static final SecurityService INSTANCE = new TimestampSecurityService();

    private TimestampSecurityService() {
        this.registerMachine(new TimerMachine());
    }

    <T extends TimerMachine> void registerMachine(T randomMachine) {
        super.registerMachine(randomMachine);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTimestampInSeconds() {
        return String.valueOf(((TimerMachine) this.getRandomMachine()).getMilis() / 1000);
    }

    static class TimerMachine implements RandomMachine {

        private final Random rand = new Random();

        @Override
        public String getRandom() {
            return String.valueOf(this.getMilis() / 1000 + this.getRandomInteger());
        }

        Long getMilis() {
            return System.currentTimeMillis();
        }

        Integer getRandomInteger() {
            return rand.nextInt();
        }

    }

}
