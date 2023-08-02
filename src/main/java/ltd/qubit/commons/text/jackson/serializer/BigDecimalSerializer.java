////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.math.BigDecimal;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.annotation.Money;
import ltd.qubit.commons.annotation.Round;
import ltd.qubit.commons.annotation.Scale;
import ltd.qubit.commons.util.codec.BigDecimalCodec;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

/**
 * The JSON serializer of a {@link BigDecimal} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class BigDecimalSerializer extends EncoderSerializer<BigDecimal>
    implements ContextualSerializer {

  private static final long serialVersionUID = 7812000100733188757L;

  public static final BigDecimalSerializer INSTANCE = new BigDecimalSerializer();

  public BigDecimalSerializer() {
    this(new BigDecimalCodec());
  }

  public BigDecimalSerializer(final BigDecimalCodec codec) {
    super(BigDecimal.class, codec, JsonGenerator::writeRawValue);
  }

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
