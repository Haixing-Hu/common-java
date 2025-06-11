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
 * {@link ResourceLoader} 的实现，它将普通路径解析为文件系统资源，而不是类路径
 * 资源（后者是 {@link DefaultResourceLoader} 的默认策略）。
 * <p>
 * <b>注意：</b>普通路径总是被解释为相对于当前 VM 工作目录，即使它们以斜杠开头。
 * （这与 Servlet 容器中的语义一致。）
 * <b>请使用显式的 "file:" 前缀来强制指定绝对文件路径。</b>
 * <p>
 * {@code org.springframework.context.support.FileSystemXmlApplicationContext}
 * 是一个功能齐全的 ApplicationContext 实现，提供相同的资源路径解析策略。
 * <p>
 * 此类是 {@code org.springframework.core.io.FileSystemResourceLoader} 的副本，
 * 经过少量修改。它用于避免对 Spring Framework 的依赖。
 *
 * @author 胡海星
 * @see DefaultResourceLoader
 * @see org.springframework.context.support.FileSystemXmlApplicationContext
 */
public class FileSystemResourceLoader extends DefaultResourceLoader {

  /**
   * 将资源路径解析为文件系统路径。
   * <p>
   * 注意：即使给定路径以斜杠开头，它也将被解释为相对于当前 VM 工作目录。
   *
   * @param path
   *     资源路径。
   * @return a corresponding Resource handle
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
   * 通过实现 {@link ContextResource} 接口显式表示上下文相关路径的
   * {@link FileSystemResource}。
   */
  private static class FileSystemContextResource extends FileSystemResource
      implements ContextResource {

    /**
     * 构造一个新的 {@code FileSystemContextResource}。
     *
     * @param path
     *     资源路径。
     */
    public FileSystemContextResource(final String path) {
      super(path);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPathWithinContext() {
      return getPath();
    }
  }
}