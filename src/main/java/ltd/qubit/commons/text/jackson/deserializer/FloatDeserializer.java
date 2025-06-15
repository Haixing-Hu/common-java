////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.io.Serial;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import ltd.qubit.commons.annotation.Scale;
import ltd.qubit.commons.util.codec.FloatCodec;

/**
 * {@link Float} 对象的 JACKSON 反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class FloatDeserializer extends DecoderDeserializer<Float>
    implements ContextualDeserializer {

  @Serial
  private static final long serialVersionUID = 1072489984259176326L;

  public static final FloatDeserializer INSTANCE = new FloatDeserializer();

  /**
   * 构造一个 {@link FloatDeserializer} 对象。
   */
  public FloatDeserializer() {
    super(Float.class, new FloatCodec());
  }

  /**
   * 构造一个 {@link FloatDeserializer} 对象。
   *
   * @param precision
   *     指定的精度。
   */
  public FloatDeserializer(final int precision) {
    super(Float.class, new FloatCodec(precision));
  }

  /**
   * 构造一个 {@link FloatDeserializer} 对象。
   *
   * @param codec
   *     指定的编解码器。
   */
  public FloatDeserializer(final FloatCodec codec) {
    super(Float.class, codec);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<?> createContextual(final DeserializationContext context,
      final BeanProperty property) {
    final FloatCodec codec = (FloatCodec) decoder;
    final Scale scale = property.getAnnotation(Scale.class);
    if ((scale != null) && (scale.value() != codec.getPrecision())) {
      return new FloatDeserializer(scale.value());
    } else {
      return this;
    }
  }
}
