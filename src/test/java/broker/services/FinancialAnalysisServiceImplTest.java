package broker.services;

import broker.exceptions.BusinessException;
import broker.exceptions.InvalidValueException;
import broker.models.stocks.CommonStock;
import broker.models.stocks.PreferredStock;
import broker.models.trades.BuySellEnum;
import broker.services.contracts.FinancialAnalysisService;
import broker.services.contracts.StockManagementService;
import broker.services.contracts.TradeService;
import broker.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import static broker.utils.TestUtils.COMMON_STOCK;
import static broker.utils.TestUtils.PREFERRED_STOCK;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FinancialAnalysisServiceImplTest {

  @Autowired private FinancialAnalysisService analysisService;

  @Autowired private StockManagementService stockService;

  @Autowired private TradeService tradeService;

  @Before
  public void setup() {
    this.stockService.flush();
  }

  @Test
  public void getDividendYield_CommonStock() {

    final CommonStock commonStock = TestUtils.getDefaultCommonStock();
    this.stockService.registerStock(commonStock);
    commonStock.setLastDividend(new BigDecimal(23));
    final BigDecimal result =
        this.analysisService.getDividendYield(COMMON_STOCK, new BigDecimal(130));
    assertEquals(new BigDecimal("0.177"), result);
  }

  @Test
  public void getDividendYield_PreferredStock() {

    final PreferredStock preferredStock = TestUtils.getDefaultPreferredStock();
    this.stockService.registerStock(preferredStock);
    preferredStock.setFixedDividend(new BigDecimal("0.02"));
    preferredStock.setParValue(new BigDecimal(100));
    final BigDecimal result =
        this.analysisService.getDividendYield(PREFERRED_STOCK, new BigDecimal(130));
    assertEquals(new BigDecimal("0.015"), result);
  }

  @Test(expected = InvalidValueException.class)
  public void getDividendYield_InvalidPrice() {

    final CommonStock commonStock = TestUtils.getDefaultCommonStock();
    this.stockService.registerStock(commonStock);
    commonStock.setLastDividend(new BigDecimal(23));
    this.analysisService.getDividendYield(COMMON_STOCK, new BigDecimal(0));
  }

  @Test
  public void getPeRatio_CorrectCalculation() {
    final CommonStock stock = TestUtils.getDefaultCommonStock();
    this.stockService.registerStock(stock);
    stock.setLastDividend(new BigDecimal(23));
    final BigDecimal result = this.analysisService.getPeRatio(COMMON_STOCK, new BigDecimal(100));
    assertEquals(new BigDecimal("4.348"), result);
  }

  @Test(expected = InvalidValueException.class)
  public void getPeRatio_InvalidPrice() {
    final CommonStock stock = TestUtils.getDefaultCommonStock();
    this.stockService.registerStock(stock);
    stock.setLastDividend(new BigDecimal(23));
    this.analysisService.getPeRatio(COMMON_STOCK, new BigDecimal(0));
  }

  @Test(expected = BusinessException.class)
  public void getPeRatio_NoDividend() {
    final CommonStock stock = TestUtils.getDefaultCommonStock();
    this.stockService.registerStock(stock);
    stock.setLastDividend(BigDecimal.ZERO);
    this.analysisService.getPeRatio(COMMON_STOCK, new BigDecimal(100));
  }

  @Test
  public void volumeWeightedStockPrice_CorrectCalculation() {
    final CommonStock commonStock = TestUtils.getDefaultCommonStock();
    final PreferredStock preferredStock = TestUtils.getDefaultPreferredStock();
    this.stockService.registerStock(commonStock);
    this.stockService.registerStock(preferredStock);
    final long startTime = new Date().getTime();
    this.tradeService.recordTrade(
        COMMON_STOCK,
        new Date(startTime - 5 * 1000 * 60),
        new BigInteger("100"),
        BuySellEnum.BUY,
        new BigDecimal(22));
    this.tradeService.recordTrade(
        COMMON_STOCK,
        new Date(startTime - 8 * 1000 * 60),
        new BigInteger("10"),
        BuySellEnum.SELL,
        new BigDecimal(20));
    this.tradeService.recordTrade(
        PREFERRED_STOCK,
        new Date(startTime - 9 * 1000 * 60),
        new BigInteger("50"),
        BuySellEnum.BUY,
        new BigDecimal(50));
    this.tradeService.recordTrade(
        PREFERRED_STOCK,
        new Date(startTime - 16 * 1000 * 60),
        new BigInteger("3"),
        BuySellEnum.SELL,
        new BigDecimal(30));
    assertEquals(
        new BigDecimal(22), this.analysisService.getVolumeWeightedStockPrice(COMMON_STOCK));
  }

  @Test
  public void volumeWeightedStockPrice_NoTradeIn15Minutes() {
    final CommonStock commonStock = TestUtils.getDefaultCommonStock();
    this.stockService.registerStock(commonStock);
    final long startTime = new Date().getTime();
    this.tradeService.recordTrade(
        COMMON_STOCK,
        new Date(startTime - 20 * 1000 * 60),
        new BigInteger("50"),
        BuySellEnum.BUY,
        new BigDecimal(20));
    assertEquals(new BigDecimal(0), this.analysisService.getVolumeWeightedStockPrice(COMMON_STOCK));
  }

  @Test
  public void getAllShareIndex_CorrectCalculation() {
    final CommonStock commonStock1 =
        new CommonStock(COMMON_STOCK + 1, BigDecimal.ZERO, BigDecimal.ZERO, new BigDecimal(20));
    final CommonStock commonStock2 =
        new CommonStock(COMMON_STOCK + 2, BigDecimal.ZERO, BigDecimal.ZERO, new BigDecimal(23));
    final CommonStock commonStock3 =
        new CommonStock(COMMON_STOCK + 3, BigDecimal.ZERO, BigDecimal.ZERO, new BigDecimal(66));
    final PreferredStock preferredStock1 =
        new PreferredStock(
            PREFERRED_STOCK + 1,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            new BigDecimal(55),
            BigDecimal.ZERO);
    final PreferredStock preferredStock2 =
        new PreferredStock(
            PREFERRED_STOCK + 2,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            new BigDecimal(22),
            BigDecimal.ZERO);
    this.stockService.registerStock(commonStock1);
    this.stockService.registerStock(commonStock2);
    this.stockService.registerStock(commonStock3);
    this.stockService.registerStock(preferredStock1);
    this.stockService.registerStock(preferredStock2);
    assertEquals(new BigDecimal(33), this.analysisService.getAllShareIndex());
  }

  @Test(expected = InvalidValueException.class)
  public void getAllShareIndex_PriceZero() {
    final CommonStock commonStock1 =
        new CommonStock(COMMON_STOCK + 1, BigDecimal.ZERO, BigDecimal.ZERO, new BigDecimal(22));
    final CommonStock commonStock2 =
        new CommonStock(COMMON_STOCK + 2, BigDecimal.ZERO, BigDecimal.ZERO, new BigDecimal(13));
    final CommonStock commonStock3 =
        new CommonStock(COMMON_STOCK + 3, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    this.stockService.registerStock(commonStock1);
    this.stockService.registerStock(commonStock2);
    this.stockService.registerStock(commonStock3);
    this.analysisService.getAllShareIndex();
  }

  @Test(expected = BusinessException.class)
  public void getAllShareIndex_NoStock() {
    assertEquals(BigDecimal.ZERO, this.analysisService.getAllShareIndex());
  }
}
