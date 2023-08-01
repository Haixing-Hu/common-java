////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import ltd.qubit.commons.reflect.WriteMethodReference;
import ltd.qubit.commons.util.codec.Encoder;

import com.fasterxml.jackson.core.JsonGenerator;

/**
 * The JSON serializer of a {@link Boolean} object.
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
