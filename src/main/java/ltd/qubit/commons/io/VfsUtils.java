////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;

import javax.annotation.Nullable;

import ltd.qubit.commons.error.ExceptionUtils;

import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_OBJECT_ARRAY;

/**
 * 用于在类路径中检测和访问 JBoss VFS 的实用工具。
 *
 * <p>从 Spring 4.0 开始，这个类支持 JBoss AS 6+ 上的 VFS 3.x（包名为
 * {@code org.jboss.vfs}），并且特别与 JBoss AS 7 和 WildFly 8+ 兼容。
 *
 * <p>感谢 Marius Bogoevici 的初步实现。
 *
 * <p><b>注意：</b>这是一个内部类，不应在框架之外使用。
 *
 * <p>这个类是 {@code org.springframework.core.io.VfsUtils} 的一个副本，
 * 经过了轻微的修改。它用于避免对 Spring Framework 的依赖。
 *
 * @author Costin Leau
 * @author Juergen Hoeller
 * @author 胡海星
 */
public abstract class VfsUtils {

  private static final String VFS3_PKG = "org.jboss.vfs.";
  private static final String VFS_NAME = "VFS";

  private static final Method VFS_METHOD_GET_ROOT_URL;
  private static final Method VFS_METHOD_GET_ROOT_URI;

  private static final Method VIRTUAL_FILE_METHOD_EXISTS;
  private static final Method VIRTUAL_FILE_METHOD_GET_INPUT_STREAM;
  private static final Method VIRTUAL_FILE_METHOD_GET_SIZE;
  private static final Method VIRTUAL_FILE_METHOD_GET_LAST_MODIFIED;
  private static final Method VIRTUAL_FILE_METHOD_TO_URL;
  private static final Method VIRTUAL_FILE_METHOD_TO_URI;
  private static final Method VIRTUAL_FILE_METHOD_GET_NAME;
  private static final Method VIRTUAL_FILE_METHOD_GET_PATH_NAME;
  private static final Method VIRTUAL_FILE_METHOD_GET_PHYSICAL_FILE;
  private static final Method VIRTUAL_FILE_METHOD_GET_CHILD;

  protected static final Class<?> VIRTUAL_FILE_VISITOR_INTERFACE;
  protected static final Method VIRTUAL_FILE_METHOD_VISIT;

  private static final Field VISITOR_ATTRIBUTES_FIELD_RECURSE;

  static {
    final ClassLoader loader = VfsUtils.class.getClassLoader();
    try {
      final Class<?> vfsClass = loader.loadClass(VFS3_PKG + VFS_NAME);
      VFS_METHOD_GET_ROOT_URL = vfsClass.getMethod("getChild", URL.class);
      VFS_METHOD_GET_ROOT_URI = vfsClass.getMethod("getChild", URI.class);

      final Class<?> virtualFile = loader.loadClass(VFS3_PKG + "VirtualFile");
      VIRTUAL_FILE_METHOD_EXISTS = virtualFile.getMethod("exists");
      VIRTUAL_FILE_METHOD_GET_INPUT_STREAM = virtualFile.getMethod("openStream");
      VIRTUAL_FILE_METHOD_GET_SIZE = virtualFile.getMethod("getSize");
      VIRTUAL_FILE_METHOD_GET_LAST_MODIFIED = virtualFile.getMethod("getLastModified");
      VIRTUAL_FILE_METHOD_TO_URI = virtualFile.getMethod("toURI");
      VIRTUAL_FILE_METHOD_TO_URL = virtualFile.getMethod("toURL");
      VIRTUAL_FILE_METHOD_GET_NAME = virtualFile.getMethod("getName");
      VIRTUAL_FILE_METHOD_GET_PATH_NAME = virtualFile.getMethod("getPathName");
      VIRTUAL_FILE_METHOD_GET_PHYSICAL_FILE = virtualFile.getMethod("getPhysicalFile");
      VIRTUAL_FILE_METHOD_GET_CHILD = virtualFile.getMethod("getChild", String.class);

      VIRTUAL_FILE_VISITOR_INTERFACE = loader.loadClass(VFS3_PKG + "VirtualFileVisitor");
      VIRTUAL_FILE_METHOD_VISIT = virtualFile.getMethod("visit", VIRTUAL_FILE_VISITOR_INTERFACE);

      final Class<?> visitorAttributesClass = loader.loadClass(VFS3_PKG + "VisitorAttributes");
      VISITOR_ATTRIBUTES_FIELD_RECURSE = visitorAttributesClass.getField("RECURSE");
    } catch (final Throwable ex) {
      throw new IllegalStateException(
          "Could not detect JBoss VFS infrastructure", ex);
    }
  }

  /**
   * 调用指定的VFS方法。
   *
   * @param method
   *     要调用的方法。
   * @param target
   *     调用方法的对象。
   * @param args
   *     方法的参数。
   * @return 调用方法后的返回值。
   * @throws IOException
   *     如果发生I/O错误。
   */
  public static Object invokeVfsMethod(final Method method,
      @Nullable final Object target, final Object... args) throws IOException {
    try {
      return method.invoke(target, args);
    } catch (final InvocationTargetException ex) {
      final Throwable targetEx = ex.getTargetException();
      if (targetEx instanceof IOException) {
        throw (IOException) targetEx;
      }
      ExceptionUtils.handleInvocationTargetException(ex);
    } catch (final Exception ex) {
      ExceptionUtils.handleReflectionException(ex);
    }

    throw new IllegalStateException("Invalid code path reached");
  }

  /**
   * 检查指定的VFS资源是否存在。
   *
   * @param vfsResource
   *     指定的VFS资源。
   * @return
   *     如果指定的VFS资源存在，则返回{@code true}；否则返回{@code false}。
   */
  public static boolean exists(final Object vfsResource) {
    try {
      return (Boolean) invokeVfsMethod(VIRTUAL_FILE_METHOD_EXISTS, vfsResource);
    } catch (final IOException ex) {
      return false;
    }
  }

  /**
   * 检查指定的VFS资源是否可读。
   *
   * @param vfsResource
   *     指定的VFS资源。
   * @return
   *     如果指定的VFS资源可读，则返回{@code true}；否则返回{@code false}。
   */
  public static boolean isReadable(final Object vfsResource) {
    try {
      return ((Long) invokeVfsMethod(VIRTUAL_FILE_METHOD_GET_SIZE, vfsResource)) > 0;
    } catch (final IOException ex) {
      return false;
    }
  }

  /**
   * 获取指定的VFS资源的大小。
   *
   * @param vfsResource
   *     指定的VFS资源。
   * @return
   *     指定的VFS资源的大小。
   * @throws IOException
   *     如果发生I/O错误。
   */
  public static long getSize(final Object vfsResource) throws IOException {
    return (Long) invokeVfsMethod(VIRTUAL_FILE_METHOD_GET_SIZE, vfsResource);
  }

  /**
   * 获取指定的VFS资源的最后修改时间。
   *
   * @param vfsResource
   *     指定的VFS资源。
   * @return
   *     指定的VFS资源的最后修改时间。
   * @throws IOException
   *     如果发生I/O错误。
   */
  public static long getLastModified(final Object vfsResource) throws IOException {
    return (Long) invokeVfsMethod(VIRTUAL_FILE_METHOD_GET_LAST_MODIFIED, vfsResource);
  }

  /**
   * 获取指定的VFS资源的输入流。
   *
   * @param vfsResource
   *     指定的VFS资源。
   * @return
   *     指定的VFS资源的输入流。
   * @throws IOException
   *     如果发生I/O错误。
   */
  public static InputStream getInputStream(final Object vfsResource) throws IOException {
    return (InputStream) invokeVfsMethod(VIRTUAL_FILE_METHOD_GET_INPUT_STREAM, vfsResource);
  }

  /**
   * 获取指定的VFS资源的URL。
   *
   * @param vfsResource
   *     指定的VFS资源。
   * @return
   *     指定的VFS资源的URL。
   * @throws IOException
   *     如果发生I/O错误。
   */
  public static URL getURL(final Object vfsResource) throws IOException {
    return (URL) invokeVfsMethod(VIRTUAL_FILE_METHOD_TO_URL, vfsResource);
  }

  /**
   * 获取指定的VFS资源的URI。
   *
   * @param vfsResource
   *     指定的VFS资源。
   * @return
   *     指定的VFS资源的URI。
   * @throws IOException
   *     如果发生I/O错误。
   */
  public static URI getURI(final Object vfsResource) throws IOException {
    return (URI) invokeVfsMethod(VIRTUAL_FILE_METHOD_TO_URI, vfsResource);
  }

  /**
   * 获取指定的VFS资源的名称。
   *
   * @param vfsResource
   *     指定的VFS资源。
   * @return
   *     指定的VFS资源的名称。
   */
  public static String getName(final Object vfsResource) {
    try {
      return (String) invokeVfsMethod(VIRTUAL_FILE_METHOD_GET_NAME, vfsResource);
    } catch (final IOException ex) {
      throw new IllegalStateException("Cannot get resource name", ex);
    }
  }

  /**
   * 获取相对于指定URL的VFS资源。
   *
   * @param url
   *     指定的URL。
   * @return
   *     相对于指定URL的VFS资源。
   * @throws IOException
   *     如果发生I/O错误。
   */
  public static Object getRelative(final URL url) throws IOException {
    return invokeVfsMethod(VFS_METHOD_GET_ROOT_URL, null, url);
  }

  /**
   * 获取指定的VFS资源的子资源。
   *
   * @param vfsResource
   *     指定的VFS资源。
   * @param path
   *     子资源的路径。
   * @return
   *     指定的VFS资源的子资源。
   * @throws IOException
   *     如果发生I/O错误。
   */
  public static Object getChild(final Object vfsResource, final String path) throws IOException {
    return invokeVfsMethod(VIRTUAL_FILE_METHOD_GET_CHILD, vfsResource, path);
  }

  /**
   * 获取指定的VFS资源对应的物理文件。
   *
   * @param vfsResource
   *     指定的VFS资源。
   * @return
   *     指定的VFS资源对应的物理文件。
   * @throws IOException
   *     如果发生I/O错误。
   */
  public static File getFile(final Object vfsResource) throws IOException {
    return (File) invokeVfsMethod(VIRTUAL_FILE_METHOD_GET_PHYSICAL_FILE, vfsResource);
  }

  /**
   * 获取指定URI的根VFS资源。
   *
   * @param url
   *     指定的URI。
   * @return
   *     指定URI的根VFS资源。
   * @throws IOException
   *     如果发生I/O错误。
   */
  public static Object getRoot(final URI url) throws IOException {
    return invokeVfsMethod(VFS_METHOD_GET_ROOT_URI, null, url);
  }

  // protected methods used by the support sub-package

  /**
   * 获取指定URL的根VFS资源。
   *
   * @param url
   *     指定的URL。
   * @return
   *     指定URL的根VFS资源。
   * @throws IOException
   *     如果发生I/O错误。
   */
  public static Object getRoot(final URL url) throws IOException {
    return invokeVfsMethod(VFS_METHOD_GET_ROOT_URL, null, url);
  }

  @Nullable
  protected static Object doGetVisitorAttributes() {
    try {
      return VISITOR_ATTRIBUTES_FIELD_RECURSE.get(null);
    } catch (final IllegalAccessException ex) {
      ExceptionUtils.handleReflectionException(ex);
    }
    throw new IllegalStateException("Should never get here");
  }

  @Nullable
  protected static String doGetPath(final Object resource) {
    try {
      return (String) VIRTUAL_FILE_METHOD_GET_PATH_NAME.invoke(resource, EMPTY_OBJECT_ARRAY);
    } catch (final Exception ex) {
      ExceptionUtils.handleReflectionException(ex);
    }
    throw new IllegalStateException("Should never get here");
  }
}