////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import ltd.qubit.commons.io.error.DirectoryCanNotCreateException;
import ltd.qubit.commons.io.error.DirectoryCanNotListException;
import ltd.qubit.commons.io.error.DirectoryCanNotWriteException;
import ltd.qubit.commons.io.error.DirectoryNotFoundException;
import ltd.qubit.commons.io.error.FileCanNotDeleteException;
import ltd.qubit.commons.io.error.FileCanNotWriteException;
import ltd.qubit.commons.io.error.FileIsDirectoryException;
import ltd.qubit.commons.io.error.FileIsNotDirectoryException;
import ltd.qubit.commons.io.error.FileNotExistException;
import ltd.qubit.commons.lang.SystemUtils;
import ltd.qubit.commons.util.filter.file.DirectoryFileFilter;
import ltd.qubit.commons.util.filter.file.RegularFileFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import static ltd.qubit.commons.io.OperationOption.MAKE_DIRS;
import static ltd.qubit.commons.io.OperationOption.OVERWRITE;
import static ltd.qubit.commons.io.OperationOption.PRESERVE_DATE;
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

  @GuardedBy(value = "itself")
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
      "yyyy-MM-dd-HH-mm-ss-SSS");

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
    synchronized (DATE_FORMAT) {
      dateStr = DATE_FORMAT.format(now);
    }
    if (isEmpty(prefix)) {
      return dateStr + defaultIfNull(suffix, EMPTY);
    } else {
      return prefix + '_' + dateStr + defaultIfNull(suffix, EMPTY);
    }
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
    return createTempFile(DEFAULT_TEMP_FILE_PREFIX, null,
        DEFAULT_TEMP_FILE_RETRIES);
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
   * @param maxTries
   *     the maximum number of retries performed by this function.
   * @return a new temporary file in the system's default temporary directory
   *     with the specified prefix.
   * @throws IOException
   *     if any error occurs.
   */
  public static File createTempFile(@Nullable final String prefix,
      final int maxTries) throws IOException {
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
      LOGGER.warn("Failed to create the temporary file, try again: {}",
          tempFile);
      if (tries >= maxTries) {
        throw new IOException("Failed to create the temporary file after "
            + "too many tries: "
            + maxTries);
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
            + "too many tries: "
            + maxTries);
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
      throw new DirectoryNotFoundException(dir.getAbsolutePath());
    }
    if (!dir.isDirectory()) {
      throw new FileIsNotDirectoryException(dir.getAbsolutePath());
    }
    long result = 0;
    final File[] files = dir.listFiles();
    if (files == null) { // null if security restricted
      throw new DirectoryCanNotListException(dir.getAbsolutePath());
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
   * Opens a {@link FileOutputStream} for the specified file, checking and
   * creating the parent directory if it does not exist.
   *
   * <p>At the end of the method either the stream will be successfully opened,
   * or an exception will have been thrown.
   *
   * <p>The parent directory will be created if it does not exist. The file
   * will
   * be created if it does not exist. An exception is thrown if the file object
   * exists but is a directory. An exception is thrown if the file exists but
   * cannot be written to. An exception is thrown if the parent directory cannot
   * be created.
   *
   * @param file
   *     the file to open for output, must not be {@code null}
   * @return a new {@link FileOutputStream} for the specified file
   * @throws IOException
   *     if the file object is a directory
   * @throws IOException
   *     if the file cannot be written to
   * @throws IOException
   *     if a parent directory needs creating but that fails
   */
  public static FileOutputStream openOutputStream(final File file)
      throws IOException {
    if (file.exists()) {
      if (file.isDirectory()) {
        throw new FileIsDirectoryException(file.getAbsolutePath());
      }
      if (!file.canWrite()) {
        throw new FileCanNotWriteException(file.getAbsolutePath());
      }
    } else {
      ensureParentExist(file);
    }
    return new FileOutputStream(file);
  }

  /**
   * Make sure that the specified directory exists.
   *
   * <p>That is, if the specified directory does not exist, the function will
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
        throw new DirectoryCanNotCreateException(dir.getAbsolutePath());
      }
    } else if (!dir.isDirectory()) {
      throw new FileIsNotDirectoryException(dir.getAbsolutePath());
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
          throw new DirectoryCanNotCreateException(parent.getAbsolutePath());
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
          throw new DirectoryCanNotCreateException(parent.getAbsolutePath());
        }
      }
      // atomically creates a new, empty file
      file.createNewFile();
    }
    final boolean success = file.setLastModified(System.currentTimeMillis());
    if (!success) {
      throw new IOException(
          "Setting the file last modify time failed: " + file);
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
      if (!dir.exists()) {
        dir.mkdirs();
      }
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
        throw new DirectoryCanNotCreateException(pathname, e);
      }
      return dir;
    } else if (SystemUtils.IS_OS_UNIX) {
      // usually unix like: use . as prefix
      final String name = dir.getName();
      if (name.length() == 0) {
        throw new IllegalArgumentException("Invalid directory name. ");
      }
      File folder = dir;
      if (name.charAt(0) != '.') {
        final String parent = folder.getParent() + File.separatorChar;
        folder = new File(parent, '.' + name);
      }
      // create folder if it does not exist
      if (!folder.exists()) {
        folder.mkdirs();
      }
      return folder;
    } else {
      throw new UnsupportedOperationException("The function is not supported "
          + "for the operation system: "
          + SystemUtils.OS_NAME);
    }
  }

  /**
   * Gets a File object represents the directory of a specified path. If the
   * path does not exists, crate a directory of that path; otherwise, if the
   * path exists and is not a directory, returns null.
   *
   * @param path
   *     the path of the directory.
   * @return a File object represents the directory of a specified path.
   */
  public static File getOrCreateDirectory(final String path) {
    final File result = new File(path);
    if (result.exists()) {
      if (result.isDirectory()) {
        return result;
      } else {
        return null;
      }
    } else {
      result.mkdirs();
      if (result.exists()) {
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
      throw new FileNotExistException(srcFile.getAbsolutePath());
    }
    if (srcFile.isDirectory()) {
      throw new FileIsDirectoryException(srcFile.getAbsolutePath());
    }
    if (destFile.exists()) {
      if ((options & OVERWRITE) == 0) {
        // do not overwrite the existing file
        return false;
      }
      if (!destFile.canWrite()) {
        throw new FileCanNotWriteException(destFile.getAbsolutePath());
      }
      if (destFile.isDirectory()) {
        throw new FileIsDirectoryException(destFile.getAbsolutePath());
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
          throw new DirectoryCanNotCreateException(
              destParent.getAbsolutePath());
        }
      }
    }
    if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath())) {
      throw new IOException(
          "The source and destination are the same file " + "or directory. ");
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
        throw new FileIsNotDirectoryException(destDir.getAbsolutePath());
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
      throw new DirectoryNotFoundException(srcDir.getAbsolutePath());
    }
    if (!srcDir.isDirectory()) {
      throw new FileIsNotDirectoryException(srcDir.getAbsolutePath());
    }
    if (destDir.exists()) {
      if (!destDir.canWrite()) {
        throw new DirectoryCanNotWriteException(destDir.getAbsolutePath());
      }
      if (!destDir.isDirectory()) {
        throw new FileIsNotDirectoryException(destDir.getAbsolutePath());
      }
    } else { // destination file does not exist
      // create the parent directories of the destination file if necessary
      if ((options & MAKE_DIRS) == 0) {
        return 0; // do not create the directories
      }
      if (!destDir.mkdirs()) {
        throw new DirectoryCanNotCreateException(destDir.getAbsolutePath());
      }
      if (!destDir.canWrite()) {
        throw new DirectoryCanNotWriteException(destDir.getAbsolutePath());
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
      throw new DirectoryCanNotListException(srcDir.getAbsolutePath());
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
            throw new FileIsNotDirectoryException(destSubDir.getAbsolutePath());
          }
        } else {
          if (!destSubDir.mkdir()) {
            throw new DirectoryCanNotCreateException(destSubDir.getPath());
          }
          if (preserveDate) {
            destSubDir.setLastModified(srcFile.lastModified());
          }
        }
        if (!destSubDir.canWrite()) {
          throw new DirectoryCanNotWriteException(destSubDir.getPath());
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
            throw new FileIsNotDirectoryException(destFile.getAbsolutePath());
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
        throw new DirectoryCanNotListException(file.getAbsolutePath());
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
    if (!file.delete()) {
      throw new FileCanNotDeleteException(file.getAbsolutePath());
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
      throw new DirectoryNotFoundException(dir.getAbsolutePath());
    }
    if (!dir.isDirectory()) {
      throw new FileIsNotDirectoryException(dir.getAbsolutePath());
    }
    final File[] files = dir.listFiles();
    if (files == null) { // null if security restricted
      throw new DirectoryCanNotListException(dir.getAbsolutePath());
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
        throw new DirectoryCanNotListException(file.getAbsolutePath());
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
   * @throws DirectoryNotFoundException
   *     if the directory does not exist.
   * @throws FileIsNotDirectoryException
   *     if the specified path is not a directory.
   * @throws IOException
   *     in case cleaning is unsuccessful
   */
  public static void cleanDirectoryOnExit(final File dir) throws IOException {
    if (!dir.exists()) {
      throw new DirectoryNotFoundException(dir.getAbsolutePath());
    }
    if (!dir.isDirectory()) {
      throw new FileIsNotDirectoryException(dir.getAbsolutePath());
    }
    final File[] files = dir.listFiles();
    if (files == null) { // null if security restricted
      throw new DirectoryCanNotListException(dir.getAbsolutePath());
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
      throw new FileIsNotDirectoryException(file.getAbsolutePath());
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

  /**
   * Returns the extension of a filename.
   *
   * @param filename
   *     a file name.
   * @return the extension of the filename, WITHOUT a dot; or "" if there is
   *     extension of the original filename.
   */
  public static String getExtension(final String filename) {
    if (filename == null) {
      return "";
    }
    final int i = filename.lastIndexOf('.');
    if (i == -1) {
      return "";
    }
    return filename.substring(i + 1);
  }

  /**
   * Returns the extension of a filename.
   *
   * @param filename
   *     a file name.
   * @return the extension of the filename, WITH a dot; or "" if there is
   *     extension of the original filename.
   */
  public static String getDotExtension(final String filename) {
    if (filename == null) {
      return "";
    }
    final int i = filename.lastIndexOf('.');
    if (i == -1) {
      return "";
    }
    return "." + filename.substring(i + 1);
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
   * Gets the the canonical version of the abstract path.
   *
   * @param file
   *     a File object representing an abstract path.
   * @return the the canonical version of the abstract path.
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
   * @throws DirectoryNotFoundException
   *     if the directory does not exist, or does exist but is not a directory.
   * @throws IOException
   *     if list() returns null
   */
  public static String[] listFiles(final File dir) throws IOException {
    if (!dir.exists()) {
      throw new DirectoryNotFoundException(dir.getAbsolutePath());
    } else if (!dir.isDirectory()) {
      throw new FileIsNotDirectoryException(dir.getAbsolutePath());
    }
    final String[] result = dir.list(RegularFileFilter.INSTANCE);
    if (result == null) {
      throw new DirectoryCanNotListException(dir.getAbsolutePath());
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
   * @throws DirectoryNotFoundException
   *     if the directory does not exist, or does exist but is not a directory.
   * @throws IOException
   *     if list() returns null
   */
  public static String[] listSubDirectories(final File dir) throws IOException {
    if (!dir.exists()) {
      throw new DirectoryNotFoundException(dir.getAbsolutePath());
    } else if (!dir.isDirectory()) {
      throw new FileIsNotDirectoryException(dir.getAbsolutePath());
    }
    final String[] result = dir.list(DirectoryFileFilter.INSTANCE);
    if (result == null) {
      throw new DirectoryCanNotListException(dir.getAbsolutePath());
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
}
