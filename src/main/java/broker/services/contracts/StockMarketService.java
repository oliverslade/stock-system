package broker.services.contracts;

import broker.models.stocks.Stock;
import broker.models.trades.BuySellEnum;
import broker.models.trades.TradeRecord;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface StockMarketService {

  /**
   * Registers a stock to the stock market.
   *
   * @param stock the stock to be registered
   */
  void registerStock(Stock stock);

  /**
   * Deregister a stock from this market service.
   *
   * @param stockSymbol the symbol of the stock to be unregistered
   */
  void unregisterStock(String stockSymbol);

  /**
   * Returns the stocks available in the stock market
   *
   * @return the stockMap a {@link HashMap} containing the stocks managed in this service
   */
  Map<String, Stock> getStockMap();

  /**
   * Calculates the dividend yield of a given stock based on the price at the time of calculation.
   * The results precision is 3 and uses {@link BigDecimal#ROUND_HALF_EVEN}.
   *
   * @param symbol the symbol of the stock
   * @param price the price to be used in the calculation
   */
  BigDecimal getDividendYield(String symbol, BigDecimal price);

  /**
   * Calculates the P/E Ratio of a given stock based on the price at the time of calculation. The
   * results precision is 3 and uses {@link BigDecimal#ROUND_HALF_EVEN}.
   *
   * @param symbol the symbol of the stock
   * @param price the trade price
   */
  BigDecimal getPERatio(String symbol, BigDecimal price);

  /**
   * Records a trade, with time stamp, quantity of shares, buy or sell indicator and traded price.
   */
  void recordTrade(
      String symbol, Date timestamp, BigInteger quantity, BuySellEnum indicator, BigDecimal price);

  /**
   * Gets the volume weighted stock price based on the trades in the 15 minutes. The result keeps
   * precision scale is 0 and applies {@link BigDecimal#ROUND_HALF_EVEN}.
   *
   * @param symbol the symbol of the stock
   * @return the VolumeWeightedStockPrice in the last in 15 minutes.
   */
  BigDecimal getVolumeWeightedStockPrice(String symbol);

  /**
   * Calculates the GBCE all share index value based on the prices of all the stocks in the market
   * service. The result precision is 0 and applies {@link BigDecimal#ROUND_HALF_EVEN}.
   *
   * @return the GBCE all share index value, with 2 scale.
   */
  BigDecimal getGBCEAllShareIndex();

  /**
   * Gets the trade records of the given stock in the last given minutes.
   *
   * @param stock the {@link Stock} to search
   * @param minutes the range of the time to search
   * @return a {@link TradeRecord}s matching the search criterion
   */
  List<TradeRecord> getTradeRecordsByTime(Stock stock, int minutes);

  /**
   * Finds the {@link Stock} registered to this market with the given symbol.
   *
   * @param symbol the symbol of the stock to look up
   * @return the {@link Stock} found
   */
  Stock findStockBySymbol(String symbol);

  /**
   * Validates the given value is a positive.
   *
   * @param value the value to be checked
   */
  void checkPositive(BigDecimal value);
}
