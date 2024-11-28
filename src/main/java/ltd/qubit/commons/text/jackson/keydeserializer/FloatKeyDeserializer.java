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
import ltd.qubit.commons.util.codec.FloatCodec;

/**
 * The JACKSON key deserializer of a {@link Float} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class FloatKeyDeserializer extends DecoderKeyDeserializer<Float>
    implements ContextualKeyDeserializer {

  private static final long serialVersionUID = -3137251329959339026L;

  public static final FloatKeyDeserializer INSTANCE = new FloatKeyDeserializer();

  public FloatKeyDeserializer() {
    super(Float.class, new FloatCodec());
  }

  public FloatKeyDeserializer(final int precision) {
    super(Float.class, new FloatCodec(precision));
  }
  public FloatKeyDeserializer(final FloatCodec codec) {
    super(Float.class, codec);
  }

  @Override
  public KeyDeserializer createContextual(final DeserializationContext context,
      final BeanProperty property) throws JsonMappingException {
    final FloatCodec codec = (FloatCodec) decoder;
    final Scale scale = property.getAnnotation(Scale.class);
    if ((scale != null) && (scale.value() != codec.getPrecision())) {
      return new FloatKeyDeserializer(scale.value());
    } else {
      return this;
    }
  }
}
