////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.io.resource;

/**
 * Extended interface for a resource that is loaded from an enclosing
 * 'context', e.g. from a {@link jakarta.servlet.ServletContext} but also
 * from plain classpath paths or relative file system paths (specified
 * without an explicit prefix, hence applying relative to the local
 * {@link ResourceLoader}'s context).
 * <p>
 * This class is a copy of {@code org.springframework.core.io.ContextResource}
 * with slight modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Juergen Hoeller
 * @author Haixing Hu
 * @see org.springframework.web.context.support.ServletContextResource
 */
public interface ContextResource extends Resource {

  /**
   * Return the path within the enclosing 'context'.
   * <p>
   * This is typically path relative to a context-specific root directory, e.g.
   * a ServletContext root or a PortletContext root.
   */
  String getPathWithinContext();
}
