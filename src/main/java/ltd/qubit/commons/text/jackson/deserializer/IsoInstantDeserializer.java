////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.io.Serial;
import java.time.Instant;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoInstantCodec;

/**
 * {@link Instant} 对象的 JACKSON 序列化器，采用 ISO-8601 格式 "yyyy-MM-dd'T'HH:mm:ss'Z'"。
 *
 * @author 胡海星
 */
@Immutable
public class IsoInstantDeserializer extends InstantDeserializer {

  @Serial
  private static final long serialVersionUID = 3170703127325054711L;

  /**
   * 单例实例。
   */
  public static final IsoInstantDeserializer INSTANCE =
      new IsoInstantDeserializer();

  /**
   * 构造一个 {@link IsoInstantDeserializer} 对象。
   */
  public IsoInstantDeserializer() {
    super(new IsoInstantCodec());
  }
}
