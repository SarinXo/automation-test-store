package page.cart;

import java.math.BigDecimal;

public record CartItemDto(
        String name,
        BigDecimal unitPrice,
        Integer quantity,
        BigDecimal total
) {

}
