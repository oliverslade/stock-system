package broker.models.stocks;

import broker.models.trades.TradeLedger;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.Data;

@Data
@Entity
public abstract class Stock {

  @Id private String symbol;

  private BigDecimal lastDividend;

  private BigDecimal parValue;

  private BigDecimal price;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Transient
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
