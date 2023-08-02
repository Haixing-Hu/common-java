////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * A top-level domain (TLD) is the last part of an Internet domain name; that
 * is, the letters which follow the final dot of any domain name.
 *
 * <p>For example, in the domain name {@code www.website.com}, the top-level
 * domain is {@code com}.
 *
 * @author Haixing Hu
 * @see <a href="http://www.iana.org/">INNA</a>
 * @see <a href="http://en.wikipedia.org/wiki/Top-level_domain">Top-Level Domain</a>
 */
@Immutable
public final class TopLevelDomain extends DomainSuffix {

  private static final long serialVersionUID = - 3880755857023154643L;

  public enum Type {
    INFRASTRUCTURE,
    GENERIC,
    COUNTRY
  }

  Type type;
  String country;

  TopLevelDomain() {
    type = Type.GENERIC;
    country = StringUtils.EMPTY;
  }

  TopLevelDomain(final String domain, final Status status,
      final String description, final Type type, final String country) {
    super(domain, status, description);
    this.type = type;
    this.country = country;
  }

  public Type getType() {
    return type;
  }

  @Override
  public boolean isTopLevel() {
    return true;
  }

  /**
   * Returns the country name if this TLD is Country Code TLD.
   *
   * @return country name or null
   */
  public String getCountry() {
    return country;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("domain", domain)
               .append("status", status)
               .append("description", description)
               .append("type", type)
               .append("country", country)
               .toString();
  }
}
