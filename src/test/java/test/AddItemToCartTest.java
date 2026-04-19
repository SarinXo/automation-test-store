package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.cart.CartItemDto;
import page.cart.CartPage;
import page.product.ProductPage;
import page.search.SearchPage;
import util.RandomUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.within;
import static page.search.SortOption.PRODUCT_NAME_ASC;

public class AddItemToCartTest extends BaseTest {

    @BeforeEach
    void setUp() {
        driver.get("https://automationteststore.com");
    }

    @Test
    @DisplayName("Проверка поиска, сортировки и манипуляций с корзиной")
    void testSearchAndCartLogic() {
        SearchPage search = new SearchPage(driver);

        search.searchFor("shirt")
                .selectSortOption(PRODUCT_NAME_ASC)
                .clickProductButton(1)
                .setQuantity(RandomUtils.number(5))
                .addProductToCart();

        driver.get("https://automationteststore.com");
        SearchPage searchPageAfterReturn = new SearchPage(driver);

        searchPageAfterReturn.searchFor("shirt")
                .selectSortOption(PRODUCT_NAME_ASC)
                .clickProductButton(2)
                .setQuantity(RandomUtils.number(5))
                .addProductToCart();

        CartPage cart = new CartPage(driver);

        List<CartItemDto> cartItems = cart.getCartItems();

        CartItemDto cheapestItem = cartItems.stream()
                .min(Comparator.comparing(CartItemDto::unitPrice))
                .orElseThrow(() -> new RuntimeException("Корзина пуста!"));

        int cheapestItemIndex = cartItems.indexOf(cheapestItem) + 1;
        int newCheapestItemQuantity = cheapestItem.quantity() * 2;

        cart.updateProductQuantityByIndex(cheapestItemIndex, newCheapestItemQuantity);

        List<CartItemDto> updatedItems = cart.getCartItems();

        BigDecimal expectedTotal = updatedItems.stream()
                .map(CartItemDto::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal actualTotal = cart.getSubTotal();

        assertThat(actualTotal)
                .as("Проверка итоговой суммы в корзине")
                .isCloseTo(expectedTotal, within(BigDecimal.valueOf(0.001)));
    }
}

