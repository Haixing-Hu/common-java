////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.time.LocalTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoLocalTimeCodec;

/**
 * The JACKSON serializer of a {@link LocalTime} object, in the ISO-8601
 * format of "HH:mm:ss".
 *
 * @author Haixing Hu
 */
@Immutable
public class IsoLocalTimeSerializer extends LocalTimeSerializer {

  private static final long serialVersionUID = 5545681876876701858L;

  public static final IsoLocalTimeSerializer INSTANCE = new IsoLocalTimeSerializer();

  public IsoLocalTimeSerializer() {
    super(new IsoLocalTimeCodec());
  }
}
