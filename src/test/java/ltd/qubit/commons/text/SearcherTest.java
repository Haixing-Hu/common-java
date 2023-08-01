////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.util.filter.character.CharFilter;
import ltd.qubit.commons.util.filter.character.InStringCharFilter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearcherTest {

  @Test
  public void testFindFirstIndexOfChar() {
    assertEquals(-1,
        new Searcher().forChar(' ').startFrom(0).findFirstIndexIn(null));
    assertEquals(-1,
        new Searcher().forChar(' ').startFrom(-1).findFirstIndexIn(null));
    assertEquals(-1,
        new Searcher().forChar(' ').startFrom(0).findFirstIndexIn(""));
    assertEquals(-1,
        new Searcher().forChar(' ').startFrom(-1).findFirstIndexIn(""));
    assertEquals(0,
        new Searcher().forChar('a').startFrom(0).findFirstIndexIn("aabaabaa"));
    assertEquals(2,
        new Searcher().forChar('b').startFrom(0).findFirstIndexIn("aabaabaa"));
    assertEquals(5,
        new Searcher().forChar('b').startFrom(3).findFirstIndexIn("aabaabaa"));
    assertEquals(-1,
        new Searcher().forChar('b').startFrom(9).findFirstIndexIn("aabaabaa"));
    assertEquals(2,
        new Searcher().forChar('b').startFrom(-1).findFirstIndexIn("aabaabaa"));
  }

  @Test
  public void testFindFirstIndexOfCharsSatisfy() {
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(null)
        .startFrom(0)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(null)
        .startFrom(-1)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(null)
        .startFrom(100)
        .findFirstIndexIn(null));

    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter(""))
        .startFrom(0)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter(""))
        .startFrom(-1)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter(""))
        .startFrom(100)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("ab"))
        .startFrom(0)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("ab"))
        .startFrom(-1)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("ab"))
        .startFrom(100)
        .findFirstIndexIn(null));

    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("")))
        .startFrom(0)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("")))
        .startFrom(-1)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("")))
        .startFrom(100)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("ab")))
        .startFrom(0)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("ab")))
        .startFrom(-1)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("ab")))
        .startFrom(100)
        .findFirstIndexIn(null));

    assertEquals(-1, new Searcher()
        .forCharsSatisfy(null)
        .startFrom(0)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(null)
        .startFrom(-1)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(null)
        .startFrom(100)
        .findFirstIndexIn(""));

    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter(""))
        .startFrom(0)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter(""))
        .startFrom(-1)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter(""))
        .startFrom(100)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("ab"))
        .startFrom(0)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("ab"))
        .startFrom(-1)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("ab"))
        .startFrom(100)
        .findFirstIndexIn(""));

    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("")))
        .startFrom(0)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("")))
        .startFrom(-1)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("")))
        .startFrom(100)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("ab")))
        .startFrom(0)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("ab")))
        .startFrom(-1)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("ab")))
        .startFrom(100)
        .findFirstIndexIn(""));

    assertEquals(-1, new Searcher()
        .forCharsSatisfy(null)
        .startFrom(0)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(null)
        .startFrom(-1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(null)
        .startFrom(100)
        .findFirstIndexIn("zzabyycdxx"));

    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter(""))
        .startFrom(0)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter(""))
        .startFrom(-1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter(""))
        .startFrom(100)
        .findFirstIndexIn("zzabyycdxx"));

    assertEquals(0, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("")))
        .startFrom(0)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(0, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("")))
        .startFrom(-1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("")))
        .startFrom(100)
        .findFirstIndexIn("zzabyycdxx"));

    assertEquals(0, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("zax"))
        .startFrom(0)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(0, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("zax"))
        .startFrom(-1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("zax"))
        .startFrom(100)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("zax"))
        .startFrom(1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(2, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("zax"))
        .startFrom(2)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(8, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("zax"))
        .startFrom(3)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(9, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("zax"))
        .startFrom(9)
        .findFirstIndexIn("zzabyycdxx"));

    assertEquals(3, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("zax")))
        .startFrom(0)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(3, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("zax")))
        .startFrom(-1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("zax")))
        .startFrom(100)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(3, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("zax")))
        .startFrom(1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(3, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("zax")))
        .startFrom(2)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(3, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("zax")))
        .startFrom(3)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("zax")))
        .startFrom(9)
        .findFirstIndexIn("zzabyycdxx"));

    assertEquals(3, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("byx"))
        .startFrom(0)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(3, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("byx"))
        .startFrom(-1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("byx"))
        .startFrom(100)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(3, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("byx"))
        .startFrom(1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(4, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("byx"))
        .startFrom(4)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(8, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("byx"))
        .startFrom(6)
        .findFirstIndexIn("zzabyycdxx"));

    assertEquals(0, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("byx")))
        .startFrom(0)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(0, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("byx")))
        .startFrom(-1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("byx")))
        .startFrom(100)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("byx")))
        .startFrom(1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(6, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("byx")))
        .startFrom(4)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(7, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("byx")))
        .startFrom(7)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("byx")))
        .startFrom(8)
        .findFirstIndexIn("zzabyycdxx"));

    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("zx"))
        .startFrom(0)
        .findFirstIndexIn("ab"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("zx"))
        .startFrom(-1)
        .findFirstIndexIn("ab"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("zx"))
        .startFrom(100)
        .findFirstIndexIn("ab"));

    assertEquals(0, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("zx")))
        .startFrom(0)
        .findFirstIndexIn("ab"));
    assertEquals(0, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("zx")))
        .startFrom(-1)
        .findFirstIndexIn("ab"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("zx")))
        .startFrom(100)
        .findFirstIndexIn("ab"));
  }

  @Test
  public void testFindFirstIndexOfCharsInArray() {
    assertEquals(-1, new Searcher()
        .forCharsIn((char[]) null)
        .startFrom(0)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsIn((char[]) null)
        .startFrom(-1)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsIn((char[]) null)
        .startFrom(100)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsIn()
        .startFrom(0)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsIn()
        .startFrom(-1)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsIn()
        .startFrom(100)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsIn('a', 'b')
        .startFrom(0)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsIn('a', 'b')
        .startFrom(-1)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsIn('a', 'b')
        .startFrom(100)
        .findFirstIndexIn(null));

    assertEquals(-1, new Searcher()
        .forCharsIn((char[]) null)
        .startFrom(0)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsIn((char[]) null)
        .startFrom(-1)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsIn((char[]) null)
        .startFrom(100)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsIn()
        .startFrom(0)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsIn()
        .startFrom(-1)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsIn()
        .startFrom(100)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsIn('a', 'b')
        .startFrom(0)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsIn('a', 'b')
        .startFrom(-1)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsIn('a', 'b')
        .startFrom(100)
        .findFirstIndexIn(""));

    assertEquals(-1, new Searcher()
        .forCharsIn((char[]) null)
        .startFrom(0)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn((char[]) null)
        .startFrom(-1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn((char[]) null)
        .startFrom(100)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn()
        .startFrom(0)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn()
        .startFrom(-1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn()
        .startFrom(100)
        .findFirstIndexIn("zzabyycdxx"));

    assertEquals(0, new Searcher()
        .forCharsIn('z', 'a', 'x')
        .startFrom(0)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(0, new Searcher()
        .forCharsIn('z', 'a', 'x')
        .startFrom(-1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn('z', 'a', 'x')
        .startFrom(100)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(1, new Searcher()
        .forCharsIn('z', 'a', 'x')
        .startFrom(1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(2, new Searcher()
        .forCharsIn('z', 'a', 'x')
        .startFrom(2)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(8, new Searcher()
        .forCharsIn('z', 'a', 'x')
        .startFrom(3)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(9, new Searcher()
        .forCharsIn('z', 'a', 'x')
        .startFrom(9)
        .findFirstIndexIn("zzabyycdxx"));

    assertEquals(3, new Searcher()
        .forCharsIn('b', 'y', 'x')
        .startFrom(0)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(3, new Searcher()
        .forCharsIn('b', 'y', 'x')
        .startFrom(-1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn('b', 'y', 'x')
        .startFrom(100)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(3, new Searcher()
        .forCharsIn('b', 'y', 'x')
        .startFrom(1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(4, new Searcher()
        .forCharsIn('b', 'y', 'x')
        .startFrom(4)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(8, new Searcher()
        .forCharsIn('b', 'y', 'x')
        .startFrom(6)
        .findFirstIndexIn("zzabyycdxx"));

    assertEquals(-1, new Searcher()
        .forCharsIn('z')
        .startFrom(0)
        .findFirstIndexIn("ab"));
    assertEquals(-1, new Searcher()
        .forCharsIn('z')
        .startFrom(-1)
        .findFirstIndexIn("ab"));
    assertEquals(-1, new Searcher()
        .forCharsIn('z')
        .startFrom(100)
        .findFirstIndexIn("ab"));

  }

  @Test
  public void testFindFirstIndexOfCharsInCharSequence() {
    assertEquals(-1, new Searcher()
        .forCharsIn((String) null)
        .startFrom(0)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsIn((String) null)
        .startFrom(-1)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsIn((String) null)
        .startFrom(100)
        .findFirstIndexIn(null));
    assertEquals(-1,
        new Searcher().forCharsIn("").startFrom(0).findFirstIndexIn(null));
    assertEquals(-1,
        new Searcher().forCharsIn("").startFrom(-1).findFirstIndexIn(null));
    assertEquals(-1,
        new Searcher().forCharsIn("").startFrom(100).findFirstIndexIn(null));
    assertEquals(-1,
        new Searcher().forCharsIn("ab").startFrom(0).findFirstIndexIn(null));
    assertEquals(-1,
        new Searcher().forCharsIn("ab").startFrom(-1).findFirstIndexIn(null));
    assertEquals(-1,
        new Searcher().forCharsIn("ab").startFrom(100).findFirstIndexIn(null));

    assertEquals(-1, new Searcher()
        .forCharsIn((String) null)
        .startFrom(0)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsIn((String) null)
        .startFrom(-1)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsIn((String) null)
        .startFrom(100)
        .findFirstIndexIn(""));
    assertEquals(-1,
        new Searcher().forCharsIn("").startFrom(0).findFirstIndexIn(""));
    assertEquals(-1,
        new Searcher().forCharsIn("").startFrom(-1).findFirstIndexIn(""));
    assertEquals(-1,
        new Searcher().forCharsIn("").startFrom(100).findFirstIndexIn(""));
    assertEquals(-1,
        new Searcher().forCharsIn("ab").startFrom(0).findFirstIndexIn(""));
    assertEquals(-1,
        new Searcher().forCharsIn("ab").startFrom(-1).findFirstIndexIn(""));
    assertEquals(-1,
        new Searcher().forCharsIn("ab").startFrom(100).findFirstIndexIn(""));

    assertEquals(-1, new Searcher()
        .forCharsIn((String) null)
        .startFrom(0)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn((String) null)
        .startFrom(-1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn((String) null)
        .startFrom(100)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn("")
        .startFrom(0)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn("")
        .startFrom(-1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn("")
        .startFrom(100)
        .findFirstIndexIn("zzabyycdxx"));

    assertEquals(0, new Searcher()
        .forCharsIn("zax")
        .startFrom(0)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(0, new Searcher()
        .forCharsIn("zax")
        .startFrom(-1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn("zax")
        .startFrom(100)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(1, new Searcher()
        .forCharsIn("zax")
        .startFrom(1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(2, new Searcher()
        .forCharsIn("zax")
        .startFrom(2)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(8, new Searcher()
        .forCharsIn("zax")
        .startFrom(3)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(9, new Searcher()
        .forCharsIn("zax")
        .startFrom(9)
        .findFirstIndexIn("zzabyycdxx"));

    assertEquals(3, new Searcher()
        .forCharsIn("byx")
        .startFrom(0)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(3, new Searcher()
        .forCharsIn("byx")
        .startFrom(-1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn("byx")
        .startFrom(100)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(3, new Searcher()
        .forCharsIn("byx")
        .startFrom(1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(4, new Searcher()
        .forCharsIn("byx")
        .startFrom(4)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(8, new Searcher()
        .forCharsIn("byx")
        .startFrom(6)
        .findFirstIndexIn("zzabyycdxx"));

    assertEquals(-1,
        new Searcher().forCharsIn("zx").startFrom(0).findFirstIndexIn("ab"));
    assertEquals(-1,
        new Searcher().forCharsIn("zx").startFrom(-1).findFirstIndexIn("ab"));
    assertEquals(-1,
        new Searcher().forCharsIn("zx").startFrom(100).findFirstIndexIn("ab"));
  }

  @Test
  public void testFindFirstIndexOfCharsNotInArray() {
    assertEquals(-1, new Searcher()
        .forCharsNotIn((char[]) null)
        .startFrom(0)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsNotIn((char[]) null)
        .startFrom(-1)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsNotIn((char[]) null)
        .startFrom(100)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsNotIn()
        .startFrom(0)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsNotIn()
        .startFrom(-1)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsNotIn()
        .startFrom(100)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsNotIn('a', 'b')
        .startFrom(0)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsNotIn('a', 'b')
        .startFrom(-1)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsNotIn('a', 'b')
        .startFrom(100)
        .findFirstIndexIn(null));

    assertEquals(-1, new Searcher()
        .forCharsNotIn((char[]) null)
        .startFrom(0)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsNotIn((char[]) null)
        .startFrom(-1)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsNotIn((char[]) null)
        .startFrom(100)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsNotIn()
        .startFrom(0)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsNotIn()
        .startFrom(-1)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsNotIn()
        .startFrom(100)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsNotIn('a', 'b')
        .startFrom(0)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsNotIn('a', 'b')
        .startFrom(-1)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsNotIn('a', 'b')
        .startFrom(100)
        .findFirstIndexIn(""));

    assertEquals(0, new Searcher()
        .forCharsNotIn((char[]) null)
        .startFrom(0)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(0, new Searcher()
        .forCharsNotIn((char[]) null)
        .startFrom(-1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsNotIn((char[]) null)
        .startFrom(100)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(0, new Searcher()
        .forCharsNotIn()
        .startFrom(0)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(1, new Searcher()
        .forCharsNotIn()
        .startFrom(1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(0, new Searcher()
        .forCharsNotIn()
        .startFrom(-1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsNotIn()
        .startFrom(100)
        .findFirstIndexIn("zzabyycdxx"));

    assertEquals(3, new Searcher()
        .forCharsNotIn('z', 'a', 'x')
        .startFrom(0)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(3, new Searcher()
        .forCharsNotIn('z', 'a', 'x')
        .startFrom(-1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsNotIn('z', 'a', 'x')
        .startFrom(100)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(3, new Searcher()
        .forCharsNotIn('z', 'a', 'x')
        .startFrom(1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(3, new Searcher()
        .forCharsNotIn('z', 'a', 'x')
        .startFrom(2)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(3, new Searcher()
        .forCharsNotIn('z', 'a', 'x')
        .startFrom(3)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsNotIn('z', 'a', 'x')
        .startFrom(9)
        .findFirstIndexIn("zzabyycdxx"));

    assertEquals(0, new Searcher()
        .forCharsNotIn('b', 'y', 'x')
        .startFrom(0)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(0, new Searcher()
        .forCharsNotIn('b', 'y', 'x')
        .startFrom(-1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsNotIn('b', 'y', 'x')
        .startFrom(100)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(1, new Searcher()
        .forCharsNotIn('b', 'y', 'x')
        .startFrom(1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(6, new Searcher()
        .forCharsNotIn('b', 'y', 'x')
        .startFrom(4)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(6, new Searcher()
        .forCharsNotIn('b', 'y', 'x')
        .startFrom(6)
        .findFirstIndexIn("zzabyycdxx"));

    assertEquals(0, new Searcher()
        .forCharsNotIn('z')
        .startFrom(0)
        .findFirstIndexIn("ab"));
    assertEquals(0, new Searcher()
        .forCharsNotIn('z')
        .startFrom(-1)
        .findFirstIndexIn("ab"));
    assertEquals(-1, new Searcher()
        .forCharsNotIn('z')
        .startFrom(100)
        .findFirstIndexIn("ab"));

  }

  @Test
  public void testFindFirstIndexOfCharsNotInCharSequence() {
    assertEquals(-1, new Searcher()
        .forCharsNotIn((String) null)
        .startFrom(0)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsNotIn((String) null)
        .startFrom(-1)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsNotIn((String) null)
        .startFrom(100)
        .findFirstIndexIn(null));
    assertEquals(-1,
        new Searcher().forCharsNotIn("").startFrom(0).findFirstIndexIn(null));
    assertEquals(-1,
        new Searcher().forCharsNotIn("").startFrom(-1).findFirstIndexIn(null));
    assertEquals(-1,
        new Searcher().forCharsNotIn("").startFrom(100).findFirstIndexIn(null));
    assertEquals(-1,
        new Searcher().forCharsNotIn("ab").startFrom(0).findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsNotIn("ab")
        .startFrom(-1)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsNotIn("ab")
        .startFrom(100)
        .findFirstIndexIn(null));

    assertEquals(-1, new Searcher()
        .forCharsNotIn((String) null)
        .startFrom(0)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsNotIn((String) null)
        .startFrom(-1)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsNotIn((String) null)
        .startFrom(100)
        .findFirstIndexIn(""));
    assertEquals(-1,
        new Searcher().forCharsNotIn("").startFrom(0).findFirstIndexIn(""));
    assertEquals(-1,
        new Searcher().forCharsNotIn("").startFrom(-1).findFirstIndexIn(""));
    assertEquals(-1,
        new Searcher().forCharsNotIn("").startFrom(100).findFirstIndexIn(""));
    assertEquals(-1,
        new Searcher().forCharsNotIn("ab").startFrom(0).findFirstIndexIn(""));
    assertEquals(-1,
        new Searcher().forCharsNotIn("ab").startFrom(-1).findFirstIndexIn(""));
    assertEquals(-1,
        new Searcher().forCharsNotIn("ab").startFrom(100).findFirstIndexIn(""));

    assertEquals(0, new Searcher()
        .forCharsNotIn((String) null)
        .startFrom(0)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(0, new Searcher()
        .forCharsNotIn((String) null)
        .startFrom(-1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsNotIn((String) null)
        .startFrom(100)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(0, new Searcher()
        .forCharsNotIn("")
        .startFrom(0)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(0, new Searcher()
        .forCharsNotIn("")
        .startFrom(-1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsNotIn("")
        .startFrom(100)
        .findFirstIndexIn("zzabyycdxx"));

    assertEquals(3, new Searcher()
        .forCharsNotIn("zax")
        .startFrom(0)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(3, new Searcher()
        .forCharsNotIn("zax")
        .startFrom(-1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsNotIn("zax")
        .startFrom(100)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(3, new Searcher()
        .forCharsNotIn("zax")
        .startFrom(1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(3, new Searcher()
        .forCharsNotIn("zax")
        .startFrom(2)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(3, new Searcher()
        .forCharsNotIn("zax")
        .startFrom(3)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsNotIn("zax")
        .startFrom(9)
        .findFirstIndexIn("zzabyycdxx"));

    assertEquals(0, new Searcher()
        .forCharsNotIn("byx")
        .startFrom(0)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(0, new Searcher()
        .forCharsNotIn("byx")
        .startFrom(-1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsNotIn("byx")
        .startFrom(100)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(1, new Searcher()
        .forCharsNotIn("byx")
        .startFrom(1)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(6, new Searcher()
        .forCharsNotIn("byx")
        .startFrom(4)
        .findFirstIndexIn("zzabyycdxx"));
    assertEquals(6, new Searcher()
        .forCharsNotIn("byx")
        .startFrom(6)
        .findFirstIndexIn("zzabyycdxx"));

    assertEquals(0,
        new Searcher().forCharsNotIn("zx").startFrom(0).findFirstIndexIn("ab"));
    assertEquals(0, new Searcher()
        .forCharsNotIn("zx")
        .startFrom(-1)
        .findFirstIndexIn("ab"));
    assertEquals(-1, new Searcher()
        .forCharsNotIn("zx")
        .startFrom(100)
        .findFirstIndexIn("ab"));
  }

  @Test
  public void testFindFirstIndexOfSubstring() {
    assertEquals(-1, new Searcher()
        .forSubstring(null)
        .startFrom(0)
        .ignoreCase(false)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forSubstring(null)
        .startFrom(-1)
        .ignoreCase(false)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forSubstring(null)
        .startFrom(100)
        .ignoreCase(false)
        .findFirstIndexIn(null));

    assertEquals(-1, new Searcher()
        .forSubstring(null)
        .startFrom(0)
        .ignoreCase(false)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forSubstring(null)
        .startFrom(-1)
        .ignoreCase(false)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forSubstring(null)
        .startFrom(100)
        .ignoreCase(false)
        .findFirstIndexIn(""));

    assertEquals(-1, new Searcher()
        .forSubstring(null)
        .startFrom(0)
        .ignoreCase(false)
        .findFirstIndexIn("abc"));

    assertEquals(-1, new Searcher()
        .forSubstring("")
        .startFrom(0)
        .ignoreCase(false)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forSubstring("")
        .startFrom(-1)
        .ignoreCase(false)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forSubstring("")
        .startFrom(100)
        .ignoreCase(false)
        .findFirstIndexIn(""));

    assertEquals(0, new Searcher()
        .forSubstring("a")
        .startFrom(0)
        .ignoreCase(false)
        .findFirstIndexIn("aabaabaa"));
    assertEquals(0, new Searcher()
        .forSubstring("a")
        .startFrom(-1)
        .ignoreCase(false)
        .findFirstIndexIn("aabaabaa"));
    assertEquals(-1, new Searcher()
        .forSubstring("a")
        .startFrom(100)
        .ignoreCase(false)
        .findFirstIndexIn("aabaabaa"));
    assertEquals(1, new Searcher()
        .forSubstring("a")
        .startFrom(1)
        .ignoreCase(false)
        .findFirstIndexIn("aabaabaa"));
    assertEquals(3, new Searcher()
        .forSubstring("a")
        .startFrom(2)
        .ignoreCase(false)
        .findFirstIndexIn("aabaabaa"));

    assertEquals(2, new Searcher()
        .forSubstring("b")
        .startFrom(0)
        .ignoreCase(false)
        .findFirstIndexIn("aabaabaa"));
    assertEquals(2, new Searcher()
        .forSubstring("b")
        .startFrom(-1)
        .ignoreCase(false)
        .findFirstIndexIn("aabaabaa"));
    assertEquals(-1, new Searcher()
        .forSubstring("b")
        .startFrom(100)
        .ignoreCase(false)
        .findFirstIndexIn("aabaabaa"));
    assertEquals(5, new Searcher()
        .forSubstring("b")
        .startFrom(3)
        .ignoreCase(false)
        .findFirstIndexIn("aabaabaa"));

    assertEquals(-1, new Searcher()
        .forSubstring("Ab")
        .startFrom(0)
        .ignoreCase(false)
        .findFirstIndexIn("aabaabaa"));
    assertEquals(1, new Searcher()
        .forSubstring("Ab")
        .startFrom(0)
        .ignoreCase(true)
        .findFirstIndexIn("aabaabaa"));
    assertEquals(-1, new Searcher()
        .forSubstring("aB")
        .startFrom(-1)
        .ignoreCase(false)
        .findFirstIndexIn("aabaabaa"));
    assertEquals(1, new Searcher()
        .forSubstring("aB")
        .startFrom(-1)
        .ignoreCase(true)
        .findFirstIndexIn("aabaabaa"));
    assertEquals(-1, new Searcher()
        .forSubstring("ab")
        .startFrom(100)
        .ignoreCase(false)
        .findFirstIndexIn("aabaabaa"));
    assertEquals(-1, new Searcher()
        .forSubstring("ab")
        .startFrom(100)
        .ignoreCase(true)
        .findFirstIndexIn("aabaabaa"));
    assertEquals(-1, new Searcher()
        .forSubstring("AB")
        .startFrom(2)
        .ignoreCase(false)
        .findFirstIndexIn("aabaabaa"));
    assertEquals(4, new Searcher()
        .forSubstring("AB")
        .startFrom(2)
        .ignoreCase(true)
        .findFirstIndexIn("aabaabaa"));

    assertEquals(0, new Searcher()
        .forSubstring("")
        .startFrom(0)
        .ignoreCase(false)
        .findFirstIndexIn("aabaabaa"));
    assertEquals(0, new Searcher()
        .forSubstring("")
        .startFrom(-1)
        .ignoreCase(false)
        .findFirstIndexIn("aabaabaa"));
    assertEquals(-1, new Searcher()
        .forSubstring("")
        .startFrom(100)
        .ignoreCase(false)
        .findFirstIndexIn("aabaabaa"));
    assertEquals(4, new Searcher()
        .forSubstring("")
        .startFrom(4)
        .ignoreCase(false)
        .findFirstIndexIn("aabaabaa"));
  }

  @Test
  public void testFindFiIndexOfSubtringInArray() {
    assertEquals(-1, new Searcher()
        .forSubstringsIn((CharSequence[]) null)
        .startFrom(0)
        .ignoreCase(false)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forSubstringsIn("ob", "ba")
        .startFrom(0)
        .ignoreCase(false)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forSubstringsIn((CharSequence[]) null)
        .startFrom(0)
        .ignoreCase(false)
        .findFirstIndexIn("foobar"));

    assertEquals(2, new Searcher()
        .forSubstringsIn("ob", "ba")
        .startFrom(0)
        .ignoreCase(false)
        .findFirstIndexIn("foobar"));
    assertEquals(2, new Searcher()
        .forSubstringsIn("ob", "ba")
        .startFrom(-1)
        .ignoreCase(false)
        .findFirstIndexIn("foobar"));
    assertEquals(-1, new Searcher()
        .forSubstringsIn("ob", "ba")
        .startFrom(100)
        .ignoreCase(false)
        .findFirstIndexIn("foobar"));
    assertEquals(3, new Searcher()
        .forSubstringsIn("ob", "ba")
        .startFrom(3)
        .ignoreCase(false)
        .findFirstIndexIn("foobar"));

    assertEquals(-1, new Searcher()
        .forSubstringsIn()
        .startFrom(0)
        .ignoreCase(false)
        .findFirstIndexIn("foobar"));
    assertEquals(-1, new Searcher()
        .forSubstringsIn()
        .startFrom(0)
        .ignoreCase(false)
        .findFirstIndexIn(null));
    assertEquals(-1, new Searcher()
        .forSubstringsIn()
        .startFrom(0)
        .ignoreCase(false)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forSubstringsIn("llll")
        .startFrom(0)
        .ignoreCase(false)
        .findFirstIndexIn("foobar"));

    assertEquals(0, new Searcher()
        .forSubstringsIn("")
        .startFrom(0)
        .ignoreCase(false)
        .findFirstIndexIn("foobar"));
    assertEquals(0, new Searcher()
        .forSubstringsIn("")
        .startFrom(-1)
        .ignoreCase(false)
        .findFirstIndexIn("foobar"));
    assertEquals(-1, new Searcher()
        .forSubstringsIn("")
        .startFrom(100)
        .ignoreCase(false)
        .findFirstIndexIn("foobar"));
    assertEquals(4, new Searcher()
        .forSubstringsIn("")
        .startFrom(4)
        .ignoreCase(false)
        .findFirstIndexIn("foobar"));

    assertEquals(-1, new Searcher()
        .forSubstringsIn("")
        .startFrom(0)
        .ignoreCase(false)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forSubstringsIn("a")
        .startFrom(0)
        .ignoreCase(false)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forSubstringsIn((String) null)
        .startFrom(0)
        .ignoreCase(false)
        .findFirstIndexIn(""));
    assertEquals(-1, new Searcher()
        .forSubstringsIn((String) null)
        .startFrom(0)
        .ignoreCase(false)
        .findFirstIndexIn("foobar"));
    assertEquals(0, new Searcher()
        .forSubstringsIn(null, "")
        .startFrom(0)
        .ignoreCase(false)
        .findFirstIndexIn("foobar"));
    assertEquals(-1, new Searcher()
        .forSubstringsIn((String) null)
        .startFrom(0)
        .ignoreCase(false)
        .findFirstIndexIn(null));

    assertEquals(2, new Searcher()
        .forSubstringsIn("ob", null)
        .startFrom(-1)
        .ignoreCase(false)
        .findFirstIndexIn("foobar"));
  }

  @Test
  public void testFindLastIndexOfChar() {
    assertEquals(-1,
        new Searcher().forChar(' ').endBefore(0).findLastIndexIn(null));
    assertEquals(-1,
        new Searcher().forChar(' ').endBefore(-1).findLastIndexIn(null));
    assertEquals(-1,
        new Searcher().forChar(' ').endBefore(0).findLastIndexIn(""));
    assertEquals(-1,
        new Searcher().forChar(' ').endBefore(-1).findLastIndexIn(""));

    assertEquals(7,
        new Searcher().forChar('a').endBefore(8).findLastIndexIn("aabaabaa"));
    assertEquals(7, new Searcher()
        .forChar('a')
        .endBefore(Integer.MAX_VALUE)
        .findLastIndexIn("aabaabaa"));
    assertEquals(5,
        new Searcher().forChar('b').endBefore(8).findLastIndexIn("aabaabaa"));
    assertEquals(2,
        new Searcher().forChar('b').endBefore(3).findLastIndexIn("aabaabaa"));
    assertEquals(5,
        new Searcher().forChar('b').endBefore(9).findLastIndexIn("aabaabaa"));
    assertEquals(5,
        new Searcher().forChar('b').endBefore(100).findLastIndexIn("aabaabaa"));
    assertEquals(-1,
        new Searcher().forChar('b').endBefore(-1).findLastIndexIn("aabaabaa"));
    assertEquals(0,
        new Searcher().forChar('a').endBefore(0).findLastIndexIn("aabaabaa"));
  }

  @Test
  public void testFindLastIndexOfCharsSatisfy() {
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(null)
        .startFrom(0)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(null)
        .startFrom(-1)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(null)
        .startFrom(100)
        .findLastIndexIn(null));

    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter(""))
        .startFrom(0)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter(""))
        .startFrom(-1)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter(""))
        .startFrom(100)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("ab"))
        .startFrom(0)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("ab"))
        .startFrom(-1)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("ab"))
        .startFrom(100)
        .findLastIndexIn(null));

    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("")))
        .startFrom(0)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("")))
        .startFrom(-1)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("")))
        .startFrom(100)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("ab")))
        .startFrom(0)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("ab")))
        .startFrom(-1)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("ab")))
        .startFrom(100)
        .findLastIndexIn(null));

    assertEquals(-1,
        new Searcher().forCharsSatisfy(null).startFrom(0).findLastIndexIn(""));
    assertEquals(-1,
        new Searcher().forCharsSatisfy(null).startFrom(-1).findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(null)
        .startFrom(100)
        .findLastIndexIn(""));

    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter(""))
        .startFrom(0)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter(""))
        .startFrom(-1)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter(""))
        .startFrom(100)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("ab"))
        .startFrom(0)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("ab"))
        .startFrom(-1)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("ab"))
        .startFrom(100)
        .findLastIndexIn(""));

    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("")))
        .startFrom(0)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("")))
        .startFrom(-1)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("")))
        .startFrom(100)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("ab")))
        .startFrom(0)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("ab")))
        .startFrom(-1)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("ab")))
        .startFrom(100)
        .findLastIndexIn(""));

    assertEquals(-1, new Searcher()
        .forCharsSatisfy(null)
        .startFrom(0)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(null)
        .startFrom(-1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(null)
        .startFrom(100)
        .findLastIndexIn("zzabyycdxx"));

    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter(""))
        .startFrom(0)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter(""))
        .startFrom(-1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter(""))
        .startFrom(100)
        .findLastIndexIn("zzabyycdxx"));

    assertEquals(0, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("")))
        .startFrom(0)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("")))
        .startFrom(-1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(9, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("")))
        .startFrom(100)
        .findLastIndexIn("zzabyycdxx"));

    assertEquals(0, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("zax"))
        .startFrom(0)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("zax"))
        .startFrom(-1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(9, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("zax"))
        .startFrom(100)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("zax"))
        .startFrom(1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(2, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("zax"))
        .startFrom(2)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(2, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("zax"))
        .startFrom(3)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(2, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("zax"))
        .startFrom(7)
        .findLastIndexIn("zzabyycdxx"));

    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("zax")))
        .startFrom(0)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("zax")))
        .startFrom(-1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(7, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("zax")))
        .startFrom(100)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("zax")))
        .startFrom(1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("zax")))
        .startFrom(2)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(3, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("zax")))
        .startFrom(3)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(7, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("zax")))
        .startFrom(9)
        .findLastIndexIn("zzabyycdxx"));

    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("byx"))
        .startFrom(0)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("byx"))
        .startFrom(-1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(9, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("byx"))
        .startFrom(100)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("byx"))
        .startFrom(1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(4, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("byx"))
        .startFrom(4)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(5, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("byx"))
        .startFrom(6)
        .findLastIndexIn("zzabyycdxx"));

    assertEquals(0, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("byx")))
        .startFrom(0)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("byx")))
        .startFrom(-1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(7, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("byx")))
        .startFrom(100)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("byx")))
        .startFrom(1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(2, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("byx")))
        .startFrom(4)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(7, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("byx")))
        .startFrom(7)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(7, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("byx")))
        .startFrom(8)
        .findLastIndexIn("zzabyycdxx"));

    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("zx"))
        .startFrom(0)
        .findLastIndexIn("ab"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("zx"))
        .startFrom(-1)
        .findLastIndexIn("ab"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(new InStringCharFilter("zx"))
        .startFrom(100)
        .findLastIndexIn("ab"));

    assertEquals(0, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("zx")))
        .startFrom(0)
        .findLastIndexIn("ab"));
    assertEquals(-1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("zx")))
        .startFrom(-1)
        .findLastIndexIn("ab"));
    assertEquals(1, new Searcher()
        .forCharsSatisfy(CharFilter.not(new InStringCharFilter("zx")))
        .startFrom(100)
        .findLastIndexIn("ab"));
  }

  @Test
  public void testFindLastIndexOfCharsInArray() {
    assertEquals(-1, new Searcher()
        .forCharsIn((char[]) null)
        .startFrom(0)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsIn((char[]) null)
        .startFrom(-1)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsIn((char[]) null)
        .startFrom(100)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsIn()
        .startFrom(0)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsIn()
        .startFrom(-1)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsIn()
        .startFrom(100)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsIn('a', 'b')
        .startFrom(0)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsIn('a', 'b')
        .startFrom(-1)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsIn('a', 'b')
        .startFrom(100)
        .findLastIndexIn(null));

    assertEquals(-1, new Searcher()
        .forCharsIn((char[]) null)
        .startFrom(0)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsIn((char[]) null)
        .startFrom(-1)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsIn((char[]) null)
        .startFrom(100)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsIn()
        .startFrom(0)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsIn()
        .startFrom(-1)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsIn()
        .startFrom(100)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsIn('a', 'b')
        .startFrom(0)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsIn('a', 'b')
        .startFrom(-1)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsIn('a', 'b')
        .startFrom(100)
        .findLastIndexIn(""));

    assertEquals(-1, new Searcher()
        .forCharsIn((char[]) null)
        .startFrom(0)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn((char[]) null)
        .startFrom(-1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn((char[]) null)
        .startFrom(100)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn()
        .startFrom(0)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn()
        .startFrom(-1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn()
        .startFrom(100)
        .findLastIndexIn("zzabyycdxx"));

    assertEquals(0, new Searcher()
        .forCharsIn('z', 'a', 'x')
        .startFrom(0)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn('z', 'a', 'x')
        .startFrom(-1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(9, new Searcher()
        .forCharsIn('z', 'a', 'x')
        .startFrom(100)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(1, new Searcher()
        .forCharsIn('z', 'a', 'x')
        .startFrom(1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(2, new Searcher()
        .forCharsIn('z', 'a', 'x')
        .startFrom(2)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(2, new Searcher()
        .forCharsIn('z', 'a', 'x')
        .startFrom(3)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(9, new Searcher()
        .forCharsIn('z', 'a', 'x')
        .startFrom(9)
        .findLastIndexIn("zzabyycdxx"));

    assertEquals(-1, new Searcher()
        .forCharsIn('b', 'y', 'x')
        .startFrom(0)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn('b', 'y', 'x')
        .startFrom(-1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(9, new Searcher()
        .forCharsIn('b', 'y', 'x')
        .startFrom(100)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn('b', 'y', 'x')
        .startFrom(1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(4, new Searcher()
        .forCharsIn('b', 'y', 'x')
        .startFrom(4)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(5, new Searcher()
        .forCharsIn('b', 'y', 'x')
        .startFrom(6)
        .findLastIndexIn("zzabyycdxx"));

    assertEquals(-1, new Searcher()
        .forCharsIn('z')
        .startFrom(0)
        .findLastIndexIn("ab"));
    assertEquals(-1, new Searcher()
        .forCharsIn('z')
        .startFrom(-1)
        .findLastIndexIn("ab"));
    assertEquals(-1, new Searcher()
        .forCharsIn('z')
        .startFrom(100)
        .findLastIndexIn("ab"));
  }

  @Test
  public void testFindLastIndexOfCharsInCharSequence() {
    assertEquals(-1, new Searcher()
        .forCharsIn((String) null)
        .startFrom(0)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsIn((String) null)
        .startFrom(-1)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsIn((String) null)
        .startFrom(100)
        .findLastIndexIn(null));
    assertEquals(-1,
        new Searcher().forCharsIn("").startFrom(0).findLastIndexIn(null));
    assertEquals(-1,
        new Searcher().forCharsIn("").startFrom(-1).findLastIndexIn(null));
    assertEquals(-1,
        new Searcher().forCharsIn("").startFrom(100).findLastIndexIn(null));
    assertEquals(-1,
        new Searcher().forCharsIn("ab").startFrom(0).findLastIndexIn(null));
    assertEquals(-1,
        new Searcher().forCharsIn("ab").startFrom(-1).findLastIndexIn(null));
    assertEquals(-1,
        new Searcher().forCharsIn("ab").startFrom(100).findLastIndexIn(null));

    assertEquals(-1, new Searcher()
        .forCharsIn((String) null)
        .startFrom(0)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsIn((String) null)
        .startFrom(-1)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsIn((String) null)
        .startFrom(100)
        .findLastIndexIn(""));
    assertEquals(-1,
        new Searcher().forCharsIn("").startFrom(0).findLastIndexIn(""));
    assertEquals(-1,
        new Searcher().forCharsIn("").startFrom(-1).findLastIndexIn(""));
    assertEquals(-1,
        new Searcher().forCharsIn("").startFrom(100).findLastIndexIn(""));
    assertEquals(-1,
        new Searcher().forCharsIn("ab").startFrom(0).findLastIndexIn(""));
    assertEquals(-1,
        new Searcher().forCharsIn("ab").startFrom(-1).findLastIndexIn(""));
    assertEquals(-1,
        new Searcher().forCharsIn("ab").startFrom(100).findLastIndexIn(""));

    assertEquals(-1, new Searcher()
        .forCharsIn((String) null)
        .startFrom(0)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn((String) null)
        .startFrom(-1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn((String) null)
        .startFrom(100)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn("")
        .startFrom(0)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn("")
        .startFrom(-1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn("")
        .startFrom(100)
        .findLastIndexIn("zzabyycdxx"));

    assertEquals(0, new Searcher()
        .forCharsIn("zax")
        .startFrom(0)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn("zax")
        .startFrom(-1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(9, new Searcher()
        .forCharsIn("zax")
        .startFrom(100)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(1, new Searcher()
        .forCharsIn("zax")
        .startFrom(1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(2, new Searcher()
        .forCharsIn("zax")
        .startFrom(2)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(2, new Searcher()
        .forCharsIn("zax")
        .startFrom(3)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(2, new Searcher()
        .forCharsIn("zax")
        .startFrom(7)
        .findLastIndexIn("zzabyycdxx"));

    assertEquals(-1, new Searcher()
        .forCharsIn("byx")
        .startFrom(0)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn("byx")
        .startFrom(-1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(9, new Searcher()
        .forCharsIn("byx")
        .startFrom(100)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsIn("byx")
        .startFrom(1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(4, new Searcher()
        .forCharsIn("byx")
        .startFrom(4)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(5, new Searcher()
        .forCharsIn("byx")
        .startFrom(6)
        .findLastIndexIn("zzabyycdxx"));

    assertEquals(-1,
        new Searcher().forCharsIn("zx").startFrom(0).findLastIndexIn("ab"));
    assertEquals(-1,
        new Searcher().forCharsIn("zx").startFrom(-1).findLastIndexIn("ab"));
    assertEquals(-1,
        new Searcher().forCharsIn("zx").startFrom(100).findLastIndexIn("ab"));
  }

  @Test
  public void testFindLastIndexOfCharsNotInArray() {
    assertEquals(-1, new Searcher()
        .forCharsNotIn((char[]) null)
        .startFrom(0)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsNotIn((char[]) null)
        .startFrom(-1)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsNotIn((char[]) null)
        .startFrom(100)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsNotIn()
        .startFrom(0)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsNotIn()
        .startFrom(-1)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsNotIn()
        .startFrom(100)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsNotIn('a', 'b')
        .startFrom(0)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsNotIn('a', 'b')
        .startFrom(-1)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsNotIn('a', 'b')
        .startFrom(100)
        .findLastIndexIn(null));

    assertEquals(-1, new Searcher()
        .forCharsNotIn((char[]) null)
        .startFrom(0)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsNotIn((char[]) null)
        .startFrom(-1)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsNotIn((char[]) null)
        .startFrom(100)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsNotIn()
        .startFrom(0)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsNotIn()
        .startFrom(-1)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsNotIn()
        .startFrom(100)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsNotIn('a', 'b')
        .startFrom(0)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsNotIn('a', 'b')
        .startFrom(-1)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsNotIn('a', 'b')
        .startFrom(100)
        .findLastIndexIn(""));

    assertEquals(0, new Searcher()
        .forCharsNotIn((char[]) null)
        .startFrom(0)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsNotIn((char[]) null)
        .startFrom(-1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(9, new Searcher()
        .forCharsNotIn((char[]) null)
        .startFrom(100)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(0, new Searcher()
        .forCharsNotIn()
        .startFrom(0)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(1, new Searcher()
        .forCharsNotIn()
        .startFrom(1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsNotIn()
        .startFrom(-1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(9, new Searcher()
        .forCharsNotIn()
        .startFrom(100)
        .findLastIndexIn("zzabyycdxx"));

    assertEquals(-1, new Searcher()
        .forCharsNotIn('z', 'a', 'x')
        .startFrom(0)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsNotIn('z', 'a', 'x')
        .startFrom(-1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(7, new Searcher()
        .forCharsNotIn('z', 'a', 'x')
        .startFrom(100)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsNotIn('z', 'a', 'x')
        .startFrom(1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsNotIn('z', 'a', 'x')
        .startFrom(2)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(3, new Searcher()
        .forCharsNotIn('z', 'a', 'x')
        .startFrom(3)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(7, new Searcher()
        .forCharsNotIn('z', 'a', 'x')
        .startFrom(9)
        .findLastIndexIn("zzabyycdxx"));

    assertEquals(0, new Searcher()
        .forCharsNotIn('b', 'y', 'x')
        .startFrom(0)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsNotIn('b', 'y', 'x')
        .startFrom(-1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(7, new Searcher()
        .forCharsNotIn('b', 'y', 'x')
        .startFrom(100)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(1, new Searcher()
        .forCharsNotIn('b', 'y', 'x')
        .startFrom(1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(2, new Searcher()
        .forCharsNotIn('b', 'y', 'x')
        .startFrom(4)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(6, new Searcher()
        .forCharsNotIn('b', 'y', 'x')
        .startFrom(6)
        .findLastIndexIn("zzabyycdxx"));

    assertEquals(0, new Searcher()
        .forCharsNotIn('z')
        .startFrom(0)
        .findLastIndexIn("ab"));
    assertEquals(-1, new Searcher()
        .forCharsNotIn('z')
        .startFrom(-1)
        .findLastIndexIn("ab"));
    assertEquals(1, new Searcher()
        .forCharsNotIn('z')
        .startFrom(100)
        .findLastIndexIn("ab"));
  }

  @Test
  public void testFindLastIndexOfCharsNotInCharSequence() {
    assertEquals(-1, new Searcher()
        .forCharsNotIn((String) null)
        .startFrom(0)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsNotIn((String) null)
        .startFrom(-1)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsNotIn((String) null)
        .startFrom(100)
        .findLastIndexIn(null));
    assertEquals(-1,
        new Searcher().forCharsNotIn("").startFrom(0).findLastIndexIn(null));
    assertEquals(-1,
        new Searcher().forCharsNotIn("").startFrom(-1).findLastIndexIn(null));
    assertEquals(-1,
        new Searcher().forCharsNotIn("").startFrom(100).findLastIndexIn(null));
    assertEquals(-1,
        new Searcher().forCharsNotIn("ab").startFrom(0).findLastIndexIn(null));
    assertEquals(-1,
        new Searcher().forCharsNotIn("ab").startFrom(-1).findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forCharsNotIn("ab")
        .startFrom(100)
        .findLastIndexIn(null));

    assertEquals(-1, new Searcher()
        .forCharsNotIn((String) null)
        .startFrom(0)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsNotIn((String) null)
        .startFrom(-1)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forCharsNotIn((String) null)
        .startFrom(100)
        .findLastIndexIn(""));
    assertEquals(-1,
        new Searcher().forCharsNotIn("").startFrom(0).findLastIndexIn(""));
    assertEquals(-1,
        new Searcher().forCharsNotIn("").startFrom(-1).findLastIndexIn(""));
    assertEquals(-1,
        new Searcher().forCharsNotIn("").startFrom(100).findLastIndexIn(""));
    assertEquals(-1,
        new Searcher().forCharsNotIn("ab").startFrom(0).findLastIndexIn(""));
    assertEquals(-1,
        new Searcher().forCharsNotIn("ab").startFrom(-1).findLastIndexIn(""));
    assertEquals(-1,
        new Searcher().forCharsNotIn("ab").startFrom(100).findLastIndexIn(""));

    assertEquals(0, new Searcher()
        .forCharsNotIn((String) null)
        .startFrom(0)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsNotIn((String) null)
        .startFrom(-1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(9, new Searcher()
        .forCharsNotIn((String) null)
        .startFrom(100)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(0, new Searcher()
        .forCharsNotIn("")
        .startFrom(0)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(1, new Searcher()
        .forCharsNotIn("")
        .startFrom(1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsNotIn("")
        .startFrom(-1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(9, new Searcher()
        .forCharsNotIn("")
        .startFrom(100)
        .findLastIndexIn("zzabyycdxx"));

    assertEquals(-1, new Searcher()
        .forCharsNotIn("zax")
        .startFrom(0)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsNotIn("zax")
        .startFrom(-1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(7, new Searcher()
        .forCharsNotIn("zax")
        .startFrom(100)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsNotIn("zax")
        .startFrom(1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsNotIn("zax")
        .startFrom(2)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(3, new Searcher()
        .forCharsNotIn("zax")
        .startFrom(3)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(7, new Searcher()
        .forCharsNotIn("zax")
        .startFrom(9)
        .findLastIndexIn("zzabyycdxx"));

    assertEquals(0, new Searcher()
        .forCharsNotIn("byx")
        .startFrom(0)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(-1, new Searcher()
        .forCharsNotIn("byx")
        .startFrom(-1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(7, new Searcher()
        .forCharsNotIn("byx")
        .startFrom(100)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(1, new Searcher()
        .forCharsNotIn("byx")
        .startFrom(1)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(2, new Searcher()
        .forCharsNotIn("byx")
        .startFrom(4)
        .findLastIndexIn("zzabyycdxx"));
    assertEquals(6, new Searcher()
        .forCharsNotIn("byx")
        .startFrom(6)
        .findLastIndexIn("zzabyycdxx"));

    assertEquals(0,
        new Searcher().forCharsNotIn("zx").startFrom(0).findLastIndexIn("ab"));
    assertEquals(-1,
        new Searcher().forCharsNotIn("zx").startFrom(-1).findLastIndexIn("ab"));
    assertEquals(1, new Searcher()
        .forCharsNotIn("zx")
        .startFrom(100)
        .findLastIndexIn("ab"));
  }

  @Test
  public void testFindLastIndexOfSubstring() {
    assertEquals(-1, new Searcher()
        .forSubstring(null)
        .startFrom(0)
        .ignoreCase(false)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forSubstring(null)
        .startFrom(-1)
        .ignoreCase(false)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forSubstring(null)
        .startFrom(100)
        .ignoreCase(false)
        .findLastIndexIn(null));

    assertEquals(-1, new Searcher()
        .forSubstring(null)
        .startFrom(0)
        .ignoreCase(false)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forSubstring(null)
        .startFrom(-1)
        .ignoreCase(false)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forSubstring(null)
        .startFrom(100)
        .ignoreCase(false)
        .findLastIndexIn(""));

    assertEquals(-1, new Searcher()
        .forSubstring("")
        .startFrom(0)
        .ignoreCase(false)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forSubstring("")
        .startFrom(-1)
        .ignoreCase(false)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forSubstring("")
        .startFrom(100)
        .ignoreCase(false)
        .findLastIndexIn(""));

    assertEquals(0, new Searcher()
        .forSubstring("a")
        .startFrom(0)
        .ignoreCase(false)
        .findLastIndexIn("aabaabaa"));
    assertEquals(-1, new Searcher()
        .forSubstring("a")
        .startFrom(-1)
        .ignoreCase(false)
        .findLastIndexIn("aabaabaa"));
    assertEquals(7, new Searcher()
        .forSubstring("a")
        .startFrom(100)
        .ignoreCase(false)
        .findLastIndexIn("aabaabaa"));
    assertEquals(1, new Searcher()
        .forSubstring("a")
        .startFrom(1)
        .ignoreCase(false)
        .findLastIndexIn("aabaabaa"));
    assertEquals(1, new Searcher()
        .forSubstring("a")
        .startFrom(2)
        .ignoreCase(false)
        .findLastIndexIn("aabaabaa"));

    assertEquals(-1, new Searcher()
        .forSubstring("b")
        .startFrom(0)
        .ignoreCase(false)
        .findLastIndexIn("aabaabaa"));
    assertEquals(-1, new Searcher()
        .forSubstring("b")
        .startFrom(-1)
        .ignoreCase(false)
        .findLastIndexIn("aabaabaa"));
    assertEquals(5, new Searcher()
        .forSubstring("b")
        .startFrom(100)
        .ignoreCase(false)
        .findLastIndexIn("aabaabaa"));
    assertEquals(2, new Searcher()
        .forSubstring("b")
        .startFrom(3)
        .ignoreCase(false)
        .findLastIndexIn("aabaabaa"));

    assertEquals(-1, new Searcher()
        .forSubstring("ab")
        .startFrom(0)
        .ignoreCase(false)
        .findLastIndexIn("aabaabaa"));
    assertEquals(-1, new Searcher()
        .forSubstring("ab")
        .startFrom(-1)
        .ignoreCase(false)
        .findLastIndexIn("aabaabaa"));
    assertEquals(4, new Searcher()
        .forSubstring("ab")
        .startFrom(100)
        .ignoreCase(false)
        .findLastIndexIn("aabaabaa"));
    assertEquals(1, new Searcher()
        .forSubstring("ab")
        .startFrom(3)
        .ignoreCase(false)
        .findLastIndexIn("aabaabaa"));

    assertEquals(0, new Searcher()
        .forSubstring("")
        .startFrom(0)
        .ignoreCase(false)
        .findLastIndexIn("aabaabaa"));
    assertEquals(-1, new Searcher()
        .forSubstring("")
        .startFrom(-1)
        .ignoreCase(false)
        .findLastIndexIn("aabaabaa"));
    assertEquals(7, new Searcher()
        .forSubstring("")
        .startFrom(100)
        .ignoreCase(false)
        .findLastIndexIn("aabaabaa"));
    assertEquals(4, new Searcher()
        .forSubstring("")
        .startFrom(4)
        .ignoreCase(false)
        .findLastIndexIn("aabaabaa"));
  }

  @Test
  public void testLastIndexOfSubstringInArray() {
    assertEquals(-1, new Searcher()
        .forSubstringsIn((CharSequence[]) null)
        .startFrom(0)
        .ignoreCase(false)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forSubstringsIn(new String[]{"ob", "ba"})
        .startFrom(0)
        .ignoreCase(false)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forSubstringsIn((CharSequence[]) null)
        .startFrom(0)
        .ignoreCase(false)
        .findLastIndexIn("foobar"));

    assertEquals(-1, new Searcher()
        .forSubstringsIn("ob", "ba")
        .startFrom(0)
        .ignoreCase(false)
        .findLastIndexIn("foobar"));
    assertEquals(-1, new Searcher()
        .forSubstringsIn("ob", "ba")
        .startFrom(-1)
        .ignoreCase(false)
        .findLastIndexIn("foobar"));
    assertEquals(3, new Searcher()
        .forSubstringsIn("ob", "ba")
        .startFrom(100)
        .ignoreCase(false)
        .findLastIndexIn("foobar"));
    assertEquals(3, new Searcher()
        .forSubstringsIn("ob", "ba")
        .startFrom(3)
        .ignoreCase(false)
        .findLastIndexIn("foobar"));

    assertEquals(-1, new Searcher()
        .forSubstringsIn()
        .startFrom(0)
        .ignoreCase(false)
        .findLastIndexIn("foobar"));
    assertEquals(-1, new Searcher()
        .forSubstringsIn()
        .startFrom(0)
        .ignoreCase(false)
        .findLastIndexIn(null));
    assertEquals(-1, new Searcher()
        .forSubstringsIn()
        .startFrom(0)
        .ignoreCase(false)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forSubstringsIn("llll")
        .startFrom(0)
        .ignoreCase(false)
        .findLastIndexIn("foobar"));

    assertEquals(0, new Searcher()
        .forSubstringsIn("")
        .startFrom(0)
        .ignoreCase(false)
        .findLastIndexIn("foobar"));
    assertEquals(-1, new Searcher()
        .forSubstringsIn("")
        .startFrom(-1)
        .ignoreCase(false)
        .findLastIndexIn("foobar"));
    assertEquals(5, new Searcher()
        .forSubstringsIn("")
        .startFrom(100)
        .ignoreCase(false)
        .findLastIndexIn("foobar"));
    assertEquals(4, new Searcher()
        .forSubstringsIn("")
        .startFrom(4)
        .ignoreCase(false)
        .findLastIndexIn("foobar"));

    assertEquals(-1, new Searcher()
        .forSubstringsIn("")
        .startFrom(0)
        .ignoreCase(false)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forSubstringsIn("a")
        .startFrom(0)
        .ignoreCase(false)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forSubstringsIn((String) null)
        .startFrom(0)
        .ignoreCase(false)
        .findLastIndexIn(""));
    assertEquals(-1, new Searcher()
        .forSubstringsIn((String) null)
        .startFrom(0)
        .ignoreCase(false)
        .findLastIndexIn("foobar"));
    assertEquals(5, new Searcher()
        .forSubstringsIn(null, "")
        .startFrom(100)
        .ignoreCase(false)
        .findLastIndexIn("foobar"));
    assertEquals(-1, new Searcher()
        .forSubstringsIn((String) null)
        .startFrom(0)
        .ignoreCase(false)
        .findLastIndexIn(null));

    assertEquals(2, new Searcher()
        .forSubstringsIn("ob", null)
        .startFrom(100)
        .ignoreCase(false)
        .findLastIndexIn("foobar"));
  }

  @Test
  public void testContainsOnly_String() {
    final String str1 = "a";
    final String str2 = "b";
    final String str3 = "ab";
    final String chars1 = "b";
    final String chars2 = "a";
    final String chars3 = "ab";
    assertEquals(false, StringUtils.containsOnly(null, (String) null));
    assertEquals(false, StringUtils.containsOnly("", (String) null));
    assertEquals(false, StringUtils.containsOnly(null, ""));
    assertEquals(false, StringUtils.containsOnly(str1, ""));
    assertEquals(true, StringUtils.containsOnly("", ""));
    assertEquals(true, StringUtils.containsOnly("", chars1));
    assertEquals(false, StringUtils.containsOnly(str1, chars1));
    assertEquals(true, StringUtils.containsOnly(str1, chars2));
    assertEquals(true, StringUtils.containsOnly(str1, chars3));
    assertEquals(true, StringUtils.containsOnly(str2, chars1));
    assertEquals(false, StringUtils.containsOnly(str2, chars2));
    assertEquals(true, StringUtils.containsOnly(str2, chars3));
    assertEquals(false, StringUtils.containsOnly(str3, chars1));
    assertEquals(false, StringUtils.containsOnly(str3, chars2));
    assertEquals(true, StringUtils.containsOnly(str3, chars3));
  }

  @Test
  public void testContainsOnly_Chararray() {
    final String str1 = "a";
    final String str2 = "b";
    final String str3 = "ab";
    final char[] chars1 = {'b'};
    final char[] chars2 = {'a'};
    final char[] chars3 = {'a', 'b'};
    final char[] emptyChars = new char[0];
    assertEquals(false, StringUtils.containsOnly(null, (char[]) null));
    assertEquals(false, StringUtils.containsOnly("", (char[]) null));
    assertEquals(false, StringUtils.containsOnly(null, emptyChars));
    assertEquals(false, StringUtils.containsOnly(str1, emptyChars));
    assertEquals(true, StringUtils.containsOnly("", emptyChars));
    assertEquals(true, StringUtils.containsOnly("", chars1));
    assertEquals(false, StringUtils.containsOnly(str1, chars1));
    assertEquals(true, StringUtils.containsOnly(str1, chars2));
    assertEquals(true, StringUtils.containsOnly(str1, chars3));
    assertEquals(true, StringUtils.containsOnly(str2, chars1));
    assertEquals(false, StringUtils.containsOnly(str2, chars2));
    assertEquals(true, StringUtils.containsOnly(str2, chars3));
    assertEquals(false, StringUtils.containsOnly(str3, chars1));
    assertEquals(false, StringUtils.containsOnly(str3, chars2));
    assertEquals(true, StringUtils.containsOnly(str3, chars3));
  }

  //  TODO
  //  @Test
  //  public void testContainsNone_String() {
  //    final String str1 = "a";
  //    final String str2 = "b";
  //    final String str3 = "ab.";
  //    final String chars1= "b";
  //    final String chars2= ".";
  //    final String chars3= "cd";
  //    assertEquals(true, containsNone(null, (String) null));
  //    assertEquals(true, containsNone("", (String) null));
  //    assertEquals(true, containsNone(null, ""));
  //    assertEquals(true, containsNone(str1, ""));
  //    assertEquals(true, containsNone("", ""));
  //    assertEquals(true, containsNone("", chars1));
  //    assertEquals(true, containsNone(str1, chars1));
  //    assertEquals(true, containsNone(str1, chars2));
  //    assertEquals(true, containsNone(str1, chars3));
  //    assertEquals(false, containsNone(str2, chars1));
  //    assertEquals(true, containsNone(str2, chars2));
  //    assertEquals(true, containsNone(str2, chars3));
  //    assertEquals(false, containsNone(str3, chars1));
  //    assertEquals(false, containsNone(str3, chars2));
  //    assertEquals(true, containsNone(str3, chars3));
  //  }

  //  @Test
  //  public void testContainsNone_Chararray() {
  //    final String str1 = "a";
  //    final String str2 = "b";
  //    final String str3 = "ab.";
  //    final char[] chars1= {'b'};
  //    final char[] chars2= {'.'};
  //    final char[] chars3= {'c', 'd'};
  //    final char[] emptyChars = new char[0];
  //    assertEquals(true, containsNone(null, (char[]) null));
  //    assertEquals(true, containsNone("", (char[]) null));
  //    assertEquals(true, containsNone(null, emptyChars));
  //    assertEquals(true, containsNone(str1, emptyChars));
  //    assertEquals(true, containsNone("", emptyChars));
  //    assertEquals(true, containsNone("", chars1));
  //    assertEquals(true, containsNone(str1, chars1));
  //    assertEquals(true, containsNone(str1, chars2));
  //    assertEquals(true, containsNone(str1, chars3));
  //    assertEquals(false, containsNone(str2, chars1));
  //    assertEquals(true, containsNone(str2, chars2));
  //    assertEquals(true, containsNone(str2, chars3));
  //    assertEquals(false, containsNone(str3, chars1));
  //    assertEquals(false, containsNone(str3, chars2));
  //    assertEquals(true, containsNone(str3, chars3));
  //  }

  @Test
  public void testCountMatchesOfChar() {
    assertEquals(0, new Searcher().forChar('\0').countMatchesIn(null));
    assertEquals(0, new Searcher().forChar('\0').countMatchesIn(""));
    assertEquals(0, new Searcher().forChar('\0').countMatchesIn("hello world"));
    assertEquals(1, new Searcher().forChar('h').countMatchesIn("hello world"));
    assertEquals(2, new Searcher().forChar('o').countMatchesIn("hello world"));
    assertEquals(3, new Searcher().forChar('l').countMatchesIn("hello world"));
  }

  @Test
  public void testCountMatchesOfSubstring() {
    assertEquals(0, new Searcher().forSubstring(null).countMatchesIn(null));
    assertEquals(0, new Searcher().forSubstring(null).countMatchesIn("blah"));
    assertEquals(0, new Searcher().forSubstring("DD").countMatchesIn(null));
    assertEquals(0, new Searcher().forSubstring("").countMatchesIn("x"));
    assertEquals(0, new Searcher().forSubstring("").countMatchesIn(""));
    assertEquals(3, new Searcher()
        .forSubstring("one")
        .countMatchesIn("one long someone sentence of one"));
    assertEquals(0, new Searcher()
        .forSubstring("two")
        .countMatchesIn("one long someone sentence of one"));
    assertEquals(4,
        new Searcher().forSubstring("ooo").countMatchesIn("oooooooooooo"));
  }
}
