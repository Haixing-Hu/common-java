////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import ltd.qubit.commons.reflect.WriteMethodReference;
import ltd.qubit.commons.util.codec.Encoder;
import ltd.qubit.commons.util.codec.EncodingException;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

public class EncoderSerializer<T> extends StdSerializer<T> {

  private static final long serialVersionUID = 44431662165308463L;

  protected final Encoder<T, String> encoder;
  protected final WriteMethodReference<JsonGenerator> writeMethod;

  /**
   * Construct a {@link EncoderSerializer}.
   *
   * @param valueClass
   *     the class object of the values to be serialized.
   * @param encoder
   *     the encoder of the values to be serialized.
   * @param writeMethod
   *     the reference to the method of the {@link JsonGenerator} to write
   *     the encoded value. The reference may be {@link JsonGenerator#writeString(String)}
   *     or {@link JsonGenerator#writeRawValue(String)}, depending on the
   *     implementation.
   */
  protected EncoderSerializer(final Class<T> valueClass,
      final Encoder<T, String> encoder,
      final WriteMethodReference<JsonGenerator> writeMethod) {
    super(valueClass);
    this.encoder = requireNonNull("encoder", encoder);
    this.writeMethod = requireNonNull("writeMethod", writeMethod);
  }

  @Override
  public void serialize(final T value, final JsonGenerator generator,
      final SerializerProvider provider) throws IOException {
    if (value == null) {
      generator.writeNull();
    } else {
      try {
        final String encoded = encoder.encode(value);
        writeMethod.invoke(generator, encoded);
      } catch (final EncodingException e) {
        throw new JsonGenerationException(e.getMessage(), generator);
      }
    }
  }
}
