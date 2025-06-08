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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import ltd.qubit.commons.io.error.AlreadyOpenedException;
import ltd.qubit.commons.io.error.NotOpenedException;

/**
 * 实现 {@link Cache} 接口的抽象基类。
 *
 * @author 胡海星
 */
public abstract class AbstractCache<KEY, VALUE> implements Cache<KEY, VALUE> {

  /**
   * 缓存中允许的默认最大对象数, 值为 {@value}.
   */
  public static final int DEFAULT_MAX_CACHED = Integer.MAX_VALUE;

  /**
   * 存储在内部map中的数据, 其中包含值及其最后访问时间以及访问频率.
   *
   * @author 胡海星
   */
  protected class Data {
    /**
     * 最后访问时间。
     */
    long lastAccessTime = 0;
    /**
     * 访问频率。
     */
    long accessFrequency = 0;
    /**
     * 缓存的数值。
     */
    VALUE value = null;
  }

  protected boolean opened;
  protected int maxCached;
  protected Map<KEY, Data> cached;

  /**
   * 构造一个具有默认设置的 {@link AbstractCache}。
   */
  protected AbstractCache() {
    opened = false;
    maxCached = DEFAULT_MAX_CACHED;
    cached = new HashMap<>();
  }

  /**
   * 构造一个具有指定最大缓存数量的 {@link AbstractCache}。
   *
   * @param maxCached
   *     所允许的缓存中对象的最大数量。
   */
  protected AbstractCache(final int maxCached) {
    this.opened = false;
    this.maxCached = (maxCached < 0 ? Integer.MAX_VALUE : maxCached);
    this.cached = new HashMap<>();
  }

  /**
   * 为底层的map构造一个具有指定初始容量和加载因子的 {@link AbstractCache}。
   *
   * @param intitalCapacity
   *     底层map的初始容量。
   * @param loadFactor
   *     底层map的加载因子。
   */
  protected AbstractCache(final int intitalCapacity, final float loadFactor) {
    this.opened = false;
    this.maxCached = DEFAULT_MAX_CACHED;
    this.cached = new HashMap<>(intitalCapacity, loadFactor);
  }

  /**
   * 构造一个具有指定最大大小、初始容量和加载因子的 {@link AbstractCache}。
   *
   * @param maxCached
   *     所允许的缓存中对象的最大数量。
   * @param intitalCapacity
   *     底层map的初始容量。
   * @param loadFactor
   *     底层map的加载因子。
   */
  protected AbstractCache(final int maxCached, final int intitalCapacity,
      final float loadFactor) {
    this.opened = false;
    this.maxCached = (maxCached < 0 ? Integer.MAX_VALUE : maxCached);
    this.cached = new HashMap<>(intitalCapacity, loadFactor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isOpened() {
    return opened;
  }

  @Override
  public void open() throws IOException {
    if (opened) {
      throw new AlreadyOpenedException();
    }
    doOpen();
    opened = true;
  }

  /**
   * 执行打开操作。
   *
   * <p>实现可以初始化底层数据源, 打开底层数据源, 获取部分或全部数据.
   *
   * @throws IOException
   *           如果发生任何I/O错误.
   */
  protected abstract void doOpen() throws IOException;

  @Override
  public VALUE get(final KEY key) throws IOException {
    if (! opened) {
      throw new NotOpenedException();
    }
    Data data = cached.get(key);
    if (data == null) {
      data = new Data();
      data.value = fetchValue(key);
      if (cached.size() > maxCached - 1) {
        cleanCache(cached.size() - 1);
      }
      cached.put(key, data);
    }
    ++data.accessFrequency;
    data.lastAccessTime = System.currentTimeMillis();
    return data.value;
  }

  /**
   * 从底层数据源获取指定键对应的值.
   *
   * @param key
   *          要获取的值的键.
   * @return 从底层数据源获取的指定键对应的值.
   * @throws IOException
   *           如果发生任何I/O错误.
   */
  protected abstract VALUE fetchValue(KEY key) throws IOException;

  /**
   * 清理缓存, 使其对象数量不超过期望的数量.
   *
   * <p>实现可以选择如何选择要从缓存中删除的对象.
   * @param desiredSize
   *          调用此函数后缓存中剩余的期望对象数.
   * @throws IOException
   *           如果发生任何I/O错误.
   */
  protected abstract void cleanCache(int desiredSize) throws IOException;

  @Override
  public Collection<VALUE> getAll() throws IOException {
    if (! opened) {
      throw new NotOpenedException();
    }
    return cached.values().stream().map(d -> d.value).collect(Collectors.toList());
  }

  @Override
  public Set<KEY> keySet() throws IOException {
    if (! opened) {
      throw new NotOpenedException();
    }
    return cached.keySet();
  }

  @Override
  public boolean containsKey(final KEY key) throws IOException {
    if (! opened) {
      throw new NotOpenedException();
    }
    return cached.containsKey(key);
  }

  @Override
  public boolean cachesKey(final KEY key) throws IOException {
    if (! opened) {
      throw new NotOpenedException();
    }
    return cached.containsKey(key);
  }

  @Override
  public void close() throws IOException {
    if (! opened) {
      return;
    }
    doClose();
    opened = false;
  }

  /**
   * 执行关闭操作.
   *
   * @throws IOException
   *           如果发生任何I/O错误.
   */
  protected abstract void doClose() throws IOException;

}