package broker.services.impls;

import broker.exceptions.BusinessException;
import broker.exceptions.InvalidValueException;
import broker.models.stocks.CommonStock;
import broker.models.stocks.PreferredStock;
import broker.models.stocks.Stock;
import broker.models.trades.TradeRecord;
import broker.services.contracts.FinancialAnalysisService;
import broker.services.contracts.StockManagementService;
import broker.services.contracts.TradeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static broker.constants.ServiceConstants.PRECISION_SCALE;

public class FinancialAnalysisServiceImpl implements FinancialAnalysisService {

  @Autowired private StockManagementService stockManagementService;
  @Autowired private TradeService tradeService;

  @Override
  public BigDecimal getDividendYield(final String symbol, final BigDecimal price) {

    final Stock stock = this.stockManagementService.getStockBySymbol(symbol);

    this.isPricePositive(price);

    BigDecimal result = BigDecimal.ZERO;
    if (stock instanceof CommonStock) {
      final CommonStock commonStock = (CommonStock) stock;
      result =
          commonStock.getLastDividend().divide(price, PRECISION_SCALE, BigDecimal.ROUND_HALF_EVEN);
    } else if (stock instanceof PreferredStock) {
      final PreferredStock preferredStock = (PreferredStock) stock;
      result =
          preferredStock
              .getFixedDividend()
              .multiply(stock.getParValue())
              .divide(price, PRECISION_SCALE, BigDecimal.ROUND_HALF_EVEN);
    }

    return result.setScale(3, BigDecimal.ROUND_HALF_EVEN);
  }

  @Override
  public BigDecimal getPeRatio(final String symbol, final BigDecimal price) {
    final Stock stock = this.stockManagementService.getStockBySymbol(symbol);

    this.isPricePositive(price);

    final BigDecimal result;
    if (BigDecimal.ZERO.compareTo(stock.getLastDividend()) >= 0) {
      throw new BusinessException(
          "Cannot calculate P/E Ratio for the stock " + symbol + "since the dividend is ZERO.");
    }
    result = price.divide(stock.getLastDividend(), PRECISION_SCALE, BigDecimal.ROUND_HALF_EVEN);
    return result.setScale(3, BigDecimal.ROUND_HALF_EVEN);
  }

  @Override
  public BigDecimal getVolumeWeightedStockPrice(final String symbol) {

    final Stock stock = this.stockManagementService.getStockBySymbol(symbol);

    BigDecimal result = BigDecimal.ZERO;
    final List<TradeRecord> tradeRecords = this.tradeService.getTradeRecordsByTime(stock, 15);
    if (tradeRecords.isEmpty()) {
      return result;
    }

    BigDecimal priceSum = BigDecimal.ZERO;
    BigInteger quantitySum = BigInteger.ZERO;

    for (final TradeRecord record : tradeRecords) {
      final BigDecimal price = record.getPrice();
      this.isPricePositive(price);
      final BigDecimal quatity = new BigDecimal(record.getQuantity());
      this.isPricePositive(quatity);

      priceSum = priceSum.add(price.multiply(quatity));
      quantitySum = quantitySum.add(record.getQuantity());
    }
    result = priceSum.divide(new BigDecimal(quantitySum), PRECISION_SCALE, 3);
    return result.setScale(0, BigDecimal.ROUND_HALF_EVEN);
  }

  @Override
  public BigDecimal getAllShareIndex() {
    if (this.stockManagementService.getAllStocks().isEmpty()) {
      throw new BusinessException("There are no stocks available to purchase at the moment.");
    }

    BigDecimal accumulate = BigDecimal.ONE;
    final int n = this.stockManagementService.getAllStocks().size();

    for (final Stock stock : this.stockManagementService.getAllStocks().values()) {
      final BigDecimal price = stock.getPrice();
      this.isPricePositive(price);
      accumulate = accumulate.multiply(price);
    }

    BigDecimal x = accumulate.divide(accumulate, BigDecimal.ROUND_HALF_EVEN);
    BigDecimal temp;
    final BigDecimal e = new BigDecimal("0.1");

    do {
      temp = x;
      x =
          x.add(
              accumulate
                  .subtract(x.pow(n))
                  .divide(
                      new BigDecimal(n).multiply(x.pow(n - 1)),
                      PRECISION_SCALE,
                      BigDecimal.ROUND_HALF_EVEN));
    } while (x.subtract(temp).abs().compareTo(e) > 0);

    return x.setScale(0, BigDecimal.ROUND_HALF_EVEN);
  }

  @Override
  public void isPricePositive(final BigDecimal value) {
    if (value == null || BigDecimal.ZERO.compareTo(value) >= 0) {
      throw new InvalidValueException("Found non-positive value: " + value);
    }
  }
}
