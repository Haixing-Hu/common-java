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
import java.time.LocalTime;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoLocalTimeCodec;

/**
 * {@link LocalTime}对象的Jackson序列化器，使用ISO-8601格式"HH:mm:ss"。
 *
 * @author 胡海星
 */
@Immutable
public class IsoLocalTimeSerializer extends LocalTimeSerializer {

  @Serial
  private static final long serialVersionUID = 5545681876876701858L;

  /**
   * IsoLocalTimeSerializer的单例实例。
   */
  public static final IsoLocalTimeSerializer INSTANCE = new IsoLocalTimeSerializer();

  /**
   * 构造一个新的IsoLocalTimeSerializer实例。
   */
  public IsoLocalTimeSerializer() {
    super(new IsoLocalTimeCodec());
  }
}