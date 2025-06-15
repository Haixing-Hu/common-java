////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.io.Serial;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import ltd.qubit.commons.annotation.Scale;
import ltd.qubit.commons.util.codec.DoubleCodec;

/**
 * {@link Double}对象的Jackson序列化器。
 * <p>
 * 该序列化器支持通过{@link Scale}注解配置精度。
 *
 * @author 胡海星
 */
@Immutable
public class DoubleSerializer extends EncoderSerializer<Double>
    implements ContextualSerializer {

  @Serial
  private static final long serialVersionUID = 2198946838706943360L;

  /**
   * DoubleSerializer的单例实例。
   */
  public static final DoubleSerializer INSTANCE = new DoubleSerializer();

  /**
   * 构造一个默认的DoubleSerializer实例。
   */
  public DoubleSerializer() {
    this(new DoubleCodec());
  }

  /**
   * 构造一个指定精度的DoubleSerializer实例。
   *
   * @param precision
   *     小数精度。
   */
  public DoubleSerializer(final int precision) {
    this(new DoubleCodec(precision));
  }

  /**
   * 构造一个使用指定编解码器的DoubleSerializer实例。
   *
   * @param codec
   *     Double编解码器。
   */
  public DoubleSerializer(final DoubleCodec codec) {
    super(Double.class, codec, JsonGenerator::writeRawValue);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<?> createContextual(final SerializerProvider prov,
      final BeanProperty property) {
    final DoubleCodec codec = (DoubleCodec) encoder;
    final Scale scale = property.getAnnotation(Scale.class);
    if ((scale != null) && (scale.value() != codec.getPrecision())) {
      return new DoubleSerializer(scale.value());
    } else {
      return this;
    }
  }
}