////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.io.IOException;
import java.io.Serial;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import ltd.qubit.commons.text.Stripper;

/**
 * 用于XML序列化的String类序列化器。
 *
 * @author 胡海星
 */
public class StripStringSerializer extends StdSerializer<String> {

  @Serial
  private static final long serialVersionUID = 6987148423553149459L;

  /**
   * StripStringSerializer的单例实例。
   */
  public static final StripStringSerializer INSTANCE = new StripStringSerializer();

  /**
   * 构造一个StripStringSerializer实例。
   */
  public StripStringSerializer() {
    super(String.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void serialize(final String value, final JsonGenerator generator,
      final SerializerProvider provider) throws IOException {
    if (value == null) {
      generator.writeNull();
    } else {
      // final ToXmlGenerator xmlGenerator = (ToXmlGenerator) generator;
      // if (StringUtils.isStrippable(value)) {
      //   // if the value contains leading or trailing non-graph character, keep
      //   // it with CDATA section
      //   xmlGenerator.setNextIsCData(true);
      //   xmlGenerator.writeString(value);
      // } else {
      //   xmlGenerator.writeString(value);
      // }
      final String text = new Stripper().strip(value);
      generator.writeString(text);
    }
  }
}