package broker.models.stocks;

import java.math.BigDecimal;

public class CommonStock extends Stock {

  public CommonStock(
      final String symbol,
      final BigDecimal lastDividend,
      final BigDecimal parValue,
      final BigDecimal price) {
    super(symbol, lastDividend, parValue, price);
  }
}
