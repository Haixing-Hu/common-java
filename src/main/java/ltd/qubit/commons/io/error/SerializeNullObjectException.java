////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.error;

/**
 * Thrown to indicate trying to serialize a null object.
 *
 * @author Haixing Hu
 */
public final class SerializeNullObjectException extends SerializationException {

  private static final long serialVersionUID = -3566512187781686495L;

  public SerializeNullObjectException() {
    super("Try to serialize a null object.");
  }
}
