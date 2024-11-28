////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.time.format.DateTimeFormatter;

import javax.annotation.concurrent.Immutable;

@Immutable
public class IsoOffsetTimeCodec extends OffsetTimeCodec {

  public static final IsoOffsetTimeCodec INSTANCE = new IsoOffsetTimeCodec();

  public IsoOffsetTimeCodec() {
    super(DateTimeFormatter.ISO_OFFSET_TIME);
  }
}
