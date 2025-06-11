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
 * 从封闭的"上下文"加载的资源的扩展接口，例如从 {@link jakarta.servlet.ServletContext} 
 * 加载，也可以从普通的类路径或相对文件系统路径加载（指定时没有显式前缀，
 * 因此相对于本地 {@link ResourceLoader} 的上下文应用）。
 * <p>
 * 此类是 {@code org.springframework.core.io.ContextResource} 的副本，
 * 略有修改。用于避免对 Spring Framework 的依赖。
 *
 * @author Juergen Hoeller
 * @author 胡海星
 * @see org.springframework.web.context.support.ServletContextResource
 */
public interface ContextResource extends Resource {

  /**
   * 返回封闭"上下文"中的路径。
   * <p>
   * 这通常是相对于特定上下文根目录的路径，例如 ServletContext 根目录或 PortletContext 根目录。
   */
  String getPathWithinContext();
}