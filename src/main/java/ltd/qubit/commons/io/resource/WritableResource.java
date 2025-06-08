////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

/**
 * Extended interface for a resource that supports writing to it.
 * Provides an {@link #getOutputStream() OutputStream accessor}.
 * <p>
 * This class is a copy of {@code org.springframework.core.io.WritableResource}
 * with slight modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Juergen Hoeller
 * @author Haixing Hu
 * @see OutputStream
 */
public interface WritableResource extends Resource {

  /**
   * Indicate whether the contents of this resource can be written via
   * {@link #getOutputStream()}.
   * <p>
   * Will be {@code true} for typical resource descriptors; note that actual
   * content writing may still fail when attempted. However, a value of
   * {@code false} is a definitive indication that the resource content cannot
   * be modified.
   *
   * @see #getOutputStream()
   * @see #isReadable()
   */
  default boolean isWritable() {
    return true;
  }

  /**
   * Return an {@link OutputStream} for the underlying resource, allowing to
   * (over-)write its content.
   *
   * @throws IOException
   *     if the stream could not be opened
   * @see #getInputStream()
   */
  OutputStream getOutputStream() throws IOException;

  /**
   * Return a {@link WritableByteChannel}.
   * <p>
   * It is expected that each call creates a <i>fresh</i> channel.
   * <p>
   * The default implementation returns
   * {@link Channels#newChannel(OutputStream)} with the result of
   * {@link #getOutputStream()}.
   *
   * @return the byte channel for the underlying resource (must not be
   *     {@code null})
   * @throws FileNotFoundException
   *     if the underlying resource doesn't exist
   * @throws IOException
   *     if the content channel could not be opened
   * @see #getOutputStream()
   * @since 5.0
   */
  default WritableByteChannel writableChannel() throws IOException {
    return Channels.newChannel(getOutputStream());
  }
}