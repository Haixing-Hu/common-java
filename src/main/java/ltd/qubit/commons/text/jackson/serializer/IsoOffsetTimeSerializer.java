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
import java.time.OffsetTime;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.IsoOffsetTimeCodec;

/**
 * {@link OffsetTime}对象的Jackson序列化器，使用ISO-8601格式。
 *
 * @author 胡海星
 */
@Immutable
public class IsoOffsetTimeSerializer extends EncoderSerializer<OffsetTime> {

  @Serial
  private static final long serialVersionUID = -8607540977638353617L;

  /**
   * IsoOffsetTimeSerializer的单例实例。
   */
  public static final IsoOffsetTimeSerializer INSTANCE = new IsoOffsetTimeSerializer();

  /**
   * 构造一个新的IsoOffsetTimeSerializer实例。
   */
  public IsoOffsetTimeSerializer() {
    super(OffsetTime.class, IsoOffsetTimeCodec.INSTANCE, JsonGenerator::writeString);
  }
}