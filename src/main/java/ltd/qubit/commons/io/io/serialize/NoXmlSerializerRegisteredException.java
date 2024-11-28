////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.io.serialize;

import ltd.qubit.commons.text.xml.XmlSerializationException;

/**
 * Thrown to indicate that no XML serializer was registered for the specified
 * class.
 *
 * @author Haixing Hu
 */
public class NoXmlSerializerRegisteredException extends
        XmlSerializationException {

  private static final long serialVersionUID = - 7020988206462175166L;

  private final Class<?> objectClass;

  public NoXmlSerializerRegisteredException(final Class<?> objectClass) {
    super("No XML serializer was registered for class " + objectClass);
    this.objectClass = objectClass;
  }

  public Class<?> getObjectClass() {
    return objectClass;
  }
}
