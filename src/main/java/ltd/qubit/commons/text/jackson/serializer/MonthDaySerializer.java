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
import java.time.MonthDay;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.Encoder;
import ltd.qubit.commons.util.codec.MonthDayCodec;

/**
 * {@link MonthDay}对象的Jackson序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class MonthDaySerializer extends EncoderSerializer<MonthDay> {

  @Serial
  private static final long serialVersionUID = 537655584599361987L;

  /**
   * MonthDaySerializer的单例实例。
   */
  public static final MonthDaySerializer INSTANCE = new MonthDaySerializer();

  /**
   * 构造一个使用默认编码器的MonthDaySerializer实例。
   */
  public MonthDaySerializer() {
    this(MonthDayCodec.INSTANCE);
  }

  /**
   * 构造一个使用指定编码器的MonthDaySerializer实例。
   *
   * @param encoder
   *     MonthDay编码器。
   */
  public MonthDaySerializer(final Encoder<MonthDay, String> encoder) {
    super(MonthDay.class, encoder, JsonGenerator::writeString);
  }
}