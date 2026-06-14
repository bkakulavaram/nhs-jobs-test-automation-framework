package com.nhs.automation.framework.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    static {
        try {
            InputStream file = ConfigReader.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties");
            if (file == null) {
                throw new RuntimeException("config.properties not found");
            }

            properties = new Properties();
            properties.load(file);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to load config.properties",
                    e);
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("baseUrl");
    }

    public static String getProperty(String property) {
        return properties.getProperty(property);
    }

    public static String getLocationNotFoundUrl() {

        return properties.getProperty("locationNotFound");
    }
    public static String getTooManyLocationsUrl() {

        return properties.getProperty("tooManyLocations");
    }

}