////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

import javax.annotation.concurrent.Immutable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cn.njzhyl.commons.i18n.LocaleUtils;
import cn.njzhyl.commons.io.serialize.XmlSerializer;
import cn.njzhyl.commons.text.xml.DomUtils;
import cn.njzhyl.commons.text.xml.XmlException;
import cn.njzhyl.commons.text.xml.XmlSerializationException;

/**
 * The {@link XmlSerializer} for the {@link DatePattern} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class DatePatternXmlSerializer implements XmlSerializer {

  public static final String ROOT_NODE = "date-pattern";

  public static final String PATTERN_NODE = "pattern";

  public static final String FORMAT_NODE = "format";

  public static final String LOCALE_NODE = "locale";

  public static final DatePatternXmlSerializer INSTANCE = new DatePatternXmlSerializer();

  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

  @Override
  public DatePattern deserialize(final Element root) throws XmlException {
    DomUtils.checkNode(root, ROOT_NODE);
    final DatePattern result = new DatePattern();
    result.matcher = null;
    result.dateFormat = null;
    // parse pattern
    result.pattern = DomUtils
            .getReqStringChild(root, PATTERN_NODE, null, true, true);
    result.format = DomUtils
            .getReqStringChild(root, FORMAT_NODE, null, true, true);
    final String localeId = DomUtils
            .getOptStringChild(root, LOCALE_NODE, null, true, null);
    if (localeId == null) {
      result.locale = null;
    } else {
      result.locale = LocaleUtils.fromPosixLocale(localeId);
    }
    return result;
  }

  @Override
  public Element serialize(final Document doc, final Object obj) throws XmlException {
    final DatePattern dp;
    try {
      dp = (DatePattern) obj;
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
    final Element root = doc.createElement(ROOT_NODE);
    DomUtils.appendReqStringChild(doc, root, PATTERN_NODE, null, dp.pattern);
    DomUtils.appendReqStringChild(doc, root, FORMAT_NODE, null, dp.format);
    if (dp.locale != null) {
      final String localId = LocaleUtils.toPosixLocale(dp.locale);
      DomUtils.appendReqStringChild(doc, root, LOCALE_NODE, null, localId);
    }
    return root;
  }

}
