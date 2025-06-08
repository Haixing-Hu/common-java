////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.time.LocalTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoLocalTimeCodec;

/**
 * The JACKSON key deserializer of a {@link LocalTime} object, in the ISO-8601
 * format of "HH:mm:ss".
 *
 * @author Haixing Hu
 */
@Immutable
public class IsoLocalTimeKeyDeserializer extends LocalTimeKeyDeserializer {

  private static final long serialVersionUID = -4386919975438873483L;

  public static final IsoLocalTimeKeyDeserializer INSTANCE = new IsoLocalTimeKeyDeserializer();

  public IsoLocalTimeKeyDeserializer() {
    super(new IsoLocalTimeCodec());
  }
}