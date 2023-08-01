////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.text.Stripper;

/**
 * The codec which decode/encode booleans from/to strings.
 *
 * @author Haixing Hu
 */
public class BooleanCodec implements Codec<Boolean, String> {

  public static final String DEFAULT_TRUE_VALUE = "true";

  public static final String DEFAULT_FALSE_VALUE = "false";

  public static final boolean DEFAULT_EMPTY_FOR_NULL = false;

  public static final boolean DEFAULT_IGNORE_CASE = true;

  public static final boolean DEFAULT_TRIM = true;

  public static final boolean DEFAULT_STRICT_FALSE = false;

  private String trueValue = DEFAULT_TRUE_VALUE;
  private String falseValue = DEFAULT_FALSE_VALUE;
  private boolean emptyForNull = DEFAULT_EMPTY_FOR_NULL;
  private boolean ignoreCase = DEFAULT_IGNORE_CASE;
  private boolean trim = DEFAULT_TRIM;
  private boolean strictFalse = DEFAULT_STRICT_FALSE;

  public BooleanCodec() {
    //  empty
  }

  public BooleanCodec(final String trueValue, final String falseValue) {
    this(trueValue, falseValue, DEFAULT_EMPTY_FOR_NULL);
  }

  public BooleanCodec(final String trueValue, final String falseValue, final boolean emptyForNull) {
    this.trueValue = trueValue;
    this.falseValue = falseValue;
    this.emptyForNull = emptyForNull;
  }

  public final String getTrueValue() {
    return trueValue;
  }

  public final BooleanCodec setTrueValue(final String trueValue) {
    this.trueValue = trueValue;
    return this;
  }

  public final String getFalseValue() {
    return falseValue;
  }

  public final BooleanCodec setFalseValue(final String falseValue) {
    this.falseValue = falseValue;
    return this;
  }

  public final boolean isEmptyForNull() {
    return emptyForNull;
  }

  public final BooleanCodec setEmptyForNull(final boolean emptyForNull) {
    this.emptyForNull = emptyForNull;
    return this;
  }

  public final boolean isIgnoreCase() {
    return ignoreCase;
  }

  public final BooleanCodec setIgnoreCase(final boolean ignoreCase) {
    this.ignoreCase = ignoreCase;
    return this;
  }

  public final boolean isTrim() {
    return trim;
  }

  public final BooleanCodec setTrim(final boolean trim) {
    this.trim = trim;
    return this;
  }

  public final boolean isStrictFalse() {
    return strictFalse;
  }

  public final BooleanCodec setStrictFalse(final boolean strictFalse) {
    this.strictFalse = strictFalse;
    return this;
  }

  @Override
  public String encode(@Nullable final Boolean value) {
    if (value == null) {
      return (emptyForNull ? StringUtils.EMPTY : null);
    } else {
      return (value ? trueValue : falseValue);
    }
  }

  @Override
  public Boolean decode(@Nullable final String str) throws DecodingException {
    if (str == null) {
      return null;
    }
    final String text = (trim ? new Stripper().strip(str) : str);
    if (text.length() == 0) {
      if (emptyForNull) {
        return null;
      }
    }
    if (ignoreCase) {
      if (text.equalsIgnoreCase(trueValue)) {
        return Boolean.TRUE;
      } else if (text.equalsIgnoreCase(falseValue)) {
        return Boolean.FALSE;
      }
    } else {
      if (text.equals(trueValue)) {
        return Boolean.TRUE;
      } else if (text.equals(falseValue)) {
        return Boolean.FALSE;
      }
    }
    if (strictFalse) {
      throw new DecodingException("Invalid boolean value: '" + str + "'");
    } else {
      return Boolean.FALSE;
    }
  }
}
