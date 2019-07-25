package broker.services;

import static org.junit.Assert.assertEquals;

import broker.exceptions.InvalidValueException;
import broker.models.stocks.CommonStock;
import broker.models.trades.BuySellEnum;
import broker.models.trades.TradeLedger;
import broker.services.contracts.StockManagementService;
import broker.services.impls.TradeServiceImpl;
import broker.utils.TestUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeServiceImplTest {

  @Autowired private TradeServiceImpl tradeService;
  @Autowired private StockManagementService stockService;

  @Before
  public void setup() {
    this.stockService.flush();
  }

  @Test
  public void recordTrade_Successful() {
    final CommonStock stock = TestUtils.getDefaultCommonStock();
    this.stockService.registerStock(stock);
    final Date timestamp = new Date();
    final BigInteger quantity = new BigInteger("1000");
    final BigDecimal price = new BigDecimal(1250);
    this.tradeService.recordTrade(
        TestUtils.COMMON_STOCK, timestamp, quantity, BuySellEnum.BUY, price);
    final TradeLedger record = stock.getTradeLedger().get(0);
    assertEquals(TestUtils.COMMON_STOCK, record.getStockSymbol());
    assertEquals(timestamp, record.getTimestamp());
    assertEquals(quantity, record.getQuantity());
    assertEquals(price, record.getPrice());
    assertEquals(BuySellEnum.BUY, record.getIndicator());
  }

  @Test(expected = InvalidValueException.class)
  public void recordTrade_InvalidPrice() {
    final CommonStock stock = TestUtils.getDefaultCommonStock();
    this.stockService.registerStock(stock);
    final BigInteger quantity = new BigInteger("1000");
    final BigDecimal price = new BigDecimal(-1);
    this.tradeService.recordTrade(
        TestUtils.COMMON_STOCK, new Date(), quantity, BuySellEnum.BUY, price);
  }

  @Test(expected = InvalidValueException.class)
  public void recordTrade_InvalidQuantity() {
    final CommonStock stock = TestUtils.getDefaultCommonStock();
    this.stockService.registerStock(stock);
    final BigInteger quantity = BigInteger.ZERO;
    final BigDecimal price = new BigDecimal(100);
    this.tradeService.recordTrade(
        TestUtils.COMMON_STOCK, new Date(), quantity, BuySellEnum.BUY, price);
  }
}
