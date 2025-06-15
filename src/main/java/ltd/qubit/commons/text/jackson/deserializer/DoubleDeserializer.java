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
import ltd.qubit.commons.util.codec.DoubleCodec;

/**
 * {@link Double}对象的Jackson反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class DoubleDeserializer extends DecoderDeserializer<Double>
    implements ContextualDeserializer {

  @Serial
  private static final long serialVersionUID = -6589338450998945233L;

  /**
   * 单例实例。
   */
  public static final DoubleDeserializer INSTANCE = new DoubleDeserializer();

  /**
   * 构造Double反序列化器。
   */
  public DoubleDeserializer() {
    super(Double.class, new DoubleCodec());
  }

  /**
   * 构造具有指定精度的Double反序列化器。
   *
   * @param precision
   *     精度。
   */
  public DoubleDeserializer(final int precision) {
    super(Double.class, new DoubleCodec(precision));
  }

  /**
   * 构造具有指定编解码器的Double反序列化器。
   *
   * @param codec
   *     编解码器。
   */
  public DoubleDeserializer(final DoubleCodec codec) {
    super(Double.class, codec);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<?> createContextual(final DeserializationContext ctxt,
      final BeanProperty property) {
    final DoubleCodec codec = (DoubleCodec) decoder;
    final Scale scale = property.getAnnotation(Scale.class);
    if ((scale != null) && (scale.value() != codec.getPrecision())) {
      return new DoubleDeserializer(scale.value());
    } else {
      return this;
    }
  }
}