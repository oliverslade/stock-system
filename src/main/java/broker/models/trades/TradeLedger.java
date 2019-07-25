package broker.models.trades;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
