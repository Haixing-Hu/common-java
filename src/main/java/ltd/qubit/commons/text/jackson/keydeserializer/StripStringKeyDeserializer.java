////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.Stripper;

/**
 * 用于 XML 序列化的字符串类键反序列化器。
 *
 * @author 胡海星
 */
@Immutable
public class StripStringKeyDeserializer extends KeyDeserializer implements Serializable {

  @Serial
  private static final long serialVersionUID = -2775703888645453983L;

  /**
   * 默认实例。
   */
  public static final StripStringKeyDeserializer INSTANCE = new StripStringKeyDeserializer();

  /**
   * 构造一个 {@link StripStringKeyDeserializer}。
   */
  public StripStringKeyDeserializer() {}

  /**
   * {@inheritDoc}
   */
  @Override
  public Object deserializeKey(final String key, final DeserializationContext ctxt) throws IOException {
    return new Stripper().strip(key);
  }
}