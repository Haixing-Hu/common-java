/// /////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2024 - 2024.
//    Nanjing Xinglin Digital Technology Co., Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.generator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UuidRandomTokenGeneratorTest {

  @Test
  public void testGenerate() {
    final UuidRandomTokenGenerator generator = new UuidRandomTokenGenerator();
    final String token = generator.generate("new-app-code");
    assertNotNull(token);
    System.out.println(token);
  }
}
