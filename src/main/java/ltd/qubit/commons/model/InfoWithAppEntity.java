////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import java.io.Serial;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * This model represents the basic information of a deletable object belonging to an App, an entity.
 *
 * @author Haixing Hu
 */
public class InfoWithAppEntity extends Info implements WithApp, WithEntity {

  @Serial
  private static final long serialVersionUID = 7281371900014761423L;

  /**
   * 所属App。
   */
  @Nullable
  private StatefulInfo app;

  /**
   * 所属实体。
   */
  @Nullable
  private String entity;

  public InfoWithAppEntity() {}

  public InfoWithAppEntity(final InfoWithAppEntity other) {
    assign(other);
  }

  @Nullable
  public StatefulInfo getApp() {
    return app;
  }

  public void setApp(@Nullable final StatefulInfo app) {
    this.app = app;
  }

  @Override
  @Nullable
  public String getEntity() {
    return entity;
  }

  public void setEntity(@Nullable final String entity) {
    this.entity = entity;
  }

  public void assign(final Info other) {
    super.assign(other);
    if (other instanceof final InfoWithAppEntity info) {
      app = Assignment.clone(info.app);
      entity = info.entity;
    }
  }

  public void assign(final InfoWithAppEntity other) {
    super.assign(other);
    app = Assignment.clone(other.app);
    entity = other.entity;
  }

  @Override
  public InfoWithAppEntity clone() {
    return new InfoWithAppEntity(this);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final InfoWithAppEntity other = (InfoWithAppEntity) o;
    return super.equals(other)
        && Equality.equals(app, other.app)
        && Equality.equals(entity, other.entity);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, super.hashCode());
    result = Hash.combine(result, multiplier, app);
    result = Hash.combine(result, multiplier, entity);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("app", app)
            .append("entity", entity)
            .toString();
  }
}
