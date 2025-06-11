////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.resource;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Deque;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.SystemUtils;

import static ltd.qubit.commons.lang.StringUtils.isEmpty;
import static ltd.qubit.commons.lang.StringUtils.join;
import static ltd.qubit.commons.lang.StringUtils.replace;
import static ltd.qubit.commons.lang.StringUtils.splitToArray;

/**
 * 用于将资源位置解析为文件系统中文件的实用方法。主要供框架内部使用。
 * <p>
 * 考虑在核心包中使用 Spring 的资源抽象，以统一的方式处理各种文件资源。
 * {@link ResourceLoader} 的 {@code getResource()} 方法可以将任何位置解析为
 * {@link Resource} 对象，该对象又允许通过其 {@code getFile()} 方法获取文件
 * 系统中的 {@code java.io.File}。
 * <p>
 * 此类是 {@code org.springframework.core.io.ResourceUtils} 的副本，经过少量修改。
 * 它用于避免对 Spring Framework 的依赖。
 *
 * @author Juergen Hoeller
 * @author 胡海星
 */
public abstract class ResourceUtils {

  /**
   * 用于从类路径加载的伪 URL 前缀："classpath:"。
   */
  public static final String CLASSPATH_URL_PREFIX = "classpath:";

  /**
   * 用于从文件系统加载的 URL 前缀："file:"。
   */
  public static final String FILE_URL_PREFIX = "file:";

  /**
   * 用于从 JAR 文件加载的 URL 前缀："jar:"。
   */
  public static final String JAR_URL_PREFIX = "jar:";

  /**
   * 用于在 Tomcat 上从 WAR 文件加载的 URL 前缀："war:"。
   */
  public static final String WAR_URL_PREFIX = "war:";

  /**
   * 用于文件系统中文件的 URL 协议："file"。
   */
  public static final String URL_PROTOCOL_FILE = "file";

  /**
   * 用于 JAR 文件中条目的 URL 协议："jar"。
   */
  public static final String URL_PROTOCOL_JAR = "jar";

  /**
   * 用于 WAR 文件中条目的 URL 协议："war"。
   */
  public static final String URL_PROTOCOL_WAR = "war";

  /**
   * 用于 ZIP 文件中条目的 URL 协议："zip"。
   */
  public static final String URL_PROTOCOL_ZIP = "zip";

  /**
   * 用于 WebSphere JAR 文件中条目的 URL 协议："wsjar"。
   */
  public static final String URL_PROTOCOL_WSJAR = "wsjar";

  /**
   * 用于 JBoss JAR 文件中条目的 URL 协议："vfszip"。
   */
  public static final String URL_PROTOCOL_VFSZIP = "vfszip";

  /**
   * 用于 JBoss 文件系统资源的 URL 协议："vfsfile"。
   */
  public static final String URL_PROTOCOL_VFSFILE = "vfsfile";

  /**
   * 用于通用 JBoss VFS 资源的 URL 协议："vfs"。
   */
  public static final String URL_PROTOCOL_VFS = "vfs";

  /**
   * 常规 JAR 文件的文件扩展名：".jar"。
   */
  public static final String JAR_FILE_EXTENSION = ".jar";

  /**
   * JAR URL 和 JAR 中文件路径之间的分隔符："!"。
   */
  public static final String JAR_URL_SEPARATOR = "!/";

  /**
   * Tomcat 上 WAR URL 和 JAR 部分之间的特殊分隔符。
   */
  public static final String WAR_URL_SEPARATOR = "*/";

  /**
   * 包分隔符：{@code '.'}。
   */
  public static final char PACKAGE_SEPARATOR = '.';

  /**
   * 路径分隔符：{@code '/'}。
   */
  public static final char PATH_SEPARATOR = '/';

  public static final String FOLDER_SEPARATOR = "/";

  public static final char FOLDER_SEPARATOR_CHAR = '/';

  public static final String WINDOWS_FOLDER_SEPARATOR = "\\";

  public static final String TOP_PATH = "..";

  public static final String CURRENT_PATH = ".";

  /**
   * 返回给定的资源位置是否为 URL：一个特殊的"classpath"伪 URL 或标准 URL。
   *
   * @param resourceLocation
   *     要检查的位置字符串。
   * @return 位置是否符合 URL 的条件。
   * @see #CLASSPATH_URL_PREFIX
   * @see java.net.URL
   */
  public static boolean isUrl(@Nullable final String resourceLocation) {
    if (resourceLocation == null) {
      return false;
    }
    if (resourceLocation.startsWith(CLASSPATH_URL_PREFIX)) {
      return true;
    }
    try {
      toURL(resourceLocation);
      return true;
    } catch (final MalformedURLException ex) {
      return false;
    }
  }

  /**
   * 将给定的资源位置解析为 {@code java.net.URL}。
   * <p>不检查 URL 是否实际存在；仅返回给定位置对应的 URL。
   *
   * @param resourceLocation
   *     要解析的资源位置：可以是 "classpath:" 伪 URL、"file:" URL 或普通文件路径。
   * @return 相应的 URL 对象。
   * @throws FileNotFoundException
   *     如果资源无法解析为 URL。
   */
  public static URL getURL(final String resourceLocation)
      throws FileNotFoundException {
    if (resourceLocation == null) {
      throw new IllegalArgumentException("Resource location must not be null");
    }
    if (resourceLocation.startsWith(CLASSPATH_URL_PREFIX)) {
      final String path = resourceLocation.substring(
          CLASSPATH_URL_PREFIX.length());
      final ClassLoader cl = SystemUtils.getDefaultClassLoader();
      final URL url = (cl != null ? cl.getResource(path) : ClassLoader.getSystemResource(path));
      if (url == null) {
        final String description = "class path resource [" + path + "]";
        throw new FileNotFoundException(description
            + " cannot be resolved to URL because it does not exist");
      }
      return url;
    }
    try {
      // try URL
      return toURL(resourceLocation);
    } catch (final MalformedURLException ex) {
      // no URL -> treat as file path
      try {
        return new File(resourceLocation).toURI().toURL();
      } catch (final MalformedURLException ex2) {
        throw new FileNotFoundException("Resource location ["
            + resourceLocation
            + "] is neither a URL not a well-formed file path");
      }
    }
  }

  /**
   * 将给定的资源位置解析为 {@code java.io.File}，即文件系统中的文件。
   * <p>不检查文件是否实际存在；仅返回给定位置对应的 File 对象。
   *
   * @param resourceLocation
   *     要解析的资源位置：可以是 "classpath:" 伪 URL、"file:" URL 或普通文件路径。
   * @return 相应的 File 对象。
   * @throws FileNotFoundException
   *     如果资源无法解析为文件系统中的文件。
   */
  public static File getFile(final String resourceLocation)
      throws FileNotFoundException {
    if (resourceLocation == null) {
      throw new IllegalArgumentException("Resource location must not be null");
    }
    if (resourceLocation.startsWith(CLASSPATH_URL_PREFIX)) {
      final String path = resourceLocation.substring(
          CLASSPATH_URL_PREFIX.length());
      final String description = "class path resource [" + path + "]";
      final ClassLoader cl = SystemUtils.getDefaultClassLoader();
      final URL url = (cl != null ? cl.getResource(path) : ClassLoader.getSystemResource(path));
      if (url == null) {
        throw new FileNotFoundException(description
            + " cannot be resolved to absolute file path because it does not exist");
      }
      return getFile(url, description);
    }
    try {
      // try URL
      return getFile(toURL(resourceLocation));
    } catch (final MalformedURLException ex) {
      // no URL -> treat as file path
      return new File(resourceLocation);
    }
  }

  /**
   * 将给定的资源 URL 解析为 {@code java.io.File}，即文件系统中的文件。
   *
   * @param resourceUrl
   *     要解析的资源 URL。
   * @return 相应的 File 对象。
   * @throws FileNotFoundException
   *     如果 URL 无法解析为文件系统中的文件。
   */
  public static File getFile(final URL resourceUrl)
      throws FileNotFoundException {
    return getFile(resourceUrl, "URL");
  }

  /**
   * 将给定的资源 URL 解析为 {@code java.io.File}，即文件系统中的文件。
   *
   * @param resourceUrl
   *     要解析的资源 URL。
   * @param description
   *     创建 URL 的原始资源的描述（例如，类路径位置）。
   * @return 相应的 File 对象。
   * @throws FileNotFoundException
   *     如果 URL 无法解析为文件系统中的文件。
   */
  public static File getFile(final URL resourceUrl, final String description)
      throws FileNotFoundException {
    if (resourceUrl == null) {
      throw new IllegalArgumentException("Resource URL must not be null");
    }
    if (!URL_PROTOCOL_FILE.equals(resourceUrl.getProtocol())) {
      throw new FileNotFoundException(description
          + " cannot be resolved to absolute file path "
          + "because it does not reside in the file system: "
          + resourceUrl);
    }
    try {
      // URI decoding for special characters such as spaces.
      return new File(toURI(resourceUrl).getSchemeSpecificPart());
    } catch (final URISyntaxException ex) {
      // Fallback for URLs that are not valid URIs (should hardly ever happen).
      return new File(resourceUrl.getFile());
    }
  }

  /**
   * 将给定的资源 URI 解析为 {@code java.io.File}，即文件系统中的文件。
   *
   * @param resourceUri
   *     要解析的资源 URI。
   * @return a corresponding File object
   * @throws FileNotFoundException
   *     如果 URL 无法解析为文件系统中的文件。
   * @since 2.5
   */
  public static File getFile(final URI resourceUri)
      throws FileNotFoundException {
    return getFile(resourceUri, "URI");
  }

  /**
   * 将给定的资源 URI 解析为 {@code java.io.File}，即文件系统中的文件。
   *
   * @param resourceUri
   *     要解析的资源 URI。
   * @param description
   *     创建 URI 的原始资源的描述（例如，类路径位置）。
   * @return a corresponding File object
   * @throws FileNotFoundException
   *     如果 URL 无法解析为文件系统中的文件。
   * @since 2.5
   */
  public static File getFile(final URI resourceUri, final String description)
      throws FileNotFoundException {
    if (resourceUri == null) {
      throw new IllegalArgumentException("Resource URI must not be null");
    }
    if (!URL_PROTOCOL_FILE.equals(resourceUri.getScheme())) {
      throw new FileNotFoundException(description
          + " cannot be resolved to absolute file path "
          + "because it does not reside in the file system: "
          + resourceUri);
    }
    return new File(resourceUri.getSchemeSpecificPart());
  }

  /**
   * 判断给定的 URL 是否指向文件系统中的资源，即协议为 "file"、"vfsfile" 或 "vfs"。
   *
   * @param url
   *     要检查的 URL。
   * @return URL 是否已标识为文件系统 URL。
   */
  public static boolean isFileURL(final URL url) {
    final String protocol = url.getProtocol();
    return URL_PROTOCOL_FILE.equals(protocol)
        || URL_PROTOCOL_VFSFILE.equals(protocol)
        || URL_PROTOCOL_VFS.equals(protocol);
  }

  /**
   * 判断给定的 URL 是否指向 JAR 文件中的资源——例如，URL 的协议是否为 "jar"、
   * "war"、"zip"、"vfszip" 或 "wsjar"。
   *
   * @param url
   *     要检查的 URL。
   * @return URL 是否已标识为 JAR URL。
   */
  public static boolean isJarURL(final URL url) {
    final String protocol = url.getProtocol();
    return URL_PROTOCOL_JAR.equals(protocol)
        || URL_PROTOCOL_WAR.equals(protocol)
        || URL_PROTOCOL_ZIP.equals(protocol)
        || URL_PROTOCOL_VFSZIP.equals(protocol)
        || URL_PROTOCOL_WSJAR.equals(protocol);
  }

  /**
   * 判断给定的 URL 是否指向 JAR 文件本身，即协议为 "file" 且以 ".jar" 扩展名结尾。
   *
   * @param url
   *     要检查的 URL。
   * @return URL 是否已标识为 JAR 文件 URL。
   * @since 4.1
   */
  public static boolean isJarFileURL(final URL url) {
    return URL_PROTOCOL_FILE.equals(url.getProtocol())
        && url.getPath().toLowerCase().endsWith(JAR_FILE_EXTENSION);
  }

  /**
   * 从给定的 URL（可能指向 JAR 文件中的资源或 JAR 文件本身）中提取实际 JAR 文件的 URL。
   *
   * @param jarUrl
   *     原始 URL。
   * @return 实际 JAR 文件的 URL。
   * @throws MalformedURLException
   *     如果无法提取有效的 JAR 文件 URL。
   */
  public static URL extractJarFileURL(final URL jarUrl)
      throws MalformedURLException {
    final String urlFile = jarUrl.getFile();
    final int separatorIndex = urlFile.indexOf(JAR_URL_SEPARATOR);
    if (separatorIndex != -1) {
      String jarFile = urlFile.substring(0, separatorIndex);
      try {
        return toURL(jarFile);
      } catch (final MalformedURLException ex) {
        // Probably no protocol in original jar URL, like "jar:C:/mypath/myjar.jar".
        // This usually indicates that the jar file resides in the file system.
        if (!jarFile.startsWith("/")) {
          jarFile = "/" + jarFile;
        }
        return toURL(FILE_URL_PREFIX + jarFile);
      }
    } else {
      return jarUrl;
    }
  }

  /**
   * 从给定的 JAR/WAR URL（可能指向 JAR 文件中的资源或 JAR 文件本身）中提取最外层
   * 归档的 URL。
   * <p>在 JAR 文件嵌套在 WAR 文件中的情况下，这将返回指向 WAR 文件的 URL，
   * 因为该 URL 可在文件系统中解析。
   *
   * @param jarUrl
   *     原始 URL。
   * @return 实际 JAR 文件的 URL。
   * @throws MalformedURLException
   *     如果无法提取有效的 JAR 文件 URL。
   * @see #extractJarFileURL(URL)
   * @since 4.1.8
   */
  public static URL extractArchiveURL(final URL jarUrl)
      throws MalformedURLException {
    final String urlFile = jarUrl.getFile();
    final int endIndex = urlFile.indexOf(WAR_URL_SEPARATOR);
    if (endIndex != -1) {
      // Tomcat's "war:file:...mywar.war*/WEB-INF/lib/myjar.jar!/myentry.txt"
      final String warFile = urlFile.substring(0, endIndex);
      if (URL_PROTOCOL_WAR.equals(jarUrl.getProtocol())) {
        return toURL(warFile);
      }
      final int startIndex = warFile.indexOf(WAR_URL_PREFIX);
      if (startIndex != -1) {
        return toURL(warFile.substring(startIndex + WAR_URL_PREFIX.length()));
      }
    }
    // Regular "jar:file:...myjar.jar!/myentry.txt"
    return extractJarFileURL(jarUrl);
  }

  /**
   * 为给定的 URL 创建一个 URI 实例，首先将空格替换为 "%20" URI 编码。
   *
   * @param url
   *     要转换为 URI 实例的 URL。
   * @return URI 实例。
   * @throws URISyntaxException
   *     如果 URL 不是有效的 URI。
   * @see java.net.URL#toURI()
   */
  public static URI toURI(final URL url) throws URISyntaxException {
    return toURI(url.toString());
  }

  /**
   * 为给定的位置字符串创建一个 URI 实例，首先将空格替换为 "%20" URI 编码。
   *
   * @param location
   *     要转换为 URI 实例的位置字符串。
   * @return URI 实例。
   * @throws URISyntaxException
   *     如果位置不是有效的 URI。
   */
  public static URI toURI(final String location) throws URISyntaxException {
    return new URI(replace(location, " ", "%20"));
  }

  /**
   * 为给定的位置字符串创建一个 URL 实例，通过 URI 构造然后进行 URL 转换。
   *
   * @param location
   *     要转换为 URL 实例的位置字符串。
   * @return URL 实例。
   * @throws MalformedURLException
   *     如果位置不是有效的 URL。
   * @since 6.0
   */
  @SuppressWarnings("deprecation")  // on JDK 20
  public static URL toURL(final String location) throws MalformedURLException {
    try {
      // Prefer URI construction with toURL conversion (as of 6.1)
      return toURI(cleanPath(location)).toURL();
    } catch (final URISyntaxException | IllegalArgumentException ex) {
      // Lenient fallback to deprecated (on JDK 20) URL constructor,
      // e.g. for decoded location Strings with percent characters.
      return new URL(location);
    }
  }

  /**
   * 为给定的根 URL 和相对路径创建一个相对 URL。
   *
   * @param root
   *     根 URL。
   * @param relativePath
   *     相对路径。
   * @return
   *     解析后的相对 URL。
   * @throws MalformedURLException
   *     如果最终结果不是有效的 URL。
   * @since 6.0
   */
  public static URL toRelativeURL(final URL root, String relativePath)
      throws MalformedURLException {
    // # can appear in filenames, java.net.URL should not treat it as a fragment
    relativePath = replace(relativePath, "#", "%23");
    return toURL(applyRelativePath(root.toString(), relativePath));
  }

  /**
   * 在给定的连接上设置 {@link URLConnection#setUseCaches "useCaches"} 标志，
   * 优先使用 {@code false}，但对于 jar 资源，将该标志保留为其 JVM 默认值
   * （通常为 {@code true}）。
   *
   * @param con
   *     要设置标志的 URLConnection。
   */
  public static void useCachesIfNecessary(final URLConnection con) {
    if (!(con instanceof JarURLConnection)) {
      con.setUseCaches(false);
    }
  }

  /**
   * 将给定的相对路径应用于给定的 Java 资源路径，假定使用标准的 Java 文件夹
   * 分隔符（即"/"分隔符）。
   *
   * @param path
   *     起始路径（通常是完整的文件路径）。
   * @param relativePath
   *     要应用的相对路径（相对于上面的完整文件路径）。
   * @return 应用相对路径后得到的完整文件路径。
   */
  public static String applyRelativePath(final String path, final String relativePath) {
    final int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR_CHAR);
    if (separatorIndex != -1) {
      String newPath = path.substring(0, separatorIndex);
      if (!relativePath.startsWith(FOLDER_SEPARATOR)) {
        newPath += FOLDER_SEPARATOR_CHAR;
      }
      return newPath + relativePath;
    } else {
      return relativePath;
    }
  }

  /**
   * 通过抑制 "path/.." 和内部的简单点之类的序列来规范化路径。
   * <p>
   * 结果便于路径比较。对于其他用途，请注意 Windows 分隔符 ("\") 会被替换为
   * 简单斜杠。
   * <p>
   * <strong>注意</strong>，在安全上下文中不应依赖 {@code cleanPath}。应使用
   * 其他机制来防止路径遍历问题。
   *
   * @param path
   *     原始路径。
   * @return 规范化的路径。
   */
  public static String cleanPath(final String path) {
    if (isEmpty(path)) {
      return path;
    }
    final String normalizedPath = replace(path, WINDOWS_FOLDER_SEPARATOR, FOLDER_SEPARATOR);
    String pathToUse = normalizedPath;
    // Shortcut if there is no work to do
    if (pathToUse.indexOf('.') == -1) {
      return pathToUse;
    }
    // Strip prefix from path to analyze, to not treat it as part of the
    // first path element. This is necessary to correctly parse paths like
    // "file:core/../core/io/Resource.class", where the ".." should just
    // strip the first "core" directory while keeping the "file:" prefix.
    final int prefixIndex = pathToUse.indexOf(':');
    String prefix = "";
    if (prefixIndex != -1) {
      prefix = pathToUse.substring(0, prefixIndex + 1);
      if (prefix.contains(FOLDER_SEPARATOR)) {
        prefix = "";
      } else {
        pathToUse = pathToUse.substring(prefixIndex + 1);
      }
    }
    if (pathToUse.startsWith(FOLDER_SEPARATOR)) {
      prefix = prefix + FOLDER_SEPARATOR;
      pathToUse = pathToUse.substring(1);
    }
    final String[] pathArray = splitToArray(pathToUse, FOLDER_SEPARATOR_CHAR);
    // we never require more elements than pathArray and in the common case the same number
    final Deque<String> pathElements = new ArrayDeque<>(pathArray.length);
    int tops = 0;
    for (int i = pathArray.length - 1; i >= 0; i--) {
      final String element = pathArray[i];
      if (CURRENT_PATH.equals(element)) {
        // Points to current directory - drop it.
      } else if (TOP_PATH.equals(element)) {
        // Registering top path found.
        tops++;
      } else {
        if (tops > 0) {
          // Merging path element with element corresponding to top path.
          tops--;
        } else {
          // Normal path element found.
          pathElements.addFirst(element);
        }
      }
    }
    // All path elements stayed the same - shortcut
    if (pathArray.length == pathElements.size()) {
      return normalizedPath;
    }
    // Remaining top paths need to be retained.
    for (int i = 0; i < tops; i++) {
      pathElements.addFirst(TOP_PATH);
    }
    // If nothing else left, at least explicitly point to current path.
    if (pathElements.size() == 1
        && pathElements.getLast().isEmpty()
        && !prefix.endsWith(FOLDER_SEPARATOR)) {
      pathElements.addFirst(CURRENT_PATH);
    }
    final String joined = join(FOLDER_SEPARATOR_CHAR, pathElements);
    // avoid string concatenation with empty prefix
    return prefix.isEmpty() ? joined : prefix + joined;
  }

  /**
   * 在对两个路径进行规范化后比较它们。
   *
   * @param path1
   *     用于比较的第一个路径。
   * @param path2
   *     用于比较的第二个路径。
   * @return 两个路径在规范化后是否相等。
   */
  public static boolean pathEquals(final String path1, final String path2) {
    return cleanPath(path1).equals(cleanPath(path2));
  }

  /**
   * 给定一个输入类对象，返回一个由类的包名组成的字符串作为路径名，即所有点 ('.')
   * 都被斜杠 ('/') 替换。
   * <p>
   * 不会添加前导或尾随斜杠。结果可以与斜杠和资源名称连接，并直接提供给
   * {@code ClassLoader.getResource()}。要将其提供给 {@code Class.getResource}，
   * 还必须在返回的值前添加前导斜杠。
   *
   * @param clazz
   *     输入类。{@code null} 值或默认（空）包将导致返回空字符串 ("")。
   * @return 表示包名的路径。
   * @see ClassLoader#getResource
   * @see Class#getResource
   */
  public static String classPackageAsResourcePath(@Nullable final Class<?> clazz) {
    if (clazz == null) {
      return "";
    }
    final String className = clazz.getName();
    final int packageEndIndex = className.lastIndexOf(PACKAGE_SEPARATOR);
    if (packageEndIndex == -1) {
      return "";
    }
    final String packageName = className.substring(0, packageEndIndex);
    return packageName.replace(PACKAGE_SEPARATOR, PATH_SEPARATOR);
  }

  /**
   * 从给定的 Java 资源路径中提取文件名，例如
   * {@code "mypath/myfile.txt" → "myfile.txt"}。
   *
   * @param path
   *     文件路径（可以为 {@code null}）。
   * @return 提取的文件名，如果没有则为 {@code null}。
   */
  @Nullable
  public static String getFilename(@Nullable final String path) {
    if (path == null) {
      return null;
    }
    final int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR_CHAR);
    return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
  }

  /**
   * 获取指定资源的带缓冲的输入流。
   *
   * @param resource
   *     指定的资源。
   * @return
   *     指定资源的带缓冲的输入流。
   * @throws IOException
   *     如果发生I/O错误。
   */
  public static BufferedInputStream getInputStream(final Resource resource) throws IOException {
    return new BufferedInputStream(resource.getInputStream());
  }

  /**
   * 获取指定资源的读取器。
   *
   * @param resource
   *     指定的资源。
   * @return
   *     指定资源的读取器。
   * @throws IOException
   *     如果发生I/O错误。
   */
  public static BufferedReader getReader(final Resource resource) throws IOException {
    return getReader(resource, StandardCharsets.UTF_8);
  }

  /**
   * 获取指定资源的读取器。
   *
   * @param resource
   *     指定的资源。
   * @param charset
   *     指定的字符集。
   * @return
   *     指定资源的读取器。
   * @throws IOException
   *     如果发生I/O错误。
   */
  public static BufferedReader getReader(final Resource resource,
      final Charset charset) throws IOException {
    return new BufferedReader(new InputStreamReader(resource.getInputStream(), charset));
  }
}