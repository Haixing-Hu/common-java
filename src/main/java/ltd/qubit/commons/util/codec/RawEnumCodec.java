////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.text.Stripper;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.StringUtils.isEmpty;

@SuppressWarnings("rawtypes")
public class RawEnumCodec implements Codec<Enum, String> {

  private final Class<? extends Enum> enumClass;

  public RawEnumCodec() {
    this.enumClass = Enum.class;
  }

  public RawEnumCodec(final Class<? extends Enum> enumClass) {
    this.enumClass = requireNonNull("enumClass", enumClass);
  }

  public final Class<? extends Enum> getEnumClass() {
    return enumClass;
  }

  @Override
  public Enum decode(final String source) throws DecodingException {
    final String str = new Stripper().strip(source);
    if (isEmpty(str)) {
      return null;
    }
    // normalize the enum names
    final String name = str.toUpperCase().replace('-', '_');
    final Enum[] enumValues = enumClass.getEnumConstants();
    for (final Enum e : enumValues) {
      if (e.name().toUpperCase().equals(name)) {
        return e;
      }
    }
    final String message = String.format("Cannot deserialize value of type `%s`"
      + " from string \"%s\": not one of the values accepted for Enum class: %s",
        enumClass.getName(), source, ArrayUtils.toString(enumValues));
    throw new DecodingException(message);
  }

  @Override
  public String encode(final Enum source) {
    return (source == null ? null : source.name());
  }
}
