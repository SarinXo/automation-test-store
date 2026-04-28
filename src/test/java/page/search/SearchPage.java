package page.search;

import io.qameta.allure.Step;
import mapper.ProductMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import page.BasePage;
import page.product.ProductPage;

import java.util.List;

public class SearchPage extends BasePage {

    private final By searchInput = By.id("filter_keyword");
    private final By searchButton = By.cssSelector(".button-in-search");
    private final By sortDropdown = By.id("sort");
    private final By productCardsSelector = By.cssSelector(".thumbnails.grid .col-md-3");
    private final By productLink = By.className("prdocutname");

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    @Step("Ввести в поиск: {0}")
    public SearchPage searchFor(String keyword) {
        writeText(searchInput, keyword);
        click(searchButton);

        return this;
    }

    @Step("Выбрать тип сортировки: {0}")
    public SearchPage selectSortOption(SortOption sortOption) {
        WebElement dropdownElement = driver.findElement(sortDropdown);
        Select select = new Select(dropdownElement);
        select.selectByValue(sortOption.value());

        wait.until(d -> d.findElement(sortDropdown).getAttribute("value").equals(sortOption.value()));

        driver.findElement(sortDropdown);
        return this;
    }

    @Step("Зайти на страницу товара (индекс {0})")
    public ProductPage clickProductButton(int index) {
        List<WebElement> cards = driver.findElements(productCardsSelector);
        if (index < 0 || index >= cards.size()) {
            throw new IndexOutOfBoundsException("Индекс " + index + " вне границ. Всего товаров: " + cards.size());
        }

        WebElement targetCard = cards.get(index);
        WebElement linkToProduct = targetCard.findElement(productLink);
        click(linkToProduct);

        return new ProductPage(driver);
    }

    @Step("Получить все товары")
    public List<ProductSearchDto> getProducts() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productCardsSelector));

        return driver.findElements(productCardsSelector)
                .stream()
                .map(ProductMapper::webElementToProductDto)
                .toList();
    }

}

