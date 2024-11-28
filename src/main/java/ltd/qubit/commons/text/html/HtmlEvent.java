////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.html;

/**
 * This interface defines the constant of HTML event names.
 *
 * <p>The name of the constants are of the form
 * {@code ATTR_[uppercase event attribute name]}.
 *
 * @author Haixing Hu
 * @see HtmlTag
 * @see HtmlAttribute
 * @see HtmlAttributeValue
 * @see <a href="http://www.w3schools.com/tags/default.asp">HTML Element Reference</a>
 */
public interface HtmlEvent {

  /**
   * Script to be run when a document load.
   */
  String ATTR_ONLOAD      = "onload";

  /**
   * Script to be run when a document unload.
   */
  String ATTR_ONUNLOAD    = "onunload";

  /**
   * Script to be run when an element loses focus.
   */
  String ATTR_ONBLUR      = "onblur";

  /**
   * Script to be run when an element change.
   */
  String ATTR_ONCHANGE    = "onchange";

  /**
   * Script to be run when an element gets focus.
   */
  String ATTR_ONFOCUS     = "onfocus";

  /**
   * Script to be run when a form is reset.
   */
  String ATTR_ONRESET     = "onreset";

  /**
   * Script to be run when an element is selected.
   */
  String ATTR_ONSELECT    = "onselect";

  /**
   * Script to be run when a form is submitted.
   */
  String ATTR_ONSUBMIT    = "onsubmit";

  /**
   * Script to be run when loading of an image is interrupted.
   */
  String ATTR_ONABORT     = "onabort";

  /**
   * Script to be run when a key is pressed.
   */
  String ATTR_ONKEYDOWN   = "onkeydown";

  /**
   * Script to be run when a key is pressed and released.
   */
  String ATTR_ONKEYPRESS  = "onkeypress";

  /**
   * Script to be run when a key is released.
   */
  String ATTR_ONKEYUP     = "onkeyup";

  /**
   * Script to be run on a mouse click.
   */
  String ATTR_ONCLICK     = "onclick";

  /**
   * Script to be run on a mouse double-click.
   */
  String ATTR_ONDBLCLICK  = "ondblclick";

  /**
   * Script to be run when mouse button is pressed.
   */
  String ATTR_ONMOUSEDOWN = "onmousedown";

  /**
   * Script to be run when mouse pointer moves.
   */
  String ATTR_ONMOUSEMOVE = "onmousemove";

  /**
   * Script to be run when mouse pointer moves out of an element.
   */
  String ATTR_ONMOUSEOUT  = "onmouseout";

  /**
   * Script to be run when mouse pointer moves over an element.
   */
  String ATTR_ONMOUSEOVER = "onmouseover";

  /**
   * Script to be run when mouse button is released.
   */
  String ATTR_ONMOUSEUP   = "onmouseup";
}
