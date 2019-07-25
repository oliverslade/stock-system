package broker.services.impls;

import broker.exceptions.BusinessException;
import broker.models.stocks.Stock;
import broker.services.contracts.StockManagementService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class StockManagementServiceImpl implements StockManagementService {

  private Map<String, Stock> stockMap;

  public StockManagementServiceImpl() {
    this.stockMap = new HashMap<>();
  }

  @Override
  public void registerStock(final Stock stock) {
    final String errorMessage = "The stock " + stock.getSymbol() + " has already been registered.";
    if (this.stockMap.containsKey(stock.getSymbol())) {
      throw new BusinessException(errorMessage);
    }
    this.stockMap.put(stock.getSymbol(), stock);
  }

  @Override
  public void deregisterStock(final String stockSymbol) {
    if (!this.stockMap.containsKey(stockSymbol)) {
      final String errorMessage = "The stock " + stockSymbol + " has not been registered.";
      throw new BusinessException(errorMessage);
    }
    this.stockMap.remove(stockSymbol);
  }

  @Override
  public Map<String, Stock> getAllStocks() {
    return this.stockMap;
  }

  @Override
  public Stock getStockBySymbol(final String symbol) {
    final Stock stock;
    if (this.stockMap.containsKey(symbol)) {
      stock = this.stockMap.get(symbol);
    } else {
      throw new BusinessException(
          "Cannot find the stock " + symbol + " in the market. Please register the stock first");
    }
    return stock;
  }

  @Override
  public void flush() {
    this.stockMap.clear();
  }
}
