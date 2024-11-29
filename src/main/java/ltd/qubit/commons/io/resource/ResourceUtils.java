////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * Utility methods for resolving resource locations to files in the file system.
 * Mainly for internal use within the framework.
 * <p>
 * Consider using Spring's Resource abstraction in the core package for handling
 * all kinds of file resources in a uniform manner. {@link ResourceLoader}'s
 * {@code getResource()} method can resolve any location to an {@link Resource}
 * object, which in turn allows one to obtain a {@code java.io.File} in the file
 * system through its {@code getFile()} method.
 * <p>
 * This class is a copy of {@code org.springframework.core.io.ResourceUtils}
 * with slight modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Juergen Hoeller
 * @author Haixing Hu
 */
public abstract class ResourceUtils {

  /**
   * Pseudo URL prefix for loading from the class path: "classpath:".
   */
  public static final String CLASSPATH_URL_PREFIX = "classpath:";

  /**
   * URL prefix for loading from the file system: "file:".
   */
  public static final String FILE_URL_PREFIX = "file:";

  /**
   * URL prefix for loading from a jar file: "jar:".
   */
  public static final String JAR_URL_PREFIX = "jar:";

  /**
   * URL prefix for loading from a war file on Tomcat: "war:".
   */
  public static final String WAR_URL_PREFIX = "war:";

  /**
   * URL protocol for a file in the file system: "file".
   */
  public static final String URL_PROTOCOL_FILE = "file";

  /**
   * URL protocol for an entry from a jar file: "jar".
   */
  public static final String URL_PROTOCOL_JAR = "jar";

  /**
   * URL protocol for an entry from a war file: "war".
   */
  public static final String URL_PROTOCOL_WAR = "war";

  /**
   * URL protocol for an entry from a zip file: "zip".
   */
  public static final String URL_PROTOCOL_ZIP = "zip";

  /**
   * URL protocol for an entry from a WebSphere jar file: "wsjar".
   */
  public static final String URL_PROTOCOL_WSJAR = "wsjar";

  /**
   * URL protocol for an entry from a JBoss jar file: "vfszip".
   */
  public static final String URL_PROTOCOL_VFSZIP = "vfszip";

  /**
   * URL protocol for a JBoss file system resource: "vfsfile".
   */
  public static final String URL_PROTOCOL_VFSFILE = "vfsfile";

  /**
   * URL protocol for a general JBoss VFS resource: "vfs".
   */
  public static final String URL_PROTOCOL_VFS = "vfs";

  /**
   * File extension for a regular jar file: ".jar".
   */
  public static final String JAR_FILE_EXTENSION = ".jar";

  /**
   * Separator between JAR URL and file path within the JAR: "!/".
   */
  public static final String JAR_URL_SEPARATOR = "!/";

  /**
   * Special separator between WAR URL and jar part on Tomcat.
   */
  public static final String WAR_URL_SEPARATOR = "*/";

  /**
   * The package separator character: {@code '.'}.
   */
  public static final char PACKAGE_SEPARATOR = '.';

  /**
   * The path separator character: {@code '/'}.
   */
  public static final char PATH_SEPARATOR = '/';

  public static final String FOLDER_SEPARATOR = "/";

  public static final char FOLDER_SEPARATOR_CHAR = '/';

  public static final String WINDOWS_FOLDER_SEPARATOR = "\\";

  public static final String TOP_PATH = "..";

  public static final String CURRENT_PATH = ".";

  /**
   * Return whether the given resource location is a URL: either a special
   * "classpath" pseudo URL or a standard URL.
   *
   * @param resourceLocation
   *     the location String to check
   * @return whether the location qualifies as a URL
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
   * Resolve the given resource location to a {@code java.net.URL}.
   * <p>Does not check whether the URL actually exists; simply returns
   * the URL that the given location would correspond to.
   *
   * @param resourceLocation
   *     the resource location to resolve: either a "classpath:" pseudo URL, a
   *     "file:" URL, or a plain file path
   * @return a corresponding URL object
   * @throws FileNotFoundException
   *     if the resource cannot be resolved to a URL
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
   * Resolve the given resource location to a {@code java.io.File}, i.e. to a
   * file in the file system.
   * <p>Does not check whether the file actually exists; simply returns
   * the File that the given location would correspond to.
   *
   * @param resourceLocation
   *     the resource location to resolve: either a "classpath:" pseudo URL, a
   *     "file:" URL, or a plain file path
   * @return a corresponding File object
   * @throws FileNotFoundException
   *     if the resource cannot be resolved to a file in the file system
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
   * Resolve the given resource URL to a {@code java.io.File}, i.e. to a file in
   * the file system.
   *
   * @param resourceUrl
   *     the resource URL to resolve
   * @return a corresponding File object
   * @throws FileNotFoundException
   *     if the URL cannot be resolved to a file in the file system
   */
  public static File getFile(final URL resourceUrl)
      throws FileNotFoundException {
    return getFile(resourceUrl, "URL");
  }

  /**
   * Resolve the given resource URL to a {@code java.io.File}, i.e. to a file in
   * the file system.
   *
   * @param resourceUrl
   *     the resource URL to resolve
   * @param description
   *     a description of the original resource that the URL was created for
   *     (for example, a class path location)
   * @return a corresponding File object
   * @throws FileNotFoundException
   *     if the URL cannot be resolved to a file in the file system
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
   * Resolve the given resource URI to a {@code java.io.File}, i.e. to a file in
   * the file system.
   *
   * @param resourceUri
   *     the resource URI to resolve
   * @return a corresponding File object
   * @throws FileNotFoundException
   *     if the URL cannot be resolved to a file in the file system
   * @since 2.5
   */
  public static File getFile(final URI resourceUri)
      throws FileNotFoundException {
    return getFile(resourceUri, "URI");
  }

  /**
   * Resolve the given resource URI to a {@code java.io.File}, i.e. to a file in
   * the file system.
   *
   * @param resourceUri
   *     the resource URI to resolve
   * @param description
   *     a description of the original resource that the URI was created for
   *     (for example, a class path location)
   * @return a corresponding File object
   * @throws FileNotFoundException
   *     if the URL cannot be resolved to a file in the file system
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
   * Determine whether the given URL points to a resource in the file system,
   * i.e. has protocol "file", "vfsfile" or "vfs".
   *
   * @param url
   *     the URL to check
   * @return whether the URL has been identified as a file system URL
   */
  public static boolean isFileURL(final URL url) {
    final String protocol = url.getProtocol();
    return URL_PROTOCOL_FILE.equals(protocol)
        || URL_PROTOCOL_VFSFILE.equals(protocol)
        || URL_PROTOCOL_VFS.equals(protocol);
  }

  /**
   * Determine whether the given URL points to a resource in a jar file &mdash;
   * for example, whether the URL has protocol "jar", "war, "zip", "vfszip", or
   * "wsjar".
   *
   * @param url
   *     the URL to check
   * @return whether the URL has been identified as a JAR URL
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
   * Determine whether the given URL points to a jar file itself, that is, has
   * protocol "file" and ends with the ".jar" extension.
   *
   * @param url
   *     the URL to check
   * @return whether the URL has been identified as a JAR file URL
   * @since 4.1
   */
  public static boolean isJarFileURL(final URL url) {
    return URL_PROTOCOL_FILE.equals(url.getProtocol())
        && url.getPath().toLowerCase().endsWith(JAR_FILE_EXTENSION);
  }

  /**
   * Extract the URL for the actual jar file from the given URL (which may point
   * to a resource in a jar file or to a jar file itself).
   *
   * @param jarUrl
   *     the original URL
   * @return the URL for the actual jar file
   * @throws MalformedURLException
   *     if no valid jar file URL could be extracted
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
   * Extract the URL for the outermost archive from the given jar/war URL (which
   * may point to a resource in a jar file or to a jar file itself).
   * <p>In the case of a jar file nested within a war file, this will return
   * a URL to the war file since that is the one resolvable in the file system.
   *
   * @param jarUrl
   *     the original URL
   * @return the URL for the actual jar file
   * @throws MalformedURLException
   *     if no valid jar file URL could be extracted
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
   * Create a URI instance for the given URL, replacing spaces with "%20" URI
   * encoding first.
   *
   * @param url
   *     the URL to convert into a URI instance
   * @return the URI instance
   * @throws URISyntaxException
   *     if the URL wasn't a valid URI
   * @see java.net.URL#toURI()
   */
  public static URI toURI(final URL url) throws URISyntaxException {
    return toURI(url.toString());
  }

  /**
   * Create a URI instance for the given location String, replacing spaces with
   * "%20" URI encoding first.
   *
   * @param location
   *     the location String to convert into a URI instance
   * @return the URI instance
   * @throws URISyntaxException
   *     if the location wasn't a valid URI
   */
  public static URI toURI(final String location) throws URISyntaxException {
    return new URI(replace(location, " ", "%20"));
  }

  /**
   * Create a URL instance for the given location String, going through URI
   * construction and then URL conversion.
   *
   * @param location
   *     the location String to convert into a URL instance
   * @return the URL instance
   * @throws MalformedURLException
   *     if the location wasn't a valid URL
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
   * Create a URL instance for the given root URL and relative path, going
   * through URI construction and then URL conversion.
   *
   * @param root
   *     the root URL to start from
   * @param relativePath
   *     the relative path to apply
   * @return the relative URL instance
   * @throws MalformedURLException
   *     if the end result is not a valid URL
   * @since 6.0
   */
  public static URL toRelativeURL(final URL root, String relativePath)
      throws MalformedURLException {
    // # can appear in filenames, java.net.URL should not treat it as a fragment
    relativePath = replace(relativePath, "#", "%23");
    return toURL(applyRelativePath(root.toString(), relativePath));
  }

  /**
   * Set the {@link URLConnection#setUseCaches "useCaches"} flag on the given
   * connection, preferring {@code false} but leaving the flag at its JVM
   * default value for jar resources (typically {@code true}).
   *
   * @param con
   *     the URLConnection to set the flag on
   */
  public static void useCachesIfNecessary(final URLConnection con) {
    if (!(con instanceof JarURLConnection)) {
      con.setUseCaches(false);
    }
  }

  /**
   * Apply the given relative path to the given Java resource path, assuming
   * standard Java folder separation (i.e. "/" separators).
   *
   * @param path
   *     the path to start from (usually a full file path)
   * @param relativePath
   *     the relative path to apply (relative to the full file path above)
   * @return the full file path that results from applying the relative path
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
   * Normalize the path by suppressing sequences like "path/.." and inner simple
   * dots.
   * <p>
   * The result is convenient for path comparison. For other uses, notice that
   * Windows separators ("\") are replaced by simple slashes.
   * <p>
   * <strong>NOTE</strong> that {@code cleanPath} should not be depended
   * upon in a security context. Other mechanisms should be used to prevent
   * path-traversal issues.
   *
   * @param path
   *     the original path.
   * @return the normalized path.
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
   * Compare two paths after normalization of them.
   * @param path1 first path for comparison
   * @param path2 second path for comparison
   * @return whether the two paths are equivalent after normalization
   */
  public static boolean pathEquals(final String path1, final String path2) {
    return cleanPath(path1).equals(cleanPath(path2));
  }

  /**
   * Given an input class object, return a string which consists of the class's
   * package name as a pathname, i.e., all dots ('.') are replaced by slashes
   * ('/').
   * <p>
   * Neither a leading nor trailing slash is added. The result could be
   * concatenated with a slash and the name of a resource and fed directly to
   * {@code ClassLoader.getResource()}. For it to be fed to
   * {@code Class.getResource} instead, a leading slash would also have to be
   * prepended to the returned value.
   *
   * @param clazz
   *     the input class. A {@code null} value or the default (empty) package
   *     will result in an empty string ("") being returned.
   * @return a path which represents the package name
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
   * Extract the filename from the given Java resource path, e.g.
   * {@code "mypath/myfile.txt" &rarr; "myfile.txt"}.
   *
   * @param path
   *     the file path (may be {@code null})
   * @return the extracted filename, or {@code null} if none
   */
  @Nullable
  public static String getFilename(@Nullable final String path) {
    if (path == null) {
      return null;
    }
    final int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR_CHAR);
    return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
  }

  public static BufferedInputStream getInputStream(final Resource resource) throws IOException {
    return new BufferedInputStream(resource.getInputStream());
  }

  public static BufferedReader getReader(final Resource resource) throws IOException {
    return getReader(resource, StandardCharsets.UTF_8);
  }

  public static BufferedReader getReader(final Resource resource,
      final Charset charset) throws IOException {
    return new BufferedReader(new InputStreamReader(resource.getInputStream(), charset));
  }
}
