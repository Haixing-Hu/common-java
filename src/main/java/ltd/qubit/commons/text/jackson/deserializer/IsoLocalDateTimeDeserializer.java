////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.time.LocalDateTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoLocalDateTimeCodec;

/**
 * The JACKSON deserializer of a {@link LocalDateTime} object, in the ISO-8601
 * format of "yyyy-mm-dd HH:mm:ss".
 *
 * @author Haixing Hu
 */
@Immutable
public class IsoLocalDateTimeDeserializer extends LocalDateTimeDeserializer {

  private static final long serialVersionUID = 2232065032680091828L;

  public static final IsoLocalDateTimeDeserializer INSTANCE =
      new IsoLocalDateTimeDeserializer();

  public IsoLocalDateTimeDeserializer() {
    super(new IsoLocalDateTimeCodec());
  }
}