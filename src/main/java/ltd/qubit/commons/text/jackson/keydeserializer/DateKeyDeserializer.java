////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.util.Date;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.Decoder;

/**
 * The JACKSON key deserializer of a {@link Date} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class DateKeyDeserializer extends DecoderKeyDeserializer<Date> {

  private static final long serialVersionUID = -3876931545882471731L;

  public DateKeyDeserializer(final Decoder<String, Date> decoder) {
    super(Date.class, decoder);
  }
}
