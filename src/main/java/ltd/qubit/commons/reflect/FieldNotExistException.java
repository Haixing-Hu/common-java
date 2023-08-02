////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

/**
 * Thrown to indicate the specified field does not exist.
 *
 * @author Haixing Hu
 */
public class FieldNotExistException extends ReflectionException {

  private static final long serialVersionUID = 5409166838535034334L;

  private final Class<?> ownerClass;
  private final int options;
  private final String fieldName;

  public FieldNotExistException(final Class<?> cls, final int options,
      final String name) {
    super("There is no "
        + Option.toString(options)
        + " field named with '"
        + name
        + "' for the class "
        + cls.getName());
    this.ownerClass = cls;
    this.options = options;
    this.fieldName = name;
  }

  public FieldNotExistException(final Class<?> cls, final String name) {
    super("There is no field named with '"
        + name
        + "' for the class "
        + cls.getName());
    this.ownerClass = cls;
    this.options = 0;
    this.fieldName = name;
  }

  public final Class<?> getOwnerClass() {
    return ownerClass;
  }

  public final int getOptions() {
    return options;
  }

  public final String getFieldName() {
    return fieldName;
  }
}
