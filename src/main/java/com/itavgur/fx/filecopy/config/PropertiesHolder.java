package com.itavgur.fx.filecopy.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PropertiesHolder {

    private static final String CONFIG_FILE = "config.properties";

    private String sourceFile;

    private String destinationFile;

    public static PropertiesHolder getInstance() {

        return PropertiesHolderInnerClass.instance;
    }

    private static class PropertiesHolderInnerClass {

        private static final PropertiesHolder instance = new PropertiesHolder();

        static {
            Properties properties = new Properties();

            try (InputStream input = new FileInputStream(ClassLoader.getSystemResource(CONFIG_FILE).getPath())) {
                properties.load(input);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            instance.sourceFile = properties.getProperty("source.file.path");
            instance.destinationFile = properties.getProperty("destination.file.path");
        }


    }
}
