package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import page.categorypage.CategoryPage;
import page.categorypage.ProductDto;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CategorySortingTest extends BaseTest {

    CategoryPage categoryPage;

    @BeforeEach
    void setUp() {
        driver.get("https://automationteststore.com/index.php?rt=product/category&path=68_70");
        categoryPage = new CategoryPage(driver);
    }

    @ParameterizedTest(name = "Сортировка {0}")
    @EnumSource(SortStrategy.class)
    @DisplayName("Проверка сортировки товаров в категории (продуктов должно быть больше 4)")
    void testProductSorting(SortStrategy strategy) {
        List<ProductDto> products = categoryPage
                .selectSortOption(strategy.sortOption())
                .getProducts();

        assertThat(products.size()).isGreaterThanOrEqualTo(4);
        assertThat(products).isSortedAccordingTo(strategy.getSortBy());
    }

    public enum SortStrategy {
        NAME_ASC("Name A-Z", "pd.name-ASC", Comparator.comparing(ProductDto::name)),
        NAME_DESC("Name Z-A", "pd.name-DESC", Comparator.comparing(ProductDto::name, Comparator.reverseOrder())),
        PRICE_ASC("Price Low-High", "p.price-ASC", Comparator.comparing(ProductDto::price)),
        PRICE_DESC("Price High-Low", "p.price-DESC", Comparator.comparing(ProductDto::price, Comparator.reverseOrder()));

        private final String reportName;
        private final String htmlValue;
        private final Comparator<ProductDto> sortBy;

        SortStrategy(String reportName, String htmlValue, Comparator<ProductDto> sortBy) {
            this.reportName = reportName;
            this.htmlValue = htmlValue;
            this.sortBy = sortBy;
        }

        public String getReportName() {
            return reportName;
        }

        public String sortOption() {
            return htmlValue;
        }

        public Comparator<ProductDto> getSortBy() {
            return sortBy;
        }

        @Override
        public String toString() {
            return reportName;
        }
    }

}
