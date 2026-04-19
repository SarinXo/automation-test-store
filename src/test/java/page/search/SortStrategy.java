package page.search;

import java.util.Comparator;

import static page.search.SortOption.PRODUCT_NAME_ASC;
import static page.search.SortOption.PRODUCT_NAME_DESC;
import static page.search.SortOption.PRODUCT_PRICE_ASC;
import static page.search.SortOption.PRODUCT_PRICE_DESC;

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
