package utilities;

import java.util.UUID;

public class RandomDataUtil {
    private RandomDataUtil() {}

    public static String randomEmail() {
        return "testuser" + System.currentTimeMillis() + "@gmail.com";
    }

    public static String randomPhone() {
        return "98" + String.valueOf(System.currentTimeMillis()).substring(5, 13);
    }

    public static String randomPassword() {
        return "Test" + UUID.randomUUID().toString().substring(0, 6);
    }
}