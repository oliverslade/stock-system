package broker.utils;

import broker.models.stocks.CommonStock;
import broker.models.stocks.PreferredStock;

import java.math.BigDecimal;

public class TestUtils {

  public static final String PREFIX_STOCK_SYMBOL = "SS";

  public static final String TEST_COMMON_STOCK = PREFIX_STOCK_SYMBOL + "C";

  public static final String TEST_PREFERRED_STOCK = PREFIX_STOCK_SYMBOL + "P";

  /** @return a random bigDecimal between 0 to 100,000. */
  public static BigDecimal getRandomBigDecimal() {
    return new BigDecimal(Math.random() * 100000);
  }

  /** @return a default CommonStock */
  public static CommonStock getDefaultCommonStock() {
    return new CommonStock(TEST_COMMON_STOCK, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
  }

  /** @return a default PreferredStock */
  public static PreferredStock getDefaultPreferredStock() {
    return new PreferredStock(
        TEST_PREFERRED_STOCK, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
  }
}
