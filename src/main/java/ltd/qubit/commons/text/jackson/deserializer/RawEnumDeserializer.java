////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.io.Serial;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import ltd.qubit.commons.lang.ClassUtils;
import ltd.qubit.commons.util.codec.RawEnumCodec;

/**
 * 枚举对象的 JACKSON 反序列化器。
 *
 * @author 胡海星
 */
@SuppressWarnings("rawtypes")
@Immutable
public class RawEnumDeserializer extends DecoderDeserializer<Enum>
    implements ContextualDeserializer {

  @Serial
  private static final long serialVersionUID = 528653488213198744L;

  /**
   * 单例实例。
   */
  public static final RawEnumDeserializer INSTANCE = new RawEnumDeserializer();

  /**
   * 构造一个 {@link RawEnumDeserializer} 对象。
   */
  public RawEnumDeserializer() {
    // make RawEnumCodec support @JsonValue annotation
    super(Enum.class, new RawEnumCodec(true));
  }

  /**
   * 构造一个 {@link RawEnumDeserializer} 对象。
   *
   * @param enumClass
   *     指定的枚举类。
   */
  private RawEnumDeserializer(final Class<? extends Enum> enumClass) {
    // make RawEnumCodec support @JsonValue annotation
    super(Enum.class, new RawEnumCodec(enumClass, true));
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public JsonDeserializer<?> createContextual(final DeserializationContext context,
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
      return new RawEnumDeserializer((Class<? extends Enum>) cls);
    }
  }
}