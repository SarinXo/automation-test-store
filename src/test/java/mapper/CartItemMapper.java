package mapper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import page.cart.CartItemDto;

import java.math.BigDecimal;

import static util.MoneyUtils.parsePrice;

public class CartItemMapper {

    public static CartItemDto webElementToCartItemDto(WebElement element) {
        String name = element.findElement(By.xpath("./td[2]//a")).getText().trim();
        BigDecimal unitPrice = parsePrice(element.findElement(By.xpath("./td[4]")).getText());
        Integer quantity = Integer.parseInt(element.findElement(By.xpath("./td[5]//input")).getAttribute("value"));
        BigDecimal total = parsePrice(element.findElement(By.xpath("./td[6]")).getText());

        return new CartItemDto(name, unitPrice, quantity, total);
    }

}
