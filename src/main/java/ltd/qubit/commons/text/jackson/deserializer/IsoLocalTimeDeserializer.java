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
import java.time.LocalTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoLocalTimeCodec;

/**
 * {@link LocalTime} 对象的 JACKSON 反序列化器，采用 ISO-8601 格式 "HH:mm:ss"。
 *
 * @author 胡海星
 */
@Immutable
public class IsoLocalTimeDeserializer extends LocalTimeDeserializer {

  @Serial
  private static final long serialVersionUID = 7834072898738048137L;

  /**
   * 单例实例。
   */
  public static final IsoLocalTimeDeserializer INSTANCE =
      new IsoLocalTimeDeserializer();

  /**
   * 构造一个 {@link IsoLocalTimeDeserializer} 对象。
   */
  public IsoLocalTimeDeserializer() {
    super(new IsoLocalTimeCodec());
  }
}
