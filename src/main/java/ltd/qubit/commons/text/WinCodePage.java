////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.HashMap;
import java.util.Map;

/**
 * A class provides a function to convert a Windows code page to a Java charset.
 *
 * @author Haixing Hu
 * @see <a href="http://msdn.microsoft.com/en-us/library/dd317756(VS.85).aspx">
 *   Windows Code Page Identifiers</a>
 */
public final class WinCodePage {
  private static final Map<Integer, String> CODE_PAGE_MAP;

  public static Charset toCharset(final int codePage) {
    final String name = CODE_PAGE_MAP.get(codePage);
    if (name == null) {
      return null;
    } else {
      try {
        return Charset.forName(name);
      } catch (final IllegalCharsetNameException e) {
        return null; // Suppress the exception
      }
    }
  }

  static {
    CODE_PAGE_MAP = new HashMap<>();
    //  stop checkstyle: MagicNumberCheck
    CODE_PAGE_MAP.put(20127, "ASCII");
    CODE_PAGE_MAP.put(28591, "ISO-8859-1");
    CODE_PAGE_MAP.put(28592, "ISO-8859-2");
    CODE_PAGE_MAP.put(28593, "ISO-8859-3");
    CODE_PAGE_MAP.put(28594, "ISO-8859-4");
    CODE_PAGE_MAP.put(28595, "ISO-8859-5");
    CODE_PAGE_MAP.put(28596, "ISO-8859-6");
    CODE_PAGE_MAP.put(28597, "ISO-8859-7");
    CODE_PAGE_MAP.put(28598, "ISO-8859-8");
    CODE_PAGE_MAP.put(28599, "ISO-8859-9");
    CODE_PAGE_MAP.put(932, "SHIFT_JIS");
    CODE_PAGE_MAP.put(51932, "EUC-JP");
    CODE_PAGE_MAP.put(949, "KS_C_5601-1987");
    CODE_PAGE_MAP.put(50225, "ISO-2022-KR");
    CODE_PAGE_MAP.put(51949, "EUC-KR");
    CODE_PAGE_MAP.put(50222, "ISO-2022-JP");
    CODE_PAGE_MAP.put(709, "ASMO_449");
    CODE_PAGE_MAP.put(38598, "ISO-8859-8-I");
    CODE_PAGE_MAP.put(50227, "ISO-2022-CN");
    //    codePageMap.put(50227, "ISO-2022-CN-EXT");
    CODE_PAGE_MAP.put(65001, "UTF-8");
    CODE_PAGE_MAP.put(28603, "ISO-8859-13");
    CODE_PAGE_MAP.put(28605, "ISO-8859-15");
    CODE_PAGE_MAP.put(936, "GBK");
    //    codePageMap.put(936, "GB2312");
    CODE_PAGE_MAP.put(54936, "GB18030");
    CODE_PAGE_MAP.put(65000, "UTF-7");
    CODE_PAGE_MAP.put(1201, "UTF-16BE");
    CODE_PAGE_MAP.put(1200, "UTF-16LE");
    CODE_PAGE_MAP.put(12001, "UTF-32BE");
    CODE_PAGE_MAP.put(12000, "UTF-32LE");
    CODE_PAGE_MAP.put(850, "IBM850");
    CODE_PAGE_MAP.put(862, "IBM862");
    CODE_PAGE_MAP.put(20838, "IBM-THAI");
    CODE_PAGE_MAP.put(950, "BIG5");
    CODE_PAGE_MAP.put(37, "IBM037");
    CODE_PAGE_MAP.put(20273, "IBM273");
    CODE_PAGE_MAP.put(20277, "IBM277");
    CODE_PAGE_MAP.put(20278, "IBM278");
    CODE_PAGE_MAP.put(20280, "IBM280");
    CODE_PAGE_MAP.put(20284, "IBM284");
    CODE_PAGE_MAP.put(20285, "IBM285");
    CODE_PAGE_MAP.put(20290, "IBM290");
    CODE_PAGE_MAP.put(20297, "IBM297");
    CODE_PAGE_MAP.put(20420, "IBM420");
    CODE_PAGE_MAP.put(20423, "IBM423");
    CODE_PAGE_MAP.put(20424, "IBM424");
    CODE_PAGE_MAP.put(437, "IBM437");
    CODE_PAGE_MAP.put(500, "IBM500");
    CODE_PAGE_MAP.put(852, "IBM852");
    CODE_PAGE_MAP.put(855, "IBM855");
    CODE_PAGE_MAP.put(857, "IBM857");
    CODE_PAGE_MAP.put(860, "IBM860");
    CODE_PAGE_MAP.put(861, "IBM861");
    CODE_PAGE_MAP.put(863, "IBM863");
    CODE_PAGE_MAP.put(864, "IBM864");
    CODE_PAGE_MAP.put(865, "IBM865");
    CODE_PAGE_MAP.put(869, "IBM869");
    CODE_PAGE_MAP.put(870, "IBM870");
    CODE_PAGE_MAP.put(20871, "IBM871");
    CODE_PAGE_MAP.put(20880, "IBM880");
    CODE_PAGE_MAP.put(20905, "IBM905");
    CODE_PAGE_MAP.put(1026, "IBM1026");
    CODE_PAGE_MAP.put(20866, "KOI8-R");
    CODE_PAGE_MAP.put(52936, "HZ-GB-2312");
    CODE_PAGE_MAP.put(866, "IBM866");
    CODE_PAGE_MAP.put(21866, "KOI8-U");
    CODE_PAGE_MAP.put(20924, "IBM00924");
    CODE_PAGE_MAP.put(1140, "IBM01140");
    CODE_PAGE_MAP.put(1141, "IBM01141");
    CODE_PAGE_MAP.put(1142, "IBM01142");
    CODE_PAGE_MAP.put(1143, "IBM01143");
    CODE_PAGE_MAP.put(1144, "IBM01144");
    CODE_PAGE_MAP.put(1145, "IBM01145");
    CODE_PAGE_MAP.put(1146, "IBM01146");
    CODE_PAGE_MAP.put(1147, "IBM01147");
    CODE_PAGE_MAP.put(1149, "IBM01149");
    CODE_PAGE_MAP.put(1047, "IBM1047");
    CODE_PAGE_MAP.put(1250, "WINDOWS-1250");
    CODE_PAGE_MAP.put(1251, "WINDOWS-1251");
    CODE_PAGE_MAP.put(1252, "WINDOWS-1252");
    CODE_PAGE_MAP.put(1253, "WINDOWS-1253");
    CODE_PAGE_MAP.put(1254, "WINDOWS-1254");
    CODE_PAGE_MAP.put(1255, "WINDOWS-1255");
    CODE_PAGE_MAP.put(1256, "WINDOWS-1256");
    CODE_PAGE_MAP.put(1257, "WINDOWS-1257");
    CODE_PAGE_MAP.put(1258, "WINDOWS-1258");
    //  resume checkstyle: MagicNumberCheck
  }
}
