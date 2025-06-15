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

import ltd.qubit.commons.util.codec.Base64Codec;

/**
 * {@code byte[]}对象的Jackson序列化器，将{@code byte[]}序列化为Base64格式编码的字符串。
 *
 * @author 胡海星
 */
@Immutable
public class Base64ByteArraySerializer extends EncoderSerializer<byte[]> {

  @Serial
  private static final long serialVersionUID = 3804753080990851417L;

  /**
   * Base64ByteArraySerializer的单例实例。
   */
  public static final Base64ByteArraySerializer INSTANCE = new Base64ByteArraySerializer();

  /**
   * 构造一个新的Base64ByteArraySerializer实例。
   */
  public Base64ByteArraySerializer() {
    super(byte[].class, Base64Codec.INSTANCE, JsonGenerator::writeString);
  }
}