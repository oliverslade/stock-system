package broker.services.contracts;

import java.math.BigDecimal;

public interface FinancialAnalysisService {
  /**
   * Calculates the dividend yield of a given stock based on the price at the time of calculation.
   * The results precision is 3 and uses {@link BigDecimal#ROUND_HALF_EVEN}.
   *
   * @param symbol the symbol of the stock
   * @param price the price to be used in the calculation
   */
  BigDecimal getDividendYield(String symbol, BigDecimal price);

  /**
   * Calculates the P/E Ratio of a given stock based on the price at the time of calculation. The
   * results precision is 3 and uses {@link BigDecimal#ROUND_HALF_EVEN}.
   *
   * @param symbol the symbol of the stock
   * @param price the trade price
   */
  BigDecimal getPeRatio(String symbol, BigDecimal price);

  /**
   * Gets the volume weighted stock price based on the trades in the 15 minutes. The result keeps
   * precision scale is 0 and applies {@link BigDecimal#ROUND_HALF_EVEN}.
   *
   * @param symbol the symbol of the stock
   * @return the VolumeWeightedStockPrice in the last in 15 minutes.
   */
  BigDecimal getVolumeWeightedStockPrice(String symbol);

  /**
   * Calculates the GBCE all share index value based on the prices of all the stocks in the market
   * service. The result precision is 0 and applies {@link BigDecimal#ROUND_HALF_EVEN}.
   *
   * @return the GBCE all share index value, with 2 scale.
   */
  BigDecimal getAllShareIndex();

  /**
   * Validates the given value is a positive.
   *
   * @param value the value to be checked
   */
  void isPricePositive(BigDecimal value);
}
