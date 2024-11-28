////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SingularToPluralTransformerTest {

  @Test
  public void test() {
    final SingularToPluralTransformer transformer = SingularToPluralTransformer.INSTANCE;
    for (final String[] data : SingularPluralData.DATA) {
      final String singular = data[0];
      final String plural = data[1];
      System.out.println("Singular: " + singular + "  Plural: " + plural);
      final String actual = transformer.transform(singular);
      assertEquals(plural, actual);
    }
  }
}
