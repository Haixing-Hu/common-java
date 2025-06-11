////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.serialize;

import ltd.qubit.commons.text.xml.XmlSerializationException;

/**
 * 抛出此异常以指示没有为指定类注册 XML 序列化器。
 *
 * @author 胡海星
 */
public class NoXmlSerializerRegisteredException extends
        XmlSerializationException {

  private static final long serialVersionUID = - 7020988206462175166L;

  private final Class<?> objectClass;

  /**
   * 构造一个表示没有为指定类注册 XML 序列化器的异常。
   *
   * @param objectClass
   *     没有注册 XML 序列化器的类。
   */ 
  public NoXmlSerializerRegisteredException(final Class<?> objectClass) {
    super("No XML serializer was registered for class " + objectClass);
    this.objectClass = objectClass;
  }

  /**
   * 返回没有注册 XML 序列化器的类。
   *
   * @return 没有注册 XML 序列化器的类。
   */
  public Class<?> getObjectClass() {
    return objectClass;
  }
}