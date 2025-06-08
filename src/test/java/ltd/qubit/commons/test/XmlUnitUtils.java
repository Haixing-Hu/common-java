////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.test;

import javax.annotation.Nullable;

import org.xmlunit.assertj.XmlAssert;
import org.xmlunit.matchers.EvaluateXPathMatcher;
import org.xmlunit.matchers.HasXPathMatcher;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

/**
 * Provide utilities functions for XML Unit 2.
 *
 * @author Haixing Hu
 */
public class XmlUnitUtils {

  public static void assertXPathEquals(final String xml, final String xpath,
          @Nullable final Object value) {
    if (value == null) {
      assertThat(xml, not(HasXPathMatcher.hasXPath(xpath)));
    } else {
      final String expected = value.toString();
      assertThat(xml, EvaluateXPathMatcher.hasXPath(xpath, equalTo(expected)));
    }
  }

  public static void assertXPathNull(final String xml, final String xpath) {
    assertThat(xml, not(HasXPathMatcher.hasXPath(xpath)));
  }

  public static void assertXPathAbsent(final String xml, final String xpath) {
    assertThat(xml, not(HasXPathMatcher.hasXPath(xpath)));
  }

  public static <T> void assertXPathArrayEquals(final String xml,
      final String rootPath, final String wrapperNode, final String elementNode,
      @Nullable final T[] array) {
    if (array == null) {
      assertXPathNull(xml, rootPath + "/" + wrapperNode);
    } else {
      final int n = array.length;
      final String prefix = rootPath + "/" + wrapperNode + "/" + elementNode;
      for (int i = 0; i < n; ++i) {
        assertXPathEquals(xml, prefix + "[" + (i + 1) + "]", array[i]);
      }
    }
  }

  public static void assertXmlEqual(final String expected, final String actual) {
    XmlAssert.assertThat(actual).and(expected)
        .ignoreComments()
        .ignoreChildNodesOrder()
        .ignoreWhitespace()
        .ignoreElementContentWhitespace()
        .areIdentical();
  }
}