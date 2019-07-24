package broker.services.impls;

import broker.models.stocks.Stock;
import broker.models.trades.BuySellEnum;
import broker.models.trades.TradeRecord;
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

  @Autowired private StockManagementService stockManagementService;
  @Autowired private FinancialAnalysisService financialAnalysisService;

  @Override
  public void recordTrade(
      final String symbol,
      final Date timestamp,
      final BigInteger quantity,
      final BuySellEnum indicator,
      final BigDecimal price) {
    final Stock stock = this.stockManagementService.getStockBySymbol(symbol);

    this.financialAnalysisService.isPricePositive(price);
    this.financialAnalysisService.isPricePositive(new BigDecimal(quantity));

    final TradeRecord record = new TradeRecord(symbol, timestamp, quantity, indicator, price);
    stock.addTradeRecord(record);
  }

  @Override
  public List<TradeRecord> getTradeRecordsByTime(final Stock stock, final int minutes) {
    // TODO: LinkedList
    final List<TradeRecord> result = new ArrayList<>();
    final Date currentTime = new Date();
    for (final TradeRecord record : stock.getTradeRecords()) {
      if (currentTime.getTime() - record.getTimestamp().getTime() <= minutes * 60 * 1000) {
        result.add(record);
      }
    }

    return result;
  }
}
