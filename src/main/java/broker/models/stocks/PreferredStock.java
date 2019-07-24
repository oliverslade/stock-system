package broker.models.stocks;

import java.math.BigDecimal;

public class PreferredStock extends Stock {

  private BigDecimal fixedDividend;

  public PreferredStock(
      final String symbol,
      final BigDecimal lastDividend,
      final BigDecimal parValue,
      final BigDecimal price,
      final BigDecimal fixedDividend) {
    super(symbol, lastDividend, parValue, price);
    this.fixedDividend = fixedDividend;
  }

  public BigDecimal getFixedDividend() {
    return this.fixedDividend;
  }

  public void setFixedDividend(final BigDecimal fixedDividend) {
    this.fixedDividend = fixedDividend;
  }
}
