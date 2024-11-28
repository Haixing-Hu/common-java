////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.io;

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

import ltd.qubit.commons.io.io.error.DirectoryCannotCreateException;
import ltd.qubit.commons.io.io.error.DirectoryCannotListException;
import ltd.qubit.commons.io.io.error.DirectoryCannotWriteException;
import ltd.qubit.commons.io.io.error.DirectoryNotExistException;
import ltd.qubit.commons.io.io.error.FileCannotDeleteException;
import ltd.qubit.commons.io.io.error.FileCannotReadException;
import ltd.qubit.commons.io.io.error.FileCannotWriteException;
import ltd.qubit.commons.io.io.error.FileIsDirectoryException;
import ltd.qubit.commons.io.io.error.FileIsNotDirectoryException;
import ltd.qubit.commons.io.io.error.FileNotExistException;
import ltd.qubit.commons.lang.SystemUtils;
import ltd.qubit.commons.util.filter.file.DirectoryFileFilter;
import ltd.qubit.commons.util.filter.file.RegularFileFilter;

import static ltd.qubit.commons.io.io.OperationOption.MAKE_DIRS;
import static ltd.qubit.commons.io.io.OperationOption.OVERWRITE;
import static ltd.qubit.commons.io.io.OperationOption.PRESERVE_DATE;
import static ltd.qubit.commons.lang.ObjectUtils.defaultIfNull;
import static ltd.qubit.commons.lang.StringUtils.EMPTY;
import static ltd.qubit.commons.lang.StringUtils.concat;
import static ltd.qubit.commons.lang.StringUtils.isEmpty;

/**
 * This class provides common file system operations.
 *
 * @author Haixing Hu
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
   * Generate a random filename with a specified prefix.
   *
   * @param prefix
   *     the prefix of the temporary filename.
   * @return a random filename with the specified prefix.
   */
  public static String getRandomFileName(@Nullable final String prefix) {
    return getRandomFileName(prefix, null);
  }

  /**
   * Generate a random filename with a specified prefix.
   *
   * @param prefix
   *     the prefix of the temporary filename.
   * @param suffix
   *     the suffix of the temporary filename.
   * @return a random filename with the specified prefix.
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
   * Get the path of the system temporary directory.
   *
   * @return
   *     the path of the system temporary directory.
   * @throws IOException
   *     if any error occurs.
   */
  public static File getTempDirectory() throws IOException {
    final String path = SystemUtils.JAVA_IO_TMPDIR;
    if (path == null) {
      throw new IOException("The system property 'java.io.tmpdir' is not defined.");
    }
    return new File(path);
  }

  /**
   * Get a temporary file or directory pathname with a specified prefix.
   *
   * @param prefix
   *     the prefix of the temporary filename.
   * @return an abstract pathname of a file or directory in the system's default
   *     temporary directory.
   */
  public static String getTempFilePath(@Nullable final String prefix) {
    return getTempFilePath(prefix, null);
  }

  /**
   * Get a temporary file or directory pathname with a specified prefix.
   *
   * @param prefix
   *     the prefix of the temporary filename.
   * @param suffix
   *     the suffix of the temporary filename.
   * @return an abstract pathname of a file or directory in the system's default
   *     temporary directory.
   */
  public static String getTempFilePath(@Nullable final String prefix,
      @Nullable final String suffix) {
    final String tempDirPath = SystemUtils.JAVA_IO_TMPDIR;
    final String tempFileName = getRandomFileName(prefix, suffix);
    return tempDirPath + File.separatorChar + tempFileName;
  }

  /**
   * Get a temporary file or directory pathname with a default prefix.
   *
   * @return an abstract pathname of a file or directory in the system's default
   *     temporary directory.
   */
  public static File getTempFile() {
    return getTempFile(DEFAULT_TEMP_FILE_PREFIX, null);
  }

  /**
   * Get a temporary file or directory pathname with a specified prefix.
   *
   * @param prefix
   *     the prefix of the temporary filename.
   * @return an abstract pathname of a file or directory in the system's default
   *     temporary directory.
   */
  public static File getTempFile(@Nullable final String prefix) {
    return getTempFile(prefix, null);
  }

  /**
   * Get a temporary file or directory pathname with a specified prefix.
   *
   * @param prefix
   *     the prefix of the temporary filename.
   * @param suffix
   *     the suffix of the temporary filename.
   * @return an abstract pathname of a file or directory in the system's default
   *     temporary directory.
   */
  public static File getTempFile(@Nullable final String prefix,
      @Nullable final String suffix) {
    final String tempDirPath = SystemUtils.JAVA_IO_TMPDIR;
    final String tempFileName = getRandomFileName(prefix, suffix);
    return new File(tempDirPath, tempFileName);
  }

  /**
   * Create a new empty temporary file in the system's default temporary
   * directory.
   *
   * @return a new temporary file in the system's default temporary directory
   *     with the default prefix.
   * @throws IOException
   *     if any error occurs.
   */
  public static File createTempFile() throws IOException {
    return createTempFile(DEFAULT_TEMP_FILE_PREFIX, null, DEFAULT_TEMP_FILE_RETRIES);
  }

  /**
   * Create a new empty temporary file in the system's default temporary
   * directory.
   *
   * @param prefix
   *     the prefix of the temporary filename.
   * @return a new temporary file in the system's default temporary directory
   *     with the specified prefix.
   * @throws IOException
   *     if any error occurs.
   */
  public static File createTempFile(final String prefix) throws IOException {
    return createTempFile(prefix, null, DEFAULT_TEMP_FILE_RETRIES);
  }

  /**
   * Create a new empty temporary file in the system's default temporary
   * directory.
   *
   * @param prefix
   *     the prefix of the temporary filename.
   * @param suffix
   *     the suffix of the temporary filename.
   * @return a new temporary file in the system's default temporary directory
   *     with the specified prefix.
   * @throws IOException
   *     if any error occurs.
   */
  public static File createTempFile(final String prefix, final String suffix)
      throws IOException {
    return createTempFile(prefix, suffix, DEFAULT_TEMP_FILE_RETRIES);
  }

  /**
   * Create a new empty temporary file in the system's default temporary
   * directory.
   *
   * @param prefix
   *     the prefix of the temporary filename.
   * @param maxTries
   *     the maximum number of retries performed by this function.
   * @return a new temporary file in the system's default temporary directory
   *     with the specified prefix.
   * @throws IOException
   *     if any error occurs.
   */
  public static File createTempFile(@Nullable final String prefix, final int maxTries)
      throws IOException {
    return createTempFile(prefix, null, maxTries);
  }

  /**
   * Create a new empty temporary file in the system's default temporary
   * directory.
   *
   * @param prefix
   *     the prefix of the temporary filename.
   * @param suffix
   *     the suffix of the temporary filename.
   * @param maxTries
   *     the maximum number of retries performed by this function.
   * @return a new temporary file in the system's default temporary directory
   *     with the specified prefix and suffix.
   * @throws IOException
   *     if any error occurs.
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
   * Create a new empty temporary directory in the system's default temporary
   * directory.
   *
   * @param prefix
   *     the prefix of the temporary directory name.
   * @param maxTries
   *     the maximum number of retries performed by this function.
   * @return a new temporary directory in the system's default temporary
   *     directory with the specified prefix.
   * @throws IOException
   *     if any error occurs.
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
   * Counts the size of a directory recursively (sum of the length of all
   * files).
   *
   * @param dir
   *     directory to inspect, must not be {@code null}
   * @return size of directory in bytes, 0 if directory is security restricted
   * @throws FileNotExistException
   *     if the directory does not exist.
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
   * Make sure that the specified directory exists.
   * <p>
   * That is, if the specified directory does not exist, the function will
   * create it.
   *
   * @param dir
   *     a specified directory.
   * @throws IOException
   *     if the directory does not exist and the function failed to create it;
   *     or if the file of the specified pathname exists but is not a
   *     directory.
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
   * Make sure that the parent directory of the specified file exists.
   *
   * <p>That is, if the parent directory of the specified file does not exist,
   * the function will create it.
   *
   * @param file
   *     a specified file.
   * @throws IOException
   *     if any I/O error occurs.
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
   * Implements the same behavior as the "touch" utility on Unix. It creates a
   * new file with size 0 or, if the file exists already, it is opened and
   * closed without modifying it, but updating the file date and time.
   *
   * <p>NOTE: This method throws an IOException if the last modified date of
   * the file cannot be set. Also, this method creates parent directories if
   * they do not exist.
   *
   * @param file
   *     the File to touch
   * @throws IOException
   *     If an I/O problem occurs
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
   * Create a new hidden directory. The name may change on Unix.
   *
   * <p>If the directory already exists, that directory is returned without
   * creating a new one.
   *
   * @param dir
   *     the directory to be created.
   * @return the File object of the created hidden directory.
   * @throws IOException
   *     if any error occurs.
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
   * Gets a File object represents the directory of a specified path. If the
   * path does not exists, crate a directory of that path; otherwise, if the
   * path exists and is not a directory, returns null.
   *
   * @param path
   *     the path of the directory.
   * @return
   *     a {@link File} object represents the directory of a specified path.
   * @throws IOException
   *     if any error occurs.
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
   * Gets a File object represents the directory of a specified path. If the
   * path does not exists, crate a directory of that path; otherwise, if the
   * path exists and is not a directory, returns null.
   *
   * @param path
   *     the path of the directory.
   * @return
   *     a {@link File} object represents the directory of a specified path, or
   *     {@code null} if failed to create the specified directory.
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
   * Compares the binary contents of two files to determine if they are equal or
   * not.
   *
   * <p>This method checks to see if the two files are different lengths or if
   * they point to the same file, before resorting to byte-by-byte comparison of
   * the contents.
   *
   * @param file1
   *     the first file
   * @param file2
   *     the second file
   * @return true if the binary content of the files are equal or they both
   *     don't exist, false otherwise
   * @throws IOException
   *     in case of an I/O error
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
   * Compares the binary contents of two files lexicographically.
   *
   * <p>This method checks to see if the two files are different lengths or if
   * they point to the same file, before resorting to byte-by-byte comparison of
   * the contents.
   *
   * @param file1
   *     the first file
   * @param file2
   *     the second file
   * @return An integer less than, equal to or greater than 0, if the binary
   *     content of the first file compares lexicographically less than, equal
   *     to, or greater than the binary content of the second file.
   * @throws IOException
   *     in case of an I/O error
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
   * Copies a file to a new location.
   *
   * <p>This method copies the contents of the specified source file to the
   * specified destination file. The directory holding the destination file is
   * created if it does not exist.
   *
   * <p>TODO: add the supporting of a progress displaying call-back function.
   *
   * @param srcFile
   *     an existing file to copy.
   * @param destFile
   *     the destination file.
   * @param options
   *     a bitwise combination of the constants defined in the
   *     {@link OperationOption} class.
   * @return true if the copying succeed; false otherwise.
   * @throws FileNotExistException
   *     if the srcFile does not exist.
   * @throws IOException
   *     if an IO error occurs during copying
   * @see OperationOption
   */
  public static boolean copyFile(final File srcFile, final File destFile,
      final int options) throws IOException {
    if (!srcFile.exists()) {
      throw new FileNotExistException(srcFile);
    }
    if (srcFile.isDirectory()) {
      throw new FileIsDirectoryException(srcFile);
    }
    if (destFile.exists()) {
      if ((options & OVERWRITE) == 0) {
        // do not overwrite the existing file
        return false;
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
          return false;
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
    return true;
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
   * Copies a file to a directory optionally preserving the file date.
   *
   * <p>This method copies the contents of the specified source file to a file
   * of the same name in the specified destination directory. The destination
   * directory is created if it does not exist. If the destination file exists,
   * then this method will overwrite it.
   *
   * @param srcFile
   *     an existing file to copy, must not be {@code null}
   * @param destDir
   *     the directory to place the copy in, must not be {@code null}
   * @param options
   *     a bitwise combination of the constants defined in the
   *     {@link OperationOption} class.
   * @return true if the copying succeed; false otherwise.
   * @throws FileNotExistException
   *     if the srcFile does not exist.
   * @throws IOException
   *     if an IO error occurs during copying
   * @see OperationOption
   */
  public static boolean copyFileToDirectory(final File srcFile,
      final File destDir, final int options) throws IOException {
    if (destDir.exists()) {
      if (!destDir.isDirectory()) {
        throw new FileIsNotDirectoryException(destDir);
      }
    }
    final File destFile = new File(destDir, srcFile.getName());
    return copyFile(srcFile, destFile, options);
  }

  /**
   * Copies a filtered directory to a new location.
   *
   * <p>This method copies the contents of the specified source directory to
   * within the specified destination directory.
   *
   * <p>The destination directory is created if it does not exist. If the
   * destination directory did exist, then this method merges the source with
   * the destination, with the source taking precedence.
   *
   * <p>Example: Copy directories only
   * <pre>
   * // only copy the directory structure
   * FileUtils.copyDirectory(srcDir, destDir, DirectoryFileFilter.DIRECTORY, false);
   * </pre>
   *
   * <p>Example: Copy directories and txt files
   * <pre>
   * // Create a filter for &quot;.txt&quot; files
   * IOFileFilter txtSuffixFilter = FileFilterUtils.suffixFileFilter(&quot;.txt&quot;);
   * IOFileFilter txtFiles = FileFilterUtils.andFileFilter(FileFileFilter.FILE,
   *     txtSuffixFilter);
   * // Create a filter for either directories or &quot;.txt&quot; files
   * FileFilter filter = FileFilterUtils.orFileFilter(DirectoryFileFilter.DIRECTORY,
   *     txtFiles);
   * // Copy using the filter
   * FileUtils.copyDirectory(srcDir, destDir, filter, false);
   * </pre>
   *
   * @param srcDir
   *     an existing directory to copy, must not be {@code null}
   * @param destDir
   *     the new directory, must not be {@code null}
   * @param filter
   *     the filter to apply, null means copy all directories and files
   * @param options
   *     a bitwise combination of the constants defined in the
   *     {@link OperationOption} class.
   * @return the number of files or directories copied.
   * @throws FileNotExistException
   *     if the srcDir does not exist.
   * @throws IOException
   *     if an IO error occurs during copying
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
   * Deletes a file. If file is a directory, delete it and all sub-directories.
   *
   * <p>The difference between File.delete() and this method are:
   * <ul>
   * <li>A directory to be deleted does not have to be empty.</li>
   * <li>You get exceptions when a file or directory cannot be deleted.
   * (java.io.File methods returns a boolean)</li>
   * </ul>
   *
   * @param file
   *     file or directory to delete. Note that if the file does not exist, the
   *     function do nothing.
   * @throws IOException
   *     in case deletion is unsuccessful
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
   * Deletes the specified file.
   * <p>
   * This function is similar to the {@link File#delete()}, but it throws an
   * {@link IOException} if the file cannot be deleted or any security error
   * occurs.
   *
   * @param file
   *     the file to delete.
   * @throws IOException
   *     if the file cannot be deleted, or any error occurs.
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
   * Cleans a directory without deleting it.
   *
   * <p>After calling this function, all files and sub-directories under the
   * directory is deleted, while the directory itself is kept.
   *
   * @param dir
   *     directory to clean
   * @throws FileNotExistException
   *     if dir does not exist
   * @throws IOException
   *     in case cleaning is unsuccessful
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
   * Schedules a file to be deleted when JVM exits. If file is directory delete
   * it and all sub-directories.
   *
   * @param file
   *     file or directory to delete. If the file does not exist, the function
   *     does nothing.
   * @throws IOException
   *     in case deletion is unsuccessful
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
   * Cleans a directory without deleting it.
   *
   * <p>Schedules a directory to be cleaned when JVM exits. That is, all files
   * and sub-directories under the directory will be deleted when JVM exits,
   * while the directory itself is kept.
   *
   * @param dir
   *     directory to clean
   * @throws DirectoryNotExistException
   *     if the directory does not exist.
   * @throws FileIsNotDirectoryException
   *     if the specified path is not a directory.
   * @throws IOException
   *     in case cleaning is unsuccessful
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
   * Computes the checksum of a file using the specified checksum object.
   * Multiple files may be checked using one {@code Checksum} instance if
   * desired simply by reusing the same checksum object. For example:
   *
   * <pre>
   * long csum = FileUtils.checksum(file, new CRC32()).getValue();
   * </pre>
   *
   * @param file
   *     the file to checksum, must not be {@code null}
   * @param checksum
   *     the checksum object to be used, must not be {@code null}
   * @return the checksum specified, updated with the content of the file
   * @throws NullPointerException
   *     if the file or checksum is {@code null}
   * @throws IllegalArgumentException
   *     if the file is a directory
   * @throws IOException
   *     if an IO error occurs reading the file
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
   * Composes the file path.
   *
   * @param folder
   *     the path of the folder, may or may not end with the path separator.
   * @param filename
   *     the name of the file.
   * @return the composed file path.
   */
  public static String getPath(final String folder, final String filename) {
    return getPath(folder, filename, null);
  }

  /**
   * Composes the file path.
   *
   * @param folder
   *     the path of the folder, may or may not end with the path separator.
   * @param filename
   *     the name of the file.
   * @param extension
   *     the extension of the file, which may be null or empty.
   * @return the composed file path.
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
   * Gets the canonical version of the abstract path.
   *
   * @param file
   *     a File object representing an abstract path.
   * @return the canonical version of the abstract path.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static File getCanonicalFile(final File file) throws IOException {
    return new File(file.getCanonicalPath());
  }

  /**
   * Lists all regular files (not sub-directories) in a specified directory.
   * This method never returns null (throws {@link IOException} instead).
   *
   * @param dir
   *     the directory.
   * @return the files in the specified directory.
   * @throws DirectoryNotExistException
   *     if the directory does not exist, or does exist but is not a directory.
   * @throws IOException
   *     if list() returns null
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
   * Lists all sub-directories in a specified directory. This method never
   * returns null (throws {@link IOException} instead).
   *
   * @param dir
   *     the specified directory.
   * @return the sub-directories in the specified directory.
   * @throws DirectoryNotExistException
   *     if the directory does not exist, or does exist but is not a directory.
   * @throws IOException
   *     if list() returns null
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
   * Copy or move a file.
   *
   * @param source
   *     the source file.
   * @param dest
   *     the destination file.
   * @param move
   *     {@code true} to move the file; and {@code false} to copy the file.
   * @throws IOException
   *     if any I/O error occurs.
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
   * Reads the data from the specified file.
   *
   * @param file
   *     the file to read the data from.
   * @return
   *     the data read from the file.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static byte[] readFromFile(final File file) throws IOException {
    try (final FileInputStream input = new FileInputStream(file)) {
      return IoUtils.toByteArray(input);
    }
  }

  /**
   * Writes the specified data to the specified file.
   *
   * @param data
   *     the data to be written.
   * @param file
   *     the file to write the data to.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static void writeToFile(final byte[] data, final File file) throws IOException {
    try (final FileOutputStream output = new FileOutputStream(file)) {
      output.write(data);
      output.flush();
    }
  }

  /**
   * Writes the specified data to the specified file.
   *
   * @param data
   *     the data to be written.
   * @param filename
   *     the path of the file to write the data to.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static void writeToFile(final byte[] data, final String filename) throws IOException {
    final File file = new File(filename);
    writeToFile(data, file);
  }

  /**
   * Writes the specified data to the specified file.
   *
   * @param data
   *     the data to be written.
   * @param path
   *     the path of the file to write the data to.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static void writeToFile(final byte[] data, final Path path) throws IOException {
    try (final OutputStream output = Files.newOutputStream(path)) {
      output.write(data);
      output.flush();
    }
  }

  /**
   * Writes the specified text data to the specified file.
   *
   * @param data
   *     the text data to be written.
   * @param file
   *     the file to write the data to.
   * @param charset
   *     the charset to encode the data.
   * @throws IOException
   *     if any I/O error occurs.
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
   * Writes the specified text data to the specified file.
   *
   * @param data
   *     the text data to be written.
   * @param filename
   *     the path of the file to write the data to.
   * @param charset
   *     the charset to encode the data.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static void writeToFile(final String data, final String filename,
      final Charset charset) throws IOException {
    final File file = new File(filename);
    writeToFile(data, file, charset);
  }

  /**
   * Writes the specified text data to the specified file.
   *
   * @param data
   *     the text data to be written.
   * @param path
   *     the path of the file to write the data to.
   * @param charset
   *     the charset to encode the data.
   * @throws IOException
   *     if any I/O error occurs.
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
   * Opens a {@link BufferedInputStream} for the specified file.
   * <p>
   * At the end of the method either the stream will be successfully opened,
   * or an exception will have been thrown.
   *
   * @param file
   *     the file to open for input, must not be {@code null}.
   * @return
   *     a new {@link BufferedInputStream} for the specified file.
   * @throws FileNotExistException
   *     if the file does not exist.
   * @throws FileIsDirectoryException
   *     if the file exists but is a directory.
   * @throws FileCannotReadException
   *     if the file cannot be read.
   * @throws IOException
   *     if any other I/O error occurs.
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
   * Opens a {@link BufferedOutputStream} for the specified file, checking and
   * creating the parent directory if it does not exist.
   * <p>
   * At the end of the method either the stream will be successfully opened,
   * or an exception will have been thrown.
   * <p>
   * The parent directory will be created if it does not exist. The file will
   * be created if it does not exist. An exception is thrown if the file object
   * exists but is a directory. An exception is thrown if the file exists but
   * cannot be written to. An exception is thrown if the parent directory cannot
   * be created.
   *
   * @param file
   *     the file to open for output, must not be {@code null}.
   * @return
   *     a new {@link BufferedOutputStream} for the specified file.
   * @throws FileIsDirectoryException
   *     if the file exists but is a directory.
   * @throws FileCannotReadException
   *     if the file cannot be written.
   * @throws IOException
   *     if a parent directory needs creating but that fails
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
   * Opens a {@link BufferedReader} for the specified file with the specified
   * charset.
   * <p>
   * At the end of the method either the reader will be successfully opened,
   * or an exception will have been thrown.
   *
   * @param file
   *     the file to open for input, must not be {@code null}.
   * @param charset
   *     the charset to use.
   * @return
   *     a new {@link BufferedReader} for the specified file, opened with the
   *     specified charset.
   * @throws FileNotExistException
   *     if the file does not exist.
   * @throws FileIsDirectoryException
   *     if the file exists but is a directory.
   * @throws FileCannotReadException
   *     if the file cannot be read.
   * @throws IOException
   *     if any other I/O error occurs.
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
   * Opens a {@link BufferedWriter} for the specified file with the specified
   * charset, checking and creating the parent directory if it does not exist.
   * <p>
   * At the end of the method either the stream will be successfully opened,
   * or an exception will have been thrown.
   * <p>
   * The parent directory will be created if it does not exist. The file will
   * be created if it does not exist. An exception is thrown if the file object
   * exists but is a directory. An exception is thrown if the file exists but
   * cannot be written to. An exception is thrown if the parent directory cannot
   * be created.
   *
   * @param file
   *     the file to open for output, must not be {@code null}.
   * @param charset
   *     the charset to use.
   * @return
   *     a new {@link BufferedWriter} for the specified file, opened with the
   *     specified charset.
   * @throws FileIsDirectoryException
   *     if the file exists but is a directory.
   * @throws FileCannotReadException
   *     if the file cannot be written.
   * @throws IOException
   *     if a parent directory needs creating but that fails
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
