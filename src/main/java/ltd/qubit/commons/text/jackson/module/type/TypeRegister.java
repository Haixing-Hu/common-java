////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.type;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

/**
 * 类型注册器接口，为指定类型提供Jackson序列化器、反序列化器、键序列化器、键反序列化器的提供者。
 *
 * @param <T>
 *     要被序列化和反序列化的对象类型。
 * @author 胡海星
 */
public interface TypeRegister<T> {

  /**
   * 获取要注册的类型。
   *
   * @return 要注册的类型。
   */
  Class<T> getType();

  /**
   * 获取用于序列化该类型的序列化器。
   *
   * @return 用于序列化该类型的序列化器。
   */
  JsonSerializer<T> getSerializer();

  /**
   * 获取用于反序列化该类型的反序列化器。
   *
   * @return 用于反序列化该类型的反序列化器。
   */
  JsonDeserializer<T> getDeserializer();

  /**
   * 获取用于序列化该类型作为Map键的序列化器。
   *
   * @return 用于序列化该类型作为Map键的序列化器。
   */
  JsonSerializer<T> getKeySerializer();

  /**
   * 获取用于反序列化该类型作为Map键的反序列化器。
   *
   * @return 用于反序列化该类型作为Map键的反序列化器。
   */
  KeyDeserializer getKeyDeserializer();
}