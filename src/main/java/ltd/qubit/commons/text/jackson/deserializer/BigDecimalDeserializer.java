////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import ltd.qubit.commons.text.Stripper;
import ltd.qubit.commons.util.codec.BigDecimalCodec;
import ltd.qubit.commons.util.codec.DecodingException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ltd.qubit.commons.annotation.Money;
import ltd.qubit.commons.annotation.Round;
import ltd.qubit.commons.annotation.Scale;

import java.io.IOException;
import java.math.BigDecimal;
import javax.annotation.concurrent.Immutable;

import static ltd.qubit.commons.lang.StringUtils.isEmpty;

/**
 * The JSON deserializer of a {@link BigDecimal} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class BigDecimalDeserializer extends StdDeserializer<BigDecimal>
    implements ContextualDeserializer {

  private static final long serialVersionUID = -1826416153857761383L;

  public static final BigDecimalDeserializer INSTANCE =
      new BigDecimalDeserializer();

  private final BigDecimalCodec codec;

  public BigDecimalDeserializer() {
    this(new BigDecimalCodec());
  }

  public BigDecimalDeserializer(final BigDecimalCodec codec) {
    super(BigDecimal.class);
    this.codec = codec;
  }

  @Override
  public JsonDeserializer<?> createContextual(final DeserializationContext context,
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
    if (codec.equals(newCodec)) {
      return this;
    } else {
      return new BigDecimalDeserializer(newCodec);
    }
  }

  @Override
  public BigDecimal deserialize(final JsonParser parser,
      final DeserializationContext context) throws IOException {
    final String text = parser.getText();
    final String str = new Stripper().strip(text);
    if (isEmpty(str)) {
      return null;
    } else {
      try {
        return codec.decode(str);
      } catch (final DecodingException e) {
        throw new IOException(e);
      }
    }
  }
}
