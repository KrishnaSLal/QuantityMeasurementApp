package com.app.quantitymeasurement.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ApplicationConfig {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream inputStream =
                     ApplicationConfig.class.getClassLoader().getResourceAsStream("application.properties")) {

            if (inputStream != null) {
                PROPERTIES.load(inputStream);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load application.properties", e);
        }
    }

    private ApplicationConfig() {
    }

    public static String getProperty(String key, String defaultValue) {
        return System.getProperty(key, PROPERTIES.getProperty(key, defaultValue));
    }

    public static int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key, String.valueOf(defaultValue));
        return Integer.parseInt(value);
    }

    public static String getRepositoryType() {
        return getProperty("repository.type", "cache");
    }

    public static String getDbDriver() {
        return getProperty("db.driver", "org.h2.Driver");
    }

    public static String getDbUrl() {
        return getProperty("db.url", "jdbc:h2:./data/quantitydb;AUTO_SERVER=TRUE");
    }

    public static String getDbUsername() {
        return getProperty("db.username", "sa");
    }

    public static String getDbPassword() {
        return getProperty("db.password", "");
    }

    public static int getInitialPoolSize() {
        return getIntProperty("db.pool.initialSize", 2);
    }

    public static int getMaxPoolSize() {
        return getIntProperty("db.pool.maxSize", 5);
    }
}