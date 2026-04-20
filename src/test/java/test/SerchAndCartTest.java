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

import static config.AppConfig.CART_PAGE_URL;
import static config.AppConfig.MAIN_PAGE_URL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.within;
import static page.search.SortOption.PRODUCT_NAME_ASC;

public class SerchAndCartTest extends BaseTest {

    private static final String SEARCH_TERM = "shirt";

    @BeforeEach
    void setUp() {
        driver.get(CART_PAGE_URL);
    }

    @Test
    @DisplayName("Проверка поиска, сортировки и манипуляций с корзиной")
    void testSearchAndCartLogic() {
        SearchPage searchPage = new SearchPage(driver);
        CartPage cart = new CartPage(driver);

        List<CartItemDto> cartItems = cart.getCartItems();

        driver.navigate().to(MAIN_PAGE_URL);
        addProductToCartBySearch(searchPage, 1);
        addProductToCartBySearch(searchPage, 2);

        driver.navigate().to(CART_PAGE_URL);
        List<CartItemDto> cartItemsAfterAdd = cart.getCartItems();

        multiplyCheapestItemInCart(cart);

        BigDecimal expectedTotal = cart.countControlSum();
        BigDecimal actualTotal = cart.getSubTotal();

        assertThat(cartItems.size())
                .as("Изначально в корзине ничего нет")
                .isEqualTo(0);

        assertThat(cartItemsAfterAdd.size())
                .as("В корзине появилось 2 предмета")
                .isEqualTo(2);

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

        driver.navigate().to(MAIN_PAGE_URL);
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

}

