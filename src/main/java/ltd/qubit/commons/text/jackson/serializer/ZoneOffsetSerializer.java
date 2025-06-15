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
import java.time.ZoneOffset;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.ZoneOffsetCodec;

/**
 * {@link ZoneOffset}对象的Jackson序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class ZoneOffsetSerializer extends EncoderSerializer<ZoneOffset> {

  @Serial
  private static final long serialVersionUID = -171168624211059720L;

  /**
   * ZoneOffsetSerializer的单例实例。
   */
  public static final ZoneOffsetSerializer INSTANCE =
      new ZoneOffsetSerializer();

  /**
   * 构造一个ZoneOffsetSerializer实例。
   */
  public ZoneOffsetSerializer() {
    super(ZoneOffset.class, ZoneOffsetCodec.INSTANCE, JsonGenerator::writeString);
  }
}