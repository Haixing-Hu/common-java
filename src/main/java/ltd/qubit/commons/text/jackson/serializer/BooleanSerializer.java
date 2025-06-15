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

import com.fasterxml.jackson.core.JsonGenerator;

import ltd.qubit.commons.reflect.WriteMethodReference;
import ltd.qubit.commons.util.codec.Encoder;

/**
 * {@link Boolean}对象的Jackson序列化器抽象基类。
 *
 * @author 胡海星
 */
public abstract class BooleanSerializer extends EncoderSerializer<Boolean> {

  @Serial
  private static final long serialVersionUID = 3087228619436703111L;

  /**
   * 构造一个BooleanSerializer实例。
   *
   * @param encoder
   *     布尔值编码器。
   * @param writeMethod
   *     JSON写入方法引用。
   */
  public BooleanSerializer(final Encoder<Boolean, String> encoder,
      final WriteMethodReference<JsonGenerator> writeMethod) {
    super(Boolean.class, encoder, writeMethod);
  }
}