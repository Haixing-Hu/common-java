////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.time.ZoneOffset;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.ZoneOffsetCodec;

/**
 * The JACKSON key deserializer of a {@link ZoneOffset} object.
 *
 * @author Haixing Hu
 */
@Immutable
public class ZoneOffsetKeyDeserializer extends DecoderKeyDeserializer<ZoneOffset> {

  private static final long serialVersionUID = 2457561833385507287L;

  public static final ZoneOffsetKeyDeserializer INSTANCE = new ZoneOffsetKeyDeserializer();

  public ZoneOffsetKeyDeserializer() {
    super(ZoneOffset.class, ZoneOffsetCodec.INSTANCE);
  }
}