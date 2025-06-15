////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.MapperConfig;

/**
 * 序列化/反序列化操作的枚举。
 *
 * @author 胡海星
 */
public enum Operation {

  /**
   * 序列化操作。
   */
  SERIALIZE,

  /**
   * 反序列化操作。
   */
  DESERIALIZE;

  /**
   * 获取指定ObjectMapper的配置。
   *
   * @param mapper 要获取配置的ObjectMapper实例。
   * @return 对应操作的MapperConfig实例。
   * @throws IllegalArgumentException 如果操作类型不支持。
   */
  public MapperConfig<?> getConfig(final ObjectMapper mapper) {
    switch (this) {
      case SERIALIZE:
        return mapper.getSerializationConfig();
      case DESERIALIZE:
        return mapper.getDeserializationConfig();
      default:
        throw new IllegalArgumentException("Unsupported operation: " + name());
    }
  }
}