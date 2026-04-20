package mapper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import page.search.ProductSearchDto;

import java.math.BigDecimal;

import static util.MoneyUtils.parsePrice;

public class ProductMapper {

    public static ProductSearchDto webElementToProductDto(WebElement element) {
        //опечатка на странице
        String name = element.findElement(By.className("prdocutname")).getAttribute("title");
        String priceText = element.findElement(By.className("oneprice")).getText();

        BigDecimal price = parsePrice(priceText);

        return new ProductSearchDto(name, price);
    }

}
