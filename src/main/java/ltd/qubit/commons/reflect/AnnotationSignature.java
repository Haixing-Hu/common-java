////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
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
 * The signature of an annotation.
 *
 * @author Haixing Hu
 */
final class AnnotationSignature {
  public final Field field;
  public final Class<? extends Annotation> type;

  public AnnotationSignature(final Field field, final Class<? extends Annotation> type) {
    this.field = field;
    this.type = type;
  }

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
