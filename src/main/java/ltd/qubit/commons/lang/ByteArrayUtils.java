////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteOrder;
import java.util.Date;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import ltd.qubit.commons.error.UnsupportedByteOrderException;
import ltd.qubit.commons.util.codec.HexCodec;

/**
 * 此类提供了操作字节数组的方法。
 *
 * @author 胡海星
 */
@ThreadSafe
public final class ByteArrayUtils {

  /**
   * 默认字节序，为大端序。
   *
   * <p><b>注意：</b>默认字节序使用标准的网络字节序，即大端序，
   * 根据 RFC 1700 规范。由 {@link SystemUtils#NATIVE_BYTE_ORDER} 指定的系统字节序
   * 取决于当前操作系统。在 Windows 上通常是小端序，在 Mac 或 Linux 上通常是大端序。
   */
  public static final ByteOrder DEFAULT_BYTE_ORDER = ByteOrder.BIG_ENDIAN;

  // stop checkstyle: MagicNumberCheck

  /**
   * 将字节数组转换为 {@code boolean} 值。
   *
   * @param value
   *     要转换的字节数组
   * @return
   *     转换后的 {@code boolean} 值。如果 {@code value} 为 {@code null} 或空数组，
   *     返回 {@link BooleanUtils#DEFAULT}；否则返回第一个字节是否不为零
   */
  public static boolean toBoolean(@Nullable final byte[] value) {
    return toBoolean(value, BooleanUtils.DEFAULT);
  }

  /**
   * 将字节数组转换为 {@code boolean} 值。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code boolean} 值。如果 {@code value} 为 {@code null} 或空数组，
   *     返回 {@code defaultValue}；否则返回第一个字节是否不为零
   */
  public static boolean toBoolean(@Nullable final byte[] value,
      final boolean defaultValue) {
    if ((value == null) || (value.length == 0)) {
      return defaultValue;
    } else {
      return value[0] != 0;
    }
  }

  /**
   * 将字节数组转换为 {@link Boolean} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @return
   *     转换后的 {@link Boolean} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}；否则返回第一个字节是否不为零
   */
  public static Boolean toBooleanObject(@Nullable final byte[] value) {
    return (value == null ? null : toBoolean(value));
  }

  /**
   * 将字节数组转换为 {@link Boolean} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@link Boolean} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}；否则返回第一个字节是否不为零
   */
  public static Boolean toBooleanObject(@Nullable final byte[] value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue : Boolean.valueOf(toBoolean(value)));
  }

  /**
   * 将字节数组转换为 {@code char} 值，使用默认字节序。
   *
   * @param value
   *     要转换的字节数组
   * @return
   *     转换后的 {@code char} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@link CharUtils#DEFAULT}
   */
  public static char toChar(@Nullable final byte[] value) {
    return toChar(value, CharUtils.DEFAULT, DEFAULT_BYTE_ORDER);
  }

  /**
   * 将字节数组转换为 {@code char} 值，使用指定的字节序。
   *
   * @param value
   *     要转换的字节数组
   * @param byteOrder
   *     字节序
   * @return
   *     转换后的 {@code char} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@link CharUtils#DEFAULT}
   */
  public static char toChar(@Nullable final byte[] value,
      final ByteOrder byteOrder) {
    return toChar(value, CharUtils.DEFAULT, byteOrder);
  }

  /**
   * 将字节数组转换为 {@code char} 值，使用默认字节序。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code char} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static char toChar(@Nullable final byte[] value,
      final char defaultValue) {
    return toChar(value, defaultValue, DEFAULT_BYTE_ORDER);
  }

  /**
   * 将字节数组转换为 {@code char} 值，使用指定的字节序。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @param byteOrder
   *     字节序
   * @return
   *     转换后的 {@code char} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static char toChar(@Nullable final byte[] value,
      final char defaultValue, final ByteOrder byteOrder) {
    if (value == null) {
      return defaultValue;
    } else {
      return (char) toShort(value, ShortUtils.DEFAULT, byteOrder);
    }
  }

  /**
   * 将字节数组转换为 {@code Character} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @return
   *     转换后的 {@code Character} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Character toCharObject(@Nullable final byte[] value) {
    return (value == null ? null : toChar(value));
  }

  /**
   * 将字节数组转换为 {@code Character} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @param byteOrder
   *     字节序
   * @return
   *     转换后的 {@code Character} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Character toCharObject(@Nullable final byte[] value,
      final ByteOrder byteOrder) {
    return (value == null ? null : toChar(value, byteOrder));
  }

  /**
   * 将字节数组转换为 {@code Character} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code Character} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Character toCharObject(@Nullable final byte[] value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue : Character.valueOf(toChar(value)));
  }

  /**
   * 将字节数组转换为 {@code Character} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @param byteOrder
   *     字节序
   * @return
   *     转换后的 {@code Character} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Character toCharObject(@Nullable final byte[] value,
      @Nullable final Character defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue
                          : Character.valueOf(toChar(value, byteOrder)));
  }

  /**
   * 将字节数组转换为 {@code byte} 值。
   *
   * @param value
   *     要转换的字节数组
   * @return
   *     转换后的 {@code byte} 值。如果 {@code value} 为 {@code null} 或空数组，
   *     返回 {@code ByteUtils.DEFAULT}
   */
  public static byte toByte(@Nullable final byte[] value) {
    return toByte(value, ByteUtils.DEFAULT);
  }

  /**
   * 将字节数组转换为 {@code byte} 值。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code byte} 值。如果 {@code value} 为 {@code null} 或空数组，
   *     返回 {@code defaultValue}；否则返回数组的第一个元素
   */
  public static byte toByte(@Nullable final byte[] value,
      final byte defaultValue) {
    if ((value == null) || (value.length == 0)) {
      return defaultValue;
    } else {
      return value[0];
    }
  }

  /**
   * 将字节数组转换为 {@code Byte} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @return
   *     转换后的 {@code Byte} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Byte toByteObject(@Nullable final byte[] value) {
    return (value == null ? null : toByte(value));
  }

  /**
   * 将字节数组转换为 {@code Byte} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code Byte} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Byte toByteObject(@Nullable final byte[] value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue : Byte.valueOf(toByte(value)));
  }

  /**
   * 将字节数组转换为 {@code short} 值。
   *
   * @param value
   *     要转换的字节数组
   * @return
   *     转换后的 {@code short} 值。如果 {@code value} 为 {@code null} 或长度不足，
   *     返回 {@code ShortUtils.DEFAULT}
   */
  public static short toShort(@Nullable final byte[] value) {
    return toShort(value, ShortUtils.DEFAULT, DEFAULT_BYTE_ORDER);
  }

  /**
   * 将字节数组转换为 {@code short} 值。
   *
   * @param value
   *     要转换的字节数组
   * @param byteOrder
   *     字节序
   * @return
   *     转换后的 {@code short} 值。如果 {@code value} 为 {@code null} 或长度不足，
   *     返回 {@code ShortUtils.DEFAULT}
   */
  public static short toShort(@Nullable final byte[] value,
      final ByteOrder byteOrder) {
    return toShort(value, ShortUtils.DEFAULT, byteOrder);
  }

  /**
   * 将字节数组转换为 {@code short} 值。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code short} 值。如果 {@code value} 为 {@code null} 或长度不足，
   *     返回 {@code defaultValue}
   */
  public static short toShort(@Nullable final byte[] value,
      final short defaultValue) {
    return toShort(value, defaultValue, DEFAULT_BYTE_ORDER);
  }

  /**
   * 将字节数组转换为 {@code short} 值。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @param byteOrder
   *     字节序
   * @return
   *     转换后的 {@code short} 值。如果 {@code value} 为 {@code null} 或长度不足，
   *     返回 {@code defaultValue}
   * @throws UnsupportedByteOrderException
   *     如果字节序不被支持
   */
  public static short toShort(@Nullable final byte[] value,
      final short defaultValue, final ByteOrder byteOrder) {
    if (value == null) {
      return defaultValue;
    }
    if (value.length < 2) { // invalid length
      return defaultValue;
    }
    if (byteOrder == ByteOrder.BIG_ENDIAN) {
      return toShortBigEndian(value);
    } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
      return toShortLittleEndian(value);
    } else {
      throw new UnsupportedByteOrderException(byteOrder);
    }
  }

  //  stop checkstyle: MagicNumberCheck

  private static short toShortBigEndian(final byte[] value) {
    switch (value.length) {
      case 0:
        return 0;
      case 1:
        return (short) (value[0] << 8);
      default:
        return (short) ((value[0] << 8) | (value[1] & 0xFF));
    }
  }

  private static short toShortLittleEndian(final byte[] value) {
    switch (value.length) {
      case 0:
        return 0;
      case 1:
        return value[0];
      default:
        return (short) ((value[1] << 8) | (value[0] & 0xFF));
    }
  }

  //  resume checkstyle: MagicNumberCheck

  /**
   * 将字节数组转换为 {@code Short} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @return
   *     转换后的 {@code Short} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Short toShortObject(@Nullable final byte[] value) {
    return (value == null ? null : toShort(value));
  }

  /**
   * 将字节数组转换为 {@code Short} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @param byteOrder
   *     字节序
   * @return
   *     转换后的 {@code Short} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Short toShortObject(@Nullable final byte[] value,
      final ByteOrder byteOrder) {
    return (value == null ? null : toShort(value, byteOrder));
  }

  /**
   * 将字节数组转换为 {@code Short} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code Short} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Short toShortObject(@Nullable final byte[] value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue : Short.valueOf(toShort(value)));
  }

  /**
   * 将字节数组转换为 {@code Short} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @param byteOrder
   *     字节序
   * @return
   *     转换后的 {@code Short} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Short toShortObject(@Nullable final byte[] value,
      @Nullable final Short defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue
                          : Short.valueOf(toShort(value, byteOrder)));
  }

  /**
   * 将字节数组转换为 {@code int} 值。
   *
   * @param value
   *     要转换的字节数组
   * @return
   *     转换后的 {@code int} 值。如果 {@code value} 为 {@code null} 或长度不足，
   *     返回 {@code IntUtils.DEFAULT}
   */
  public static int toInt(@Nullable final byte[] value) {
    return toInt(value, IntUtils.DEFAULT, DEFAULT_BYTE_ORDER);
  }

  /**
   * 将字节数组转换为 {@code int} 值。
   *
   * @param value
   *     要转换的字节数组
   * @param byteOrder
   *     字节序
   * @return
   *     转换后的 {@code int} 值。如果 {@code value} 为 {@code null} 或长度不足，
   *     返回 {@code IntUtils.DEFAULT}
   */
  public static int toInt(@Nullable final byte[] value,
      final ByteOrder byteOrder) {
    return toInt(value, IntUtils.DEFAULT, byteOrder);
  }

  /**
   * 将字节数组转换为 {@code int} 值。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code int} 值。如果 {@code value} 为 {@code null} 或长度不足，
   *     返回 {@code defaultValue}
   */
  public static int toInt(@Nullable final byte[] value,
      final int defaultValue) {
    return toInt(value, defaultValue, DEFAULT_BYTE_ORDER);
  }

  /**
   * 将字节数组转换为 {@code int} 值。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @param byteOrder
   *     字节序
   * @return
   *     转换后的 {@code int} 值。如果 {@code value} 为 {@code null} 或长度不足，
   *     返回 {@code defaultValue}
   * @throws UnsupportedByteOrderException
   *     如果字节序不被支持
   */
  public static int toInt(@Nullable final byte[] value, final int defaultValue,
      final ByteOrder byteOrder) {
    if (value == null) {
      return defaultValue;
    }
    if (value.length < 4) {
      // invalid length
      return defaultValue;
    }
    if (byteOrder == ByteOrder.BIG_ENDIAN) {
      return toIntBigEndian(value);
    } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
      return toIntLittleEndian(value);
    } else {
      throw new UnsupportedByteOrderException(byteOrder);
    }

  }

  //  stop checkstyle: MagicNumberCheck

  private static int toIntBigEndian(final byte[] value) {
    switch (value.length) {
      case 0:
        return 0;
      case 1:
        return (value[0] << 24);
      case 2:
        return (value[0] << 24)
            | (value[1] << 16);
      case 3:
        return (value[0] << 24)
            | (value[1] << 16)
            | (value[2] << 8);
      default:
        return (value[0] << 24)
            | (value[1] << 16)
            | (value[2] << 8)
            | value[3];
    }
  }

  private static int toIntLittleEndian(final byte[] value) {
    switch (value.length) {
      case 0:
        return 0;
      case 1:
        return value[0];
      case 2:
        return (value[1] << 8)
            | (value[0] & 0xFF);
      case 3:
        return (value[2] << 16)
            | (value[1] << 8)
            | (value[0] & 0xFF);
      default:
        return (value[3] << 24)
            | (value[2] << 16)
            | (value[1] << 8)
            | (value[0] & 0xFF);
    }
  }

  //  resume checkstyle: MagicNumberCheck

  /**
   * 将字节数组转换为 {@code Integer} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @return
   *     转换后的 {@code Integer} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Integer toIntObject(@Nullable final byte[] value) {
    return (value == null ? null : toInt(value));
  }

  /**
   * 将字节数组转换为 {@code Integer} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @param byteOrder
   *     字节序
   * @return
   *     转换后的 {@code Integer} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Integer toIntObject(@Nullable final byte[] value,
      final ByteOrder byteOrder) {
    return (value == null ? null : toInt(value, byteOrder));
  }

  /**
   * 将字节数组转换为 {@code Integer} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code Integer} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Integer toIntObject(@Nullable final byte[] value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : Integer.valueOf(toInt(value)));
  }

  /**
   * 将字节数组转换为 {@code Integer} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @param byteOrder
   *     字节序
   * @return
   *     转换后的 {@code Integer} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Integer toIntObject(@Nullable final byte[] value,
      @Nullable final Integer defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue
                          : Integer.valueOf(toInt(value, byteOrder)));
  }

  /**
   * 将字节数组转换为 {@code long} 值。
   *
   * @param value
   *     要转换的字节数组
   * @return
   *     转换后的 {@code long} 值。如果 {@code value} 为 {@code null} 或长度不足，
   *     返回 {@code LongUtils.DEFAULT}
   */
  public static long toLong(@Nullable final byte[] value) {
    return toLong(value, LongUtils.DEFAULT, DEFAULT_BYTE_ORDER);
  }

  /**
   * 将字节数组转换为 {@code long} 值。
   *
   * @param value
   *     要转换的字节数组
   * @param byteOrder
   *     字节序
   * @return
   *     转换后的 {@code long} 值。如果 {@code value} 为 {@code null} 或长度不足，
   *     返回 {@code LongUtils.DEFAULT}
   */
  public static long toLong(@Nullable final byte[] value,
      final ByteOrder byteOrder) {
    return toLong(value, LongUtils.DEFAULT, byteOrder);
  }

  /**
   * 将字节数组转换为 {@code long} 值。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code long} 值。如果 {@code value} 为 {@code null} 或长度不足，
   *     返回 {@code defaultValue}
   */
  public static long toLong(@Nullable final byte[] value,
      final long defaultValue) {
    return toLong(value, defaultValue, DEFAULT_BYTE_ORDER);
  }

  /**
   * 将字节数组转换为 {@code long} 值。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @param byteOrder
   *     字节序
   * @return
   *     转换后的 {@code long} 值。如果 {@code value} 为 {@code null} 或长度不足，
   *     返回 {@code defaultValue}
   * @throws UnsupportedByteOrderException
   *     如果字节序不被支持
   */
  public static long toLong(@Nullable final byte[] value,
      final long defaultValue, final ByteOrder byteOrder) {
    if (value == null) {
      return defaultValue;
    }
    if (value.length < Long.BYTES) { // invalid length
      return defaultValue;
    }
    if (byteOrder == ByteOrder.BIG_ENDIAN) {
      return toLongBigEndian(value);
    } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
      return toLongLittleEndian(value);
    } else {
      throw new UnsupportedByteOrderException(byteOrder);
    }
  }

  //  stop checkstyle: MagicNumberCheck

  private static long toLongBigEndian(final byte[] value) {
    switch (value.length) {
      case 0:
        return 0;
      case 1:
        return ((value[0] & 0xFFL) << 56);
      case 2:
        return ((value[0] & 0xFFL) << 56)
            | ((value[1] & 0xFFL) << 48);
      case 3:
        return ((value[0] & 0xFFL) << 56)
            | ((value[1] & 0xFFL) << 48)
            | ((value[2] & 0xFFL) << 40);
      case 4:
        return ((value[0] & 0xFFL) << 56)
            | ((value[1] & 0xFFL) << 48)
            | ((value[2] & 0xFFL) << 40)
            | ((value[3] & 0xFFL) << 32);
      case 5:
        return ((value[0] & 0xFFL) << 56)
            | ((value[1] & 0xFFL) << 48)
            | ((value[2] & 0xFFL) << 40)
            | ((value[3] & 0xFFL) << 32)
            | ((value[4] & 0xFFL) << 24);
      case 6:
        return ((value[0] & 0xFFL) << 56)
            | ((value[1] & 0xFFL) << 48)
            | ((value[2] & 0xFFL) << 40)
            | ((value[3] & 0xFFL) << 32)
            | ((value[4] & 0xFFL) << 24)
            | ((value[5] & 0xFFL) << 16);
      case 7:
        return ((value[0] & 0xFFL) << 56)
            | ((value[1] & 0xFFL) << 48)
            | ((value[2] & 0xFFL) << 40)
            | ((value[3] & 0xFFL) << 32)
            | ((value[4] & 0xFFL) << 24)
            | ((value[5] & 0xFFL) << 16)
            | ((value[6] & 0xFFL) << 8);
      default:
        return ((value[0] & 0xFFL) << 56)
            | ((value[1] & 0xFFL) << 48)
            | ((value[2] & 0xFFL) << 40)
            | ((value[3] & 0xFFL) << 32)
            | ((value[4] & 0xFFL) << 24)
            | ((value[5] & 0xFFL) << 16)
            | ((value[6] & 0xFFL) << 8)
            | (value[7] & 0xFFL);
    }
  }

  private static long toLongLittleEndian(final byte[] value) {
    switch (value.length) {
      case 0:
        return 0;
      case 1:
        return (value[0] & 0xFFL);
      case 2:
        return ((value[1] & 0xFFL) << 8)
            | (value[0] & 0xFFL);
      case 3:
        return ((value[2] & 0xFFL) << 16)
            | ((value[1] & 0xFFL) << 8)
            | (value[0] & 0xFFL);
      case 4:
        return ((value[3] & 0xFFL) << 24)
            | ((value[2] & 0xFFL) << 16)
            | ((value[1] & 0xFFL) << 8)
            | (value[0] & 0xFFL);
      case 5:
        return ((value[4] & 0xFFL) << 32)
            | ((value[3] & 0xFFL) << 24)
            | ((value[2] & 0xFFL) << 16)
            | ((value[1] & 0xFFL) << 8)
            | (value[0] & 0xFFL);
      case 6:
        return ((value[5] & 0xFFL) << 40)
            | ((value[4] & 0xFFL) << 32)
            | ((value[3] & 0xFFL) << 24)
            | ((value[2] & 0xFFL) << 16)
            | ((value[1] & 0xFFL) << 8)
            | (value[0] & 0xFFL);
      case 7:
        return ((value[6] & 0xFFL) << 48)
            | ((value[5] & 0xFFL) << 40)
            | ((value[4] & 0xFFL) << 32)
            | ((value[3] & 0xFFL) << 24)
            | ((value[2] & 0xFFL) << 16)
            | ((value[1] & 0xFFL) << 8)
            | (value[0] & 0xFFL);
      default:
        return ((value[7] & 0xFFL) << 56)
            | ((value[6] & 0xFFL) << 48)
            | ((value[5] & 0xFFL) << 40)
            | ((value[4] & 0xFFL) << 32)
            | ((value[3] & 0xFFL) << 24)
            | ((value[2] & 0xFFL) << 16)
            | ((value[1] & 0xFFL) << 8)
            | (value[0] & 0xFFL);
    }
  }

  //  resume checkstyle: MagicNumberCheck

  /**
   * 将字节数组转换为 {@code Long} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @return
   *     转换后的 {@code Long} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Long toLongObject(@Nullable final byte[] value) {
    return (value == null ? null : toLong(value));
  }

  /**
   * 将字节数组转换为 {@code Long} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @param byteOrder
   *     字节序
   * @return
   *     转换后的 {@code Long} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Long toLongObject(@Nullable final byte[] value,
      final ByteOrder byteOrder) {
    return (value == null ? null : toLong(value, byteOrder));
  }

  /**
   * 将字节数组转换为 {@code Long} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code Long} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Long toLongObject(@Nullable final byte[] value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : Long.valueOf(toLong(value)));
  }

  /**
   * 将字节数组转换为 {@code Long} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @param byteOrder
   *     字节序
   * @return
   *     转换后的 {@code Long} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Long toLongObject(@Nullable final byte[] value,
      @Nullable final Long defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue : Long.valueOf(toLong(value, byteOrder)));
  }

  /**
   * 将字节数组转换为 {@code float} 值。
   *
   * @param value
   *     要转换的字节数组
   * @return
   *     转换后的 {@code float} 值。如果 {@code value} 为 {@code null} 或长度不足，
   *     返回 {@code FloatUtils.DEFAULT}
   */
  public static float toFloat(@Nullable final byte[] value) {
    return toFloat(value, FloatUtils.DEFAULT, DEFAULT_BYTE_ORDER);
  }

  /**
   * 将字节数组转换为 {@code float} 值。
   *
   * @param value
   *     要转换的字节数组
   * @param byteOrder
   *     字节序
   * @return
   *     转换后的 {@code float} 值。如果 {@code value} 为 {@code null} 或长度不足，
   *     返回 {@code FloatUtils.DEFAULT}
   */
  public static float toFloat(@Nullable final byte[] value,
      final ByteOrder byteOrder) {
    return toFloat(value, FloatUtils.DEFAULT, byteOrder);
  }

  /**
   * 将字节数组转换为 {@code float} 值。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code float} 值。如果 {@code value} 为 {@code null} 或长度不足，
   *     返回 {@code defaultValue}
   */
  public static float toFloat(@Nullable final byte[] value,
      final float defaultValue) {
    return toFloat(value, defaultValue, DEFAULT_BYTE_ORDER);
  }

  /**
   * 将字节数组转换为 {@code float} 值。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @param byteOrder
   *     字节序
   * @return
   *     转换后的 {@code float} 值。如果 {@code value} 为 {@code null} 或长度不足，
   *     返回 {@code defaultValue}
   */
  public static float toFloat(@Nullable final byte[] value,
      final float defaultValue, final ByteOrder byteOrder) {
    if (value == null) {
      return defaultValue;
    } else {
      final int bits = toInt(value, byteOrder);
      return Float.intBitsToFloat(bits);
    }
  }

  /**
   * 将字节数组转换为 {@code Float} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @return
   *     转换后的 {@code Float} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Float toFloatObject(@Nullable final byte[] value) {
    return (value == null ? null : toFloat(value));
  }

  /**
   * 将字节数组转换为 {@code Float} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @param byteOrder
   *     字节序
   * @return
   *     转换后的 {@code Float} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Float toFloatObject(@Nullable final byte[] value, final ByteOrder byteOrder) {
    return (value == null ? null : toFloat(value, byteOrder));
  }

  /**
   * 将字节数组转换为 {@code Float} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code Float} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Float toFloatObject(@Nullable final byte[] value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : Float.valueOf(toFloat(value)));
  }

  /**
   * 将字节数组转换为 {@code Float} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @param byteOrder
   *     字节序
   * @return
   *     转换后的 {@code Float} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Float toFloatObject(@Nullable final byte[] value,
      @Nullable final Float defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue
                          : Float.valueOf(toFloat(value, byteOrder)));
  }

  /**
   * 将字节数组转换为 {@code double} 值。
   *
   * @param value
   *     要转换的字节数组
   * @return
   *     转换后的 {@code double} 值。如果 {@code value} 为 {@code null} 或长度不足，
   *     返回 {@code DoubleUtils.DEFAULT}
   */
  public static double toDouble(@Nullable final byte[] value) {
    return toDouble(value, DoubleUtils.DEFAULT, DEFAULT_BYTE_ORDER);
  }

  /**
   * 将字节数组转换为 {@code double} 值。
   *
   * @param value
   *     要转换的字节数组
   * @param byteOrder
   *     字节序
   * @return
   *     转换后的 {@code double} 值。如果 {@code value} 为 {@code null} 或长度不足，
   *     返回 {@code DoubleUtils.DEFAULT}
   */
  public static double toDouble(@Nullable final byte[] value,
      final ByteOrder byteOrder) {
    return toDouble(value, DoubleUtils.DEFAULT, byteOrder);
  }

  /**
   * 将字节数组转换为 {@code double} 值。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code double} 值。如果 {@code value} 为 {@code null} 或长度不足，
   *     返回 {@code defaultValue}
   */
  public static double toDouble(@Nullable final byte[] value,
      final double defaultValue) {
    return toDouble(value, defaultValue, DEFAULT_BYTE_ORDER);
  }

  /**
   * 将字节数组转换为 {@code double} 值。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @param byteOrder
   *     字节序
   * @return
   *     转换后的 {@code double} 值。如果 {@code value} 为 {@code null} 或长度不足，
   *     返回 {@code defaultValue}
   */
  public static double toDouble(@Nullable final byte[] value,
      final double defaultValue, final ByteOrder byteOrder) {
    if (value == null) {
      return defaultValue;
    } else {
      final long bits = toLong(value, byteOrder);
      return Double.longBitsToDouble(bits);
    }
  }

  /**
   * 将字节数组转换为 {@code Double} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @return
   *     转换后的 {@code Double} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Double toDoubleObject(@Nullable final byte[] value) {
    return (value == null ? null : toDouble(value));
  }

  /**
   * 将字节数组转换为 {@code Double} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @param byteOrder
   *     字节序
   * @return
   *     转换后的 {@code Double} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Double toDoubleObject(@Nullable final byte[] value,
      final ByteOrder byteOrder) {
    return (value == null ? null : toDouble(value, byteOrder));
  }

  /**
   * 将字节数组转换为 {@code Double} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code Double} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Double toDoubleObject(@Nullable final byte[] value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : Double.valueOf(toDouble(value)));
  }

  /**
   * 将字节数组转换为 {@code Double} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @param byteOrder
   *     字节序
   * @return
   *     转换后的 {@code Double} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Double toDoubleObject(@Nullable final byte[] value,
      @Nullable final Double defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue
                          : Double.valueOf(toDouble(value, byteOrder)));
  }

  /**
   * 将字节数组转换为十六进制字符串。
   *
   * @param value
   *     要转换的字节数组
   * @return
   *     转换后的十六进制字符串。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static String toString(@Nullable final byte[] value) {
    return toString(value, null);
  }

  /**
   * 将字节数组转换为十六进制字符串。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的十六进制字符串。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static String toString(@Nullable final byte[] value,
      @Nullable final String defaultValue) {
    if (value == null) {
      return defaultValue;
    } else {
      return new HexCodec().encode(value);
    }
  }

  /**
   * 将字节数组转换为 {@code Date} 对象。
   *
   * @param value
   *     要转换的字节数组，包含时间戳的毫秒数
   * @return
   *     转换后的 {@code Date} 对象。如果 {@code value} 为 {@code null} 或长度不足，
   *     返回 {@code null}
   */
  public static Date toDate(@Nullable final byte[] value) {
    return toDate(value, null, DEFAULT_BYTE_ORDER);
  }

  /**
   * 将字节数组转换为 {@code Date} 对象。
   *
   * @param value
   *     要转换的字节数组，包含时间戳的毫秒数
   * @param byteOrder
   *     字节序
   * @return
   *     转换后的 {@code Date} 对象。如果 {@code value} 为 {@code null} 或长度不足，
   *     返回 {@code null}
   */
  public static Date toDate(@Nullable final byte[] value,
      final ByteOrder byteOrder) {
    return toDate(value, null, byteOrder);
  }

  /**
   * 将字节数组转换为 {@code Date} 对象。
   *
   * @param value
   *     要转换的字节数组，包含时间戳的毫秒数
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code Date} 对象。如果 {@code value} 为 {@code null} 或长度不足，
   *     返回 {@code defaultValue}
   */
  public static Date toDate(@Nullable final byte[] value,
      @Nullable final Date defaultValue) {
    return toDate(value, defaultValue, DEFAULT_BYTE_ORDER);
  }

  /**
   * 将字节数组转换为 {@code Date} 对象。
   *
   * @param value
   *     要转换的字节数组，包含时间戳的毫秒数
   * @param defaultValue
   *     默认值
   * @param byteOrder
   *     字节序
   * @return
   *     转换后的 {@code Date} 对象。如果 {@code value} 为 {@code null} 或长度不足，
   *     返回 {@code defaultValue}
   */
  public static Date toDate(@Nullable final byte[] value,
      @Nullable final Date defaultValue, final ByteOrder byteOrder) {
    if (value == null) {
      return defaultValue;
    } else {
      final long time = toLong(value, byteOrder);
      return new Date(time);
    }
  }

  /**
   * 将字节数组转换为 {@code Class} 对象。
   *
   * @param value
   *     要转换的字节数组，包含类名的字符串
   * @return
   *     转换后的 {@code Class} 对象。如果 {@code value} 为 {@code null} 或类不存在，
   *     返回 {@code null}
   */
  public static Class<?> toClass(@Nullable final byte[] value) {
    return toClass(value, null);
  }

  /**
   * 将字节数组转换为 {@code Class} 对象。
   *
   * @param value
   *     要转换的字节数组，包含类名的字符串
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code Class} 对象。如果 {@code value} 为 {@code null} 或类不存在，
   *     返回 {@code defaultValue}
   */
  public static Class<?> toClass(@Nullable final byte[] value,
      @Nullable final Class<?> defaultValue) {
    if (value == null) {
      return defaultValue;
    } else {
      final String className = toString(value);
      try {
        return ClassUtils.getClass(className);
      } catch (final ClassNotFoundException e) {
        return defaultValue;
      }
    }
  }

  /**
   * 将字节数组转换为 {@code BigInteger} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @return
   *     转换后的 {@code BigInteger} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}；如果长度为0，返回 {@code BigInteger.ZERO}
   */
  public static BigInteger toBigInteger(@Nullable final byte[] value) {
    return toBigInteger(value, null);
  }

  /**
   * 将字节数组转换为 {@code BigInteger} 对象。
   *
   * @param value
   *     要转换的字节数组
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code BigInteger} 对象。如果 {@code value} 为 {@code null} 或转换失败，
   *     返回 {@code defaultValue}；如果长度为0，返回 {@code BigInteger.ZERO}
   */
  public static BigInteger toBigInteger(@Nullable final byte[] value,
      @Nullable final BigInteger defaultValue) {
    if (value == null) {
      return defaultValue;
    } else if (value.length == 0) {
      return BigInteger.ZERO;
    } else {
      try {
        return new BigInteger(value);
      } catch (final NumberFormatException e) {
        return defaultValue;
      }
    }
  }

  /**
   * 将字节数组转换为 {@code BigDecimal} 对象。
   *
   * @param value
   *     要转换的字节数组，前4个字节为精度，其余字节为 {@code BigInteger} 的字节数组
   * @return
   *     转换后的 {@code BigDecimal} 对象。如果 {@code value} 为 {@code null} 或长度不足，
   *     返回 {@code null}；如果长度为0，返回 {@code BigDecimal.ZERO}
   */
  public static BigDecimal toBigDecimal(@Nullable final byte[] value) {
    return toBigDecimal(value, null);
  }

  /**
   * 将字节数组转换为 {@code BigDecimal} 对象。
   *
   * @param value
   *     要转换的字节数组，前4个字节为精度，其余字节为 {@code BigInteger} 的字节数组
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code BigDecimal} 对象。如果 {@code value} 为 {@code null}、长度不足或转换失败，
   *     返回 {@code defaultValue}；如果长度为0，返回 {@code BigDecimal.ZERO}
   */
  public static BigDecimal toBigDecimal(@Nullable final byte[] value,
      @Nullable final BigDecimal defaultValue) {
    if (value == null) {
      return defaultValue;
    } else if (value.length == 0) {
      return BigDecimal.ZERO;
    } else if (value.length <= 4) {
      // invalid byte array
      return defaultValue;
    } else {
      final int scale = toInt(value, DEFAULT_BYTE_ORDER);
      final byte[] temp = new byte[value.length - 4];
      System.arraycopy(value, 4, temp, 0, temp.length);
      final BigInteger unscaledValue;
      try {
        unscaledValue = new BigInteger(temp);
      } catch (final NumberFormatException e) {
        // invalid byte array
        return defaultValue;
      }
      return new BigDecimal(unscaledValue, scale);
    }
  }
  // resume checkstyle: MagicNumberCheck
}