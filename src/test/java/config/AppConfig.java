package config;

import property.AppProperty;
import property.YmlConfig;
import util.YamlReader;

public class AppConfig {

    private final static YmlConfig properties;

    static {
        YamlReader yamlReader = new YamlReader();
        properties = yamlReader.readConfig();
    }

    public static YmlConfig getProperties() {
        return properties;
    }

}
