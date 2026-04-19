package page.search;

public enum SortOption {

    DATE_MODIFIED_ASC("date_modified-ASC"),
    DATE_MODIFIED_DESC("date_modified-DESC"),

    PRODUCT_NAME_ASC("pd.name-ASC"),
    PRODUCT_NAME_DESC("pd.name-DESC"),

    PRODUCT_PRICE_ASC("p.price-ASC"),
    PRODUCT_PRICE_DESC("p.price-DESC"),

    RATING_ASC("rating-ASC"),
    RATING_DESC("rating-DESC");

    private final String value;

    SortOption(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}