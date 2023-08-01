////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import ltd.qubit.commons.util.codec.MoneyCodec;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import ltd.qubit.commons.annotation.Money;

import java.math.BigDecimal;
import javax.annotation.concurrent.Immutable;

/**
 * The JSON serializer of money values, which are represented with
 * {@link Money} objects.
 *
 * @author Haixing Hu
 */
@Immutable
public class MoneySerializer extends EncoderSerializer<BigDecimal>
    implements ContextualSerializer {

  private static final long serialVersionUID = -7252111294856231946L;

  public static final MoneySerializer INSTANCE = new MoneySerializer();

  public MoneySerializer() {
    this(new MoneyCodec());
  }

  public MoneySerializer(final int scale) {
    this(new MoneyCodec(scale));
  }

  public MoneySerializer(final MoneyCodec codec) {
    super(BigDecimal.class, codec, JsonGenerator::writeRawValue);
  }

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
