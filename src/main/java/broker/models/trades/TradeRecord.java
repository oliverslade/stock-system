package broker.models.trades;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * A model class of trade record containing the trade information, like stock symbol, time stamp,
 * quantity of shares, buy or sell indicator and trading price.
 */
public class TradeRecord {

  /** The stock symbol. */
  private String stockSymbol;

  /** The time stamp of this trade. */
  private Date timestamp;

  /** The quantify of this trade. */
  private BigInteger quantity;

  /** A buy/sell trade indicator. */
  private BuySellEnum indicator;

  /** The trade price. */
  private BigDecimal price;

  /**
   * Constructor.
   *
   * @param stockSymbol
   * @param timestamp
   * @param quantity
   * @param indicator
   * @param price
   */
  public TradeRecord(
      final String stockSymbol,
      final Date timestamp,
      final BigInteger quantity,
      final BuySellEnum indicator,
      final BigDecimal price) {
    this.stockSymbol = stockSymbol;
    this.timestamp = timestamp;
    this.quantity = quantity;
    this.indicator = indicator;
    this.price = price;
  }

  /** Constructor. */
  TradeRecord() {}

  /** @return the stockSymbol */
  public String getStockSymbol() {
    return this.stockSymbol;
  }

  /** @param stockSymbol the stockSymbol to set */
  void setStockSymbol(final String stockSymbol) {
    this.stockSymbol = stockSymbol;
  }

  /** @return the timestamp */
  public Date getTimestamp() {
    return this.timestamp;
  }

  /** @param timestamp the timestamp to set */
  void setTimestamp(final Date timestamp) {
    this.timestamp = timestamp;
  }

  /** @return the quantity */
  public BigInteger getQuantity() {
    return this.quantity;
  }

  /** @param quantity the quantity to set */
  void setQuantity(final BigInteger quantity) {
    this.quantity = quantity;
  }

  /** @return the indicator */
  public BuySellEnum getIndicator() {
    return this.indicator;
  }

  /** @param indicator the indicator to set */
  void setIndicator(final BuySellEnum indicator) {
    this.indicator = indicator;
  }

  /** @return the price */
  public BigDecimal getPrice() {
    return this.price;
  }

  /** @param price the price to set */
  void setPrice(final BigDecimal price) {
    this.price = price;
  }
}
