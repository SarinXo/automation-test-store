package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import page.search.SearchPage;
import page.search.ProductSearchDto;
import page.search.SortOption;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static page.search.SortOption.PRODUCT_NAME_ASC;
import static page.search.SortOption.PRODUCT_NAME_DESC;
import static page.search.SortOption.PRODUCT_PRICE_ASC;
import static page.search.SortOption.PRODUCT_PRICE_DESC;

public class CategorySortingTest extends BaseTest {

    SearchPage searchPage;

    @BeforeEach
    void setUp() {
        driver.get("https://automationteststore.com/index.php?rt=product/category&path=68_70");
        searchPage = new SearchPage(driver);
    }

    @ParameterizedTest(name = "Сортировка {0}")
    @EnumSource(SortStrategy.class)
    @DisplayName("Проверка сортировки товаров в категории (продуктов должно быть больше 4)")
    void testProductSorting(SortStrategy strategy) {
        List<ProductSearchDto> products = searchPage
                .selectSortOption(strategy.sortOption())
                .getProducts();

        assertThat(products.size()).isGreaterThanOrEqualTo(4);
        assertThat(products).isSortedAccordingTo(strategy.getSortBy());
    }

    public enum SortStrategy {
        NAME_ASC("Name A-Z", PRODUCT_NAME_ASC, Comparator.comparing(ProductSearchDto::name)),
        NAME_DESC("Name Z-A", PRODUCT_NAME_DESC, Comparator.comparing(ProductSearchDto::name, Comparator.reverseOrder())),
        PRICE_ASC("Price Low-High", PRODUCT_PRICE_ASC, Comparator.comparing(ProductSearchDto::price)),
        PRICE_DESC("Price High-Low", PRODUCT_PRICE_DESC, Comparator.comparing(ProductSearchDto::price, Comparator.reverseOrder()));

        private final String reportName;
        private final SortOption htmlValue;
        private final Comparator<ProductSearchDto> sortBy;

        SortStrategy(String reportName, SortOption htmlValue, Comparator<ProductSearchDto> sortBy) {
            this.reportName = reportName;
            this.htmlValue = htmlValue;
            this.sortBy = sortBy;
        }

        public String getReportName() {
            return reportName;
        }

        public SortOption sortOption() {
            return htmlValue;
        }

        public Comparator<ProductSearchDto> getSortBy() {
            return sortBy;
        }

        @Override
        public String toString() {
            return reportName;
        }
    }

}
