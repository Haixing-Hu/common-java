////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

/**
 * 符合 ISO-8601 的本地时间编码器，其编码格式为 "HH:mm:ss"。
 *
 * @author 胡海星
 */
public class IsoLocalTimeCodec extends LocalTimeCodec {

  public static IsoLocalTimeCodec INSTANCE = new IsoLocalTimeCodec();

  public IsoLocalTimeCodec() {
    super("HH:mm:ss", false);
  }
}