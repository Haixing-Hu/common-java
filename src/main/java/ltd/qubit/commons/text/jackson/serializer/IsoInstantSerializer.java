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
import java.time.Instant;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoInstantCodec;

/**
 * {@link Instant}对象的Jackson序列化器，使用ISO-8601格式"yyyy-MM-dd'T'HH:mm:ss'Z'"。
 *
 * @author 胡海星
 */
@Immutable
public class IsoInstantSerializer extends InstantSerializer {

  @Serial
  private static final long serialVersionUID = -3704390827010017389L;

  /**
   * IsoInstantSerializer的单例实例。
   */
  public static final IsoInstantSerializer INSTANCE =
      new IsoInstantSerializer();

  /**
   * 构造一个新的IsoInstantSerializer实例。
   */
  public IsoInstantSerializer() {
    super(new IsoInstantCodec());
  }
}