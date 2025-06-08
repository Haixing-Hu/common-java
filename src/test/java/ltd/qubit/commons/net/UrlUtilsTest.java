////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import java.net.URI;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test of the UrlUtils class.
 *
 * @author Haixing Hu
 */
public class UrlUtilsTest {

  @Test
  public void testGetDomain() {

    assertEquals("sina.com.cn", UrlUtils.getDomain("www.sina.com.cn"));
    assertEquals("sina.com.cn", UrlUtils.getDomain("news.sina.com.cn"));
    assertEquals("sina.com.cn", UrlUtils.getDomain("sina.com.cn"));
    assertEquals("sina.com.cn", UrlUtils.getDomain("a.b.c.d.sina.com.cn"));

    assertEquals("", UrlUtils.getDomain(""));
    assertEquals("a", UrlUtils.getDomain("a"));

    assertEquals("a.cn", UrlUtils.getDomain("a.cn"));
    assertEquals("cn", UrlUtils.getDomain("cn"));
  }

  @Test
  public void testGetDomainSuffix() {
    assertEquals("com.cn", UrlUtils.getDomainSuffix("www.sina.com.cn").getDomain());
    assertEquals("com.cn", UrlUtils.getDomainSuffix("news.sina.com.cn").getDomain());
    assertEquals("com.cn", UrlUtils.getDomainSuffix("sina.com.cn").getDomain());
    assertEquals("com.cn", UrlUtils.getDomainSuffix("a.b.c.d.sina.com.cn").getDomain());
    assertEquals("cn", UrlUtils.getDomainSuffix("a.cn").getDomain());
    assertEquals("cn", UrlUtils.getDomainSuffix("cn").getDomain());

    assertNull(UrlUtils.getDomainSuffix(""));
    assertNull(UrlUtils.getDomainSuffix("a"));
  }

  @Test
  void buildBase64DataUrlWithValidInput() {
    final String mimeType = "text/plain";
    final String base64String = "SGVsbG8sIFdvcmxkIQ==";
    final String expectedUrl = "data:text/plain;base64,SGVsbG8sIFdvcmxkIQ==";
    assertEquals(expectedUrl, UrlUtils.buildBase64DataUrl(mimeType, base64String));
  }

  @Test
  void buildBase64DataUrlWithEmptyMimeType() {
    final String mimeType = "";
    final String base64String = "SGVsbG8sIFdvcmxkIQ==";
    final String expectedUrl = "data:;base64,SGVsbG8sIFdvcmxkIQ==";
    assertEquals(expectedUrl, UrlUtils.buildBase64DataUrl(mimeType, base64String));
  }

  @Test
  void buildBase64DataUrlWithEmptyBase64String() {
    final String mimeType = "text/plain";
    final String base64String = "";
    final String expectedUrl = "data:text/plain;base64,";
    assertEquals(expectedUrl, UrlUtils.buildBase64DataUrl(mimeType, base64String));
  }

  @Test
  void buildBase64DataUrlWithNullMimeType() {
    final String base64String = "SGVsbG8sIFdvcmxkIQ==";
    assertThrows(NullPointerException.class, () -> UrlUtils.buildBase64DataUrl(null, base64String));
  }

  @Test
  void buildBase64DataUrlWithNullBase64String() {
    final String mimeType = "text/plain";
    assertThrows(NullPointerException.class, () -> UrlUtils.buildBase64DataUrl(mimeType, (String) null));
  }

  @Test
  void buildBase64DataUriWithValidInput() {
    final String mimeType = "text/plain";
    final String base64String = "SGVsbG8sIFdvcmxkIQ==";
    final URI expectedUri = URI.create("data:text/plain;base64,SGVsbG8sIFdvcmxkIQ==");
    assertEquals(expectedUri, UrlUtils.buildBase64DataUri(mimeType, base64String));
  }

  @Test
  void buildBase64DataUriWithEmptyMimeType() {
    final String mimeType = "";
    final String base64String = "SGVsbG8sIFdvcmxkIQ==";
    final URI expectedUri = URI.create("data:;base64,SGVsbG8sIFdvcmxkIQ==");
    assertEquals(expectedUri, UrlUtils.buildBase64DataUri(mimeType, base64String));
  }

  @Test
  void buildBase64DataUriWithEmptyBase64String() {
    final String mimeType = "text/plain";
    final String base64String = "";
    final URI expectedUri = URI.create("data:text/plain;base64,");
    assertEquals(expectedUri, UrlUtils.buildBase64DataUri(mimeType, base64String));
  }

  @Test
  void buildBase64DataUriWithNullMimeType() {
    final String base64String = "SGVsbG8sIFdvcmxkIQ==";
    assertThrows(NullPointerException.class, () -> UrlUtils.buildBase64DataUri(null, base64String));
  }

  @Test
  void buildBase64DataUriWithNullBase64String() {
    final String mimeType = "text/plain";
    assertThrows(NullPointerException.class, () -> UrlUtils.buildBase64DataUri(mimeType, (String) null));
  }
}