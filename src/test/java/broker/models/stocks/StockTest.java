package broker.models.stocks;

import broker.models.trades.TradeRecord;
import broker.utils.TestUtils;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * A test class for {@link broker.models.stocks.Stock}.
 *
 * @author Oliver Slade
 */
public class StockTest {

  /** The {@link broker.models.stocks.Stock} object under test. */
  private Stock stock;

  /**
   * The price of this stock.
   *
   * <p>/** Runs before every test.
   */
  @Before
  public void setUp() {
    this.stock =
        new CommonStock(
            TestUtils.PREFIX_STOCK_SYMBOL, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
  }

  /**
   * Tests for {@link broker.models.stocks.Stock#Stock()}.
   *
   * <ul>
   *   <li>create a new {@link broker.models.stocks.Stock}
   *   <li>verify that the trade records is not null
   * </ul>
   */
  @Test
  public void testBaseStock() {
    Assert.assertNotNull(this.stock.getTradeRecords());
  }

  /**
   * Tests for {@link broker.models.stocks.Stock#setSymbol(String)} and {@link
   * broker.models.stocks.Stock#getSymbol()}.
   *
   * <ul>
   *   <li>call {@link broker.models.stocks.Stock}
   *   <li>verify that the {@link broker.models.stocks.Stock} returns the expected value
   * </ul>
   */
  @Test
  public void testSetAndGetSymbol() {
    this.stock.setSymbol("Test");
    Assert.assertEquals("Test", this.stock.getSymbol());
  }

  /**
   * Tests for {@link broker.models.stocks.Stock#setPrice(BigDecimal)} and {@link
   * broker.models.stocks.Stock#getPrice()} .
   *
   * <ul>
   *   <li>call {@link broker.models.stocks.Stock#setPrice(BigDecimal)}
   *   <li>verify that the {@link broker.models.stocks.Stock#getPrice()} returns the expected value
   * </ul>
   */
  @Test
  public void testSetAndGetPrice() {
    final BigDecimal value = TestUtils.getRandomBigDecimal();
    this.stock.setPrice(value);
    Assert.assertEquals(value, this.stock.getPrice());
  }

  /**
   * Tests for {@link broker.models.stocks.Stock#setParValue(BigDecimal)} and {@link
   * broker.models.stocks.Stock#getParValue()}.
   *
   * <ul>
   *   <li>call {@link broker.models.stocks.Stock#setParValue(BigDecimal)}
   *   <li>verify that the {@link broker.models.stocks.Stock#getParValue()} returns the expected
   *       value
   * </ul>
   */
  @Test
  public void testSetAndGetParValue() {
    final BigDecimal value = TestUtils.getRandomBigDecimal();
    this.stock.setParValue(value);
    Assert.assertEquals(value, this.stock.getParValue());
  }

  /**
   * Tests for {@link broker.models.stocks.Stock#setLastDividend(BigDecimal)} and {@link
   * broker.models.stocks.Stock#getLastDividend()}.
   *
   * <ul>
   *   <li>call {@link broker.models.stocks.Stock#setLastDividend(BigDecimal)}
   *   <li>verify that the {@link broker.models.stocks.Stock#getLastDividend()} returns the expected
   *       value
   * </ul>
   */
  @Test
  public void testSetAndGetLastDividend() {
    final BigDecimal value = TestUtils.getRandomBigDecimal();
    this.stock.setLastDividend(value);
    Assert.assertEquals(value, this.stock.getLastDividend());
  }

  /**
   * Tests for {@link broker.models.stocks.Stock#addTradeRecord(TradeRecord)} and {@link
   * broker.models.stocks.Stock#getTradeRecords()}.
   *
   * <ul>
   *   <li>call {@link broker.models.stocks.Stock#addTradeRecord(TradeRecord)}
   *   <li>verify that the {@link broker.models.stocks.Stock#getTradeRecords()} returns the expected
   *       value
   * </ul>
   */
  @Test
  public void testSetAndGetTradeRecords() {
    final TradeRecord record = Mockito.mock(TradeRecord.class);
    this.stock.addTradeRecord(record);
    Assert.assertEquals(record, this.stock.getTradeRecords().get(0));
  }
}
