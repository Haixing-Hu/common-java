////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.io.IOException;
import java.io.Serial;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import ltd.qubit.commons.reflect.WriteMethodReference;
import ltd.qubit.commons.util.codec.Encoder;
import ltd.qubit.commons.util.codec.EncodingException;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 基于编码器的Jackson序列化器基类。
 *
 * @param <T>
 *     要序列化的值的类型。
 * @author 胡海星
 */
public class EncoderSerializer<T> extends StdSerializer<T> {

  @Serial
  private static final long serialVersionUID = 44431662165308463L;

  /**
   * 值的编码器。
   */
  protected final Encoder<T, String> encoder;

  /**
   * JSON生成器的写入方法引用。
   */
  protected final WriteMethodReference<JsonGenerator> writeMethod;

  /**
   * 构造一个{@link EncoderSerializer}实例。
   *
   * @param valueClass
   *     要序列化的值的类对象。
   * @param encoder
   *     要序列化的值的编码器。
   * @param writeMethod
   *     {@link JsonGenerator}写入编码值的方法引用。
   *     该引用可以是{@link JsonGenerator#writeString(String)}
   *     或{@link JsonGenerator#writeRawValue(String)}，取决于具体实现。
   */
  protected EncoderSerializer(final Class<T> valueClass,
      final Encoder<T, String> encoder,
      final WriteMethodReference<JsonGenerator> writeMethod) {
    super(valueClass);
    this.encoder = requireNonNull("encoder", encoder);
    this.writeMethod = requireNonNull("writeMethod", writeMethod);
  }

  /**
   * {@inheritDoc}
   */
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