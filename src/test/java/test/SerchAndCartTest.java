package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.cart.CartItemDto;
import page.cart.CartPage;
import page.search.SearchPage;
import util.RandomUtils;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.within;
import static page.search.SortOption.PRODUCT_NAME_ASC;

public class SerchAndCartTest extends BaseTest {

    private static final String SEARCH_TERM = "shirt";
    private static final String BASE_URL = "https://automationteststore.com";

    @BeforeEach
    void setUp() {
        driver.get(BASE_URL);
    }

    @Test
    @DisplayName("Проверка поиска, сортировки и манипуляций с корзиной")
    void testSearchAndCartLogic() {
        SearchPage search = new SearchPage(driver);
        CartPage cart = new CartPage(driver);

        addProductToCartBySearch(search, 1);
        addProductToCartBySearch(search, 2);

        multiplyCheapestItemInCart(cart);

        BigDecimal expectedTotal = countAmount(cart);
        BigDecimal actualTotal = cart.getSubTotal();

        assertThat(actualTotal)
                .as("Проверка итоговой суммы в корзине")
                .isCloseTo(expectedTotal, within(BigDecimal.valueOf(0.001)));
    }

    private void addProductToCartBySearch(SearchPage searchPage, int productIndex) {
        searchPage.searchFor(SEARCH_TERM)
                .selectSortOption(PRODUCT_NAME_ASC)
                .clickProductButton(productIndex)
                .setQuantity(RandomUtils.number(5))
                .addProductToCart();

        driver.navigate().to(BASE_URL);
    }

    private void multiplyCheapestItemInCart(CartPage cart) {
        List<CartItemDto> cartItems = cart.getCartItems();

        CartItemDto cheapestItem = cartItems.stream()
                .min(Comparator.comparing(CartItemDto::unitPrice))
                .orElseThrow(() -> new RuntimeException("Корзина пуста!"));

        int cheapestItemIndex = cartItems.indexOf(cheapestItem) + 1;
        int newCheapestItemQuantity = cheapestItem.quantity() * 2;

        cart.updateProductQuantityByIndex(cheapestItemIndex, newCheapestItemQuantity);
    }

    private BigDecimal countAmount(CartPage cart) {
        List<CartItemDto> updatedItems = cart.getCartItems();

        return updatedItems.stream()
                .map(CartItemDto::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}

