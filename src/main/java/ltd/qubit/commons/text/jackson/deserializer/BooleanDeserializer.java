////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;

/**
 * The JACKSON deserializer of a {@link Boolean} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class BooleanDeserializer extends DecoderDeserializer<Boolean> {

  private static final long serialVersionUID = -8623054434907870733L;

  public BooleanDeserializer(final Decoder<String, Boolean> decoder) {
    super(Boolean.class, decoder);
  }
}
