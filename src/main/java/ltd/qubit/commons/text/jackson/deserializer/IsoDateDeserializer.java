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
import java.util.Date;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoDateCodec;

/**
 * {@link Date} 对象的 JACKSON 反序列化器，采用 ISO-8601 格式 "yyyy-MM-dd'T'HH:mm:ss'Z'"。
 *
 * @author 胡海星
 */
@Immutable
public class IsoDateDeserializer extends DateDeserializer {

  @Serial
  private static final long serialVersionUID = 8254014955826356806L;

  /**
   * 单例实例。
   */
  public static final IsoDateDeserializer INSTANCE = new IsoDateDeserializer();

  /**
   * 构造一个 {@link IsoDateDeserializer} 对象。
   */
  public IsoDateDeserializer() {
    super(new IsoDateCodec());
  }
}
