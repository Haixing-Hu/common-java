////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 将{@link LocalDate}对象与字符串相互编解码的编解码器。
 *
 * @author 胡海星
 * @see LocalDateTimeCodec
 */
public class LocalDateCodec implements Codec<LocalDate, String> {

  private static final Logger LOGGER = LoggerFactory.getLogger(LocalDateCodec.class);

  public static final String DEFAULT_ENCODE_PATTERN = "uuuu-MM-dd";

  private final LocalDateTimeCodec delegate;

  public LocalDateCodec() {
    delegate = new LocalDateTimeCodec();
    delegate.setEncodePattern(DEFAULT_ENCODE_PATTERN);
  }

  public LocalDateCodec(@Nonnull final String pattern, final boolean emptyForNull) {
    delegate = new LocalDateTimeCodec(pattern, emptyForNull);
  }

  public LocalDateCodec(@Nonnull final String pattern, final boolean emptyForNull,
          final boolean stripBeforeParsing) {
    delegate = new LocalDateTimeCodec(pattern, pattern, emptyForNull, stripBeforeParsing);
  }

  public LocalDateCodec(final boolean emptyForNull) {
    delegate = new LocalDateTimeCodec(emptyForNull);
  }

  public LocalDateCodec(final boolean emptyForNull, final boolean trim) {
    delegate = new LocalDateTimeCodec(emptyForNull, trim);
  }

  public LocalDateCodec(@Nonnull final String encodePattern,
      @Nonnull final String decodePattern, final boolean emptyForNull) {
    delegate = new LocalDateTimeCodec(encodePattern, decodePattern, emptyForNull);
  }

  public LocalDateCodec(@Nonnull final String encodePattern,
      @Nonnull final String decodePattern, final boolean emptyForNull,
      final boolean trim) {
    delegate = new LocalDateTimeCodec(encodePattern, decodePattern, emptyForNull, trim);
  }

  public final String getEncodePattern() {
    return delegate.getEncodePattern();
  }

  public final LocalDateCodec setEncodePattern(final String encodePattern) {
    this.delegate.setEncodePattern(encodePattern);
    return this;
  }

  public final String[] getDecodePatterns() {
    return delegate.getDecodePatterns();
  }

  public final LocalDateCodec setDecodePatterns(final String[] decodePatterns) {
    this.delegate.setDecodePatterns(decodePatterns);
    return this;
  }

  public final boolean isEmptyForNull() {
    return delegate.isEmptyForNull();
  }

  public final LocalDateCodec setEmptyForNull(final boolean emptyForNull) {
    this.delegate.setEmptyForNull(emptyForNull);
    return this;
  }

  public final boolean isTrim() {
    return delegate.isTrim();
  }

  public final LocalDateCodec setTrim(final boolean trim) {
    this.delegate.setTrim(trim);
    return this;
  }

  @Override
  public String encode(@Nullable final LocalDate value) {
    final LocalDateTime dateTime =
        (value == null ? null : LocalDateTime.of(value.getYear(), value.getMonth(), value.getDayOfMonth(), 0, 0));
    return delegate.encode(dateTime);
  }

  @Override
  public LocalDate decode(@Nullable final String str) throws DecodingException {
    final LocalDateTime dateTime = delegate.decode(str);
    return (dateTime == null ? null : LocalDate.from(dateTime));
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final LocalDateCodec other = (LocalDateCodec) o;
    return Equality.equals(delegate, other.delegate);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, delegate);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("delegate", delegate)
        .toString();
  }
}