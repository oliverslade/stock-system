package broker.models.trades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeLedger {

  private String stockSymbol;

  private Date timestamp;

  private BigInteger quantity;

  private BuySellEnum indicator;

  private BigDecimal price;

}
