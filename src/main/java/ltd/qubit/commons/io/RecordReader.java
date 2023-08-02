////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

/**
 * This interface provides functions to read key-value pair records from an
 * input source.
 *
 * @author Haixing Hu
 */
public interface RecordReader<KEY, VALUE> extends Closeable {

  /**
   * Creates a key.
   *
   * @return A default constructed key object.
   * @throws IllegalAccessException
   *           if the class or its nullary constructor is not accessible.
   * @throws InstantiationException
   *           if this Class represents an abstract class, an interface, an
   *           array class, a primitive type, or void; or if the class has no
   *           nullary constructor; or if the instantiation fails for some other
   *           reason.
   * @throws ExceptionInInitializerError
   *           if the initialization provoked by this method fails.
   * @throws SecurityException
   *           If a security manager, s, is present and any of the following
   *           conditions is met:
   *           <ul>
   *           <li>invocation of s.checkMemberAccess(this, Member.PUBLIC) denies
   *           creation of new instances of this class.</li>
   *           <li>the caller's class loader is not the same as or an ancestor
   *           of the class loader for the current class and invocation of
   *           s.checkPackageAccess() denies access to the package of this
   *           class.</li>
   *           </ul>
   */
  KEY createKey() throws IllegalAccessException, InstantiationException;

  /**
   * Creates a value.
   *
   * @return A default constructed value object.
   * @throws IllegalAccessException
   *           if the class or its nullary constructor is not accessible.
   * @throws InstantiationException
   *           if this Class represents an abstract class, an interface, an
   *           array class, a primitive type, or void; or if the class has no
   *           nullary constructor; or if the instantiation fails for some other
   *           reason.
   * @throws ExceptionInInitializerError
   *           if the initialization provoked by this method fails.
   * @throws SecurityException
   *           If a security manager, s, is present and any of the following
   *           conditions is met:
   *           <ul>
   *           <li>invocation of s.checkMemberAccess(this, Member.PUBLIC) denies
   *           creation of new instances of this class</li>
   *           <li>the caller's class loader is not the same as or an ancestor
   *           of the class loader for the current class and invocation of
   *           s.checkPackageAccess() denies access to the package of this
   *           class.</li>
   *           </ul>
   */
  VALUE createValue() throws IllegalAccessException,
      InstantiationException;

  /**
   * Gets the current position in the input.
   *
   * @return the current position in the input.
   * @throws IOException
   *           if any I/O error occurred.
   */
  long getPosition() throws IOException;

  /**
   * Gets the radio of the input this RecordReader has consumed.
   *
   * @return the radio of the input this RecordReader has consumed, between 0.0f
   *         and 1.0f.
   * @throws IOException
   *           if any I/O error occurred.
   */
  float getProgress() throws IOException;

  /**
   * Tests whether the EOF is encountered.
   *
   * @return true if there is record could be read; false if at EOF.
   * @throws IOException
   *           if any I/O error occurred.
   */
  boolean hasNext() throws IOException;

  /**
   * Reads the next key/value pair from the input for processing.
   *
   * @return the next key/value pair read from the input.
   * @throws IOException
   *           if any I/O error occurred.
   */
  Map.Entry<KEY, VALUE> next() throws IOException;
}
