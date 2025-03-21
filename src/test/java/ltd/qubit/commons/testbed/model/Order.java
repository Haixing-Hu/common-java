////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.testbed.model;

import java.math.BigDecimal;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * A test model class representing an order.
 *
 * @author Haixing Hu
 */
public class Order {

  private Customer customer;
  private String status;
  private BigDecimal amount;
  private Info info;

  public Order() {
    // empty
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(final Customer customer) {
    this.customer = customer;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(final String status) {
    this.status = status;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(final BigDecimal amount) {
    this.amount = amount;
  }

  public Info getInfo() {
    return info;
  }

  public void setInfo(final Info info) {
    this.info = info;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Order other = (Order) o;
    return Equality.equals(customer, other.customer) && Equality.equals(status, other.status) && Equality.equals(amount,
        other.amount) && Equality.equals(info, other.info);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, customer);
    result = Hash.combine(result, multiplier, status);
    result = Hash.combine(result, multiplier, amount);
    result = Hash.combine(result, multiplier, info);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append(Order::getCustomer, customer)
        .append(Order::getStatus, status)
        .append(Order::getAmount, amount)
        .append(Order::getInfo, info)
        .toString();
  }
}