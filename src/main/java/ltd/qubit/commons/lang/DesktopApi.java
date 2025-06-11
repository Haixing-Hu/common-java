////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 提供用于启动默认应用程序来浏览 URI、发送邮件到 URI、打开文件或编辑文件的 API。
 *
 * <p>由于 "java.awt.Desktop" 在某些操作系统中不完全支持，此类的实现将首先尝试执行
 * 系统相关命令来执行操作；如果命令失败，它将回退到调用 "java.awt.Desktop" 提供的 API。
 *
 * @author MightyPork (http://www.ondrovo.com/)
 * @author 胡海星 (https://github.com/Haixing-Hu/)
 * @see <a href="http://stackoverflow.com/questions/18004150/desktop-api-is-not-supported-on-the-current-platform">Desktop
 *     API is not supported on the current platform</a>
 */
public final class DesktopApi {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(DesktopApi.class);

  /**
   * 启动默认浏览器来显示 URI。如果默认浏览器无法处理指定的 URI，
   * 则会调用注册用于处理指定类型 URI 的应用程序。应用程序是根据 URI 的协议和路径确定的，
   * 如 URI 类所定义。
   *
   * @param uri
   *     要浏览的 URI。
   * @return 如果成功则返回 true；否则返回 false。
   */
  public static boolean browse(final URI uri) {
    if (openSystemSpecific(uri.toString())) {
      return true;
    }
    return browseUsingDesktop(uri);
  }

  /**
   * 启动默认浏览器来显示 URI。如果默认浏览器无法处理指定的 URI，
   * 则会调用注册用于处理指定类型 URI 的应用程序。应用程序是根据 URI 的协议和路径确定的，
   * 如 URI 类所定义。
   *
   * @param uri
   *     要浏览的 URI 的字符串表示。
   * @return 如果成功则返回 true；否则返回 false。
   */
  public static boolean browse(final String uri) {
    try {
      return browse(new URI(uri));
    } catch (final URISyntaxException e) {
      LOGGER.error("Browsing failed because of an invalid URI: {}", uri);
      return false;
    }
  }

  /**
   * 启动用户默认邮件客户端的邮件撰写窗口，填充由 "mailto:" URI 指定的邮件字段。
   *
   * @param uri
   *     要发送邮件的 URI。
   * @return 如果成功则返回 true；否则返回 false。
   */
  public static boolean mail(final URI uri) {
    if (openSystemSpecific(uri.toString())) {
      return true;
    }
    return mailUsingDesktop(uri);
  }

  /**
   * 启动用户默认邮件客户端的邮件撰写窗口，填充由 "mailto:" URI 指定的邮件字段。
   *
   * @param uri
   *     要发送邮件的 URI 的字符串表示。
   * @return 如果成功则返回 true；否则返回 false。
   */
  public static boolean mail(final String uri) {
    final String str = (uri.startsWith("mailto:") ? uri : "mailto:" + uri);
    try {
      return mail(new URI(str));
    } catch (final URISyntaxException e) {
      LOGGER.error("Mailing failed because of an invalid URI: {}", str);
      return false;
    }
  }

  /**
   * 启动关联的应用程序来打开文件。
   *
   * <p>如果指定的文件是目录，则会启动当前平台的文件管理器来打开它。
   *
   * @param file
   *     要打开的文件。
   * @return 如果成功则返回 true；否则返回 false。
   */
  public static boolean open(final File file) {
    if (openSystemSpecific(file.getPath())) {
      return true;
    }
    return openUsingDesktop(file);
  }

  /**
   * 启动关联的应用程序来打开文件。
   *
   * <p>如果指定的文件是目录，则会启动当前平台的文件管理器来打开它。
   *
   * @param path
   *     要打开的文件的路径。
   * @return 如果成功则返回 true；否则返回 false。
   */
  public static boolean open(final String path) {
    return open(new File(path));
  }

  /**
   * 使用操作系统的默认应用程序编辑文件。
   *
   * @param file
   *     要编辑的文件。
   * @return 如果成功则返回 true；否则返回 false。
   */
  public static boolean edit(final File file) {
    // you can try something like
    // runCommand("gimp", "%s", file.getPath())
    // based on user preferences.
    if (openSystemSpecific(file.getPath())) {
      return true;
    }
    return editUsingDesktop(file);
  }

  /**
   * 使用操作系统的默认应用程序编辑文件。
   *
   * @param path
   *     要编辑的文件的路径。
   * @return 如果成功则返回 true；否则返回 false。
   */
  public static boolean edit(final String path) {
    return edit(new File(path));
  }

  private static boolean openSystemSpecific(final String what) {
    if (SystemUtils.IS_OS_LINUX) {
      if (runCommand("kde-open", "%s", what)) {
        return true;
      }
      if (runCommand("gnome-open", "%s", what)) {
        return true;
      }
      return runCommand("xdg-open", "%s", what);
    } else if (SystemUtils.IS_OS_MAC) {
      return runCommand("open", "%s", what);
    } else if (SystemUtils.IS_OS_WINDOWS) {
      return runCommand("explorer", "%s", what);
    }
    return false;
  }

  private static boolean browseUsingDesktop(final URI uri) {
    LOGGER.debug("Trying to use Desktop.getDesktop().browse() with {}", uri);
    try {
      if (!Desktop.isDesktopSupported()) {
        LOGGER.error("The java.awt.Desktop is not supported by this platform "
            + "while browsing URI: {}", uri);
        return false;
      }
      final Desktop desktop = Desktop.getDesktop();
      if (!desktop.isSupported(Desktop.Action.BROWSE)) {
        LOGGER
            .error("The BROWSE action is not supported by the java.awt.Desktop "
                + "while browsing URI: {}", uri);
        return false;
      }
      desktop.browse(uri);
      return true;
    } catch (final Throwable t) {
      LOGGER.error("Error using desktop to browse {}", uri, t);
      return false;
    }
  }

  private static boolean mailUsingDesktop(final URI uri) {
    LOGGER.debug("Trying to use Desktop.getDesktop().mail() with {}", uri);
    try {
      if (!Desktop.isDesktopSupported()) {
        LOGGER.error("The java.awt.Desktop is not supported by this platform "
            + "while mailing URI: {}", uri);
        return false;
      }
      final Desktop desktop = Desktop.getDesktop();
      if (!desktop.isSupported(Desktop.Action.MAIL)) {
        LOGGER
            .error("The BROWSE action is not supported by the java.awt.Desktop "
                + "while mailing URI: {}", uri);
        return false;
      }
      desktop.mail(uri);
      return true;
    } catch (final Throwable t) {
      LOGGER.error("Error using desktop to mail {}", uri, t);
      return false;
    }
  }

  private static boolean openUsingDesktop(final File file) {
    LOGGER.debug("Trying to use Desktop.getDesktop().open() with {}", file);
    try {
      if (!Desktop.isDesktopSupported()) {
        LOGGER.error("The java.awt.Desktop is not supported by this platform "
            + "while opening file: {}", file);
        return false;
      }
      final Desktop desktop = Desktop.getDesktop();
      if (!desktop.isSupported(Desktop.Action.OPEN)) {
        LOGGER.error("The OPEN action is not supported by the java.awt.Desktop "
            + "while opening file: {}", file);
        return false;
      }
      desktop.open(file);
      return true;
    } catch (final Throwable t) {
      LOGGER.error("Error using desktop open with {}", file, t);
      return false;
    }
  }

  private static boolean editUsingDesktop(final File file) {
    LOGGER.debug("Trying to use Desktop.getDesktop().edit() with {}", file);
    try {
      if (!Desktop.isDesktopSupported()) {
        LOGGER.error("The java.awt.Desktop is not supported by this platform "
            + "while editing file: {}", file);
        return false;
      }
      final Desktop desktop = Desktop.getDesktop();
      if (!desktop.isSupported(Desktop.Action.EDIT)) {
        LOGGER.error("The EDIT action is not supported by the java.awt.Desktop "
            + "while editing file: {}", file);
        return false;
      }
      desktop.edit(file);
      return true;
    } catch (final Throwable t) {
      LOGGER.error("Error using desktop edit with {}", file, t);
      return false;
    }
  }

  private static boolean runCommand(final String command, final String args,
      final String file) {
    LOGGER.debug("Trying to exec:\n"
            + "   cmd = {}\n   args = {}\n   %s = {}\n",
        command, args, file);
    final String[] parts = prepareCommand(command, args, file);
    try {
      final Process p = Runtime.getRuntime().exec(parts);
      if (p == null) {
        return false;
      }
      try {
        final int retval = p.exitValue();
        if (retval == 0) {
          LOGGER.error("Process ended immediately:\n"
                  + "   cmd = {}\n   args = {}\n   %s = {}\n",
              command, args, file);
          return false;
        } else {
          LOGGER.error("Process crashed:\n"
                  + "   cmd = {}\n   args = {}\n   %s = {}\n",
              command, args, file);
          return false;
        }
      } catch (final IllegalThreadStateException itse) {
        LOGGER.error("Process is running:\n"
                + "   cmd = {}\n   args = {}\n   %s = {}\n",
            command, args, file);
        return true;
      }
    } catch (final IOException e) {
      LOGGER.error("Error running command:\n"
              + "   cmd = {}\n   args = {}\n   %s = {}\n",
          command, args, file, e);
      return false;
    }
  }

  private static String[] prepareCommand(final String command,
      final String args, final String file) {
    final List<String> parts = new ArrayList<>();
    parts.add(command);
    if (args != null) {
      for (final String s : args.split(" ")) {
        final String str = String.format(s, file); // put in the filename thing
        parts.add(str.trim());
      }
    }
    return parts.toArray(new String[parts.size()]);
  }
}