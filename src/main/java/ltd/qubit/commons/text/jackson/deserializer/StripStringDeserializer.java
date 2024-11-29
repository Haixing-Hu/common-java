////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.io.IOException;
import java.io.Serial;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import ltd.qubit.commons.text.Stripper;

/**
 * The deserializer of String class for XML serializations.
 *
 * @author Haixing Hu
 */
@Immutable
public class StripStringDeserializer extends StdDeserializer<String> {

  @Serial
  private static final long serialVersionUID = -6141183135378178754L;

  public static final StripStringDeserializer INSTANCE = new StripStringDeserializer();

  public StripStringDeserializer() {
    super(String.class);
  }

  @Override
  public String deserialize(final JsonParser parser,
      final DeserializationContext context) throws IOException {
    // strip the string value
    final String text = parser.getText();
    return new Stripper().strip(text);
  }
}
