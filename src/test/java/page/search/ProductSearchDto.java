package page.search;

import java.math.BigDecimal;

public record ProductSearchDto(
        String name,
        BigDecimal price
) {

}
