package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties = new Properties();
    private static boolean loaded = false;

    private ConfigReader() {}

    public static void loadConfig() {
        if (loaded) {
            return;
        }
        try (FileInputStream fis = new FileInputStream("src\\test\\resources\\config\\  config.properties")) {
            properties.load(fis);
            loaded = true;
        } catch (IOException e) {
            throw new RuntimeException("Unable to load config.properties file", e);
        }
    }

    public static String get(String key) {
        loadConfig();
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Missing config key: " + key);
        }
        return value.trim();
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}