////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import ltd.qubit.commons.lang.DateUtils;

import java.time.Instant;
import javax.annotation.Nullable;

/**
 * 符合 ISO-8601 的时间戳编码器，其编码格式为 "uuuu-mm-dd'T'HH:mm:ss.SSS'Z'"。
 *
 * @author 胡海星
 * @see <a href="https://stackoverflow.com/questions/29014225/what-is-the-difference-between-year-and-year-of-era">
 * What is the difference between year and year-of-era?</a>
 */
public class IsoInstantCodec extends InstantCodec {

  public static String encodeTimestamp(@Nullable final Long epochMillis) {
    if (epochMillis == null) {
      return null;
    } else {
      final Instant time = Instant.ofEpochMilli(epochMillis);
      final IsoInstantCodec codec = new IsoInstantCodec();
      return codec.encode(time);
    }
  }

  public IsoInstantCodec() {
    // NOTE that 'u' represent the year, while 'y' represent the year-of-era.
    // see: https://stackoverflow.com/questions/29014225/what-is-the-difference-between-year-and-year-of-era
    super("uuuu-MM-dd'T'HH:mm:ss[.SSS]'Z'",
            "uuuu-MM-dd[[' ']['T']HH:mm[':'ss[.SSS]]][' ']['Z'][Z][z]",
            DateUtils.UTC_ZONE_ID, false);
  }

  public String encode(@Nullable final Instant value) {
    final String result = super.encode(value);
    return (result == null ? null : fixTrailingZeroMicroseconds(result));
  }

  private static final String TRAILING_ZERO_MICROSECONDS = ".000Z";

  private String fixTrailingZeroMicroseconds(final String str) {
    // 去除编码后全0的毫秒数部分，例如将 "2018-09-21T00:00:00.000Z" 转换为 "2018-09-21T00:00:00Z"
    String result = str;
    if (result.endsWith(TRAILING_ZERO_MICROSECONDS)) {
      result = result.substring(0, result.length() - TRAILING_ZERO_MICROSECONDS.length());
      result = result + "Z";
    }
    return result;
  }
}
