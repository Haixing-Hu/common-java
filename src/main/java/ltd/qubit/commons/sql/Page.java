////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * A {@link Page} is a sublist of a list of objects. It allows gain information
 * about the position of it in the containing entire list.
 *
 * @param <E>
 *     the type of entities.
 * @author Haixing Hu
 */
@Immutable
public final class Page<E> {

  /**
   * Create a page.
   *
   * @param <E>
   *     the type of elements in the page.
   * @param pageRequest
   *     the specified page request, which may be {@code null}, indicates a page
   *     with unlimited page size.
   * @param totalCount
   *     total number of results satisfying the query criterion.
   * @param content
   *     the list of query results.
   * @return
   *     the page of query results.
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

  private final long totalCount;

  private final long totalPages;

  private final int pageIndex;

  private final int pageSize;

  private final List<E> content;

  public Page() {
    totalCount = 0;
    totalPages = 0;
    pageIndex = 0;
    pageSize = 0;
    content = Collections.emptyList();
  }

  /**
   * Constructs an empty {@link Page}.
   *
   * @param pageRequest
   *     the pagination requirements of the query.
   */
  public Page(final PageRequest pageRequest) {
    this(0, 0, pageRequest.getPageIndex(), pageRequest.getPageSize(),
        Collections.emptyList());
  }

  /**
   * Constructs a {@link Page}.
   *
   * @param totalCount
   *     the total number of entities in the query result, which may be an
   *     estimation. It must be non-negative.
   * @param totalPages
   *     the estimated total number of pages in the query result, according to
   *     the specified page pageRequest. It must be non-negative.
   * @param pageRequest
   *     the pagination requirements of the query.
   * @param content
   *     the content of the query result in this page.
   */
  public Page(final long totalCount, final long totalPages,
      final PageRequest pageRequest, final List<E> content) {
    this(totalCount, totalPages, pageRequest.getPageIndex(),
        pageRequest.getPageSize(), content);
  }

  /**
   * Constructs a {@link Page}.
   *
   * @param totalCount
   *     the total number of entities in the query result, which may be an
   *     estimation. It must be non-negative.
   * @param totalPages
   *     the estimated total number of pages in the query result, according to
   *     the specified page pageRequest. It must be non-negative.
   * @param pageIndex
   *     the index of the page.
   * @param pageSize
   *     the size of the page.
   * @param content
   *     the content of the query result in this page.
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
   * Constructs a {@link Page}.
   *
   * @param <T>
   *     the type of content of the other page.
   * @param page
   *     another page object whose configuration will be copied to the new
   *     page.
   * @param content
   *     the content of the query result in this page.
   */
  public <T> Page(final Page<T> page, final List<E> content) {
    this(page.getTotalCount(), page.getTotalPages(), page.getPageIndex(),
        page.getPageSize(), content);
  }


  /**
   * Constructs a {@link Page}.
   *
   * @param <T>
   *     the type of content of the other page.
   * @param page
   *     another page object whose configuration will be copied to the new
   *     page.
   * @param mapper
   *     the function to map the content of the other page to the content of
   *     this page.
   */
  public <T> Page(final Page<T> page, final Function<T, E> mapper) {
    this(page.getTotalCount(), page.getTotalPages(), page.getPageIndex(),
        page.getPageSize(), page.getContent().stream().map(mapper).toList());
  }

  /**
   * Tests whether the content of this page is empty.
   *
   * @return
   *     {@code true} if the content of this page is empty; {@code false}
   *     otherwise.
   */
  public boolean isEmpty() {
    return content.isEmpty();
  }

  /**
   * Gets the number of entities in the content of this page.
   *
   * @return
   *     the number of entities in the content of this page.
   */
  public int size() {
    return content.size();
  }

  /**
   * Gets the total number of entities in the query result, which may be an
   * estimation.
   *
   * @return
   *     the total number of entities in the query result, which may be an
   *     estimation.
   */
  public long getTotalCount() {
    return totalCount;
  }

  /**
   * Gets the estimated total number of pages in the query result, according to
   * the specified page pageRequest.
   *
   * @return
   *     the estimated total number of pages in the query result, according
   *     to the specified page pageRequest.
   */
  public long getTotalPages() {
    return totalPages;
  }

  /**
   * Gets the index of page.
   *
   * @return
   *     the index of page.
   */
  public int getPageIndex() {
    return pageIndex;
  }

  /**
   * Gets the size of page.
   *
   * @return
   *     the size of page.
   */
  public int getPageSize() {
    return pageSize;
  }

  /**
   * Gets the content of the query result in this page.
   *
   * @return
   *     the content of the query result in this page; or an empty list if
   *     there is no content.
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
