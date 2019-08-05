package broker.controllers;

import broker.models.stocks.CommonStock;
import broker.models.stocks.Stock;
import broker.services.contracts.StockManagementService;
import java.math.BigDecimal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {
  @Autowired private StockManagementService stockService;

  @RequestMapping("/get-stock")
  public Stock getStock(@RequestParam String symbol) {
    return stockService.getStockBySymbol(symbol);
  }

  @RequestMapping("/get-all-stocks")
  public Map<String, Stock> getAllStocks() {
    return stockService.getAllStocks();
  }

  @RequestMapping("/register-common-stock")
  public void registerCommonStock(
      @RequestParam String symbol,
      @RequestParam BigDecimal lastDividend,
      @RequestParam BigDecimal parValue,
      @RequestParam BigDecimal price) {
    Stock newStock = new CommonStock(symbol, lastDividend, parValue, price);
    stockService.registerStock(newStock);
  }

  @RequestMapping("/deregister-stock")
  public void deregisterStock(@RequestParam String symbol) {
    stockService.deregisterStock(symbol);
  }
}
