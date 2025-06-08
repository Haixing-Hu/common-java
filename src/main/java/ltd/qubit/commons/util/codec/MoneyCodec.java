////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.math.BigDecimal;
import java.math.RoundingMode;

import ltd.qubit.commons.annotation.Money;

/**
 * The codec of money values, which are represented with {@link BigDecimal}
 * objects.
 *
 * @author Haixing Hu
 */
public class MoneyCodec extends BigDecimalCodec {

  public static final int DEFAULT_SCALE = Money.DEFAULT_SCALE;

  public static final RoundingMode DEFAULT_ROUND_MODE = Money.DEFAULT_ROUNDING_MODE;

  /**
   * The default mark of whether to use digit grouping.
   */
  public static final boolean DEFAULT_USE_GROUP = false;

  public MoneyCodec() {
    super(DEFAULT_SCALE, DEFAULT_USE_GROUP, DEFAULT_ROUND_MODE);
  }

  public MoneyCodec(final int scale) {
    super(scale, DEFAULT_USE_GROUP, DEFAULT_ROUND_MODE);
  }

  public MoneyCodec(final int scale, final boolean useGroup) {
    super(scale, useGroup, DEFAULT_ROUND_MODE);
  }

  public MoneyCodec(final int scale, final boolean useGroup,
      final RoundingMode roundingMode) {
    super(scale, useGroup, roundingMode);
  }
}