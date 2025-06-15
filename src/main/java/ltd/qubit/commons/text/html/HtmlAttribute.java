////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.html;

/**
 * 此接口定义HTML属性名的常量。
 *
 * <p>常量的名称形式为{@code ATTR_[大写属性名]}。
 *
 * @author 胡海星
 * @see HtmlTag
 * @see HtmlAttributeValue
 * @see HtmlEvent
 * @see <a href="http://www.w3schools.com/tags/default.asp">HTML Element Reference</a>
 */
public interface HtmlAttribute {

  /**
   * 指定访问元素的键盘快捷键。
   *
   * <p>这是一个标准属性。
   */
  String ATTR_ACCESSKEY      = "accesskey";

  /**
   * 为元素指定类名。
   *
   * <p>这是一个标准属性。
   */
  String ATTR_CLASS          = "class";

  /**
   * 指定元素中内容的文本方向。
   *
   * <p>这是一个标准属性。
   */
  String ATTR_DIR            = "dir";

  /**
   * 为元素指定唯一的id。
   *
   * <p>这是一个标准属性。
   */
  String ATTR_ID             = "id";

  /**
   * 为元素中的内容指定语言代码。
   *
   * <p>这是一个标准属性。
   */
  String ATTR_LANG           = "lang";

  /**
   * 为元素指定内联样式。
   *
   * <p>这是一个标准属性。
   */
  String ATTR_STYLE          = "style";

  /**
   * 指定元素的tab键顺序。
   *
   * <p>这是一个标准属性。
   */
  String ATTR_TABINDEX       = "tabindex";

  /**
   * 指定关于元素的额外信息。
   *
   * <p>这是一个标准属性。
   */
  String ATTR_TITLE          = "title";

  /**
   * 在XHTML文档中为元素中的内容指定语言代码。
   *
   * <p>这是一个标准属性。
   */
  String ATTR_XML_LANG       = "xml:lang";

  /**
   * 指定链接文档的字符集。
   *
   * <p>应用于标签 {@code a}、{@code link}、{@code script}。
   */
  String ATTR_CHARSET        = "charset";

  /**
   * 指定元素的坐标。
   *
   * <p>应用于标签 {@code a}、{@code area}。
   */
  String ATTR_COORDS         = "coords";

  /**
   * 指定链接的目标地址。
   *
   * <p>应用于标签 {@code a}、{@code area}、{@code base}、{@code link}。
   */
  String ATTR_HREF           = "href";

  /**
   * 指定链接文档的语言。
   *
   * <p>应用于标签 {@code a}、{@code link}。
   */
  String ATTR_HREFLANG       = "hreflang";

  /**
   * 指定元素的名称。
   *
   * <p>应用于标签 {@code a}、{@code applet}、{@code button}、
   * {@code form}、{@code frame}、{@code iframe}、
   * {@code input}、{@code map}、{@code meta}、
   * {@code object}、{@code param}、{@code select}、
   * {@code textarea}。
   */
  String ATTR_NAME           = "name";

  /**
   * 指定当前文档与链接文档之间的关系。
   *
   * <p>应用于标签 {@code a}、{@code link}。
   */
  String ATTR_REL            = "rel";

  /**
   * 指定链接文档与当前文档之间的关系。
   *
   * <p>应用于标签 {@code a}、{@code link}。
   */
  String ATTR_REV            = "rev";

  /**
   * 指定元素的形状。
   *
   * <p>应用于标签 {@code a}、{@code area}。
   */
  String ATTR_SHAPE          = "shape";

  /**
   * 指定在何处打开 {@code href} 属性中指定的链接页面。
   *
   * <p>应用于标签 {@code a}、{@code area}、{@code base}、
   * {@code form}、{@code link}。
   */
  String ATTR_TARGET         = "target";

  /**
   * 指定 Java 小程序的文件名。
   *
   * <p>应用于标签 {@code applet}。
   */
  String ATTR_CODE           = "code";

  /**
   * 指定对小程序序列化表示的引用。
   *
   * <p>应用于标签 {@code applet}。
   */
  String ATTR_OBJECT         = "object";

  /**
   * 指定元素相对于周围元素的对齐方式。
   *
   * <p>应用于标签 {@code applet}、{@code caption}、{@code col}、
   * {@code colgroup}、{@code div}、{@code h1}、{@code h2}、
   * {@code h3}、{@code h4}、{@code h5}、{@code h6}、
   * {@code hr}、{@code iframe}、{@code img}、{@code input}、
   * {@code legend}、{@code object}、{@code p}、
   * {@code table}、{@code tbody}、{@code td}、{@code tfoot}、
   * {@code th}、{@code thead}、{@code tr}。
   */
  String ATTR_ALIGN          = "align";

  /**
   * 指定元素的替代文本。
   *
   * <p>应用于标签 {@code applet}、{@code area}、{@code img}、
   * {@code input}。
   */
  String ATTR_ALT            = "alt";

  /**
   * 指定归档文件的位置。
   *
   * <p>应用于标签 {@code applet}、{@code object}。
   */
  String ATTR_ARCHIVE        = "archive";

  /**
   * 为代码属性中指定的小程序/对象指定相对基础URL。
   *
   * <p>应用于标签 {@code applet}、{@code object}。
   */
  String ATTR_CODEBASE       = "codebase";

  /**
   * 指定元素的高度。
   *
   * <p>应用于标签 {@code applet}、{@code iframe}、{@code img}、
   * {@code object}、{@code td}、{@code th}。
   */
  String ATTR_HEIGHT         = "height";

  /**
   * 定义元素周围的水平间距。
   *
   * <p>应用于标签 {@code applet}、{@code img}、{@code object}。
   */
  String ATTR_HSPACE         = "hspace";

  /**
   * 定义元素周围的垂直间距。
   *
   * <p>应用于标签 {@code applet}、{@code img}、{@code object}。
   */
  String ATTR_VSPACE         = "vspace";

  /**
   * 指定元素的宽度。
   *
   * <p>应用于标签 {@code applet}、{@code col}、{@code colgroup}、
   * {@code hr}、{@code iframe}、{@code img}、
   * {@code object}、{@code pre}、{@code table}、{@code td}、
   * {@code th}。
   */
  String ATTR_WIDTH          = "width";

  /**
   * 指定某个区域没有关联的链接。
   *
   * <p>应用于标签 {@code area}。
   */
  String ATTR_NOHREF         = "nohref";

  /**
   * 指定引文的来源，或指定解释文本被删除/插入/更改原因的文档的URL。
   *
   * <p>应用于标签 {@code blockquote}、{@code del}、{@code ins}、
   * {@code q}。
   */
  String ATTR_CITE           = "cite";

  /**
   * 指定文档中活动链接的颜色。
   *
   * <p>已弃用，请使用样式代替。
   *
   * <p>应用于标签 {@code body}。
   */
  String ATTR_ALINK          = "alink";

  /**
   * 指定文档的背景图像。
   *
   * <p>已弃用，请使用样式代替。
   *
   * <p>应用于标签 {@code body}。
   */
  String ATTR_BACKGROUND     = "background";

  /**
   * 指定元素的背景颜色。
   *
   * <p>已弃用，请使用样式代替。
   *
   * <p>应用于标签 {@code body}、{@code table}、{@code td}、
   * {@code th}、{@code tr}。
   */
  String ATTR_BGCOLOR        = "bgcolor";

  /**
   * 指定文档中未访问链接的默认颜色。
   *
   * <p>已弃用，请使用样式代替。
   *
   * <p>应用于标签 {@code body}。
   */
  String ATTR_LINK           = "link";

  /**
   * 指定文档中文本的颜色。
   *
   * <p>已弃用，请使用样式代替。
   *
   * <p>应用于标签 {@code body}。
   */
  String ATTR_TEXT           = "text";

  /**
   * 指定文档中已访问链接的默认颜色。
   *
   * <p>已弃用，请使用样式代替。
   *
   * <p>应用于标签 {@code body}。
   */
  String ATTR_VLINK          = "vlink";

  /**
   * 指定元素应该被禁用。
   *
   * <p>应用于标签 {@code button}、{@code input}、
   * {@code optgroup}、{@code option}、{@code select}、
   * {@code textarea}。
   */
  String ATTR_DISABLED       = "disabled";

  /**
   * 指定元素的类型。
   *
   * <p>应用于标签 {@code button}、{@code input}、{@code li}、
   * {@code link}、{@code object}、{@code ol}、{@code param}、
   * {@code script}、{@code style}、{@code ul}。
   */
  String ATTR_TYPE           = "type";

  /**
   * 指定元素的基础值。
   *
   * <p>应用于标签 {@code button}、{@code input}、{@code li}、
   * {@code option}、{@code param}。
   */
  String ATTR_VALUE          = "value";

  /**
   * 将单元格中的内容与字符对齐。
   *
   * <p>应用于标签 {@code col}、{@code colgroup}、{@code tbody}、
   * {@code td}、{@code tfoot}、{@code th}、{@code thead}、
   * {@code tr}。
   */
  String ATTR_CHAR           = "char";

  /**
   * 指定内容将从 {@code char} 属性指定的字符对齐的字符数。
   *
   * <p>应用于标签 {@code col}、{@code colgroup}、{@code tbody}、
   * {@code td}、{@code tfoot}、{@code th}、{@code thead}、
   * {@code tr}。
   */
  String ATTR_CHAROFF        = "charoff";

  /**
   * 指定表格列元素应跨越的列数。
   *
   * <p>应用于标签 {@code col}、{@code colgroup}。
   */
  String ATTR_SPAN           = "span";

  /**
   * 指定与表格列元素相关的内容的垂直对齐。
   *
   * <p>应用于标签 {@code col}、{@code colgroup}、{@code tbody}、
   * {@code td}、{@code tfoot}、{@code th}、{@code thead}、
   * {@code tr}。
   */
  String ATTR_VALIGN         = "valign";

  /**
   * 指定文本被删除/插入/更改的日期和时间。
   *
   * <p>应用于标签 {@code del}、{@code ins}。
   */
  String ATTR_DATETIME       = "datetime";

  /**
   * 指定列表应该比正常大小更小地呈现。
   *
   * <p>已弃用，请使用样式代替。
   *
   * <p>应用于标签 {@code dir}、{@code menu}、{@code ol}、
   * {@code ul}。
   */
  String ATTR_COMPACT        = "compact";

  /**
   * 指定文本的颜色。
   *
   * <p>已弃用，请使用样式代替。
   *
   * <p>应用于标签 {@code font}。
   */
  String ATTR_COLOR          = "color";

  /**
   * 指定文本的字体。
   *
   * <p>已弃用，请使用样式代替。
   *
   * <p>应用于标签 {@code font}。
   */
  String ATTR_FACE           = "face";

  /**
   * 指定元素的大小。
   *
   * <p>已弃用，请使用样式代替。
   *
   * <p>应用于标签 {@code font}、{@code hr}、{@code input}、
   * {@code select}。
   */
  String ATTR_SIZE           = "size";

  /**
   * 指定提交表单时表单数据的发送地址。
   *
   * <p>应用于标签 {@code form}。
   */
  String ATTR_ACTION         = "action";

  /**
   * 指定可以通过文件上传提交的文件类型。
   *
   * <p>应用于标签 {@code form}、{@code input}。
   */
  String ATTR_ACCEPT         = "accept";

  /**
   * 指定服务器可以处理的表单数据字符集。
   *
   * <p>应用于标签 {@code form}。
   */
  String ATTR_ACCEPT_CHARSET = "accept-charset";

  /**
   * 指定表单数据在发送到服务器之前应如何编码。
   *
   * <p>应用于标签 {@code form}。
   */
  String ATTR_ENCTYPE        = "enctype";

  /**
   * 指定如何发送表单数据。
   *
   * <p>应用于标签 {@code form}。
   */
  String ATTR_METHOD         = "method";

  /**
   * 指定是否在框架周围显示边框。
   *
   * <p>应用于标签 {@code frame}、{@code iframe}。
   */
  String ATTR_FRAMEBORDER    = "frameborder";

  /**
   * 指定包含元素内容长描述的页面。
   *
   * <p>应用于标签 {@code frame}、{@code iframe}、{@code img}。
   */
  String ATTR_LONGDESC       = "longdesc";

  /**
   * 指定框架的上下边距。
   *
   * <p>应用于标签 {@code frame}、{@code iframe}。
   */
  String ATTR_MARGINHEIGHT   = "marginheight";

  /**
   * 指定框架的左右边距。
   *
   * <p>应用于标签 {@code frame}、{@code iframe}。
   */
  String ATTR_MARGINWIDTH    = "marginwidth";

  /**
   * 指定框架不能被调整大小。
   *
   * <p>应用于标签 {@code frame}。
   */
  String ATTR_NORESIZE       = "noresize";

  /**
   * 指定是否在框架中显示滚动条。
   *
   * <p>应用于标签 {@code frame}、{@code iframe}。
   */
  String ATTR_SCROLLING      = "scrolling";

  /**
   * 指定要在元素中显示的内容的URL。
   *
   * <p>应用于标签 {@code frame}、{@code iframe}、{@code img}、
   * {@code input}、{@code script}。
   */
  String ATTR_SRC            = "src";

  /**
   * 指定元素中列的数量和大小。
   *
   * <p>应用于标签 {@code frameset}、{@code textarea}。
   */
  String ATTR_COLS           = "cols";

  /**
   * 指定元素中行的数量和大小。
   *
   * <p>应用于标签 {@code frameset}、{@code textarea}。
   */
  String ATTR_ROWS           = "rows";

  /**
   * 指定包含一组规则的文档的URL。浏览器可以读取这些规则以
   * 清楚地理解 {@code meta} 标签的 {@code content} 属性中的信息。
   *
   * <p>应用于标签 {@code head}。
   */
  String ATTR_PROFILE        = "profile";

  /**
   * 指定 {@code hr} 元素应以单一实色（无阴影）呈现，
   * 而不是阴影颜色。
   *
   * <p>已弃用，请使用样式代替。
   *
   * <p>应用于标签 {@code hr}。
   */
  String ATTR_NOSHADE        = "noshade";

  /**
   * 指定要使用的命名空间（仅适用于XHTML文档！）。
   *
   * <p>应用于标签 {@code html}。
   */
  String ATTR_XMLNS          = "xmlns";

  /**
   * 指定元素周围边框的宽度。
   *
   * <p>已弃用，请使用样式代替。
   *
   * <p>应用于标签 {@code img}、{@code object}、{@code table}。
   */
  String ATTR_BORDER         = "border";

  /**
   * 指定图像为服务器端图像映射。
   *
   * <p>应用于标签 {@code img}。
   */
  String ATTR_ISMAP          = "ismap";

  /**
   * 指定图像为客户端图像映射。
   *
   * <p>应用于标签 {@code img}、{@code object}。
   */
  String ATTR_USEMAP         = "usemap";

  /**
   * 指定输入元素在页面加载时应该被预选
   * （对于 type="checkbox" 或 type="radio"）。
   *
   * <p>应用于标签 {@code input}。
   */
  String ATTR_CHECKED        = "checked";

  /**
   * 指定输入字段的最大长度（字符数）
   * （对于 type="text" 或 type="password"）。
   *
   * <p>应用于标签 {@code input}。
   */
  String ATTR_MAXLENGTH      = "maxlength";

  /**
   * 指定元素的内容应该是只读的。
   *
   * <p>应用于标签 {@code input}、{@code textarea}。
   */
  String ATTR_READONLY       = "readonly";

  /**
   * 指定标签绑定到哪个表单元素。
   *
   * <p>应用于标签 {@code label}。
   */
  String ATTR_FOR            = "for";

  /**
   * 指定链接文档将在什么设备上显示。
   *
   * <p>应用于标签 {@code link}、{@code style}。
   */
  String ATTR_MEDIA          = "media";

  /**
   * 指定元信息的内容。
   *
   * <p>应用于标签 {@code meta}。
   */
  String ATTR_CONTENT        = "content";

  /**
   * 为内容属性中的信息提供HTTP头。
   *
   * <p>应用于标签 {@code meta}。
   */
  String ATTR_HTTP_EQUIV     = "http-equiv";

  /**
   * 指定用于解释内容属性值的方案。
   *
   * <p>应用于标签 {@code meta}。
   */
  String ATTR_SCHEME         = "scheme";

  /**
   * 定义在Windows注册表中设置的类ID值或URL。
   *
   * <p>应用于标签 {@code object}。
   */
  String ATTR_CLASSID        = "classid";

  /**
   * {@code classid} 属性引用的代码的互联网媒体类型。
   *
   * <p>应用于标签 {@code object}。
   */
  String ATTR_CODETYPE       = "codetype";

  /**
   * 定义引用对象数据的URL。
   *
   * <p>应用于标签 {@code object}。
   */
  String ATTR_DATA           = "data";

  /**
   * 定义对象应该只被声明，而不是在需要之前创建或实例化。
   *
   * <p>应用于标签 {@code object}。
   */
  String ATTR_DECLARE        = "declare";

  /**
   * 定义对象加载时显示的文本。
   *
   * <p>应用于标签 {@code object}。
   */
  String ATTR_STANDBY        = "standby";

  /**
   * 指定列表中的起始点。
   *
   * <p>已弃用，请使用样式代替。
   *
   * <p>应用于标签 {@code ol}。
   */
  String ATTR_START          = "start";

  /**
   * 指定选项组的描述。
   *
   * <p>应用于标签 {@code optgroup}、{@code option}。
   */
  String ATTR_LABEL          = "label";

  /**
   * 指定选项应该默认被选中。
   *
   * <p>应用于标签 {@code option}。
   */
  String ATTR_SELECTED       = "selected";

  /**
   * 指定值的类型。
   *
   * <p>应用于标签 {@code param}。
   */
  String ATTR_VALUETYPE      = "valuetype";

  /**
   * 指定脚本的执行应该被推迟（延迟）直到页面加载完成。
   *
   * <p>应用于标签 {@code script}。
   */
  String ATTR_DEFER          = "defer";

  /**
   * 指定代码中的空白字符是否应该被保留。
   *
   * <p>应用于标签 {@code script}。
   */
  String ATTR_XML_SPACE      = "xml:space";

  /**
   * 指定可以选择多个选项。
   *
   * <p>应用于标签 {@code select}。
   */
  String ATTR_MULTIPLE       = "multiple";

  /**
   * 指定单元格壁和单元格内容之间的空间。
   *
   * <p>应用于标签 {@code table}。
   */
  String ATTR_CELLPADDING    = "cellpadding";

  /**
   * 指定单元格之间的空间。
   *
   * <p>应用于标签 {@code table}。
   */
  String ATTR_CELLSPACING    = "cellspacing";

  /**
   * 指定外边框的哪些部分应该可见。
   *
   * <p>应用于标签 {@code table}。
   */
  String ATTR_FRAME          = "frame";

  /**
   * 指定内边框的哪些部分应该可见。
   *
   * <p>应用于标签 {@code table}。
   */
  String ATTR_RULES          = "rules";

  /**
   * 指定表格内容的摘要。
   *
   * <p>应用于标签 {@code table}。
   */
  String ATTR_SUMMARY        = "summary";

  /**
   * 指定单元格内容的缩写版本。
   *
   * <p>应用于标签 {@code td}、{@code th}。
   */
  String ATTR_ABBR           = "abbr";

  /**
   * 对单元格进行分类。
   *
   * <p>应用于标签 {@code td}、{@code th}。
   */
  String ATTR_AXIS           = "axis";

  /**
   * 指定单元格应跨越的列数。
   *
   * <p>应用于标签 {@code td}、{@code th}。
   */
  String ATTR_COLSPAN        = "colspan";

  /**
   * 指定与单元格相关的表头。
   *
   * <p>应用于标签 {@code td}。
   */
  String ATTR_HEADERS        = "headers";

  /**
   * 指定单元格内的内容不应换行。
   *
   * <p>已弃用，请使用样式代替。
   *
   * <p>应用于标签 {@code td}、{@code th}。
   */
  String ATTR_NOWRAP         = "nowrap";

  /**
   * 设置单元格应跨越的行数。
   *
   * <p>应用于标签 {@code td}、{@code th}。
   */
  String ATTR_ROWSPAN        = "rowspan";

  /**
   * 定义在表格中关联表头单元格和数据单元格的方法。
   *
   * <p>应用于标签 {@code td}、{@code th}。
   */
  String ATTR_SCOPE          = "scope";

}