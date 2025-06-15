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
import java.util.Locale;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.PosixLocaleCodec;

/**
 * {@link Locale}对象的Jackson序列化器，使用POSIX格式。
 *
 * @author 胡海星
 */
@Immutable
public class PosixLocaleSerializer extends EncoderSerializer<Locale> {

  @Serial
  private static final long serialVersionUID = 8709094403572085333L;

  /**
   * PosixLocaleSerializer的单例实例。
   */
  public static final PosixLocaleSerializer INSTANCE = new PosixLocaleSerializer();

  /**
   * 构造一个PosixLocaleSerializer实例。
   */
  public PosixLocaleSerializer() {
    super(Locale.class, PosixLocaleCodec.INSTANCE, JsonGenerator::writeString);
  }
}