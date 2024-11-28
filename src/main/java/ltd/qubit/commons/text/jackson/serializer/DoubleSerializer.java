////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import ltd.qubit.commons.annotation.Scale;
import ltd.qubit.commons.util.codec.DoubleCodec;

/**
 * The JACKSON serializer of a {@link Double} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class DoubleSerializer extends EncoderSerializer<Double>
    implements ContextualSerializer {

  private static final long serialVersionUID = 2198946838706943360L;

  public static final DoubleSerializer INSTANCE = new DoubleSerializer();

  public DoubleSerializer() {
    this(new DoubleCodec());
  }

  public DoubleSerializer(final int precision) {
    this(new DoubleCodec(precision));
  }

  public DoubleSerializer(final DoubleCodec codec) {
    super(Double.class, codec, JsonGenerator::writeRawValue);
  }

  @Override
  public JsonSerializer<?> createContextual(final SerializerProvider prov,
      final BeanProperty property) {
    final DoubleCodec codec = (DoubleCodec) encoder;
    final Scale scale = property.getAnnotation(Scale.class);
    if ((scale != null) && (scale.value() != codec.getPrecision())) {
      return new DoubleSerializer(scale.value());
    } else {
      return this;
    }
  }
}
