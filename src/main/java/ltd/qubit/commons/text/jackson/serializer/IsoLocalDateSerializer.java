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
import java.time.LocalDate;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoLocalDateCodec;

/**
 * {@link LocalDate}对象的Jackson序列化器，使用ISO-8601格式"yyyy-MM-dd"。
 *
 * @author 胡海星
 */
@Immutable
public class IsoLocalDateSerializer extends LocalDateSerializer {

  @Serial
  private static final long serialVersionUID = 7554711342122229142L;

  /**
   * IsoLocalDateSerializer的单例实例。
   */
  public static final IsoLocalDateSerializer INSTANCE =
      new IsoLocalDateSerializer();

  /**
   * 构造一个新的IsoLocalDateSerializer实例。
   */
  public IsoLocalDateSerializer() {
    super(new IsoLocalDateCodec());
  }
}