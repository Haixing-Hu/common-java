////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.math.BigDecimal;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import static ltd.qubit.commons.lang.BigDecimalUtils.limitPrecision;

/**
 * The encoder and decoder of geolocation coordinates.
 *
 * @author Haixing Hu
 */
@Immutable
public class LocationCoordinateCodec extends BigDecimalCodec {

  /**
   * The coordinate accuracy, that is, the number of digits after the decimal
   * point
   * <p>
   * The default is reserved for {@value} digits.
   */
  public static final int SCALE = 6;

  public static final BigDecimal MIN_COORDINATE = BigDecimal.valueOf(-180);

  public static final BigDecimal MAX_COORDINATE = BigDecimal.valueOf(180);

  public static final BigDecimal ROUND_DEGREE = BigDecimal.valueOf(360);

  public static final LocationCoordinateCodec INSTANCE = new LocationCoordinateCodec();

  public LocationCoordinateCodec() {
    super(SCALE);
  }

  /**
   * Normalizes a coordinate value with the default scale.
   *
   * @param value
   *     the coordinate value to be normalized.
   * @return
   *     the normalized coordinate value.
   */
  @Nullable
  public static BigDecimal normalize(@Nullable final BigDecimal value) {
    return normalize(value, SCALE);
  }

  /**
   * Normalizes a coordinate value with the specified scale.
   *
   * @param value
   *     the coordinate value to be normalized.
   * @param scale
   *     the specified scale, which specifies the coordinate accuracy, that is,
   *     the number of digits after the decimal point.
   * @return
   *     the normalized coordinate value.
   */
  @Nullable
  public static BigDecimal normalize(@Nullable final BigDecimal value, final int scale) {
    if (value == null) {
      return null;
    }
    BigDecimal v = limitPrecision(value, scale);
    v = v.remainder(ROUND_DEGREE);
    if (v.compareTo(MIN_COORDINATE) < 0) {
      v = v.add(ROUND_DEGREE);
    } else if (v.compareTo(MAX_COORDINATE) > 0) {
      v = v.subtract(ROUND_DEGREE);
    }
    return v;
  }
}
