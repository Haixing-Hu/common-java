////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

/**
 * 压缩格式的日期格式的日期时间编码器，该编码器将日期编码为"yyyyMMddHHmmss"的形式。
 *
 * @author 胡海星
 */
public class CompactDateTimeCodec extends LocalDateTimeCodec {

  public CompactDateTimeCodec() {
    super("yyyyMMddHHmmss", true, true);
  }
}
