////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import ltd.qubit.commons.text.Stripper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import javax.annotation.Nullable;

import static ltd.qubit.commons.lang.Argument.requireNonNegative;
import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * The codec of the {@link Float} class.
 *
 * @author Haixing Hu
 */
public class FloatCodec implements Codec<Float, String> {

  /**
   * The default precision, i.e., the default number of digits after the decimal
   * points.
   */
  public static final int DEFAULT_PRECISION = 2;

  /**
   * The default mark of whether to use digit grouping.
   */
  public static final boolean DEFAULT_USE_GROUP = false;

  /**
   * The default rounding mode, which is {@link RoundingMode#HALF_UP}.
   */
  public static final RoundingMode DEFAULT_ROUND_MODE = RoundingMode.HALF_UP;

  private int precision;
  private boolean useGroup;
  private final DecimalFormat format;

  public FloatCodec() {
    this(DEFAULT_PRECISION, DEFAULT_USE_GROUP, DEFAULT_ROUND_MODE);
  }

  public FloatCodec(final int precision) {
    this(precision, DEFAULT_USE_GROUP, DEFAULT_ROUND_MODE);
  }

  public FloatCodec(final int precision, final boolean useGroup) {
    this(precision, useGroup, DEFAULT_ROUND_MODE);
  }

  public FloatCodec(final int precision, final boolean useGroup, final RoundingMode roundingMode) {
    this.precision = requireNonNegative("precision", precision);
    this.useGroup = useGroup;
    this.format = new DecimalFormat();
    this.format.setParseBigDecimal(true);
    this.format.setMinimumFractionDigits(precision);
    this.format.setMaximumFractionDigits(precision);
    this.format.setGroupingUsed(useGroup);
    this.format.setRoundingMode(requireNonNull("roundingMode", roundingMode));
  }

  public final int getPrecision() {
    return precision;
  }

  public final FloatCodec setPrecision(final int precision) {
    requireNonNegative("precision", precision);
    this.precision = precision;
    this.format.setParseBigDecimal(true);
    this.format.setMinimumFractionDigits(precision);
    this.format.setMaximumFractionDigits(precision);
    return this;
  }

  public final boolean isUseGroup() {
    return useGroup;
  }

  public final FloatCodec setUseGroup(final boolean useGroup) {
    this.useGroup = useGroup;
    this.format.setGroupingUsed(useGroup);
    return this;
  }

  public final RoundingMode getRoundingMode() {
    return format.getRoundingMode();
  }

  public final FloatCodec setRoundingMode(final RoundingMode mode) {
    format.setRoundingMode(mode);
    return this;
  }

  @Override
  public Float decode(@Nullable final String str) throws DecodingException {
    final String text = new Stripper().strip(str);
    if (text == null || text.isEmpty()) {
      return null;
    } else {
      try {
        final BigDecimal value = (BigDecimal) format.parse(text);
        return value.setScale(precision, format.getRoundingMode())
                    .stripTrailingZeros()
                    .floatValue();
      } catch (final ParseException e) {
        throw new DecodingException(e);
      }
    }
  }

  @Override
  public String encode(@Nullable final Float value) throws EncodingException {
    if (value == null) {
      return null;
    } else {
      final BigDecimal decimal = new BigDecimal(value)
                              .setScale(precision, format.getRoundingMode());
      return format.format(decimal);
    }
  }
}
