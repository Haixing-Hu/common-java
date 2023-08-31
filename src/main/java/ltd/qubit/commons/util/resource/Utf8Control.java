////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.resource;

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
 * @author Haixing Hu
 * @see <a href="https://gist.github.com/DemkaAge/8999236">ResourceBundle UTF-8 Control class</a>
 */
public class Utf8Control extends ResourceBundle.Control {

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
