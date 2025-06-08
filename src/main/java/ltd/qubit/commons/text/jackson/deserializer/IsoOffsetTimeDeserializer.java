////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.time.OffsetTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoOffsetTimeCodec;

/**
 * The JACKSON deserializer of a {@link OffsetTime} object, in the ISO-8601
 * format.
 *
 * @author Haixing Hu
 */
@Immutable
public class IsoOffsetTimeDeserializer extends OffsetTimeDeserializer {

  private static final long serialVersionUID = -2608618436291577502L;

  public static final IsoOffsetTimeDeserializer INSTANCE = new IsoOffsetTimeDeserializer();

  public IsoOffsetTimeDeserializer() {
    super(IsoOffsetTimeCodec.INSTANCE);
  }
}