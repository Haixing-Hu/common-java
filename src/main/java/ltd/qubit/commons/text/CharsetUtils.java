////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定义通用字符集的常量。
 *
 * @author 胡海星
 */
@ThreadSafe
public final class CharsetUtils {

  /**
   * 字符集名称：ASCII
   */
  public static final String NAME_ASCII         = "ASCII";

  /**
   * 字符集名称：ISO-8859-1
   */
  public static final String NAME_ISO_8859_1    = "ISO-8859-1";

  /**
   * 字符集名称：LATIN-1
   */
  @Deprecated
  public static final String NAME_LATIN_1       = NAME_ISO_8859_1;

  /**
   * 字符集名称：UTF-8
   */
  public static final String NAME_UTF_8         = "UTF-8";

  /**
   * 字符集名称：UTF-16LE
   */
  public static final String NAME_UTF_16LE      = "UTF-16LE";

  /**
   * 字符集名称：UTF-16BE
   */
  public static final String NAME_UTF_16BE      = "UTF-16BE";

  /**
   * 字符集名称：UTF-16
   */
  public static final String NAME_UTF_16        = "UTF-16";

  /**
   * 字符集名称：UTF-32LE
   */
  public static final String NAME_UTF_32LE      = "UTF-32LE";

  /**
   * 字符集名称：UTF-32BE
   */
  public static final String NAME_UTF_32BE      = "UTF-32BE";

  /**
   * 字符集名称：UTF-32
   */
  public static final String NAME_UTF_32        = "UTF-32";

  /**
   * 字符集名称：GB2312
   */
  public static final String NAME_GB2312        = "GB2312";

  /**
   * 字符集名称：GBK
   */
  public static final String NAME_GBK           = "GBK";

  /**
   * 字符集名称：GB18030
   */
  public static final String NAME_GB18030       = "GB18030";

  /**
   * 字符集名称：BIG5
   */
  public static final String NAME_BIG5          = "BIG5";

  /**
   * 请改用{@link java.nio.charset.StandardCharsets#US_ASCII}。
   */
  @Deprecated
  public static final Charset ASCII             = StandardCharsets.US_ASCII;

  /**
   * 请改用{@link java.nio.charset.StandardCharsets#ISO_8859_1}。
   */
  @Deprecated
  public static final Charset ISO_8859_1        = StandardCharsets.ISO_8859_1;

  /**
   * 请改用{@link java.nio.charset.StandardCharsets#ISO_8859_1}。
   */
  @Deprecated
  public static final Charset LATIN_1           = ISO_8859_1;

  /**
   * 请改用{@link java.nio.charset.StandardCharsets#UTF_8}。
   */
  @Deprecated
  public static final Charset UTF_8             = StandardCharsets.UTF_8;

  /**
   * 请改用{@link java.nio.charset.StandardCharsets#UTF_16LE}。
   */
  @Deprecated
  public static final Charset UTF_16LE          = StandardCharsets.UTF_16LE;

  /**
   * 请改用{@link java.nio.charset.StandardCharsets#UTF_16BE}。
   */
  @Deprecated
  public static final Charset UTF_16BE          = StandardCharsets.UTF_16BE;

  /**
   * 请改用{@link java.nio.charset.StandardCharsets#UTF_16}。
   */
  @Deprecated
  public static final Charset UTF_16            = StandardCharsets.UTF_16;

  /**
   * 字符集：GB2312
   */
  public static final Charset GB2312            = Charset.forName(NAME_GB2312);

  /**
   * 字符集：GBK
   */
  public static final Charset GBK               = Charset.forName(NAME_GBK);

  /**
   * 字符集：GB18030
   */
  public static final Charset GB18030           = Charset.forName(NAME_GB18030);

  /**
   * 字符集：BIG5
   */
  public static final Charset BIG5              = Charset.forName(NAME_BIG5);

  private static final Logger LOGGER = LoggerFactory.getLogger(CharsetUtils.class);

  /**
   * 根据字符集名称创建字符集对象，如果失败则返回默认字符集。
   *
   * @param charsetName 字符集名称，可以为null
   * @param defaultCharset 默认字符集，可以为null
   * @return 字符集对象，如果charsetName为null或无效，则返回defaultCharset
   */
  public static Charset forName(@Nullable final String charsetName,
      @Nullable final Charset defaultCharset) {
    Charset charset;
    if (charsetName == null) {
      LOGGER.debug("The charset name is null, use default '{}'", defaultCharset);
      charset = defaultCharset;
    } else {
      try {
        charset = Charset.forName(charsetName);
      } catch (final Exception e) {
        LOGGER.warn("Failed to create Charset of name '{}', use the default '{}'",
            charsetName, defaultCharset);
        charset = defaultCharset;
      }
    }
    return charset;
  }

  private CharsetUtils() {}
}