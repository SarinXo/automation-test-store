package page.categorypage;

import io.qameta.allure.Step;
import mapper.ProductMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import page.BasePage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CategoryPage extends BasePage {

    private final By sortDropdown = By.id("sort");
    private final By productCardsSelector = By.cssSelector(".thumbnails.grid .col-md-3");

    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    @Step("Выбрать тип сортировки: {0}")
    public CategoryPage selectSortOption(String optionValue) {
        WebElement dropdownElement = driver.findElement(sortDropdown);
        Select select = new Select(dropdownElement);
        select.selectByValue(optionValue);

        wait.until(d -> d.findElement(sortDropdown).getAttribute("value").equals(optionValue));

        driver.findElement(sortDropdown);// подождать чтобы стало кликабельным
        return this;
    }

    @Step("Получить все товары")
    public List<ProductDto> getProducts() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productCardsSelector));

        List<WebElement> cards = driver.findElements(productCardsSelector);

        return IntStream.range(0, cards.size())
                .mapToObj(i -> ProductMapper.webElementToProductDto(cards.get(i), i))
                .collect(Collectors.toList());
    }

}
