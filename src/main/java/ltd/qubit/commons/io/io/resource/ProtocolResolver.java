////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.io.resource;

import javax.annotation.Nullable;

/**
 * A resolution strategy for protocol-specific resource handles.
 * <p>
 * Used as an SPI for {@link DefaultResourceLoader}, allowing for
 * custom protocols to be handled without subclassing the loader
 * implementation (or application context implementation).
 * <p>
 * This class is a copy of
 * {@code org.springframework.core.io.ProtocolResolver} with slight
 * modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Juergen Hoeller
 * @author Haixing Hu
 * @see DefaultResourceLoader#addProtocolResolver
 */
@FunctionalInterface
public interface ProtocolResolver {

  /**
   * Resolve the given location against the given resource loader
   * if this implementation's protocol matches.
   * @param location the user-specified resource location
   * @param resourceLoader the associated resource loader
   * @return a corresponding {@code Resource} handle if the given location
   * matches this resolver's protocol, or {@code null} otherwise
   */
  @Nullable
  Resource resolve(String location, ResourceLoader resourceLoader);
}
