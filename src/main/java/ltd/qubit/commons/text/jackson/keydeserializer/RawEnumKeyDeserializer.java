////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.io.Serial;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualKeyDeserializer;

import ltd.qubit.commons.lang.ClassUtils;
import ltd.qubit.commons.util.codec.RawEnumCodec;

/**
 * The JACKSON key deserializer of the enum objects.
 *
 * @author Haixing Hu
 */
@SuppressWarnings("rawtypes")
@Immutable
public class RawEnumKeyDeserializer extends DecoderKeyDeserializer<Enum>
    implements ContextualKeyDeserializer {

  @Serial
  private static final long serialVersionUID = -8135769705648525956L;

  public static final RawEnumKeyDeserializer INSTANCE = new RawEnumKeyDeserializer();

  public RawEnumKeyDeserializer() {
    super(Enum.class, new RawEnumCodec());
  }

  public RawEnumKeyDeserializer(final Class<? extends Enum> enumClass) {
    super(Enum.class, new RawEnumCodec(enumClass));
  }

  @SuppressWarnings("unchecked")
  @Override
  public KeyDeserializer createContextual(final DeserializationContext context,
      final BeanProperty property) throws JsonMappingException {
    final JavaType type = context.getContextualType();
    final Class<?> cls = type.getRawClass();
    if (!ClassUtils.isEnumType(cls)) {
      throw JsonMappingException.from(context.getParser(),
          "The property must be of a Enum type, but it is of " + cls.getName());
    }
    final RawEnumCodec codec = (RawEnumCodec) decoder;
    if (codec.getEnumClass().getName().equals(cls.getName())) {
      return this;
    } else {
      return new RawEnumKeyDeserializer((Class<? extends Enum>) cls);
    }
  }

}
