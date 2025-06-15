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
import java.util.Date;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoDateCodec;

/**
 * {@link Date} 对象的 JACKSON 键反序列化器，使用 ISO-8601 格式
 * "yyyy-MM-dd'T'HH:mm:ss'Z'"。
 *
 * @author 胡海星
 */
@Immutable
public class IsoDateKeyDeserializer extends DateKeyDeserializer {

  @Serial
  private static final long serialVersionUID = 5491058965489717729L;

  /**
   * 默认实例。
   */
  public static final IsoDateKeyDeserializer INSTANCE = new IsoDateKeyDeserializer();

  /**
   * 构造一个 {@link IsoDateKeyDeserializer}。
   */
  public IsoDateKeyDeserializer() {
    super(new IsoDateCodec());
  }
}