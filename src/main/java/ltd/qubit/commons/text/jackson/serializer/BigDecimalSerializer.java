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
import java.math.BigDecimal;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import ltd.qubit.commons.annotation.Money;
import ltd.qubit.commons.annotation.Round;
import ltd.qubit.commons.annotation.Scale;
import ltd.qubit.commons.util.codec.BigDecimalCodec;

/**
 * {@link BigDecimal}对象的Jackson序列化器。
 * <p>
 * 该序列化器支持通过注解配置精度、舍入模式和货币格式等属性。
 *
 * @author 胡海星
 */
@Immutable
public class BigDecimalSerializer extends EncoderSerializer<BigDecimal>
    implements ContextualSerializer {

  @Serial
  private static final long serialVersionUID = 7812000100733188757L;

  /**
   * BigDecimalSerializer的单例实例。
   */
  public static final BigDecimalSerializer INSTANCE = new BigDecimalSerializer();

  /**
   * 构造一个默认的BigDecimalSerializer实例。
   */
  public BigDecimalSerializer() {
    this(new BigDecimalCodec());
  }

  /**
   * 构造一个使用指定编解码器的BigDecimalSerializer实例。
   *
   * @param codec
   *     BigDecimal编解码器。
   */
  public BigDecimalSerializer(final BigDecimalCodec codec) {
    super(BigDecimal.class, codec, JsonGenerator::writeRawValue);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<?> createContextual(final SerializerProvider prov,
      final BeanProperty property) {
    // Note that if we have to change the internal state of the codec,
    // this function should not change codec of this serializer, instead,
    // returns a NEW serializer instance with the new codec.
    final BigDecimalCodec newCodec = new BigDecimalCodec();
    final Scale scale = property.getAnnotation(Scale.class);
    if (scale != null) {
      newCodec.setScale(scale.value());
    }
    final Round round = property.getAnnotation(Round.class);
    if (round != null) {
      newCodec.setRoundingMode(round.value());
    }
    final Money money = property.getAnnotation(Money.class);
    if (money != null) {
      newCodec.setScale(money.scale());
      newCodec.setRoundingMode(money.roundingModel());
      newCodec.setUseGroup(money.useGroup());
    }
    final BigDecimalCodec codec = (BigDecimalCodec) encoder;
    if (codec.equals(newCodec)) {
      return this;
    } else {
      return new BigDecimalSerializer(newCodec);
    }
  }
}