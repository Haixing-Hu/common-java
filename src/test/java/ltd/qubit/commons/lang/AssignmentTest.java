////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test of the {@link Assignment} class.
 *
 * @author Haixing Hu
 */
public class AssignmentTest {

  static class A implements CloneableEx<A> {

    protected int x;

    public A(final int x) {
      this.x = x;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + x;
      return result;
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
      final A other = (A) obj;
      return x == other.x;
    }

    @Override
    public A clone() {
      return new A(x);
    }
  }

  static class B extends A {

    private final int y;

    public B(final int x, final int y) {
      super(x);
      this.y = y;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + y;
      return result;
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (! super.equals(obj)) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      final B other = (B) obj;
      return y == other.y;
    }

    @Override
    public B clone() {
      return new B(x, y);
    }
  }

  @Test
  public void testCloneCloneable() {
    A a = null;
    B b = null;
    assertEquals(a, Assignment.clone(a));
    assertEquals(b, Assignment.clone(b));

    a = new A(1);
    b = new B(2, 3);
    assertEquals(a, Assignment.clone(a));
    assertEquals(b, Assignment.clone(b));
  }

  // TODO: finish test of other functions

}
