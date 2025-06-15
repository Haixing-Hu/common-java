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
import java.math.BigDecimal;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualKeyDeserializer;

import ltd.qubit.commons.annotation.Money;
import ltd.qubit.commons.annotation.Round;
import ltd.qubit.commons.annotation.Scale;
import ltd.qubit.commons.util.codec.BigDecimalCodec;

/**
 * 用于{@link BigDecimal}对象的JACKSON键反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class BigDecimalKeyDeserializer extends DecoderKeyDeserializer<BigDecimal>
    implements ContextualKeyDeserializer {

  @Serial
  private static final long serialVersionUID = 8610277491732069052L;

  /**
   * 单例实例。
   */
  public static final BigDecimalKeyDeserializer INSTANCE = new BigDecimalKeyDeserializer();

  /**
   * 构造函数。
   */
  public BigDecimalKeyDeserializer() {
    super(BigDecimal.class, new BigDecimalCodec());
  }

  /**
   * 构造函数。
   *
   * @param codec
   *     指定的编解码器。
   */
  public BigDecimalKeyDeserializer(final BigDecimalCodec codec) {
    super(BigDecimal.class, codec);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public KeyDeserializer createContextual(final DeserializationContext context,
      final BeanProperty property) throws JsonMappingException {
    // Note that if we have to change the internal state of the codec,
    // this function should not change codec of this serializer, instead,
    // returns a NEW serializer instance with the new codec.
    final Scale scale = property.getAnnotation(Scale.class);
    final Round round = property.getAnnotation(Round.class);
    final Money money = property.getAnnotation(Money.class);
    final BigDecimalCodec newCodec = new BigDecimalCodec(scale, round, money);
    final BigDecimalCodec codec = (BigDecimalCodec) decoder;
    if (codec.equals(newCodec)) {
      return this;
    } else {
      return new BigDecimalKeyDeserializer(newCodec);
    }
  }
}
