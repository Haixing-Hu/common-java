////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.time.LocalDate;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoLocalDateCodec;

/**
 * The JACKSON deserializer of a {@link LocalDate} object, in the ISO-8601
 * format of "yyyy-mm-dd".
 *
 * @author Haixing Hu
 */
@Immutable
public class IsoLocalDateDeserializer extends LocalDateDeserializer {

  private static final long serialVersionUID = 7884285368967920278L;

  public static final IsoLocalDateDeserializer INSTANCE = new IsoLocalDateDeserializer();

  public IsoLocalDateDeserializer() {
    super(new IsoLocalDateCodec());
  }
}
