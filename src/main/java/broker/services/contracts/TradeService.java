package broker.services.contracts;

import broker.models.stocks.Stock;
import broker.models.trades.BuySellEnum;
import broker.models.trades.TradeLedger;

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
   * Gets the trade records of the given stock in the last 15 minutes.
   *
   * @param stock the stock to search
   * @return the TradeLedger for the last 15 minutes
   */
  List<TradeLedger> getLast15MinutesTrades(Stock stock);
}
