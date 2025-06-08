////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.file;

import java.io.File;

import ltd.qubit.commons.util.filter.Filter;

/**
 * An implementation of {@link FileFilter} interface is a filter used to filter
 * the abstract file pathname.
 *
 * <p>Note that it also extends the {@link java.io.FilenameFilter} in the
 * standard JDK.
 *
 * @author Haixing Hu
 */
@FunctionalInterface
public interface FileFilter extends Filter<File>, java.io.FileFilter,
    java.io.FilenameFilter {

  /**
   * Constructs a new filter which accepts all objects that rejected by the
   * specified filter.
   *
   * @param filter
   *     the specified filter.
   * @return
   *     a new filter which accepts all objects that rejected by the specified
   *     filter.
   */
  static FileFilter not(final FileFilter filter) {
    return (f) -> (!filter.accept(f));
  }

  /**
   * Constructs a new filter which accepts all objects that accepted by both of
   * the specified filters.
   *
   * @param filter1
   *     the first specified filter.
   * @param filter2
   *     the second specified filter.
   * @return
   *     a new filter which accepts all objects that accepted by both of the
   *     specified filters.
   */
  static FileFilter and(final FileFilter filter1, final FileFilter filter2) {
    return (f) -> (filter1.accept(f) && filter2.accept(f));
  }

  /**
   * Constructs a new filter which accepts all objects that accepted by any of
   * the specified filters.
   *
   * @param filter1
   *     the first specified filter.
   * @param filter2
   *     the second specified filter.
   * @return
   *     a new filter which accepts all objects that accepted by any of the
   *     specified filters.
   */
  static FileFilter or(final FileFilter filter1, final FileFilter filter2) {
    return (f) -> (filter1.accept(f) || filter2.accept(f));
  }

  /**
   * Tests whether the specified abstract pathname should be included in
   * a pathname list.
   *
   * @param file
   *     The abstract pathname to be tested
   * @return
   *     {@code true} if and only if {@code pathname} should be included.
   */
  @Override
  boolean accept(File file);

  /**
   * Tests if a specified file should be included in a file list.
   *
   * @param dir
   *     the directory in which the file was found.
   * @param name
   *     the name of the file.
   * @return
   *     {@code true} if and only if the name should be included in the file
   *     list; {@code false} otherwise.
   */
  @Override
  default boolean accept(final File dir, final String name) {
    return accept(new File(dir, name));
  }
}