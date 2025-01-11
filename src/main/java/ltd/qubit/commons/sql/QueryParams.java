////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import javax.annotation.Nullable;

import ltd.qubit.commons.annotation.Computed;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.reflect.impl.GetterMethod;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * The base class of query parameters.
 *
 * @author Haixing Hu
 */
public abstract class QueryParams<T> implements WithPageRequestParams, WithSortRequestParams {

  private final Class<T> entityClass;

  private final GetterMethod<T, ?> defaultSortFieldGetter;

  private final SortOrder defaultSortOrder;

  /**
   * 分页的索引，从0开始编号。若为{@code null}则默认0。
   */
  @Nullable
  protected Integer pageIndex = 0;

  /**
   * 分页中每页记录数目。若为{@code null}，当{@code pageIndex}也为{@code null}时则等价
   * 于无穷大，即在一页中返回所有指定数据列表；否则，将使用默认分页大小，其值为
   * {@value PageRequest#DEFAULT_PAGE_SIZE}。
   */
  @Nullable
  protected Integer pageSize;

  /**
   * 用于排序的属性名称（CamelCase形式）。若为{@code null}则使用默认排序。
   */
  @Nullable
  protected String sortField;

  /**
   * 指定是正序还是倒序。若为{@code null}则使用默认值。
   */
  @Nullable
  protected SortOrder sortOrder;

  /**
   * 指定空值排序选项。若为{@code null}则使用默认值。
   */
  @Nullable
  protected NullSortOption nullSortOption;

  /**
   * Creates a new instance of {@link QueryParams}.
   *
   * @param entityClass
   *     the class of the entity to be queried.
   * @param defaultSortFieldGetter
   *     the getter function of the default sorting field.
   * @param defaultSortOrder
   *     the default sorting order.
   */
  protected QueryParams(final Class<T> entityClass,
      final GetterMethod<T, ?> defaultSortFieldGetter,
      final SortOrder defaultSortOrder) {
    this.entityClass = entityClass;
    this.defaultSortFieldGetter = defaultSortFieldGetter;
    this.defaultSortOrder = defaultSortOrder;
  }

  @Override
  @Nullable
  public Integer getPageIndex() {
    return pageIndex;
  }

  public void setPageIndex(@Nullable final Integer pageIndex) {
    this.pageIndex = pageIndex;
  }

  @Override
  @Nullable
  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(@Nullable final Integer pageSize) {
    this.pageSize = pageSize;
  }

  @Override
  @Nullable
  public String getSortField() {
    return sortField;
  }

  public void setSortField(@Nullable final String sortField) {
    this.sortField = sortField;
  }

  @Override
  @Nullable
  public SortOrder getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(@Nullable final SortOrder sortOrder) {
    this.sortOrder = sortOrder;
  }

  @Override
  @Nullable
  public NullSortOption getNullSortOption() {
    return nullSortOption;
  }

  public void setNullSortOption(@Nullable final NullSortOption nullSortOption) {
    this.nullSortOption = nullSortOption;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    @SuppressWarnings("unchecked")
    final QueryParams<T> other = (QueryParams<T>) o;
    return Equality.equals(pageIndex, other.pageIndex)
        && Equality.equals(pageSize, other.pageSize)
        && Equality.equals(sortField, other.sortField)
        && Equality.equals(sortOrder, other.sortOrder)
        && Equality.equals(nullSortOption, other.nullSortOption);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, pageIndex);
    result = Hash.combine(result, multiplier, pageSize);
    result = Hash.combine(result, multiplier, sortField);
    result = Hash.combine(result, multiplier, sortOrder);
    result = Hash.combine(result, multiplier, nullSortOption);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("pageIndex", pageIndex)
        .append("pageSize", pageSize)
        .append("sortField", sortField)
        .append("sortOrder", sortOrder)
        .append("nullSortOption", nullSortOption)
        .toString();
  }

  @Computed
  public PageRequest getPageRequest() {
    return PageRequest.create(this);
  }

  @Computed
  public SortRequest<T> getSortRequest() {
    return SortRequest.create(entityClass, this, defaultSortFieldGetter, defaultSortOrder);
  }

  @Computed
  public abstract Criterion<T> getCriterion();
}
