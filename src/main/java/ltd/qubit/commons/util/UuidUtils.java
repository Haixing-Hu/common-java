////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

import java.util.concurrent.ThreadLocalRandom;

import static ltd.qubit.commons.lang.CharUtils.LOWERCASE_DIGITS;
import static ltd.qubit.commons.text.NumberFormat.HEX_RADIX;

/**
 * 高性能的创建UUID的工具类.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @since 1.9.3
 * @see <a href="https://github.com/lets-mica/mica">Mica</a>
 */
public class UuidUtils {

  /**
   * 生成uuid，采用 jdk 9 的形式，优化性能.
   *
   * <p>Copy from mica：https://github.com/lets-mica/mica/blob/master/mica-core/src/main/java/net/dreamlu/mica/core/utils/StringUtil.java#L335
   *
   * <p>关于mica uuid生成方式的压测结果，可以参考：https://github.com/lets-mica/mica-jmh/wiki/uuid
   *
   * @return UUID
   */
  public static String getUuid() {
    final ThreadLocalRandom random = ThreadLocalRandom.current();
    final long lsb = random.nextLong();
    final long msb = random.nextLong();
    // stop checkstyle: MagicNumberCheck
    final char[] buf = new char[36];
    formatUnsignedLong(lsb, buf, 24, 12);
    buf[23] = '-';
    formatUnsignedLong(lsb >>> 48, buf, 19, 4);
    buf[18] = '-';
    formatUnsignedLong(msb, buf, 14, 4);
    buf[13] = '-';
    formatUnsignedLong(msb >>> 16, buf, 9, 4);
    buf[8] = '-';
    formatUnsignedLong(msb >>> 32, buf, 0, 8);
    // resume checkstyle: MagicNumberCheck
    return new String(buf);
  }

  /**
   * Format an unsigned long.
   *
   * <p>Copy from mica：https://github.com/lets-mica/mica/blob/master/mica-core/src/main/java/net/dreamlu/mica/core/utils/StringUtil.java#L348
   */
  private static void formatUnsignedLong(final long value, final char[] buf,
      final int offset, final int len) {
    long val = value;
    int charPos = offset + len;
    do {
      buf[--charPos] = LOWERCASE_DIGITS[((int) val) & MASK];
      val >>>= 4;
    } while (charPos > offset);
  }

  private static final int RADIX = HEX_RADIX;
  private static final int MASK = RADIX - 1;
}
