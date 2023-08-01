////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import org.junit.jupiter.api.Test;

import static ltd.qubit.commons.text.NamingStyleUtils.propertyPathToDatabaseField;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NamingStyleUtilsTest {

  @Test
  public void testPropertyPathToDatabaseFieldName() {
    assertEquals(null, propertyPathToDatabaseField(null));
    assertEquals("", propertyPathToDatabaseField(""));
    assertEquals("a", propertyPathToDatabaseField("a"));
    assertEquals("abc", propertyPathToDatabaseField("Abc"));
    assertEquals("abc_def", propertyPathToDatabaseField("AbcDef"));
    assertEquals("abc_def", propertyPathToDatabaseField("abcDef"));
    assertEquals("a_a_b_b", propertyPathToDatabaseField("AABB"));

    assertEquals("a_", propertyPathToDatabaseField("a."));
    assertEquals("ab_c", propertyPathToDatabaseField("Ab.c"));
    assertEquals("abc_de_f", propertyPathToDatabaseField("Abc.De.f"));
    assertEquals("abc_x_de_f", propertyPathToDatabaseField("Abc.xDe.f"));
    assertEquals("abc_d_ef", propertyPathToDatabaseField("abcD.ef"));
    assertEquals("a_a_b_b", propertyPathToDatabaseField("AAB.B"));
    assertEquals("a_ab_b", propertyPathToDatabaseField("A..ab.B"));

    assertEquals("app_code", propertyPathToDatabaseField("app.code"));
    assertEquals("attachment_owner_id", propertyPathToDatabaseField("attachment.ownerId"));

    assertEquals("attachment_m_owner_id", propertyPathToDatabaseField("attachment.m_ownerId"));
    assertEquals("m_owner_id", propertyPathToDatabaseField("m_ownerId"));
    assertEquals("m_owner_id", propertyPathToDatabaseField("m_OwnerId"));
  }
}
