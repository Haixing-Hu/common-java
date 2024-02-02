////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.concurrent.Immutable;

import cn.njzhyl.commons.i18n.LocaleUtils;
import cn.njzhyl.commons.io.OutputUtils;
import cn.njzhyl.commons.io.error.SerializationException;
import cn.njzhyl.commons.io.error.UnexpectedNullValueException;
import cn.njzhyl.commons.io.serialize.BinarySerializer;

import static cn.njzhyl.commons.io.InputUtils.readNullMark;
import static cn.njzhyl.commons.io.InputUtils.readString;
import static cn.njzhyl.commons.io.OutputUtils.writeString;

/**
 * The {@link BinarySerializer} for the {@link DatePattern} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class DatePatternBinarySerializer implements BinarySerializer {

  public static final DatePatternBinarySerializer INSTANCE = new DatePatternBinarySerializer();

  @Override
  public DatePattern deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    } else {
      final DatePattern result = new DatePattern();
      result.pattern = readString(in, false);
      result.format = readString(in, false);
      final String localeId = readString(in, true);
      result.locale = LocaleUtils.fromPosixLocale(localeId);
      result.matcher = null;
      result.dateFormat = null;
      return result;
    }
  }

  @Override
  public void serialize(final OutputStream out, final Object obj) throws IOException {
    if (! OutputUtils.writeNullMark(out, obj)) {
      final DatePattern dp;
      try {
        dp = (DatePattern) obj;
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      writeString(out, dp.pattern);
      writeString(out, dp.format);
      final String localeId = LocaleUtils.toPosixLocale(dp.locale);
      writeString(out, localeId);
    }
  }

}
