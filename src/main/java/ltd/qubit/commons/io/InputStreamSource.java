////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Nonnull;

import ltd.qubit.commons.io.resource.ByteArrayResource;
import ltd.qubit.commons.io.resource.InputStreamResource;
import ltd.qubit.commons.io.resource.Resource;

/**
 * Simple interface for objects that are sources for an {@link InputStream}.
 * <p>
 * This is the base interface for Spring's more extensive {@link Resource}
 * interface.
 * <p>
 * For single-use streams, {@link InputStreamResource} can be used for any
 * given {@code InputStream}. Spring's {@link ByteArrayResource} or any
 * file-based {@code Resource} implementation can be used as a concrete
 * instance, allowing one to read the underlying content stream multiple times.
 * This makes this interface useful as an abstract content source for mail
 * attachments, for example.
 * <p>
 * This class is a copy of {@code org.springframework.core.io.InputStreamSource}
 * with slight modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Juergen Hoeller
 * @author Haixing Hu
 * @see InputStream
 */
public interface InputStreamSource {

  /**
   * Return an {@link InputStream} for the content of an underlying resource.
   * <p>
   * It is expected that each call creates a <i>fresh</i> stream.
   * <p>
   * This requirement is particularly important when you consider an API such as
   * JavaMail, which needs to be able to read the stream multiple times when
   * creating mail attachments. For such a use case, it is <i>required</i> that
   * each {@code getInputStream()} call returns a fresh stream.
   *
   * @return the input stream for the underlying resource (must not be {@code null})
   * @throws java.io.FileNotFoundException
   *     if the underlying resource does not exist
   * @throws IOException
   *     if the content stream could not be opened
   * @see Resource#isReadable()
   */
  @Nonnull
  InputStream getInputStream() throws IOException;
}