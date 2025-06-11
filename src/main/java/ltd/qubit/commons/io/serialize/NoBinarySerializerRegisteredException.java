////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.serialize;

import ltd.qubit.commons.io.error.SerializationException;

/**
 * 抛出此异常以指示没有为指定类注册二进制序列化器。
 *
 * @author 胡海星
 */
public class NoBinarySerializerRegisteredException extends
    SerializationException {

  private static final long serialVersionUID = -7020988206462175166L;

  private final Class<?> objectClass;

  /**
   * 构造一个表示没有为指定类注册二进制序列化器的异常。
   *
   * @param objectClass
   *     没有注册二进制序列化器的类。
   */
  public NoBinarySerializerRegisteredException(final Class<?> objectClass) {
    super("No binary serializer was registered for class " + objectClass);
    this.objectClass = objectClass;
  }

  /**
   * 返回没有注册二进制序列化器的类。
   *
   * @return 没有注册二进制序列化器的类。
   */
  public Class<?> getObjectClass() {
    return objectClass;
  }
}