////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.time.LocalDateTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoLocalDateTimeCodec;

/**
 * The JACKSON serializer of a {@link LocalDateTime} object, in the ISO-8601
 * format of "yyyy-mm-dd HH:mm:ss".
 *
 * @author Haixing Hu
 */
@Immutable
public class IsoLocalDateTimeSerializer extends LocalDateTimeSerializer {

  private static final long serialVersionUID = -1860496449765162843L;

  public static final IsoLocalDateTimeSerializer INSTANCE = new IsoLocalDateTimeSerializer();

  public IsoLocalDateTimeSerializer() {
    super(new IsoLocalDateTimeCodec());
  }
}