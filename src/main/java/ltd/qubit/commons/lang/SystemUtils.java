////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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
import java.net.URL;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.concurrent.Lazy;
import ltd.qubit.commons.config.error.ConfigurationError;
import ltd.qubit.commons.io.IoUtils;
import ltd.qubit.commons.math.RandomEx;
import ltd.qubit.commons.reflect.ConstructorUtils;

import static ltd.qubit.commons.lang.CharUtils.isAsciiDigit;

/**
 * 此类为 {@code java.lang.System} 提供辅助函数。
 *
 * <p>如果由于安全限制无法读取系统属性，此类中相应的字段将被设置为 {@code null}，
 * 并且会向日志写入一条消息。
 *
 * @author 胡海星
 */
@ThreadSafe
public final class SystemUtils {

  /**
   * 所有 Windows 操作系统的前缀字符串。
   */
  private static final String OS_NAME_WINDOWS_PREFIX = "Windows";

  /**
   * Java 主目录的系统属性键。
   */
  public static final String JAVA_HOME_KEY = "java.home";

  /**
   * Java IO 临时目录的系统属性键。
   */
  public static final String JAVA_IO_TMPDIR_KEY = "java.io.tmpdir";

  /**
   * Java 类路径的系统属性键。
   */
  public static final String JAVA_CLASS_PATH_KEY = "java.class.path";

  /**
   * Java 类格式版本号的系统属性键。
   */
  public static final String JAVA_CLASS_VERSION_KEY = "java.class.version";

  /**
   * Java 编译器名称的系统属性键。
   */
  public static final String JAVA_COMPILER_KEY = "java.compiler";

  /**
   * Java 认可目录的系统属性键。
   */
  public static final String JAVA_ENDORSED_DIRS_KEY = "java.endorsed.dirs";

  /**
   * Java 扩展目录的系统属性键。
   */
  public static final String JAVA_EXT_DIRS_KEY = "java.ext.dirs";

  /**
   * 加载库时搜索路径列表的系统属性键。
   */
  public static final String JAVA_LIBRARY_PATH_KEY = "java.library.path";

  /**
   * Java 运行时环境名称的系统属性键。
   */
  public static final String JAVA_RUNTIME_NAME_KEY = "java.runtime.name";

  /**
   * Java 运行时环境版本的系统属性键。
   */
  public static final String JAVA_RUNTIME_VERSION_KEY = "java.runtime.version";

  /**
   * Java 运行时环境规范名称的系统属性键。
   */
  public static final String JAVA_SPECIFICATION_NAME_KEY =
      "java.specification.name";

  /**
   * Java 运行时环境规范版本的系统属性键。
   */
  public static final String JAVA_SPECIFICATION_VERSION_KEY =
      "java.specification.version";

  /**
   * Java 运行时环境规范供应商的系统属性键。
   */
  public static final String JAVA_SPECIFICATION_VENDOR_KEY =
      "java.specification.vendor";

  /**
   * Java PreferencesFactory 类名的系统属性键。
   */
  public static final String JAVA_PREFERENCES_FACTORY_KEY =
      "java.util.prefs.PreferencesFactory";

  /**
   * Java 供应商特定字符串的系统属性键。
   */
  public static final String JAVA_VENDOR_KEY = "java.vendor";

  /**
   * Java 供应商 URL 的系统属性键。
   */
  public static final String JAVA_VENDOR_URL_KEY = "java.vendor.url";

  /**
   * Java 版本号的系统属性键。
   */
  public static final String JAVA_VERSION_KEY = "java.version";

  /**
   * Java 虚拟机实现信息的系统属性键。
   */
  public static final String JAVA_VM_INFO_KEY = "java.vm.info";

  /**
   * Java 虚拟机实现名称的系统属性键。
   */
  public static final String JAVA_VM_NAME_KEY = "java.vm.name";

  /**
   * Java 虚拟机实现版本的系统属性键。
   */
  public static final String JAVA_VM_VERSION_KEY = "java.vm.version";

  /**
   * Java 虚拟机实现供应商的系统属性键。
   */
  public static final String JAVA_VM_VENDOR_KEY = "java.vm.vendor";

  /**
   * Java 虚拟机规范名称的系统属性键。
   */
  public static final String JAVA_VM_SPECIFICATION_NAME_KEY =
      "java.vm.specification.name";

  /**
   * Java 虚拟机规范供应商的系统属性键。
   */
  public static final String JAVA_VM_SPECIFICATION_VENDOR_KEY =
      "java.vm.specification.vendor";

  /**
   * Java 虚拟机规范版本的系统属性键。
   */
  public static final String JAVA_VM_SPECIFICATION_VERSION_KEY =
      "java.vm.specification.version";

  /**
   * 操作系统架构的系统属性键。
   */
  public static final String OS_ARCH_KEY = "os.arch";

  /**
   * 操作系统名称的系统属性键。
   */
  public static final String OS_NAME_KEY = "os.name";

  /**
   * 操作系统版本的系统属性键。
   */
  public static final String OS_VERSION_KEY = "os.version";

  /**
   * 操作系统行分隔符的系统属性键。
   */
  public static final String LINE_SEPARATOR_KEY = "line.separator";

  /**
   * 操作系统路径分隔符的系统属性键。
   */
  public static final String PATH_SEPARATOR_KEY = "path.separator";

  /**
   * 用户国家代码的系统属性键。
   */
  public static final String USER_COUNTRY_KEY = "user.country";

  /**
   * 用户当前工作目录的系统属性键。
   */
  public static final String USER_DIR_KEY = "user.dir";

  /**
   * 用户主目录的系统属性键。
   */
  public static final String USER_HOME_KEY = "user.home";

  /**
   * 用户语言代码的系统属性键，例如 {@code "en"}。
   */
  public static final String USER_LANGUAGE_KEY = "user.language";

  /**
   * 用户账户名的系统属性键。
   */
  public static final String USER_NAME_KEY = "user.name";

  /**
   * 用户时区的系统属性键。例如：{@code "America/Los_Angeles"}。
   */
  public static final String USER_TIMEZONE_KEY = "user.timezone";

  /**
   * AWT 工具包类名的系统属性键。
   */
  public static final String AWT_TOOLKIT_KEY = "awt.toolkit";

  /**
   * 文件编码名称的系统属性键。
   */
  public static final String FILE_ENCODING_KEY = "file.encoding";

  /**
   * 文件分隔符的系统属性键。
   */
  public static final String FILE_SEPARATOR_KEY = "file.separator";

  /**
   * AWT 字体的系统属性键。
   */
  public static final String JAVA_AWT_FONTS_KEY = "java.awt.fonts";

  /**
   * AWT 图形环境的系统属性键。
   */
  public static final String JAVA_AWT_GRAPHICSENV_KEY = "java.awt.graphicsenv";

  /**
   * AWT 无头属性的系统属性键。此属性的值是字符串 {@code "true"} 或 {@code "false"}。
   */
  public static final String JAVA_AWT_HEADLESS_KEY = "java.awt.headless";

  /**
   * AWT 打印作业属性的系统属性键。
   */
  public static final String JAVA_AWT_PRINTERJOB_KEY = "java.awt.printerjob";

  /**
   * Sun 架构数据模型的系统属性键。
   */
  public static final String SUN_ARCH_DATA_MODEL_KEY = "sun.arch.data.model";

  /**
   * Java 的主目录。
   */
  public static final String JAVA_HOME = getProperty(JAVA_HOME_KEY);

  /**
   * Java IO 临时目录的路径名。
   */
  public static final String JAVA_IO_TMPDIR = getProperty(JAVA_IO_TMPDIR_KEY);

  /**
   * Java 的版本字符串。
   */
  public static final String JAVA_VERSION = getProperty(JAVA_VERSION_KEY);

  /**
   * 获取去除前导字母的 Java 版本字符串。
   *
   * <p>如果 {@link #JAVA_VERSION} 是 {@code null}，则此字段将返回 {@code null}。
   */
  public static final String JAVA_VERSION_TRIMMED = getJavaVersionTrimmed(JAVA_VERSION);

  /**
   * 获取 Java 版本的 {@code float} 值。
   *
   * <p>示例返回值：
   * <ul>
   * <li>{@code 1.2f} 表示 JDK 1.2
   * <li>{@code 1.31f} 表示 JDK 1.3.1
   * </ul>
   *
   * <p>如果 {@link #JAVA_VERSION} 是 {@code null}，则此字段将返回零。
   */
  public static final float JAVA_VERSION_FLOAT = getJavaVersionAsFloat(JAVA_VERSION);

  /**
   * 获取 Java 版本的 {@code int} 值。
   *
   * <p>示例返回值：
   * <ul>
   * <li>{@code 120} 表示 JDK 1.2
   * <li>{@code 131} 表示 JDK 1.3.1
   * </ul>
   *
   * <p>如果 {@link #JAVA_VERSION} 是 {@code null}，则此字段将返回零。
   */
  public static final int JAVA_VERSION_INT = getJavaVersionAsInt(JAVA_VERSION);

  /**
   * 操作系统架构。
   */
  public static final String OS_ARCH = getProperty(OS_ARCH_KEY);

  /**
   * 操作系统名称。
   */
  public static final String OS_NAME = getProperty(OS_NAME_KEY);

  /**
   * 操作系统版本。
   */
  public static final String OS_VERSION = getProperty(OS_VERSION_KEY);

  /**
   * 操作系统行分隔符。
   *
   * <p>如果无法从系统属性中获取 {@link #LINE_SEPARATOR_KEY} 属性值，
   * 则类将调用 {@link PrintWriter#println()} 函数来获取行分隔符。
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
   * 操作系统路径分隔符。
   */
  public static final String PATH_SEPARATOR = getProperty(PATH_SEPARATOR_KEY);

  /**
   * 用户主目录路径名。
   */
  public static final String USER_HOME = getProperty(USER_HOME_KEY);

  /**
   * 用户当前工作目录路径名。
   */
  public static final String USER_DIR = getProperty(USER_DIR_KEY);

  /**
   * 用户语言代码，例如 {@code "en"}。
   */
  public static final String USER_LANGUAGE = getProperty(USER_LANGUAGE_KEY);

  /**
   * 用户账户名。
   */
  public static final String USER_NAME = getProperty(USER_NAME_KEY);

  /**
   * 用户时区。例如：{@code "America/Los_Angeles"}。
   */
  public static final String USER_TIMEZONE = getProperty(USER_TIMEZONE_KEY);

  /**
   * 如果这是 Java 版本 1.1（也包括 1.1.x 版本），则为 {@code true}。
   *
   * <p>如果 {@link #JAVA_VERSION} 是 {@code null}，则此字段将返回 {@code false}。
   */
  public static final boolean IS_JAVA_1 =
      getJavaVersionMatches(JAVA_VERSION, "1.1");

  /**
   * 如果这是 Java 版本 1.2（也包括 1.2.x 版本），则为 {@code true}。
   *
   * <p>如果 {@link #JAVA_VERSION} 是 {@code null}，则此字段将返回 {@code false}。
   */
  public static final boolean IS_JAVA_2 =
      getJavaVersionMatches(JAVA_VERSION, "1.2");

  /**
   * 如果这是 Java 版本 1.3（也包括 1.3.x 版本），则为 {@code true}。
   *
   * <p>如果 {@link #JAVA_VERSION} 是 {@code null}，则此字段将返回 {@code false}。
   */
  public static final boolean IS_JAVA_3 =
      getJavaVersionMatches(JAVA_VERSION, "1.3");

  /**
   * 如果这是 Java 版本 1.4（也包括 1.4.x 版本），则为 {@code true}。
   *
   * <p>如果 {@link #JAVA_VERSION} 是 {@code null}，则此字段将返回 {@code false}。
   */
  public static final boolean IS_JAVA_4 =
      getJavaVersionMatches(JAVA_VERSION, "1.4");

  /**
   * 如果这是 Java 版本 5 或 Java 版本 1.5（也包括 1.5.x 版本），则为 {@code true}。
   *
   * <p>如果 {@link #JAVA_VERSION} 是 {@code null}，则此字段将返回 {@code false}。
   */
  public static final boolean IS_JAVA_5 =
      getJavaVersionMatches(JAVA_VERSION, "1.5");

  /**
   * 如果这是 Java 版本 6 或 Java 版本 1.6（也包括 1.6.x 版本），则为 {@code true}。
   *
   * <p>如果 {@link #JAVA_VERSION} 是 {@code null}，则此字段将返回 {@code false}。
   */
  public static final boolean IS_JAVA_6 =
      getJavaVersionMatches(JAVA_VERSION, "1.6");

  /**
   * 如果这是 Java 版本 7 或 Java 版本 1.7（也包括 1.7.x 版本），则为 {@code true}。
   *
   * <p>如果 {@link #JAVA_VERSION} 是 {@code null}，则此字段将返回 {@code false}。
   */
  public static final boolean IS_JAVA_7 =
      getJavaVersionMatches(JAVA_VERSION, "1.7");

  /**
   * 如果这是 Java 版本 8 或 Java 版本 1.8（也包括 1.8.x 版本），则为 {@code true}。
   *
   * <p>如果 {@link #JAVA_VERSION} 是 {@code null}，则此字段将返回 {@code false}。
   */
  public static final boolean IS_JAVA_8 =
      getJavaVersionMatches(JAVA_VERSION, "1.8");

  /**
   * 如果这是 Java 版本 9（也包括 9.x.x 版本），则为 {@code true}。
   *
   * <p>如果 {@link #JAVA_VERSION} 是 {@code null}，则此字段将返回 {@code false}。
   *
   * @see <a href="https://www.oracle.com/java/technologies/9all-relnotes.html">
   * Release Notes for JDK 9 and JDK 9 Update Releases</a>
   */
  public static final boolean IS_JAVA_9 =
      getJavaVersionMatches(JAVA_VERSION, "9.");

  /**
   * 如果这是 Java 版本 10（也包括 10.x.x 版本），则为 {@code true}。
   *
   * <p>如果 {@link #JAVA_VERSION} 是 {@code null}，则此字段将返回 {@code false}。
   */
  public static final boolean IS_JAVA_10 =
      getJavaVersionMatches(JAVA_VERSION, "10.");

  /**
   * 如果这是 Java 版本 11（也包括 11.x.x 版本），则为 {@code true}。
   *
   * <p>如果 {@link #JAVA_VERSION} 是 {@code null}，则此字段将返回 {@code false}。
   */
  public static final boolean IS_JAVA_11 =
      getJavaVersionMatches(JAVA_VERSION, "11.");

  /**
   * 如果这是 Java 版本 12（也包括 12.x.x 版本），则为 {@code true}。
   *
   * <p>如果 {@link #JAVA_VERSION} 是 {@code null}，则此字段将返回 {@code false}。
   */
  public static final boolean IS_JAVA_12 =
      getJavaVersionMatches(JAVA_VERSION, "12.");

  /**
   * 如果这是 Java 版本 13（也包括 13.x.x 版本），则为 {@code true}。
   *
   * <p>如果 {@link #JAVA_VERSION} 是 {@code null}，则此字段将返回 {@code false}。
   */
  public static final boolean IS_JAVA_13 =
      getJavaVersionMatches(JAVA_VERSION, "13.");

  /**
   * 如果这是 Java 版本 14（也包括 14.x.x 版本），则为 {@code true}。
   *
   * <p>如果 {@link #JAVA_VERSION} 是 {@code null}，则此字段将返回 {@code false}。
   */
  public static final boolean IS_JAVA_14 =
      getJavaVersionMatches(JAVA_VERSION, "14.");

  /**
   * 如果当前 JRE 是 64 位，则为 {@code true}。
   *
   * <p>FIXME: 此逻辑可能不正确。
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
   * 如果当前 JRE 是 32 位，则为 {@code true}。
   */
  public static final boolean IS_JAVA_32BIT = (! IS_JAVA_64BIT);

  /**
   * 如果这是 AIX，则为 {@code true}。
   *
   * <p>如果 {@code OS_NAME} 是 {@code null}，则此字段将返回 {@code false}。
   */
  public static final boolean IS_OS_AIX = getOsMatches(OS_NAME, "AIX");

  /**
   * 如果这是 HP-UX，则为 {@code true}。
   *
   * <p>如果 {@code OS_NAME} 是 {@code null}，则此字段将返回 {@code false}。
   */
  public static final boolean IS_OS_HP_UX = getOsMatches(OS_NAME, "HP-UX");

  /**
   * 如果这是 Irix，则为 {@code true}。
   *
   * <p>如果 {@code OS_NAME} 是 {@code null}，则此字段将返回 {@code false}。
   */
  public static final boolean IS_OS_IRIX = getOsMatches(OS_NAME, "Irix");

  /**
   * 如果这是 Linux，则为 {@code true}。
   *
   * <p>如果 {@code OS_NAME} 是 {@code null}，则此字段将返回 {@code false}。
   */
  public static final boolean IS_OS_LINUX = getOsMatches(OS_NAME, "Linux")
          || getOsMatches(OS_NAME, "LINUX");

  /**
   * 如果这是 Mac，则为 {@code true}。
   *
   * <p>如果 {@code OS_NAME} 是 {@code null}，则此字段将返回 {@code false}。
   *
   * @since 2.0
   */
  public static final boolean IS_OS_MAC = getOsMatches(OS_NAME, "Mac");

  /**
   * 如果这是 Mac，则为 {@code true}。
   *
   * <p>如果 {@code OS_NAME} 是 {@code null}，则此字段将返回 {@code false}。
   *
   * @since 2.0
   */
  public static final boolean IS_OS_MAC_OSX = getOsMatches(OS_NAME, "Mac OS X");

  /**
   * 如果这是 OS/2，则为 {@code true}。
   *
   * <p>如果 {@code OS_NAME} 是 {@code null}，则此字段将返回 {@code false}。
   *
   * @since 2.0
   */
  public static final boolean IS_OS_OS2 = getOsMatches(OS_NAME, "OS/2");

  /**
   * 如果这是 Solaris，则为 {@code true}。
   *
   * <p>如果 {@code OS_NAME} 是 {@code null}，则此字段将返回 {@code false}。
   *
   * @since 2.0
   */
  public static final boolean IS_OS_SOLARIS = getOsMatches(OS_NAME, "Solaris");

  /**
   * 如果这是 SunOS，则为 {@code true}。
   *
   * <p>如果 {@code OS_NAME} 是 {@code null}，则此字段将返回 {@code false}。
   *
   * @since 2.0
   */
  public static final boolean IS_OS_SUN_OS = getOsMatches(OS_NAME, "SunOS");

  /**
   * 如果这是一个 POSIX 兼容系统，则为 {@code true}，包括 AIX、HP-UX、Irix、Linux、MacOSX、Solaris 或 SUN OS 中的任何一个。
   *
   * <p>如果 {@code OS_NAME} 是 {@code null}，则此字段将返回 {@code false}。
   *
   * @since 2.1
   */
  public static final boolean IS_OS_UNIX = IS_OS_AIX || IS_OS_HP_UX
          || IS_OS_IRIX || IS_OS_LINUX || IS_OS_MAC_OSX || IS_OS_SOLARIS
          || IS_OS_SUN_OS;

  /**
   * 如果这是 Windows，则为 {@code true}。
   *
   * <p>如果 {@code OS_NAME} 是 {@code null}，则此字段将返回 {@code false}。
   *
   * @since 2.0
   */
  public static final boolean IS_OS_WINDOWS =
      getOsMatches(OS_NAME, OS_NAME_WINDOWS_PREFIX);

  /**
   * 如果这是 Windows 2000，则为 {@code true}。
   *
   * <p>如果 {@code OS_NAME} 是 {@code null}，则此字段将返回 {@code false}。
   *
   * @since 2.0
   */
  public static final boolean IS_OS_WINDOWS_2000 =
      getOsMatches(OS_NAME, OS_VERSION, OS_NAME_WINDOWS_PREFIX, "5.0");

  /**
   * 如果这是 Windows 95，则为 {@code true}。
   *
   * <p>如果 {@code OS_NAME} 是 {@code null}，则此字段将返回 {@code false}。
   *
   * @since 2.0
   */
  public static final boolean IS_OS_WINDOWS_95 =
      getOsMatches(OS_NAME, OS_VERSION, OS_NAME_WINDOWS_PREFIX + " 9", "4.0");
  // JDK 1.2 running on Windows98 returns 'Windows 95', hence the above

  /**
   * 如果这是 Windows 98，则为 {@code true}。
   *
   * <p>如果 {@code OS_NAME} 是 {@code null}，则此字段将返回 {@code false}。
   *
   * @since 2.0
   */
  public static final boolean IS_OS_WINDOWS_98 =
      getOsMatches(OS_NAME, OS_VERSION, OS_NAME_WINDOWS_PREFIX + " 9", "4.1");

  /**
   * 如果这是 Windows ME，则为 {@code true}。
   *
   * <p>如果 {@code OS_NAME} 是 {@code null}，则此字段将返回 {@code false}。
   */
  public static final boolean IS_OS_WINDOWS_ME =
      getOsMatches(OS_NAME, OS_VERSION, OS_NAME_WINDOWS_PREFIX, "4.9");

  /**
   * 如果这是 Windows NT，则为 {@code true}。
   *
   * <p>如果 {@code OS_NAME} 是 {@code null}，则此字段将返回 {@code false}。
   */
  public static final boolean IS_OS_WINDOWS_NT =
      getOsMatches(OS_NAME, OS_NAME_WINDOWS_PREFIX + " NT");

  /**
   * 如果这是 Windows XP，则为 {@code true}。
   *
   * <p>如果 {@code OS_NAME} 是 {@code null}，则此字段将返回 {@code false}。
   */
  public static final boolean IS_OS_WINDOWS_XP =
      getOsMatches(OS_NAME, OS_VERSION, OS_NAME_WINDOWS_PREFIX, "5.1");

  /**
   * 如果这是 Windows Vista，则为 {@code true}。
   *
   * <p>如果 {@code OS_NAME} 是 {@code null}，则此字段将返回 {@code false}。
   */
  public static final boolean IS_OS_WINDOWS_VISTA =
      getOsMatches(OS_NAME, OS_VERSION, OS_NAME_WINDOWS_PREFIX, "6.0");

  /**
   * 此系统的本机 {@code ByteOrder}。
   *
   * <p><b>注意：</b>{@link ByteArrayUtils#DEFAULT_BYTE_ORDER} 指定的默认字节序是标准网络字节序，
   * 即根据 RFC 1700 的大端序。{@link SystemUtils#NATIVE_BYTE_ORDER} 指定的本机字节序，
   * 取决于当前操作系统。在 Windows 上通常是小端序，在 Mac 或 Linux 上通常是大端序。
   */
  public static final ByteOrder NATIVE_BYTE_ORDER = ByteOrder.nativeOrder();

  /**
   * 指示此平台是否支持取消映射内存映射文件。
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
   * 私有构造函数。
   */
  private SystemUtils() {}

  /**
   * 获取 Java 版本号的 {@code float} 值。
   *
   * <p>示例返回值：
   * <ul>
   * <li>{@code 1.2f} 表示 JDK 1.2
   * <li>{@code 1.31f} 表示 JDK 1.3.1
   * </ul>
   *
   * <p>不报告补丁版本。如果 {@link #JAVA_VERSION_TRIMMED} 是 {@code null}，则返回零。
   *
   * @param version
   *     Java 版本字符串。
   * @return 版本号，例如 JDK 1.3.1 返回 1.31f
   */
  static float getJavaVersionAsFloat(final String version) {
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
   * 获取 Java 版本号的 {@code int} 值。
   *
   * <p>示例返回值：
   * <ul>
   * <li>{@code 120} 表示 JDK 1.2
   * <li>{@code 131} 表示 JDK 1.3.1
   * </ul>
   *
   * <p>不报告补丁版本。如果 {@link #JAVA_VERSION_TRIMMED} 是 {@code null}，则返回零。
   *
   * @param version
   *     Java 版本字符串。
   * @return 版本号，例如 JDK 1.3.1 返回 131
   */
  static int getJavaVersionAsInt(final String version) {
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
   * 修剪 Java 版本文本以数字开头。
   *
   * @param version
   *     Java 版本字符串。
   * @return 修剪后的 Java 版本
   */
  private static String getJavaVersionTrimmed(final String version) {
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
   * 判断 Java 版本是否匹配。
   *
   * @param version
   *     Java 版本字符串。
   * @param versionPrefix
   *     Java 版本的前缀
   * @return 如果匹配则返回 true，如果不匹配或无法确定则返回 false
   */
  static boolean getJavaVersionMatches(final String version,
      final String versionPrefix) {
    final String versionTrimmed = getJavaVersionTrimmed(version);
    if (versionTrimmed == null) {
      return false;
    }
    return versionTrimmed.startsWith(versionPrefix);
  }

  /**
   * 判断操作系统是否匹配。
   *
   * @param name
   *     操作系统的名称
   * @param namePrefix
   *     操作系统名称的前缀
   * @return 如果匹配则返回 true，如果不匹配或无法确定则返回 false
   */
  static boolean getOsMatches(final String name, final String namePrefix) {
    if (name == null) {
      return false;
    }
    return name.startsWith(namePrefix);
  }

  /**
   * 判断操作系统是否匹配。
   *
   * @param name
   *     操作系统的名称
   * @param version
   *     操作系统的版本
   * @param namePrefix
   *     操作系统名称的前缀
   * @param versionPrefix
   *     操作系统版本的前缀
   * @return 如果匹配则返回 true，如果不匹配或无法确定则返回 false
   */
  static boolean getOsMatches(final String name, final String version,
      final String namePrefix, final String versionPrefix) {
    if ((name == null) || (version == null)) {
      return false;
    }
    return name.startsWith(namePrefix) && version.startsWith(versionPrefix);
  }

  /**
   * 获取系统属性，如果无法读取属性则默认返回 {@code null}。
   *
   * <p>如果捕获到 {@code SecurityException}，返回值为 {@code null} 并且会向日志写入一条消息。
   *
   * @param property
   *     系统属性名称
   * @return 系统属性值，如果没有此属性或发生安全问题则返回 {@code null}
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
   * 判断 Java 版本是否至少达到所请求的版本。
   *
   * <p>示例输入：
   * <ul>
   * <li>{@code 1.2f} 测试 JDK 1.2</li>
   * <li>{@code 1.31f} 测试 JDK 1.3.1</li>
   * </ul>
   *
   * @param requiredVersion
   *     所需的版本，例如 1.31f
   * @return 如果实际版本等于或大于所需版本则返回 {@code true}
   */
  public static boolean isJavaVersionAtLeast(final float requiredVersion) {
    return JAVA_VERSION_FLOAT >= requiredVersion;
  }

  /**
   * 判断 Java 版本是否至少达到所请求的版本。
   *
   * <p>示例输入：
   * <ul>
   * <li>{@code 120} 测试 JDK 1.2 或更高版本</li>
   * <li>{@code 131} 测试 JDK 1.3.1 或更高版本</li>
   * </ul>
   *
   * @param requiredVersion
   *     所需的版本，例如 131
   * @return 如果实际版本等于或大于所需版本则返回 {@code true}
   */
  public static boolean isJavaVersionAtLeast(final int requiredVersion) {
    return JAVA_VERSION_INT >= requiredVersion;
  }

  /**
   * 获取指定类的默认构造对象的字节大小。
   *
   * @param clazz
   *     指定的类，必须具有默认构造函数。
   * @return 指定类的默认构造对象的字节大小
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
   * 尝试运行 JVM 的垃圾回收。
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

  private static final String CLASSPATH_PREFIX = "classpath:";

  private static final String CLASSPATH_STAR_PREFIX = "classpath*:";


  /**
   * 清理资源名称，去除 classpath: 或 classpath*: 前缀。
   *
   * @param resource
   *     资源名称
   * @return 清理后的资源名称
   */
  private static String cleanResourceName(final String resource) {
    if (resource == null) {
      throw new NullPointerException("resource");
    }
    if (resource.startsWith(CLASSPATH_PREFIX)) {
      return resource.substring(CLASSPATH_PREFIX.length());
    }
    if (resource.startsWith(CLASSPATH_STAR_PREFIX)) {
      return resource.substring(CLASSPATH_STAR_PREFIX.length());
    }
    return resource;
  }

  /**
   * 获取指定资源的 URL。
   *
   * @param resource
   *     指定资源的名称。如果资源名称以 "/" 开头，则被视为绝对文件路径。
   *     如果不是，则被视为相对文件路径。如果资源名称以 "classpath:" 或
   *     "classpath*:" 开头，则去除前缀并从类路径加载资源。
   * @return 指定资源的 URL，如果未找到资源则返回 {@code null}。
   */
  @Nullable
  public static URL getResource(final String resource) {
    final String res = cleanResourceName(resource);
    LOGGER.debug(GETTING_CLASS_RESOURCE, res);
    URL url = null;
    final ClassLoader loader = Thread.currentThread().getContextClassLoader();
    if (loader != null) {
      LOGGER.debug(GETTING_CONTEXT_RESOURCE, res);
      url = loader.getResource(res);
    }
    if (url == null) {
      LOGGER.debug(GETTING_SYSTEM_RESOURCE, res);
      url = ClassLoader.getSystemResource(res);
    }
    LOGGER.debug(RESOURCE_URL_IS, res, url);
    if (url == null) {
      LOGGER.debug(CANNOT_FIND_RESOURCE, res);
    }
    return url;
  }

  /**
   * 获取与给定类关联的指定资源的 URL。
   *
   * @param resource
   *     指定资源的名称。如果资源名称以 "/" 开头，则被视为绝对文件路径。
   *     如果不是，则被视为相对文件路径。如果资源名称以 "classpath:" 或
   *     "classpath*:" 开头，则去除前缀并从类路径加载资源。
   * @param clazz
   *     与资源关联的类。
   * @return 指定资源的 URL，如果未找到资源则返回 {@code null}。
   */
  @Nullable
  public static URL getResource(final String resource, final Class<?> clazz) {
    final String res = cleanResourceName(resource);
    LOGGER.debug(GETTING_CLASS_RESOURCE, res);
    URL url = clazz.getResource(res);
    if (url == null) {
      ClassLoader loader = clazz.getClassLoader();
      if (loader != null) {
        LOGGER.debug(GETTING_CLASS_LOADER_RESOURCE, res);
        url = loader.getResource(res);
      }
      if (url == null) {
        loader = Thread.currentThread().getContextClassLoader();
        if (loader != null) {
          LOGGER.debug(GETTING_CONTEXT_RESOURCE, res);
          url = loader.getResource(res);
        }
        if (url == null) {
          LOGGER.debug(GETTING_SYSTEM_RESOURCE, res);
          url = ClassLoader.getSystemResource(res);
        }
      }
    }
    LOGGER.debug(RESOURCE_URL_IS, resource, url);
    if (url == null) {
      LOGGER.debug(CANNOT_FIND_RESOURCE, res);
    }
    return url;
  }

  /**
   * 获取与给定类加载器关联的指定资源的 URL。
   *
   * @param resource
   *     指定资源的名称。如果资源名称以 "/" 开头，则被视为绝对文件路径。
   *     如果不是，则被视为相对文件路径。如果资源名称以 "classpath:" 或
   *     "classpath*:" 开头，则去除前缀并从类路径加载资源。
   * @param loader
   *     与资源关联的类加载器。
   * @return 指定资源的 URL，如果未找到资源则返回 {@code null}。
   */
  @Nullable
  public static URL getResource(final String resource,
      final ClassLoader loader) {
    final String res = cleanResourceName(resource);
    LOGGER.debug(GETTING_CLASS_RESOURCE, res);
    URL url = loader.getResource(res);
    if (url == null) {
      final ClassLoader cl = Thread.currentThread().getContextClassLoader();
      if (cl != null) {
        LOGGER.debug(GETTING_CONTEXT_RESOURCE, res);
        url = cl.getResource(res);
      }
      if (url == null) {
        LOGGER.debug(GETTING_SYSTEM_RESOURCE, res);
        url = ClassLoader.getSystemResource(res);
      }
    }
    LOGGER.debug(RESOURCE_URL_IS, res, url);
    if (url == null) {
      LOGGER.debug(CANNOT_FIND_RESOURCE, res);
    }
    return url;
  }

  /**
   * 获取指定资源的 URL。
   *
   * @param resource
   *     指定资源的名称。如果资源名称以 "/" 开头，则被视为绝对文件路径。
   *     如果不是，则被视为相对文件路径。如果资源名称以 "classpath:" 或
   *     "classpath*:" 开头，则去除前缀并从类路径加载资源。
   * @return 指定资源的 URL。
   * @throws ConfigurationError
   *     如果未找到资源。
   */
  public static URL getResourceElseThrow(final String resource)
      throws ConfigurationError {
    final URL url = getResource(resource);
    if (url == null) {
      throw new ConfigurationError("Cannot find the resource: " + resource);
    }
    return url;
  }

  /**
   * 获取与给定类关联的指定资源的 URL。
   *
   * @param resource
   *     指定资源的名称。如果资源名称以 "/" 开头，则被视为绝对文件路径。
   *     如果不是，则被视为相对文件路径。如果资源名称以 "classpath:" 或
   *     "classpath*:" 开头，则去除前缀并从类路径加载资源。
   * @param clazz
   *     与资源关联的类。
   * @return 指定资源的 URL。
   * @throws ConfigurationError
   *     如果未找到资源。
   */
  public static URL getResourceElseThrow(final String resource, final Class<?> clazz)
      throws ConfigurationError {
    final URL url = getResource(resource, clazz);
    if (url == null) {
      throw new ConfigurationError("Cannot find the resource: " + resource);
    }
    return url;
  }

  /**
   * 获取与给定类加载器关联的指定资源的 URL。
   *
   * @param resource
   *     指定资源的名称。如果资源名称以 "/" 开头，则被视为绝对文件路径。
   *     如果不是，则被视为相对文件路径。如果资源名称以 "classpath:" 或
   *     "classpath*:" 开头，则去除前缀并从类路径加载资源。
   * @param loader
   *     与资源关联的类加载器。
   * @return 指定资源的 URL。
   * @throws ConfigurationError
   *     如果未找到资源。
   */
  public static URL getResourceElseThrow(final String resource, final ClassLoader loader)
      throws ConfigurationError {
    final URL url = getResource(resource, loader);
    if (url == null) {
      throw new ConfigurationError("Cannot find the resource: " + resource);
    }
    return url;
  }

  private static final int STACK_DEPTH = 20;

  /**
   * 转储所有线程的信息和堆栈跟踪。
   *
   * @return 线程信息和堆栈跟踪的字符串表示。
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
   * 延迟初始化的全局 {@link SecureRandom} 对象。
   */
  private static final Lazy<SecureRandom> LAZY_SECURE_RANDOM = Lazy.of(SecureRandom::new);

  /**
   * 获取一个延迟初始化的全局 {@link SecureRandom} 对象。
   *
   * @return 一个延迟初始化的全局 {@link SecureRandom} 对象。
   */
  public static SecureRandom getSecureRandom() {
    return LAZY_SECURE_RANDOM.get();
  }

  /**
   * 获取一个延迟初始化的全局 {@link RandomEx} 对象。
   *
   * @return 一个延迟初始化的全局 {@link RandomEx} 对象。
   */
  public static RandomEx getRandom() {
    return RandomEx.LAZY.get();
  }

  /**
   * 生成带有指定前缀和后缀的随机名称。
   *
   * @param prefix
   *     指定的前缀，不能为 null 但可以是空字符串。
   * @param suffix
   *     指定的后缀，不能为 null 但可以是空字符串。
   * @return 随机生成的临时名称。
   */
  public static String generateRandomName(final String prefix,
          final String suffix) {
    long n = LAZY_SECURE_RANDOM.get().nextLong();
    if (n == Long.MIN_VALUE) {
      n = 0;  // corner case
    } else {
      n = Math.abs(n);
    }
    return prefix + n + suffix;
  }

  /**
   * 返回要使用的默认 ClassLoader：通常是线程上下文 ClassLoader（如果可用）；
   * 加载 {@link SystemUtils} 类的 ClassLoader 将用作备用。
   * <p>
   * 如果您打算在明确希望非 null ClassLoader 引用的场景中使用线程上下文 ClassLoader，
   * 请调用此方法：例如，用于类路径资源加载（但不一定用于 {@code Class.forName}，
   * 它也接受 {@code null} ClassLoader 引用）。
   *
   * @return 默认的 ClassLoader（只有在连系统 ClassLoader 都无法访问时才为 {@code null}）
   * @see Thread#getContextClassLoader()
   * @see ClassLoader#getSystemClassLoader()
   */
  @Nullable
  public static ClassLoader getDefaultClassLoader() {
    ClassLoader cl = null;
    try {
      cl = Thread.currentThread().getContextClassLoader();
    } catch (final Throwable ex) {
      // Cannot access thread context ClassLoader - falling back...
    }
    if (cl == null) {
      // No thread context class loader -> use class loader of this class.
      cl = SystemUtils.class.getClassLoader();
      if (cl == null) {
        // getClassLoader() returning null indicates the bootstrap ClassLoader
        try {
          cl = ClassLoader.getSystemClassLoader();
        } catch (final Throwable ex) {
          // Cannot access system ClassLoader - oh well, maybe the caller can live with null...
        }
      }
    }
    return cl;
  }

  /**
   * 从指定资源加载实例。
   * <p>
   * 资源文件是一个文本文件，包含完全限定类名的列表，每个类名在单独的行中。
   * 此函数将加载这些类并创建它们的实例。
   *
   * @param <T>
   *     要加载的实例的类的类型。
   * @param cls
   *     实例的类。
   * @param resource
   *     指定资源的路径。
   * @param loader
   *     类加载器。
   * @return
   *     从指定资源加载的实例列表。
   */
  public static <T> List<T> loadInstance(final Class<T> cls,
      final String resource, final ClassLoader loader)
      throws IOException, ClassNotFoundException {
    final URL url = getResource(resource, loader);
    if (url == null) {
      throw new IllegalArgumentException("Cannot find resource: " + resource);
    }
    final List<String> classNames = IoUtils.readLines(url, StandardCharsets.UTF_8);
    final List<T> result = new ArrayList<>();
    for (final String className : classNames) {
      if (className.startsWith("#")) {    // skip comments
        continue;
      }
      final Class<?> clazz = Class.forName(className, true, loader);
      if (cls.isAssignableFrom(clazz)) {
        final Object instance = ConstructorUtils.newInstance(clazz);
        result.add(cls.cast(instance));
      }
    }
    return result;
  }
}