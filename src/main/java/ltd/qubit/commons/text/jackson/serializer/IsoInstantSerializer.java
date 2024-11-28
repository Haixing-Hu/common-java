////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.time.Instant;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoInstantCodec;

/**
 * The JACKSON deserializer of a {@link Instant} object, in the ISO-8601
 * format of "yyyy-MM-dd'T'HH:mm:ss'Z'".
 *
 * @author Haixing Hu
 */
@Immutable
public class IsoInstantSerializer extends InstantSerializer {

  private static final long serialVersionUID = -3704390827010017389L;

  public static final IsoInstantSerializer INSTANCE =
      new IsoInstantSerializer();

  public IsoInstantSerializer() {
    super(new IsoInstantCodec());
  }
}
