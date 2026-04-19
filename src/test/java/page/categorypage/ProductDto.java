package page.categorypage;

import java.math.BigDecimal;

public record ProductDto(
        Integer serialNumber,
        String name,
        BigDecimal price
) {

}
