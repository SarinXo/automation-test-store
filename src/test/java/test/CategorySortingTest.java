package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import page.search.ProductSearchDto;
import page.search.SearchPage;
import page.search.SortStrategy;

import java.util.List;

import static config.AppConfig.CATEGORY_PAGE_URL;
import static org.assertj.core.api.Assertions.assertThat;

public class CategorySortingTest extends BaseTest {

    SearchPage searchPage;

    @BeforeEach
    void setUp() {
        driver.get(CATEGORY_PAGE_URL);
        searchPage = new SearchPage(driver);
    }

    @ParameterizedTest(name = "Сортировка {0}")
    @EnumSource(SortStrategy.class)
    @DisplayName("Проверка сортировки товаров в категории (продуктов должно быть больше 4)")
    void testProductSorting(SortStrategy strategy) {
        List<ProductSearchDto> products = searchPage
                .selectSortOption(strategy.sortOption())
                .getProducts();

        assertThat(products.size())
                .as("Проверка нижней границы предметов")
                .isGreaterThanOrEqualTo(4);
        assertThat(products)
                .as("Проверка сортировки")
                .isSortedAccordingTo(strategy.getSortBy());
    }

}
