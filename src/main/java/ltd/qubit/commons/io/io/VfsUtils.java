////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.io;

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
 * Utility for detecting and accessing JBoss VFS in the classpath.
 * <p>
 * As of Spring 4.0, this class supports VFS 3.x on JBoss AS 6+
 * (package {@code org.jboss.vfs}) and is in particular compatible with JBoss AS
 * 7 and WildFly 8+.
 * <p>
 * Thanks go to Marius Bogoevici for the initial implementation.
 * <p>
 * <b>Note:</b> This is an internal class and should not be used outside the
 * framework.
 * <p>
 * This class is a copy of {@code org.springframework.core.io.VfsUtils}
 * with slight modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Costin Leau
 * @author Juergen Hoeller
 * @author Haixing Hu
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

  public static boolean exists(final Object vfsResource) {
    try {
      return (Boolean) invokeVfsMethod(VIRTUAL_FILE_METHOD_EXISTS, vfsResource);
    } catch (final IOException ex) {
      return false;
    }
  }

  public static boolean isReadable(final Object vfsResource) {
    try {
      return ((Long) invokeVfsMethod(VIRTUAL_FILE_METHOD_GET_SIZE, vfsResource)) > 0;
    } catch (final IOException ex) {
      return false;
    }
  }

  public static long getSize(final Object vfsResource) throws IOException {
    return (Long) invokeVfsMethod(VIRTUAL_FILE_METHOD_GET_SIZE, vfsResource);
  }

  public static long getLastModified(final Object vfsResource) throws IOException {
    return (Long) invokeVfsMethod(VIRTUAL_FILE_METHOD_GET_LAST_MODIFIED, vfsResource);
  }

  public static InputStream getInputStream(final Object vfsResource) throws IOException {
    return (InputStream) invokeVfsMethod(VIRTUAL_FILE_METHOD_GET_INPUT_STREAM, vfsResource);
  }

  public static URL getURL(final Object vfsResource) throws IOException {
    return (URL) invokeVfsMethod(VIRTUAL_FILE_METHOD_TO_URL, vfsResource);
  }

  public static URI getURI(final Object vfsResource) throws IOException {
    return (URI) invokeVfsMethod(VIRTUAL_FILE_METHOD_TO_URI, vfsResource);
  }

  public static String getName(final Object vfsResource) {
    try {
      return (String) invokeVfsMethod(VIRTUAL_FILE_METHOD_GET_NAME, vfsResource);
    } catch (final IOException ex) {
      throw new IllegalStateException("Cannot get resource name", ex);
    }
  }

  public static Object getRelative(final URL url) throws IOException {
    return invokeVfsMethod(VFS_METHOD_GET_ROOT_URL, null, url);
  }

  public static Object getChild(final Object vfsResource, final String path) throws IOException {
    return invokeVfsMethod(VIRTUAL_FILE_METHOD_GET_CHILD, vfsResource, path);
  }

  public static File getFile(final Object vfsResource) throws IOException {
    return (File) invokeVfsMethod(VIRTUAL_FILE_METHOD_GET_PHYSICAL_FILE, vfsResource);
  }

  public static Object getRoot(final URI url) throws IOException {
    return invokeVfsMethod(VFS_METHOD_GET_ROOT_URI, null, url);
  }

  // protected methods used by the support sub-package

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
