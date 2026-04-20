package page.cart;

import mapper.CartItemMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import page.BasePage;

import java.math.BigDecimal;
import java.util.List;

import static util.MoneyUtils.parsePrice;

public class CartPage extends BasePage {

    private final By cartRows = By.cssSelector(".product-list table.table-striped.table-bordered tbody tr:not(:first-child)");
    private final By updateButton = By.id("cart_update");
    private final By subTotalElement = By.cssSelector("#totals_table tbody tr:first-child td:nth-child(2)");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public List<CartItemDto> getCartItems() {
        List<WebElement> rows = driver.findElements(cartRows);

        return rows.stream()
                .map(CartItemMapper::webElementToCartItemDto)
                .toList();
    }

    public CartPage deleteItemByIndex(int index) {
        List<WebElement> rows = driver.findElements(cartRows);
        if (index >= rows.size()) {
            throw new IndexOutOfBoundsException("Индекс " + index + " вне границ. Всего товаров: " + rows.size());
        }
        WebElement row = rows.get(index);

        WebElement deleteButton = row.findElement(
                By.xpath(".//td[7]//a[contains(@class, 'btn-default')]")
        );
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();

        return this;
    }

    public CartPage updateProductQuantityByIndex(int rowIndex, int newQuantity) {
        wait.until(ExpectedConditions.presenceOfElementLocated(cartRows));
        List<WebElement> rows = driver.findElements(cartRows);

        if (rowIndex < 1 || rowIndex > rows.size()) {
            throw new IndexOutOfBoundsException("Значение " + rowIndex + " Вне рамок. Всего товаров: " + rows.size());
        }

        WebElement quantityInput = rows.get(rowIndex - 1).findElement(By.xpath(".//input[@type='text']"));
        quantityInput.clear();
        quantityInput.sendKeys(String.valueOf(newQuantity));

        clickUpdate();

        return this;
    }

    public CartPage clickUpdate() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(updateButton));
        btn.click();
        wait.until(ExpectedConditions.stalenessOf(btn));

        return this;
    }

    public BigDecimal countControlSum() {
        List<CartItemDto> updatedItems = getCartItems();

        return updatedItems.stream()
                .map(CartItemDto::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getSubTotal() {
        String text = wait.until(ExpectedConditions.visibilityOfElementLocated(subTotalElement)).getText();
        return parsePrice(text);
    }

}

