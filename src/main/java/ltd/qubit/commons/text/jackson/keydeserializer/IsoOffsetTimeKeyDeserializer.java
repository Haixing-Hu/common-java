////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.time.OffsetTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoOffsetTimeCodec;

/**
 * The JACKSON key deserializer of a {@link OffsetTime} object, in the ISO-8601
 * format.
 *
 * @author Haixing Hu
 */
@Immutable
public class IsoOffsetTimeKeyDeserializer extends OffsetTimeKeyDeserializer {

  private static final long serialVersionUID = 6530978579305551410L;

  public static final IsoOffsetTimeKeyDeserializer INSTANCE = new IsoOffsetTimeKeyDeserializer();

  public IsoOffsetTimeKeyDeserializer() {
    super(IsoOffsetTimeCodec.INSTANCE);
  }
}