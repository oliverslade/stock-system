package broker.models.stocks;

import broker.models.trades.TradeLedger;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public abstract class Stock {

  private String symbol;

  private BigDecimal lastDividend;

  private BigDecimal parValue;

  private BigDecimal price;

  private List<TradeLedger> tradeLedger;

  Stock(
      final String symbol,
      final BigDecimal lastDividend,
      final BigDecimal parValue,
      final BigDecimal price) {
    this.symbol = symbol;
    this.lastDividend = lastDividend;
    this.parValue = parValue;
    this.price = price;
    this.tradeLedger = new ArrayList<>();
  }

  public void addNewTrade(final TradeLedger tradeLedger) {
    this.tradeLedger.add(tradeLedger);
  }
}
