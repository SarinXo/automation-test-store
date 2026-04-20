package config;

import property.YmlConfig;
import util.YamlReader;

public class AppConfig {

    public final static String MAIN_PAGE_URL;
    public final static String CART_PAGE_URL;
    public final static String CATEGORY_PAGE_URL;
    public final static Integer WAIT_TIMEOUT;

    static {
        YamlReader yamlReader = new YamlReader();
        YmlConfig ymlConfig = yamlReader.readConfig();

        MAIN_PAGE_URL = ymlConfig.getApp().getPageUrls().get("MainPage");
        CART_PAGE_URL = ymlConfig.getApp().getPageUrls().get("CartPage");
        CATEGORY_PAGE_URL = ymlConfig.getApp().getPageUrls().get("CategoryPage");
        WAIT_TIMEOUT = ymlConfig.getApp().getTimeout();
    }

}
