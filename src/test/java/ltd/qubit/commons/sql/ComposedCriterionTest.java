////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import java.sql.SQLSyntaxErrorException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.reflect.testbed.Foo;
import ltd.qubit.commons.testbed.model.Address;
import ltd.qubit.commons.testbed.model.Customer;
import ltd.qubit.commons.testbed.model.Info;
import ltd.qubit.commons.testbed.model.Order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import static ltd.qubit.commons.util.ComparisonOperator.EQUAL;
import static ltd.qubit.commons.util.ComparisonOperator.GREATER;
import static ltd.qubit.commons.util.ComparisonOperator.IN;
import static ltd.qubit.commons.util.ComparisonOperator.LESS;
import static ltd.qubit.commons.util.ComparisonOperator.NOT_EQUAL;
import static ltd.qubit.commons.util.LogicRelation.AND;
import static ltd.qubit.commons.util.LogicRelation.NOT;

/**
 * Unit test for {@link ComposedCriterion}.
 *
 * @author 胡海星
 */
public class ComposedCriterionTest {

  @Test
  public void testConstructor() {
    final SimpleCriterion<Foo> c1 = new SimpleCriterion<>(Foo.class, "field1",
        LESS, "value");
    final SimpleCriterion<Foo> c2 = new SimpleCriterion<>(Foo.class, "field2",
        EQUAL, null);
    final SimpleCriterion<Foo> c3 = new SimpleCriterion<>(Foo.class, "field3",
        NOT_EQUAL, "field4", true);
    final SimpleCriterion<Foo> c4 = new SimpleCriterion<>(Foo.class, "field5",
        IN, Arrays.asList(1, 2, 3));
    final ComposedCriterion<Foo> c5 = new ComposedCriterion<>(Foo.class, AND,
        Arrays.asList(c1, c2, c3, c4));
    assertEquals(Arrays.asList(c1, c2, c3, c4), c5.getCriteria());
    assertEquals(AND, c5.getRelation());
  }

  @Test
  public void testToSql() throws SQLSyntaxErrorException {
    final SimpleCriterion<Foo> c1 = new SimpleCriterion<>(Foo.class, "field1",
        LESS, "value");
    final SimpleCriterion<Foo> c2 = new SimpleCriterion<>(Foo.class, "field2",
        EQUAL, null);
    final SimpleCriterion<Foo> c3 = new SimpleCriterion<>(Foo.class, "field3",
        NOT_EQUAL, "field4", true);
    final SimpleCriterion<Foo> c4 = new SimpleCriterion<>(Foo.class, "field5",
        IN, new int[]{1, 2, 3});
    final ComposedCriterion<Foo> cc1 = new ComposedCriterion<>(Foo.class, AND,
        Arrays.asList(c1, c2, c3, c4));
    assertEquals("(`field1` < 'value') AND (`field2` IS NULL) AND "
            + "(`field3` != `field4`) AND (`field5` IN (1, 2, 3))", cc1.toSql());

    final ComposedCriterion<Foo> cc2 = new ComposedCriterion<>(Foo.class, NOT, List.of(c1));
    assertEquals("NOT (`field1` < 'value')", cc2.toSql());
  }

  @Test
  public void testExtractSubEntityCriterion_SingleLevel() {
    // Create test data
    final Order order = new Order();
    final Customer customer = new Customer();
    customer.setName("John");
    customer.setAge(20);
    order.setCustomer(customer);

    // Create composite criteria
    final ComposedCriterion<Order> criterion = new ComposedCriterion<>(
        Order.class,
        AND,
        new SimpleCriterion<>(Order.class, "customer.name", EQUAL, "John"),
        new SimpleCriterion<>(Order.class, "customer.age", GREATER, 18)
    );

    // Extract Customer entity criteria
    final ComposedCriterion<Customer> customerCriterion = criterion.extractSubEntityCriterion(
        Customer.class,
        Order::getCustomer
    );

    // Verify results
    assertNotNull(customerCriterion);
    assertEquals(2, customerCriterion.getCriteria().size());
    assertEquals("name", customerCriterion.getCriteria().get(0).getProperty());
    assertEquals("age", customerCriterion.getCriteria().get(1).getProperty());
  }

  @Test
  public void testExtractSubEntityCriterion_TwoLevels() {
    // Create composite criteria
    final ComposedCriterion<Order> criterion = new ComposedCriterion<>(
        Order.class,
        AND,
        new SimpleCriterion<>(Order.class, "customer.name", EQUAL, "John"),
        new SimpleCriterion<>(Order.class, "customer.address.city", EQUAL, "Beijing")
    );

    // Extract Customer entity criteria
    final ComposedCriterion<Customer> customerCriterion = criterion.extractSubEntityCriterion(
        Customer.class,
        Order::getCustomer
    );

    // Verify results
    assertNotNull(customerCriterion);
    assertEquals(2, customerCriterion.getCriteria().size());
    assertEquals("name", customerCriterion.getCriteria().get(0).getProperty());
    assertEquals("address.city", customerCriterion.getCriteria().get(1).getProperty());

    // Extract Address entity criteria
    final ComposedCriterion<Address> addressCriterion = customerCriterion.extractSubEntityCriterion(
        Address.class,
        Customer::getAddress
    );

    // Verify results
    assertNotNull(addressCriterion);
    assertEquals(1, addressCriterion.getCriteria().size());
    assertEquals("city", addressCriterion.getCriteria().get(0).getProperty());
  }

  @Test
  public void testExtractSubEntityCriterion_NoMatch() {
    // Create test data
    final Order order = new Order();
    final Customer customer = new Customer();
    customer.setName("John");
    order.setCustomer(customer);

    // Create composite criteria
    final ComposedCriterion<Order> criterion = new ComposedCriterion<>(
        Order.class,
        AND,
        new SimpleCriterion<>(Order.class, "status", EQUAL, "PENDING")
    );

    // Extract Customer entity criteria
    final ComposedCriterion<Customer> customerCriterion = criterion.extractSubEntityCriterion(
        Customer.class,
        Order::getCustomer
    );

    // Verify results
    assertNull(customerCriterion);
  }

  @Test
  public void testExtractSubEntityCriterion_PartialMatch() {
    // Create test data
    final Order order = new Order();
    final Customer customer = new Customer();
    customer.setName("John");
    order.setCustomer(customer);
    final Info info = new Info("test");
    order.setInfo(info);

    // Create composite criteria
    final ComposedCriterion<Order> criterion = new ComposedCriterion<>(
        Order.class,
        AND,
        new SimpleCriterion<>(Order.class, "customer.name", EQUAL, "John"),
        new SimpleCriterion<>(Order.class, "info.value", EQUAL, "test")
    );

    // Extract Customer entity criteria
    final ComposedCriterion<Customer> customerCriterion = criterion.extractSubEntityCriterion(
        Customer.class,
        Order::getCustomer
    );

    // Verify results
    assertNotNull(customerCriterion);
    assertEquals(1, customerCriterion.getCriteria().size());
    assertEquals("name", customerCriterion.getCriteria().get(0).getProperty());

    // Extract Info entity criteria
    final ComposedCriterion<Info> infoCriterion = criterion.extractSubEntityCriterion(
        Info.class,
        Order::getInfo
    );

    // Verify results
    assertNotNull(infoCriterion);
    assertEquals(1, infoCriterion.getCriteria().size());
    assertEquals("value", infoCriterion.getCriteria().get(0).getProperty());
  }

  @Test
  public void testExcludeSubEntityCriterion_SingleLevel() {
    // Create test data
    final Order order = new Order();
    final Customer customer = new Customer();
    customer.setName("John");
    order.setCustomer(customer);
    final Info info = new Info("test");
    order.setInfo(info);

    // Create composite criteria
    final ComposedCriterion<Order> criterion = new ComposedCriterion<>(
        Order.class,
        AND,
        new SimpleCriterion<>(Order.class, "customer.name", EQUAL, "John"),
        new SimpleCriterion<>(Order.class, "info.value", EQUAL, "test")
    );

    // Exclude Customer entity criteria
    final ComposedCriterion<Order> remainingCriterion = criterion.excludeSubEntityCriterion(
        Customer.class,
        Order::getCustomer
    );

    // Verify results
    assertNotNull(remainingCriterion);
    assertEquals(1, remainingCriterion.getCriteria().size());
    assertEquals("info.value", remainingCriterion.getCriteria().get(0).getProperty());
  }

  @Test
  public void testExcludeSubEntityCriterion_TwoLevels() {
    // Create test data
    final Order order = new Order();
    final Customer customer = new Customer();
    customer.setName("John");
    final Address address = new Address();
    address.setCity(new Info("Beijing"));
    customer.setAddress(address);
    order.setCustomer(customer);
    final Info info = new Info("test");
    order.setInfo(info);

    // Create composite criteria
    final ComposedCriterion<Order> criterion = new ComposedCriterion<>(
        Order.class,
        AND,
        new SimpleCriterion<>(Order.class, "customer.name", EQUAL, "John"),
        new SimpleCriterion<>(Order.class, "customer.address.city", EQUAL, "Beijing"),
        new SimpleCriterion<>(Order.class, "info.value", EQUAL, "test")
    );

    // Exclude Customer entity criteria
    final ComposedCriterion<Order> remainingCriterion = criterion.excludeSubEntityCriterion(
        Customer.class,
        Order::getCustomer
    );

    // Verify results
    assertNotNull(remainingCriterion);
    assertEquals(1, remainingCriterion.getCriteria().size());
    assertEquals("info.value", remainingCriterion.getCriteria().get(0).getProperty());
  }
}