////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.SecureRandom;
import java.util.Random;

import javax.annotation.concurrent.ThreadSafe;

import ltd.qubit.commons.reflect.ConstructorUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ltd.qubit.commons.lang.CharUtils.isAsciiDigit;

/**
 * This class provides helper functions for {@code java.lang.System}.
 *
 * <p>If a system property cannot be read due to security restrictions, the
 * corresponding field in this class will be set to {@code null} and a
 * message will be written to the log.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class SystemUtils {

  /**
   * The prefix String for all Windows OS.
   */
  private static final String OS_NAME_WINDOWS_PREFIX = "Windows";

  /**
   * The System property key for the Java home directory.
   */
  public static final String JAVA_HOME_KEY = "java.home";

  /**
   * The System property key for the Java IO temporary directory.
   */
  public static final String JAVA_IO_TMPDIR_KEY = "java.io.tmpdir";

  /**
   * The System property key for the Java class path.
   */
  public static final String JAVA_CLASS_PATH_KEY = "java.class.path";

  /**
   * The System property key for the Java class format version number.
   */
  public static final String JAVA_CLASS_VERSION_KEY = "java.class.version";

  /**
   * The System property key for Java compiler name.
   */
  public static final String JAVA_COMPILER_KEY = "java.compiler";

  /**
   * The System property key for the Java endorsed directory or directories..
   */
  public static final String JAVA_ENDORSED_DIRS_KEY = "java.endorsed.dirs";

  /**
   * The System property key for the Java extension directory or directories.
   */
  public static final String JAVA_EXT_DIRS_KEY = "java.ext.dirs";

  /**
   * The System property key for the list of paths to search when loading
   * libraries.
   */
  public static final String JAVA_LIBRARY_PATH_KEY = "java.library.path";

  /**
   * The System property key for the Java runtime environment name.
   */
  public static final String JAVA_RUNTIME_NAME_KEY = "java.runtime.name";

  /**
   * The System property key for the Java runtime environment version.
   */
  public static final String JAVA_RUNTIME_VERSION_KEY = "java.runtime.version";

  /**
   * The System property key for the Java runtime environment specification
   * name.
   */
  public static final String JAVA_SPECIFICATION_NAME_KEY =
      "java.specification.name";

  /**
   * The System property key for the Java runtime environment specification
   * version.
   */
  public static final String JAVA_SPECIFICATION_VERSION_KEY =
      "java.specification.version";

  /**
   * The System property key for the Java runtime environment specification
   * vendor.
   */
  public static final String JAVA_SPECIFICATION_VENDOR_KEY =
      "java.specification.vendor";

  /**
   * The System property key for the Java PreferencesFactory class name.
   */
  public static final String JAVA_PREFERENCES_FACTORY_KEY =
      "java.util.prefs.PreferencesFactory";

  /**
   * The System property key for the Java vendor-specific string.
   */
  public static final String JAVA_VENDOR_KEY = "java.vendor";

  /**
   * The System property key for the Java vendor URL.
   */
  public static final String JAVA_VENDOR_URL_KEY = "java.vendor.url";

  /**
   * The System property key for the Java version number.
   */
  public static final String JAVA_VERSION_KEY = "java.version";

  /**
   * The System property key for the Java Virtual Machine implementation info.
   */
  public static final String JAVA_VM_INFO_KEY = "java.vm.info";

  /**
   * The System property key for the Java Virtual Machine implementation name.
   */
  public static final String JAVA_VM_NAME_KEY = "java.vm.name";

  /**
   * The System property key for the Java Virtual Machine implementation
   * version.
   */
  public static final String JAVA_VM_VERSION_KEY = "java.vm.version";

  /**
   * The System property key for the Java Virtual Machine implementation vendor.
   */
  public static final String JAVA_VM_VENDOR_KEY = "java.vm.vendor";

  /**
   * The System property key for the Java Virtual Machine specification name.
   */
  public static final String JAVA_VM_SPECIFICATION_NAME_KEY =
      "java.vm.specification.name";

  /**
   * The System property key for the Java Virtual Machine specification vendor.
   */
  public static final String JAVA_VM_SPECIFICATION_VENDOR_KEY =
      "java.vm.specification.vendor";

  /**
   * The System property key for the Java Virtual Machine specification version.
   */
  public static final String JAVA_VM_SPECIFICATION_VERSION_KEY =
      "java.vm.specification.version";

  /**
   * The System property key for the operating system architecture.
   */
  public static final String OS_ARCH_KEY = "os.arch";

  /**
   * The System property key for the operating system name.
   */
  public static final String OS_NAME_KEY = "os.name";

  /**
   * The System property key for the operating system version.
   */
  public static final String OS_VERSION_KEY = "os.version";

  /**
   * The System property key for the operating system line separator.
   */
  public static final String LINE_SEPARATOR_KEY = "line.separator";

  /**
   * The System property key for the operating system path separator.
   */
  public static final String PATH_SEPARATOR_KEY = "path.separator";

  /**
   * The System property key for the user's country code.
   */
  public static final String USER_COUNTRY_KEY = "user.country";

  /**
   * The System property key for the user's current working directory.
   */
  public static final String USER_DIR_KEY = "user.dir";

  /**
   * The System property key for the user's home directory.
   */
  public static final String USER_HOME_KEY = "user.home";

  /**
   * The System property key for the user's language code, such as
   * {@code "en"}.
   */
  public static final String USER_LANGUAGE_KEY = "user.language";

  /**
   * The System property key for the user's account name.
   */
  public static final String USER_NAME_KEY = "user.name";

  /**
   * The System property key for the user's time zone. For example:
   * {@code "America/Los_Angeles"}.
   */
  public static final String USER_TIMEZONE_KEY = "user.timezone";

  /**
   * The System property key for the AWT toolkit class name.
   */
  public static final String AWT_TOOLKIT_KEY = "awt.toolkit";

  /**
   * The System property key for the file encoding name.
   */
  public static final String FILE_ENCODING_KEY = "file.encoding";

  /**
   * The System property key for the file separator.
   */
  public static final String FILE_SEPARATOR_KEY = "file.separator";

  /**
   * The System property key for the AWT fonts.
   */
  public static final String JAVA_AWT_FONTS_KEY = "java.awt.fonts";

  /**
   * The System property key for the AWT graphic environment.
   */
  public static final String JAVA_AWT_GRAPHICSENV_KEY = "java.awt.graphicsenv";

  /**
   * The System property key for the AWT headless property. The value of this
   * property is the String {@code "true"} or {@code "false"}.
   */
  public static final String JAVA_AWT_HEADLESS_KEY = "java.awt.headless";

  /**
   * The System property key for the AWT printer job property.
   */
  public static final String JAVA_AWT_PRINTERJOB_KEY = "java.awt.printerjob";

  /**
   * The System property key for the Sun's architecture data model.
   */
  public static final String SUN_ARCH_DATA_MODEL_KEY = "sun.arch.data.model";

  /**
   * The home directory of Java.
   */
  public static final String JAVA_HOME = getProperty(JAVA_HOME_KEY);

  /**
   * The pathname of the Java IO temporary directory.
   */
  public static final String JAVA_IO_TMPDIR = getProperty(JAVA_IO_TMPDIR_KEY);

  /**
   * The version string of the Java.
   */
  public static final String JAVA_VERSION = getProperty(JAVA_VERSION_KEY);

  /**
   * Gets the Java version as a {@code String} trimming leading letters.
   *
   * <p>The field will return {@code null} if {@link #JAVA_VERSION} is
   * {@code null}.
   */
  public static final String JAVA_VERSION_TRIMMED =
      getJavaVersionTrimmed(JAVA_VERSION);

  /**
   * Gets the Java version as a {@code float}.
   *
   * <p>Example return values:
   * <ul>
   * <li>{@code 1.2f} for JDK 1.2
   * <li>{@code 1.31f} for JDK 1.3.1
   * </ul>
   *
   * <p>The field will return zero if {@link #JAVA_VERSION} is {@code null}.
   */
  public static final float JAVA_VERSION_FLOAT =
      getJavaVersionAsFloat(JAVA_VERSION);

  /**
   * Gets the Java version as an {@code int}.
   *
   * <p>Example return values:
   * <ul>
   * <li>{@code 120} for JDK 1.2
   * <li>{@code 131} for JDK 1.3.1
   * </ul>
   *
   * <p>The field will return zero if {@link #JAVA_VERSION} is {@code null}.
   */
  public static final int JAVA_VERSION_INT = getJavaVersionAsInt(JAVA_VERSION);

  /**
   * The operating system architecture.
   */
  public static final String OS_ARCH = getProperty(OS_ARCH_KEY);

  /**
   * The operating system name.
   */
  public static final String OS_NAME = getProperty(OS_NAME_KEY);

  /**
   * The operating system version.
   */
  public static final String OS_VERSION = getProperty(OS_VERSION_KEY);

  /**
   * The operating system line separator.
   *
   * <p>If it is failed to get the {@link #LINE_SEPARATOR_KEY} property value
   * from the system properties, the class will call the
   * {@link PrintWriter#println()} function to get a line separator.
   */
  public static final String LINE_SEPARATOR;

  static {
    String lineSeparator = getProperty(LINE_SEPARATOR_KEY);
    if (lineSeparator == null) {
      final StringWriter buffer = new StringWriter(4);
      final PrintWriter out = new PrintWriter(buffer);
      out.println();
      lineSeparator = buffer.toString();
    }
    LINE_SEPARATOR = lineSeparator;
  }

  /**
   * The operating system path separator.
   */
  public static final String PATH_SEPARATOR = getProperty(PATH_SEPARATOR_KEY);

  /**
   * The user's home directory pathname.
   */
  public static final String USER_HOME = getProperty(USER_HOME_KEY);

  /**
   * The user's current working directory pathname.
   */
  public static final String USER_DIR = getProperty(USER_DIR_KEY);

  /**
   * The user's language code, such as {@code "en"}.
   */
  public static final String USER_LANGUAGE = getProperty(USER_LANGUAGE_KEY);

  /**
   * The user's account name.
   */
  public static final String USER_NAME = getProperty(USER_NAME_KEY);

  /**
   * The user's time zone. For example: {@code "America/Los_Angeles"}.
   */
  public static final String USER_TIMEZONE = getProperty(USER_TIMEZONE_KEY);

  /**
   * Is {@code true} if this is Java version 1.1 (also 1.1.x versions).
   *
   * <p>The field will return {@code false} if {@link #JAVA_VERSION} is
   * {@code null}.
   */
  public static final boolean IS_JAVA_1 =
      getJavaVersionMatches(JAVA_VERSION, "1.1");

  /**
   * Is {@code true} if this is Java version 1.2 (also 1.2.x versions).
   *
   * <p>The field will return {@code false} if {@link #JAVA_VERSION} is
   * {@code null}.
   */
  public static final boolean IS_JAVA_2 =
      getJavaVersionMatches(JAVA_VERSION, "1.2");

  /**
   * Is {@code true} if this is Java version 1.3 (also 1.3.x versions).
   *
   * <p>The field will return {@code false} if {@link #JAVA_VERSION} is
   * {@code null}.
   */
  public static final boolean IS_JAVA_3 =
      getJavaVersionMatches(JAVA_VERSION, "1.3");

  /**
   * Is {@code true} if this is Java version 1.4 (also 1.4.x versions).
   *
   * <p>The field will return {@code false} if {@link #JAVA_VERSION} is
   * {@code null}.
   */
  public static final boolean IS_JAVA_4 =
      getJavaVersionMatches(JAVA_VERSION, "1.4");

  /**
   * Is {@code true} if this is Java version 5 or Java version 1.5
   * (also 1.5.x versions).
   *
   * <p>The field will return {@code false} if {@link #JAVA_VERSION} is
   * {@code null}.
   */
  public static final boolean IS_JAVA_5 =
      getJavaVersionMatches(JAVA_VERSION, "1.5");

  /**
   * Is {@code true} if this is Java version 6 or Java version 1.6
   * (also 1.6.x versions).
   *
   * <p>The field will return {@code false} if {@link #JAVA_VERSION} is
   * {@code null}.
   */
  public static final boolean IS_JAVA_6 =
      getJavaVersionMatches(JAVA_VERSION, "1.6");

  /**
   * Is {@code true} if this is Java version 7 or Java version 1.7
   * (also 1.7.x versions).
   *
   * <p>The field will return {@code false} if {@link #JAVA_VERSION} is
   * {@code null}.
   */
  public static final boolean IS_JAVA_7 =
      getJavaVersionMatches(JAVA_VERSION, "1.7");

  /**
   * Is {@code true} if this is Java version 8 or Java version 1.8
   * (also 1.8.x versions).
   *
   * <p>The field will return {@code false} if {@link #JAVA_VERSION} is
   * {@code null}.
   */
  public static final boolean IS_JAVA_8 =
      getJavaVersionMatches(JAVA_VERSION, "1.8");

  /**
   * Is {@code true} if this is Java version 9 (also 9.x.x versions).
   *
   * <p>The field will return {@code false} if {@link #JAVA_VERSION} is {@code null}.
   *
   * @see <a href="https://www.oracle.com/java/technologies/9all-relnotes.html">
   * Release Notes for JDK 9 and JDK 9 Update Releases</a>
   */
  public static final boolean IS_JAVA_9 =
      getJavaVersionMatches(JAVA_VERSION, "9.");

  /**
   * Is {@code true} if this is Java version 10 (also 10.x.x versions).
   *
   * <p>The field will return {@code false} if {@link #JAVA_VERSION} is
   * {@code null}.
   */
  public static final boolean IS_JAVA_10 =
      getJavaVersionMatches(JAVA_VERSION, "10.");

  /**
   * Is {@code true} if this is Java version 11 (also 11.x.x versions).
   *
   * <p>The field will return {@code false} if {@link #JAVA_VERSION} is
   * {@code null}.
   */
  public static final boolean IS_JAVA_11 =
      getJavaVersionMatches(JAVA_VERSION, "11.");

  /**
   * Is {@code true} if this is Java version 12 (also 12.x.x versions).
   *
   * <p>The field will return {@code false} if {@link #JAVA_VERSION} is
   * {@code null}.
   */
  public static final boolean IS_JAVA_12 =
      getJavaVersionMatches(JAVA_VERSION, "12.");

  /**
   * Is {@code true} if this is Java version 13 (also 13.x.x versions).
   *
   * <p>The field will return {@code false} if {@link #JAVA_VERSION} is
   * {@code null}.
   */
  public static final boolean IS_JAVA_13 =
      getJavaVersionMatches(JAVA_VERSION, "13.");

  /**
   * Is {@code true} if this is Java version 14 (also 14.x.x versions).
   *
   * <p>The field will return {@code false} if {@link #JAVA_VERSION} is
   * {@code null}.
   */
  public static final boolean IS_JAVA_14 =
      getJavaVersionMatches(JAVA_VERSION, "14.");

  /**
   * Is {@code true} if the current JRE is 64-bit.
   *
   * <p>FIXME: this logic may not be correct.
   */
  public static final boolean IS_JAVA_64BIT;

  static {
    final String dataModel = getProperty(SUN_ARCH_DATA_MODEL_KEY);
    if (dataModel != null) {
      IS_JAVA_64BIT = dataModel.contains("64");
    } else {
      IS_JAVA_64BIT = (OS_ARCH != null) && (OS_ARCH.contains("64"));
    }
  }

  /**
   * Is {@code true} if the current JRE is 32-bit.
   */
  public static final boolean IS_JAVA_32BIT = (! IS_JAVA_64BIT);

  /**
   * Is {@code true} if this is AIX.
   *
   * <p>The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   */
  public static final boolean IS_OS_AIX = getOsMatches(OS_NAME, "AIX");

  /**
   * Is {@code true} if this is HP-UX.
   *
   * <p>The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   */
  public static final boolean IS_OS_HP_UX = getOsMatches(OS_NAME, "HP-UX");

  /**
   * Is {@code true} if this is Irix.
   *
   * <p>The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   */
  public static final boolean IS_OS_IRIX = getOsMatches(OS_NAME, "Irix");

  /**
   * Is {@code true} if this is Linux.
   *
   * <p>The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   */
  public static final boolean IS_OS_LINUX = getOsMatches(OS_NAME, "Linux")
          || getOsMatches(OS_NAME, "LINUX");

  /**
   * Is {@code true} if this is Mac.
   *
   * <p>The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   *
   * @since 2.0
   */
  public static final boolean IS_OS_MAC = getOsMatches(OS_NAME, "Mac");

  /**
   * Is {@code true} if this is Mac.
   *
   * <p>The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   *
   * @since 2.0
   */
  public static final boolean IS_OS_MAC_OSX = getOsMatches(OS_NAME, "Mac OS X");

  /**
   * Is {@code true} if this is OS/2.
   *
   * <p>The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   *
   * @since 2.0
   */
  public static final boolean IS_OS_OS2 = getOsMatches(OS_NAME, "OS/2");

  /**
   * Is {@code true} if this is Solaris.
   *
   * <p>The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   *
   * @since 2.0
   */
  public static final boolean IS_OS_SOLARIS = getOsMatches(OS_NAME, "Solaris");

  /**
   * Is {@code true} if this is SunOS.
   *
   * <p>The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   *
   * @since 2.0
   */
  public static final boolean IS_OS_SUN_OS = getOsMatches(OS_NAME, "SunOS");

  /**
   * Is {@code true} if this is a POSIX compilant system, as in any of AIX,
   * HP-UX, Irix, Linux, MacOSX, Solaris or SUN OS.
   *
   * <p>The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   *
   * @since 2.1
   */
  public static final boolean IS_OS_UNIX = IS_OS_AIX || IS_OS_HP_UX
          || IS_OS_IRIX || IS_OS_LINUX || IS_OS_MAC_OSX || IS_OS_SOLARIS
          || IS_OS_SUN_OS;

  /**
   * Is {@code true} if this is Windows.
   *
   * <p>The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   *
   * @since 2.0
   */
  public static final boolean IS_OS_WINDOWS =
      getOsMatches(OS_NAME, OS_NAME_WINDOWS_PREFIX);

  /**
   * Is {@code true} if this is Windows 2000.
   *
   * <p>The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   *
   * @since 2.0
   */
  public static final boolean IS_OS_WINDOWS_2000 =
      getOsMatches(OS_NAME, OS_VERSION, OS_NAME_WINDOWS_PREFIX, "5.0");

  /**
   * Is {@code true} if this is Windows 95.
   *
   * <p>The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   *
   * @since 2.0
   */
  public static final boolean IS_OS_WINDOWS_95 =
      getOsMatches(OS_NAME, OS_VERSION, OS_NAME_WINDOWS_PREFIX + " 9", "4.0");
  // JDK 1.2 running on Windows98 returns 'Windows 95', hence the above

  /**
   * Is {@code true} if this is Windows 98.
   *
   * <p>The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   *
   * @since 2.0
   */
  public static final boolean IS_OS_WINDOWS_98 =
      getOsMatches(OS_NAME, OS_VERSION, OS_NAME_WINDOWS_PREFIX + " 9", "4.1");

  /**
   * Is {@code true} if this is Windows ME.
   *
   * <p>The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   */
  public static final boolean IS_OS_WINDOWS_ME =
      getOsMatches(OS_NAME, OS_VERSION, OS_NAME_WINDOWS_PREFIX, "4.9");

  /**
   * Is {@code true} if this is Windows NT.
   *
   * <p>The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   */
  public static final boolean IS_OS_WINDOWS_NT =
      getOsMatches(OS_NAME, OS_NAME_WINDOWS_PREFIX + " NT");

  /**
   * Is {@code true} if this is Windows XP.
   *
   * <p>The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   */
  public static final boolean IS_OS_WINDOWS_XP =
      getOsMatches(OS_NAME, OS_VERSION, OS_NAME_WINDOWS_PREFIX, "5.1");

  /**
   * Is {@code true} if this is Windows Vista.
   *
   * <p>The field will return {@code false} if {@code OS_NAME} is
   * {@code null}.
   */
  public static final boolean IS_OS_WINDOWS_VISTA =
      getOsMatches(OS_NAME, OS_VERSION, OS_NAME_WINDOWS_PREFIX, "6.0");

  /**
   * The native {@code ByteOrder} of this system.
   *
   * <p><b>NOTE: </b>The default byte order, specified by
   * {@link ByteArrayUtils#DEFAULT_BYTE_ORDER}, is the standard network byte
   * order, i.e., the big endian, according to the RFC 1700. The native byte
   * order specified by {@link SystemUtils#NATIVE_BYTE_ORDER}, depends on the
   * current operation system. On Windows it is usually little endian, on Mac or
   * Linux, it is usually big endian.
   */
  public static final ByteOrder NATIVE_BYTE_ORDER = ByteOrder.nativeOrder();

  /**
   * Indicates whether this platform supports unmapping mmapped files.
   */
  public static final boolean UNMAP_MMAP_SUPPORTED;

  static {
    boolean supported;
    try {
      Class.forName("sun.misc.Cleaner");
      Class.forName("java.nio.DirectByteBuffer").getMethod("cleaner");
      supported = true;
    } catch (final Exception e) {
      supported = false;
    }
    UNMAP_MMAP_SUPPORTED = supported;
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(SystemUtils.class);

  /**
   * Private constructor.
   */
  private SystemUtils() {}

  /**
   * Gets the Java version number as a {@code float}.
   *
   * <p>Example return values:
   * <ul>
   * <li>{@code 1.2f} for JDK 1.2
   * <li>{@code 1.31f} for JDK 1.3.1
   * <li>{@code }
   * </ul>
   *
   * <p>Patch releases are not reported. Zero is returned if
   * {@link #JAVA_VERSION_TRIMMED} is {@code null}.
   *
   * @param version
   *     the java version string.
   * @return the version, for example 1.31f for JDK 1.3.1
   */
  protected static float getJavaVersionAsFloat(final String version) {
    String versionTrimmed = getJavaVersionTrimmed(version);
    if (versionTrimmed == null) {
      return 0f;
    }
    // since JDK9, the java.vm.version string changes from 1.8.x to 9.x.x
    // see https://docs.oracle.com/javase/1.5.0/docs/relnotes/version-5.0.html
    // see https://www.oracle.com/java/technologies/9all-relnotes.html
    if (versionTrimmed.startsWith("1.")) {
      versionTrimmed = versionTrimmed.substring(2);
    }
    // build the version number string in the format of a float
    if (! isAsciiDigit(versionTrimmed.charAt(0))) {
      return 0;
    }
    final StringBuilder builder = new StringBuilder();
    int dotCount = 0;
    for (int i = 0; i < versionTrimmed.length(); ++i) {
      final char ch = versionTrimmed.charAt(i);
      if (ch == '.') {
        ++dotCount;
        if (dotCount == 1) {
          builder.append('.');    // append the first dot
        }
      } else if (isAsciiDigit(ch)) {
        builder.append(ch);
      } else if (i == 0) {
        return 0;   // this case should not happen
      } else {
        final char lastCh = versionTrimmed.charAt(i - 1);
        if (isAsciiDigit(lastCh)) {
          break;  // ignore the building number, e.g. 1.8.0_234
        } else {
          return 0;
        }
      }

    }
    // parse the version number string
    try {
      return Float.parseFloat(builder.toString());
    } catch (final NumberFormatException ex) {
      return 0f;
    }
  }

  /**
   * Gets the Java version number as an {@code int}.
   *
   * <p>Example return values:
   * <ul>
   * <li>{@code 120} for JDK 1.2
   * <li>{@code 131} for JDK 1.3.1
   * </ul>
   *
   * <p>Patch releases are not reported. Zero is returned if
   * {@link #JAVA_VERSION_TRIMMED} is {@code null}.
   *
   * @param version
   *     the Java version string.
   * @return the version, for example 131 for JDK 1.3.1
   */
  protected static int getJavaVersionAsInt(final String version) {
    String versionTrimmed = getJavaVersionTrimmed(version);
    if (versionTrimmed == null || versionTrimmed.length() == 0) {
      return 0;
    }
    // since JDK9, the java.vm.version string changes from 1.8.x to 9.x.x
    // see https://docs.oracle.com/javase/1.5.0/docs/relnotes/version-5.0.html
    // see https://www.oracle.com/java/technologies/9all-relnotes.html
    if (versionTrimmed.startsWith("1.")) {
      versionTrimmed = versionTrimmed.substring(2);
    }
    // build the version number string in the format of a int
    if (! isAsciiDigit(versionTrimmed.charAt(0))) {
      return 0;
    }
    final StringBuilder builder = new StringBuilder();
    int dotCount = 0;
    for (int i = 0; i < versionTrimmed.length(); ++i) {
      final char ch = versionTrimmed.charAt(i);
      if (ch == '.') {
        ++dotCount;
      } else if (isAsciiDigit(ch)) {
        builder.append(ch);
      } else if (i == 0) {
        return 0;   // this case should not happen
      } else {
        final char lastCh = versionTrimmed.charAt(i - 1);
        if (isAsciiDigit(lastCh)) {
          break;  // ignore the building number, e.g. 1.8.0_234
        } else {
          return 0;
        }
      }
    }
    if (dotCount == 0) {
      builder.append("00");
    } else if (dotCount == 1) {
      builder.append("0");
    }
    // parse the version number string
    try {
      return Integer.parseInt(builder.toString());
    } catch (final NumberFormatException ex) {
      return 0;
    }
  }

  /**
   * Trims the text of the java version to start with numbers.
   *
   * @param version
   *     the java version string.
   * @return the trimmed java version
   */
  protected static String getJavaVersionTrimmed(final String version) {
    if (version != null) {
      for (int i = 0; i < version.length(); i++) {
        final char ch = version.charAt(i);
        if ((ch >= '0') && (ch <= '9')) {
          return version.substring(i);
        }
      }
    }
    return null;
  }

  /**
   * Decides if the java version matches.
   *
   * @param version
   *     the JAVA version string.
   * @param versionPrefix
   *     the prefix for the java version
   * @return true if matches, or false if not or can't determine
   */
  protected static boolean getJavaVersionMatches(final String version,
          final String versionPrefix) {
    final String versionTrimmed = getJavaVersionTrimmed(version);
    if (versionTrimmed == null) {
      return false;
    }
    return versionTrimmed.startsWith(versionPrefix);
  }

  /**
   * Decides if the operating system matches.
   *
   * @param name
   *     the name of the operating system
   * @param namePrefix
   *     the prefix for the name of the operating system
   * @return true if matches, or false if not or can't determine
   */
  protected static boolean getOsMatches(final String name,
      final String namePrefix) {
    if (name == null) {
      return false;
    }
    return name.startsWith(namePrefix);
  }

  /**
   * Decides if the operating system matches.
   *
   * @param name
   *     the name of the operating system
   * @param version
   *     the version of the operating system
   * @param namePrefix
   *     the prefix for the name of the operating system
   * @param versionPrefix
   *     the prefix for the version of the operating system
   * @return true if matches, or false if not or can't determine
   */
  protected static boolean getOsMatches(final String name, final String version,
          final String namePrefix, final String versionPrefix) {
    if ((name == null) || (version == null)) {
      return false;
    }
    return name.startsWith(namePrefix) && version.startsWith(versionPrefix);
  }

  /**
   * Gets a System property, defaulting to {@code null} if the property cannot
   * be read.
   *
   * <p>If a {@code SecurityException} is caught, the return value is
   * {@code null} and a message is written to the log.
   *
   * @param property
   *     the system property name
   * @return the system property value or {@code null} if no such property or a
   *     security problem occurs.
   */
  public static String getProperty(final String property) {
    try {
      return System.getProperty(property);
    } catch (final SecurityException e) {
      // we are not allowed to look at this property
      LOGGER.error("Failed to read the system property {}.", property, e);
      return null;
    }
  }

  /**
   * Is the Java version at least the requested version.
   *
   * <p>Example input:
   * <ul>
   * <li>{@code 1.2f} to test for JDK 1.2</li>
   * <li>{@code 1.31f} to test for JDK 1.3.1</li>
   * </ul>
   *
   * @param requiredVersion
   *     the required version, for example 1.31f
   * @return {@code true} if the actual version is equal or greater than the
   *     required version
   */
  public static boolean isJavaVersionAtLeast(final float requiredVersion) {
    return JAVA_VERSION_FLOAT >= requiredVersion;
  }

  /**
   * Is the Java version at least the requested version.
   *
   * <p>Example input:
   * <ul>
   * <li>{@code 120} to test for JDK 1.2 or greater</li>
   * <li>{@code 131} to test for JDK 1.3.1 or greater</li>
   * </ul>
   *
   * @param requiredVersion
   *     the required version, for example 131
   * @return {@code true} if the actual version is equal or greater than the
   *     required version
   */
  public static boolean isJavaVersionAtLeast(final int requiredVersion) {
    return JAVA_VERSION_INT >= requiredVersion;
  }

  /**
   * Gets the size in bytes of the default constructed object of a specified
   * class.
   *
   * @param clazz
   *     a specified class, which must has a default constructor.
   * @return the size in bytes of the default constructed object of the
   *     specified class.
   */
  public static long getSizeOf(final Class<?> clazz) {
    final Runtime runtime = Runtime.getRuntime();
    final Object[] objects = new Object[SIZE_OF_OBJECT_COUNT];
    for (int i = 0; i < 4; ++i) {
      runGC();
    }
    final long memoryUsedBefore = runtime.totalMemory() - runtime.freeMemory();
    for (int i = 0; i < objects.length; ++i) {
      try {
        objects[i] = ConstructorUtils.newInstance(clazz);
      } catch (final Exception e) {
        LOGGER.error("Failed to create instance of " + clazz, e);
      }
    }
    for (int i = 0; i < 4; ++i) {
      runGC();
    }
    final long memoryUsedAfter = runtime.totalMemory() - runtime.freeMemory();
    return (memoryUsedAfter - memoryUsedBefore) / objects.length;
  }

  private static final int SIZE_OF_OBJECT_COUNT = 100;

  /**
   * Try to run the garbage collection of the JVM.
   */
  public static void runGC() {
    final Runtime runtime = Runtime.getRuntime();
    long usedMem1 = runtime.totalMemory() - runtime.freeMemory();
    long usedMem2;
    for (int i = 0; i < GC_LOOPS; ++i) {
      runtime.runFinalization();
      runtime.gc();
      Thread.yield();
      usedMem2 = usedMem1;
      usedMem1 = runtime.totalMemory() - runtime.freeMemory();
      if (usedMem1 >= usedMem2) {
        return;
      }
    }
  }

  private static final int GC_LOOPS = 1024;

  /**
   * Try to unmap the buffer, this method silently fails if no support for that
   * in the JVM.
   *
   * <p>On Windows, this leads to the fact, that mmapped files cannot be
   * modified or deleted.
   *
   * @param buffer
   *     the mmapped buffer.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static void cleanupMmapping(final ByteBuffer buffer)
          throws IOException {
    if (UNMAP_MMAP_SUPPORTED) {
      try {
        AccessController.doPrivileged((PrivilegedExceptionAction<Object>) () -> {
          final Class<?> bufferClass = buffer.getClass();
          final Method getCleanerMethod = bufferClass.getMethod("cleaner");
          getCleanerMethod.setAccessible(true);
          final Object cleaner = getCleanerMethod.invoke(buffer);
          if (cleaner != null) {
            cleaner.getClass().getMethod("clean").invoke(cleaner);
          }
          return null;
        });
      } catch (final PrivilegedActionException e) {
        throw new IOException("Unable to unmap the mapped buffer", e.getCause());
      }
    }
  }

  private static final String GETTING_CONTEXT_RESOURCE =
          "Getting resource {} using context class loader ...";

  private static final String GETTING_SYSTEM_RESOURCE =
          "Getting resource {} using system class loader ...";

  private static final String GETTING_CLASS_RESOURCE =
          "Getting resource {} using specified class ...";

  private static final String GETTING_CLASS_LOADER_RESOURCE =
          "Getting resource {} using specified class loader ...";

  private static final String RESOURCE_URL_IS =
          "The URL of the resource {} is: {}";

  private static final String CANNOT_FIND_RESOURCE =
          "Cannot find the resource {}.";

  /**
   * Gets the URL of a specified resource.
   *
   * @param resource
   *     the name of the specified resource.
   * @return the URL of the specified resource.
   */
  public static URL getResource(final String resource) {
    URL url = null;
    final ClassLoader loader = Thread.currentThread().getContextClassLoader();
    if (loader != null) {
      LOGGER.debug(GETTING_CONTEXT_RESOURCE, resource);
      url = loader.getResource(resource);
    }
    if (url == null) {
      LOGGER.debug(GETTING_SYSTEM_RESOURCE, resource);
      url = ClassLoader.getSystemResource(resource);
    }
    LOGGER.debug(RESOURCE_URL_IS, resource, url);
    if (url == null) {
      LOGGER.warn(CANNOT_FIND_RESOURCE, resource);
    }
    return url;
  }

  /**
   * Gets the URL of a specified resource associated with a given class.
   *
   * @param resource
   *     the name of the specified resource.
   * @param clazz
   *     the class to which the resource is associated with.
   * @return the URL of the specified resource.
   */
  public static URL getResource(final String resource, final Class<?> clazz) {
    LOGGER.debug(GETTING_CLASS_RESOURCE, resource);
    URL url = clazz.getResource(resource);
    if (url == null) {
      ClassLoader loader = clazz.getClassLoader();
      if (loader != null) {
        LOGGER.debug(GETTING_CLASS_LOADER_RESOURCE, resource);
        url = loader.getResource(resource);
      }
      if (url == null) {
        loader = Thread.currentThread().getContextClassLoader();
        if (loader != null) {
          LOGGER.debug(GETTING_CONTEXT_RESOURCE, resource);
          url = loader.getResource(resource);
        }
        if (url == null) {
          LOGGER.debug(GETTING_SYSTEM_RESOURCE, resource);
          url = ClassLoader.getSystemResource(resource);
        }
      }
    }
    LOGGER.debug(RESOURCE_URL_IS, resource, url);
    if (url == null) {
      LOGGER.warn(CANNOT_FIND_RESOURCE, resource);
    }
    return url;
  }

  /**
   * Gets the URL of a specified resource associated with a given class loader.
   *
   * @param resource
   *     the name of the specified resource.
   * @param loader
   *     the class loader to which the resource is associated with.
   * @return the URL of the specified resource.
   */
  public static URL getResource(final String resource,
      final ClassLoader loader) {
    LOGGER.debug(GETTING_CLASS_RESOURCE, resource);
    URL url = loader.getResource(resource);
    if (url == null) {
      final ClassLoader cl = Thread.currentThread().getContextClassLoader();
      if (cl != null) {
        LOGGER.debug(GETTING_CONTEXT_RESOURCE, resource);
        url = cl.getResource(resource);
      }
      if (url == null) {
        LOGGER.debug(GETTING_SYSTEM_RESOURCE, resource);
        url = ClassLoader.getSystemResource(resource);
      }
    }
    LOGGER.debug(RESOURCE_URL_IS, resource, url);
    if (url == null) {
      LOGGER.warn(CANNOT_FIND_RESOURCE, resource);
    }
    return url;
  }

  private static final int STACK_DEPTH = 20;

  /**
   * Dump all of the thread's information and stack traces.
   *
   * @return the thread's information and stack traces as a string.
   */
  public static String getThreadInfo() {
    final StringBuilder builder = new StringBuilder();
    final ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
    final boolean contention = threadBean.isThreadContentionMonitoringEnabled();
    final long[] threadIds = threadBean.getAllThreadIds();
    builder.append("Process Thread Dump:\n");
    builder.append(threadIds.length).append(" active threads\n");
    for (final long tid : threadIds) {
      final ThreadInfo info = threadBean.getThreadInfo(tid, STACK_DEPTH);
      if (info == null) {
        builder.append("  Inactive\n");
        continue;
      }
      builder.append("Thread ").append(info.getThreadId()).append(" (")
             .append(info.getThreadName()).append("):\n");
      final Thread.State state = info.getThreadState();
      builder.append("  State:         ").append(state).append('\n');
      builder.append("  Blocked count: ").append(info.getBlockedCount())
             .append('\n');
      builder.append("  Waited count:  ").append(info.getWaitedCount())
             .append('\n');
      if (contention) {
        builder.append("  Blocked time:  ").append(info.getBlockedTime())
               .append('\n');
        builder.append("  Waited time:   ").append(info.getWaitedTime())
               .append('\n');
      }
      if (state == Thread.State.WAITING) {
        builder.append("  Waiting on:    ").append(info.getLockName())
               .append('\n');
      } else if (state == Thread.State.BLOCKED) {
        builder.append("  Blocked on:    ").append(info.getLockName())
               .append('\n');
        builder.append("  Blocked by:    ").append(info.getLockOwnerId())
               .append(" (").append(info.getLockOwnerName()).append(")\n");
      }
      builder.append("  Stack:\n");
      for (final StackTraceElement frame : info.getStackTrace()) {
        builder.append("    ").append(frame.toString()).append('\n');
      }
    }
    return builder.toString();
  }

  /**
   * Gets a lazy initialized globally {@link SecureRandom} object.
   *
   * @return a lazy initialized globally {@link SecureRandom} object.
   */
  public static SecureRandom getSecureRandom() {
    return LazySecureRandom.random;
  }

  // lazy initialization of SecureRandom
  private static class LazySecureRandom {
    static final SecureRandom random = new SecureRandom();
  }

  /**
   * Gets a lazy initialized globally {@link Random} object.
   *
   * @return a lazy initialized globally {@link Random} object.
   */
  public static Random getRandom() {
    return LazyRandom.random;
  }

  // lazy initialization of Random
  private static class LazyRandom {
    static final Random random = new Random();
  }

  /**
   * Generates a random name with the specified prefix and suffix.
   *
   * @param prefix
   *     a specified prefix, which could not be null but can be an empty
   *     string.
   * @param suffix
   *     a specified suffix, which could not be null but can be an empty
   *     string.
   * @return a randomly generated temporary name.
   */
  public static String generateRandomName(final String prefix,
          final String suffix) {
    long n = LazySecureRandom.random.nextLong();
    if (n == Long.MIN_VALUE) {
      n = 0;  // corner case
    } else {
      n = Math.abs(n);
    }
    return prefix + n + suffix;
  }
}
