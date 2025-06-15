////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.io.Serial;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualKeyDeserializer;

import ltd.qubit.commons.annotation.Scale;
import ltd.qubit.commons.util.codec.FloatCodec;

/**
 * {@link Float} 对象的 JACKSON 键反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class FloatKeyDeserializer extends DecoderKeyDeserializer<Float>
    implements ContextualKeyDeserializer {

  @Serial
  private static final long serialVersionUID = -3137251329959339026L;

  /**
   * 默认实例。
   */
  public static final FloatKeyDeserializer INSTANCE = new FloatKeyDeserializer();

  /**
   * 构造一个 {@link FloatKeyDeserializer}。
   */
  public FloatKeyDeserializer() {
    super(Float.class, new FloatCodec());
  }

  /**
   * 构造一个指定精度的 {@link FloatKeyDeserializer}。
   *
   * @param precision
   *     精度。
   */
  public FloatKeyDeserializer(final int precision) {
    super(Float.class, new FloatCodec(precision));
  }

  /**
   * 构造一个使用指定编解码器的 {@link FloatKeyDeserializer}。
   *
   * @param codec
   *     Float 编解码器。
   */
  public FloatKeyDeserializer(final FloatCodec codec) {
    super(Float.class, codec);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public KeyDeserializer createContextual(final DeserializationContext context,
      final BeanProperty property) throws JsonMappingException {
    final FloatCodec codec = (FloatCodec) decoder;
    final Scale scale = property.getAnnotation(Scale.class);
    if ((scale != null) && (scale.value() != codec.getPrecision())) {
      return new FloatKeyDeserializer(scale.value());
    } else {
      return this;
    }
  }
}