////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.cache;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import ltd.qubit.commons.io.Openable;

/**
 * The interface of a cache.
 *
 * <p>A cache is a facility which reads data from a underlying data source, and
 * store them in the memory in order to provide fast access.
 *
 * <p>The cache may store all data in the memory, or just store some amount of
 * data in the memory, depending on its caching policy.
 *
 * <p>The cache may fetch a piece of data from the data source a time, or fetch
 * a batch of data a time.
 *
 * <p>Sometime the cached data may be abandoned, if they satisfy some condition
 * (i.e., too old, too little access frequency, etc). If the client request the
 * abandoned data again, the cache will fetch the data from the underlying data
 * source again.
 *
 * <p>The implementation do NOT need to be thread-safe. In order to get a
 * thread-safe cache, use the {@link SynchronizedCache} to wrap a non
 * thread-safe {@link Cache} object.
 *
 * @author Haixing Hu
 */
public interface Cache<K, V> extends Closeable, Openable {

  /**
   * Tests whether this cache is opened.
   *
   * @return true if this cache is opened; false otherwise.
   */
  @Override
  boolean isOpened();

  /**
   * Opens this cache.
   *
   * <p>If this cache has already been opened, an {@link IOException} will be
   * thrown.
   *
   * @throws IOException
   *     if any I/O error occurred.
   */
  @Override
  void open() throws IOException;

  /**
   * Gets the value for the given key from the cache.
   *
   * @param key
   *     the key of the value to be get.
   * @return the value corresponding to the key, or null if no such value.
   * @throws IOException
   *     if any I/O error occurred.
   */
  V get(K key) throws IOException;

  /**
   * Gets all values in the underlying data source of this cache.
   *
   * @return the collection of all values in the underlying data source of this
   *     cache. The returned collection may be a simple collection, or a "lazy"
   *     (i.e., request the data on demand) collection.
   * @throws IOException
   *     if any I/O error occurred.
   */
  Collection<V> getAll() throws IOException;

  /**
   * Gets all keys of values in the underlying data source of this cache.
   *
   * @return the set of all keys of values in the underlying data source of this
   *     cache. The returned collection may be a simple set, or a "lazy" (i.e.,
   *     request the data on demand) set.
   * @throws IOException
   *     if any I/O error occurred.
   */
  Set<K> keySet() throws IOException;

  /**
   * Tests whether the data corresponding to the given key is contained in the
   * underlying data source of this cache.
   *
   * @param key
   *     the key to be test.
   * @return true if the data corresponding to the given key is contained in the
   *     underlying data source of this cache; false otherwise.
   * @throws IOException
   *     if any I/O error occurred.
   */
  boolean containsKey(K key) throws IOException;

  /**
   * Tests whether the data corresponding to the given key is cached in this
   * cache.
   *
   * @param key
   *     the key to be test.
   * @return true if the data corresponding to the given key is cached in this
   *     cache; false otherwise.
   * @throws IOException
   *     if any I/O error occurred.
   */
  boolean cachesKey(K key) throws IOException;

  /**
   * Closes this cache.
   *
   * <p>If this cache has already been closed, calling this function has no
   * effect.
   *
   * @throws IOException
   *     if any I/O error occurred.
   */
  @Override
  void close() throws IOException;
}
