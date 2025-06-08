////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.text.Stripper;

/**
 * The codec which encode/decode {@link Charset} objects to/from strings.
 *
 * Note that the name of the charset is NOT its constant name. For example,
 * the name of {@link java.nio.charset.StandardCharsets#UTF_8} is "UTF-8"
 * instead of "UTF_8".
 *
 * @author Haixing Hu
 */
public class CharsetCodec implements Codec<Charset, String> {

  private static final Logger LOGGER = LoggerFactory.getLogger(CharsetCodec.class);

  public static final boolean DEFAULT_EMPTY_FOR_NULL = false;

  private boolean emptyForNull;

  public CharsetCodec() {
    emptyForNull = DEFAULT_EMPTY_FOR_NULL;
  }

  public CharsetCodec(final boolean emptyForNull) {
    this.emptyForNull = emptyForNull;
  }

  public boolean isEmptyForNull() {
    return emptyForNull;
  }

  public void setEmptyForNull(final boolean emptyForNull) {
    this.emptyForNull = emptyForNull;
  }

  @Override
  public String encode(@Nullable final Charset value) {
    if (value == null) {
      return (emptyForNull ? StringUtils.EMPTY : null);
    } else {
      return value.name();
    }
  }

  @Override
  public Charset decode(@Nullable final String str) throws DecodingException {
    final String text = new Stripper().strip(str);
    if (text == null || text.isEmpty()) {
      return null;
    } else {
      LOGGER.debug("Parsing charset: {}", text);
      try {
        return Charset.forName(text);
      } catch (final IllegalCharsetNameException e) {
        LOGGER.error("The charset name is illegal: {}", text);
        throw new DecodingException(e);
      } catch (final UnsupportedCharsetException e) {
        LOGGER.error("The charset is unsupported: {}", text);
        throw new DecodingException(e);
      }
    }
  }
}