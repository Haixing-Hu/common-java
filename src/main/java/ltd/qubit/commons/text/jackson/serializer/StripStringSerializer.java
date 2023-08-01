////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import ltd.qubit.commons.text.Stripper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * The serializer of String class for XML serializations.
 *
 * @author Haixing Hu
 */
public class StripStringSerializer extends StdSerializer<String> {

  private static final long serialVersionUID = 6987148423553149459L;

  public static final StripStringSerializer INSTANCE = new StripStringSerializer();

  public StripStringSerializer() {
    super(String.class);
  }

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
