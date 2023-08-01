////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ltd.qubit.commons.model.Foo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortRequestTest {

  @Test
  public void testGetField() {
    final SortRequest<Foo> r1 = new SortRequest<>(Foo.class, "app.id");
    assertEquals("app_id", r1.getField());

    final SortRequest<Foo> r2 = new SortRequest<>(Foo.class, "userName");
    assertEquals("user_name", r2.getField());

    final SortRequest<Foo> r3 = new SortRequest<>(Foo.class, "userName.passWord");
    assertEquals("user_name_pass_word", r3.getField());

    final SortRequest<Foo> r4 = new SortRequest<>(Foo.class, "");
    assertEquals("", r4.getField());
  }

  @Test
  public void testSort() {
    final Foo f1 = new Foo();
    final Foo f2 = new Foo();
    final Foo f3 = new Foo();
    final Foo f4 = new Foo();
    f1.m_int = 7;
    f2.m_int = 6;
    f3.m_int = 5;
    f4.m_int = 4;

    f1.m_String = "123";
    f2.m_String = "234";
    f3.m_String = "345";
    f4.m_String = "456";

    final SortRequest<Foo> r1 = new SortRequest<>(Foo.class, "m_int");
    final List<Foo> list = new ArrayList<>();
    r1.sort(list);
    assertTrue(list.isEmpty());

    list.add(f1);
    r1.sort(list);
    assertEquals(1, list.size());

    list.add(f2);
    list.add(f3);
    list.add(f4);

    r1.sort(list);
    assertEquals(Arrays.asList(f4, f3, f2, f1), list);

    final SortRequest<Foo> r2 = new SortRequest<>(Foo.class, "m_String");
    r2.sort(list);
    assertEquals(Arrays.asList(f1, f2, f3, f4), list);

    f1.m_child = new Foo();
    f1.m_child.m_LocalDate = LocalDate.parse("2022-03-01");
    f2.m_child = new Foo();
    f2.m_child.m_LocalDate = LocalDate.parse("2022-02-01");
    f3.m_child = new Foo();
    f3.m_child.m_LocalDate = LocalDate.parse("2022-05-01");
    f4.m_child = new Foo();
    f4.m_child.m_LocalDate = LocalDate.parse("2022-04-01");

    final SortRequest<Foo> r3 = new SortRequest<>(Foo.class, "m_child.m_LocalDate");
    r3.sort(list);
    assertEquals(Arrays.asList(f2, f1, f4, f3), list);
  }
}
