package mapper;

import org.assertj.core.util.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import page.categorypage.ProductDto;

import java.math.BigDecimal;

public class ProductMapper {

    public static ProductDto webElementToProductDto(WebElement element, Integer serialNumber) {
        String name = element.findElement(By.className("prdocutname")).getAttribute("title");//опечатка на странице
        String priceText = element.findElement(By.className("oneprice"))
                .getText()
                .replace("$", "")
                .replace(",", "");

        if (Strings.isNullOrEmpty(priceText)) {
            throw new RuntimeException("Цена в товаре не указана!");
        }
        BigDecimal price = new BigDecimal(priceText);

        return new ProductDto(serialNumber, name, price);
    }

}
