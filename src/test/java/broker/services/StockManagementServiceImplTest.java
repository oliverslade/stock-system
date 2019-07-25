package broker.services;

import static org.junit.Assert.assertTrue;

import broker.exceptions.BusinessException;
import broker.models.stocks.CommonStock;
import broker.services.contracts.StockManagementService;
import broker.utils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockManagementServiceImplTest {

  @Autowired private StockManagementService stockService;

  @Before
  public void setup() {
    this.stockService.flush();
  }

  @Test
  public void registerAndUnregisterStock_Successful() {
    final CommonStock stock = TestUtils.getDefaultCommonStock();
    this.stockService.registerStock(stock);
    Assert.assertEquals(stock, this.stockService.getAllStocks().get(TestUtils.COMMON_STOCK));
    this.stockService.deregisterStock(TestUtils.COMMON_STOCK);
    assertTrue(this.stockService.getAllStocks().isEmpty());
  }

  @Test(expected = BusinessException.class)
  public void registerStock_Duplicated() {
    final CommonStock stock = TestUtils.getDefaultCommonStock();
    this.stockService.registerStock(stock);
    this.stockService.registerStock(stock);
  }

  @Test(expected = BusinessException.class)
  public void deregisterStock_NonExistent() {
    this.stockService.deregisterStock(TestUtils.COMMON_STOCK);
  }
}
