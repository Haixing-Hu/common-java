////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.i18n.bundle;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 支持UTF-8编码的 .properties 文件的 {@code ResourceBundle.Control}.
 *
 * @author 胡海星
 * @see <a href="https://gist.github.com/DemkaAge/8999236">ResourceBundle UTF-8 Control class</a>
 */
public class Utf8Control extends ResourceBundle.Control {

  /**
   * 创建一个新的资源包实例，该实例支持 UTF-8 编码的 .properties 文件。
   *
   * @param baseName
   *     资源包的基础名称
   * @param locale
   *     期望的区域设置
   * @param format
   *     资源包格式
   * @param loader
   *     用于加载资源包的类加载器
   * @param reload
   *     是否重新加载资源包
   * @return 新的资源包实例，如果找不到资源则返回 null
   * @throws IOException
   *     如果读取资源时发生 I/O 错误
   */
  public ResourceBundle newBundle(final String baseName, final Locale locale,
      final String format, final ClassLoader loader, final boolean reload)
      throws IOException {
    // The below code is copied from default Control#newBundle() implementation.
    // Only the PropertyResourceBundle line is changed to read the file as UTF-8.
    final String bundleName = toBundleName(baseName, locale);
    final String resourceName = toResourceName(bundleName, "properties");
    ResourceBundle bundle = null;
    InputStream stream = null;
    if (reload) {
      final URL url = loader.getResource(resourceName);
      if (url != null) {
        final URLConnection connection = url.openConnection();
        if (connection != null) {
          connection.setUseCaches(false);
          stream = connection.getInputStream();
        }
      }
    } else {
      stream = loader.getResourceAsStream(resourceName);
    }
    if (stream != null) {
      try {
        bundle = new PropertyResourceBundle(new InputStreamReader(stream, UTF_8));
      } finally {
        stream.close();
      }
    }
    return bundle;
  }

}