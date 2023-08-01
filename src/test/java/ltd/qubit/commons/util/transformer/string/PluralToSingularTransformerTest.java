////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PluralToSingularTransformerTest {

  @Test
  public void test() {
    final PluralToSingularTransformer transformer = PluralToSingularTransformer.INSTANCE;
    for (final String[] data : SingularPluralData.DATA) {
      final String singular = data[0];
      final String plural = data[1];
      System.out.println("Singular: " + singular + "  Plural: " + plural);
      final String actual = transformer.transform(plural);
      assertEquals(singular, actual);
    }
  }
}
