////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.time.LocalDateTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoLocalDateTimeCodec;

/**
 * The JACKSON key deserializer of a {@link LocalDateTime} object, in the ISO-8601
 * format of "yyyy-mm-dd HH:mm:ss".
 *
 * @author Haixing Hu
 */
@Immutable
public class IsoLocalDateTimeKeyDeserializer extends LocalDateTimeKeyDeserializer {

  private static final long serialVersionUID = -3884818036521351155L;

  public static final IsoLocalDateTimeKeyDeserializer INSTANCE = new IsoLocalDateTimeKeyDeserializer();

  public IsoLocalDateTimeKeyDeserializer() {
    super(new IsoLocalDateTimeCodec());
  }
}