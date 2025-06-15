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
import java.math.BigDecimal;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import ltd.qubit.commons.annotation.Money;
import ltd.qubit.commons.util.codec.MoneyCodec;

/**
 * 货币值的 JACKSON 反序列化器，货币值用 {@link BigDecimal} 对象表示。
 *
 * @author 胡海星
 */
@Immutable
public class MoneyDeserializer extends DecoderDeserializer<BigDecimal>
    implements ContextualDeserializer {

  @Serial
  private static final long serialVersionUID = -1404767776903552360L;

  /**
   * 单例实例。
   */
  public static final MoneyDeserializer INSTANCE = new MoneyDeserializer();

  /**
   * 构造一个 {@link MoneyDeserializer} 对象。
   */
  public MoneyDeserializer() {
    super(BigDecimal.class, new MoneyCodec());
  }

  /**
   * 构造一个 {@link MoneyDeserializer} 对象。
   *
   * @param scale
   *     指定的小数位数。
   */
  public MoneyDeserializer(final int scale) {
    super(BigDecimal.class, new MoneyCodec(scale));
  }

  /**
   * 构造一个 {@link MoneyDeserializer} 对象。
   *
   * @param codec
   *     指定的编解码器。
   */
  public MoneyDeserializer(final MoneyCodec codec) {
    super(BigDecimal.class, codec);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MoneyDeserializer createContextual(final DeserializationContext context,
      final BeanProperty property) throws JsonMappingException {
    final MoneyCodec codec = (MoneyCodec) decoder;
    final Money money = property.getAnnotation(Money.class);
    if ((money != null)
        && ((money.scale() != codec.getScale())
         || (money.roundingModel() != codec.getRoundingMode())
         || (money.useGroup() != codec.isUseGroup()))) {
      final MoneyCodec newCodec = new MoneyCodec();
      newCodec.setScale(money.scale());
      newCodec.setRoundingMode(money.roundingModel());
      newCodec.setUseGroup(money.useGroup());
      return new MoneyDeserializer(newCodec);
    }
    return this;
  }
}
