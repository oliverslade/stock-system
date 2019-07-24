package broker.services;

import broker.exceptions.BusinessException;
import broker.exceptions.InvalidValueException;
import broker.models.stocks.CommonStock;
import broker.models.stocks.PreferredStock;
import broker.models.trades.BuySellEnum;
import broker.services.contracts.StockManagementService;
import broker.services.contracts.TradeService;
import broker.services.impls.FinancialAnalysisServiceImpl;
import broker.utils.TestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class FinancialAnalysisServiceImplTest {

  @Autowired private FinancialAnalysisServiceImpl analysisService;

  @Autowired private StockManagementService stockService;

  @Autowired private TradeService tradeService;

  /**
   * Tests for {@link FinancialAnalysisServiceImpl#getDividendYield(String, BigDecimal)} for a
   * {@link CommonStock}.
   *
   * <ul>
   *   <li>create a {@link CommonStock} with last dividend of 23
   *   <li>call the {@link FinancialAnalysisServiceImpl#getDividendYield(String, BigDecimal)} with
   *       price of 130
   *   <li>assert that dividend yield is calculated correctly
   * </ul>
   */
  @Test
  public void testGetDividendYield_CommonStock() {

    final CommonStock commonStock = TestUtils.getDefaultCommonStock();
    this.stockService.registerStock(commonStock);
    commonStock.setLastDividend(new BigDecimal(23));
    final BigDecimal result =
        this.analysisService.getDividendYield(TestUtils.TEST_COMMON_STOCK, new BigDecimal(130));
    Assert.assertEquals(new BigDecimal("0.177"), result);
  }

  /**
   * Tests for {@link FinancialAnalysisServiceImpl#getDividendYield(String, BigDecimal)} for a
   * {@link PreferredStock}.
   *
   * <ul>
   *   <li>create a {@link PreferredStock} with FixedDividend of 0.02 and ParValue of 100
   *   <li>call {@link FinancialAnalysisServiceImpl#getDividendYield(String, BigDecimal)} with a
   *       price of 130
   *   <li>assert that the dividend yields is calculated correctly
   * </ul>
   */
  @Test
  public void testGetDividendYield_PreferredStock() {

    final PreferredStock preferredStock = TestUtils.getDefaultPreferredStock();
    this.stockService.registerStock(preferredStock);
    preferredStock.setFixedDividend(new BigDecimal("0.02"));
    preferredStock.setParValue(new BigDecimal(100));
    final BigDecimal result =
        this.analysisService.getDividendYield(TestUtils.TEST_PREFERRED_STOCK, new BigDecimal(130));
    Assert.assertEquals(new BigDecimal("0.015"), result);
  }

  /**
   * Tests for {@link FinancialAnalysisServiceImpl#getDividendYield(String, BigDecimal)} for a
   * {@link CommonStock} with an invalid price.
   *
   * <ul>
   *   <li>create a {@link CommonStock}
   *   <li>call {@link FinancialAnalysisServiceImpl#getDividendYield(String, BigDecimal)} with a
   *       price of -1
   *   <li>assert that an {@link InvalidValueException} is thrown
   * </ul>
   */
  @Test(expected = InvalidValueException.class)
  public void testGetDividendYield_InvalidPrice() {

    final CommonStock commonStock = TestUtils.getDefaultCommonStock();
    this.stockService.registerStock(commonStock);
    commonStock.setLastDividend(new BigDecimal(23));
    this.analysisService.getDividendYield(TestUtils.TEST_COMMON_STOCK, new BigDecimal(0));
  }

  /**
   * Tests for {@link FinancialAnalysisServiceImpl#getPeRatio(String, BigDecimal)} for a {@link
   * CommonStock}.
   *
   * <ul>
   *   <li>create a {@link CommonStock} with a LastDividend of 23
   *   <li>call the {@link FinancialAnalysisServiceImpl#getPeRatio(String, BigDecimal)} with a price
   *       of 100
   *   <li>verify that P/E Ratio is calculated correctly
   * </ul>
   */
  @Test
  public void testGetPeRatio() {
    final CommonStock stock = TestUtils.getDefaultCommonStock();
    this.stockService.registerStock(stock);
    stock.setLastDividend(new BigDecimal(23));
    final BigDecimal result =
        this.analysisService.getPeRatio(TestUtils.TEST_COMMON_STOCK, new BigDecimal(100));
    // TODO: Fix expected
    Assert.assertEquals(new BigDecimal("2.609"), result);
  }

  /**
   * Tests for {@link FinancialAnalysisServiceImpl#getPeRatio(String, BigDecimal)} for a {@link
   * CommonStock}.
   *
   * <ul>
   *   <li>create a {@link CommonStock} with a LastDividend of 23
   *   <li>call {@link FinancialAnalysisServiceImpl#getPeRatio(String, BigDecimal)} with a price of
   *       0
   *   <li>verify that a {@link InvalidValueException} is thrown
   * </ul>
   */
  @Test(expected = InvalidValueException.class)
  public void testGetPeRatio_InvalidPrice() {
    final CommonStock stock = TestUtils.getDefaultCommonStock();
    this.stockService.registerStock(stock);
    stock.setLastDividend(new BigDecimal(23));
    this.analysisService.getPeRatio(TestUtils.TEST_COMMON_STOCK, new BigDecimal(0));
  }

  /**
   * Tests for {@link FinancialAnalysisServiceImpl#getPeRatio(String, BigDecimal)} for a {@link
   * CommonStock}.
   *
   * <ul>
   *   <li>create a {@link CommonStock} with a LastDividend of 0
   *   <li>call {@link FinancialAnalysisServiceImpl#getPeRatio(String, BigDecimal)} with a given
   *       price
   *   <li>verify that a {@link BusinessException} is thrown
   * </ul>
   */
  @Test(expected = BusinessException.class)
  public void testGetPeRatio_DividendZero() {
    final CommonStock stock = TestUtils.getDefaultCommonStock();
    this.stockService.registerStock(stock);
    stock.setLastDividend(BigDecimal.ZERO);
    this.analysisService.getPeRatio(TestUtils.TEST_COMMON_STOCK, new BigDecimal(100));
  }

  /**
   * Tests for {@link FinancialAnalysisServiceImpl#getVolumeWeightedStockPrice(String).
   * <ul>
   * <li>record a TEST_COMMON_STOCK trade 3 minutes ago which bought 123456 shares at the price 1235</li>
   * <li>record a TEST_COMMON_STOCK trade 12 minutes ago which sold 7890 shares at the price 1021</li>
   * <li>record a TEST_PREFERRED_STOCK trade 7 minutes ago which bought 10000 shares at the price 3012</li>
   * <li>record a TEST_COMMON_STOCK trade 16 minutes ago which bought 2400 shares at the price 1187</li>
   * <li>call {@link FinancialAnalysisServiceImpl#recordTrade(String, Date, BigInteger, BuySellEnum, BigDecimal)}</li>
   * <li>verify that the only the trade of TEST_COMMON_STOCK in the last 15 minutes were used and the result is correct</li>
   * </ul>
   */
  @Test
  public void testVolumeWeightedStockPrice() {
    final CommonStock commonStock = TestUtils.getDefaultCommonStock();
    final PreferredStock preferredStock = TestUtils.getDefaultPreferredStock();
    this.stockService.registerStock(commonStock);
    this.stockService.registerStock(preferredStock);
    final long startTime = new Date().getTime();
    this.tradeService.recordTrade(
        TestUtils.TEST_COMMON_STOCK,
        new Date(startTime - 3 * 1000 * 60),
        new BigInteger("123456"),
        BuySellEnum.BUY,
        new BigDecimal(1235));
    this.tradeService.recordTrade(
        TestUtils.TEST_COMMON_STOCK,
        new Date(startTime - 12 * 1000 * 60),
        new BigInteger("7890"),
        BuySellEnum.SELL,
        new BigDecimal(1021));
    this.tradeService.recordTrade(
        TestUtils.TEST_PREFERRED_STOCK,
        new Date(startTime - 7 * 1000 * 60),
        new BigInteger("10000"),
        BuySellEnum.BUY,
        new BigDecimal(3012));
    this.tradeService.recordTrade(
        TestUtils.TEST_COMMON_STOCK,
        new Date(startTime - 16 * 1000 * 60),
        new BigInteger("2400"),
        BuySellEnum.BUY,
        new BigDecimal(1187));
    Assert.assertEquals(
        new BigDecimal(1222),
        this.analysisService.getVolumeWeightedStockPrice(TestUtils.TEST_COMMON_STOCK));
  }

  /**
   * Tests for {@link FinancialAnalysisServiceImpl#getVolumeWeightedStockPrice(String).
   * <ul>
   * <li>record only one trade 20 minutes ago</li>
   * <li>assert that there hasnt been a trade in the last 15 minutes.</li>
   * </ul>
   */
  @Test
  public void testVolumeWeightedStockPrice_NoTrade() {
    final CommonStock commonStock = TestUtils.getDefaultCommonStock();
    this.stockService.registerStock(commonStock);
    final long startTime = new Date().getTime();
    this.tradeService.recordTrade(
        TestUtils.TEST_COMMON_STOCK,
        new Date(startTime - 20 * 1000 * 60),
        new BigInteger("2400"),
        BuySellEnum.BUY,
        new BigDecimal(1187));
    Assert.assertEquals(
        new BigDecimal(0),
        this.analysisService.getVolumeWeightedStockPrice(TestUtils.TEST_COMMON_STOCK));
  }

  /**
   * Tests for {@link FinancialAnalysisServiceImpl#getAllShareIndex().
   * <ul>
   * <li>register 5 stocks with valid prices in the market</li>
   * <li>assert that the geometric mean is calculated correctly</li>
   * </ul>
   */
  @Test
  public void testGetAllShareIndex() {
    final CommonStock commonStock1 =
        new CommonStock(
            TestUtils.TEST_COMMON_STOCK + 1,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            new BigDecimal(1234));
    final CommonStock commonStock2 =
        new CommonStock(
            TestUtils.TEST_COMMON_STOCK + 2,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            new BigDecimal(7057));
    final CommonStock commonStock3 =
        new CommonStock(
            TestUtils.TEST_COMMON_STOCK + 3,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            new BigDecimal(4069));
    final PreferredStock preferStock1 =
        new PreferredStock(
            TestUtils.TEST_PREFERRED_STOCK + 1,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            new BigDecimal(583),
            BigDecimal.ZERO);
    final PreferredStock preferStock2 =
        new PreferredStock(
            TestUtils.TEST_PREFERRED_STOCK + 2,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            new BigDecimal(11231),
            BigDecimal.ZERO);
    this.stockService.registerStock(commonStock1);
    this.stockService.registerStock(commonStock2);
    this.stockService.registerStock(commonStock3);
    this.stockService.registerStock(preferStock1);
    this.stockService.registerStock(preferStock2);
    Assert.assertEquals(new BigDecimal(2972), this.analysisService.getAllShareIndex());
  }

  /**
   * Tests for {@link FinancialAnalysisServiceImpl#getAllShareIndex().
   * <ul>
   * <li>register 5 stocks with invalid prices in the market</li>
   * <li>assert that a {@link InvalidValueException} was thrown</li>
   * </ul>
   */
  @Test(expected = InvalidValueException.class)
  public void testGetAllShareIndex_PriceZero() {
    final CommonStock commonStock1 =
        new CommonStock(
            TestUtils.TEST_COMMON_STOCK + 1,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            new BigDecimal(1234));
    final CommonStock commonStock2 =
        new CommonStock(
            TestUtils.TEST_COMMON_STOCK + 2,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            new BigDecimal(7057));
    final CommonStock commonStock3 =
        new CommonStock(
            TestUtils.TEST_COMMON_STOCK + 3, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    this.stockService.registerStock(commonStock1);
    this.stockService.registerStock(commonStock2);
    this.stockService.registerStock(commonStock3);
    this.analysisService.getAllShareIndex();
  }

  /**
   * Tests for {@link FinancialAnalysisServiceImpl#getAllShareIndex().
   * <ul>
   * <li>call the getShareIndex method when there aren't any stocks registered</li>
   * <li>assert that a {@link BusinessException} was caught</li>
   * </ul>
   */
  @Test(expected = BusinessException.class)
  public void testGetAllShareIndex_NoStock() {
    Assert.assertEquals(BigDecimal.ZERO, this.analysisService.getAllShareIndex());
  }
}
