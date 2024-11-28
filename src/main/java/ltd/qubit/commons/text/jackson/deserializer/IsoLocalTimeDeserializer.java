////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.time.LocalTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoLocalTimeCodec;

/**
 * The JACKSON deserializer of a {@link LocalTime} object, in the ISO-8601
 * format of "HH:mm:ss".
 *
 * @author Haixing Hu
 */
@Immutable
public class IsoLocalTimeDeserializer extends LocalTimeDeserializer {

  private static final long serialVersionUID = 7834072898738048137L;

  public static final IsoLocalTimeDeserializer INSTANCE =
      new IsoLocalTimeDeserializer();

  public IsoLocalTimeDeserializer() {
    super(new IsoLocalTimeCodec());
  }
}
