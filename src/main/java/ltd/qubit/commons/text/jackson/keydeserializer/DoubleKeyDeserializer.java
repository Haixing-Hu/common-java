////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualKeyDeserializer;

import ltd.qubit.commons.annotation.Scale;
import ltd.qubit.commons.util.codec.DoubleCodec;

/**
 * The JACKSON key deserializer of a {@link Double} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class DoubleKeyDeserializer extends DecoderKeyDeserializer<Double>
    implements ContextualKeyDeserializer {

  private static final long serialVersionUID = -2569140073681261776L;

  public static final DoubleKeyDeserializer INSTANCE = new DoubleKeyDeserializer();

  public DoubleKeyDeserializer() {
    super(Double.class, new DoubleCodec());
  }

  public DoubleKeyDeserializer(final int precision) {
    super(Double.class, new DoubleCodec(precision));
  }

  public DoubleKeyDeserializer(final DoubleCodec codec) {
    super(Double.class, codec);
  }

  @Override
  public KeyDeserializer createContextual(final DeserializationContext context,
      final BeanProperty property) throws JsonMappingException {
    final DoubleCodec codec = (DoubleCodec) decoder;
    final Scale scale = property.getAnnotation(Scale.class);
    if ((scale != null) && (scale.value() != codec.getPrecision())) {
      return new DoubleKeyDeserializer(scale.value());
    } else {
      return this;
    }
  }
}
