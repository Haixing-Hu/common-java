////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.math.BigDecimal;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import ltd.qubit.commons.annotation.Money;
import ltd.qubit.commons.util.codec.MoneyCodec;

/**
 * The JACKSON deserializer of money values, which are represented with
 * {@link BigDecimal} objects.
 *
 * @author Haixing Hu
 */
@Immutable
public class MoneyDeserializer extends DecoderDeserializer<BigDecimal>
    implements ContextualDeserializer {

  private static final long serialVersionUID = -1404767776903552360L;

  public static final MoneyDeserializer INSTANCE = new MoneyDeserializer();

  public MoneyDeserializer() {
    super(BigDecimal.class, new MoneyCodec());
  }

  public MoneyDeserializer(final int scale) {
    super(BigDecimal.class, new MoneyCodec(scale));
  }

  public MoneyDeserializer(final MoneyCodec codec) {
    super(BigDecimal.class, codec);
  }

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
