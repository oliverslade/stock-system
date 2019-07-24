package broker.services.contracts;

import broker.models.stocks.Stock;

import java.util.Map;

public interface StockManagementService {
  /**
   * Registers a stock to the stock market.
   *
   * @param stock the stock to be registered
   */
  void registerStock(Stock stock);

  /**
   * Deregister a stock from this market service.
   *
   * @param stockSymbol the symbol of the stock to be unregistered
   */
  void deregisterStock(String stockSymbol);

  /** @return a HashMap containing the stocks managed in this service */
  Map<String, Stock> getAllStocks();

  /**
   * Finds the {@link Stock} registered to this market with the given symbol.
   *
   * @param symbol the symbol of the stock to look up
   * @return the {@link Stock} found
   */
  Stock getStockBySymbol(String symbol);
}
