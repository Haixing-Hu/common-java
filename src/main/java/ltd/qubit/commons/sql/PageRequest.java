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
 * A {@link PageRequest} encapsulates the pagination requirements of a query.
 *
 * @author Haixing Hu
 */
public final class PageRequest {

  /**
   * The default page size.
   */
  public static final int DEFAULT_PAGE_SIZE = 10;

  private int pageIndex;

  private int pageSize;

  /**
   * Creates a {@link PageRequest} with the specified or default page index and
   * page size.
   *
   * @param params
   *     the parameters of the page request.
   * @return
   *     a {@link PageRequest} with the specified or default page index and page
   *     size. If {@link params#isRequestAll()} is {@code true}, then this
   *     function will return {@code null}, indicating that all entities should
   *     be returned.
   */
  @Nullable
  public static PageRequest create(final WithPageRequestParams params) {
    if (params.isRequestAll()) {
      return null;
    }
    return create(params.getPageIndex(), params.getPageSize());
  }

  /**
   * Creates a {@link PageRequest}.
   *
   * @param pageIndex
   *     the page index, or {@code null} to use the default value. The default
   *     value is {@code 0}.
   * @param pageSize
   *     the page size, or {@code null} to use the default value. The default
   *     value is {@value #DEFAULT_PAGE_SIZE}.
   * @return
   *     a {@link PageRequest} with the specified or default page index and page
   *     size.
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
   * Constructs a {@link PageRequest} requesting the page contains all values.
   *
   * <p>The pages are indexed from 0, and the page size is set to
   * {@link #DEFAULT_PAGE_SIZE}.
   */
  public PageRequest() {
    pageIndex = 0;
    pageSize = DEFAULT_PAGE_SIZE;
  }

  /**
   * Constructs a {@link PageRequest} requesting the first page.
   *
   * <p>The pages are indexed from 0.
   *
   * @param pageSize
   *     the number of entities of each page, which must be greater than zero.
   * @throws IllegalArgumentException
   *     if the {@code pageSize} is less than or equal to zero.
   */
  public PageRequest(final int pageSize) {
    pageIndex = 0;
    this.pageSize = requirePositive("pageSize", pageSize);
  }

  /**
   * Constructs a {@link PageRequest}.
   *
   * <p>The pages are numbered from 0.
   *
   * @param pageIndex
   *     the index of the page required to be returned in this query request,
   *     which must be greater than or equal to zero.
   * @param pageSize
   *     the number of entities of each page, which must be greater than zero.
   * @throws IllegalArgumentException
   *     if the {@code pageIndex} is less than zero or the {@code pageSize} is
   *     less than or equal to zero.
   */
  public PageRequest(final int pageIndex, final int pageSize) {
    this.pageIndex = requireNonNegative("pageIndex", pageIndex);
    this.pageSize = requirePositive("pageSize", pageSize);
  }

  /**
   * Gets the page index.
   *
   * <p>The page index is the index of the page required to be returned in this
   * query request.
   *
   * @return the page index.
   */
  public int getPageIndex() {
    return pageIndex;
  }

  /**
   * Sets the page index.
   *
   * <p>The page index is the index of the page required to be returned in this
   * query request.
   *
   * @param pageIndex
   *     the new page index.
   */
  public void setPageIndex(final int pageIndex) {
    this.pageIndex = pageIndex;
  }

  /**
   * Gets the page size.
   *
   * <p>The page size is number of entities of each page.
   *
   * @return the page size.
   */
  public int getPageSize() {
    return pageSize;
  }

  /**
   * Sets the page size.
   *
   * <p>The page size is number of entities of each page.
   *
   * @param pageSize
   *     the new page size.
   */
  public void setPageSize(final int pageSize) {
    this.pageSize = pageSize;
  }

  /**
   * Calculates the total number of pages.
   *
   * @param totalCount
   *     the total number of entities.
   * @return the total number of pages for displaying the entities.
   */
  public long getTotalPages(final long totalCount) {
    return (totalCount / pageSize) + ((totalCount % pageSize) == 0 ? 0 : 1);
  }

  /**
   * Tests whether the result of this page request is empty.
   *
   * @param totalCount
   *     the total number of entities.
   * @return {@code true} if the result of this page request is empty respects
   *     to the specified number of entities; {@code false} otherwise.
   */
  public boolean isEmpty(final long totalCount) {
    return pageIndex >= getTotalPages(totalCount);
  }

  /**
   * Calculates the index of the first entity satisfied this page request.
   *
   * <p><b>NOTE:</b> before calling this function, the caller <b>MUST</b> make
   * sure
   * that the multiplication will never overflows. That is, this request does
   * not exceed the maximum number of entities.
   *
   * @return the index of the first entity satisfied this page request.
   */
  public long getOffset() {
    return (long) pageIndex * (long) pageSize;
  }

  /**
   * Gets the next page request.
   * <p>
   * This function will create a new page request with the page index increased
   * by one. It is different from {@link #toNext()} which will modify this page
   * request directly.
   *
   * @return the next page request.
   * @see #toNext()
   */
  public PageRequest next() {
    return new PageRequest(pageIndex + 1, pageSize);
  }

  /**
   * Moves this page request the next page request.
   * <p>
   * This function will increase the page index of this page request by one.
   * It is different from {@link #next()} which will create a new page request
   * with the page index increased by one.
   *
   * @see #next()
   */
  public void toNext() {
    ++pageIndex;
  }

  /**
   * Gets the page of entities corresponds to this page request.
   *
   * @param <T>
   *     the type of the elements hold in the page.
   * @param totalCount
   *     the total number of entities.
   * @param content
   *     the list of entities corresponds to this page request.
   * @return the page of entities corresponds to this page request.
   */
  public <T> Page<T> getPage(final long totalCount, final List<T> content) {
    return new Page<>(totalCount, getTotalPages(totalCount), this, content);
  }

  /**
   * Gets an empty page of entities corresponds to this page request.
   *
   * @param <T>
   *     the type of the elements hold in the page.
   * @return an empty page of entities corresponds to this page request.
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