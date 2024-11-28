////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.testbed;

import java.time.Instant;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonProperty;

import ltd.qubit.commons.annotation.Computed;
import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.annotation.Reference;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.Replacer;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 此模型表示字典项基本信息。
 *
 * @author 胡海星
 */
public class DictEntryInfo implements WithInfo, Deletable, Emptyful,
    Normalizable, Assignable<DictEntryInfo> {

  private static final long serialVersionUID = -8877480796401433733L;

  /**
   * 唯一标识。
   */
  @Identifier
  private Long id;

  /**
   * 编码。
   */
  @Size(min = 1, max = 64)
  private String code;

  /**
   * 名称。
   */
  @Size(min = 1, max = 128)
  private String name;

  /**
   * 所属字典的ID。
   */
  @Reference(entity = Dict.class, property = "id")
  private Long dictId;

  /**
   * 附加参数。
   */
  @Size(min = 1, max = 5)
  @Nullable
  private String[] params;

  /**
   * 标记删除时间。
   */
  @Nullable
  private Instant deleteTime;

  /**
   * 创建一个{@link DictEntryInfo}对象。
   *
   * @param id
   *     对象ID，可以为{@code null}.
   * @param code
   *     对象编码，可以为{@code null}.
   * @return 若{@code id}，{@code code}不全为{@code null}，则返回一个指定ID、编码
   *     的 {@link DictEntryInfo}对象，否则返回{@code null}.
   */
  public static DictEntryInfo create(@Nullable final Long id,
      @Nullable final String code) {
    if (id == null && code == null) {
      return null;
    } else {
      return new DictEntryInfo(id, code);
    }
  }

  /**
   * 创建一个{@link DictEntryInfo}对象。
   *
   * @param id
   *     对象ID，可以为{@code null}.
   * @param code
   *     对象编码，可以为{@code null}.
   * @param name
   *     对象名称，可以为{@code null}.
   * @return 若{@code id}，{@code code}和{@code name}不全为{@code null}，则返回一个
   *     指定ID、编码和名称的{@link DictEntryInfo}对象，否则返回{@code null}.
   */
  public static DictEntryInfo create(@Nullable final Long id,
      @Nullable final String code, @Nullable final String name) {
    if (id == null && code == null && name == null) {
      return null;
    } else {
      return new DictEntryInfo(id, code, name);
    }
  }

  public DictEntryInfo() {
    // empty
  }

  public DictEntryInfo(final Long id, final String code) {
    this.id = requireNonNull("id", id);
    this.code = requireNonNull("code", code);
  }

  public DictEntryInfo(final Long id, final String code,
      @Nullable final String name) {
    this.id = requireNonNull("id", id);
    this.code = requireNonNull("code", code);
    this.name = requireNonNull("name", name);
  }

  public DictEntryInfo(final DictEntry entry) {
    this.id = entry.getId();
    this.code = entry.getCode();
    this.name = entry.getName();
    this.dictId = (entry.getDict() == null ? null : entry.getDict().getId());
    this.deleteTime = entry.getDeleteTime();
  }

  public DictEntryInfo(final DictEntryInfo other) {
    assign(other);
  }

  @Override
  public void assign(final DictEntryInfo other) {
    requireNonNull("other", other);
    id = other.id;
    code = other.code;
    name = other.name;
    dictId = other.dictId;
    params = Assignment.clone(other.params);
    deleteTime = other.deleteTime;
  }

  @Override
  public DictEntryInfo cloneEx() {
    return new DictEntryInfo(this);
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(final String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public Long getDictId() {
    return dictId;
  }

  public void setDictId(final Long dictId) {
    this.dictId = dictId;
  }

  @Nullable
  public String[] getParams() {
    return params;
  }

  public void setParams(@Nullable final String[] params) {
    this.params = params;
  }

  @Nullable
  public Instant getDeleteTime() {
    return deleteTime;
  }

  public void setDeleteTime(@Nullable final Instant deleteTime) {
    this.deleteTime = deleteTime;
  }

  /**
   * 获取该字典项的显示代码。
   *
   * <p>若该字典项的参数{@link #params}为空，则其显示代码即其代码{@link #code}；否则，
   * 依次用其参数{@link #params}中的每一项替换其代码中的占位符 "{0}"，"{1}", "{2}"，
   * ……，可得到其显示代码。
   *
   * <p>例如，西药使用频次字典中某字典项如下：
   * <ul>
   *   <li>编码：{0}W{1}D</li>
   *   <li>名称：每{0}星期使用{1}天</li>
   *   <li>参数：{"1", "2"} </li>
   * </ul>
   *
   * <p>则其显示编码格式化为"1W2D"，其显示名称格式化为"每1星期使用2天"。
   *
   * @return 该字典项的显示代码。
   */
  @JsonProperty
  @XmlElement
  @Computed({"code", "params"})
  public String getDisplayCode() {
    if (params == null) {
      return code;
    } else {
      return formatWithParams(code);
    }
  }

  /**
   * 设置显示编码。
   *
   * <p>此函数不起任何作用，仅为了能正确被框架进行XML和JSON序列化而提供此函数。</p>
   *
   * @param displayCode
   *     待设置的显示编码。
   */
  @XmlElement
  public void setDisplayCode(final String displayCode) {
    // do nothing
  }

  /**
   * 获取该字典项的显示名称。
   *
   * <p>若该字典项的参数{@link #params}为空，则其显示名称即其名称{@link #name}；否则，
   * 依次用其参数{@link #params}中的每一项替换其名称中的占位符 "{0}"，"{1}", "{2}"，
   * ……，可得到其显示名称。
   *
   * <p>例如，西药使用频次字典中某字典项如下：
   * <ul>
   *   <li>编码：{0}W{1}D</li>
   *   <li>名称：每{0}星期使用{1}天</li>
   *   <li>参数：{"1", "2"} </li>
   * </ul>
   *
   * <p>则其显示编码格式化为"1W2D"，其显示名称格式化为"每1星期使用2天"。
   *
   * @return 该字典项的显示名称。
   */
  @JsonProperty
  @XmlElement
  @Computed({"name", "params"})
  public String getDisplayName() {
    if (params == null) {
      return name;
    } else {
      return formatWithParams(name);
    }
  }

  /**
   * 设置显示名称。
   *
   * <p>此函数不起任何作用，仅为了能正确被框架进行XML和JSON序列化而提供此函数。</p>
   *
   * @param displayName
   *     待设置的显示名称。
   */
  @XmlElement
  public void setDisplayName(final String displayName) {
    // do nothing
  }

  private String formatWithParams(final String str) {
    if (params == null) {
      return str;
    }
    final Replacer replacer = new Replacer().ignoreCase(false);
    String result = str;
    for (int i = 0; i < params.length; ++i) {
      final String placeholder = "{" + i + "}";
      result = replacer.searchForSubstring(placeholder)
          .replaceWithString(params[i])
          .applyTo(result);

    }
    return result;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final DictEntryInfo other = (DictEntryInfo) o;
    return Equality.equals(id, other.id)
        && Equality.equals(code, other.code)
        && Equality.equals(name, other.name)
        && Equality.equals(dictId, other.dictId)
        && Equality.equals(params, other.params)
        && Equality.equals(deleteTime, other.deleteTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, code);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, dictId);
    result = Hash.combine(result, multiplier, params);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("code", code)
        .append("name", name)
        .append("dictId", dictId)
        .append("params", params)
        .append("deleteTime", deleteTime)
        .toString();
  }
}
