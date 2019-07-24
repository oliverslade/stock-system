package broker.services.impls;

import static broker.constants.ServiceConstants.PRECISION_SCALE;

import broker.exceptions.BusinessException;
import broker.exceptions.InvalidValueException;
import broker.models.stocks.CommonStock;
import broker.models.stocks.PreferredStock;
import broker.models.stocks.Stock;
import broker.models.trades.BuySellEnum;
import broker.models.trades.TradeRecord;
import broker.services.contracts.StockMarketService;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * A stock market service that manages stocks and trades information.
 *
 * @author Oliver Slade
 */
public class StockMarketServiceImpl implements StockMarketService {

  private final Map<String, Stock> stockMap;

  public StockMarketServiceImpl() {
    this.stockMap = new HashMap<>();
  }

  @Override
  public void registerStock(final Stock stock) {
    final String errorMessage = "The stock " + stock.getSymbol() + " has already been registered.";
    if (this.stockMap.containsKey(stock.getSymbol())) {
      throw new BusinessException(errorMessage);
    }
    this.stockMap.put(stock.getSymbol(), stock);
  }

  @Override
  public void unregisterStock(final String stockSymbol) {
    if (!this.stockMap.containsKey(stockSymbol)) {
      final String errorMessage = "The stock " + stockSymbol + " has not been registered.";
      throw new BusinessException(errorMessage);
    }
    this.stockMap.remove(stockSymbol);
  }

  @Override
  public Map<String, Stock> getStockMap() {
    return this.stockMap;
  }

  @Override
  public BigDecimal getDividendYield(final String symbol, final BigDecimal price) {

    final Stock stock = this.findStockBySymbol(symbol);

    this.checkPositive(price);

    BigDecimal result = BigDecimal.ZERO;
    if (stock instanceof CommonStock) {
      final CommonStock commonStock = (CommonStock) stock;
      result =
          commonStock.getLastDividend().divide(price, PRECISION_SCALE, BigDecimal.ROUND_HALF_EVEN);
    } else if (stock instanceof PreferredStock) {
      final PreferredStock preferredStock = (PreferredStock) stock;
      result =
          preferredStock
              .getFixedDividend()
              .multiply(stock.getParValue())
              .divide(price, PRECISION_SCALE, BigDecimal.ROUND_HALF_EVEN);
    }

    return result.setScale(3, BigDecimal.ROUND_HALF_EVEN);
  }

  @Override
  public BigDecimal getPERatio(final String symbol, final BigDecimal price) {
    final Stock stock = this.findStockBySymbol(symbol);

    this.checkPositive(price);

    final BigDecimal result;
    if (BigDecimal.ZERO.compareTo(stock.getLastDividend()) >= 0) {
      throw new BusinessException(
          "Cannot calculate P/E Ratio for the stock " + symbol + "since the dividend is ZERO.");
    }
    result = price.divide(stock.getLastDividend(), PRECISION_SCALE, BigDecimal.ROUND_HALF_EVEN);
    return result.setScale(3, BigDecimal.ROUND_HALF_EVEN);
  }

  @Override
  public void recordTrade(
      final String symbol,
      final Date timestamp,
      final BigInteger quantity,
      final BuySellEnum indicator,
      final BigDecimal price) {
    final Stock stock = this.findStockBySymbol(symbol);

    this.checkPositive(price);
    this.checkPositive(new BigDecimal(quantity));

    final TradeRecord record = new TradeRecord(symbol, timestamp, quantity, indicator, price);
    stock.addTradeRecord(record);
  }

  @Override
  public BigDecimal getVolumeWeightedStockPrice(final String symbol) {

    final Stock stock = this.findStockBySymbol(symbol);

    BigDecimal result = BigDecimal.ZERO;
    final List<TradeRecord> tradeRecords = this.getTradeRecordsByTime(stock, 15);
    if (tradeRecords.isEmpty()) {
      return result;
    }

    BigDecimal priceSum = BigDecimal.ZERO;
    BigInteger quantitySum = BigInteger.ZERO;

    for (final TradeRecord record : tradeRecords) {
      final BigDecimal price = record.getPrice();
      this.checkPositive(price);
      final BigDecimal quatity = new BigDecimal(record.getQuantity());
      this.checkPositive(quatity);

      priceSum = priceSum.add(price.multiply(quatity));
      quantitySum = quantitySum.add(record.getQuantity());
    }
    result = priceSum.divide(new BigDecimal(quantitySum), PRECISION_SCALE, 3);
    return result.setScale(0, BigDecimal.ROUND_HALF_EVEN);
  }

  @Override
  public BigDecimal getGBCEAllShareIndex() {
    if (this.stockMap.isEmpty()) {
      throw new BusinessException("There are no stocks available to purchase at the moment.");
    }

    BigDecimal accumulate = BigDecimal.ONE;
    final int n = this.stockMap.size();

    for (final Stock stock : this.stockMap.values()) {
      final BigDecimal price = stock.getPrice();
      this.checkPositive(price);
      accumulate = accumulate.multiply(price);
    }

    BigDecimal x = accumulate.divide(accumulate, BigDecimal.ROUND_HALF_EVEN);
    BigDecimal temp;
    final BigDecimal e = new BigDecimal("0.1");

    do {
      temp = x;
      x =
          x.add(
              accumulate
                  .subtract(x.pow(n))
                  .divide(
                      new BigDecimal(n).multiply(x.pow(n - 1)),
                      PRECISION_SCALE,
                      BigDecimal.ROUND_HALF_EVEN));
    } while (x.subtract(temp).abs().compareTo(e) > 0);

    return x.setScale(0, BigDecimal.ROUND_HALF_EVEN);
  }

  @Override
  public List<TradeRecord> getTradeRecordsByTime(final Stock stock, final int minutes) {
    // TODO: LinkedList
    final List<TradeRecord> result = new ArrayList<>();
    final Date currentTime = new Date();
    for (final TradeRecord record : stock.getTradeRecords()) {
      if (currentTime.getTime() - record.getTimestamp().getTime() <= minutes * 60 * 1000) {
        result.add(record);
      }
    }

    return result;
  }

  @Override
  public Stock findStockBySymbol(final String symbol) {
    final Stock stock;
    if (this.stockMap.containsKey(symbol)) {
      stock = this.stockMap.get(symbol);
    } else {
      throw new BusinessException(
          "Cannot find the stock " + symbol + " in the market. Please register the stock first");
    }
    return stock;
  }

  @Override
  public void checkPositive(final BigDecimal value) {
    if (value == null || BigDecimal.ZERO.compareTo(value) >= 0) {
      throw new InvalidValueException("Found non-positive value: " + value);
    }
  }
}
