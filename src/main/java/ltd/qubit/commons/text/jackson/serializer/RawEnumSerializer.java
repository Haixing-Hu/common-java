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

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.util.codec.RawEnumCodec;

/**
 * 枚举类的Jackson序列化器。
 *
 * @author 胡海星
 */
@SuppressWarnings("rawtypes")
@Immutable
public class RawEnumSerializer extends EncoderSerializer<Enum> {

  @Serial
  private static final long serialVersionUID = -2540120793385986043L;

  /**
   * RawEnumSerializer的单例实例。
   */
  public static final RawEnumSerializer INSTANCE = new RawEnumSerializer();

  /**
   * 构造一个RawEnumSerializer实例。
   *
   * <p>使RawEnumCodec支持@JsonValue注解。
   */
  public RawEnumSerializer() {
    // make the RawEnumCodec support @JsonValue annotation
    super(Enum.class, new RawEnumCodec(true), JsonGenerator::writeString);
  }
}