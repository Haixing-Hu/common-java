////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.time.LocalTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoLocalTimeCodec;

/**
 * 符合 ISO-8601 的本地时间类 {@link LocalTime} 的 JSON 序列化器，其编码格
 * 式为 "HH:mm:ss"。
 *
 * @author 胡海星
 */
@Immutable
public class IsoLocalTimeSerializer extends LocalTimeSerializer {

  private static final long serialVersionUID = 5545681876876701858L;

  public static final IsoLocalTimeSerializer INSTANCE =
      new IsoLocalTimeSerializer();

  public IsoLocalTimeSerializer() {
    super(new IsoLocalTimeCodec());
  }
}
