////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.time.Instant;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoInstantCodec;

/**
 * The JACKSON key deserializer of a {@link Instant} object, in the ISO-8601
 * format of "yyyy-MM-dd'T'HH:mm:ss'Z'".
 *
 * @author Haixing Hu
 */
@Immutable
public class IsoInstantKeyDeserializer extends InstantKeyDeserializer {

  private static final long serialVersionUID = -5891486913012881193L;

  public static final IsoInstantKeyDeserializer INSTANCE = new IsoInstantKeyDeserializer();

  public IsoInstantKeyDeserializer() {
    super(new IsoInstantCodec());
  }
}
