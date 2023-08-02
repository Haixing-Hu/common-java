////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import java.io.Serializable;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireGreater;
import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * This structure stores the client information supported by the database.
 *
 * @author Haixing Hu
 */
public final class ClientInfo implements Serializable {

  private static final long serialVersionUID = 5116233033559100607L;

  private String name;
  private int maxLength;
  private @Nullable String defaultValue;
  private @Nullable String description;

  public ClientInfo() {
    name = null;
    maxLength = 0;
    defaultValue = null;
    description = null;
  }

  public ClientInfo(final String name, final int maxLength,
      @Nullable final String defaultValue, @Nullable final String description) {
    this.name = requireNonNull("name", name);
    this.maxLength = requireGreater("maxLength", maxLength, "zero", 0);
    this.defaultValue = defaultValue;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  protected void setName(final String name) {
    this.name = requireNonNull("name", name);
  }

  public int getMaxLength() {
    return maxLength;
  }

  protected void setMaxLength(final int maxLength) {
    this.maxLength = requireGreater("maxLength", maxLength, "zero", 0);
  }

  public String getDefaultValue() {
    return defaultValue;
  }

  protected void setDefaultValue(@Nullable final String defaultValue) {
    this.defaultValue = defaultValue;
  }

  public String getDescription() {
    return description;
  }

  protected void setDescription(@Nullable final String description) {
    this.description = description;
  }

  @Override
  public int hashCode() {
    final int multiplier = 3;
    int code = 123;
    code = Hash.combine(code, multiplier, name);
    code = Hash.combine(code, multiplier, maxLength);
    code = Hash.combine(code, multiplier, defaultValue);
    code = Hash.combine(code, multiplier, description);
    return code;
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
    final ClientInfo other = (ClientInfo) obj;
    return Equality.equals(name, other.name)
         && Equality.equals(maxLength, maxLength)
         && Equality.equals(defaultValue, defaultValue)
         && Equality.equals(description, description);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("name", name)
               .append("maxLength", maxLength)
               .append("defaultValue", defaultValue)
               .append("description", description)
               .toString();
  }
}
