////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.time.Instant;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoInstantCodec;

/**
 * The JACKSON serializer of a {@link Instant} object, in the ISO-8601
 * format of "yyyy-MM-dd'T'HH:mm:ss'Z'".
 *
 * @author Haixing Hu
 */
@Immutable
public class IsoInstantDeserializer extends InstantDeserializer {

  private static final long serialVersionUID = 3170703127325054711L;

  public static final IsoInstantDeserializer INSTANCE =
      new IsoInstantDeserializer();

  public IsoInstantDeserializer() {
    super(new IsoInstantCodec());
  }
}