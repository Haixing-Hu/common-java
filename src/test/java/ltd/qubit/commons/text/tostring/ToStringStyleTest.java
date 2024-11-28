////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.tostring;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test of the ToStringStyle class.
 *
 * @author Haixing Hu
 */
public class ToStringStyleTest {

  /**
   * An object used to test {@link ToStringStyle}.
   */
  static class Person {
    String name;
    int age;
    boolean smoker;
  }

  private static class ToStringStyleImpl extends ToStringStyle {

    private static final long serialVersionUID = 1352235200257356466L;
    // empty
  }

  /**
   */
  @BeforeEach
  public void setUp() {
  }

  /**
   */
  @AfterEach
  public void tearDown() {
  }

  @Test
  public void testSetArrayStart() {
    final ToStringStyle style = new ToStringStyleImpl();
    style.setArrayStart(null);
    assertEquals("", style.getArrayStart());
  }

  @Test
  public void testSetArrayEnd() {
    final ToStringStyle style = new ToStringStyleImpl();
    style.setArrayEnd(null);
    assertEquals("", style.getArrayEnd());
  }

  @Test
  public void testSetArraySeparator() {
    final ToStringStyle style = new ToStringStyleImpl();
    style.setArraySeparator(null);
    assertEquals("", style.getArraySeparator());
  }

  @Test
  public void testSetContentStart() {
    final ToStringStyle style = new ToStringStyleImpl();
    style.setContentStart(null);
    assertEquals("", style.getContentStart());
  }

  @Test
  public void testSetContentEnd() {
    final ToStringStyle style = new ToStringStyleImpl();
    style.setContentEnd(null);
    assertEquals("", style.getContentEnd());
  }

  @Test
  public void testSetFieldNameValueSeparator() {
    final ToStringStyle style = new ToStringStyleImpl();
    style.setFieldNameValueSeparator(null);
    assertEquals("", style.getFieldNameValueSeparator());
  }

  @Test
  public void testSetFieldSeparator() {
    final ToStringStyle style = new ToStringStyleImpl();
    style.setFieldSeparator(null);
    assertEquals("", style.getFieldSeparator());
  }

  @Test
  public void testSetNullText() {
    final ToStringStyle style = new ToStringStyleImpl();
    style.setNullText(null);
    assertEquals("", style.getNullText());
  }

  @Test
  public void testSetSizeStartText() {
    final ToStringStyle style = new ToStringStyleImpl();
    style.setSizeStartText(null);
    assertEquals("", style.getSizeStartText());
  }

  @Test
  public void testSetSizeEndText() {
    final ToStringStyle style = new ToStringStyleImpl();
    style.setSizeEndText(null);
    assertEquals("", style.getSizeEndText());
  }

  @Test
  public void testSetSummaryObjectStartText() {
    final ToStringStyle style = new ToStringStyleImpl();
    style.setSummaryObjectStartText(null);
    assertEquals("", style.getSummaryObjectStartText());
  }

  @Test
  public void testSetSummaryObjectEndText() {
    final ToStringStyle style = new ToStringStyleImpl();
    style.setSummaryObjectEndText(null);
    assertEquals("", style.getSummaryObjectEndText());
  }

}
