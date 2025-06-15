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
import java.time.ZoneId;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.ZoneIdCodec;

/**
 * {@link ZoneId}对象的Jackson序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class ZoneIdSerializer extends EncoderSerializer<ZoneId> {

  @Serial
  private static final long serialVersionUID = 6231057212284169151L;

  /**
   * ZoneIdSerializer的单例实例。
   */
  public static final ZoneIdSerializer INSTANCE = new ZoneIdSerializer();

  /**
   * 构造一个ZoneIdSerializer实例。
   */
  public ZoneIdSerializer() {
    super(ZoneId.class, ZoneIdCodec.INSTANCE, JsonGenerator::writeString);
  }
}