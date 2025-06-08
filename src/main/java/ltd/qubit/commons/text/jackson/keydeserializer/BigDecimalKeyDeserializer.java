////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

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
 * The JACKSON key deserializer of a {@link BigDecimal} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class BigDecimalKeyDeserializer extends DecoderKeyDeserializer<BigDecimal>
    implements ContextualKeyDeserializer {

  private static final long serialVersionUID = 8610277491732069052L;

  public static final BigDecimalKeyDeserializer INSTANCE = new BigDecimalKeyDeserializer();

  public BigDecimalKeyDeserializer() {
    super(BigDecimal.class, new BigDecimalCodec());
  }

  public BigDecimalKeyDeserializer(final BigDecimalCodec codec) {
    super(BigDecimal.class, codec);
  }

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