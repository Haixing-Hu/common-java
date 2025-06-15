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
import ltd.qubit.commons.util.codec.DoubleCodec;

/**
 * {@link Double} 对象的 JACKSON 键反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class DoubleKeyDeserializer extends DecoderKeyDeserializer<Double>
    implements ContextualKeyDeserializer {

  @Serial
  private static final long serialVersionUID = -2569140073681261776L;

  /**
   * 默认实例。
   */
  public static final DoubleKeyDeserializer INSTANCE = new DoubleKeyDeserializer();

  /**
   * 构造一个 {@link DoubleKeyDeserializer}。
   */
  public DoubleKeyDeserializer() {
    super(Double.class, new DoubleCodec());
  }

  /**
   * 构造一个指定精度的 {@link DoubleKeyDeserializer}。
   *
   * @param precision
   *     精度。
   */
  public DoubleKeyDeserializer(final int precision) {
    super(Double.class, new DoubleCodec(precision));
  }

  /**
   * 构造一个使用指定编解码器的 {@link DoubleKeyDeserializer}。
   *
   * @param codec
   *     Double 编解码器。
   */
  public DoubleKeyDeserializer(final DoubleCodec codec) {
    super(Double.class, codec);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public KeyDeserializer createContextual(final DeserializationContext context,
      final BeanProperty property) throws JsonMappingException {
    final DoubleCodec codec = (DoubleCodec) decoder;
    final Scale scale = property.getAnnotation(Scale.class);
    if ((scale != null) && (scale.value() != codec.getPrecision())) {
      return new DoubleKeyDeserializer(scale.value());
    } else {
      return this;
    }
  }
}