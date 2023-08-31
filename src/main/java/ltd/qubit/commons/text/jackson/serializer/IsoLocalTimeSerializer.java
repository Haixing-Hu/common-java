////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import ltd.qubit.commons.util.codec.IsoLocalTimeCodec;

import javax.annotation.concurrent.Immutable;
import java.time.LocalTime;

/**
 * 符合 ISO-8601 的本地时间类 {@link LocalTime} 的 JSON 序列化器，其编码格
 * 式为 "HH:mm:ss"。
 *
 * @author Haixing Hu
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
