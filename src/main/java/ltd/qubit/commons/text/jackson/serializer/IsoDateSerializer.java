////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.io.Serial;
import java.util.Date;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoDateCodec;

/**
 * {@link Date} 对象的 Jackson 序列化器，使用 ISO-8601 格式 "yyyy-MM-dd'T'HH:mm:ss'Z'"。
 *
 * @author 胡海星
 */
@Immutable
public class IsoDateSerializer extends DateSerializer {

  @Serial
  private static final long serialVersionUID = 4166452457184587852L;

  /**
   * IsoDateSerializer的单例实例。
   */
  public static final IsoDateSerializer INSTANCE = new IsoDateSerializer();

  /**
   * 构造一个新的IsoDateSerializer实例。
   */
  public IsoDateSerializer() {
    super(new IsoDateCodec());
  }
}