package com.emailschedule.util;

import java.util.UUID;

public class UUIDGenerator {

    private static UUIDGenerator instance;

    private UUIDGenerator() {
    }

    public static synchronized UUIDGenerator getInstance() {
        if (instance == null) {
            instance = new UUIDGenerator();
        }
        return instance;
    }

    public UUID generateUUID() {
        return UUID.randomUUID();
    }
}
