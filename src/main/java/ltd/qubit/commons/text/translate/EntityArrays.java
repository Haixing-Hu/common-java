////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.translate;

import java.util.Map;

import static ltd.qubit.commons.datastructure.map.MapUtils.invertAsUnmodifiable;

/**
 * Class holding various entity data for HTML and XML - generally for use with
 * the LookupTranslator.
 * <p>
 * All Maps are generated using {@code java.util.Collections.unmodifiableMap()}.
 *
 * @author Haixing Hu
 */
public class EntityArrays {

  /**
   * A Map of characters to escape.
   * <p>
   * The map contains the
   * <a href="https://secure.wikimedia.org/wikipedia/en/wiki/ISO/IEC_8859-1">ISO-8859-1</a>
   * characters to their named HTML 3.x equivalents.</p>
   */
  public static final Map<CharSequence, CharSequence> ISO8859_1_ESCAPE = Map.ofEntries(
      Map.entry("\u00A0", "&nbsp;"),    // non-breaking space
      Map.entry("\u00A1", "&iexcl;"),   // inverted exclamation mark
      Map.entry("\u00A2", "&cent;"),    // cent sign
      Map.entry("\u00A3", "&pound;"),   // pound sign
      Map.entry("\u00A4", "&curren;"),  // currency sign
      Map.entry("\u00A5", "&yen;"),     // yen sign = yuan sign
      Map.entry("\u00A6", "&brvbar;"),  // broken bar = broken vertical bar
      Map.entry("\u00A7", "&sect;"),    // section sign
      Map.entry("\u00A8", "&uml;"),     // diaeresis = spacing diaeresis
      Map.entry("\u00A9", "&copy;"),    // © - copyright sign
      Map.entry("\u00AA", "&ordf;"),    // feminine ordinal indicator
      Map.entry("\u00AB", "&laquo;"),   // left-pointing double angle quotation mark = left pointing guillemet
      Map.entry("\u00AC", "&not;"),     // not sign
      Map.entry("\u00AD", "&shy;"),     // soft hyphen = discretionary hyphen
      Map.entry("\u00AE", "&reg;"),     // ® - registered trademark sign
      Map.entry("\u00AF", "&macr;"),    // macron = spacing macron = overline = APL overbar
      Map.entry("\u00B0", "&deg;"),     // degree sign
      Map.entry("\u00B1", "&plusmn;"),  // plus-minus sign = plus-or-minus sign
      Map.entry("\u00B2", "&sup2;"),    // superscript two = superscript digit two = squared
      Map.entry("\u00B3", "&sup3;"),    // superscript three = superscript digit three = cubed
      Map.entry("\u00B4", "&acute;"),   // acute accent = spacing acute
      Map.entry("\u00B5", "&micro;"),   // micro sign
      Map.entry("\u00B6", "&para;"),    // pilcrow sign = paragraph sign
      Map.entry("\u00B7", "&middot;"),  // middle dot = Georgian comma = Greek middle dot
      Map.entry("\u00B8", "&cedil;"),   // cedilla = spacing cedilla
      Map.entry("\u00B9", "&sup1;"),    // superscript one = superscript digit one
      Map.entry("\u00BA", "&ordm;"),    // masculine ordinal indicator
      Map.entry("\u00BB", "&raquo;"),   // right-pointing double angle quotation mark = right pointing guillemet
      Map.entry("\u00BC", "&frac14;"),  // vulgar fraction one quarter = fraction one quarter
      Map.entry("\u00BD", "&frac12;"),  // vulgar fraction one half = fraction one half
      Map.entry("\u00BE", "&frac34;"),  // vulgar fraction three quarters = fraction three quarters
      Map.entry("\u00BF", "&iquest;"),  // inverted question mark = turned question mark
      Map.entry("\u00C0", "&Agrave;"),  // À - uppercase A, grave accent
      Map.entry("\u00C1", "&Aacute;"),  // Á - uppercase A, acute accent
      Map.entry("\u00C2", "&Acirc;"),   // Â - uppercase A, circumflex accent
      Map.entry("\u00C3", "&Atilde;"),  // Ã - uppercase A, tilde
      Map.entry("\u00C4", "&Auml;"),    // Ä - uppercase A, umlaut
      Map.entry("\u00C5", "&Aring;"),   // � - uppercase A, ring
      Map.entry("\u00C6", "&AElig;"),   // Æ - uppercase AE
      Map.entry("\u00C7", "&Ccedil;"),  // Ç - uppercase C, cedilla
      Map.entry("\u00C8", "&Egrave;"),  // È - uppercase E, grave accent
      Map.entry("\u00C9", "&Eacute;"),  // É - uppercase E, acute accent
      Map.entry("\u00CA", "&Ecirc;"),   // Ê - uppercase E, circumflex accent
      Map.entry("\u00CB", "&Euml;"),    // Ë - uppercase E, umlaut
      Map.entry("\u00CC", "&Igrave;"),  // Ì - uppercase I, grave accent
      Map.entry("\u00CD", "&Iacute;"),  // Í - uppercase I, acute accent
      Map.entry("\u00CE", "&Icirc;"),   // Î - uppercase I, circumflex accent
      Map.entry("\u00CF", "&Iuml;"),    // Ï - uppercase I, umlaut
      Map.entry("\u00D0", "&ETH;"),     // Ð - uppercase Eth, Icelandic
      Map.entry("\u00D1", "&Ntilde;"),  // Ñ - uppercase N, tilde
      Map.entry("\u00D2", "&Ograve;"),  // Ò - uppercase O, grave accent
      Map.entry("\u00D3", "&Oacute;"),  // Ó - uppercase O, acute accent
      Map.entry("\u00D4", "&Ocirc;"),   // Ô - uppercase O, circumflex accent
      Map.entry("\u00D5", "&Otilde;"),  // Õ - uppercase O, tilde
      Map.entry("\u00D6", "&Ouml;"),    // Ö - uppercase O, umlaut
      Map.entry("\u00D7", "&times;"),   // multiplication sign
      Map.entry("\u00D8", "&Oslash;"),  // Ø - uppercase O, slash
      Map.entry("\u00D9", "&Ugrave;"),  // Ù - uppercase U, grave accent
      Map.entry("\u00DA", "&Uacute;"),  // Ú - uppercase U, acute accent
      Map.entry("\u00DB", "&Ucirc;"),   // Û - uppercase U, circumflex accent
      Map.entry("\u00DC", "&Uuml;"),    // Ü - uppercase U, umlaut
      Map.entry("\u00DD", "&Yacute;"),  // Ý - uppercase Y, acute accent
      Map.entry("\u00DE", "&THORN;"),   // Þ - uppercase THORN, Icelandic
      Map.entry("\u00DF", "&szlig;"),   // ß - lowercase sharps, German
      Map.entry("\u00E0", "&agrave;"),  // à - lowercase a, grave accent
      Map.entry("\u00E1", "&aacute;"),  // á - lowercase a, acute accent
      Map.entry("\u00E2", "&acirc;"),   // â - lowercase a, circumflex accent
      Map.entry("\u00E3", "&atilde;"),  // ã - lowercase a, tilde
      Map.entry("\u00E4", "&auml;"),    // ä - lowercase a, umlaut
      Map.entry("\u00E5", "&aring;"),   // å - lowercase a, ring
      Map.entry("\u00E6", "&aelig;"),   // æ - lowercase ae
      Map.entry("\u00E7", "&ccedil;"),  // ç - lowercase c, cedilla
      Map.entry("\u00E8", "&egrave;"),  // è - lowercase e, grave accent
      Map.entry("\u00E9", "&eacute;"),  // é - lowercase e, acute accent
      Map.entry("\u00EA", "&ecirc;"),   // ê - lowercase e, circumflex accent
      Map.entry("\u00EB", "&euml;"),    // ë - lowercase e, umlaut
      Map.entry("\u00EC", "&igrave;"),  // ì - lowercase i, grave accent
      Map.entry("\u00ED", "&iacute;"),  // í - lowercase i, acute accent
      Map.entry("\u00EE", "&icirc;"),   // î - lowercase i, circumflex accent
      Map.entry("\u00EF", "&iuml;"),    // ï - lowercase i, umlaut
      Map.entry("\u00F0", "&eth;"),     // ð - lowercase eth, Icelandic
      Map.entry("\u00F1", "&ntilde;"),  // ñ - lowercase n, tilde
      Map.entry("\u00F2", "&ograve;"),  // ò - lowercase o, grave accent
      Map.entry("\u00F3", "&oacute;"),  // ó - lowercase o, acute accent
      Map.entry("\u00F4", "&ocirc;"),   // ô - lowercase o, circumflex accent
      Map.entry("\u00F5", "&otilde;"),  // õ - lowercase o, tilde
      Map.entry("\u00F6", "&ouml;"),    // ö - lowercase o, umlaut
      Map.entry("\u00F7", "&divide;"),  // division sign
      Map.entry("\u00F8", "&oslash;"),  // ø - lowercase o, slash
      Map.entry("\u00F9", "&ugrave;"),  // ù - lowercase u, grave accent
      Map.entry("\u00FA", "&uacute;"),  // ú - lowercase u, acute accent
      Map.entry("\u00FB", "&ucirc;"),   // û - lowercase u, circumflex accent
      Map.entry("\u00FC", "&uuml;"),    // ü - lowercase u, umlaut
      Map.entry("\u00FD", "&yacute;"),  // ý - lowercase y, acute accent
      Map.entry("\u00FE", "&thorn;"),   // þ - lowercase thorn, Icelandic
      Map.entry("\u00FF", "&yuml;")     // ÿ - lowercase y, umlaut
  );

  /**
   * Reverse of {@link #ISO8859_1_ESCAPE} for unescaping purposes.
   */
  public static final Map<CharSequence, CharSequence> ISO8859_1_UNESCAPE
      = invertAsUnmodifiable(ISO8859_1_ESCAPE);

  /**
   * A Map&lt;CharSequence, CharSequence&gt; to escape additional
   * <a href="http://www.w3.org/TR/REC-html40/sgml/entities.html">character entity
   * references</a>. Note that this must be used with {@link #ISO8859_1_ESCAPE} to get the full list of
   * HTML 4.0 character entities.
   */
  public static final Map<CharSequence, CharSequence> HTML40_EXTENDED_ESCAPE = Map.ofEntries(
      Map.entry("\u0192", "&fnof;"),    // latin small f with hook = function= florin, U+0192 ISOtech
      // Greek
      Map.entry("\u0391", "&Alpha;"),   // greek capital letter alpha, U+0391
      Map.entry("\u0392", "&Beta;"),    // greek capital letter beta, U+0392
      Map.entry("\u0393", "&Gamma;"),   // greek capital letter gamma,U+0393 ISOgrk3
      Map.entry("\u0394", "&Delta;"),   // greek capital letter delta,U+0394 ISOgrk3
      Map.entry("\u0395", "&Epsilon;"), // greek capital letter epsilon, U+0395
      Map.entry("\u0396", "&Zeta;"),    // greek capital letter zeta, U+0396
      Map.entry("\u0397", "&Eta;"),     // greek capital letter eta, U+0397
      Map.entry("\u0398", "&Theta;"),   // greek capital letter theta,U+0398 ISOgrk3
      Map.entry("\u0399", "&Iota;"),    // greek capital letter iota, U+0399
      Map.entry("\u039A", "&Kappa;"),   // greek capital letter kappa, U+039A
      Map.entry("\u039B", "&Lambda;"),  // greek capital letter lambda,U+039B ISOgrk3
      Map.entry("\u039C", "&Mu;"),      // greek capital letter mu, U+039C
      Map.entry("\u039D", "&Nu;"),      // greek capital letter nu, U+039D
      Map.entry("\u039E", "&Xi;"),      // greek capital letter xi, U+039E ISOgrk3
      Map.entry("\u039F", "&Omicron;"), // greek capital letter omicron, U+039F
      Map.entry("\u03A0", "&Pi;"),      // greek capital letter pi, U+03A0 ISOgrk3
      Map.entry("\u03A1", "&Rho;"),     // greek capital letter rho, U+03A1
                                        // there is no Sigmaf, and no U+03A2 character either
      Map.entry("\u03A3", "&Sigma;"),   // greek capital letter sigma,U+03A3 ISOgrk3
      Map.entry("\u03A4", "&Tau;"),     // greek capital letter tau, U+03A4
      Map.entry("\u03A5", "&Upsilon;"), // greek capital letter upsilon,U+03A5 ISOgrk3
      Map.entry("\u03A6", "&Phi;"),     // greek capital letter phi,U+03A6 ISOgrk3
      Map.entry("\u03A7", "&Chi;"),     // greek capital letter chi, U+03A7
      Map.entry("\u03A8", "&Psi;"),     // greek capital letter psi,U+03A8 ISOgrk3
      Map.entry("\u03A9", "&Omega;"),   // greek capital letter omega,U+03A9 ISOgrk3
      Map.entry("\u03B1", "&alpha;"),   // greek small letter alpha,U+03B1 ISOgrk3
      Map.entry("\u03B2", "&beta;"),    // greek small letter beta, U+03B2 ISOgrk3
      Map.entry("\u03B3", "&gamma;"),   // greek small letter gamma,U+03B3 ISOgrk3
      Map.entry("\u03B4", "&delta;"),   // greek small letter delta,U+03B4 ISOgrk3
      Map.entry("\u03B5", "&epsilon;"), // greek small letter epsilon,U+03B5 ISOgrk3
      Map.entry("\u03B6", "&zeta;"),    // greek small letter zeta, U+03B6 ISOgrk3
      Map.entry("\u03B7", "&eta;"),     // greek small letter eta, U+03B7 ISOgrk3
      Map.entry("\u03B8", "&theta;"),   // greek small letter theta,U+03B8 ISOgrk3
      Map.entry("\u03B9", "&iota;"),    // greek small letter iota, U+03B9 ISOgrk3
      Map.entry("\u03BA", "&kappa;"),   // greek small letter kappa,U+03BA ISOgrk3
      Map.entry("\u03BB", "&lambda;"),  // greek small letter lambda,U+03BB ISOgrk3
      Map.entry("\u03BC", "&mu;"),      // greek small letter mu, U+03BC ISOgrk3
      Map.entry("\u03BD", "&nu;"),      // greek small letter nu, U+03BD ISOgrk3
      Map.entry("\u03BE", "&xi;"),      // greek small letter xi, U+03BE ISOgrk3
      Map.entry("\u03BF", "&omicron;"), // greek small letter omicron, U+03BF NEW
      Map.entry("\u03C0", "&pi;"),      // greek small letter pi, U+03C0 ISOgrk3
      Map.entry("\u03C1", "&rho;"),     // greek small letter rho, U+03C1 ISOgrk3
      Map.entry("\u03C2", "&sigmaf;"),  // greek small letter final sigma,U+03C2 ISOgrk3
      Map.entry("\u03C3", "&sigma;"),   // greek small letter sigma,U+03C3 ISOgrk3
      Map.entry("\u03C4", "&tau;"),     // greek small letter tau, U+03C4 ISOgrk3
      Map.entry("\u03C5", "&upsilon;"), // greek small letter upsilon,U+03C5 ISOgrk3
      Map.entry("\u03C6", "&phi;"),     // greek small letter phi, U+03C6 ISOgrk3
      Map.entry("\u03C7", "&chi;"),     // greek small letter chi, U+03C7 ISOgrk3
      Map.entry("\u03C8", "&psi;"),     // greek small letter psi, U+03C8 ISOgrk3
      Map.entry("\u03C9", "&omega;"),   // greek small letter omega,U+03C9 ISOgrk3
      Map.entry("\u03D1", "&thetasym;"),// greek small letter theta symbol,U+03D1 NEW
      Map.entry("\u03D2", "&upsih;"),   // greek upsilon with hook symbol,U+03D2 NEW
      Map.entry("\u03D6", "&piv;"),     // greek pi symbol, U+03D6 ISOgrk3
      // General Punctuation
      Map.entry("\u2022", "&bull;"),    // bullet = black small circle,U+2022 ISOpub
                                        // bullet is NOT the same as bullet operator, U+2219
      Map.entry("\u2026", "&hellip;"),  // horizontal ellipsis = three dot leader,U+2026 ISOpub
      Map.entry("\u2032", "&prime;"),   // prime = minutes = feet, U+2032 ISOtech
      Map.entry("\u2033", "&Prime;"),   // double prime = seconds = inches,U+2033 ISOtech
      Map.entry("\u203E", "&oline;"),   // overline = spacing overscore,U+203E NEW
      Map.entry("\u2044", "&frasl;"),   // fraction slash, U+2044 NEW
      // Letterlike Symbols
      Map.entry("\u2118", "&weierp;"),  // script capital P = power set= Weierstrass p, U+2118 ISOamso
      Map.entry("\u2111", "&image;"),   // blackletter capital I = imaginary part,U+2111 ISOamso
      Map.entry("\u211C", "&real;"),    // blackletter capital R = real part symbol,U+211C ISOamso
      Map.entry("\u2122", "&trade;"),   // trade mark sign, U+2122 ISOnum
      Map.entry("\u2135", "&alefsym;"), // alef symbol = first transfinite cardinal,U+2135 NEW
                                        // alef symbol is NOT the same as hebrew letter alef,U+05D0 although the
                                        // same glyph could be used to depict both characters
      // Arrows
      Map.entry("\u2190", "&larr;"),    // leftwards arrow, U+2190 ISOnum
      Map.entry("\u2191", "&uarr;"),    // upwards arrow, U+2191 ISOnum-->
      Map.entry("\u2192", "&rarr;"),    // rightwards arrow, U+2192 ISOnum
      Map.entry("\u2193", "&darr;"),    // downwards arrow, U+2193 ISOnum
      Map.entry("\u2194", "&harr;"),    // left right arrow, U+2194 ISOamsa
      Map.entry("\u21B5", "&crarr;"),   // downwards arrow with corner leftwards= carriage return, U+21B5 NEW
      Map.entry("\u21D0", "&lArr;"),    // leftwards double arrow, U+21D0 ISOtech
                                        // ISO 10646 does not say that lArr is the same as the 'is implied by'
                                        // arrow but also does not have any other character for that function.
                                        // So ? lArr canbe used for 'is implied by' as ISOtech suggests
      Map.entry("\u21D1", "&uArr;"),    // upwards double arrow, U+21D1 ISOamsa
      Map.entry("\u21D2", "&rArr;"),    // rightwards double arrow,U+21D2 ISOtech
                                        // ISO 10646 does not say this is the 'implies' character but does not
                                        // have another character with this function so ?rArr can be used for
                                        // 'implies' as ISOtech suggests
      Map.entry("\u21D3", "&dArr;"),    // downwards double arrow, U+21D3 ISOamsa
      Map.entry("\u21D4", "&hArr;"),    // left right double arrow,U+21D4 ISOamsa
      // Mathematical Operators
      Map.entry("\u2200", "&forall;"),  // for all, U+2200 ISOtech
      Map.entry("\u2202", "&part;"),    // partial differential, U+2202 ISOtech
      Map.entry("\u2203", "&exist;"),   // there exists, U+2203 ISOtech
      Map.entry("\u2205", "&empty;"),   // empty set = null set = diameter,U+2205 ISOamso
      Map.entry("\u2207", "&nabla;"),   // nabla = backward difference,U+2207 ISOtech
      Map.entry("\u2208", "&isin;"),    // element of, U+2208 ISOtech
      Map.entry("\u2209", "&notin;"),   // not an element of, U+2209 ISOtech
      Map.entry("\u220B", "&ni;"),      // contains as member, U+220B ISOtech
                                        // should there be a more memorable name than 'ni'?
      Map.entry("\u220F", "&prod;"),    // n-ary product = product sign,U+220F ISOamsb
                                        // prod is NOT the same character as U+03A0 'greek capital letter pi'
                                        // though the same glyph might be used for both
      Map.entry("\u2211", "&sum;"),     // n-ary summation, U+2211 ISOamsb
                                        // sum is NOT the same character as U+03A3 'greek capital letter sigma'
                                        // though the same glyph might be used for both
      Map.entry("\u2212", "&minus;"),   // minus sign, U+2212 ISOtech
      Map.entry("\u2217", "&lowast;"),  // asterisk operator, U+2217 ISOtech
      Map.entry("\u221A", "&radic;"),   // square root = radical sign,U+221A ISOtech
      Map.entry("\u221D", "&prop;"),    // proportional to, U+221D ISOtech
      Map.entry("\u221E", "&infin;"),   // infinity, U+221E ISOtech
      Map.entry("\u2220", "&ang;"),     // angle, U+2220 ISOamso
      Map.entry("\u2227", "&and;"),     // logical and = wedge, U+2227 ISOtech
      Map.entry("\u2228", "&or;"),      // logical or = vee, U+2228 ISOtech
      Map.entry("\u2229", "&cap;"),     // intersection = cap, U+2229 ISOtech
      Map.entry("\u222A", "&cup;"),     // union = cup, U+222A ISOtech
      Map.entry("\u222B", "&int;"),     // integral, U+222B ISOtech
      Map.entry("\u2234", "&there4;"),  // therefore, U+2234 ISOtech
      Map.entry("\u223C", "&sim;"),     // tilde operator = varies with = similar to,U+223C ISOtech
                                        // tilde operator is NOT the same character as the tilde, U+007E,although
                                        // the same glyph might be used to represent both
      Map.entry("\u2245", "&cong;"),    // approximately equal to, U+2245 ISOtech
      Map.entry("\u2248", "&asymp;"),   // almost equal to = asymptotic to,U+2248 ISOamsr
      Map.entry("\u2260", "&ne;"),      // not equal to, U+2260 ISOtech
      Map.entry("\u2261", "&equiv;"),   // identical to, U+2261 ISOtech
      Map.entry("\u2264", "&le;"),      // less-than or equal to, U+2264 ISOtech
      Map.entry("\u2265", "&ge;"),      // greater-than or equal to,U+2265 ISOtech
      Map.entry("\u2282", "&sub;"),     // subset of, U+2282 ISOtech
      Map.entry("\u2283", "&sup;"),     // superset of, U+2283 ISOtech
                                        // note that nsup, 'not a superset of, U+2283' is not covered by the
                                        // Symbol font encoding and is not included. Should it be, for symmetry?
                                        // It is in ISOamsn,
      Map.entry("\u2284", "&nsub;"),    // not a subset of, U+2284 ISOamsn
      Map.entry("\u2286", "&sube;"),    // subset of or equal to, U+2286 ISOtech
      Map.entry("\u2287", "&supe;"),    // superset of or equal to,U+2287 ISOtech
      Map.entry("\u2295", "&oplus;"),   // circled plus = direct sum,U+2295 ISOamsb
      Map.entry("\u2297", "&otimes;"),  // circled times = vector product,U+2297 ISOamsb
      Map.entry("\u22A5", "&perp;"),    // up tack = orthogonal to = perpendicular,U+22A5 ISOtech
      Map.entry("\u22C5", "&sdot;"),    // dot operator, U+22C5 ISOamsb
                                        // dot operator is NOT the same character as U+00B7 middle dot
      // Miscellaneous Technical
      Map.entry("\u2308", "&lceil;"),   // left ceiling = apl upstile,U+2308 ISOamsc
      Map.entry("\u2309", "&rceil;"),   // right ceiling, U+2309 ISOamsc
      Map.entry("\u230A", "&lfloor;"),  // left floor = apl downstile,U+230A ISOamsc
      Map.entry("\u230B", "&rfloor;"),  // right floor, U+230B ISOamsc
      Map.entry("\u2329", "&lang;"),    // left-pointing angle bracket = bra,U+2329 ISOtech
                                        // lang is NOT the same character as U+003C 'less than'
                                        // or U+2039 'single left-pointing angle quotation mark'
      Map.entry("\u232A", "&rang;"),    // right-pointing angle bracket = ket,U+232A ISOtech
                                        // rang is NOT the same character as U+003E 'greater than' or U+203A
                                        // 'single right-pointing angle quotation mark'
      // Geometric Shapes
      Map.entry("\u25CA", "&loz;"),     // lozenge, U+25CA ISOpub
      // Miscellaneous Symbols
      Map.entry("\u2660", "&spades;"),  // black spade suit, U+2660 ISOpub
      // black here seems to mean filled as opposed to hollow
      Map.entry("\u2663", "&clubs;"),   // black club suit = shamrock,U+2663 ISOpub
      Map.entry("\u2665", "&hearts;"),  // black heart suit = valentine,U+2665 ISOpub
      Map.entry("\u2666", "&diams;"),   // black diamond suit, U+2666 ISOpub
      // Latin Extended-A
      Map.entry("\u0152", "&OElig;"),   // latin capital ligature OE,U+0152 ISOlat2
      Map.entry("\u0153", "&oelig;"),   // latin small ligature oe, U+0153 ISOlat2
      // ligature is a misnomer, this is a separate character in some languages
      Map.entry("\u0160", "&Scaron;"),  // latin capital letter S with caron,U+0160 ISOlat2
      Map.entry("\u0161", "&scaron;"),  // latin small letter s with caron,U+0161 ISOlat2
      Map.entry("\u0178", "&Yuml;"),    // latin capital letter Y with diaeresis,U+0178 ISOlat2
      // Spacing Modifier Letters
      Map.entry("\u02C6", "&circ;"),    // modifier letter circumflex accent,U+02C6 ISOpub
      Map.entry("\u02DC", "&tilde;"),   // small tilde, U+02DC ISOdia
      // General Punctuation
      Map.entry("\u2002", "&ensp;"),    // en space, U+2002 ISOpub
      Map.entry("\u2003", "&emsp;"),    // em space, U+2003 ISOpub
      Map.entry("\u2009", "&thinsp;"),  // thin space, U+2009 ISOpub
      Map.entry("\u200C", "&zwnj;"),    // zero width non-joiner,U+200C NEW RFC 2070
      Map.entry("\u200D", "&zwj;"),     // zero width joiner, U+200D NEW RFC 2070
      Map.entry("\u200E", "&lrm;"),     // left-to-right mark, U+200E NEW RFC 2070
      Map.entry("\u200F", "&rlm;"),     // right-to-left mark, U+200F NEW RFC 2070
      Map.entry("\u2013", "&ndash;"),   // en dash, U+2013 ISOpub
      Map.entry("\u2014", "&mdash;"),   // em dash, U+2014 ISOpub
      Map.entry("\u2018", "&lsquo;"),   // left single quotation mark,U+2018 ISOnum
      Map.entry("\u2019", "&rsquo;"),   // right single quotation mark,U+2019 ISOnum
      Map.entry("\u201A", "&sbquo;"),   // single low-9 quotation mark, U+201A NEW
      Map.entry("\u201C", "&ldquo;"),   // left double quotation mark,U+201C ISOnum
      Map.entry("\u201D", "&rdquo;"),   // right double quotation mark,U+201D ISOnum
      Map.entry("\u201E", "&bdquo;"),   // double low-9 quotation mark, U+201E NEW
      Map.entry("\u2020", "&dagger;"),  // dagger, U+2020 ISOpub
      Map.entry("\u2021", "&Dagger;"),  // double dagger, U+2021 ISOpub
      Map.entry("\u2030", "&permil;"),  // per mille sign, U+2030 ISOtech
      Map.entry("\u2039", "&lsaquo;"),  // single left-pointing angle quotation mark,U+2039 ISO proposed
                                        // lsaquo is proposed but not yet ISO standardized
      Map.entry("\u203A", "&rsaquo;"),  // single right-pointing angle quotation mark,U+203A ISO proposed
                                        // rsaquo is proposed but not yet ISO standardized
      Map.entry("\u20AC", "&euro;")     // euro sign, U+20AC NEW
  );

  /**
   * Reverse of {@link #HTML40_EXTENDED_ESCAPE} for unescaping purposes.
   */
  public static final Map<CharSequence, CharSequence> HTML40_EXTENDED_UNESCAPE
      = invertAsUnmodifiable(HTML40_EXTENDED_ESCAPE);

  /**
   * A Map&lt;CharSequence, CharSequence&gt; to escape the basic XML and HTML
   * character entities.
   * <p>
   * Namely: {@code " & < >}
   */
  public static final Map<CharSequence, CharSequence> BASIC_ESCAPE = Map.of(
      "\"", "&quot;", // " - double-quote
      "&", "&amp;",   // & - ampersand
      "<", "&lt;",    // < - less-than
      ">", "&gt;"     // > - greater-than
  );

  /**
   * Reverse of {@link #BASIC_ESCAPE} for unescaping purposes.
   */
  public static final Map<CharSequence, CharSequence> BASIC_UNESCAPE
      = invertAsUnmodifiable(BASIC_ESCAPE);

  /**
   * A Map&lt;CharSequence, CharSequence&gt; to escape the apostrophe character to
   * its XML character entity.
   */
  public static final Map<CharSequence, CharSequence> APOS_ESCAPE = Map.of(
      "'", "&apos;"
  );

  /**
   * Reverse of {@link #APOS_ESCAPE} for unescaping purposes.
   */
  public static final Map<CharSequence, CharSequence> APOS_UNESCAPE
      = invertAsUnmodifiable(APOS_ESCAPE);

  /**
   * A Map&lt;CharSequence, CharSequence&gt; to escape the Java
   * control characters.
   * <p>
   * Namely: {@code \b \n \t \f \r}
   */
  public static final Map<CharSequence, CharSequence> JAVA_CTRL_CHARS_ESCAPE = Map.of(
      "\b", "\\b",
      "\n", "\\n",
      "\t", "\\t",
      "\f", "\\f",
      "\r", "\\r"
  );

  /**
   * Reverse of {@link #JAVA_CTRL_CHARS_ESCAPE} for unescaping purposes.
   */
  public static final Map<CharSequence, CharSequence> JAVA_CTRL_CHARS_UNESCAPE
      = invertAsUnmodifiable(JAVA_CTRL_CHARS_ESCAPE);

  /**
   * A Map&lt;CharSequence, CharSequence&gt; to escape the special characters of
   * Java regular expressions.
   * <p>
   * Namely: {@code \ . [ ] { } ( ) ^ $ | ? * +}
   */
  public static final Map<CharSequence, CharSequence> JAVA_REGEX_ESCAPE = Map.ofEntries(
      Map.entry("\\", "\\\\"),
      Map.entry(".", "\\."),
      Map.entry("[", "\\["),
      Map.entry("]", "\\]"),
      Map.entry("{", "\\{"),
      Map.entry("}", "\\}"),
      Map.entry("(", "\\("),
      Map.entry(")", "\\)"),
      Map.entry("^", "\\^"),
      Map.entry("$", "\\$"),
      Map.entry("|", "\\|"),
      Map.entry("?", "\\?"),
      Map.entry("*", "\\*"),
      Map.entry("+", "\\+")
  );

  /**
   * Reverse of {@link #JAVA_REGEX_ESCAPE} for unescaping purposes.
   */
  public static final Map<CharSequence, CharSequence> JAVA_REGEX_UNESCAPE
      = invertAsUnmodifiable(JAVA_REGEX_ESCAPE);
}