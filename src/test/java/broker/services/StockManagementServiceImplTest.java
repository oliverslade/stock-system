package broker.services;

import broker.exceptions.BusinessException;
import broker.models.stocks.CommonStock;
import broker.models.stocks.Stock;
import broker.services.impls.StockManagementServiceImpl;
import broker.utils.TestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockManagementServiceImplTest {

  @Autowired private StockManagementServiceImpl stockManagementService;

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
    this.stockManagementService.registerStock(stock);
    Assert.assertEquals(
        stock, this.stockManagementService.getAllStocks().get(TestUtils.TEST_COMMON_STOCK));
    this.stockManagementService.deregisterStock(TestUtils.TEST_COMMON_STOCK);
    assertTrue(this.stockManagementService.getAllStocks().isEmpty());
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
    this.stockManagementService.registerStock(stock);
    this.stockManagementService.registerStock(stock);
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
    this.stockManagementService.deregisterStock(TestUtils.TEST_COMMON_STOCK);
  }
}