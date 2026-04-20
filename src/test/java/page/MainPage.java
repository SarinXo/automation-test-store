package page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import page.product.ProductPage;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends BasePage {

    private final By allProductCardsLocator = By.xpath(
            "//section[@id='featured' or @id='latest' or @id='bestseller' or @id='special']//div[contains(@class, 'col-md-3')]"
    );

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Step("Зайти на страницу товара")
    public ProductPage clickProductButton(String productId) {
        By productLinkLocator = By.xpath("//a[contains(@href, 'product_id=" + productId + "')]");

        WebElement productLink = wait.until(ExpectedConditions.presenceOfElementLocated(productLinkLocator));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", productLink);

        click(productLink);

        return new ProductPage(driver);
    }

    @Step("Получить все карточки товаров")
    public List<WebElement> getAllProductsFromSpecifiedSections() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(allProductCardsLocator));

        return driver.findElements(allProductCardsLocator);
    }

    @Step("Выбрать 5 карточек с товаром")
    public List<String> getRandomFiveUniqueProducts() {
        List<WebElement> allProducts = getAllProductsFromSpecifiedSections();

        if (allProducts.size() < 5) {
            throw new IllegalStateException("Не достаточно элементов, чтобы выбрать 5. Найдено: " + allProducts.size());
        }

        List<WebElement> randomizedList = new ArrayList<>(allProducts);

        return randomizedList.stream()
                .limit(5)
                .map(el -> el.findElement(By.cssSelector(".productcart")).getAttribute("data-id"))
                .toList();
    }

}
