package page.product;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import page.BasePage;
import page.cart.CartPage;

public class ProductPage extends BasePage {

    private final By quantityInput = By.id("product_quantity");
    private final By addToCartButton = By.cssSelector("a.cart");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public ProductPage setQuantity(Integer quantity) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(quantityInput));

        input.clear();
        input.sendKeys(quantity.toString());

        return this;
    }

    public CartPage addProductToCart() {
        click(addToCartButton);

        return new CartPage(driver);
    }

}
