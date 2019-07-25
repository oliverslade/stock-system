package broker.utils;

import broker.models.stocks.CommonStock;
import broker.models.stocks.PreferredStock;
import java.math.BigDecimal;

public class TestUtils {

  public static final String COMMON_STOCK = "SSC";

  public static final String PREFERRED_STOCK = "SSP";

  public static BigDecimal getRandomBigDecimal() {
    return new BigDecimal(Math.random() * 100000);
  }

  public static CommonStock getDefaultCommonStock() {
    return new CommonStock(COMMON_STOCK, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
  }

  public static PreferredStock getDefaultPreferredStock() {
    return new PreferredStock(
        PREFERRED_STOCK, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
  }
}
