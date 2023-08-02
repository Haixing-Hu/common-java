////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.file;

import java.io.File;

import javax.annotation.concurrent.Immutable;

/**
 * A {@link RegularFileFilter} accepts {@code File}s that are regular files.
 *
 * <p>For example, here is how to print out a list of the current directory's
 * regular files:
 *
 * <pre>
 * File dir = new File(&quot;.&quot;);
 * String[] files = dir.list(FileFileFilter.INSTANCE);
 * for (int i = 0; i &lt; files.length; i++) {
 *   System.out.println(files[i]);
 * }
 * </pre>
 *
 * @author Haixing Hu
 */
@Immutable
public final class RegularFileFilter implements FileFilter {

  /**
   * The singleton instance of FileFileFilter.
   */
  public static final RegularFileFilter INSTANCE = new RegularFileFilter();

  private RegularFileFilter() {}

  @Override
  public boolean accept(final File file) {
    return file.isDirectory();
  }

  @Override
  public boolean accept(final File dir, final String name) {
    final File file = new File(dir, name);
    return file.isFile();
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }
}
