package broker.models.trades;

import broker.utils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Random;

/**
 * Tests for {@link broker.models.trades.TradeRecord}.
 *
 * @author Oliver Slade
 */
public class TradeRecordTest {

  /** The trade record under tests. */
  private TradeRecord tradeRecord;

  /** Constructor. */
  public TradeRecordTest() {}

  /** Runs before every test. */
  @Before
  public void setUp() {
    this.tradeRecord = new TradeRecord();
  }

  /**
   * Tests for {@link broker.models.trades.TradeRecord#setStockSymbol(String)} and {@link
   * broker.models.trades.TradeRecord#getStockSymbol()}.
   *
   * <ul>
   *   <li>call the {@link broker.models.trades.TradeRecord#setStockSymbol(String)}
   *   <li>verify that {@link broker.models.trades.TradeRecord#getStockSymbol()} returns the
   *       expected value
   * </ul>
   */
  @Test
  public void testSetAndGetStockSymbol() {
    this.tradeRecord.setStockSymbol(TestUtils.PREFIX_STOCK_SYMBOL);
    Assert.assertEquals(TestUtils.PREFIX_STOCK_SYMBOL, this.tradeRecord.getStockSymbol());
  }

  /**
   * Tests for {@link broker.models.trades.TradeRecord#setTimestamp(java.util.Date)} and {@link
   * broker.models.trades.TradeRecord#getTimestamp()}.
   *
   * <ul>
   *   <li>call the {@link broker.models.trades.TradeRecord#setTimestamp(java.util.Date)}
   *   <li>verify that {@link broker.models.trades.TradeRecord#getTimestamp()} returns the expected
   *       value
   * </ul>
   */
  @Test
  public void testSetAndGetTimeStamp() {
    final Date timestamp = new Date();
    this.tradeRecord.setTimestamp(timestamp);
    Assert.assertEquals(timestamp, this.tradeRecord.getTimestamp());
  }

  /**
   * Tests for {@link broker.models.trades.TradeRecord#setIndicator(BuySellEnum)} and {@link
   * broker.models.trades.TradeRecord#getIndicator()}.
   *
   * <ul>
   *   <li>call the {@link broker.models.trades.TradeRecord#setIndicator(BuySellEnum)}
   *   <li>verify that {@link broker.models.trades.TradeRecord#getIndicator()} returns the expected
   *       value
   * </ul>
   */
  @Test
  public void testSetAndGetIndicator() {
    this.tradeRecord.setIndicator(BuySellEnum.BUY);
    Assert.assertEquals(BuySellEnum.BUY, this.tradeRecord.getIndicator());
  }

  /**
   * Tests for {@link broker.models.trades.TradeRecord#setQuantity(java.math.BigDecimal)} and {@link
   * broker.models.trades.TradeRecord#getQuantity()}.
   *
   * <ul>
   *   <li>call the {@link broker.models.trades.TradeRecord#setQuantity(java.math.BigDecimal)}
   *   <li>verify that {@link broker.models.trades.TradeRecord#getQuantity()} returns the expected
   *       value
   * </ul>
   */
  @Test
  public void testSetAndGetQuantity() {
    final BigInteger quantity = new BigInteger(64, new Random());
    this.tradeRecord.setQuantity(quantity);
    Assert.assertEquals(quantity, this.tradeRecord.getQuantity());
  }

  /**
   * Tests for {@link broker.models.trades.TradeRecord#setPrice(java.math.BigDecimal)} and {@link
   * broker.models.trades.TradeRecord#getPrice()}.
   *
   * <ul>
   *   <li>call the {@link broker.models.trades.TradeRecord#setPrice(java.math.BigDecimal)}
   *   <li>verify that {@link broker.models.trades.TradeRecord#getPrice()} returns the expected
   *       value
   * </ul>
   */
  @Test
  public void testAndGetPrice() {
    final BigDecimal price = TestUtils.getRandomBigDecimal();
    this.tradeRecord.setPrice(price);
    Assert.assertEquals(price, this.tradeRecord.getPrice());
  }
}
