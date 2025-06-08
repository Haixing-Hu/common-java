////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.cache;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import javax.annotation.concurrent.ThreadSafe;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 一个简单的 {@link Cache} 包装器,可同步访问缓存的所有调用。
 *
 * @author 胡海星
 */
@ThreadSafe
public final class SynchronizedCache<KEY, VALUE> implements Cache<KEY, VALUE> {

  private final Cache<KEY, VALUE> cache;

  /**
   * 构造一个 {@link SynchronizedCache}。
   *
   * @param cache
   *     被包装的缓存。
   */
  public SynchronizedCache(final Cache<KEY, VALUE> cache) {
    this.cache = requireNonNull("cache", cache);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized boolean isOpened() {
    return cache.isOpened();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized void open() throws IOException {
    cache.open();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized VALUE get(final KEY key) throws IOException {
    return cache.get(key);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized Collection<VALUE> getAll() throws IOException {
    return cache.getAll();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized Set<KEY> keySet() throws IOException {
    return cache.keySet();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized boolean containsKey(final KEY key) throws IOException {
    return cache.containsKey(key);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized boolean cachesKey(final KEY key) throws IOException {
    return cache.cachesKey(key);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized void close() throws IOException {
    cache.close();
  }

}