////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.serialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * A {@link BinarySerializer} provides interface to serialize and deserialize
 * objects to and from binary streams.
 *
 * <p><b>NOTE</b>: the implementation of this interface <b>MUST</b> be thread
 * safe.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public interface BinarySerializer {

  /**
   * Deserializes an object from a binary input stream.
   *
   * @param in
   *     A binary input stream.
   * @param allowNull
   *     Indicates whether to allowed the returned object to be {@code null}.
   * @return The object deserialized from the binary input stream, or {@code
   *     null} if the object stored in the binary input stream is null and the
   *     argument {@code allowNull} is true.
   * @throws IOException
   *     If any I/O error occurred.
   */
  Object deserialize(InputStream in, boolean allowNull)
      throws IOException;

  /**
   * Serializes an object to a binary output stream.
   *
   * @param out
   *     A binary output stream.
   * @param obj
   *     The object to be serialized. It could be {@code null}.
   * @throws IOException
   *     If any I/O error occurred.
   */
  void serialize(OutputStream out, @Nullable Object obj)
      throws IOException;

}
