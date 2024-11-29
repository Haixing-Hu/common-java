////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import ltd.qubit.commons.annotation.Scale;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.jackson.module.TypeRegistrationModule;
import ltd.qubit.commons.text.jackson.module.type.TypeRegister;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TypeRegistrationModuleTest {

  public static class Foo {

    @Scale(2)
    private BigDecimal price;

    @Scale(4)
    private BigDecimal amount;

    public BigDecimal getPrice() {
      return price;
    }

    public void setPrice(final BigDecimal price) {
      this.price = price;
    }

    public BigDecimal getAmount() {
      return amount;
    }

    public void setAmount(final BigDecimal amount) {
      this.amount = amount;
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) {
        return true;
      }
      if ((o == null) || (getClass() != o.getClass())) {
        return false;
      }
      final Foo other = (Foo) o;
      return Equality.equals(price, other.price)
          && Equality.equals(amount, other.amount);
    }

    @Override
    public int hashCode() {
      final int multiplier = 7;
      int result = 3;
      result = Hash.combine(result, multiplier, price);
      result = Hash.combine(result, multiplier, amount);
      return result;
    }

    @Override
    public String toString() {
      return new ToStringBuilder(this)
          .append("price", price)
          .append("amount", amount)
          .toString();
    }
  }

  @Test
  public void testContextualSerializerJson() throws JsonProcessingException {
    final Foo foo = new Foo();
    foo.setPrice(new BigDecimal("1.21"));
    foo.setAmount(new BigDecimal("2.1234"));
    final CustomizedJsonMapper mapper = new CustomizedJsonMapper();
    final String s1 = mapper.writeValueAsString(foo);
    final String s2 = mapper.writeValueAsString(foo);
    System.out.println(s1);
    System.out.println(s2);
    assertEquals(s1, s2);
  }

  @Test
  public void testContextualSerializerXml() throws JsonProcessingException {
    final Foo foo = new Foo();
    foo.setPrice(new BigDecimal("1.21"));
    foo.setAmount(new BigDecimal("2.1234"));
    final CustomizedXmlMapper mapper = new CustomizedXmlMapper();
    final String s1 = mapper.writeValueAsString(foo);
    final String s2 = mapper.writeValueAsString(foo);
    System.out.println(s1);
    System.out.println(s2);
    assertEquals(s1, s2);
  }

  @Test
  public void testGetCommonTypeRegisters() {
    @SuppressWarnings("rawtypes")
    final List<TypeRegister> registerList = TypeRegistrationModule.getCommonTypeRegisters();
    assertNotEquals(0, registerList.size());
    for (final TypeRegister<?> register : registerList) {
      assertNotNull(register);
      System.out.println(register);
    }
    TypeRegistrationModule.clear();
    TypeRegistrationModule.registerTypes(registerList);
  }
}
