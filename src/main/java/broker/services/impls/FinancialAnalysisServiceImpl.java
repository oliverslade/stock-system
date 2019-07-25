package broker.services.impls;

import broker.exceptions.BusinessException;
import broker.exceptions.InvalidValueException;
import broker.models.stocks.CommonStock;
import broker.models.stocks.PreferredStock;
import broker.models.stocks.Stock;
import broker.models.trades.TradeLedger;
import broker.services.contracts.FinancialAnalysisService;
import broker.services.contracts.StockManagementService;
import broker.services.contracts.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static broker.constants.ServiceConstants.PRECISION_SCALE;
import static java.math.BigDecimal.ROUND_HALF_EVEN;

@Service
public class FinancialAnalysisServiceImpl implements FinancialAnalysisService {

  @Autowired private StockManagementService stockManagementService;
  @Autowired private TradeService tradeService;

  @Override
  public BigDecimal getDividendYield(final String symbol, final BigDecimal price) {

    final Stock stock = this.stockManagementService.getStockBySymbol(symbol);

    this.isNumberPositive(price);

    BigDecimal result = BigDecimal.ZERO;
    if (stock instanceof CommonStock) {
      final CommonStock commonStock = (CommonStock) stock;
      result = commonStock.getLastDividend().divide(price, PRECISION_SCALE, ROUND_HALF_EVEN);
    } else if (stock instanceof PreferredStock) {
      final PreferredStock preferredStock = (PreferredStock) stock;
      result =
          preferredStock
              .getFixedDividend()
              .multiply(stock.getParValue())
              .divide(price, PRECISION_SCALE, ROUND_HALF_EVEN);
    }

    return result.setScale(3, ROUND_HALF_EVEN);
  }

  @Override
  public BigDecimal getPeRatio(final String symbol, final BigDecimal price) {
    final Stock stock = this.stockManagementService.getStockBySymbol(symbol);

    this.isNumberPositive(price);

    final BigDecimal result;
    if (BigDecimal.ZERO.compareTo(stock.getLastDividend()) >= 0) {
      throw new BusinessException(
          "Cannot calculate P/E Ratio for the stock " + symbol + "since the dividend is ZERO.");
    }
    result = price.divide(stock.getLastDividend(), PRECISION_SCALE, ROUND_HALF_EVEN);
    return result.setScale(3, ROUND_HALF_EVEN);
  }

  @Override
  public BigDecimal getVolumeWeightedStockPrice(final String symbol) {

    final Stock stock = this.stockManagementService.getStockBySymbol(symbol);

    BigDecimal result = BigDecimal.ZERO;
    final List<TradeLedger> tradeLedger = this.tradeService.getLast15MinutesTrades(stock);
    if (tradeLedger.isEmpty()) {
      return result;
    }

    BigDecimal runningPriceTotal = BigDecimal.ZERO;
    BigInteger runningQuantityTotal = BigInteger.ZERO;

    for (final TradeLedger record : tradeLedger) {
      final BigDecimal price = record.getPrice();
      this.isNumberPositive(price);
      final BigDecimal quantity = new BigDecimal(record.getQuantity());
      this.isNumberPositive(quantity);

      runningPriceTotal = runningPriceTotal.add(price.multiply(quantity));
      runningQuantityTotal = runningQuantityTotal.add(record.getQuantity());
    }
    result = runningPriceTotal.divide(new BigDecimal(runningQuantityTotal), PRECISION_SCALE, 3);
    return result.setScale(0, ROUND_HALF_EVEN);
  }

  @Override
  public BigDecimal getAllShareIndex() {
    if (this.stockManagementService.getAllStocks().isEmpty()) {
      throw new BusinessException("There are no stocks available to purchase at the moment.");
    }

    BigDecimal accumulate = BigDecimal.ONE;
    final int totalNumberOfStocks = this.stockManagementService.getAllStocks().size();

    for (final Stock stock : this.stockManagementService.getAllStocks().values()) {
      final BigDecimal price = stock.getPrice();
      this.isNumberPositive(price);
      accumulate = accumulate.multiply(price);
    }

    BigDecimal index = accumulate.divide(accumulate, ROUND_HALF_EVEN);
    BigDecimal temp;
    final BigDecimal e = new BigDecimal("0.1");

    do {
      temp = index;
      index =
          index.add(
              accumulate
                  .subtract(index.pow(totalNumberOfStocks))
                  .divide(
                      new BigDecimal(totalNumberOfStocks)
                          .multiply(index.pow(totalNumberOfStocks - 1)),
                      PRECISION_SCALE,
                      ROUND_HALF_EVEN));
    } while (index.subtract(temp).abs().compareTo(e) > 0);

    return index.setScale(0, ROUND_HALF_EVEN);
  }

  @Override
  public void isNumberPositive(final BigDecimal value) {
    if (value == null || BigDecimal.ZERO.compareTo(value) >= 0) {
      throw new InvalidValueException("Found non-positive value: " + value);
    }
  }
}
