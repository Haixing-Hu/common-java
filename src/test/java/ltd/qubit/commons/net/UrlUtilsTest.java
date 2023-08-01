////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
}
