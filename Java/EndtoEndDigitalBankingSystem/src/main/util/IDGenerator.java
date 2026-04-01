package util;

import java.util.UUID;

public class IDGenerator {
    public static String generateUserId() {
        return "USR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public static String generateAccountId() {
        return "ACC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public static String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
