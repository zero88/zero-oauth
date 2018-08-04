package com.zero.oauth.client.security;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Abstract random service to test.
 */
@RequiredArgsConstructor
@Getter(AccessLevel.PACKAGE)
abstract class RandomSecurityService implements SecurityService {

    private RandomMachine randomMachine;

    final <T extends RandomMachine> void registerMachine(T machine) {
        this.randomMachine = machine;
    }

    @Override
    public final String randomToken() {
        return this.getRandomMachine().getRandom();
    }

    interface RandomMachine {

        String getRandom();

    }

}
