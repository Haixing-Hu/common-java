////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.time.ZoneId;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.ZoneIdCodec;

/**
 * The JACKSON key deserializer of a {@link ZoneId} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class ZoneIdKeyDeserializer extends DecoderKeyDeserializer<ZoneId> {

  private static final long serialVersionUID = 2056166166657379211L;

  public static final ZoneIdKeyDeserializer INSTANCE = new ZoneIdKeyDeserializer();

  public ZoneIdKeyDeserializer() {
    super(ZoneId.class, ZoneIdCodec.INSTANCE);
  }
}
