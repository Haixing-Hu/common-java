////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config;

import javax.annotation.Nullable;

import ltd.qubit.commons.util.value.NamedMultiValues;

/**
 * A {@link Property} object represents a property, which has the following
 * attributes:
 *
 * <ul>
 * <li>a name</li>
 * <li>a type</li>
 * <li>a optional description</li>
 * <li>a boolean mark to indicate whether this property is final</li>
 * <li>one or more values of the specified type</li>
 * </ul>
 *
 * <p>Currently the {@link Property} objects support the following types:
 * <ul>
 * <li>{@code boolean}</li>
 * <li>{@code char}</li>
 * <li>{@code byte}</li>
 * <li>{@code short}</li>
 * <li>{@code int}</li>
 * <li>{@code long}</li>
 * <li>{@code float}</li>
 * <li>{@code double}</li>
 * <li>{@code String}</li>
 * <li>{@code java.util.Date}</li>
 * <li>{@code java.math.BigDecimal}</li>
 * <li>{@code java.math.BigInteger}</li>
 * <li>{@code byte[]}</li>
 * <li>{@code Class}</li>
 * </ul>
 *
 * @author Haixing Hu
 */
public interface Property extends NamedMultiValues {

  /**
   * Gets the description of this property.
   *
   * @return the description of this property, which could be null.
   */
  @Nullable
  String getDescription();

  /**
   * Sets the description of this property.
   *
   * @param description
   *     the description of this property, which could be null.
   */
  void setDescription(@Nullable String description);

  /**
   * Tests whether this property if final.
   *
   * <p>A final property can not be override by merging with the property having
   * the same name.
   *
   * @return true if this property if final; false otherwise.
   */
  boolean isFinal();

  /**
   * Sets whether this property is final.
   *
   * <p>A final property can not be override by merging with the property having
   * the same name.
   *
   * @param isFinal
   *     if it is true, this property will be set to be final; otherwise, it
   *     will be set to be non-final.
   */
  void setFinal(boolean isFinal);

  /**
   * Assigns another {@link Property} object to this object.
   *
   * @param other
   *     the other {@link Property} object to be assigned to this object.
   */
  void assign(Property other);
}
