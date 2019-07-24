package broker.utils;

import broker.models.stocks.CommonStock;
import broker.models.stocks.PreferredStock;
import java.math.BigDecimal;

/**
 * A utility class for tests.
 *
 * @author Oliver Slade
 */
public class TestUtils {

  /** The symbol of the stock. */
  public static final String PREFIX_STOCK_SYMBOL = "SS";

  /** The symbol of the common stock. */
  public static final String TEST_COMMON_STOCK = PREFIX_STOCK_SYMBOL + "C";

  /** The symbol of the preferred stock. */
  public static final String TEST_PREFERRED_STOCK = PREFIX_STOCK_SYMBOL + "P";

  /**
   * Gets a random {@link BigDecimal} between 0 to 100,000.
   *
   * @return a {@link BigDecimal}
   */
  public static BigDecimal getRamdamBigDecimal() {

    return new BigDecimal(Math.random() * 100000);
  }

  /**
   * Gets a default {@link CommonStock} for tests.
   *
   * @return a default {@link CommonStock}
   */
  public static CommonStock getDefaultCommonStock() {
    return new CommonStock(TEST_COMMON_STOCK, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
  }

  /**
   * Gets a default {@link PreferredStock} for tests.
   *
   * @return a default {@link PreferredStock}
   */
  public static PreferredStock getDefaultPreferredStock() {
    return new PreferredStock(
        TEST_PREFERRED_STOCK, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
  }
}
