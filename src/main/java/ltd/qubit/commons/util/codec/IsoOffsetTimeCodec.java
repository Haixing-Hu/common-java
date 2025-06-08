////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.time.format.DateTimeFormatter;

import javax.annotation.concurrent.Immutable;

/**
 * {@link java.time.OffsetTime}的编解码器,格式为{@link DateTimeFormatter#ISO_OFFSET_TIME}。
 *
 * @author 胡海星
 */
@Immutable
public class IsoOffsetTimeCodec extends OffsetTimeCodec {

  public static final IsoOffsetTimeCodec INSTANCE = new IsoOffsetTimeCodec();

  public IsoOffsetTimeCodec() {
    super(DateTimeFormatter.ISO_OFFSET_TIME);
  }
}