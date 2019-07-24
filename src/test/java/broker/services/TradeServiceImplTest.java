package broker.services;

import broker.exceptions.InvalidValueException;
import broker.models.stocks.CommonStock;
import broker.models.trades.BuySellEnum;
import broker.models.trades.TradeRecord;
import broker.services.contracts.StockManagementService;
import broker.services.impls.TradeServiceImpl;
import broker.utils.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeServiceImplTest {

  @Autowired private TradeServiceImpl tradeService;
  @Autowired private StockManagementService stockService;

  /**
   * Tests for {@link TradeServiceImpl#recordTrade(String, Date, BigInteger, BuySellEnum,
   * BigDecimal)}.
   *
   * <ul>
   *   <li>call {@link TradeServiceImpl#recordTrade(String, Date, BigInteger, BuySellEnum,
   *       BigDecimal)}
   *   <li>verify that the trade information is recorded correctly
   * </ul>
   */
  @Test
  public void testRecordTrade() {
    final CommonStock stock = TestUtils.getDefaultCommonStock();
    this.stockService.registerStock(stock);
    final Date timestamp = new Date();
    final BigInteger quantity = new BigInteger("1000");
    final BigDecimal price = new BigDecimal(1250);
    this.tradeService.recordTrade(
        TestUtils.TEST_COMMON_STOCK, timestamp, quantity, BuySellEnum.BUY, price);
    final TradeRecord record = stock.getTradeRecords().get(0);
    assertEquals(TestUtils.TEST_COMMON_STOCK, record.getStockSymbol());
    assertEquals(timestamp, record.getTimestamp());
    assertEquals(quantity, record.getQuantity());
    assertEquals(price, record.getPrice());
    assertEquals(BuySellEnum.BUY, record.getIndicator());
  }

  /**
   * Tests for {@link TradeServiceImpl#recordTrade(String, Date, BigInteger, BuySellEnum,
   * BigDecimal)}.
   *
   * <ul>
   *   <li>call {@link TradeServiceImpl#recordTrade(String, Date, BigInteger, BuySellEnum,
   *       BigDecimal)} with price==-1
   *   <li>verify that an {@link InvalidValueException } is caught
   * </ul>
   */
  @Test(expected = InvalidValueException.class)
  public void testRecordTrade_InvalidPrice() {
    final CommonStock stock = TestUtils.getDefaultCommonStock();
    this.stockService.registerStock(stock);
    final BigInteger quantity = new BigInteger("1000");
    final BigDecimal price = new BigDecimal(-1);
    this.tradeService.recordTrade(
        TestUtils.TEST_COMMON_STOCK, new Date(), quantity, BuySellEnum.BUY, price);
  }

  /**
   * Tests for {@link TradeServiceImpl#recordTrade(String, Date, BigInteger, BuySellEnum,
   * BigDecimal)}.
   *
   * <ul>
   *   <li>call {@link TradeServiceImpl#recordTrade(String, Date, BigInteger, BuySellEnum,
   *       BigDecimal)} with quantity==0
   *   <li>verify that an {@link InvalidValueException} is caught
   * </ul>
   */
  @Test(expected = InvalidValueException.class)
  public void testRecordTrade_InvalidQuantity() {
    final CommonStock stock = TestUtils.getDefaultCommonStock();
    this.stockService.registerStock(stock);
    final BigInteger quantity = BigInteger.ZERO;
    final BigDecimal price = new BigDecimal(100);
    this.tradeService.recordTrade(
        TestUtils.TEST_COMMON_STOCK, new Date(), quantity, BuySellEnum.BUY, price);
  }
}
