////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.annotation.Scale;
import ltd.qubit.commons.util.codec.FloatCodec;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

/**
 * The JSON deserializer of a {@link Float} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class FloatDeserializer extends DecoderDeserializer<Float>
    implements ContextualDeserializer {

  private static final long serialVersionUID = 1072489984259176326L;

  public static final FloatDeserializer INSTANCE = new FloatDeserializer();

  public FloatDeserializer() {
    super(Float.class, new FloatCodec());
  }

  public FloatDeserializer(final int precision) {
    super(Float.class, new FloatCodec(precision));
  }

  public FloatDeserializer(final FloatCodec codec) {
    super(Float.class, codec);
  }

  @Override
  public JsonDeserializer<?> createContextual(final DeserializationContext ctxt,
      final BeanProperty property) {
    final FloatCodec codec = (FloatCodec) decoder;
    final Scale scale = property.getAnnotation(Scale.class);
    if ((scale != null) && (scale.value() != codec.getPrecision())) {
      return new FloatDeserializer(scale.value());
    } else {
      return this;
    }
  }
}
