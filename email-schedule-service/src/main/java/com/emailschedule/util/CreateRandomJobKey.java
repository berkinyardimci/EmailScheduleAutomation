package com.emailschedule.util;

import java.util.UUID;

public class CreateRandomJobKey {

    public static String generateRandomKey() {
        String randomKey = UUID.randomUUID().toString();
        return randomKey.substring(0, 15);
    }
}
