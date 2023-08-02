////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.io.IOException;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.text.Stripper;
import ltd.qubit.commons.util.codec.Decoder;
import ltd.qubit.commons.util.codec.DecodingException;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.StringUtils.isEmpty;

/**
 * The deserializer which use a decoder to decode string to the specified value.
 *
 * @param <T>
 *     the type of values to be deserialized.
 */
@Immutable
public class DecoderKeyDeserializer<T> extends KeyDeserializer {

  private static final long serialVersionUID = 2275892683881205502L;

  protected final Class<T> valueClass;
  protected final Decoder<String, T> decoder;

  protected DecoderKeyDeserializer(final Class<T> valueClass,
      final Decoder<String, T> decoder) {
    this.valueClass = requireNonNull("valueClass", valueClass);
    this.decoder = requireNonNull("decoder", decoder);
  }

  @Override
  public Object deserializeKey(final String key,
      final DeserializationContext context) throws IOException {
    final String str = new Stripper().strip(key);
    if (isEmpty(str)) {
      return null;
    } else {
      try {
        return decoder.decode(str);
      } catch (final DecodingException e) {
        throw context.weirdStringException(key, valueClass, e.getMessage());
      }
    }
  }
}
