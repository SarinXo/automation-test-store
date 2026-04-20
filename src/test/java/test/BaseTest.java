package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.DriverManager;

import java.time.Duration;

import static config.AppConfig.WAIT_TIMEOUT;

public class BaseTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    public void setup() {
        driver = DriverManager.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT));
    }

    @AfterEach
    public void teardown() {
        DriverManager.quitDriver();
    }

}
