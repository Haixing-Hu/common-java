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
 * 符合 ISO-8601 的本地时间编码器，其编码格式为 "HH:mm:ss"。
 *
 * @author Haixing Hu
 */
public class IsoLocalTimeCodec extends LocalTimeCodec {

  public IsoLocalTimeCodec() {
    super("HH:mm:ss", false);
  }
}
