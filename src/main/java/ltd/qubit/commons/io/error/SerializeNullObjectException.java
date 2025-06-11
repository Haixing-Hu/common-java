////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.error;

/**
 * 序列化一个空对象时抛出此异常。
 *
 * @author 胡海星
 */
public final class SerializeNullObjectException extends SerializationException {

  private static final long serialVersionUID = -3566512187781686495L;

  /**
   * 构造一个 {@code SerializeNullObjectException}.
   */
  public SerializeNullObjectException() {
    super("Try to serialize a null object.");
  }
}