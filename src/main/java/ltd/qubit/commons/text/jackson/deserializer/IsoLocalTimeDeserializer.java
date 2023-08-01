////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import ltd.qubit.commons.util.codec.IsoLocalTimeCodec;

import java.time.LocalTime;
import javax.annotation.concurrent.Immutable;

/**
 * 符合 ISO-8601 的本地时间类 {@link LocalTime} 的 JSON 反序列化器，其编
 * 码格式为 "HH:mm:ss"。
 *
 * @author 胡海星
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
