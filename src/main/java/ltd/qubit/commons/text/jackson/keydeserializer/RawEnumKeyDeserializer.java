////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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
 * 枚举对象的 JACKSON 键反序列化器。
 *
 * @author 胡海星
 */
@SuppressWarnings("rawtypes")
@Immutable
public class RawEnumKeyDeserializer extends DecoderKeyDeserializer<Enum>
    implements ContextualKeyDeserializer {

  @Serial
  private static final long serialVersionUID = -8135769705648525956L;

  /**
   * 默认实例。
   */
  public static final RawEnumKeyDeserializer INSTANCE = new RawEnumKeyDeserializer();

  /**
   * 构造一个 {@link RawEnumKeyDeserializer}。
   */
  public RawEnumKeyDeserializer() {
    // make RawEnumCodec support @JsonValue annotation
    super(Enum.class, new RawEnumCodec(true));
  }

  /**
   * 构造一个指定枚举类的 {@link RawEnumKeyDeserializer}。
   *
   * @param enumClass
   *     枚举类。
   */
  public RawEnumKeyDeserializer(final Class<? extends Enum> enumClass) {
    // make RawEnumCodec support @JsonValue annotation
    super(Enum.class, new RawEnumCodec(enumClass, true));
  }

  /**
   * {@inheritDoc}
   */
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