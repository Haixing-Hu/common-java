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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import ltd.qubit.commons.annotation.Money;
import ltd.qubit.commons.util.codec.MoneyCodec;

/**
 * 货币值的Jackson序列化器，货币值用{@link Money}对象表示。
 *
 * @author 胡海星
 */
@Immutable
public class MoneySerializer extends EncoderSerializer<BigDecimal>
    implements ContextualSerializer {

  @Serial
  private static final long serialVersionUID = -7252111294856231946L;

  /**
   * MoneySerializer的单例实例。
   */
  public static final MoneySerializer INSTANCE = new MoneySerializer();

  /**
   * 构造一个使用默认编码器的MoneySerializer实例。
   */
  public MoneySerializer() {
    this(new MoneyCodec());
  }

  /**
   * 构造一个使用指定精度的MoneySerializer实例。
   *
   * @param scale
   *     小数位数。
   */
  public MoneySerializer(final int scale) {
    this(new MoneyCodec(scale));
  }

  /**
   * 构造一个使用指定编码器的MoneySerializer实例。
   *
   * @param codec
   *     货币编码器。
   */
  public MoneySerializer(final MoneyCodec codec) {
    super(BigDecimal.class, codec, JsonGenerator::writeRawValue);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MoneySerializer createContextual(final SerializerProvider prov,
      final BeanProperty property) throws JsonMappingException {
    final MoneyCodec codec = (MoneyCodec) encoder;
    final Money money = property.getAnnotation(Money.class);
    if ((money != null)
        && ((money.scale() != codec.getScale())
        || (money.roundingModel() != codec.getRoundingMode())
        || (money.useGroup() != codec.isUseGroup()))) {
      final MoneyCodec newCodec = new MoneyCodec();
      newCodec.setScale(money.scale());
      newCodec.setRoundingMode(money.roundingModel());
      newCodec.setUseGroup(money.useGroup());
      return new MoneySerializer(newCodec);
    }
    return this;
  }
}