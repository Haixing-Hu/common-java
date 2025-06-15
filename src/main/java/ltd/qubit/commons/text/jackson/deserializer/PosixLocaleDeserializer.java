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
import java.util.Locale;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.PosixLocaleCodec;

/**
 * POSIX {@link Locale} 对象的 JACKSON 反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class PosixLocaleDeserializer extends DecoderDeserializer<Locale> {

  @Serial
  private static final long serialVersionUID = 5696200805930749178L;

  /**
   * 单例实例。
   */
  public static final PosixLocaleDeserializer INSTANCE =
      new PosixLocaleDeserializer();

  /**
   * 构造一个 {@link PosixLocaleDeserializer} 对象。
   */
  public PosixLocaleDeserializer() {
    super(Locale.class, PosixLocaleCodec.INSTANCE);
  }
}
