////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import ltd.qubit.commons.text.xml.jaxb.EnumXmlAdapter;

/**
 * {@link Type}枚举类的JAXB适配器。
 *
 * @author 胡海星
 */
public final class TypeXmlAdapter extends EnumXmlAdapter<Type> {

  /**
   * 构造一个新的{@link TypeXmlAdapter}实例。
   */
  public TypeXmlAdapter() {
    super(Type.class);
  }
}