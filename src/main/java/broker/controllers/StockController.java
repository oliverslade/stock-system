package broker.controllers;

import broker.models.stocks.Stock;
import broker.services.contracts.StockManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {
  @Autowired private StockManagementService stockService;

  @RequestMapping("/get-stock")
  public Stock getStock(@RequestParam(value = "symbol") String symbol) {
    return stockService.getStockBySymbol(symbol);
  }
}
