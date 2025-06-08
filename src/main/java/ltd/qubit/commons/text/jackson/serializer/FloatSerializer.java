////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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
import ltd.qubit.commons.util.codec.FloatCodec;

/**
 * The JACKSON serializer of a {@link Float} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class FloatSerializer extends EncoderSerializer<Float>
    implements ContextualSerializer {

  private static final long serialVersionUID = -9188496859529399954L;

  public static final FloatSerializer INSTANCE = new FloatSerializer();

  public FloatSerializer() {
    this(new FloatCodec());
  }

  public FloatSerializer(final int precision) {
    this(new FloatCodec(precision));
  }

  public FloatSerializer(final FloatCodec codec) {
    super(Float.class, codec, JsonGenerator::writeRawValue);
  }

  @Override
  public JsonSerializer<?> createContextual(final SerializerProvider prov,
      final BeanProperty property) {
    final FloatCodec codec = (FloatCodec) encoder;
    final Scale scale = property.getAnnotation(Scale.class);
    if ((scale != null) && (scale.value() != codec.getPrecision())) {
      return new FloatSerializer(scale.value());
    } else {
      return this;
    }
  }
}