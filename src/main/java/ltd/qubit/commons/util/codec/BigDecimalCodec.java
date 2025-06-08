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
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.annotation.Nullable;

import ltd.qubit.commons.annotation.Money;
import ltd.qubit.commons.annotation.Round;
import ltd.qubit.commons.annotation.Scale;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.Stripper;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNegative;
import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * The codec of the {@link BigDecimal} class.
 *
 * @author Haixing Hu
 */
public class BigDecimalCodec implements Codec<BigDecimal, String> {

  /**
   * The default scale, i.e., the default number of digits after the decimal
   * points.
   */
  public static final int DEFAULT_SCALE = 6;

  /**
   * The default mark of whether to use digit grouping.
   */
  public static final boolean DEFAULT_USE_GROUP = false;

  /**
   * The default rounding mode, which is {@link RoundingMode#HALF_UP}.
   */
  public static final RoundingMode DEFAULT_ROUND_MODE = RoundingMode.HALF_UP;

  private int scale;
  private boolean useGroup;
  private RoundingMode roundingMode;

  public BigDecimalCodec() {
    this(DEFAULT_SCALE, DEFAULT_USE_GROUP, DEFAULT_ROUND_MODE);
  }

  public BigDecimalCodec(final int scale) {
    this(scale, DEFAULT_USE_GROUP, DEFAULT_ROUND_MODE);
  }

  public BigDecimalCodec(final int scale, final boolean useGroup) {
    this(scale, useGroup, DEFAULT_ROUND_MODE);
  }

  public BigDecimalCodec(final int scale, final boolean useGroup,
      final RoundingMode roundingMode) {
    this.scale = requireNonNegative("scale", scale);
    this.useGroup = useGroup;
    this.roundingMode = roundingMode;
  }

  public BigDecimalCodec(@Nullable final Scale scale,
      @Nullable final Round round,
      @Nullable final Money money) {
    this();
    if (scale != null) {
      this.scale = scale.value();
    }
    if (round != null) {
      this.roundingMode = round.value();
    }
    if (money != null) {
      this.scale = money.scale();
      this.roundingMode = money.roundingModel();
      this.useGroup = money.useGroup();
    }
  }

  public final int getScale() {
    return scale;
  }

  public final BigDecimalCodec setScale(final int scale) {
    requireNonNegative("scale", scale);
    this.scale = scale;
    return this;
  }

  public final boolean isUseGroup() {
    return useGroup;
  }

  public final BigDecimalCodec setUseGroup(final boolean useGroup) {
    this.useGroup = useGroup;
    return this;
  }

  public final RoundingMode getRoundingMode() {
    return this.roundingMode;
  }

  public final BigDecimalCodec setRoundingMode(final RoundingMode roundingMode) {
    this.roundingMode = requireNonNull("roundingMode", roundingMode);
    return this;
  }

  @Override
  public BigDecimal decode(final String str) throws DecodingException {
    final String text = new Stripper().strip(str);
    if (text == null || text.isEmpty()) {
      return null;
    }
    try {
      final DecimalFormat format = new DecimalFormat();
      format.setParseBigDecimal(true);
      format.setMinimumFractionDigits(scale);
      format.setMaximumFractionDigits(scale);
      format.setGroupingUsed(useGroup);
      format.setRoundingMode(requireNonNull("roundingMode", roundingMode));
      final BigDecimal result = (BigDecimal) format.parse(text);
      return result.setScale(scale, format.getRoundingMode());
    } catch (final ParseException e) {
      throw new DecodingException(e);
    }
  }

  @Override
  public String encode(final BigDecimal value) {
    if (value == null) {
      return null;
    } else {
      final DecimalFormat format = new DecimalFormat();
      format.setParseBigDecimal(true);
      format.setMinimumFractionDigits(scale);
      format.setMaximumFractionDigits(scale);
      format.setGroupingUsed(useGroup);
      format.setRoundingMode(requireNonNull("roundingMode", roundingMode));
      return format.format(value);
    }
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final BigDecimalCodec other = (BigDecimalCodec) o;
    return Equality.equals(scale, other.scale)
        && Equality.equals(useGroup, other.useGroup)
        && Equality.equals(roundingMode, other.roundingMode);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, scale);
    result = Hash.combine(result, multiplier, useGroup);
    result = Hash.combine(result, multiplier, roundingMode);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("scale", scale)
        .append("useGroup", useGroup)
        .append("roundingMode", roundingMode)
        .toString();
  }
}