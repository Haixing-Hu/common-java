////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;

import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.io.error.DirectoryCannotCreateException;
import ltd.qubit.commons.io.error.DirectoryCannotListException;
import ltd.qubit.commons.io.error.DirectoryCannotWriteException;
import ltd.qubit.commons.io.error.DirectoryNotExistException;
import ltd.qubit.commons.io.error.FileAlreadyExistException;
import ltd.qubit.commons.io.error.FileCannotDeleteException;
import ltd.qubit.commons.io.error.FileCannotReadException;
import ltd.qubit.commons.io.error.FileCannotWriteException;
import ltd.qubit.commons.io.error.FileIsDirectoryException;
import ltd.qubit.commons.io.error.FileIsNotDirectoryException;
import ltd.qubit.commons.io.error.FileNotExistException;
import ltd.qubit.commons.lang.SystemUtils;
import ltd.qubit.commons.util.filter.file.DirectoryFileFilter;
import ltd.qubit.commons.util.filter.file.RegularFileFilter;

import static ltd.qubit.commons.io.OperationOption.MAKE_DIRS;
import static ltd.qubit.commons.io.OperationOption.OVERWRITE;
import static ltd.qubit.commons.io.OperationOption.PRESERVE_DATE;
import static ltd.qubit.commons.lang.ObjectUtils.defaultIfNull;
import static ltd.qubit.commons.lang.StringUtils.EMPTY;
import static ltd.qubit.commons.lang.StringUtils.concat;
import static ltd.qubit.commons.lang.StringUtils.isEmpty;

/**
 * 此类提供常见的文件系统操作。
 *
 * @author 胡海星
 */
@ThreadSafe
public final class FileUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

  @SuppressWarnings("SimpleDateFormat")
  @GuardedBy(value = "itself")
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");

  @GuardedBy(value = "itself")
  private static final Random RANDOM = new Random();

  public static final String DEFAULT_TEMP_FILE_PREFIX = "temp_";

  public static final int DEFAULT_TEMP_FILE_RETRIES = 10;

  /**
   * 生成一个带有指定前缀的随机文件名。
   *
   * @param prefix
   *     临时文件名的前缀。
   * @return 带有指定前缀的随机文件名。
   */
  public static String getRandomFileName(@Nullable final String prefix) {
    return getRandomFileName(prefix, null);
  }

  /**
   * 生成一个带有指定前缀的随机文件名。
   *
   * @param prefix
   *     临时文件名的前缀。
   * @param suffix
   *     临时文件名的后缀。
   * @return 带有指定前缀的随机文件名。
   */
  public static String getRandomFileName(@Nullable final String prefix,
      @Nullable final String suffix) {
    final Date now = new Date(System.currentTimeMillis());
    final String dateStr;
    final String randomStr;
    synchronized (DATE_FORMAT) {
      dateStr = DATE_FORMAT.format(now);
      randomStr = String.valueOf(Math.abs(RANDOM.nextInt()));
    }
    if (isEmpty(prefix)) {
      return dateStr + '_' + randomStr + defaultIfNull(suffix, EMPTY);
    } else {
      return prefix + '_' + dateStr + '_' + randomStr + defaultIfNull(suffix, EMPTY);
    }
  }

  /**
   * 获取系统临时目录的路径。
   *
   * @return
   *     系统临时目录的路径。
   * @throws IOException
   *     如果发生任何错误。
   */
  public static File getTempDirectory() throws IOException {
    final String path = SystemUtils.JAVA_IO_TMPDIR;
    if (path == null) {
      throw new IOException("The system property 'java.io.tmpdir' is not defined.");
    }
    return new File(path);
  }

  /**
   * 获取带有指定前缀的临时文件或目录路径名。
   *
   * @param prefix
   *     临时文件名的前缀。
   * @return 系统默认临时目录中的文件或目录的抽象路径名。
   */
  public static String getTempFilePath(@Nullable final String prefix) {
    return getTempFilePath(prefix, null);
  }

  /**
   * 获取带有指定前缀的临时文件或目录路径名。
   *
   * @param prefix
   *     临时文件名的前缀。
   * @param suffix
   *     临时文件名的后缀。
   * @return 系统默认临时目录中的文件或目录的抽象路径名。
   */
  public static String getTempFilePath(@Nullable final String prefix,
      @Nullable final String suffix) {
    final String tempDirPath = SystemUtils.JAVA_IO_TMPDIR;
    final String tempFileName = getRandomFileName(prefix, suffix);
    return tempDirPath + File.separatorChar + tempFileName;
  }

  /**
   * 获取带有默认前缀的临时文件或目录路径名。
   *
   * @return 系统默认临时目录中的文件或目录的抽象路径名。
   */
  public static File getTempFile() {
    return getTempFile(DEFAULT_TEMP_FILE_PREFIX, null);
  }

  /**
   * 获取带有指定前缀的临时文件或目录路径名。
   *
   * @param prefix
   *     临时文件名的前缀。
   * @return 系统默认临时目录中的文件或目录的抽象路径名。
   */
  public static File getTempFile(@Nullable final String prefix) {
    return getTempFile(prefix, null);
  }

  /**
   * 获取带有指定前缀的临时文件或目录路径名。
   *
   * @param prefix
   *     临时文件名的前缀。
   * @param suffix
   *     临时文件名的后缀。
   * @return 系统默认临时目录中的文件或目录的抽象路径名。
   */
  public static File getTempFile(@Nullable final String prefix,
      @Nullable final String suffix) {
    final String tempDirPath = SystemUtils.JAVA_IO_TMPDIR;
    final String tempFileName = getRandomFileName(prefix, suffix);
    return new File(tempDirPath, tempFileName);
  }

  /**
   * 在系统默认临时目录中创建一个新的空临时文件。
   *
   * @return 具有默认前缀的系统默认临时目录中的新临时文件。
   * @throws IOException
   *     如果发生任何错误。
   */
  public static File createTempFile() throws IOException {
    return createTempFile(DEFAULT_TEMP_FILE_PREFIX, null, DEFAULT_TEMP_FILE_RETRIES);
  }

  /**
   * 在系统默认临时目录中创建一个新的空临时文件。
   *
   * @param prefix
   *     临时文件名的前缀。
   * @return 具有指定前缀的系统默认临时目录中的新临时文件。
   * @throws IOException
   *     如果发生任何错误。
   */
  public static File createTempFile(@Nullable final String prefix) throws IOException {
    return createTempFile(prefix, null, DEFAULT_TEMP_FILE_RETRIES);
  }

  /**
   * 在系统默认临时目录中创建一个新的空临时文件。
   *
   * @param prefix
   *     临时文件名的前缀。
   * @param suffix
   *     临时文件名的后缀。
   * @return 具有指定前缀的系统默认临时目录中的新临时文件。
   * @throws IOException
   *     如果发生任何错误。
   */
  public static File createTempFile(@Nullable final String prefix, @Nullable final String suffix)
      throws IOException {
    return createTempFile(prefix, suffix, DEFAULT_TEMP_FILE_RETRIES);
  }

  /**
   * 在系统默认临时目录中创建一个新的空临时文件。
   *
   * @param prefix
   *     临时文件名的前缀。
   * @param maxTries
   *     此函数执行的最大重试次数。
   * @return 具有指定前缀的系统默认临时目录中的新临时文件。
   * @throws IOException
   *     如果发生任何错误。
   */
  public static File createTempFile(@Nullable final String prefix, final int maxTries)
      throws IOException {
    return createTempFile(prefix, null, maxTries);
  }

  /**
   * 在系统默认临时目录中创建一个新的空临时文件。
   *
   * @param prefix
   *     临时文件名的前缀。
   * @param suffix
   *     临时文件名的后缀。
   * @param maxTries
   *     此函数执行的最大重试次数。
   * @return 具有指定前缀和后缀的系统默认临时目录中的新临时文件。
   * @throws IOException
   *     如果发生任何错误。
   */
  public static File createTempFile(@Nullable final String prefix,
      @Nullable final String suffix, final int maxTries) throws IOException {
    File tempFile = getTempFile(prefix, suffix);
    int tries = 0;
    while (!tempFile.createNewFile()) {
      ++tries;
      LOGGER.warn("Failed to create the temporary file, try again: {}", tempFile);
      if (tries >= maxTries) {
        throw new IOException("Failed to create the temporary file after "
            + maxTries + " tries.");
      }
      tempFile = getTempFile(prefix, suffix);
    }
    return tempFile;
  }

  /**
   * 确保指定文件的父目录存在。
   * <p>
   * 也就是说，如果指定文件的父目录不存在，该函数将创建它。
   *
   * @param dir
   *     要创建的目录。
   * @return 创建的隐藏目录的File对象。
   * @throws IOException
   *     如果发生任何错误。
   */
  public static File createTempDir(final String prefix, final int maxTries)
      throws IOException {
    final String tempDirPath = SystemUtils.JAVA_IO_TMPDIR;
    String tempFileName = getRandomFileName(prefix);
    File tempDir = new File(tempDirPath, tempFileName);
    int tries = 0;
    while (!tempDir.mkdirs()) {
      ++tries;
      LOGGER.warn("Failed to create the temporary file, try again: {}",
          tempDir);
      if (tries >= maxTries) {
        throw new IOException("Failed to create the temporary file after "
            + "too many tries: " + maxTries);
      }
      tempFileName = getRandomFileName(prefix);
      tempDir = new File(tempDirPath, tempFileName);
    }
    return tempDir;
  }

  /**
   * 获取目录的大小。
   *
   * @param dir
   *     给定的目录。
   * @return
   *     给定目录的大小，以字节为单位。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  public static long getSizeOfDirectory(final File dir) throws IOException {
    if (!dir.exists()) {
      throw new DirectoryNotExistException(dir);
    }
    if (!dir.isDirectory()) {
      throw new FileIsNotDirectoryException(dir);
    }
    long result = 0;
    final File[] files = dir.listFiles();
    if (files == null) { // null if security restricted
      throw new DirectoryCannotListException(dir);
    }
    for (final File file : files) {
      if (file.isDirectory()) {
        result += getSizeOfDirectory(file);
      } else {
        result += file.length();
      }
    }
    return result;
  }

  /**
   * 确保给定的目录存在。
   *
   * @param dir
   *     给定的目录。
   * @throws IOException
   *     如果无法创建给定的目录。
   */
  public static void ensureDirectoryExist(final File dir) throws IOException {
    if (!dir.exists()) {
      if (!dir.mkdirs()) {
        throw new DirectoryCannotCreateException(dir);
      }
    } else if (!dir.isDirectory()) {
      throw new FileIsNotDirectoryException(dir);
    }
  }

  /**
   * 确保指定文件的父目录存在。
   *
   * <p>也就是说，如果指定文件的父目录不存在，该函数将创建它。
   *
   * @param file
   *     指定的文件。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  public static void ensureParentExist(final File file) throws IOException {
    if (!file.exists()) {
      final File parent = file.getParentFile();
      if ((parent != null) && (!parent.exists())) {
        if (!parent.mkdirs()) {
          throw new DirectoryCannotCreateException(parent);
        }
      }
    }
  }

  /**
   * 实现与 Unix 上的 "touch" 实用程序相同的行为。它创建一个大小为 0 的新文件，
   * 或者，如果文件已经存在，则在不修改文件的情况下打开和关闭文件，但更新文件的日期和时间。
   *
   * <p>注意：如果无法设置文件的最后修改日期，此方法会抛出 IOException。
   * 此外，如果父目录不存在，此方法会创建父目录。
   *
   * @param file
   *     要触摸的文件
   * @throws IOException
   *     如果发生 I/O 问题
   */
  public static void touch(final File file) throws IOException {
    if (!file.exists()) {
      final File parent = file.getParentFile();
      if ((parent != null) && (!parent.exists())) {
        if (!parent.mkdirs()) {
          throw new DirectoryCannotCreateException(parent);
        }

      }
      // atomically creates a new, empty file
      file.createNewFile();
    }
    final boolean success = file.setLastModified(System.currentTimeMillis());
    if (!success) {
      throw new IOException("Setting the file last modify time failed: " + file);
    }
  }

  /**
   * 创建一个新的隐藏目录。在 Unix 上名称可能会更改。
   *
   * <p>如果目录已经存在，则返回该目录而不创建新目录。
   *
   * @param dir
   *     要创建的目录。
   * @return 创建的隐藏目录的 File 对象。
   * @throws IOException
   *     如果发生任何错误。
   */
  public static File createHiddenDirectory(final File dir) throws IOException {
    if (SystemUtils.IS_OS_WINDOWS) {
      ensureDirectoryExist(dir);
      // set attribute via external process
      final String pathname = dir.getAbsolutePath();
      final String[] command = {"attrib", "+h", pathname};
      final Runtime runtime = Runtime.getRuntime();
      final Process process;
      try {
        // Execute command
        process = runtime.exec(command);
        // wait for process to finish
        process.waitFor();
      } catch (final InterruptedException | IOException e) {
        throw new DirectoryCannotCreateException(pathname, e);
      }
      return dir;
    } else if (SystemUtils.IS_OS_UNIX) {
      // usually unix like: use . as prefix
      final String name = dir.getName();
      if (name.isEmpty()) {
        throw new IllegalArgumentException("Invalid directory name. ");
      }
      File folder = dir;
      if (name.charAt(0) != '.') {
        final String parent = folder.getParent() + File.separatorChar;
        folder = new File(parent, '.' + name);
      }
      // create folder if it does not exist
      ensureDirectoryExist(folder);
      return folder;
    } else {
      throw new UnsupportedOperationException("The function is not supported "
          + "for the operation system: " + SystemUtils.OS_NAME);
    }
  }

  /**
   * 获取表示指定路径目录的 File 对象。如果路径不存在，则创建该路径的目录；
   * 否则，如果路径存在但不是目录，则抛出异常。
   *
   * @param path
   *     目录的路径。
   * @return
   *     表示指定路径目录的 {@link File} 对象。
   * @throws IOException
   *     如果发生任何错误。
   */
  public static File getOrCreateDirectory(final String path) throws IOException {
    final File result = new File(path);
    if (result.exists()) {
      if (result.isDirectory()) {
        return result;
      } else {
        throw new FileIsNotDirectoryException(path);
      }
    } else {
      if (result.mkdirs() && result.exists()) {
        return result;
      } else {
        throw new DirectoryCannotCreateException(path);
      }
    }
  }

  /**
   * 获取表示指定路径目录的 File 对象。如果路径不存在，则创建该路径的目录；
   * 否则，如果路径存在但不是目录，则返回 null。
   *
   * @param path
   *     目录的路径。
   * @return
   *     表示指定路径目录的 {@link File} 对象，如果创建指定目录失败则返回 {@code null}。
   */
  @Nullable
  public static File getOrCreateDirectoryNoThrow(final String path) {
    final File result = new File(path);
    if (result.exists()) {
      if (result.isDirectory()) {
        return result;
      } else {
        return null;
      }
    } else {
      if (result.mkdirs() && result.exists()) {
        return result;
      } else {
        return null;
      }
    }
  }

  /**
   * 比较两个文件的二进制内容以确定它们是否相等。
   *
   * <p>此方法在进行逐字节内容比较之前，会检查两个文件的长度是否不同或
   * 它们是否指向同一个文件。
   *
   * @param file1
   *     第一个文件
   * @param file2
   *     第二个文件
   * @return 如果文件的二进制内容相等或两个文件都不存在，则返回 true，否则返回 false
   * @throws IOException
   *     如果发生 I/O 错误
   */
  public static boolean contentEquals(final File file1, final File file2)
      throws IOException {
    if (!file1.exists()) {
      // two not existing files are equal
      return !file2.exists();
    } else if (!file2.exists()) {
      return false;
    }
    if (file1.isDirectory() || file2.isDirectory()) {
      // don't want to compare directory contents
      throw new IOException("Can't compare directories. ");
    }
    if (file1.length() != file2.length()) {
      // lengths differ, cannot be equal
      return false;
    }
    if (file1.getCanonicalFile().equals(file2.getCanonicalFile())) {
      // same file
      return true;
    }
    InputStream input1 = null;
    InputStream input2 = null;
    try {
      input1 = new FileInputStream(file1);
      input2 = new FileInputStream(file2);
      return IoUtils.compareContent(input1, input2) == 0;
    } finally {
      IoUtils.closeQuietly(input1);
      IoUtils.closeQuietly(input2);
    }
  }

  /**
   * 按字典序比较两个文件的二进制内容。
   *
   * <p>此方法在进行逐字节内容比较之前，会检查两个文件的长度是否不同或
   * 它们是否指向同一个文件。
   *
   * @param file1
   *     第一个文件
   * @param file2
   *     第二个文件
   * @return 如果第一个文件的二进制内容按字典序小于、等于或大于第二个文件的二进制内容，
   *     则返回小于、等于或大于 0 的整数。
   * @throws IOException
   *     如果发生 I/O 错误
   */
  public static int compareContent(final File file1, final File file2)
      throws IOException {
    if (!file1.exists()) {
      if (!file2.exists()) {
        // two not existing files are equal
        return 0;
      } else {
        // existing file is lexicographically larger than the non-existing file
        return -1;
      }
    } else if (!file2.exists()) {
      // existing file is lexicographically larger than the non-existing file
      return +1;
    }
    // both file1 and file2 exists
    if (file1.isDirectory() || file2.isDirectory()) {
      // don't want to compare directory contents
      throw new IOException("Can't compare directories. ");
    }
    if (file1.getCanonicalFile().equals(file2.getCanonicalFile())) {
      return 0; // same file
    }
    InputStream input1 = null;
    InputStream input2 = null;
    try {
      input1 = new FileInputStream(file1);
      input2 = new FileInputStream(file2);
      return IoUtils.compareContent(input1, input2);
    } finally {
      IoUtils.closeQuietly(input1);
      IoUtils.closeQuietly(input2);
    }
  }

  /**
   * 将文件复制到新位置。
   *
   * <p>此方法将指定源文件的内容复制到指定目标文件。如果目标文件所在的目录不存在，
   * 则会创建该目录。
   *
   * <p>TODO: 添加对进度显示回调函数的支持。
   *
   * @param srcFile
   *     要复制的现有文件。
   * @param destFile
   *     目标文件。
   * @param options
   *     {@link OperationOption} 类中定义的常量的按位组合。
   * @throws FileNotExistException
   *     如果 {@code srcFile} 不存在。
   * @throws FileAlreadyExistException
   *     如果 {@code destFile} 已经存在，且未提供 {@link OperationOption#OVERWRITE} 选项。
   * @throws FileIsDirectoryException
   *     如果 {@code srcFile} 是目录。
   * @throws FileCannotWriteException
   *     如果 {@code srcFile} 无法写入。
   * @throws DirectoryNotExistException
   *     如果 {@code destFile} 的父目录不存在，且未提供 {@link OperationOption#MAKE_DIRS} 选项。
   * @throws DirectoryCannotCreateException
   *     如果 {@code destFile} 的父目录不存在且无法创建。
   * @throws IOException
   *     如果复制过程中发生任何其他 IO 错误。
   * @see OperationOption
   */
  public static void copyFile(final File srcFile, final File destFile,
      final int options) throws IOException {
    if (!srcFile.exists()) {
      throw new FileNotExistException(srcFile);
    }
    if (srcFile.isDirectory()) {
      throw new FileIsDirectoryException(srcFile);
    }
    if (destFile.exists()) {
      if ((options & OVERWRITE) == 0) {
        throw new FileAlreadyExistException(destFile);
      }
      if (!destFile.canWrite()) {
        throw new FileCannotWriteException(destFile);
      }
      if (destFile.isDirectory()) {
        throw new FileIsDirectoryException(destFile);
      }
    } else { // destination file does not exist
      // create the parent directories of the destination file if necessary
      final File destParent = destFile.getParentFile();
      if ((destParent != null) && (!destParent.exists())) {
        if ((options & MAKE_DIRS) == 0) {
          // do not create the parent directories
          throw new DirectoryNotExistException(destParent);
        }
        if (!destParent.mkdirs()) {
          throw new DirectoryCannotCreateException(destParent);
        }
      }
    }
    if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath())) {
      throw new IOException("The source and destination are the same file or directory. ");
    }
    // now perform the copying
    final boolean preserveDate = ((options & PRESERVE_DATE) != 0);
    doCopyFile(srcFile, destFile, preserveDate);
  }

  private static void doCopyFile(final File srcFile, final File destFile,
      final boolean preserveDate) throws IOException {
    FileInputStream input = null;
    FileOutputStream output = null;
    try {
      LOGGER.debug("Copying files from {} to {} ...", srcFile, destFile);
      input = new FileInputStream(srcFile);
      output = new FileOutputStream(destFile);
      IoUtils.copy(input, Long.MAX_VALUE, output);
    } finally {
      IoUtils.closeQuietly(output);
      IoUtils.closeQuietly(input);
    }
    if (srcFile.length() != destFile.length()) {
      throw new IOException("Copying file failed. ");
    }
    if (preserveDate) {
      // preserve the source file date
      destFile.setLastModified(srcFile.lastModified());
    }
  }

  /**
   * 将文件复制到目录，可选择保留文件日期。
   *
   * <p>此方法将指定源文件的内容复制到指定目标目录中的同名文件。
   * 如果目标目录不存在，则会创建该目录。如果目标文件存在，则此方法会覆盖它。
   *
   * @param srcFile
   *     要复制的现有文件，不得为 {@code null}
   * @param destDir
   *     放置副本的目录，不得为 {@code null}
   * @param options
   *     {@link OperationOption} 类中定义的常量的按位组合。
   * @throws FileNotExistException
   *     如果 {@code srcFile} 不存在。
   * @throws FileAlreadyExistException
   *     如果 {@code destFile} 已经存在，且未提供 {@link OperationOption#OVERWRITE} 选项。
   * @throws FileIsDirectoryException
   *     如果 {@code srcFile} 是目录。
   * @throws FileCannotWriteException
   *     如果 {@code srcFile} 无法写入。
   * @throws DirectoryNotExistException
   *     如果 {@code destFile} 的父目录不存在，且未提供 {@link OperationOption#MAKE_DIRS} 选项。
   * @throws DirectoryCannotCreateException
   *     如果 {@code destFile} 的父目录不存在且无法创建。
   * @throws IOException
   *     如果复制过程中发生任何其他 IO 错误。
   * @see OperationOption
   */
  public static void copyFileToDirectory(final File srcFile,
      final File destDir, final int options) throws IOException {
    if (destDir.exists()) {
      if (!destDir.isDirectory()) {
        throw new FileIsNotDirectoryException(destDir);
      }
    }
    final File destFile = new File(destDir, srcFile.getName());
    copyFile(srcFile, destFile, options);
  }

  /**
   * 将过滤后的目录复制到新位置。
   *
   * <p>此方法将指定源目录的内容复制到指定目标目录中。
   *
   * <p>如果目标目录不存在，则会创建该目录。如果目标目录已存在，
   * 则此方法会将源目录与目标目录合并，源目录优先。
   *
   * <p>示例：仅复制目录
   * <pre>
   * // 仅复制目录结构
   * FileUtils.copyDirectory(srcDir, destDir, DirectoryFileFilter.DIRECTORY, false);
   * </pre>
   *
   * <p>示例：复制目录和txt文件
   * <pre>
   * // 为 ".txt" 文件创建过滤器
   * IOFileFilter txtSuffixFilter = FileFilterUtils.suffixFileFilter(".txt");
   * IOFileFilter txtFiles = FileFilterUtils.andFileFilter(FileFileFilter.FILE,
   *     txtSuffixFilter);
   * // 为目录或 ".txt" 文件创建过滤器
   * FileFilter filter = FileFilterUtils.orFileFilter(DirectoryFileFilter.DIRECTORY,
   *     txtFiles);
   * // 使用过滤器进行复制
   * FileUtils.copyDirectory(srcDir, destDir, filter, false);
   * </pre>
   *
   * @param srcDir
   *     要复制的现有目录，不得为 {@code null}
   * @param destDir
   *     新目录，不得为 {@code null}
   * @param filter
   *     要应用的过滤器，null 表示复制所有目录和文件
   * @param options
   *     {@link OperationOption} 类中定义的常量的按位组合。
   * @return 复制的文件或目录数量。
   * @throws FileNotExistException
   *     如果 srcDir 不存在。
   * @throws IOException
   *     如果复制过程中发生 IO 错误
   * @see OperationOption
   */
  public static int copyDirectory(final File srcDir, final File destDir,
      final FileFilter filter, final int options) throws IOException {
    if (!srcDir.exists()) {
      throw new DirectoryNotExistException(srcDir);
    }
    if (!srcDir.isDirectory()) {
      throw new FileIsNotDirectoryException(srcDir);
    }
    if (destDir.exists()) {
      if (!destDir.canWrite()) {
        throw new DirectoryCannotWriteException(destDir);
      }
      if (!destDir.isDirectory()) {
        throw new FileIsNotDirectoryException(destDir);
      }
    } else { // destination file does not exist
      // create the parent directories of the destination file if necessary
      if ((options & MAKE_DIRS) == 0) {
        return 0; // do not create the directories
      }
      if (!destDir.mkdirs()) {
        throw new DirectoryCannotCreateException(destDir);
      }
      if (!destDir.canWrite()) {
        throw new DirectoryCannotWriteException(destDir);
      }
    }
    final String srcDirCanon = srcDir.getCanonicalPath();
    final String destDirCanon = destDir.getCanonicalPath();
    final HashSet<String> copied = new HashSet<>();
    final boolean overwrite = ((options & OVERWRITE) != 0);
    final boolean preserveDate = ((options & PRESERVE_DATE) != 0);
    doCopyDirectory(srcDir, srcDirCanon, destDir, destDirCanon, filter,
        overwrite, preserveDate, copied);
    return copied.size();
  }

  private static int doCopyDirectory(final File srcDir,
      final String srcDirCanon, final File destDir, final String destDirCanon,
      final FileFilter filter, final boolean overwrite,
      final boolean preserveDate, final HashSet<String> copied)
      throws IOException {
    assert (srcDir.isDirectory()
        && destDir.isDirectory()
        && destDir.canWrite());
    if (srcDirCanon.equals(destDirCanon)) {
      // skip the exclusion paths, in order to avoid the circular copying.
      return 0;
    }
    LOGGER.debug("Copying directories from {} to {} ...", srcDir, destDir);
    // note that File.listFiles(FileFilter) method could accept a null argument.
    final File[] files = srcDir.listFiles(filter);
    if (files == null) { // null if security restricted
      throw new DirectoryCannotListException(srcDir);
    }
    int result = 0;
    for (final File srcFile : files) {
      final String srcFileName = srcFile.getName();
      final String newSrcCanon = srcDirCanon + File.separatorChar + srcFileName;
      final String newDestCanon = destDirCanon
          + File.separatorChar
          + srcFileName;
      if (copied.contains(newSrcCanon) || copied.contains(newDestCanon)) {
        continue; // avoid the circular copying.
      }
      if (srcFile.isDirectory()) {
        final File destSubDir = new File(destDir, srcFileName);
        if (destSubDir.exists()) {
          if (!destSubDir.isDirectory()) {
            throw new FileIsNotDirectoryException(destSubDir);
          }
        } else {
          if (!destSubDir.mkdir()) {
            throw new DirectoryCannotCreateException(destSubDir.getPath());
          }
          if (preserveDate) {
            destSubDir.setLastModified(srcFile.lastModified());
          }
        }
        if (!destSubDir.canWrite()) {
          throw new DirectoryCannotWriteException(destSubDir.getPath());
        }
        // recursively copy the sub-directory
        doCopyDirectory(srcFile, newSrcCanon, destSubDir, newDestCanon, filter,
            overwrite, preserveDate, copied);

      } else if (srcFile.isFile()) {
        // note that only copy the normal files
        final File destFile = new File(destDir, srcFileName);
        if (destFile.exists()) {
          if (!overwrite) {
            LOGGER.debug("Skip the file: {}", destFile);
            continue;
          }
          if (destFile.isDirectory()) {
            throw new FileIsNotDirectoryException(destFile);
          }
        }
        // now copy the file
        doCopyFile(srcFile, destFile, preserveDate);
        ++result;
        // add the canonical path of destFile to the copied set.
        copied.add(newDestCanon);
      }
    }
    // add the canonical path of destDir to the copied set.
    copied.add(destDirCanon);
    return result;
  }

  /**
   * 删除文件。如果文件是目录，则删除它及其所有子目录。
   *
   * <p>File.delete() 和此方法的区别是：
   * <ul>
   * <li>要删除的目录不需要为空。</li>
   * <li>当文件或目录无法删除时，您会收到异常。
   * （java.io.File 方法返回布尔值）</li>
   * </ul>
   *
   * @param file
   *     要删除的文件或目录。注意，如果文件不存在，函数不执行任何操作。
   * @throws IOException
   *     如果删除不成功
   */
  public static void forceDelete(final File file) throws IOException {
    if (!file.exists()) {
      return;
    }
    if (file.isDirectory()) {
      final File[] files = file.listFiles();
      if (files == null) { // null if security restricted
        throw new DirectoryCannotListException(file);
      }
      IOException exception = null;
      for (final File subFile : files) {
        try {
          // recursively delete the sub-files
          forceDelete(subFile);
        } catch (final IOException e) {
          exception = e;
        }
      }
      if (exception != null) {
        throw exception;
      }
    }
    try {
      if (!file.delete()) {
        throw new FileCannotDeleteException(file);
      }
    } catch (final SecurityException e) {
      throw new IOException(e);
    }
  }

  /**
   * 删除指定的文件。
   * <p>
   * 此函数类似于 {@link File#delete()}，但如果文件无法删除或发生任何安全错误，
   * 它会抛出 {@link IOException}。
   *
   * @param file
   *     要删除的文件。
   * @throws IOException
   *     如果文件无法删除或发生任何错误。
   */
  public static void delete(final File file) throws IOException {
    if (!file.exists()) {
      throw new FileNotExistException(file);
    }
    if (file.isDirectory()) {
      throw new FileIsDirectoryException(file);
    }
    try {
      if (!file.delete()) {
        throw new FileCannotDeleteException(file);
      }
    } catch (final SecurityException e) {
      throw new IOException(e);
    }
  }

  /**
   * 清空目录但不删除目录本身。
   *
   * <p>调用此函数后，目录下的所有文件和子目录都将被删除，但目录本身会保留。
   *
   * @param dir
   *     要清空的目录
   * @throws FileNotExistException
   *     如果目录不存在
   * @throws IOException
   *     如果清空操作不成功
   */
  public static void cleanDirectory(final File dir) throws IOException {
    if (!dir.exists()) {
      throw new DirectoryNotExistException(dir);
    }
    if (!dir.isDirectory()) {
      throw new FileIsNotDirectoryException(dir);
    }
    final File[] files = dir.listFiles();
    if (files == null) { // null if security restricted
      throw new DirectoryCannotListException(dir);
    }
    IOException exception = null;
    for (final File file : files) {
      try {
        forceDelete(file);
      } catch (final IOException e) {
        exception = e;
      }
    }
    if (exception != null) {
      throw exception;
    }
  }

  /**
   * 安排文件在 JVM 退出时删除。如果文件是目录，则删除它及其所有子目录。
   *
   * @param file
   *     要删除的文件或目录。如果文件不存在，函数不执行任何操作。
   * @throws IOException
   *     如果删除操作不成功
   */
  public static void forceDeleteOnExit(final File file) throws IOException {
    if (!file.exists()) {
      return;
    }
    if (file.isDirectory()) {
      final File[] files = file.listFiles();
      if (files == null) { // null if security restricted
        throw new DirectoryCannotListException(file);
      }
      IOException exception = null;
      for (final File subFile : files) {
        try {
          forceDeleteOnExit(subFile); // recursively delete the sub-files
        } catch (final IOException e) {
          exception = e;
        }
      }
      if (exception != null) {
        throw exception;
      }
    }
    file.deleteOnExit();
  }

  /**
   * 清空目录但不删除目录本身。
   *
   * <p>安排目录在 JVM 退出时清空。也就是说，目录下的所有文件和子目录将在 JVM 退出时删除，
   * 但目录本身会保留。
   *
   * @param dir
   *     要清空的目录
   * @throws DirectoryNotExistException
   *     如果目录不存在。
   * @throws FileIsNotDirectoryException
   *     如果指定的路径不是目录。
   * @throws IOException
   *     如果清空操作不成功
   */
  public static void cleanDirectoryOnExit(final File dir) throws IOException {
    if (!dir.exists()) {
      throw new DirectoryNotExistException(dir);
    }
    if (!dir.isDirectory()) {
      throw new FileIsNotDirectoryException(dir);
    }
    final File[] files = dir.listFiles();
    if (files == null) { // null if security restricted
      throw new DirectoryCannotListException(dir);
    }
    IOException exception = null;
    for (final File file : files) {
      try {
        forceDeleteOnExit(file);
      } catch (final IOException ioe) {
        exception = ioe;
      }
    }
    if (exception != null) {
      throw exception;
    }
  }

  /**
   * 使用指定的校验和对象计算文件的校验和。
   * 如果需要，可以通过重用相同的校验和对象来检查多个文件。例如：
   *
   * <pre>
   * long csum = FileUtils.checksum(file, new CRC32()).getValue();
   * </pre>
   *
   * @param file
   *     要计算校验和的文件，不得为 {@code null}
   * @param checksum
   *     要使用的校验和对象，不得为 {@code null}
   * @return 使用文件内容更新的指定校验和
   * @throws NullPointerException
   *     如果文件或校验和为 {@code null}
   * @throws IllegalArgumentException
   *     如果文件是目录
   * @throws IOException
   *     如果读取文件时发生 IO 错误
   * @since Commons IO 1.3
   */
  public static Checksum checksum(final File file, final Checksum checksum)
      throws IOException {
    if (file.isDirectory()) {
      throw new FileIsNotDirectoryException(file);
    }
    InputStream in = null;
    try {
      in = new CheckedInputStream(new FileInputStream(file), checksum);
      IoUtils.copy(in, Long.MAX_VALUE, NullOutputStream.INSTANCE);
    } finally {
      IoUtils.closeQuietly(in);
    }
    return checksum;
  }

  /**
   * 组合文件路径。
   *
   * @param folder
   *     文件夹的路径，可能以路径分隔符结尾，也可能不以路径分隔符结尾。
   * @param filename
   *     文件名。
   * @return 组合后的文件路径。
   */
  public static String getPath(final String folder, final String filename) {
    return getPath(folder, filename, null);
  }

  /**
   * 组合文件路径。
   *
   * @param folder
   *     文件夹的路径，可能以路径分隔符结尾，也可能不以路径分隔符结尾。
   * @param filename
   *     文件名。
   * @param extension
   *     文件的扩展名，可能为 null 或空。
   * @return 组合后的文件路径。
   */
  public static String getPath(final String folder, final String filename,
      @Nullable final String extension) {
    if (isEmpty(extension)) {
      if (folder.endsWith(File.separator)) {
        return concat(folder, filename);
      } else {
        return concat(folder, File.separator, filename);
      }
    } else {
      if (folder.endsWith(File.separator)) {
        return concat(folder, filename + "." + extension);
      } else {
        return concat(folder, File.separator, filename + "." + extension);
      }
    }
  }

  /**
   * 获取抽象路径的规范版本。
   *
   * @param file
   *     表示抽象路径的 File 对象。
   * @return 抽象路径的规范版本。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  public static File getCanonicalFile(final File file) throws IOException {
    return new File(file.getCanonicalPath());
  }

  /**
   * 列出指定目录中的所有常规文件（不包括子目录）。
   * 此方法永远不会返回 null（而是抛出 {@link IOException}）。
   *
   * @param dir
   *     目录。
   * @return 指定目录中的文件。
   * @throws DirectoryNotExistException
   *     如果目录不存在，或存在但不是目录。
   * @throws IOException
   *     如果 list() 返回 null
   */
  public static String[] listFiles(final File dir) throws IOException {
    if (!dir.exists()) {
      throw new DirectoryNotExistException(dir);
    } else if (!dir.isDirectory()) {
      throw new FileIsNotDirectoryException(dir);
    }
    final String[] result = dir.list(RegularFileFilter.INSTANCE);
    if (result == null) {
      throw new DirectoryCannotListException(dir);
    }
    return result;
  }

  /**
   * 列出指定目录中的所有子目录。此方法永远不会返回 null（而是抛出 {@link IOException}）。
   *
   * @param dir
   *     指定的目录。
   * @return 指定目录中的子目录。
   * @throws DirectoryNotExistException
   *     如果目录不存在，或存在但不是目录。
   * @throws IOException
   *     如果 list() 返回 null
   */
  public static String[] listSubDirectories(final File dir) throws IOException {
    if (!dir.exists()) {
      throw new DirectoryNotExistException(dir);
    } else if (!dir.isDirectory()) {
      throw new FileIsNotDirectoryException(dir);
    }
    final String[] result = dir.list(DirectoryFileFilter.INSTANCE);
    if (result == null) {
      throw new DirectoryCannotListException(dir);
    }
    return result;
  }

  /**
   * 复制或移动文件。
   *
   * @param source
   *     源文件。
   * @param dest
   *     目标文件。
   * @param move
   *     {@code true} 表示移动文件；{@code false} 表示复制文件。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  public static void copyOrMoveFile(final File source, final File dest,
      final boolean move) throws IOException {
    if (move) {
      final boolean success = source.renameTo(dest);
      if (!success) {
        throw new IOException("Failed to move the file '"
            + source.getAbsolutePath()
            + "' to '"
            + dest.getAbsolutePath()
            + "'");
      }
    } else {
      copyFile(source, dest, 0);
    }
  }

  /**
   * 从指定文件读取数据。
   *
   * @param file
   *     要读取数据的文件。
   * @return
   *     从文件读取的数据。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  public static byte[] readFromFile(final File file) throws IOException {
    try (final FileInputStream input = new FileInputStream(file)) {
      return IoUtils.toByteArray(input);
    }
  }

  /**
   * 将指定数据写入指定文件。
   *
   * @param data
   *     要写入的数据。
   * @param file
   *     要写入数据的文件。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  public static void writeToFile(final byte[] data, final File file) throws IOException {
    try (final FileOutputStream output = new FileOutputStream(file)) {
      output.write(data);
      output.flush();
    }
  }

  /**
   * 将指定数据写入指定文件。
   *
   * @param data
   *     要写入的数据。
   * @param filename
   *     要写入数据的文件路径。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  public static void writeToFile(final byte[] data, final String filename) throws IOException {
    final File file = new File(filename);
    writeToFile(data, file);
  }

  /**
   * 将指定数据写入指定文件。
   *
   * @param data
   *     要写入的数据。
   * @param path
   *     要写入数据的文件路径。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  public static void writeToFile(final byte[] data, final Path path) throws IOException {
    try (final OutputStream output = Files.newOutputStream(path)) {
      output.write(data);
      output.flush();
    }
  }

  /**
   * 将指定的文本数据写入指定文件。
   *
   * @param data
   *     要写入的文本数据。
   * @param file
   *     要写入数据的文件。
   * @param charset
   *     编码数据的字符集。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  public static void writeToFile(final String data, final File file,
      final Charset charset) throws IOException {
    try (final FileOutputStream output = new FileOutputStream(file)) {
      final OutputStreamWriter writer = new OutputStreamWriter(output, charset);
      writer.write(data);
      writer.flush();
    }
  }

  /**
   * 将指定的文本数据写入指定文件。
   *
   * @param data
   *     要写入的文本数据。
   * @param filename
   *     要写入数据的文件路径。
   * @param charset
   *     编码数据的字符集。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  public static void writeToFile(final String data, final String filename,
      final Charset charset) throws IOException {
    final File file = new File(filename);
    writeToFile(data, file, charset);
  }

  /**
   * 将指定的文本数据写入指定文件。
   *
   * @param data
   *     要写入的文本数据。
   * @param path
   *     要写入数据的文件路径。
   * @param charset
   *     编码数据的字符集。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  public static void writeToFile(final String data, final Path path,
      final Charset charset) throws IOException {
    try (final OutputStream output = Files.newOutputStream(path)) {
      final OutputStreamWriter writer = new OutputStreamWriter(output, charset);
      writer.write(data);
      writer.flush();
    }
  }

  /**
   * 为指定文件打开 {@link BufferedInputStream}。
   * <p>
   * 方法结束时，要么流成功打开，要么抛出异常。
   *
   * @param file
   *     要打开以供输入的文件，不得为 {@code null}。
   * @return
   *     指定文件的新 {@link BufferedInputStream}。
   * @throws FileNotExistException
   *     如果文件不存在。
   * @throws FileIsDirectoryException
   *     如果文件存在但是目录。
   * @throws FileCannotReadException
   *     如果文件无法读取。
   * @throws IOException
   *     如果发生任何其他 I/O 错误。
   */
  public static BufferedInputStream openInputStream(final File file)
      throws IOException {
    if (!file.exists()) {
      throw new FileNotExistException(file);
    }
    if (file.isDirectory()) {
      throw new FileIsDirectoryException(file);
    }
    if (!file.canRead()) {
      throw new FileCannotReadException(file);
    }
    final FileInputStream input = new FileInputStream(file);
    return new BufferedInputStream(input);
  }

  /**
   * 为指定文件打开 {@link BufferedOutputStream}，如果父目录不存在则检查并创建。
   * <p>
   * 方法结束时，要么流成功打开，要么抛出异常。
   * <p>
   * 如果父目录不存在，则会创建它。如果文件不存在，则会创建它。
   * 如果文件对象存在但是目录，则抛出异常。如果文件存在但无法写入，则抛出异常。
   * 如果无法创建父目录，则抛出异常。
   *
   * @param file
   *     要打开以供输出的文件，不得为 {@code null}。
   * @return
   *     指定文件的新 {@link BufferedOutputStream}。
   * @throws FileIsDirectoryException
   *     如果文件存在但是目录。
   * @throws FileCannotReadException
   *     如果文件无法写入。
   * @throws IOException
   *     如果需要创建父目录但失败
   */
  public static BufferedOutputStream openOutputStream(final File file)
      throws IOException {
    if (file.exists()) {
      if (file.isDirectory()) {
        throw new FileIsDirectoryException(file);
      }
      if (!file.canWrite()) {
        throw new FileCannotWriteException(file);
      }
    } else {
      ensureParentExist(file);
    }
    return new BufferedOutputStream(new FileOutputStream(file));
  }

  /**
   * 使用指定的字符集为指定文件打开 {@link BufferedReader}。
   * <p>
   * 方法结束时，要么读取器成功打开，要么抛出异常。
   *
   * @param file
   *     要打开以供输入的文件，不得为 {@code null}。
   * @param charset
   *     要使用的字符集。
   * @return
   *     为指定文件打开的、使用指定字符集的新 {@link BufferedReader}。
   * @throws FileNotExistException
   *     如果文件不存在。
   * @throws FileIsDirectoryException
   *     如果文件存在但是目录。
   * @throws FileCannotReadException
   *     如果文件无法读取。
   * @throws IOException
   *     如果发生任何其他 I/O 错误。
   */
  public static BufferedReader openReader(final File file, final Charset charset)
      throws IOException {
    if (!file.exists()) {
      throw new FileNotExistException(file);
    }
    if (file.isDirectory()) {
      throw new FileIsDirectoryException(file);
    }
    if (!file.canRead()) {
      throw new FileCannotReadException(file);
    }
    final FileInputStream input = new FileInputStream(file);
    final InputStreamReader reader = new InputStreamReader(input, charset);
    return new BufferedReader(reader);
  }

  /**
   * 使用指定的字符集为指定文件打开 {@link BufferedWriter}，如果父目录不存在则检查并创建。
   * <p>
   * 方法结束时，要么流成功打开，要么抛出异常。
   * <p>
   * 如果父目录不存在，则会创建它。如果文件不存在，则会创建它。
   * 如果文件对象存在但是目录，则抛出异常。如果文件存在但无法写入，则抛出异常。
   * 如果无法创建父目录，则抛出异常。
   *
   * @param file
   *     要打开以供输出的文件，不得为 {@code null}。
   * @param charset
   *     要使用的字符集。
   * @return
   *     为指定文件打开的、使用指定字符集的新 {@link BufferedWriter}。
   * @throws FileIsDirectoryException
   *     如果文件存在但是目录。
   * @throws FileCannotReadException
   *     如果文件无法写入。
   * @throws IOException
   *     如果需要创建父目录但失败
   */
  public static BufferedWriter openWriter(final File file, final Charset charset)
      throws IOException {
    if (file.exists()) {
      if (file.isDirectory()) {
        throw new FileIsDirectoryException(file);
      }
      if (!file.canWrite()) {
        throw new FileCannotWriteException(file);
      }
    } else {
      ensureParentExist(file);
    }
    final FileOutputStream output = new FileOutputStream(file);
    final OutputStreamWriter writer = new OutputStreamWriter(output, charset);
    return new BufferedWriter(writer);
  }
}
