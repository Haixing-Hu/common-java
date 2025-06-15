////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.html;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 此类定义HTML标签名称的常量。
 *
 * <p>常量的名称形式为{@code TAG_[大写标签名]}。
 *
 * @author 胡海星
 * @see HtmlAttribute
 * @see HtmlAttributeValue
 * @see HtmlEvent
 * @see <a href="http://www.w3schools.com/tags/default.asp">HTML Element Reference</a>
 * @see <a href="http://reference.sitepoint.com/html/elements">Elements</a>
 */
public final class HtmlTag {

  private HtmlTag() {}

  /**
   * 定义锚点（超链接）。
   */
  public static final String TAG_A          = "a";

  /**
   * 定义缩写。
   */
  public static final String TAG_ABBR       = "abbr";

  /**
   * 定义首字母缩略词。
   */
  public static final String TAG_ACRONYM    = "acronym";

  /**
   * 定义文档作者/所有者的联系信息。
   */
  public static final String TAG_ADDRESS    = "contact";

  /**
   * 已弃用。定义嵌入的小程序。
   */
  public static final String TAG_APPLET     = "applet";

  /**
   * 定义图像映射内的区域。
   */
  public static final String TAG_AREA       = "area";

  /**
   * 定义粗体文本。
   */
  public static final String TAG_B          = "b";

  /**
   * 定义页面上所有链接的默认联系人或默认目标。
   */
  public static final String TAG_BASE       = "base";

  /**
   * 已弃用。定义页面中文本的默认字体、颜色或大小。
   */
  public static final String TAG_BASEFONT   = "basefont";

  /**
   * 定义文本方向。
   */
  public static final String TAG_BDO        = "bdo";

  /**
   * {@code bgsound} 元素用于在页面加载时播放音频文件，
   * 并具有一些属性来控制该音频，包括平衡、音量、循环，
   * 以及最重要的 {@code src} 属性，该属性引用所需的文件。
   */
  public static final String TAG_BGSOUND    = "bgsound";

  /**
   * 定义大文本。
   */
  public static final String TAG_BIG        = "big";

  /**
   * blink元素不是任何标准的一部分，最初由早期版本的Netscape浏览器引入。
   * 它的唯一目的是使文本闪烁开关，其唯一结果是让所有遇到它的人普遍感到厌烦。
   * 说它不受欢迎是一种轻描淡写。只有当你的意图是拥有无效的应用页面，
   * 并且你希望你的观众认为他们已经回到了过去，大约在90年代初的某个时候，
   * 才使用这个元素。
   */
  public static final String TAG_BLINK      = "blink";

  /**
   * 定义长引用。
   */
  public static final String TAG_BLOCKQUOTE = "blockquote";

  /**
   * 定义文档的主体。
   */
  public static final String TAG_BODY       = "body";

  /**
   * 定义单行换行。
   */
  public static final String TAG_BR         = "br";

  /**
   * 定义按钮。
   */
  public static final String TAG_BUTTON     = "button";

  /**
   * 定义表格标题。
   */
  public static final String TAG_CAPTION    = "caption";

  /**
   * 已弃用。定义居中文本。
   */
  public static final String TAG_CENTER     = "center";

  /**
   * 定义引文。
   */
  public static final String TAG_CITE       = "cite";

  /**
   * 定义计算机代码文本。
   */
  public static final String TAG_CODE       = "code";

  /**
   * 定义表格中一列或多列的属性值。
   */
  public static final String TAG_COL        = "col";

  /**
   * 定义表格中用于格式化的列组。
   */
  public static final String TAG_COLGROUP   = "colgroup";

  /**
   * comment元素是一个非标准且支持很少的元素，用于向标记添加注释。
   * 它只有一个属性——data——指向提供有关注释的更多信息的应用页面。
   * 今后，不应使用此元素。相反，使用标准的HTML注释语法 &lt;!-- comment here --&gt;。
   */
  public static final String TAG_COMMENT    = "comment";

  /**
   * 定义定义列表中术语的描述。
   */
  public static final String TAG_DD         = "dd";

  /**
   * 定义删除的文本。
   */
  public static final String TAG_DEL        = "del";

  /**
   * 定义定义术语。
   */
  public static final String TAG_DFN        = "dfn";

  /**
   * 已弃用。定义目录列表。
   */
  public static final String TAG_DIR        = "dir";

  /**
   * 定义文档中的节。
   */
  public static final String TAG_DIV        = "div";

  /**
   * 定义定义列表。
   */
  public static final String TAG_DL         = "dl";

  /**
   * 定义定义列表中的术语（项目）。
   */
  public static final String TAG_DT         = "dt";

  /**
   * 定义强调文本。
   */
  public static final String TAG_EM         = "em";

  /**
   * {@code embed} 是一个非标准但支持良好的元素，用于嵌入多媒体内容，
   * 包括可能不被浏览器本机支持的媒体类型。它也可以用于嵌入受支持的媒体
   * 类型，例如 .jpg、.gif 或 .png 格式的图像。
   */
  public static final String TAG_EMBED      = "embed";

  /**
   * 定义表格边框。
   */
  public static final String TAG_FIELDSET   = "fieldset";

  /**
   * 已弃用。定义字体、颜色和文本大小。
   */
  public static final String TAG_FONT       = "font";

  /**
   * 定义 HTML 表单以获取用户输入。
   */
  public static final String TAG_FORM       = "form";

  /**
   * 定义框架集中的窗口（框架）。
   */
  public static final String TAG_FRAME      = "frame";

  /**
   * 定义框架集。
   */
  public static final String TAG_FRAMESET   = "frameset";

  /**
   * 定义 HTML 标题 1。
   */
  public static final String TAG_H1         = "h1";

  /**
   * 定义 HTML 标题 2。
   */
  public static final String TAG_H2         = "h2";

  /**
   * 定义 HTML 标题 3。
   */
  public static final String TAG_H3         = "h3";

  /**
   * 定义 HTML 标题 4。
   */
  public static final String TAG_H4         = "h4";

  /**
   * 定义 HTML 标题 5。
   */
  public static final String TAG_H5         = "h5";

  /**
   * 定义 HTML 标题 6。
   */
  public static final String TAG_H6         = "h6";

  /**
   * 定义文档信息。
   */
  public static final String TAG_HEAD       = "head";

  /**
   * 定义水平线。
   */
  public static final String TAG_HR         = "hr";

  /**
   * 定义 HTML 文档。
   */
  public static final String TAG_HTML       = "html";

  /**
   * 定义斜体文本。
   */
  public static final String TAG_I          = "i";

  /**
   * 定义内联框架。
   */
  public static final String TAG_IFRAME     = "iframe";

  /**
   * 定义图像。
   */
  public static final String TAG_IMG        = "img";

  /**
   * 定义输入控制。
   */
  public static final String TAG_INPUT      = "input";

  /**
   * 定义插入的文本。
   */
  public static final String TAG_INS        = "ins";

  /**
   * 已弃用。定义可搜索的当前相关文档。
   */
  public static final String TAG_ISINDEX    = "isindex";

  /**
   * 定义键盘文本。
   */
  public static final String TAG_KBD        = "kbd";

  /**
   * 定义 {@code input} 元素的标签。
   */
  public static final String TAG_LABEL      = "label";

  /**
   * 定义 {@code fieldset} 元素的标题。
   */
  public static final String TAG_LEGEND     = "legend";

  /**
   * 定义列表项。
   */
  public static final String TAG_LI         = "li";

  /**
   * 定义文档与外部资源的关联。
   */
  public static final String TAG_LINK       = "link";

  /**
   * marquee 元素提供了一种方法，可以让浏览器渲染移动的文本，
   * 而无需诉诸 JavaScript 技术。这个 marquee 是非标准的，但享受（或可能遭受）良好的浏览器支持。
   */
  public static final String TAG_MARQUEE    = "marquee";

  /**
   * 定义图像映射。
   */
  public static final String TAG_MAP        = "map";

  /**
   * 已弃用。定义菜单列表。
   */
  public static final String TAG_MENU       = "menu";

  /**
   * 定义 HTML 文档的元数据。
   */
  public static final String TAG_META       = "meta";

  /**
   * nobr 元素是一个专有（即不基于任何标准）元素，用于定义文本部分，
   * 无论发生什么，浏览器都不应允许这些文本部分换行，例如用户将窗口调整到小视口。
   * 由于它已弃用，并且效果可以通过 CSS 实现，因此不应使用该元素；此元素仅用于参考。
   */
  public static final String TAG_NOBR       = "nobr";

  /**
   * {@code noembed} 元素用于为不支持 {@code embed} 元素的浏览器提供替代内容。
   * 它不是由任何标准定义的（它是早期 Netscape 浏览器引入的），因此没有关于它可以包含或不可以包含的指南。
   */
  public static final String TAG_NOEMBED    = "noembed";

  /**
   * 为不支持客户端脚本的浏览器定义替代内容。
   */
  public static final String TAG_NOFRAMES   = "noframes";

  /**
   * 为不支持客户端脚本的浏览器定义替代内容。
   */
  public static final String TAG_NOSCRIPT   = "noscript";

  /**
   * 定义嵌入对象。
   */
  public static final String TAG_OBJECT     = "object";

  /**
   * 定义有序列表。
   */
  public static final String TAG_OL         = "ol";

  /**
   * 定义选择列表中的一组相关选项。
   */
  public static final String TAG_OPTGROUP   = "optgroup";

  /**
   * 定义选择列表中的选项。
   */
  public static final String TAG_OPTION     = "option";

  /**
   * 定义段落。
   */
  public static final String TAG_P          = "p";

  /**
   * 定义对象的参数。
   */
  public static final String TAG_PARAM      = "param";

  /**
   * 纯文本元素最初是为了让浏览器忽略任何格式或 HTML 标记，
   * 这样 &lt;p&gt; 就会在屏幕上显示为 &lt;p&gt; 而不是实际创建新段落。
   * 它已弃用，可能最好完全忘记它。
   */
  public static final String TAG_PLAINTEXT  = "plaintext";

  /**
   * 定义预格式化文本。
   *
   * @see #TAG_XMP
   */
  public static final String TAG_PRE        = "pre";

  /**
   * rb 元素是 ruby 元素的子元素，用于包含需要发音帮助或作为学习辅助显示的字符。
   */
  public static final String TAG_RB         = "rb";

  /**
   * rbc（ruby 基础容器）元素将一组 rb 元素分组，这些元素将在后续 rtc 容器中具有相关注释。
   * 在上面的示例中，从 W3C 文档中获取的示例中，rbc 包含四个日文字符（更复杂的 kanji 符号），
   * 每个字符都有自己的 rb 元素。同时，ruby 注释在相关的 rt 元素中以平假名音节（称为 furigana 当用于此目的时）写入。
   * 最后，有一个英文注释，它跨越所有四个以前的 rb 和 rt 元素。
   */
  public static final String TAG_RBC        = "rbc";

  /**
   * 尽管早在 2001 年就已定义，ruby 在浏览器中的支持度并不高。
   * 为了改变，Internet Explorer 在这方面领先于游戏！
   * 鉴于并非所有浏览器都会理解 ruby，rp 元素可能用于向不理解或不支持 ruby 的浏览器用户显示内容，
   * 但删除该内容以供支持 ruby 的浏览器使用。rp 元素中的内容应该是括号，尽管没有明确的规则规定应该使用哪个字符。
   * 最有可能的是，你会使用 "(",")","[" or "]".
   */
  public static final String TAG_RP         = "rp";

  /**
   * rt 元素是 ruby 元素的子元素，并包含将显示给用户的注释，理想情况下在基本文本（rb）旁边或上方，
   * 该基本文本需要注释。
   */
  public static final String TAG_RT         = "rt";

  /**
   * rtc（ruby 文本容器）元素将一组 rt 元素分组，这些元素包含与 rbc 容器中的内容相关的注释。
   * 上面的示例包含四个日文字符（更复杂的 Kanji 符号），每个字符都有自己的 rb 元素，
   * 而 ruby 注释在相关的 rt 元素中以平假名音节（称为 furigana 当用于此目的时）写入。
   * 在上面的示例中，从 W3C 文档中获取的示例中，有两个 rtc 元素，它们提供日语和英语注释。
   */
  public static final String TAG_RTC        = "rtc";

  /**
   * ruby 元素提供了一种机制，用于注释东亚语言的字符（日语、中文、韩语等）。
   * 通常，这些注释出现在常规文本上方或旁边的小字体中。
   */
  public static final String TAG_RUBY       = "ruby";

  /**
   * 定义短引用。
   */
  public static final String TAG_Q          = "q";

  /**
   * 已弃用。定义删除线文本。
   *
   * @see #TAG_STRIKE
   */
  public static final String TAG_S          = "s";

  /**
   * 定义样本计算机代码。
   */
  public static final String TAG_SAMP       = "samp";

  /**
   * 定义客户端脚本。
   */
  public static final String TAG_SCRIPT     = "script";

  /**
   * 定义选择列表（下拉列表）。
   */
  public static final String TAG_SELECT     = "select";

  /**
   * 定义小文本。
   */
  public static final String TAG_SMALL      = "small";

  /**
   * 定义文档中的节。
   */
  public static final String TAG_SPAN       = "span";

  /**
   * 已弃用。定义删除线文本。
   */
  public static final String TAG_STRIKE     = "strike";

  /**
   * 定义强文本。
   */
  public static final String TAG_STRONG     = "strong";

  /**
   * 定义文档样式信息。
   */
  public static final String TAG_STYLE      = "style";

  /**
   * 定义下标文本。
   */
  public static final String TAG_SUB        = "sub";

  /**
   * 定义上标文本。
   */
  public static final String TAG_SUP        = "sup";

  /**
   * 定义表格。
   */
  public static final String TAG_TABLE      = "table";

  /**
   * 对表格体内容进行分组。
   */
  public static final String TAG_TBODY      = "tbody";

  /**
   * 定义表格中的单元格。
   */
  public static final String TAG_TD         = "td";

  /**
   * 定义多行文本 {@code input} 控制。
   */
  public static final String TAG_TEXTAREA   = "textarea";

  /**
   * 对表格页脚内容进行分组。
   */
  public static final String TAG_TFOOT      = "tfoot";

  /**
   * 定义表格中的标题单元格。
   */
  public static final String TAG_TH         = "th";

  /**
   * 对表格标题内容进行分组。
   */
  public static final String TAG_THEAD      = "thead";

  /**
   * 定义文档标题。
   */
  public static final String TAG_TITLE      = "title";

  /**
   * 定义表格中的行。
   */
  public static final String TAG_TR         = "tr";

  /**
   * 定义打字机文本。
   */
  public static final String TAG_TT         = "tt";

  /**
   * 已弃用。定义下划线文本。
   */
  public static final String TAG_U          = "u";

  /**
   * 定义无序列表。
   */
  public static final String TAG_UL         = "ul";

  /**
   * 定义文本的可变部分。
   */
  public static final String TAG_VAR        = "var";

  /**
   * wbr 元素的目的是建议/提示浏览器在单词/短语中哪个点是最合适的点
   * （用连字符表示），以便在浏览器视口或包含元素缩小尺寸时发生换行。
   */
  public static final String TAG_WBR        = "wbr";

  /**
   * 已弃用。定义预格式化文本。
   *
   * @see #TAG_PRE
   */
  public static final String TAG_XMP        = "xmp";

  /**
   * 结构元素的集合。
   */
  public static final Set<String> STRUCTURAL_ELEMENTS;

  static {
    final Set<String> structuralElements = new HashSet<>();
    structuralElements.add(TAG_BLOCKQUOTE);
    structuralElements.add(TAG_BODY);
    structuralElements.add(TAG_BR);
    structuralElements.add(TAG_DIV);
    structuralElements.add(TAG_H1);
    structuralElements.add(TAG_H2);
    structuralElements.add(TAG_H3);
    structuralElements.add(TAG_H4);
    structuralElements.add(TAG_H5);
    structuralElements.add(TAG_H6);
    structuralElements.add(TAG_HEAD);
    structuralElements.add(TAG_HR);
    structuralElements.add(TAG_HTML);
    structuralElements.add(TAG_P);

    STRUCTURAL_ELEMENTS = Collections.unmodifiableSet(structuralElements);
  }

  /**
   * 头元素的集合。
   */
  public static final Set<String> HEAD_ELEMENTS;

  static {
    final Set<String> headElements = new HashSet<>();
    headElements.add(TAG_BASE);
    headElements.add(TAG_LINK);
    headElements.add(TAG_META);
    headElements.add(TAG_SCRIPT);
    headElements.add(TAG_STYLE);
    headElements.add(TAG_TITLE);
    HEAD_ELEMENTS = Collections.unmodifiableSet(headElements);
  }

  /**
   * 列表元素的集合。
   */
  public static final Set<String> LIST_ELEMENTS;

  static {
    final Set<String> listElements = new HashSet<>();
    listElements.add(TAG_DD);
    listElements.add(TAG_DIR);
    listElements.add(TAG_DL);
    listElements.add(TAG_DT);
    listElements.add(TAG_LI);
    listElements.add(TAG_MENU);
    listElements.add(TAG_OL);
    listElements.add(TAG_UL);
    LIST_ELEMENTS = Collections.unmodifiableSet(listElements);
  }

  /**
   * 文本格式元素的集合。
   */
  public static final Set<String> TEXT_FORMATTING_ELEMENTS;

  static {
    final Set<String> textFormattingElements = new HashSet<>();
    textFormattingElements.add(TAG_A);
    textFormattingElements.add(TAG_ABBR);
    textFormattingElements.add(TAG_ACRONYM);
    textFormattingElements.add(TAG_ADDRESS);
    textFormattingElements.add(TAG_B);
    textFormattingElements.add(TAG_BASEFONT);
    textFormattingElements.add(TAG_BDO);
    textFormattingElements.add(TAG_BIG);
    textFormattingElements.add(TAG_BLINK);
    textFormattingElements.add(TAG_CENTER);
    textFormattingElements.add(TAG_CITE);
    textFormattingElements.add(TAG_CODE);
    textFormattingElements.add(TAG_COMMENT);
    textFormattingElements.add(TAG_DEL);
    textFormattingElements.add(TAG_DFN);
    textFormattingElements.add(TAG_EM);
    textFormattingElements.add(TAG_FONT);
    textFormattingElements.add(TAG_I);
    textFormattingElements.add(TAG_INS);
    textFormattingElements.add(TAG_KBD);
    textFormattingElements.add(TAG_MARQUEE);
    textFormattingElements.add(TAG_NOBR);
    textFormattingElements.add(TAG_NOSCRIPT);
    textFormattingElements.add(TAG_PLAINTEXT);
    textFormattingElements.add(TAG_PRE);
    textFormattingElements.add(TAG_Q);
    textFormattingElements.add(TAG_RB);
    textFormattingElements.add(TAG_RBC);
    textFormattingElements.add(TAG_RP);
    textFormattingElements.add(TAG_RT);
    textFormattingElements.add(TAG_RTC);
    textFormattingElements.add(TAG_RUBY);
    textFormattingElements.add(TAG_S);
    textFormattingElements.add(TAG_SAMP);
    textFormattingElements.add(TAG_SMALL);
    textFormattingElements.add(TAG_SPAN);
    textFormattingElements.add(TAG_STRIKE);
    textFormattingElements.add(TAG_STRONG);
    textFormattingElements.add(TAG_SUB);
    textFormattingElements.add(TAG_SUP);
    textFormattingElements.add(TAG_TT);
    textFormattingElements.add(TAG_U);
    textFormattingElements.add(TAG_VAR);
    textFormattingElements.add(TAG_WBR);
    textFormattingElements.add(TAG_XMP);
    TEXT_FORMATTING_ELEMENTS = Collections.unmodifiableSet(textFormattingElements);
  }

  /**
   * 表单元素的集合。
   */
  public static final Set<String> FORM_ELEMENTS;

  static {
    final Set<String> formElements = new HashSet<>();
    formElements.add(TAG_BUTTON);
    formElements.add(TAG_FIELDSET);
    formElements.add(TAG_FORM);
    formElements.add(TAG_INPUT);
    formElements.add(TAG_ISINDEX);
    formElements.add(TAG_LABEL);
    formElements.add(TAG_LEGEND);
    formElements.add(TAG_OPTGROUP);
    formElements.add(TAG_OPTION);
    formElements.add(TAG_SELECT);
    formElements.add(TAG_TEXTAREA);
    FORM_ELEMENTS = Collections.unmodifiableSet(formElements);
  }

  /**
   * 图像和媒体元素的集合。
   */
  public static final Set<String> MEDIA_ELEMENTS;

  static {
    final Set<String> mediaElements = new HashSet<>();
    mediaElements.add(TAG_APPLET);
    mediaElements.add(TAG_AREA);
    mediaElements.add(TAG_BGSOUND);
    mediaElements.add(TAG_EMBED);
    mediaElements.add(TAG_IMG);
    mediaElements.add(TAG_MAP);
    mediaElements.add(TAG_NOEMBED);
    mediaElements.add(TAG_OBJECT);
    mediaElements.add(TAG_PARAM);
    MEDIA_ELEMENTS = Collections.unmodifiableSet(mediaElements);
  }

  /**
   * 表格元素的集合。
   */
  public static final Set<String> TABLE_ELEMENTS;

  static {
    final Set<String> tableElements = new HashSet<>();
    tableElements.add(TAG_CAPTION);
    tableElements.add(TAG_COL);
    tableElements.add(TAG_COLGROUP);
    tableElements.add(TAG_TABLE);
    tableElements.add(TAG_TBODY);
    tableElements.add(TAG_TD);
    tableElements.add(TAG_TFOOT);
    tableElements.add(TAG_TH);
    tableElements.add(TAG_THEAD);
    tableElements.add(TAG_TR);
    TABLE_ELEMENTS = Collections.unmodifiableSet(tableElements);
  }

  /**
   * 框架和窗口元素的集合。
   */
  public static final Set<String> FRAME_ELEMENTS;

  static {
    final Set<String> frameElements = new HashSet<>();
    frameElements.add(TAG_FRAME);
    frameElements.add(TAG_FRAMESET);
    frameElements.add(TAG_NOFRAMES);
    frameElements.add(TAG_IFRAME);
    FRAME_ELEMENTS = Collections.unmodifiableSet(frameElements);
  }

}