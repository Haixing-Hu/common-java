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
import java.time.LocalDate;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.IsoLocalDateCodec;

/**
 * {@link LocalDate} 对象的 JACKSON 反序列化器，采用 ISO-8601 格式 "yyyy-mm-dd"。
 *
 * @author 胡海星
 */
@Immutable
public class IsoLocalDateDeserializer extends LocalDateDeserializer {

  @Serial
  private static final long serialVersionUID = 7884285368967920278L;

  /**
   * 单例实例。
   */
  public static final IsoLocalDateDeserializer INSTANCE = new IsoLocalDateDeserializer();

  /**
   * 构造一个 {@link IsoLocalDateDeserializer} 对象。
   */
  public IsoLocalDateDeserializer() {
    super(new IsoLocalDateCodec());
  }
}
