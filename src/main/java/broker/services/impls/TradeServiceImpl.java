package broker.services.impls;

import broker.models.stocks.Stock;
import broker.models.trades.BuySellEnum;
import broker.models.trades.TradeLedger;
import broker.services.contracts.FinancialAnalysisService;
import broker.services.contracts.StockManagementService;
import broker.services.contracts.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {

  @Autowired private StockManagementService stockService;
  @Autowired private FinancialAnalysisService financialAnalysisService;

  @Override
  public void recordTrade(
      final String symbol,
      final Date timestamp,
      final BigInteger quantity,
      final BuySellEnum indicator,
      final BigDecimal price) {
    final Stock stock = this.stockService.getStockBySymbol(symbol);

    this.financialAnalysisService.isNumberPositive(price);
    this.financialAnalysisService.isNumberPositive(new BigDecimal(quantity));

    final TradeLedger record = new TradeLedger(symbol, timestamp, quantity, indicator, price);
    stock.addNewTrade(record);
  }

  @Override
  public List<TradeLedger> getLast15MinutesTrades(final Stock stock) {
    final List<TradeLedger> result = new ArrayList<>();
    final Date currentTime = new Date();
    for (final TradeLedger record : stock.getTradeLedger()) {
      if (currentTime.getTime() - record.getTimestamp().getTime() <= 15 * 60 * 1000) {
        result.add(record);
      }
    }

    return result;
  }
}
