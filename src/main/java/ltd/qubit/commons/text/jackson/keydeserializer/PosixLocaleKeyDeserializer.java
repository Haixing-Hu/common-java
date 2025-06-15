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
import java.util.Locale;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.codec.PosixLocaleCodec;

/**
 * {@link Locale} 对象的 JACKSON 键反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class PosixLocaleKeyDeserializer extends DecoderKeyDeserializer<Locale> {

  @Serial
  private static final long serialVersionUID = -4310833323826743748L;

  /**
   * 默认实例。
   */
  public static final PosixLocaleKeyDeserializer INSTANCE = new PosixLocaleKeyDeserializer();

  /**
   * 构造一个 {@link PosixLocaleKeyDeserializer}。
   */
  public PosixLocaleKeyDeserializer() {
    super(Locale.class, PosixLocaleCodec.INSTANCE);
  }
}