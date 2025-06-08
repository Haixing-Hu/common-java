////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.reflect.WriteMethodReference;
import ltd.qubit.commons.util.codec.Encoder;

/**
 * The JACKSON serializer of a {@link Boolean} object.
 *
 * @author Haixing Hu
 */
public abstract class BooleanSerializer extends EncoderSerializer<Boolean> {

  private static final long serialVersionUID = 3087228619436703111L;

  public BooleanSerializer(final Encoder<Boolean, String> encoder,
      final WriteMethodReference<JsonGenerator> writeMethod) {
    super(Boolean.class, encoder, writeMethod);
  }
}