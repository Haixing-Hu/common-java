////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

import java.io.Serializable;
import java.time.Instant;

import javax.annotation.Nullable;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

/**
 * 此模型表示实体的类别.
 *
 * @author 胡海星
 */
@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonAutoDetect(fieldVisibility = ANY,
                getterVisibility = NONE,
                isGetterVisibility = NONE,
                setterVisibility = NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category implements Serializable {

  private static final long serialVersionUID = 8069770708740141238L;

  /**
   * 用于连接类别显示名称的字符串.
   */
  public static final String TITLE_JOINER = " - ";

  /**
   * 唯一标识，系统自动生成.
   */
  private Long id;

  /**
   * 所属 App 的基本信息.
   */
  @Nullable
  private Info app;

  /**
   * 该类别所属实体.
   */
  private String entity;


  /**
   * 编码，同一App下同一实体的类别编码不可重复.
   */
  private String code;

  /**
   * 名称，同一App下同一实体的类别名称不可重复.
   */
  private String name;

  /**
   * 图标.
   */
  @Nullable
  private String icon;

  /**
   * 描述.
   */
  @Nullable
  private String description;

  /**
   * 显示标题。
   *
   * <p>若当前类别没有父类别，则显示标题为当前类别的名称；否则显示标题定义为：
   *
   * <pre>
   * [父类别标题] "-" [当前类别名称]
   * </pre>
   *
   * <p>即用连字符连接父类别标题和当前类别名称，其中父类别标题用同样的方法递归定义。
   *
   * <p>例如，<code>"主营业务收入 - 在线商城 - 会员卡"</code>
   *
   * <p>此字段通过查询构造生成。
   */
  @Nullable
  private String title;

  /**
   * 父类别基本信息，父类别必须与子类别属于同一个App同一个实体；若不存在父类别
   * 则此属性为{@code null}。
   */
  @Nullable
  private Info parent;

  /**
   * 是否是预定义的数据。
   */
  private boolean predefined;

  /**
   * 创建时间。
   */
  private Instant createTime;

  /**
   * 最后一次修改时间。
   */
  @Nullable
  private Instant modifyTime;

  /**
   * 删除时间。
   */
  @Nullable
  private Instant deleteTime;

  public final Long getId() {
    return id;
  }

  public final Category setId(final Long id) {
    this.id = id;
    return this;
  }

  @Nullable
  public final Info getApp() {
    return app;
  }

  public final Category setApp(@Nullable final Info app) {
    this.app = app;
    return this;
  }

  public final String getEntity() {
    return entity;
  }

  public final Category setEntity(final String entity) {
    this.entity = entity;
    return this;
  }

  public final String getCode() {
    return code;
  }

  public final Category setCode(final String code) {
    this.code = code;
    return this;
  }

  public final String getName() {
    return name;
  }

  public final Category setName(final String name) {
    this.name = name;
    return this;
  }

  @Nullable
  public final String getTitle() {
    return title;
  }

  public final Category setTitle(@Nullable final String title) {
    this.title = title;
    return this;
  }

  @Nullable
  public final String getIcon() {
    return icon;
  }

  public final Category setIcon(@Nullable final String icon) {
    this.icon = icon;
    return this;
  }

  @Nullable
  public final String getDescription() {
    return description;
  }

  public final Category setDescription(@Nullable final String description) {
    this.description = description;
    return this;
  }

  @Nullable
  public final Info getParent() {
    return parent;
  }

  public final Category setParent(@Nullable final Info parent) {
    this.parent = parent;
    return this;
  }

  public final boolean isPredefined() {
    return predefined;
  }

  public final Category setPredefined(final boolean predefined) {
    this.predefined = predefined;
    return this;
  }

  public final Instant getCreateTime() {
    return createTime;
  }

  public final Category setCreateTime(final Instant createTime) {
    this.createTime = createTime;
    return this;
  }

  @Nullable
  public final Instant getModifyTime() {
    return modifyTime;
  }

  public final Category setModifyTime(@Nullable final Instant modifyTime) {
    this.modifyTime = modifyTime;
    return this;
  }

  @Nullable
  public final Instant getDeleteTime() {
    return deleteTime;
  }

  public final Category setDeleteTime(@Nullable final Instant deleteTime) {
    this.deleteTime = deleteTime;
    return this;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Category other = (Category) o;
    return Equality.equals(predefined, other.predefined)
            && Equality.equals(id, other.id)
            && Equality.equals(app, other.app)
            && Equality.equals(entity, other.entity)
            && Equality.equals(code, other.code)
            && Equality.equals(name, other.name)
            && Equality.equals(icon, other.icon)
            && Equality.equals(description, other.description)
            && Equality.equals(title, other.title)
            && Equality.equals(parent, other.parent)
            && Equality.equals(createTime, other.createTime)
            && Equality.equals(modifyTime, other.modifyTime)
            && Equality.equals(deleteTime, other.deleteTime);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, app);
    result = Hash.combine(result, multiplier, entity);
    result = Hash.combine(result, multiplier, code);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, icon);
    result = Hash.combine(result, multiplier, description);
    result = Hash.combine(result, multiplier, title);
    result = Hash.combine(result, multiplier, parent);
    result = Hash.combine(result, multiplier, predefined);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, modifyTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
            .append("id", id)
            .append("app", app)
            .append("entity", entity)
            .append("code", code)
            .append("name", name)
            .append("icon", icon)
            .append("description", description)
            .append("title", title)
            .append("parent", parent)
            .append("predefined", predefined)
            .append("createTime", createTime)
            .append("modifyTime", modifyTime)
            .append("deleteTime", deleteTime)
            .toString();
  }
}
