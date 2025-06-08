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
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

import javax.annotation.Nullable;

import ltd.qubit.commons.net.Url;

/**
 * 提供操作文件名的实用方法。
 *
 * @author 胡海星
 */
public class FilenameUtils {

  /**
   * 获取文件名的基本名称（即直到但不包括最后一个"."的部分）。
   * <p>
   * 如果文件名不包含任何点，将返回文件名本身。
   *
   * @param filename
   *     文件名。
   * @return
   *     文件名的基本名称，如果文件名为 {@code null}，则返回 {@code null}。
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
   * 获取文件名的扩展名（即最后一个"."之后的部分）。
   * <p>
   * 如果文件名不包含任何点，将返回空字符串。
   *
   * @param filename
   *     文件名。
   * @return
   *     文件名的扩展名，如果文件名为 {@code null}，则返回 {@code null}。
   *     如果文件名不包含任何点，则返回空字符串。
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
   * 获取文件名最后路径段的基本名称（即直到但不包括最后一个"."的部分）。
   * <p>
   * 如果文件名不包含任何点，将返回文件名本身。
   * 跳过 {@code file name} 参数的所有前导目录。
   *
   * @param path
   *     要获取基本名称的文件路径。
   * @return
   *     指定路径中文件名的基本名称，如果路径为 {@code null}，
   *     或路径是目录的路径（即路径的文件名是空字符串），则返回 {@code null}。
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
   * 获取文件名最后路径段的基本名称（即直到但不包括最后一个"."的部分）。
   * <p>
   * 如果文件名不包含任何点，将返回文件名本身。
   * 跳过 {@code file name} 参数的所有前导目录。
   *
   * @param path
   *     要获取基本名称的文件路径。
   * @return
   *     指定路径中文件名的基本名称，如果路径为 {@code null}，
   *     或路径是目录的路径（即路径的文件名是空字符串），则返回 {@code null}。
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
   * 获取文件名最后路径段的基本名称（即直到但不包括最后一个"."的部分）。
   * <p>
   * 如果文件名不包含任何点，将返回文件名本身。
   * 跳过 {@code file name} 参数的所有前导目录。
   *
   * @param path
   *     要获取基本名称的文件路径。
   * @return
   *     指定路径中文件名的基本名称，如果路径为 {@code null}，
   *     或路径是目录的路径（即路径的文件名是空字符串），则返回 {@code null}。
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
   * 获取文件的扩展名（即最后一个"."之后的部分）。
   * <p>
   * 如果文件名不包含任何点，将返回空字符串。
   * 只检查文件名的最后一段 - 即跳过 {@code file name} 参数的所有前导目录。
   *
   * @param path
   *     要获取扩展名的文件路径。
   * @return
   *     指定路径中文件名的扩展名，如果路径为 {@code null}，
   *     或路径是目录的路径（即路径的文件名是空字符串），则返回 {@code null}。
   *     如果文件名不包含任何点，则返回空字符串。
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
   * 获取文件的扩展名（即最后一个"."之后的部分）。
   * <p>
   * 如果文件名不包含任何点，将返回空字符串。
   * 只检查文件名的最后一段 - 即跳过 {@code file name} 参数的所有前导目录。
   *
   * @param path
   *     要获取扩展名的文件路径。
   * @return
   *     指定路径中文件名的扩展名，如果路径为 {@code null}，
   *     或路径是目录的路径（即路径的文件名是空字符串），则返回 {@code null}。
   *     如果文件名不包含任何点，则返回空字符串。
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
   * 获取文件的扩展名（即最后一个"."之后的部分）。
   * <p>
   * 如果文件名不包含任何点，将返回空字符串。
   * 只检查文件名的最后一段 - 即跳过 {@code file name} 参数的所有前导目录。
   *
   * @param path
   *     要获取扩展名的文件路径。
   * @return
   *     指定路径中文件名的扩展名，如果路径为 {@code null}，
   *     或路径是目录的路径（即路径的文件名是空字符串），则返回 {@code null}。
   *     如果文件名不包含任何点，则返回空字符串。
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
   * 获取文件的扩展名（即最后一个"."之后的部分），带有一个点 "." 前缀。
   * <p>
   * 如果文件名不包含任何点，将返回空字符串。
   * 只检查文件名的最后一段 - 即跳过 {@code file name} 参数的所有前导目录。
   *
   * @param path
   *     要获取扩展名的文件路径。
   * @return
   *     指定路径中文件名的扩展名，带有一个点 "." 前缀，如果路径为 {@code null}，
   *     或路径是目录的路径（即路径的文件名是空字符串），则返回 {@code null}。
   *     如果文件名不包含任何点，则返回空字符串。
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
   * 获取文件的扩展名（即最后一个"."之后的部分），带有一个点 "." 前缀。
   * <p>
   * 如果文件名不包含任何点，将返回空字符串。
   * 只检查文件名的最后一段 - 即跳过 {@code file name} 参数的所有前导目录。
   *
   * @param path
   *     要获取扩展名的文件路径。
   * @return
   *     指定路径中文件名的扩展名，带有一个点 "." 前缀，如果路径为 {@code null}，
   *     或路径是目录的路径（即路径的文件名是空字符串），则返回 {@code null}。
   *     如果文件名不包含任何点，则返回空字符串。
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
   * 获取文件的扩展名（即最后一个"."之后的部分），带有一个点 "." 前缀。
   * <p>
   * 如果文件名不包含任何点，将返回空字符串。
   * 只检查文件名的最后一段 - 即跳过 {@code file name} 参数的所有前导目录。
   *
   * @param path
   *     要获取扩展名的文件路径。
   * @return
   *     指定路径中文件名的扩展名，带有一个点 "." 前缀，如果路径为 {@code null}，
   *     或路径是目录的路径（即路径的文件名是空字符串），则返回 {@code null}。
   *     如果文件名不包含任何点，则返回空字符串。
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
   * 获取文件名从文件路径。
   *
   * @param file
   *     文件路径。
   * @return 从路径提取的文件名。
   */
  public static String getFilename(final File file) {
    return getFilenameFromPath(file.getPath());
  }

  /**
   * 获取文件名从文件路径。
   *
   * @param path
   *     文件路径。
   * @return 从路径提取的文件名。
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
   * 获取文件名从文件的URL。
   *
   * @param url
   *     文件的URL。
   * @return 从路径提取的文件名。
   */
  public static String getFilename(final URL url) {
    return getFilenameFromUrl(url.toString());
  }

  /**
   * 获取文件名从文件的URL。
   *
   * @param uri
   *     文件的URI。
   * @return 从路径提取的文件名。
   */
  public static String getFilename(final URI uri) {
    return getFilenameFromUrl(uri.toString());
  }

  /**
   * 获取文件名从文件的URL。
   *
   * @param url
   *     文件的URL。
   * @return 从路径提取的文件名。
   */
  public static String getFilename(final Url url) {
    return getFilenameFromUrl(url.toString());
  }

  /**
   * 获取文件名从路径。
   *
   * @param path
   *     路径。
   * @return 从路径提取的文件名。
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
   * 获取文件名从URL。
   *
   * @param url
   *     网址。
   * @return 从URL提取的文件名。
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
   * 获取文件名从类路径。
   *
   * @param classpath
   *     类路径。
   * @return 从类路径提取的文件名。
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
