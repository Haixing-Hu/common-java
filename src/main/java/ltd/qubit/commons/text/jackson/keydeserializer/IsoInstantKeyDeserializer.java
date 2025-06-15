////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.io.Serial;
import java.time.Instant;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoInstantCodec;

/**
 * {@link Instant} 对象的 JACKSON 键反序列化器，使用 ISO-8601 格式
 * "yyyy-MM-dd'T'HH:mm:ss'Z'"。
 *
 * @author 胡海星
 */
@Immutable
public class IsoInstantKeyDeserializer extends InstantKeyDeserializer {

  @Serial
  private static final long serialVersionUID = -5891486913012881193L;

  /**
   * 默认实例。
   */
  public static final IsoInstantKeyDeserializer INSTANCE = new IsoInstantKeyDeserializer();

  /**
   * 构造一个 {@link IsoInstantKeyDeserializer}。
   */
  public IsoInstantKeyDeserializer() {
    super(new IsoInstantCodec());
  }
}