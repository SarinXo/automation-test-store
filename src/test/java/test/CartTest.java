package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.MainPage;
import page.cart.CartItemDto;
import page.cart.CartPage;
import util.RandomUtils;

import java.math.BigDecimal;
import java.util.List;

import static config.AppConfig.CART_PAGE_URL;
import static config.AppConfig.MAIN_PAGE_URL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.within;

public class CartTest extends BaseTest {

    MainPage mainPage;

    @BeforeEach
    void setUp() {
        driver.get(MAIN_PAGE_URL);
        mainPage = new MainPage(driver);
    }

    @Test
    @DisplayName("Проверка корзины 5 рандомных элемента + удаление четных")
    void testProductSorting() {
        List<String> itemCardIds = mainPage.getRandomFiveUniqueProducts();
        itemCardIds.forEach(el -> {
            mainPage.clickProductButton(el)
                    .setQuantity(RandomUtils.number(5))
                    .addProductToCart();
            driver.navigate().to(MAIN_PAGE_URL);
        });

        CartPage cart = new CartPage(driver);
        driver.navigate().to(CART_PAGE_URL);
        //четные элементы
        List<CartItemDto> cartItems = cart.getCartItems();
        cart.deleteEvenItems();
        List<CartItemDto> cartItemsAfterDelete = cart.getCartItems();

        BigDecimal expectedTotal = cart.countControlSum();
        BigDecimal actualTotal = cart.getSubTotal();

        assertThat(cartItemsAfterDelete.size())
                .as("Удалено 2 элемента")
                .isEqualTo(3);
        assertThat(cartItemsAfterDelete.contains(cartItems.get(1)))
                .as("Не содержит 2 элемента")
                .isFalse();
        assertThat(cartItemsAfterDelete.contains(cartItems.get(3)))
                .as("Не содержит 4 элемента")
                .isFalse();
        assertThat(actualTotal)
                .as("Проверка итоговой суммы в корзине")
                .isCloseTo(expectedTotal, within(BigDecimal.valueOf(0.001)));
    }

}
