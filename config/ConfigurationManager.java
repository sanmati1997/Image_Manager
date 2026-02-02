package Project_imagemanager.config;

import java.io.*;
import java.util.Properties;

// Manages application configuration settings
public class ConfigurationManager {

    private static ConfigurationManager instance;
    private Properties properties;
    private String configFilePath;

    private static final int DEFAULT_THUMBNAIL_SIZE = 100;
    private static final String DEFAULT_OUTPUT_FORMAT = "PNG";

    private ConfigurationManager() {
        properties = new Properties();
        configFilePath = System.getProperty("user.home") + "/.imagemanager.properties";
        loadConfiguration();
    }

    // Gets the single instance (Singleton pattern)
    public static synchronized ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }

    // Loads configuration from file
    private void loadConfiguration() {
        File configFile = new File(configFilePath);

        if (configFile.exists()) {
            try (FileInputStream fis = new FileInputStream(configFile)) {
                properties.load(fis);
            } catch (IOException e) {
                setDefaultConfiguration();
            }
        } else {
            setDefaultConfiguration();
        }
    }

    //Sets default configuration values
    private void setDefaultConfiguration() {
        properties.setProperty("thumbnail.size", String.valueOf(DEFAULT_THUMBNAIL_SIZE));
        properties.setProperty("default.output.format", DEFAULT_OUTPUT_FORMAT);
        saveConfiguration();
    }

    // Saves configuration to file
    private void saveConfiguration() {
        try (FileOutputStream fos = new FileOutputStream(configFilePath)) {
            properties.store(fos, "Image Manager Configuration");
        } catch (IOException e) {
            System.err.println("Failed to save configuration: " + e.getMessage());
        }
    }

    //Gets the thumbnail size setting
    public int getThumbnailSize() {
        String size = properties.getProperty("thumbnail.size", String.valueOf(DEFAULT_THUMBNAIL_SIZE));
        try {
            return Integer.parseInt(size);
        } catch (NumberFormatException e) {
            return DEFAULT_THUMBNAIL_SIZE;
        }
    }

    // Gets the default output format
    public String getDefaultOutputFormat() {
        return properties.getProperty("default.output.format", DEFAULT_OUTPUT_FORMAT);
    }
}