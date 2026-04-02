package util;

import java.util.UUID;
import java.util.Random;

public class IDGenerator {
    public static String generateUserId() {
        return "USR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public static String generateAccountId() {
        Random random = new Random();
        return String.format("%04d %04d %04d", random.nextInt(10000), random.nextInt(10000), random.nextInt(10000));
    }

    public static String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
