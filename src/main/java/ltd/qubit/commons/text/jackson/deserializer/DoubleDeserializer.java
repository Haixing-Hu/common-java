////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.annotation.Scale;
import ltd.qubit.commons.util.codec.DoubleCodec;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

/**
 * The JSON deserializer of a {@link Double} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class DoubleDeserializer extends DecoderDeserializer<Double>
    implements ContextualDeserializer {

  private static final long serialVersionUID = -6589338450998945233L;

  public static final DoubleDeserializer INSTANCE = new DoubleDeserializer();

  public DoubleDeserializer() {
    super(Double.class, new DoubleCodec());
  }

  public DoubleDeserializer(final int precision) {
    super(Double.class, new DoubleCodec(precision));
  }

  public DoubleDeserializer(final DoubleCodec codec) {
    super(Double.class, codec);
  }

  @Override
  public JsonDeserializer<?> createContextual(final DeserializationContext ctxt,
      final BeanProperty property) {
    final DoubleCodec codec = (DoubleCodec) decoder;
    final Scale scale = property.getAnnotation(Scale.class);
    if ((scale != null) && (scale.value() != codec.getPrecision())) {
      return new DoubleDeserializer(scale.value());
    } else {
      return this;
    }
  }
}
