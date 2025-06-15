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
import java.time.Year;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.Encoder;
import ltd.qubit.commons.util.codec.YearCodec;

/**
 * {@link Year}对象的Jackson序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class YearSerializer extends EncoderSerializer<Year> {

  @Serial
  private static final long serialVersionUID = 1725253327137411054L;

  /**
   * YearSerializer的单例实例。
   */
  public static final YearSerializer INSTANCE = new YearSerializer();

  /**
   * 构造一个使用默认编码器的YearSerializer实例。
   */
  public YearSerializer() {
    this(YearCodec.INSTANCE);
  }

  /**
   * 构造一个使用指定编码器的YearSerializer实例。
   *
   * @param encoder
   *     Year编码器。
   */
  public YearSerializer(final Encoder<Year, String> encoder) {
    super(Year.class, encoder, JsonGenerator::writeString);
  }
}