////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.Stripper;
import ltd.qubit.commons.util.codec.Decoder;
import ltd.qubit.commons.util.codec.DecodingException;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.StringUtils.isEmpty;

/**
 * 使用解码器将字符串解码为指定值的反序列化器。
 *
 * @param <T>
 *     要反序列化的值的类型。
 * @author 胡海星
 */
@Immutable
public class DecoderKeyDeserializer<T> extends KeyDeserializer implements Serializable {

  @Serial
  private static final long serialVersionUID = 2275892683881205502L;

  /**
   * 值的类型。
   */
  protected final Class<T> valueClass;

  /**
   * 字符串到值的解码器。
   */
  protected final Decoder<String, T> decoder;

  /**
   * 构造一个 {@link DecoderKeyDeserializer}。
   *
   * @param valueClass
   *     值的类型。
   * @param decoder
   *     字符串到值的解码器。
   */
  protected DecoderKeyDeserializer(final Class<T> valueClass,
      final Decoder<String, T> decoder) {
    this.valueClass = requireNonNull("valueClass", valueClass);
    this.decoder = requireNonNull("decoder", decoder);
  }

  /**
   * {@inheritDoc}
   */
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