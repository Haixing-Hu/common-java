////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import ltd.qubit.commons.util.codec.IsoInstantCodec;

import javax.annotation.concurrent.Immutable;
import java.time.Instant;

/**
 * 符合 ISO-8601 的时间戳类 {@link Instant} 的 JSON 反序列化器，其编码格式为
 * "yyyy-MM-dd'T'HH:mm:ss'Z'"。
 *
 * @author Haixing Hu
 */
@Immutable
public class IsoInstantDeserializer extends InstantDeserializer {

  private static final long serialVersionUID = 3170703127325054711L;

  public static final IsoInstantDeserializer INSTANCE =
      new IsoInstantDeserializer();

  public IsoInstantDeserializer() {
    super(new IsoInstantCodec());
  }
}
