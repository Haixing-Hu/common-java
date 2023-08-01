////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class UuidUtilsTest {

  private static final int LOOPS = 1000;

  @Test
  public void testGetUuid() {
    final String[] result = new String[LOOPS];
    final long start1 = System.currentTimeMillis();
    for (int i = 0; i < LOOPS; ++i) {
      result[i] = UuidUtils.getUuid();
    }
    final long end1 = System.currentTimeMillis();
    for (final String str : result) {
      System.out.println(str);
    }
    System.out.println("Total " + LOOPS + " UUID generated in "
        + HumanReadable.formatDuration(end1 - start1, TimeUnit.MILLISECONDS));
    System.out.println("=====================================================");

    final long start2 = System.currentTimeMillis();
    for (int i = 0; i < LOOPS; ++i) {
      result[i] = UUID.randomUUID().toString();
    }
    final long end2 = System.currentTimeMillis();
    for (final String str : result) {
      System.out.println(str);
    }
    System.out.println("Total " + LOOPS + " JDK UUID generated in "
        + HumanReadable.formatDuration(end2 - start2, TimeUnit.MILLISECONDS));
  }
}
