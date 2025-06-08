////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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
 * 缓存接口。
 *
 * <p>缓存是一种工具,它从底层数据源读取数据,并将其存储在内存中以提供快速访问。
 *
 * <p>根据其缓存策略,缓存可以将所有数据存储在内存中,也可以只在内存中存储部分数据。
 *
 * <p>缓存可以一次从数据源获取一条数据,也可以一次获取一批数据。
 *
 * <p>有时,如果满足某些条件(例如,时间太长,访问频率太低等),则缓存的数据可能会被放弃。
 * 如果客户端再次请求放弃的数据,则缓存将再次从底层数据源获取数据。
 *
 * <p>实现不需要是线程安全的。为了获得线程安全的缓存,请使用{@link SynchronizedCache}来包装非线程安全的{@link Cache}对象。
 *
 * @author 胡海星
 */
public interface Cache<K, V> extends Closeable, Openable {

  /**
   * 测试此缓存是否打开。
   *
   * @return 如果此缓存已打开,则为true；否则为false。
   */
  @Override
  boolean isOpened();

  /**
   * 打开此缓存。
   *
   * <p>如果此缓存已打开,则将引发{@link IOException}。
   *
   * @throws IOException
   *     如果发生任何I/O错误。
   */
  @Override
  void open() throws IOException;

  /**
   * 从缓存中获取给定键的值。
   *
   * @param key
   *     要获取的值的键。
   * @return 对应于键的值；如果不存在这样的值,则为null。
   * @throws IOException
   *     如果发生任何I/O错误。
   */
  V get(K key) throws IOException;

  /**
   * 获取此缓存的底层数据源中的所有值。
   *
   * @return 此缓存的底层数据源中所有值的集合。返回的集合可以是简单的集合,也可以是"惰性"(即按需请求数据)集合。
   * @throws IOException
   *     如果发生任何I/O错误。
   */
  Collection<V> getAll() throws IOException;

  /**
   * 获取此缓存的底层数据源中所有值的键。
   *
   * @return 此缓存的底层数据源中所有值的键的集合。返回的集合可以是简单的集合,也可以是"惰性"(即按需请求数据)集合。
   * @throws IOException
   *     如果发生任何I/O错误。
   */
  Set<K> keySet() throws IOException;

  /**
   * 测试与给定键对应的数据是否包含在此缓存的底层数据源中。
   *
   * @param key
   *     要测试的键。
   * @return 如果与给定键对应的数据包含在此缓存的底层数据源中,则为true；否则为false。
   * @throws IOException
   *     如果发生任何I/O错误。
   */
  boolean containsKey(K key) throws IOException;

  /**
   * 测试与给定键对应的数据是否已在此缓存中缓存。
   *
   * @param key
   *     要测试的键。
   * @return 如果与给定键对应的数据已在此缓存中缓存,则为true；否则为false。
   * @throws IOException
   *     如果发生任何I/O错误。
   */
  boolean cachesKey(K key) throws IOException;

  /**
   * 关闭此缓存。
   *
   * <p>如果此缓存已关闭,则调用此函数无效。
   *
   * @throws IOException
   *     如果发生任何I/O错误。
   */
  @Override
  void close() throws IOException;
}