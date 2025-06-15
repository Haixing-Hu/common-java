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
import java.time.LocalDate;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoLocalDateCodec;

/**
 * {@link LocalDate} 对象的 JACKSON 键反序列化器，使用 ISO-8601 格式
 * "yyyy-mm-dd"。
 *
 * @author 胡海星
 */
@Immutable
public class IsoLocalDateKeyDeserializer extends LocalDateKeyDeserializer {

  @Serial
  private static final long serialVersionUID = -466248186115983408L;

  /**
   * 默认实例。
   */
  public static final IsoLocalDateKeyDeserializer INSTANCE = new IsoLocalDateKeyDeserializer();

  /**
   * 构造一个 {@link IsoLocalDateKeyDeserializer}。
   */
  public IsoLocalDateKeyDeserializer() {
    super(new IsoLocalDateCodec());
  }
}