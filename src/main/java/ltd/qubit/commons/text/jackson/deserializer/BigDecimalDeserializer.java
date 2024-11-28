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
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import ltd.qubit.commons.annotation.Money;
import ltd.qubit.commons.annotation.Round;
import ltd.qubit.commons.annotation.Scale;
import ltd.qubit.commons.util.codec.BigDecimalCodec;

/**
 * The JACKSON deserializer of a {@link BigDecimal} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class BigDecimalDeserializer extends DecoderDeserializer<BigDecimal>
    implements ContextualDeserializer {

  private static final long serialVersionUID = -1826416153857761383L;

  public static final BigDecimalDeserializer INSTANCE = new BigDecimalDeserializer();

  public BigDecimalDeserializer() {
    super(BigDecimal.class, new BigDecimalCodec());
  }

  public BigDecimalDeserializer(final BigDecimalCodec codec) {
    super(BigDecimal.class, codec);
  }

  @Override
  public JsonDeserializer<?> createContextual(final DeserializationContext context,
      final BeanProperty property) {
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
      return new BigDecimalDeserializer(newCodec);
    }
  }
}
