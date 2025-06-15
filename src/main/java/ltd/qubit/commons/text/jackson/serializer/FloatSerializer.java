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
import ltd.qubit.commons.util.codec.FloatCodec;

/**
 * {@link Float}对象的Jackson序列化器。
 * <p>
 * 该序列化器支持通过{@link Scale}注解配置精度。
 *
 * @author 胡海星
 */
@Immutable
public class FloatSerializer extends EncoderSerializer<Float>
    implements ContextualSerializer {

  @Serial
  private static final long serialVersionUID = -9188496859529399954L;

  /**
   * FloatSerializer的单例实例。
   */
  public static final FloatSerializer INSTANCE = new FloatSerializer();

  /**
   * 构造一个默认的FloatSerializer实例。
   */
  public FloatSerializer() {
    this(new FloatCodec());
  }

  /**
   * 构造一个指定精度的FloatSerializer实例。
   *
   * @param precision
   *     小数精度。
   */
  public FloatSerializer(final int precision) {
    this(new FloatCodec(precision));
  }

  /**
   * 构造一个使用指定编解码器的FloatSerializer实例。
   *
   * @param codec
   *     Float编解码器。
   */
  public FloatSerializer(final FloatCodec codec) {
    super(Float.class, codec, JsonGenerator::writeRawValue);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<?> createContextual(final SerializerProvider prov,
      final BeanProperty property) {
    final FloatCodec codec = (FloatCodec) encoder;
    final Scale scale = property.getAnnotation(Scale.class);
    if ((scale != null) && (scale.value() != codec.getPrecision())) {
      return new FloatSerializer(scale.value());
    } else {
      return this;
    }
  }
}