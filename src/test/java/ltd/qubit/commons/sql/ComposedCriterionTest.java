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

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.reflect.testbed.Foo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static ltd.qubit.commons.sql.ComparisonOperator.EQUAL;
import static ltd.qubit.commons.sql.ComparisonOperator.IN;
import static ltd.qubit.commons.sql.ComparisonOperator.LESS;
import static ltd.qubit.commons.sql.ComparisonOperator.NOT_EQUAL;
import static ltd.qubit.commons.util.LogicRelation.AND;
import static ltd.qubit.commons.util.LogicRelation.NOT;

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

    final ComposedCriterion<Foo> cc2 = new ComposedCriterion<>(Foo.class, NOT,
        Arrays.asList(c1));
    assertEquals("NOT (`field1` < 'value')", cc2.toSql());

  }
}
