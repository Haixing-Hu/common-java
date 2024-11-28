////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import org.junit.jupiter.api.Test;

/**
 * Test the {@link SystemUtils#getSizeOf(Class)} method.
 *
 * @author Haixing Hu
 */
public class GetSizeOfTest {

  public static final class Node {
    public char[] key;
    public int    value;
  }

  @Test
  public void testGetSizeOf() {
    long size = 0;

    size = SystemUtils.getSizeOf(Object.class);
    System.out.println("sizeof(Object) = " + size);

    //    size = Systems.getSizeOf(Boolean.class);
    //    System.out.println("sizeof(Boolean) = " + size);

    //    size = Systems.getSizeOf(Character.class);
    //    System.out.println("sizeof(Character) = " + size);

    //    size = Systems.getSizeOf(Byte.class);
    //    System.out.println("sizeof(Byte) = " + size);
    //
    //    size = Systems.getSizeOf(Short.class);
    //    System.out.println("sizeof(Short) = " + size);
    //
    //    size = Systems.getSizeOf(Integer.class);
    //    System.out.println("sizeof(Integer) = " + size);
    //
    //    size = Systems.getSizeOf(Long.class);
    //    System.out.println("sizeof(Long) = " + size);
    //
    //    size = Systems.getSizeOf(Float.class);
    //    System.out.println("sizeof(Float) = " + size);
    //
    //    size = Systems.getSizeOf(Double.class);
    //    System.out.println("sizeof(Double) = " + size);

    size = SystemUtils.getSizeOf(String.class);
    System.out.println("sizeof(String) = " + size);

    //    size = Systems.getSizeOf(int[].class);
    //    System.out.println("sizeof(int[]) = " + size);

    size = SystemUtils.getSizeOf(Node.class);
    System.out.println("sizeof(Node) = " + size);
  }
}
