////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNegative;
import static ltd.qubit.commons.lang.Argument.requirePositive;

/**
 * {@link PageRequest} 封装了查询的分页要求。
 *
 * @author 胡海星
 */
public final class PageRequest {

  /**
   * 默认的页面大小。
   */
  public static final int DEFAULT_PAGE_SIZE = 10;

  /**
   * 页面索引。
   */
  private int pageIndex;

  /**
   * 页面大小。
   */
  private int pageSize;

  /**
   * 使用指定的或默认的页面索引和页面大小创建 {@link PageRequest}。
   *
   * @param params
   *     分页请求的参数。
   * @return
   *     具有指定的或默认的页面索引和页面大小的 {@link PageRequest}。
   *     如果 {@link params#isRequestAll()} 为 {@code true}，则此函数将返回 {@code null}，
   *     表示应返回所有实体。
   */
  @Nullable
  public static PageRequest create(final WithPageRequestParams params) {
    if (params.isRequestAll()) {
      return null;
    }
    return create(params.getPageIndex(), params.getPageSize());
  }

  /**
   * 创建一个 {@link PageRequest}。
   *
   * @param pageIndex
   *     页面索引，或 {@code null} 使用默认值。默认值为 {@code 0}。
   * @param pageSize
   *     页面大小，或 {@code null} 使用默认值。默认值为 {@value #DEFAULT_PAGE_SIZE}。
   * @return
   *     具有指定的或默认的页面索引和页面大小的 {@link PageRequest}。
   */
  public static PageRequest create(@Nullable final Integer pageIndex,
      @Nullable final Integer pageSize) {
    if (pageIndex == null && pageSize == null) {
      return new PageRequest(0, DEFAULT_PAGE_SIZE);
    } else if (pageIndex == null) {
      return new PageRequest(pageSize);
    } else if (pageSize == null) {
      return new PageRequest(pageIndex, DEFAULT_PAGE_SIZE);
    } else {
      return new PageRequest(pageIndex, pageSize);
    }
  }

  /**
   * 构造一个请求包含所有值的页面的 {@link PageRequest}。
   *
   * <p>页面从 0 开始索引，页面大小设置为 {@link #DEFAULT_PAGE_SIZE}。
   */
  public PageRequest() {
    pageIndex = 0;
    pageSize = DEFAULT_PAGE_SIZE;
  }

  /**
   * 构造一个请求第一页的 {@link PageRequest}。
   *
   * <p>页面从 0 开始索引。
   *
   * @param pageSize
   *     每页的实体数量，必须大于零。
   * @throws IllegalArgumentException
   *     如果 {@code pageSize} 小于或等于零。
   */
  public PageRequest(final int pageSize) {
    pageIndex = 0;
    this.pageSize = requirePositive("pageSize", pageSize);
  }

  /**
   * 构造一个 {@link PageRequest}。
   *
   * <p>页面从 0 开始编号。
   *
   * @param pageIndex
   *     此查询请求中要求返回的页面索引，必须大于或等于零。
   * @param pageSize
   *     每页的实体数量，必须大于零。
   * @throws IllegalArgumentException
   *     如果 {@code pageIndex} 小于零或 {@code pageSize} 小于或等于零。
   */
  public PageRequest(final int pageIndex, final int pageSize) {
    this.pageIndex = requireNonNegative("pageIndex", pageIndex);
    this.pageSize = requirePositive("pageSize", pageSize);
  }

  /**
   * 获取页面索引。
   *
   * <p>页面索引是此查询请求中要求返回的页面索引。
   *
   * @return 页面索引。
   */
  public int getPageIndex() {
    return pageIndex;
  }

  /**
   * 设置页面索引。
   *
   * <p>页面索引是此查询请求中要求返回的页面索引。
   *
   * @param pageIndex
   *     新的页面索引。
   */
  public void setPageIndex(final int pageIndex) {
    this.pageIndex = pageIndex;
  }

  /**
   * 获取页面大小。
   *
   * <p>页面大小是每页的实体数量。
   *
   * @return 页面大小。
   */
  public int getPageSize() {
    return pageSize;
  }

  /**
   * 设置页面大小。
   *
   * <p>页面大小是每页的实体数量。
   *
   * @param pageSize
   *     新的页面大小。
   */
  public void setPageSize(final int pageSize) {
    this.pageSize = pageSize;
  }

  /**
   * 计算总页数。
   *
   * @param totalCount
   *     实体的总数。
   * @return 显示实体的总页数。
   */
  public long getTotalPages(final long totalCount) {
    return (totalCount / pageSize) + ((totalCount % pageSize) == 0 ? 0 : 1);
  }

  /**
   * 测试此分页请求的结果是否为空。
   *
   * @param totalCount
   *     实体的总数。
   * @return 如果此分页请求的结果相对于指定的实体数量为空则返回 {@code true}；否则返回 {@code false}。
   */
  public boolean isEmpty(final long totalCount) {
    return pageIndex >= getTotalPages(totalCount);
  }

  /**
   * 计算满足此分页请求的第一个实体的索引。
   *
   * <p><b>注意：</b> 在调用此函数之前，调用者<b>必须</b>确保乘法永远不会溢出。
   * 也就是说，此请求不超过实体的最大数量。
   *
   * @return 满足此分页请求的第一个实体的索引。
   */
  public long getOffset() {
    return (long) pageIndex * (long) pageSize;
  }

  /**
   * 获取下一个分页请求。
   * <p>
   * 此函数将创建一个页面索引增加一的新分页请求。这与 {@link #toNext()} 不同，
   * 后者直接修改此分页请求。
   *
   * @return 下一个分页请求。
   * @see #toNext()
   */
  public PageRequest next() {
    return new PageRequest(pageIndex + 1, pageSize);
  }

  /**
   * 将此分页请求移动到下一个分页请求。
   * <p>
   * 此函数将使此分页请求的页面索引增加一。这与 {@link #next()} 不同，
   * 后者创建一个页面索引增加一的新分页请求。
   *
   * @see #next()
   */
  public void toNext() {
    ++pageIndex;
  }

  /**
   * 获取与此分页请求对应的实体页面。
   *
   * @param <T>
   *     页面中持有的元素类型。
   * @param totalCount
   *     实体的总数。
   * @param content
   *     与此分页请求对应的实体列表。
   * @return 与此分页请求对应的实体页面。
   */
  public <T> Page<T> getPage(final long totalCount, final List<T> content) {
    return new Page<>(totalCount, getTotalPages(totalCount), this, content);
  }

  /**
   * 获取与此分页请求对应的空实体页面。
   *
   * @param <T>
   *     页面中持有的元素类型。
   * @return 与此分页请求对应的空实体页面。
   */
  public <T> Page<T> getEmptyPage() {
    final List<T> content = Collections.emptyList();
    return new Page<>(0, 0, this, content);
  }

  @Override
  public int hashCode() {
    final int multiplier = 13;
    int result = 2;
    result = Hash.combine(result, multiplier, pageIndex);
    result = Hash.combine(result, multiplier, pageSize);
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final PageRequest other = (PageRequest) obj;
    return (pageIndex == other.pageIndex)
        && (pageSize == other.pageSize);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("pageIndex", pageIndex)
        .append("pageSize", pageSize)
        .toString();
  }
}