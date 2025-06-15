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
import java.time.YearMonth;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.Encoder;
import ltd.qubit.commons.util.codec.YearMonthCodec;

/**
 * {@link YearMonth}对象的Jackson序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class YearMonthSerializer extends EncoderSerializer<YearMonth> {

  @Serial
  private static final long serialVersionUID = 1291011980095251350L;

  /**
   * YearMonthSerializer的单例实例。
   */
  public static final YearMonthSerializer INSTANCE = new YearMonthSerializer();

  /**
   * 构造一个使用默认编码器的YearMonthSerializer实例。
   */
  public YearMonthSerializer() {
    this(YearMonthCodec.INSTANCE);
  }

  /**
   * 构造一个使用指定编码器的YearMonthSerializer实例。
   *
   * @param encoder
   *     YearMonth编码器。
   */
  public YearMonthSerializer(final Encoder<YearMonth, String> encoder) {
    super(YearMonth.class, encoder, JsonGenerator::writeString);
  }
}