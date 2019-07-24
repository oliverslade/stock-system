package broker.models.stocks;

import broker.models.trades.TradeRecord;
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

  private List<TradeRecord> tradeRecords;

  Stock(
      final String symbol,
      final BigDecimal lastDividend,
      final BigDecimal parValue,
      final BigDecimal price) {
    this.symbol = symbol;
    this.lastDividend = lastDividend;
    this.parValue = parValue;
    this.price = price;
    this.tradeRecords = new ArrayList<>();
  }

  public void addTradeRecord(final TradeRecord tradeRecords) {
    this.tradeRecords.add(tradeRecords);
  }
}
