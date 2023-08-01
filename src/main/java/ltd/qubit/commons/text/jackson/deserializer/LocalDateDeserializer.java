////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import ltd.qubit.commons.util.codec.Decoder;

import java.time.LocalDate;
import javax.annotation.concurrent.Immutable;

/**
 * The JSON deserializer of a {@link LocalDate} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class LocalDateDeserializer extends DecoderDeserializer<LocalDate> {

  private static final long serialVersionUID = 2892249089469487448L;

  public LocalDateDeserializer(final Decoder<String, LocalDate> decoder) {
    super(LocalDate.class, decoder);
  }
}
