package com.zero.oauth.client.security;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zero.oauth.client.utils.Constants;

public class RandomSecurityServiceTest {

    private SecurityService timerService;
    private SecurityService plainService;
    private SecurityService uuidService;

    @Before
    public void setUp() {
        timerService = ServiceRegistry.getSecurityService(Constants.TIMESTAMP_ALGO);
        plainService = ServiceRegistry.getSecurityService(Constants.TEXT_ALGO);
        uuidService = ServiceRegistry.getSecurityService(Constants.UUID_ALGO);
    }

    @Test
    public void test_timestampInSeconds_stub() {
        ((TimestampSecurityService) timerService).registerMachine(new TimerStub());
        Assert.assertEquals(String.valueOf(1234567), timerService.getTimestampInSeconds());
    }

    @Test
    public void test_randomToken_fromTimer_stub() {
        ((TimestampSecurityService) timerService).registerMachine(new TimerStub());
        Assert.assertEquals(String.valueOf(1241563), timerService.randomToken());
    }

    @Test
    public void test_randomToken_fromText_stub() {
        ((PlainTextSecurityService) plainService).registerMachine(new RandomStub());
        Assert.assertEquals("abc", plainService.randomToken());
    }

    @Test
    public void test_timestampInSeconds() {
        String ts = uuidService.getTimestampInSeconds();
        Assert.assertTrue(ts.matches("[0-9]+"));
    }

    @Test
    public void test_randomToken_fromText() {
        String token = plainService.randomToken();
        Assert.assertTrue(token.length() >= 8);
        Assert.assertTrue(token.length() < 32);
        System.out.println("Plain text token:" + token);
        Assert.assertTrue(token.matches("[A-Za-z0-9]{8,32}"));
    }

    @Test
    public void test_randomToken_fromText_MustFlexibleCharLen() {
        int len = 0;
        while (true) {
            String token = plainService.randomToken();
            len = len == 0 ? token.length() : len;
            if (token.length() - len != 0) {
                return;
            }
        }
    }

    @Test
    public void test_randomToken_fromText_Customization() {
        ((PlainTextSecurityService) plainService)
            .registerMachine(new PlainTextSecurityService.RandomText(10, "!@#$%^&*()-="));
        String token = this.plainService.randomToken();
        System.out.println("Plain text special token: " + token);
        Assert.assertTrue(token.matches("[\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\-\\=]{8,10}"));
    }

    @Test
    public void test_randomToken_fromTimer() {
        String token = timerService.randomToken();
        System.out.println("Timer token:" + token);
        Assert.assertTrue(token.matches("[0-9]+"));
    }

    @Test
    public void test_randomToken_fromUUID() {
        String token = uuidService.randomToken();
        System.out.println("UUID token:" + token);
        UUID.fromString(token);
    }

    private static class TimerStub extends TimestampSecurityService.TimerMachine {

        @Override
        public Long getMilis() {
            return 1234567890L;
        }

        @Override
        public Integer getRandomInteger() {
            return 6996;
        }

    }


    private static class RandomStub implements RandomSecurityService.RandomMachine {

        @Override
        public String getRandom() {
            return "abc";
        }

    }

}
