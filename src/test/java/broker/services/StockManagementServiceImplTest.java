package broker.services;

import broker.exceptions.BusinessException;
import broker.models.stocks.CommonStock;
import broker.models.stocks.Stock;
import broker.services.contracts.StockManagementService;
import broker.services.impls.StockManagementServiceImpl;
import broker.utils.TestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockManagementServiceImplTest {

  @Autowired private StockManagementService stockService;

  @BeforeEach
  public void setup() {
    stockService.flush();
  }


  /**
   * Tests for {@link StockManagementServiceImpl#registerStock(Stock)} )} and {@link
   * StockManagementServiceImpl#deregisterStock(String)}.
   *
   * <ul>
   *   <li>call {@link StockManagementServiceImpl#registerStock(Stock)} and then call {@link
   *       StockManagementServiceImpl#deregisterStock(String)}
   *   <li>assert that the stock was registered and then deregistered successfully.
   * </ul>
   */
  @Test
  public void testRegisterAndUnregisterStock() {
    final CommonStock stock = TestUtils.getDefaultCommonStock();
    this.stockService.registerStock(stock);
    Assert.assertEquals(
        stock, this.stockService.getAllStocks().get(TestUtils.TEST_COMMON_STOCK));
    this.stockService.deregisterStock(TestUtils.TEST_COMMON_STOCK);
    assertTrue(this.stockService.getAllStocks().isEmpty());
  }

  /**
   * Tests for {@link StockManagementServiceImpl#registerStock(Stock)} (CommonStock)} with
   * duplicated stock.
   *
   * <ul>
   *   <li>call {@link StockManagementServiceImpl#registerStock(Stock)} twice
   *   <li>assert that a {@link BusinessException} was thrown
   * </ul>
   */
  @Test(expected = BusinessException.class)
  public void testRegisterStockDuplicated() {
    final CommonStock stock = TestUtils.getDefaultCommonStock();
    this.stockService.registerStock(stock);
    this.stockService.registerStock(stock);
  }

  /**
   * Tests for {@link StockManagementServiceImpl#deregisterStock(String)} with a non-existing stock
   * symbol.
   *
   * <ul>
   *   <li>call {@link StockManagementServiceImpl#deregisterStock(String)} without registering the
   *       stock
   *   <li>assert that a {@link BusinessException} was thrown
   * </ul>
   */
  @Test(expected = BusinessException.class)
  public void testUnregisterStockNonExist() {
    this.stockService.deregisterStock(TestUtils.TEST_COMMON_STOCK);
  }
}
