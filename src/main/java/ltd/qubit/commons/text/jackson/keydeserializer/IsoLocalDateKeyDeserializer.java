////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.time.LocalDate;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoLocalDateCodec;

/**
 * The JACKSON key deserializer of a {@link LocalDate} object, in the ISO-8601
 * format of "yyyy-mm-dd".
 *
 * @author Haixing Hu
 */
@Immutable
public class IsoLocalDateKeyDeserializer extends LocalDateKeyDeserializer {

  private static final long serialVersionUID = -466248186115983408L;

  public static final IsoLocalDateKeyDeserializer INSTANCE = new IsoLocalDateKeyDeserializer();

  public IsoLocalDateKeyDeserializer() {
    super(new IsoLocalDateCodec());
  }
}