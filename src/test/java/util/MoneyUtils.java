package util;

import org.assertj.core.util.Strings;

import java.math.BigDecimal;

public class MoneyUtils {

    public static BigDecimal parsePrice(String priceText) {

        String doubleNumber = priceText.replaceAll("[^\\d.,]", "");

        if (Strings.isNullOrEmpty(doubleNumber)) {
            throw new RuntimeException("Строка с числом пустая!");
        }

        return new BigDecimal(doubleNumber);
    }
}
