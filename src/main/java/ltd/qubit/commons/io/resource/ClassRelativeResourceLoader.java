////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.resource;

/**
 * {@link ResourceLoader} 实现，将普通资源路径解释为相对于给定的 {@code java.lang.Class}。
 * <p>
 * 此类是 {@code org.springframework.core.io.ClassRelativeResourceLoader} 的副本，
 * 略有修改。用于避免对 Spring Framework 的依赖。
 *
 * @author Juergen Hoeller
 * @author 胡海星
 * @see Class#getResource(String)
 * @see ClassPathResource#ClassPathResource(String, Class)
 */
public class ClassRelativeResourceLoader extends DefaultResourceLoader {

  private final Class<?> clazz;

  /**
   * 为给定的类创建新的 ClassRelativeResourceLoader。
   * 
   * @param clazz 用于加载资源的类
   */
  public ClassRelativeResourceLoader(final Class<?> clazz) {
    if (clazz == null) {
      throw new IllegalArgumentException("Class must not be null");
    }
    this.clazz = clazz;
    setClassLoader(clazz.getClassLoader());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Resource getResourceByPath(final String path) {
    return new ClassRelativeContextResource(path, this.clazz);
  }

  /**
   * 通过实现 ContextResource 接口明确表达上下文相对路径的 ClassPathResource。
   */
  private static class ClassRelativeContextResource extends ClassPathResource
      implements ContextResource {

    private final Class<?> clazz;

    /**
     * 构造一个新的 ClassRelativeContextResource。
     *
     * @param path 资源路径
     * @param clazz 用于加载资源的类
     */
    public ClassRelativeContextResource(final String path, final Class<?> clazz) {
      super(path, clazz);
      this.clazz = clazz;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPathWithinContext() {
      return getPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Resource createRelative(final String relativePath) {
      final String pathToUse = ResourceUtils.applyRelativePath(getPath(), relativePath);
      return new ClassRelativeContextResource(pathToUse, this.clazz);
    }
  }

}