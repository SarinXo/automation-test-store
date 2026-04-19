package test;

import config.AppConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.DriverManager;

import java.time.Duration;

public class BaseTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    public void setup() {
        driver = DriverManager.getDriver();
        Integer timeout = AppConfig.getProperties().getApp().getTimeout();
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    @AfterEach
    public void teardown() {
        DriverManager.quitDriver();
    }

}
