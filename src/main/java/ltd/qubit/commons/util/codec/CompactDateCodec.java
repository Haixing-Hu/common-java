////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

/**
 * 压缩格式的日期格式的日期编码器，该编码器将日期编码为"yyyyMMdd"的形式。
 *
 * @author 胡海星
 */
public class CompactDateCodec extends LocalDateCodec {

  public CompactDateCodec() {
    super("yyyyMMdd", true, true);
  }
}
