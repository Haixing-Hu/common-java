////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.html;

/**
 * This interface defines the constant of HTML attribute names.
 *
 * <p>The name of the constants are of the form {@code ATTR_[uppercase attribute name]}.
 *
 * @author Haixing Hu
 * @see HtmlTag
 * @see HtmlAttributeValue
 * @see HtmlEvent
 * @see <a href="http://www.w3schools.com/tags/default.asp">HTML Element Reference</a>
 */
public interface HtmlAttribute {

  /**
   * Specifies a keyboard shortcut to access an element.
   *
   * <p>This is a standard attribute.
   */
  String ATTR_ACCESSKEY      = "accesskey";

  /**
   * Specifies a class name for an element.
   *
   * <p>This is a standard attribute.
   */
  String ATTR_CLASS          = "class";

  /**
   * Specifies the text direction for the content in an element.
   *
   * <p>This is a standard attribute.
   */
  String ATTR_DIR            = "dir";

  /**
   * Specifies a unique id for an element.
   *
   * <p>This is a standard attribute.
   */
  String ATTR_ID             = "id";

  /**
   * Specifies a language code for the content in an element.
   *
   * <p>This is a standard attribute.
   */
  String ATTR_LANG           = "lang";

  /**
   * Specifies an inline style for an element.
   *
   * <p>This is a standard attribute.
   */
  String ATTR_STYLE          = "style";

  /**
   * Specifies the tab order of an element.
   *
   * <p>This is a standard attribute.
   */
  String ATTR_TABINDEX       = "tabindex";

  /**
   * Specifies extra information about an element.
   *
   * <p>This is a standard attribute.
   */
  String ATTR_TITLE          = "title";

  /**
   * Specifies a language code for the content in an element, in XHTML
   * documents.
   *
   * <p>This is a standard attribute.
   */
  String ATTR_XML_LANG       = "xml:lang";

  /**
   * Specifies the character-set of a linked document.
   *
   * <p>Applied to tag {@code a}, {@code link}, {@code script}.
   */
  String ATTR_CHARSET        = "charset";

  /**
   * Specifies the coordinates of an element.
   *
   * <p>Applied to tag {@code a}, {@code area}.
   */
  String ATTR_COORDS         = "coords";

  /**
   * Specifies the destination of a link.
   *
   * <p>Applied to tag {@code a}, {@code area}, {@code base}, {@code link}.
   */
  String ATTR_HREF           = "href";

  /**
   * Specifies the language of a linked document.
   *
   * <p>Applied to tag {@code a}, {@code link}.
   */
  String ATTR_HREFLANG       = "hreflang";

  /**
   * Specifies the name of an element.
   *
   * <p>Applied to tag {@code a}, {@code applet}, {@code button},
   * {@code form}, {@code frame}, {@code iframe},
   * {@code input}, {@code map}, {@code meta},
   * {@code object}, {@code param}, {@code select},
   * {@code textarea}.
   */
  String ATTR_NAME           = "name";

  /**
   * Specifies the relationship between the current document and the linked
   * document.
   *
   * <p>Applied to tag {@code a}, {@code link}.
   */
  String ATTR_REL            = "rel";

  /**
   * Specifies the relationship between the linked document and the current
   * document.
   *
   * <p>Applied to tag {@code a}, {@code link}.
   */
  String ATTR_REV            = "rev";

  /**
   * Specifies the shape of an element.
   *
   * <p>Applied to tag {@code a}, {@code area}.
   */
  String ATTR_SHAPE          = "shape";

  /**
   * Specifies where to open the linked page specified in the {@code href}
   * attribute.
   *
   * <p>Applied to tag {@code a}, {@code area}, {@code base},
   * {@code form}, {@code link}.
   */
  String ATTR_TARGET         = "target";

  /**
   * Specifies the file name of a Java applet.
   *
   * <p>Applied to tag {@code applet}.
   */
  String ATTR_CODE           = "code";

  /**
   * Specifies a reference to a serialized representation of an applet.
   *
   * <p>Applied to tag {@code applet}.
   */
  String ATTR_OBJECT         = "object";

  /**
   * Specifies the alignment of an element according to surrounding elements.
   *
   * <p>Applied to tag {@code applet}, {@code caption}, {@code col},
   * {@code colgroup}, {@code div}, {@code h1}, {@code h2},
   * {@code h3}, {@code h4}, {@code h5}, {@code h6},
   * {@code hr}, {@code iframe}, {@code img}, {@code input},
   * {@code legend}, {@code object}, {@code p},
   * {@code table}, {@code tbody}, {@code td}, {@code tfoot}
   * , {@code th}, {@code thead}, {@code tr}.
   */
  String ATTR_ALIGN          = "align";

  /**
   * Specifies an alternate text for an element.
   *
   * <p>Applied to tag {@code applet}, {@code area}, {@code img},
   * {@code input}.
   */
  String ATTR_ALT            = "alt";

  /**
   * Specifies the location of an archive file.
   *
   * <p>Applied to tag {@code applet}, {@code object}.
   */
  String ATTR_ARCHIVE        = "archive";

  /**
   * Specifies a relative base URL for applets/objects specified in the code
   * attribute.
   *
   * <p>Applied to tag {@code applet}, {@code object}.
   */
  String ATTR_CODEBASE       = "codebase";

  /**
   * Specifies the height of an element.
   *
   * <p>Applied to tag {@code applet}, {@code iframe}, {@code img},
   * {@code object}, {@code td}, {@code th}.
   */
  String ATTR_HEIGHT         = "height";

  /**
   * Defines the horizontal spacing around an element.
   *
   * <p>Applied to tag {@code applet}, {@code img}, {@code object}.
   */
  String ATTR_HSPACE         = "hspace";

  /**
   * Defines the vertical spacing around an element.
   *
   * <p>Applied to tag {@code applet}, {@code img}, {@code object}.
   */
  String ATTR_VSPACE         = "vspace";

  /**
   * Specifies the width of an element.
   *
   * <p>Applied to tag {@code applet}, {@code col}, {@code colgroup},
   * {@code hr}, {@code iframe}, {@code img},
   * {@code object}, {@code pre}, {@code table}, {@code td},
   * {@code th}.
   */
  String ATTR_WIDTH          = "width";

  /**
   * Specifies that an area has no associated link.
   *
   * <p>Applied to tag {@code area}.
   */
  String ATTR_NOHREF         = "nohref";

  /**
   * Specifies the source of a quotation, or specifies a URL to a document which
   * explains why the text was deleted/inserted/changed.
   *
   * <p>Applied to tag {@code blockquote}, {@code del}, {@code ins},
   * {@code q}.
   */
  String ATTR_CITE           = "cite";

  /**
   * Specifies the color of an active link in a document.
   *
   * <p>Deprecated. Use styles instead.
   *
   * <p>Applied to tag {@code body}.
   */
  String ATTR_ALINK          = "alink";

  /**
   * Specifies a background image for a document.
   *
   * <p>Deprecated. Use styles instead.
   *
   * <p>Applied to tag {@code body}.
   */
  String ATTR_BACKGROUND     = "background";

  /**
   * Specifies the background color of an element.
   *
   * <p>Deprecated. Use styles instead.
   *
   * <p>Applied to tag {@code body}, {@code table}, {@code td},
   * {@code th}, {@code tr}.
   */
  String ATTR_BGCOLOR        = "bgcolor";

  /**
   * Specifies the default color of unvisited links in a document.
   *
   * <p>Deprecated. Use styles instead.
   *
   * <p>Applied to tag {@code body}.
   */
  String ATTR_LINK           = "link";

  /**
   * Specifies the color of the text in a document.
   *
   * <p>Deprecated. Use styles instead.
   *
   * <p>Applied to tag {@code body}.
   */
  String ATTR_TEXT           = "text";

  /**
   * Specifies the default color of visited links in a document.
   *
   * <p>Deprecated. Use styles instead.
   *
   * <p>Applied to tag {@code body}.
   */
  String ATTR_VLINK          = "vlink";

  /**
   * Specifies that an element should be disabled.
   *
   * <p>Applied to tag {@code button}, {@code input},
   * {@code optgroup}, {@code option}, {@code select},
   * {@code textarea}.
   */
  String ATTR_DISABLED       = "disabled";

  /**
   * Specifies the type of an element.
   *
   * <p>Applied to tag {@code button}, {@code input}, {@code li},
   * {@code link}, {@code object}, {@code ol}, {@code param},
   * {@code script}, {@code style}, {@code ul}.
   */
  String ATTR_TYPE           = "type";

  /**
   * Specifies the underlying value of an element.
   *
   * <p>Applied to tag {@code button}, {@code input}, {@code li},
   * {@code option}, {@code param}.
   */
  String ATTR_VALUE          = "value";

  /**
   * Aligns the content in a cell to a character
   *
   * <p>Applied to tag {@code col}, {@code colgroup}, {@code tbody},
   * {@code td}, {@code tfoot}, {@code th}, {@code thead},
   * {@code tr}.
   */
  String ATTR_CHAR           = "char";

  /**
   * Specifies the number of characters the content will be aligned from the
   * character specified by the {@code char} attribute.
   *
   * <p>Applied to tag {@code col}, {@code colgroup}, {@code tbody},
   * {@code td}, {@code tfoot}, {@code th}, {@code thead},
   * {@code tr}.
   */
  String ATTR_CHAROFF        = "charoff";

  /**
   * Specifies the number of columns a table column element should span.
   *
   * <p>Applied to tag {@code col}, {@code colgroup}.
   */
  String ATTR_SPAN           = "span";

  /**
   * Specifies the vertical alignment of the content related to a table column
   * element.
   *
   * <p>Applied to tag {@code col}, {@code colgroup}, {@code tbody},
   * {@code td}, {@code tfoot}, {@code th}, {@code thead},
   * {@code tr}.
   */
  String ATTR_VALIGN         = "valign";

  /**
   * Specifies the date and time when the text was deleted/inserted/changed.
   *
   * <p>Applied to tag {@code del}, {@code ins}.
   */
  String ATTR_DATETIME       = "datetime";

  /**
   * Specifies that the list should render smaller than normal.
   *
   * <p>Deprecated. Use styles instead.
   *
   * <p>Applied to tag {@code dir}, {@code menu}, {@code ol},
   * {@code ul}.
   */
  String ATTR_COMPACT        = "compact";

  /**
   * Specifies the color of text.
   *
   * <p>Deprecated. Use styles instead.
   *
   * <p>Applied to tag {@code font}.
   */
  String ATTR_COLOR          = "color";

  /**
   * Specifies the font of text.
   *
   * <p>Deprecated. Use styles instead.
   *
   * <p>Applied to tag {@code font}.
   */
  String ATTR_FACE           = "face";

  /**
   * Specifies the size of an element.
   *
   * <p>Deprecated. Use styles instead.
   *
   * <p>Applied to tag {@code font}, {@code hr}, {@code input},
   * {@code select}.
   */
  String ATTR_SIZE           = "size";

  /**
   * Specifies where to send the form-data when a form is submitted.
   *
   * <p>Applied to tag {@code form}.
   */
  String ATTR_ACTION         = "action";

  /**
   * Specifies the types of files that can be submitted through a file upload.
   *
   * <p>Applied to tag {@code form}, {@code input}.
   */
  String ATTR_ACCEPT         = "accept";

  /**
   * Specifies the character-sets the server can handle for form-data.
   *
   * <p>Applied to tag {@code form}.
   */
  String ATTR_ACCEPT_CHARSET = "accept-charset";

  /**
   * Specifies how form-data should be encoded before sending it to a server.
   *
   * <p>Applied to tag {@code form}.
   */
  String ATTR_ENCTYPE        = "enctype";

  /**
   * Specifies how to send form-data.
   *
   * <p>Applied to tag {@code form}.
   */
  String ATTR_METHOD         = "method";

  /**
   * Specifies whether or not to display a border around a frame.
   *
   * <p>Applied to tag {@code frame}, {@code iframe}.
   */
  String ATTR_FRAMEBORDER    = "frameborder";

  /**
   * Specifies a page that contains a long description of the content of an
   * element.
   *
   * <p>Applied to tag {@code frame}, {@code iframe}, {@code img}.
   */
  String ATTR_LONGDESC       = "longdesc";

  /**
   * Specifies the top and bottom margins of a frame.
   *
   * <p>Applied to tag {@code frame}, {@code iframe}.
   */
  String ATTR_MARGINHEIGHT   = "marginheight";

  /**
   * Specifies the left and right margins of a frame.
   *
   * <p>Applied to tag {@code frame}, {@code iframe}.
   */
  String ATTR_MARGINWIDTH    = "marginwidth";

  /**
   * Specifies that a frame cannot be resized.
   *
   * <p>Applied to tag {@code frame}.
   */
  String ATTR_NORESIZE       = "noresize";

  /**
   * Specifies whether or not to display scrolling bars in a frame.
   *
   * <p>Applied to tag {@code frame}, {@code iframe}.
   */
  String ATTR_SCROLLING      = "scrolling";

  /**
   * Specifies the URL of the content to show in an element.
   *
   * <p>Applied to tag {@code frame}, {@code iframe}, {@code img},
   * {@code input}, {@code script}.
   */
  String ATTR_SRC            = "src";

  /**
   * Specifies the number and size of columns in an element.
   *
   * <p>Applied to tag {@code frameset}, {@code textarea}.
   */
  String ATTR_COLS           = "cols";

  /**
   * Specifies the number and size of rows in an element.
   *
   * <p>Applied to tag {@code frameset}, {@code textarea}.
   */
  String ATTR_ROWS           = "rows";

  /**
   * Specifies a URL to a document that contains a set of rules. The rules can
   * be read by browsers to clearly understand the information in the
   * {@code meta} tag's {@code content} attribute.
   *
   * <p>Applied to tag {@code head}.
   */
  String ATTR_PROFILE        = "profile";

  /**
   * Specifies that a {@code hr} element should render in one solid color
   * (no shaded), instead of a shaded color.
   *
   * <p>Deprecated. Use styles instead.
   *
   * <p>Applied to tag {@code hr}.
   */
  String ATTR_NOSHADE        = "noshade";

  /**
   * Specifies the namespace to use (only for XHTML documents!).
   *
   * <p>Applied to tag {@code html}.
   */
  String ATTR_XMLNS          = "xmlns";

  /**
   * Specifies the width of the border around an element.
   *
   * <p>Deprecated. Use styles instead.
   *
   * <p>Applied to tag {@code img}, {@code object}, {@code table}.
   */
  String ATTR_BORDER         = "border";

  /**
   * Specifies an image as a server-side image-map
   *
   * <p>Applied to tag {@code img}.
   */
  String ATTR_ISMAP          = "ismap";

  /**
   * Specifies an image as a client-side image-map
   *
   * <p>Applied to tag {@code img}, {@code object}.
   */
  String ATTR_USEMAP         = "usemap";

  /**
   * Specifies that an input element should be preselected when the page loads
   * (for type="checkbox" or type="radio").
   *
   * <p>Applied to tag {@code input}.
   */
  String ATTR_CHECKED        = "checked";

  /**
   * Specifies the maximum length (in characters) of an input field (for
   * type="text" or type="password").
   *
   * <p>Applied to tag {@code input}.
   */
  String ATTR_MAXLENGTH      = "maxlength";

  /**
   * Specifies that the content of an element should be read-only.
   *
   * <p>Applied to tag {@code input}, {@code textarea}.
   */
  String ATTR_READONLY       = "readonly";

  /**
   * Specifies which form element a label is bound to.
   *
   * <p>Applied to tag {@code label}.
   */
  String ATTR_FOR            = "for";

  /**
   * Specifies on what device the linked document will be displayed.
   *
   * <p>Applied to tag {@code link}, {@code style}.
   */
  String ATTR_MEDIA          = "media";

  /**
   * Specifies the content of the meta information.
   *
   * <p>Applied to tag {@code meta}.
   */
  String ATTR_CONTENT        = "content";

  /**
   * Provides an HTTP header for the information in the content attribute.
   *
   * <p>Applied to tag {@code meta}.
   */
  String ATTR_HTTP_EQUIV     = "http-equiv";

  /**
   * Specifies a scheme to be used to interpret the value of the content
   * attribute.
   *
   * <p>Applied to tag {@code meta}.
   */
  String ATTR_SCHEME         = "scheme";

  /**
   * Defines a class ID value as set in the Windows Registry or a URL.
   *
   * <p>Applied to tag {@code object}.
   */
  String ATTR_CLASSID        = "classid";

  /**
   * The internet media type of the code referred to by the {@code classid}
   * attribute.
   *
   * <p>Applied to tag {@code object}.
   */
  String ATTR_CODETYPE       = "codetype";

  /**
   * Defines a URL that refers to the object's data.
   *
   * <p>Applied to tag {@code object}.
   */
  String ATTR_DATA           = "data";

  /**
   * Defines that the object should only be declared, not created or
   * instantiated until needed.
   *
   * <p>Applied to tag {@code object}.
   */
  String ATTR_DECLARE        = "declare";

  /**
   * Defines a text to display while the object is loading.
   *
   * <p>Applied to tag {@code object}.
   */
  String ATTR_STANDBY        = "standby";

  /**
   * Specifies the start point in a list.
   *
   * <p>Deprecated. Use styles instead.
   *
   * <p>Applied to tag {@code ol}.
   */
  String ATTR_START          = "start";

  /**
   * Specifies a description for a group of options.
   *
   * <p>Applied to tag {@code optgroup}, {@code option}.
   */
  String ATTR_LABEL          = "label";

  /**
   * Specifies that an option should be selected by default.
   *
   * <p>Applied to tag {@code option}.
   */
  String ATTR_SELECTED       = "selected";

  /**
   * Specifies the type of the value.
   *
   * <p>Applied to tag {@code param}.
   */
  String ATTR_VALUETYPE      = "valuetype";

  /**
   * Specifies that the execution of a script should be deferred (delayed) until
   * after the page has been loaded.
   *
   * <p>Applied to tag {@code script}.
   */
  String ATTR_DEFER          = "defer";

  /**
   * Specifies whether whitespace in code should be preserved.
   *
   * <p>Applied to tag {@code script}.
   */
  String ATTR_XML_SPACE      = "xml:space";

  /**
   * Specifies that multiple options can be selected.
   *
   * <p>Applied to tag {@code select}.
   */
  String ATTR_MULTIPLE       = "multiple";

  /**
   * Specifies the space between the cell wall and the cell content.
   *
   * <p>Applied to tag {@code table}.
   */
  String ATTR_CELLPADDING    = "cellpadding";

  /**
   * Specifies the space between cells.
   *
   * <p>Applied to tag {@code table}.
   */
  String ATTR_CELLSPACING    = "cellspacing";

  /**
   * Specifies which parts of the outside borders that should be visible.
   *
   * <p>Applied to tag {@code table}.
   */
  String ATTR_FRAME          = "frame";

  /**
   * Specifies which parts of the inside borders that should be visible.
   *
   * <p>Applied to tag {@code table}.
   */
  String ATTR_RULES          = "rules";

  /**
   * Specifies a summary of the content of a table.
   *
   * <p>Applied to tag {@code table}.
   */
  String ATTR_SUMMARY        = "summary";

  /**
   * Specifies an abbreviated version of the content in a cell.
   *
   * <p>Applied to tag {@code td}, {@code th}.
   */
  String ATTR_ABBR           = "abbr";

  /**
   * Categorizes cells.
   *
   * <p>Applied to tag {@code td}, {@code th}.
   */
  String ATTR_AXIS           = "axis";

  /**
   * Specifies the number of columns a cell should span.
   *
   * <p>Applied to tag {@code td}, {@code th}.
   */
  String ATTR_COLSPAN        = "colspan";

  /**
   * Specifies the table headers related to a cell.
   *
   * <p>Applied to tag {@code td}.
   */
  String ATTR_HEADERS        = "headers";

  /**
   * Specifies that the content inside a cell should not wrap.
   *
   * <p>Deprecated. Use styles instead.
   *
   * <p>Applied to tag {@code td}, {@code th}.
   */
  String ATTR_NOWRAP         = "nowrap";

  /**
   * Sets the number of rows a cell should span.
   *
   * <p>Applied to tag {@code td}, {@code th}.
   */
  String ATTR_ROWSPAN        = "rowspan";

  /**
   * Defines a way to associate header cells and data cells in a table.
   *
   * <p>Applied to tag {@code td}, {@code th}.
   */
  String ATTR_SCOPE          = "scope";

}
