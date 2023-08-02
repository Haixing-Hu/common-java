////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
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
 * Simple {@link Cache} wrapper that synchronizes all calls that access the cache.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class SynchronizedCache<KEY, VALUE> implements Cache<KEY, VALUE> {

  private final Cache<KEY, VALUE> cache;

  public SynchronizedCache(final Cache<KEY, VALUE> cache) {
    this.cache = requireNonNull("cache", cache);
  }

  @Override
  public synchronized boolean isOpened() {
    return cache.isOpened();
  }

  @Override
  public synchronized void open() throws IOException {
    cache.open();
  }

  @Override
  public synchronized VALUE get(final KEY key) throws IOException {
    return cache.get(key);
  }

  @Override
  public synchronized Collection<VALUE> getAll() throws IOException {
    return cache.getAll();
  }

  @Override
  public synchronized Set<KEY> keySet() throws IOException {
    return cache.keySet();
  }

  @Override
  public synchronized boolean containsKey(final KEY key) throws IOException {
    return cache.containsKey(key);
  }

  @Override
  public synchronized boolean cachesKey(final KEY key) throws IOException {
    return cache.cachesKey(key);
  }

  @Override
  public synchronized void close() throws IOException {
    cache.close();
  }

}
