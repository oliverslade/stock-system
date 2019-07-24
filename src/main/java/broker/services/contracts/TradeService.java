package broker.services.contracts;

import broker.models.stocks.Stock;
import broker.models.trades.BuySellEnum;
import broker.models.trades.TradeRecord;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public interface TradeService {

  /**
   * Records a trade, with time stamp, quantity of shares, buy or sell indicator and traded price.
   */
  void recordTrade(
      String symbol, Date timestamp, BigInteger quantity, BuySellEnum indicator, BigDecimal price);

  /**
   * Gets the trade records of the given stock in the last given minutes.
   *
   * @param stock the {@link Stock} to search
   * @param minutes the range of the time to search
   * @return a {@link TradeRecord}s matching the search criterion
   */
  List<TradeRecord> getTradeRecordsByTime(Stock stock, int minutes);
}
