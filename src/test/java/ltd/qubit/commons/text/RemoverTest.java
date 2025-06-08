////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RemoverTest {

  @Test
  public void testRemoveChar() {
    assertNull(new Remover().forChar('a').limit(-1).removeFrom(null));
    assertEquals("", new Remover().forChar('a').limit(-1).removeFrom(""));
    assertEquals("qeed",
        new Remover().forChar('u').limit(-1).removeFrom("queued"));
    assertEquals("qeued",
        new Remover().forChar('u').limit(1).removeFrom("queued"));
    assertEquals("queued",
        new Remover().forChar('u').limit(0).removeFrom("queued"));
    assertEquals("queued",
        new Remover().forChar('z').limit(-1).removeFrom("queued"));
  }

  @Test
  public void testRemovePrefix() {
    assertNull(new Remover().forPrefix(null).ignoreCase(false).removeFrom(null));
    assertNull(new Remover().forPrefix("").ignoreCase(false).removeFrom(null));
    assertNull(new Remover().forPrefix("a").ignoreCase(false).removeFrom(null));

    assertEquals("", new Remover().forPrefix(null).ignoreCase(false).removeFrom(""));
    assertEquals("", new Remover().forPrefix("").ignoreCase(false).removeFrom(""));
    assertEquals("", new Remover().forPrefix("a").ignoreCase(false).removeFrom(""));

    // All others:
    assertEquals("domain.com", new Remover()
        .forPrefix("www.")
        .ignoreCase(false)
        .removeFrom("www.domain.com"));
    assertEquals("wwW.domain.com", new Remover()
        .forPrefix("www.")
        .ignoreCase(false)
        .removeFrom("wwW.domain.com"));
    assertEquals("domain.com", new Remover()
        .forPrefix("www.")
        .ignoreCase(false)
        .removeFrom("domain.com"));
    assertEquals("domain.com", new Remover()
        .forPrefix("")
        .ignoreCase(false)
        .removeFrom("domain.com"));
    assertEquals("domain.com", new Remover()
        .forPrefix(null)
        .ignoreCase(false)
        .removeFrom("domain.com"));
    assertEquals("", new Remover()
        .forPrefix("domain.com")
        .ignoreCase(false)
        .removeFrom("domain.com"));
    assertEquals("domain.com", new Remover()
        .forPrefix("DOMAIN.COM")
        .ignoreCase(false)
        .removeFrom("domain.com"));

    // Strings.removeStart("", *)        = ""
    assertNull(new Remover().forPrefix("").ignoreCase(true).removeFrom(null));
    assertNull(new Remover().forPrefix("a").ignoreCase(true).removeFrom(null));

    // Strings.removeStart(*, null)      = *
    assertEquals("", new Remover().forPrefix("").ignoreCase(true).removeFrom(""));
    assertEquals("", new Remover().forPrefix("a").ignoreCase(true).removeFrom(""));

    // All others:
    assertEquals("domain.com", new Remover()
        .forPrefix("www.")
        .ignoreCase(true)
        .removeFrom("www.domain.com"));
    assertEquals("domain.com", new Remover()
        .forPrefix("www.")
        .ignoreCase(true)
        .removeFrom("wwW.domain.com"));
    assertEquals("domain.com", new Remover()
        .forPrefix("")
        .ignoreCase(true)
        .removeFrom("domain.com"));
    assertEquals("", new Remover()
        .forPrefix("domain.com")
        .ignoreCase(true)
        .removeFrom("domain.com"));
    assertEquals("", new Remover()
        .forPrefix("DOMAIN.COM")
        .ignoreCase(true)
        .removeFrom("domain.com"));
  }

  @Test
  public void testRemoveSuffix() {
    // Strings.removeEnd("", *)        = ""
    assertNull(new Remover().forSuffix(null).ignoreCase(false).removeFrom(null));
    assertNull(new Remover().forSuffix("").ignoreCase(false).removeFrom(null));
    assertNull(new Remover().forSuffix("a").ignoreCase(false).removeFrom(null));

    // Strings.removeEnd(*, null)      = *
    assertEquals("", new Remover().forSuffix(null).ignoreCase(false).removeFrom(""));
    assertEquals("", new Remover().forSuffix("").ignoreCase(false).removeFrom(""));
    assertEquals("", new Remover().forSuffix("a").ignoreCase(false).removeFrom(""));

    // All others:
    assertEquals("www.domain.com.", new Remover()
        .forSuffix(".com")
        .ignoreCase(false)
        .removeFrom("www.domain.com."));
    assertEquals("www.domain", new Remover()
        .forSuffix(".com")
        .ignoreCase(false)
        .removeFrom("www.domain.com"));
    assertEquals("www.domain.Com", new Remover()
        .forSuffix(".com")
        .ignoreCase(false)
        .removeFrom("www.domain.Com"));
    assertEquals("www.domain", new Remover()
        .forSuffix(".com")
        .ignoreCase(false)
        .removeFrom("www.domain"));
    assertEquals("domain.com", new Remover()
        .forSuffix("")
        .ignoreCase(false)
        .removeFrom("domain.com"));
    assertEquals("domain.com", new Remover()
        .forSuffix(null)
        .ignoreCase(false)
        .removeFrom("domain.com"));
    assertEquals("", new Remover()
        .forSuffix("domain.com")
        .ignoreCase(false)
        .removeFrom("domain.com"));
    assertEquals("domain.com", new Remover()
        .forSuffix("domain.Com")
        .ignoreCase(false)
        .removeFrom("domain.com"));

    // Strings.removeEndIgnoreCase("", *)        = ""
    assertNull(new Remover().forSuffix(null).ignoreCase(true).removeFrom(null));
    assertNull(new Remover().forSuffix("").ignoreCase(true).removeFrom(null));
    assertNull(new Remover().forSuffix("a").ignoreCase(true).removeFrom(null));

    // Strings.removeEnd(*, null)      = *
    assertEquals("", new Remover().forSuffix(null).ignoreCase(true).removeFrom(""));
    assertEquals("", new Remover().forSuffix("").ignoreCase(true).removeFrom(""));
    assertEquals("", new Remover().forSuffix("a").ignoreCase(true).removeFrom(""));

    // All others:
    assertEquals("www.domain.com.", new Remover()
        .forSuffix(".com")
        .ignoreCase(true)
        .removeFrom("www.domain.com."));
    assertEquals("www.domain", new Remover()
        .forSuffix(".com")
        .ignoreCase(true)
        .removeFrom("www.domain.com"));
    assertEquals("www.domain", new Remover()
        .forSuffix(".com")
        .ignoreCase(true)
        .removeFrom("www.domain.Com"));
    assertEquals("www.domain", new Remover()
        .forSuffix(".com")
        .ignoreCase(true)
        .removeFrom("www.domain"));
    assertEquals("domain.com", new Remover()
        .forSuffix("")
        .ignoreCase(true)
        .removeFrom("domain.com"));
    assertEquals("domain.com", new Remover()
        .forSuffix(null)
        .ignoreCase(true)
        .removeFrom("domain.com"));
    assertEquals("", new Remover()
        .forSuffix("domain.com")
        .ignoreCase(true)
        .removeFrom("domain.com"));
    assertEquals("", new Remover()
        .forSuffix("domain.Com")
        .ignoreCase(true)
        .removeFrom("domain.com"));
  }

  @Test
  public void testRemoveSubstring() {
    // Strings.removeSubstring(null, *, *, *)        = null
    assertNull(new Remover()
        .forSubstring(null)
        .limit(-1)
        .ignoreCase(false)
        .removeFrom(null));
    assertNull(new Remover()
        .forSubstring("")
        .limit(-1)
        .ignoreCase(false)
        .removeFrom(null));
    assertNull(new Remover()
        .forSubstring("a")
        .limit(-1)
        .ignoreCase(false)
        .removeFrom(null));

    // Strings.removeSubstring("", *, *, *)          = ""
    assertEquals("", new Remover()
        .forSubstring(null)
        .limit(-1)
        .ignoreCase(false)
        .removeFrom(""));
    assertEquals("", new Remover()
        .forSubstring("")
        .limit(-1)
        .ignoreCase(false)
        .removeFrom(""));
    assertEquals("", new Remover()
        .forSubstring("a")
        .limit(-1)
        .ignoreCase(false)
        .removeFrom(""));

    // Strings.remove(str, null, *, *)        = str
    assertNull(new Remover()
        .forSubstring(null)
        .limit(-1)
        .ignoreCase(false)
        .removeFrom(null));
    assertEquals("", new Remover()
        .forSubstring(null)
        .limit(-1)
        .ignoreCase(false)
        .removeFrom(""));
    assertEquals("a", new Remover()
        .forSubstring(null)
        .limit(-1)
        .ignoreCase(false)
        .removeFrom("a"));

    // Strings.remove(str, "", *, *)          = str
    assertNull(new Remover()
        .forSubstring("")
        .limit(-1)
        .ignoreCase(false)
        .removeFrom(null));
    assertEquals("", new Remover()
        .forSubstring("")
        .limit(-1)
        .ignoreCase(false)
        .removeFrom(""));
    assertEquals("a", new Remover()
        .forSubstring("")
        .limit(-1)
        .ignoreCase(false)
        .removeFrom("a"));

    assertEquals("queued", new Remover()
        .forSubstring("Ue")
        .limit(-1)
        .ignoreCase(false)
        .removeFrom("queued"));
    assertEquals("qd", new Remover()
        .forSubstring("Ue")
        .limit(-1)
        .ignoreCase(true)
        .removeFrom("queued"));
    assertEquals("queued", new Remover()
        .forSubstring("Ue")
        .limit(1)
        .ignoreCase(false)
        .removeFrom("queued"));
    assertEquals("qued", new Remover()
        .forSubstring("Ue")
        .limit(1)
        .ignoreCase(true)
        .removeFrom("queued"));
    assertEquals("queued", new Remover()
        .forSubstring("Ue")
        .limit(0)
        .ignoreCase(false)
        .removeFrom("queued"));
    assertEquals("queued", new Remover()
        .forSubstring("Ue")
        .limit(0)
        .ignoreCase(true)
        .removeFrom("queued"));

    // Strings.remove("queued", "zz") = "queued"
    assertEquals("queued", new Remover()
        .forSubstring("zz")
        .limit(-1)
        .ignoreCase(false)
        .removeFrom("queued"));
    assertEquals("queued", new Remover()
        .forSubstring("zz")
        .limit(-1)
        .ignoreCase(true)
        .removeFrom("queued"));
  }

  @Test
  public void testRemovePrefixAndSuffix() {
    // Strings.removeStart("", *)        = ""
    assertNull(new Remover()
        .forPrefix(null)
        .forSuffix(null)
        .ignoreCase(false)
        .removeFrom(null));
    assertNull(new Remover()
        .forPrefix("")
        .forSuffix("")
        .ignoreCase(false)
        .removeFrom(null));
    assertNull(new Remover()
        .forPrefix("a")
        .forSuffix("b")
        .ignoreCase(false)
        .removeFrom(null));

    // Strings.removeStart(*, null)      = *
    assertEquals("", new Remover()
        .forPrefix(null)
        .forSuffix(null)
        .ignoreCase(false)
        .removeFrom(""));
    assertEquals("", new Remover()
        .forPrefix("")
        .forSuffix("")
        .ignoreCase(false)
        .removeFrom(""));
    assertEquals("", new Remover()
        .forPrefix("a")
        .forSuffix("b")
        .ignoreCase(false)
        .removeFrom(""));

    // All others:
    assertEquals("domain", new Remover()
        .forPrefix("www.")
        .forSuffix(".com")
        .ignoreCase(false)
        .removeFrom("www.domain.com"));
    assertEquals("wwW.domain", new Remover()
        .forPrefix("www.")
        .forSuffix(".com")
        .ignoreCase(false)
        .removeFrom("wwW.domain.com"));
    assertEquals("domainxcom", new Remover()
        .forPrefix("www.")
        .forSuffix(".com")
        .ignoreCase(false)
        .removeFrom("domainxcom"));
    assertEquals("domain", new Remover()
        .forPrefix("")
        .forSuffix(".com")
        .ignoreCase(false)
        .removeFrom("domain.com"));
    assertEquals("domain", new Remover()
        .forPrefix(null)
        .forSuffix(".com")
        .ignoreCase(false)
        .removeFrom("domain.com"));
    assertEquals("", new Remover()
        .forPrefix("domain")
        .forSuffix(".com")
        .ignoreCase(false)
        .removeFrom("domain.com"));
    assertEquals("domain.com", new Remover()
        .forPrefix("DOMAIN")
        .forSuffix(".COM")
        .ignoreCase(false)
        .removeFrom("domain.com"));

    // Strings.removeStart("", *)        = ""
    assertNull(new Remover()
        .forPrefix(null)
        .forSuffix(null)
        .ignoreCase(true)
        .removeFrom(null));
    assertNull(new Remover()
        .forPrefix("")
        .forSuffix("")
        .ignoreCase(true)
        .removeFrom(null));
    assertNull(new Remover()
        .forPrefix("a")
        .forSuffix("b")
        .ignoreCase(true)
        .removeFrom(null));

    // Strings.removeStart(*, null)      = *
    assertEquals("", new Remover()
        .forPrefix(null)
        .forSuffix(null)
        .ignoreCase(true)
        .removeFrom(""));
    assertEquals("", new Remover()
        .forPrefix("")
        .forSuffix("")
        .ignoreCase(true)
        .removeFrom(""));
    assertEquals("", new Remover()
        .forPrefix("a")
        .forSuffix("b")
        .ignoreCase(true)
        .removeFrom(""));

    // All others:
    assertEquals("domain", new Remover()
        .forPrefix("www.")
        .forSuffix(".com")
        .ignoreCase(true)
        .removeFrom("www.domain.com"));
    assertEquals("domain.", new Remover()
        .forPrefix("www.")
        .forSuffix("Com")
        .ignoreCase(true)
        .removeFrom("wwW.domain.com"));
    assertEquals("domain", new Remover()
        .forPrefix("")
        .forSuffix(".coM")
        .ignoreCase(true)
        .removeFrom("domain.com"));
    assertEquals("", new Remover()
        .forPrefix("domain.com")
        .forSuffix("com")
        .ignoreCase(true)
        .removeFrom("domain.com"));
    assertEquals("m", new Remover()
        .forPrefix("DOMAIN.CO")
        .forSuffix("COM")
        .ignoreCase(true)
        .removeFrom("domain.com"));
    assertEquals("", new Remover()
        .forPrefix("DX")
        .forSuffix("domain.COM")
        .ignoreCase(true)
        .removeFrom("domain.com"));
    assertEquals("domain", new Remover()
        .forPrefix("DX")
        .forSuffix(".COM")
        .ignoreCase(true)
        .removeFrom("domain.com"));
    assertEquals("domain.com", new Remover()
        .forPrefix("DX")
        .forSuffix("COMX")
        .ignoreCase(true)
        .removeFrom("domain.com"));

    assertEquals("hello", new Remover()
        .forPrefix("<tag>")
        .forSuffix("</TAG>")
        .ignoreCase(true)
        .removeFrom("<tag>hello</tag>"));
  }
}