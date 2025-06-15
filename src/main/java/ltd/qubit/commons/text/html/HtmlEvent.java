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
 * 此接口定义HTML事件名的常量。
 *
 * <p>常量的名称形式为{@code ATTR_[大写事件属性名]}。
 *
 * @author 胡海星
 * @see HtmlTag
 * @see HtmlAttribute
 * @see HtmlAttributeValue
 * @see <a href="http://www.w3schools.com/tags/default.asp">HTML Element Reference</a>
 */
public interface HtmlEvent {

  /**
   * 文档加载时要运行的脚本。
   */
  String ATTR_ONLOAD      = "onload";

  /**
   * 文档卸载时要运行的脚本。
   */
  String ATTR_ONUNLOAD    = "onunload";

  /**
   * 元素失去焦点时要运行的脚本。
   */
  String ATTR_ONBLUR      = "onblur";

  /**
   * 元素改变时要运行的脚本。
   */
  String ATTR_ONCHANGE    = "onchange";

  /**
   * 元素获得焦点时要运行的脚本。
   */
  String ATTR_ONFOCUS     = "onfocus";

  /**
   * 表单重置时要运行的脚本。
   */
  String ATTR_ONRESET     = "onreset";

  /**
   * 元素被选中时要运行的脚本。
   */
  String ATTR_ONSELECT    = "onselect";

  /**
   * 表单提交时要运行的脚本。
   */
  String ATTR_ONSUBMIT    = "onsubmit";

  /**
   * 图像加载被中断时要运行的脚本。
   */
  String ATTR_ONABORT     = "onabort";

  /**
   * 按键被按下时要运行的脚本。
   */
  String ATTR_ONKEYDOWN   = "onkeydown";

  /**
   * 按键被按下并释放时要运行的脚本。
   */
  String ATTR_ONKEYPRESS  = "onkeypress";

  /**
   * 按键被释放时要运行的脚本。
   */
  String ATTR_ONKEYUP     = "onkeyup";

  /**
   * 鼠标点击时要运行的脚本。
   */
  String ATTR_ONCLICK     = "onclick";

  /**
   * 鼠标双击时要运行的脚本。
   */
  String ATTR_ONDBLCLICK  = "ondblclick";

  /**
   * 鼠标按钮被按下时要运行的脚本。
   */
  String ATTR_ONMOUSEDOWN = "onmousedown";

  /**
   * 鼠标指针移动时要运行的脚本。
   */
  String ATTR_ONMOUSEMOVE = "onmousemove";

  /**
   * 鼠标指针移出元素时要运行的脚本。
   */
  String ATTR_ONMOUSEOUT  = "onmouseout";

  /**
   * 鼠标指针移过元素时要运行的脚本。
   */
  String ATTR_ONMOUSEOVER = "onmouseover";

  /**
   * 鼠标按钮被释放时要运行的脚本。
   */
  String ATTR_ONMOUSEUP   = "onmouseup";
}