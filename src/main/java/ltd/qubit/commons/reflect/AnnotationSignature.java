////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import javax.annotation.Nullable;

import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 注解的签名。
 *
 * @author 胡海星
 */
final class AnnotationSignature {

  /**
   * 字段。
   */
  public final Field field;

  /**
   * 注解类型。
   */
  public final Class<? extends Annotation> type;

  /**
   * 构造一个注解签名。
   *
   * @param field
   *     字段。
   * @param type
   *     注解类型。
   */
  public AnnotationSignature(final Field field, final Class<? extends Annotation> type) {
    this.field = field;
    this.type = type;
  }

  @Override
  public int hashCode() {
    final int multiplier = 11;
    int code = 2;
    code = code * multiplier + field.hashCode();
    code = code * multiplier + type.hashCode();
    return code;
  }

  @Override
  public boolean equals(@Nullable final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (obj.getClass() != AnnotationSignature.class) {
      return false;
    }
    final AnnotationSignature other = (AnnotationSignature) obj;
    return type == other.type
        && field.equals(other.field);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("field", field)
        .append("type", type)
        .toString();
  }
}