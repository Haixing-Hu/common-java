////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.serialize;

import ltd.qubit.commons.io.error.SerializationException;

/**
 * Thrown to indicate that no binary serializer was registered for the specified
 * class.
 *
 * @author Haixing Hu
 */
public class NoBinarySerializerRegisteredException extends
    SerializationException {

  private static final long serialVersionUID = -7020988206462175166L;

  private final Class<?> objectClass;

  public NoBinarySerializerRegisteredException(final Class<?> objectClass) {
    super("No binary serializer was registered for class " + objectClass);
    this.objectClass = objectClass;
  }

  public Class<?> getObjectClass() {
    return objectClass;
  }
}
