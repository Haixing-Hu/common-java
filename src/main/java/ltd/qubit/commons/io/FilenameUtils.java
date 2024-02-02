////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2024.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

import javax.annotation.Nullable;

import ltd.qubit.commons.net.Url;

/**
 * Provides utility methods for manipulating filenames.
 *
 * @author Haixing Hu
 */
public class FilenameUtils {

  /**
   * Gets the base name (i.e. the part up to and not including the last ".") of
   * a filename.
   * <p>
   * Will return the file name itself if it doesn't contain any dots.
   *
   * @param filename
   *     the filename.
   * @return
   *     the base name of filename, or {@code null} if the filename is {@code null}.
   */
  @Nullable
  public static String getBaseNameFromFilename(@Nullable final String filename) {
    if (filename == null) {
      return null;
    }
    final int extensionIndex = filename.lastIndexOf('.');
    return extensionIndex < 0 ? filename : filename.substring(0, extensionIndex);
  }

  /**
   * Gets the extension (i.e. the part after the last ".") of a filename.
   * <p>
   * Will return an empty string if it doesn't contain any dots.
   *
   * @param filename
   *     the filename.
   * @return
   *     the extension of the filename, or {@code null} if the filename is
   *     {@code null}. If the filename doesn't contain any dots, an empty
   *     string is returned.
   */
  @Nullable
  public static String getExtensionFromFilename(@Nullable final String filename) {
    if (filename == null) {
      return null;
    }
    final int extensionIndex = filename.lastIndexOf('.');
    return extensionIndex < 0 ? "" : filename.substring(extensionIndex + 1);
  }

  /**
   * Gets the base name (i.e. the part up to and not including the last ".") of
   * the last path segment of a file name.
   * <p>
   * Will return the file name itself if it doesn't contain any dots. All
   * leading directories of the {@code file name} parameter are skipped.
   *
   * @param path
   *     the path of the file to obtain the base name of.
   * @return
   *     the base name of file name of the file in the specified path, or
   *     {@code null} if the path is {@code null}, or the path is the path
   *     of a directory (i.e., the filename of the path is an empty string).
   */
  @Nullable
  public static String getBasename(@Nullable final Path path) {
    if (path == null) {
      return null;
    }
    final Path filename = path.getFileName();
    if (filename == null || filename.toString().isEmpty()) {
      return null;
    }
    return getBaseNameFromFilename(filename.toString());
  }

  /**
   * Gets the base name (i.e. the part up to and not including the last ".") of
   * the last path segment of a file name.
   * <p>
   * Will return the file name itself if it doesn't contain any dots. All
   * leading directories of the {@code file name} parameter are skipped.
   *
   * @param path
   *     the path of the file to obtain the base name of.
   * @return
   *     the base name of file name of the file in the specified path, or
   *     {@code null} if the path is {@code null}, or the path is the path
   *     of a directory (i.e., the filename of the path is an empty string).
   */
  @Nullable
  public static String getBasename(@Nullable final File path) {
    if (path == null) {
      return null;
    }
    final String filename = path.getName();
    if (filename.isEmpty()) {
      return null;
    }
    return getBaseNameFromFilename(filename);
  }

  /**
   * Gets the base name (i.e. the part up to and not including the last ".") of
   * the last path segment of a file name.
   * <p>
   * Will return the file name itself if it doesn't contain any dots. All
   * leading directories of the {@code file name} parameter are skipped.
   *
   * @param path
   *     the path of the file to obtain the base name of.
   * @return
   *     the base name of file name of the file in the specified path, or
   *     {@code null} if the path is {@code null}, or the path is the path
   *    of a directory (i.e., the filename of the path is an empty string).
   */
  @Nullable
  public static String getBasename(@Nullable final String path) {
    if (path == null) {
      return null;
    }
    final String filename = new File(path).getName();
    if (filename.isEmpty()) {
      return null;
    }
    return getBaseNameFromFilename(filename);
  }

  /**
   * Gets the extension (i.e. the part after the last ".") of a file.
   * <p>
   * Will return an empty string if the file name doesn't contain any dots. Only
   * the last segment of the file name is consulted - i.e. all leading
   * directories of the {@code file name} parameter are skipped.
   *
   * @param path
   *     the path of the file to obtain the extension of.
   * @return
   *     the extension of file name of the file in the specified path, or
   *     {@code null} if the path is {@code null}, or the path is the path
   *     of a directory (i.e., the filename of the path is an empty string).
   *     If the file name doesn't contain any dots, an empty string is returned.
   */
  @Nullable
  public static String getExtension(@Nullable final Path path) {
    if (path == null) {
      return null;
    }
    final Path filename = path.getFileName();
    if (filename == null || filename.toString().isEmpty()) {
      return null;
    }
    return getExtensionFromFilename(filename.toString());
  }

  /**
   * Gets the extension (i.e. the part after the last ".") of a file.
   * <p>
   * Will return an empty string if the file name doesn't contain any dots. Only
   * the last segment of the file name is consulted - i.e. all leading
   * directories of the {@code file name} parameter are skipped.
   *
   * @param path
   *     the path of the file to obtain the extension of.
   * @return
   *     the extension of file name of the file in the specified path, or
   *     {@code null} if the path is {@code null}, or the path is the path
   *     of a directory (i.e., the filename of the path is an empty string). If
   *     the file name doesn't contain any dots, an empty string is returned.
   */
  @Nullable
  public static String getExtension(@Nullable final File path) {
    if (path == null) {
      return null;
    }
    final String filename = path.getName();
    if (filename.isEmpty()) {
      return null;
    }
    return getExtensionFromFilename(filename);
  }

  /**
   * Gets the extension (i.e. the part after the last ".") of a file.
   * <p>
   * Will return an empty string if the file name doesn't contain any dots. Only
   * the last segment of the file name is consulted - i.e. all leading
   * directories of the {@code file name} parameter are skipped.
   *
   * @param path
   *     the path of the file to obtain the extension of.
   * @return
   *     the extension of file name of the file in the specified path, or
   *     {@code null} if the path is {@code null}, or the path is the path
   *     of a directory (i.e., the filename of the path is an empty string). If
   *     the file name doesn't contain any dots, an empty string is returned.
   */
  @Nullable
  public static String getExtension(@Nullable final String path) {
    if (path == null) {
      return null;
    }
    final String filename = new File(path).getName();
    if (filename.isEmpty()) {
      return null;
    }
    return getExtensionFromFilename(filename);
  }

  /**
   * Gets the extension (i.e. the part after the last ".") of a file, with a
   * dot "." prefix.
   * <p>
   * Will return an empty string if the file name doesn't contain any dots. Only
   * the last segment of the file name is consulted - i.e. all leading
   * directories of the {@code file name} parameter are skipped.
   *
   * @param path
   *     the path of the file to obtain the extension of.
   * @return
   *     the extension of file name of the file in the specified path with a
   *     dot "." prefix, or {@code null} if the path is {@code null}, or the
   *     path is the path of a directory (i.e., the filename of the path is an
   *     empty string). If the file name doesn't contain any dots, an empty
   *     string is returned.
   */
  @Nullable
  public static String getDotExtension(@Nullable final Path path) {
    final String extension = getExtension(path);
    if (extension == null) {
      return null;
    } else {
      return extension.isEmpty() ? "" : "." + extension;
    }
  }

  /**
   * Gets the extension (i.e. the part after the last ".") of a file, with a
   * dot "." prefix.
   * <p>
   * Will return an empty string if the file name doesn't contain any dots. Only
   * the last segment of the file name is consulted - i.e. all leading
   * directories of the {@code file name} parameter are skipped.
   *
   * @param path
   *     the path of the file to obtain the extension of.
   * @return
   *     the extension of file name of the file in the specified path with a
   *     dot "." prefix, or {@code null} if the path is {@code null}, or the
   *     path is the path of a directory (i.e., the filename of the path is an
   *     empty string). If the file name doesn't contain any dots, an empty
   *     string is returned.
   */
  @Nullable
  public static String getDotExtension(@Nullable final File path) {
    final String extension = getExtension(path);
    if (extension == null) {
      return null;
    } else {
      return extension.isEmpty() ? "" : "." + extension;
    }
  }

  /**
   * Gets the extension (i.e. the part after the last ".") of a file, with a
   * dot "." prefix.
   * <p>
   * Will return an empty string if the file name doesn't contain any dots. Only
   * the last segment of the file name is consulted - i.e. all leading
   * directories of the {@code file name} parameter are skipped.
   *
   * @param path
   *     the path of the file to obtain the extension of.
   * @return
   *     the extension of file name of the file in the specified path with a
   *     dot "." prefix, or {@code null} if the path is {@code null}, or the
   *     path is the path of a directory (i.e., the filename of the path is an
   *     empty string). If the file name doesn't contain any dots, an empty
   *     string is returned.
   */
  @Nullable
  public static String getDotExtension(@Nullable final String path) {
    final String extension = getExtension(path);
    if (extension == null) {
      return null;
    } else {
      return extension.isEmpty() ? "" : "." + extension;
    }
  }

  /**
   * Gets the filename from the path of a file.
   *
   * @param file
   *     the path of the file.
   * @return the filename extracted from the path.
   */
  public static String getFilename(final File file) {
    return getFilenameFromPath(file.getPath());
  }

  /**
   * Gets the filename from the path of a file.
   *
   * @param path
   *     the path of the file.
   * @return the filename extracted from the path.
   */
  public static String getFilename(final Path path) {
    final Path filename = path.getFileName();
    if (filename == null) {
      return "";
    } else {
      return filename.toString();
    }
  }

  /**
   * Gets the filename from the URL of a file.
   *
   * @param url
   *     the URL of the file.
   * @return the filename extracted from the path.
   */
  public static String getFilename(final URL url) {
    return getFilenameFromUrl(url.toString());
  }

  /**
   * Gets the filename from the URL of a file.
   *
   * @param uri
   *     the URI of the file.
   * @return the filename extracted from the path.
   */
  public static String getFilename(final URI uri) {
    return getFilenameFromUrl(uri.toString());
  }

  /**
   * Gets the filename from the URL of a file.
   *
   * @param url
   *     the URL of the file.
   * @return the filename extracted from the path.
   */
  public static String getFilename(final Url url) {
    return getFilenameFromUrl(url.toString());
  }

  /**
   * Gets the filename from a path.
   *
   * @param path
   *     the path.
   * @return the filename extracted from the path.
   */
  public static String getFilenameFromPath(final String path) {
    final int index = path.lastIndexOf(File.separatorChar);
    if (index < 0) {
      return path;
    } else {
      return path.substring(index + 1);
    }
  }

  /**
   * Gets the filename from a URL.
   *
   * @param url
   *     the URL.
   * @return the filename extracted from the URL.
   */
  public static String getFilenameFromUrl(final String url) {
    final int index = url.lastIndexOf('/');
    if (index < 0) {
      return url;
    } else {
      return url.substring(index + 1);
    }
  }

  /**
   * Gets the filename from a classpath.
   *
   * @param classpath
   *     the classpath.
   * @return the filename extracted from the classpath.
   */
  public static String getFilenameFromClasspath(final String classpath) {
    final int index = classpath.lastIndexOf('/');
    if (index < 0) {
      return classpath;
    } else {
      return classpath.substring(index + 1);
    }
  }
}
