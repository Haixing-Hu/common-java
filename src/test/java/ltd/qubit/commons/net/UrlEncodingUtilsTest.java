////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import ltd.qubit.commons.util.pair.NameValuePair;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.nio.charset.StandardCharsets.UTF_8;

public class UrlEncodingUtilsTest {

  private static final int[] SWISS_GERMAN_HELLO = {0x47, 0x72, 0xFC, 0x65, 0x7A, 0x69, 0x5F, 0x7A,
      0xE4, 0x6D, 0xE4};
  private static final int[] RUSSIAN_HELLO = {0x412, 0x441, 0x435, 0x43C, 0x5F, 0x43F, 0x440, 0x438,
      0x432, 0x435, 0x442};

  private static String constructString(final int[] unicodeChars) {
    final StringBuilder buffer = new StringBuilder();
    if (unicodeChars != null) {
      for (final int unicodeChar : unicodeChars) {
        buffer.append((char) unicodeChar);
      }
    }
    return buffer.toString();
  }

  private static void assertNameValuePair(final NameValuePair parameter, final String expectedName,
      final String expectedValue) {
    assertEquals(parameter.getName(), expectedName);
    assertEquals(parameter.getValue(), expectedValue);
  }

  @Test
  public void testParseURLCodedContent() {
    List<NameValuePair> result;

    result = parse("");
    assertTrue(result.isEmpty());

    result = parse("Name0");
    assertEquals(1, result.size());
    assertNameValuePair(result.get(0), "Name0", null);

    result = parse("Name1=Value1");
    assertEquals(1, result.size());
    assertNameValuePair(result.get(0), "Name1", "Value1");

    result = parse("Name2=");
    assertEquals(1, result.size());
    assertNameValuePair(result.get(0), "Name2", "");

    result = parse("Name3");
    assertEquals(1, result.size());
    assertNameValuePair(result.get(0), "Name3", null);

    result = parse("Name4=Value%204%21");
    assertEquals(1, result.size());
    assertNameValuePair(result.get(0), "Name4", "Value 4!");

    result = parse("Name4=Value%2B4%21");
    assertEquals(1, result.size());
    assertNameValuePair(result.get(0), "Name4", "Value+4!");

    result = parse("Name4=Value%204%21%20%214");
    assertEquals(1, result.size());
    assertNameValuePair(result.get(0), "Name4", "Value 4! !4");

    result = parse("Name5=aaa&Name6=bbb");
    assertEquals(2, result.size());
    assertNameValuePair(result.get(0), "Name5", "aaa");
    assertNameValuePair(result.get(1), "Name6", "bbb");

    result = parse("Name7=aaa&Name7=b%2Cb&Name7=ccc");
    assertEquals(3, result.size());
    assertNameValuePair(result.get(0), "Name7", "aaa");
    assertNameValuePair(result.get(1), "Name7", "b,b");
    assertNameValuePair(result.get(2), "Name7", "ccc");

    result = parse("Name8=xx%2C%20%20yy%20%20%2Czz");
    assertEquals(1, result.size());
    assertNameValuePair(result.get(0), "Name8", "xx,  yy  ,zz");

    result = parse("price=10%20%E2%82%AC");
    assertEquals(1, result.size());
    assertNameValuePair(result.get(0), "price", "10 \u20AC");

    result = parse("a=b\"c&d=e");
    assertEquals(2, result.size());
    assertNameValuePair(result.get(0), "a", "b\"c");
    assertNameValuePair(result.get(1), "d", "e");
  }

  @Test
  public void testParseSegments() {
    assertThat(UrlEncodingUtils.parsePathSegments("/this/that"),
        CoreMatchers.equalTo(Arrays.asList("this", "that")));
    assertThat(UrlEncodingUtils.parsePathSegments("this/that"),
        CoreMatchers.equalTo(Arrays.asList("this", "that")));
    assertThat(UrlEncodingUtils.parsePathSegments("this//that"),
        CoreMatchers.equalTo(Arrays.asList("this", "", "that")));
    assertThat(UrlEncodingUtils.parsePathSegments("this//that/"),
        CoreMatchers.equalTo(Arrays.asList("this", "", "that", "")));
    assertThat(UrlEncodingUtils.parsePathSegments("this//that/%2fthis%20and%20that"),
        CoreMatchers.equalTo(Arrays.asList("this", "", "that", "/this and that")));
    assertThat(UrlEncodingUtils.parsePathSegments("this///that//"),
        CoreMatchers.equalTo(Arrays.asList("this", "", "", "that", "", "")));
    assertThat(UrlEncodingUtils.parsePathSegments("/"),
        CoreMatchers.equalTo(Collections.singletonList("")));
    assertThat(UrlEncodingUtils.parsePathSegments(""),
        CoreMatchers.equalTo(Collections.<String>emptyList()));
  }

  @Test
  public void testFormatSegments() {
    assertThat(UrlEncodingUtils.formatSegments("this", "that"), CoreMatchers.equalTo("/this/that"));
    assertThat(UrlEncodingUtils.formatSegments("this", "", "that"),
        CoreMatchers.equalTo("/this//that"));
    assertThat(UrlEncodingUtils.formatSegments("this", "", "that", "/this and that"),
        CoreMatchers.equalTo("/this//that/%2Fthis%20and%20that"));
    assertThat(UrlEncodingUtils.formatSegments("this", "", "", "that", "", ""),
        CoreMatchers.equalTo("/this///that//"));
    assertThat(UrlEncodingUtils.formatSegments(""), CoreMatchers.equalTo("/"));
    assertThat(UrlEncodingUtils.formatSegments(), CoreMatchers.equalTo(""));
  }

  @Test
  public void testParseURLCodedContentString() throws URISyntaxException {
    List<NameValuePair> result;

    result = parseString("");
    assertTrue(result.isEmpty());

    result = parseString("Name0");
    assertEquals(1, result.size());
    assertNameValuePair(result.get(0), "Name0", null);

    result = parseString("Name1=Value1");
    assertEquals(1, result.size());
    assertNameValuePair(result.get(0), "Name1", "Value1");

    result = parseString("Name2=");
    assertEquals(1, result.size());
    assertNameValuePair(result.get(0), "Name2", "");

    result = parseString("Name3");
    assertEquals(1, result.size());
    assertNameValuePair(result.get(0), "Name3", null);

    result = parseString("Name4=Value%204%21");
    assertEquals(1, result.size());
    assertNameValuePair(result.get(0), "Name4", "Value 4!");

    result = parseString("Name4=Value%2B4%21");
    assertEquals(1, result.size());
    assertNameValuePair(result.get(0), "Name4", "Value+4!");

    result = parseString("Name4=Value%204%21%20%214");
    assertEquals(1, result.size());
    assertNameValuePair(result.get(0), "Name4", "Value 4! !4");

    result = parseString("Name5=aaa&Name6=bbb");
    assertEquals(2, result.size());
    assertNameValuePair(result.get(0), "Name5", "aaa");
    assertNameValuePair(result.get(1), "Name6", "bbb");

    result = parseString("Name7=aaa&Name7=b%2Cb&Name7=ccc");
    assertEquals(3, result.size());
    assertNameValuePair(result.get(0), "Name7", "aaa");
    assertNameValuePair(result.get(1), "Name7", "b,b");
    assertNameValuePair(result.get(2), "Name7", "ccc");

    result = parseString("Name8=xx%2C%20%20yy%20%20%2Czz");
    assertEquals(1, result.size());
    assertNameValuePair(result.get(0), "Name8", "xx,  yy  ,zz");

    result = parseString("price=10%20%E2%82%AC");
    assertEquals(1, result.size());
    assertNameValuePair(result.get(0), "price", "10 \u20AC");
  }

  @Test
  public void testParseInvalidURLCodedContent() {
    List<NameValuePair> result;

    result = parse("name=%");
    assertEquals(1, result.size());
    assertNameValuePair(result.get(0), "name", "%");

    result = parse("name=%a");
    assertEquals(1, result.size());
    assertNameValuePair(result.get(0), "name", "%a");

    result = parse("name=%wa%20");
    assertEquals(1, result.size());
    assertNameValuePair(result.get(0), "name", "%wa ");
  }

  @Test
  public void testEmptyQuery() {
    final List<NameValuePair> result = UrlEncodingUtils.parse("", UTF_8);
    assertEquals(0, result.size());
    // [HTTPCLIENT-1889]:
    result.add(new NameValuePair("key", "value"));
  }

  @Test
  public void testParseUTF8Ampersand1String() {
    final String ru_hello = constructString(RUSSIAN_HELLO);
    final String ch_hello = constructString(SWISS_GERMAN_HELLO);
    final List<NameValuePair> parameters = new ArrayList<>();
    parameters.add(new NameValuePair("russian", ru_hello));
    parameters.add(new NameValuePair("swiss", ch_hello));

    final String s = UrlEncodingUtils.format(parameters, UTF_8);

    final List<NameValuePair> result = UrlEncodingUtils.parse(s, UTF_8);
    assertEquals(2, result.size());
    assertNameValuePair(result.get(0), "russian", ru_hello);
    assertNameValuePair(result.get(1), "swiss", ch_hello);
  }

  @Test
  public void testParseUTF8Ampersand2String() {
    testParseUTF8String('&');
  }

  @Test
  public void testParseUTF8SemicolonString() {
    testParseUTF8String(';');
  }

  private void testParseUTF8String(final char parameterSeparator) {
    final String ru_hello = constructString(RUSSIAN_HELLO);
    final String ch_hello = constructString(SWISS_GERMAN_HELLO);
    final List<NameValuePair> parameters = new ArrayList<>();
    parameters.add(new NameValuePair("russian", ru_hello));
    parameters.add(new NameValuePair("swiss", ch_hello));

    final String s = UrlEncodingUtils.format(parameters, parameterSeparator, UTF_8);

    final List<NameValuePair> result1 = UrlEncodingUtils.parse(s, UTF_8);
    assertEquals(2, result1.size());
    assertNameValuePair(result1.get(0), "russian", ru_hello);
    assertNameValuePair(result1.get(1), "swiss", ch_hello);

    final List<NameValuePair> result2 = UrlEncodingUtils.parse(s, UTF_8, parameterSeparator);
    assertEquals(result1, result2);
  }

  @Test
  public void testFormat() {
    final List<NameValuePair> params = new ArrayList<>();
    assertEquals(0, UrlEncodingUtils.format(params, US_ASCII).length());

    params.clear();
    params.add(new NameValuePair("Name0", null));
    assertEquals("Name0", UrlEncodingUtils.format(params, US_ASCII));

    params.clear();
    params.add(new NameValuePair("Name1", "Value1"));
    assertEquals("Name1=Value1", UrlEncodingUtils.format(params, US_ASCII));
    params.clear();
    params.add(new NameValuePair("Name2", ""));
    assertEquals("Name2=", UrlEncodingUtils.format(params, US_ASCII));

    params.clear();
    params.add(new NameValuePair("Name4", "Value 4&"));
    assertEquals("Name4=Value+4%26", UrlEncodingUtils.format(params, US_ASCII));

    params.clear();
    params.add(new NameValuePair("Name4", "Value+4&"));
    assertEquals("Name4=Value%2B4%26", UrlEncodingUtils.format(params, US_ASCII));

    params.clear();
    params.add(new NameValuePair("Name4", "Value 4& =4"));
    assertEquals("Name4=Value+4%26+%3D4", UrlEncodingUtils.format(params, US_ASCII));

    params.clear();
    params.add(new NameValuePair("Name5", "aaa"));
    params.add(new NameValuePair("Name6", "bbb"));
    assertEquals("Name5=aaa&Name6=bbb", UrlEncodingUtils.format(params, US_ASCII));

    params.clear();
    params.add(new NameValuePair("Name7", "aaa"));
    params.add(new NameValuePair("Name7", "b,b"));
    params.add(new NameValuePair("Name7", "ccc"));
    assertEquals("Name7=aaa&Name7=b%2Cb&Name7=ccc", UrlEncodingUtils.format(params, US_ASCII));
    assertEquals("Name7=aaa&Name7=b%2Cb&Name7=ccc", UrlEncodingUtils.format(params, '&', US_ASCII));
    assertEquals("Name7=aaa;Name7=b%2Cb;Name7=ccc", UrlEncodingUtils.format(params, ';', US_ASCII));

    params.clear();
    params.add(new NameValuePair("Name8", "xx,  yy  ,zz"));
    assertEquals("Name8=xx%2C++yy++%2Czz", UrlEncodingUtils.format(params, US_ASCII));
  }

  @Test
  public void testFormatString() { // as above, using String
    final List<NameValuePair> params = new ArrayList<>();
    assertEquals(0, UrlEncodingUtils.format(params, US_ASCII).length());

    params.clear();
    params.add(new NameValuePair("Name0", null));
    assertEquals("Name0", UrlEncodingUtils.format(params, US_ASCII));

    params.clear();
    params.add(new NameValuePair("Name1", "Value1"));
    assertEquals("Name1=Value1", UrlEncodingUtils.format(params, US_ASCII));

    params.clear();
    params.add(new NameValuePair("Name2", ""));
    assertEquals("Name2=", UrlEncodingUtils.format(params, US_ASCII));

    params.clear();
    params.add(new NameValuePair("Name4", "Value 4&"));
    assertEquals("Name4=Value+4%26", UrlEncodingUtils.format(params, US_ASCII));

    params.clear();
    params.add(new NameValuePair("Name4", "Value+4&"));
    assertEquals("Name4=Value%2B4%26", UrlEncodingUtils.format(params, US_ASCII));

    params.clear();
    params.add(new NameValuePair("Name4", "Value 4& =4"));
    assertEquals("Name4=Value+4%26+%3D4", UrlEncodingUtils.format(params, US_ASCII));

    params.clear();
    params.add(new NameValuePair("Name5", "aaa"));
    params.add(new NameValuePair("Name6", "bbb"));
    assertEquals("Name5=aaa&Name6=bbb", UrlEncodingUtils.format(params, US_ASCII));

    params.clear();
    params.add(new NameValuePair("Name7", "aaa"));
    params.add(new NameValuePair("Name7", "b,b"));
    params.add(new NameValuePair("Name7", "ccc"));
    assertEquals("Name7=aaa&Name7=b%2Cb&Name7=ccc", UrlEncodingUtils.format(params, US_ASCII));

    params.clear();
    params.add(new NameValuePair("Name8", "xx,  yy  ,zz"));
    assertEquals("Name8=xx%2C++yy++%2Czz", UrlEncodingUtils.format(params, US_ASCII));
  }

  private List<NameValuePair> parse(final String params) {
    return UrlEncodingUtils.parse(params, UTF_8);
  }

  private List<NameValuePair> parseString(final String uri) throws URISyntaxException {
    return UrlEncodingUtils.parse(new URI("?" + uri), UTF_8);
  }
}
