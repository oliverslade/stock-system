package broker.models.stocks;

import broker.utils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class TestPreferredStock {

  /** The {@link broker.models.stocks.PreferredStock} under test. */
  private PreferredStock stock;

  /** Runs before every test. */
  @Before
  public void setUp() {
    this.stock = TestUtils.getDefaultPreferredStock();
  }

  /**
   * Tests for {@link broker.models.stocks.Stock#getFixedDividend()} and {@link
   * broker.models.stocks.Stock#getFixedDividend()}.
   *
   * <ul>
   *   <li>call {@link broker.models.stocks.Stock#setFixedDividend(BigDecimal)}
   *   <li>verify that the {@link broker.models.stocks.Stock#getFixedDividend()} returns the
   *       expected value
   * </ul>
   */
  @Test
  public void testSetAndGetFixedDividend() {
    final BigDecimal value = TestUtils.getRandomBigDecimal();
    this.stock.setFixedDividend(value);
    Assert.assertEquals(value, this.stock.getFixedDividend());
  }
}
