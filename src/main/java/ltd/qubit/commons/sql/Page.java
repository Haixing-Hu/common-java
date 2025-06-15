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
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNegative;
import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * {@link Page} 是对象列表的一个子列表。它允许获取该子列表在包含的整个列表中的位置信息。
 *
 * @param <E>
 *     实体的类型。
 * @author 胡海星
 */
@Immutable
public final class Page<E> {

  /**
   * 创建一个分页对象。
   *
   * @param <E>
   *     分页中元素的类型。
   * @param pageRequest
   *     指定的分页请求，可以为 {@code null}，表示无限制页面大小的分页。
   * @param totalCount
   *     满足查询条件的结果总数。
   * @param content
   *     查询结果的列表。
   * @return
   *     查询结果的分页对象。
   */
  @Nonnull
  public static <E> Page<E> create(@Nullable final PageRequest pageRequest,
      final long totalCount, final List<E> content) {
    if (pageRequest == null) {
      return new Page<>(totalCount, 1, 0, Integer.MAX_VALUE, content);
    } else {
      return new Page<>(totalCount, pageRequest.getTotalPages(totalCount),
          pageRequest.getPageIndex(), pageRequest.getPageSize(), content);
    }
  }

  /**
   * 查询结果中实体的总数，可能是一个估计值。
   */
  private final long totalCount;

  /**
   * 根据指定的分页请求估计的查询结果总页数。
   */
  private final long totalPages;

  /**
   * 页面索引。
   */
  private final int pageIndex;

  /**
   * 页面大小。
   */
  private final int pageSize;

  /**
   * 此页中查询结果的内容。
   */
  private final List<E> content;

  /**
   * 构造一个空的 {@link Page}。
   */
  public Page() {
    totalCount = 0;
    totalPages = 0;
    pageIndex = 0;
    pageSize = 0;
    content = Collections.emptyList();
  }

  /**
   * 构造一个空的 {@link Page}，页码索引和页面大小按照指定的分页请求设置，内容为空。
   *
   * @param pageRequest
   *     查询的分页要求。
   */
  public Page(final PageRequest pageRequest) {
    this(0, 0, pageRequest.getPageIndex(), pageRequest.getPageSize(),
        Collections.emptyList());
  }

  /**
   * 构造一个 {@link Page}。
   *
   * @param totalCount
   *     查询结果中实体的总数，可能是一个估计值。必须是非负数。
   * @param totalPages
   *     根据指定的分页请求估计的查询结果总页数。必须是非负数。
   * @param pageRequest
   *     查询的分页要求。
   * @param content
   *     此页中查询结果的内容。
   */
  public Page(final long totalCount, final long totalPages,
      final PageRequest pageRequest, final List<E> content) {
    this(totalCount, totalPages, pageRequest.getPageIndex(),
        pageRequest.getPageSize(), content);
  }

  /**
   * 构造一个 {@link Page}。
   *
   * @param totalCount
   *     查询结果中实体的总数，可能是一个估计值。必须是非负数。
   * @param totalPages
   *     根据指定的分页请求估计的查询结果总页数。必须是非负数。
   * @param pageIndex
   *     页面的索引。
   * @param pageSize
   *     页面的大小。
   * @param content
   *     此页中查询结果的内容。
   */
  public Page(final long totalCount, final long totalPages,
      final int pageIndex, final int pageSize, final List<E> content) {
    this.totalCount = requireNonNegative("totalCount", totalCount);
    this.totalPages = requireNonNegative("totalPages", totalPages);
    this.pageIndex = requireNonNegative("pageIndex", pageIndex);
    this.pageSize = requireNonNegative("pageSize", pageSize);
    this.content = requireNonNull("content", content);
  }

  /**
   * 构造一个 {@link Page}。
   *
   * @param <T>
   *     另一个分页的内容类型。
   * @param page
   *     另一个分页对象，其配置将被复制到新分页中。
   * @param content
   *     此页中查询结果的内容。
   */
  public <T> Page(final Page<T> page, final List<E> content) {
    this(page.getTotalCount(), page.getTotalPages(), page.getPageIndex(),
        page.getPageSize(), content);
  }


  /**
   * 构造一个 {@link Page}。
   *
   * @param <T>
   *     另一个分页的内容类型。
   * @param page
   *     另一个分页对象，其配置将被复制到新分页中。
   * @param mapper
   *     将另一个分页的内容映射到此分页内容的函数。
   */
  public <T> Page(final Page<T> page, final Function<T, E> mapper) {
    this(page.getTotalCount(), page.getTotalPages(), page.getPageIndex(),
        page.getPageSize(), page.getContent().stream().map(mapper).toList());
  }

  /**
   * 测试此页的内容是否为空。
   *
   * @return
   *     如果此页的内容为空则返回 {@code true}；否则返回 {@code false}。
   */
  public boolean isEmpty() {
    return content.isEmpty();
  }

  /**
   * 获取此页内容中的实体数量。
   *
   * @return
   *     此页内容中的实体数量。
   */
  public int size() {
    return content.size();
  }

  /**
   * 获取查询结果中实体的总数，可能是一个估计值。
   *
   * @return
   *     查询结果中实体的总数，可能是一个估计值。
   */
  public long getTotalCount() {
    return totalCount;
  }

  /**
   * 根据指定的分页请求获取查询结果中估计的总页数。
   *
   * @return
   *     根据指定的分页请求估计的查询结果总页数。
   */
  public long getTotalPages() {
    return totalPages;
  }

  /**
   * 获取页面索引。
   *
   * @return
   *     页面索引。
   */
  public int getPageIndex() {
    return pageIndex;
  }

  /**
   * 获取页面大小。
   *
   * @return
   *     页面大小。
   */
  public int getPageSize() {
    return pageSize;
  }

  /**
   * 获取此页中查询结果的内容。
   *
   * @return
   *     此页中查询结果的内容；如果没有内容则返回空列表。
   */
  @Nonnull
  public List<E> getContent() {
    return content;
  }

  @Override
  public int hashCode() {
    final int multiplier = 13;
    int result = 2;
    result = Hash.combine(result, multiplier, totalCount);
    result = Hash.combine(result, multiplier, totalPages);
    result = Hash.combine(result, multiplier, pageIndex);
    result = Hash.combine(result, multiplier, pageSize);
    result = Hash.combine(result, multiplier, content);
    return result;
  }

  @SuppressWarnings("unchecked")
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
    final Page<E> other = (Page<E>) obj;
    return Equality.equals(totalCount, other.totalCount)
        && Equality.equals(totalPages, other.totalPages)
        && Equality.equals(pageIndex, other.pageIndex)
        && Equality.equals(pageSize, other.pageSize)
        && Equality.equals(content, other.content);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("totalCount", totalCount)
        .append("totalPages", totalPages)
        .append("pageIndex", pageIndex)
        .append("pageSize", pageSize)
        .append("content", content)
        .toString();
  }
}