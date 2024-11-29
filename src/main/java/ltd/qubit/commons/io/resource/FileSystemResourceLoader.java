////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.resource;

/**
 * {@link ResourceLoader} implementation that resolves plain paths as file
 * system resources rather than as class path resources (the latter is
 * {@link DefaultResourceLoader}'s default strategy).
 * <p>
 * <b>NOTE:</b> Plain paths will always be interpreted as relative
 * to the current VM working directory, even if they start with a slash. (This
 * is consistent with the semantics in a Servlet container.)
 * <b>Use an explicit "file:" prefix to enforce an absolute file path.</b>
 * <p>
 * {@link org.springframework.context.support.FileSystemXmlApplicationContext}
 * is a full-fledged ApplicationContext implementation that provides the same
 * resource path resolution strategy.
 * <p>
 * This class is a copy of
 * {@code org.springframework.core.io.FileSystemResourceLoader} with slight
 * modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Juergen Hoeller
 * @author Haixing Hu
 * @see DefaultResourceLoader
 * @see org.springframework.context.support.FileSystemXmlApplicationContext
 */
public class FileSystemResourceLoader extends DefaultResourceLoader {

  /**
   * Resolve resource paths as file system paths.
   * <p>
   * Note: Even if a given path starts with a slash, it will get interpreted as
   * relative to the current VM working directory.
   *
   * @param path
   *     the path to the resource
   * @return the corresponding Resource handle
   * @see FileSystemResource
   */
  @Override
  protected Resource getResourceByPath(String path) {
    if (path.startsWith("/")) {
      path = path.substring(1);
    }
    return new FileSystemContextResource(path);
  }

  /**
   * FileSystemResource that explicitly expresses a context-relative path
   * through implementing the ContextResource interface.
   */
  private static class FileSystemContextResource extends FileSystemResource
      implements ContextResource {

    public FileSystemContextResource(final String path) {
      super(path);
    }

    @Override
    public String getPathWithinContext() {
      return getPath();
    }
  }
}
