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
 * {@link ResourceLoader} implementation that interprets plain resource paths
 * as relative to a given {@code java.lang.Class}.
 * <p>
 * This class is a copy of {@code org.springframework.core.io.ClassRelativeResourceLoader}
 * with slight modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Juergen Hoeller
 * @author Haixing Hu
 * @see Class#getResource(String)
 * @see ClassPathResource#ClassPathResource(String, Class)
 */
public class ClassRelativeResourceLoader extends DefaultResourceLoader {

  private final Class<?> clazz;

  /**
   * Create a new ClassRelativeResourceLoader for the given class.
   * @param clazz the class to load resources through
   */
  public ClassRelativeResourceLoader(final Class<?> clazz) {
    if (clazz == null) {
      throw new IllegalArgumentException("Class must not be null");
    }
    this.clazz = clazz;
    setClassLoader(clazz.getClassLoader());
  }

  @Override
  protected Resource getResourceByPath(final String path) {
    return new ClassRelativeContextResource(path, this.clazz);
  }

  /**
   * ClassPathResource that explicitly expresses a context-relative path
   * through implementing the ContextResource interface.
   */
  private static class ClassRelativeContextResource extends ClassPathResource
      implements ContextResource {

    private final Class<?> clazz;

    public ClassRelativeContextResource(final String path, final Class<?> clazz) {
      super(path, clazz);
      this.clazz = clazz;
    }

    @Override
    public String getPathWithinContext() {
      return getPath();
    }

    @Override
    public Resource createRelative(final String relativePath) {
      final String pathToUse = ResourceUtils.applyRelativePath(getPath(), relativePath);
      return new ClassRelativeContextResource(pathToUse, this.clazz);
    }
  }

}
