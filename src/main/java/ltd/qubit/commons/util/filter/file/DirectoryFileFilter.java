////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.file;

import java.io.File;
import javax.annotation.concurrent.Immutable;

/**
 * This filter accepts {@link File} objects which denoting the abstract path of
 * a directory.
 *
 * <p>For example, here is how to print out a list of the current directory's
 * subdirectories:
 *
 * <pre>
 * File dir = new File(&quot;.&quot;);
 * String[] files = dir.list(DirectoryFileFilter.INSTANCE);
 * for (int i = 0; i &lt; files.length; i++) {
 *   System.out.println(files[i]);
 * }
 * </pre>
 *
 * @author Haixing Hu
 */
@Immutable
public final class DirectoryFileFilter implements FileFilter {

  /**
   * The singleton instance of DirectoryFileFilter.
   */
  public static final DirectoryFileFilter INSTANCE = new DirectoryFileFilter();

  private DirectoryFileFilter() {}

  @Override
  public boolean accept(final File file) {
    return file.isDirectory();
  }

  @Override
  public boolean accept(final File dir, final String name) {
    final File file = new File(dir, name);
    return file.isDirectory();
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
