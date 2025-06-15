////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator.Feature;

/**
 * Jackson映射器定制化工具类。
 *
 * <p>此类提供了一系列静态方法来定制化Jackson {@link ObjectMapper}的行为，
 * 包括序列化和反序列化特性的配置、可见性设置、以及正则化配置等。</p>
 *
 * @author 胡海星
 */
public class CustomizeJacksonUtils {

  /**
   * 定制化ObjectMapper的特性配置。
   *
   * <p>此方法会配置ObjectMapper的序列化和反序列化特性，包括：
   * <ul>
   * <li>序列化特性：失败处理、异常包装、日期时间格式、枚举序列化等</li>
   * <li>反序列化特性：类型验证、异常处理、数据类型转换等</li>
   * <li>XML特性：如果是XmlMapper，会禁用XML声明输出</li>
   * <li>可见性设置：仅通过字段进行序列化，忽略getter/setter</li>
   * <li>包含策略：仅序列化非空字段</li>
   * </ul>
   * </p>
   *
   * @param mapper 要定制化的ObjectMapper实例
   */
  public static void customizeFeature(final ObjectMapper mapper) {
    // 设置序列化时的特性
    mapper.disable(SerializationFeature.CLOSE_CLOSEABLE);
    mapper.enable(SerializationFeature.EAGER_SERIALIZER_FETCH);
    mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    mapper.enable(SerializationFeature.FAIL_ON_SELF_REFERENCES);
    mapper.enable(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS);
    mapper.enable(SerializationFeature.FLUSH_AFTER_WRITE_VALUE);
    mapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);  // map entry按照key字典序排序
    mapper.disable(SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID);
    mapper.enable(SerializationFeature.WRAP_EXCEPTIONS);
    mapper.disable(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS);
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // 日期时间应该以字符串形式表示
    mapper.enable(SerializationFeature.WRITE_DATES_WITH_CONTEXT_TIME_ZONE);
    mapper.disable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID);
    mapper.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
    mapper.disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
    mapper.disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS);
    mapper.disable(SerializationFeature.WRITE_ENUMS_USING_INDEX);
    mapper.disable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
    mapper.disable(SerializationFeature.WRITE_ENUM_KEYS_USING_INDEX);
    mapper.disable(SerializationFeature.WRITE_SELF_REFERENCES_AS_NULL);
    mapper.disable(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
    mapper.disable(SerializationFeature.WRAP_ROOT_VALUE);
    // 设置反序列化时的特性
    mapper.disable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
    mapper.disable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    mapper.disable(DeserializationFeature.ACCEPT_FLOAT_AS_INT);
    mapper.disable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    mapper.enable(DeserializationFeature.EAGER_DESERIALIZER_FETCH);
    mapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
    mapper.enable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE);
    mapper.enable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES);
    mapper.disable(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES);
    mapper.enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
    mapper.enable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS);
    mapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
    mapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    mapper.enable(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS);
    mapper.disable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
    mapper.disable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
    mapper.disable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
    mapper.disable(DeserializationFeature.UNWRAP_ROOT_VALUE);
    mapper.disable(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS);
    mapper.disable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
    mapper.disable(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS);
    mapper.disable(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY);
    mapper.disable(DeserializationFeature.USE_LONG_FOR_INTS);
    mapper.disable(DeserializationFeature.WRAP_EXCEPTIONS);
    // 设置 ToXmlGenerator 相关特性
    if (mapper instanceof XmlMapper) {
      final XmlMapper xmlMapper = (XmlMapper) mapper;
      xmlMapper.disable(Feature.WRITE_XML_DECLARATION);  // 不输出XML声明
    }
    // 序列化时仅根据字段进行序列化
    mapper.setVisibility(new VisibilityChecker.Std(
        Visibility.NONE,  // getter
        Visibility.NONE,  // isGetter
        Visibility.NONE,  // setter
        Visibility.NONE,  // creator
        Visibility.ANY)   // field
    );
    // 序列化时只包含非空字段
    mapper.setSerializationInclusion(Include.NON_NULL);
  }

  /**
   * 获取ObjectMapper的正则化序列化配置。
   *
   * @param mapper 要获取配置的ObjectMapper
   * @return 正则化的序列化配置
   */
  public static SerializationConfig getNormalizedConfig(final ObjectMapper mapper) {
    return getNormalizedConfig(mapper.getSerializationConfig());
  }

  /**
   * 获取正则化的序列化配置。
   *
   * <p>正则化配置会：
   * <ul>
   * <li>启用属性按字典序排序</li>
   * <li>启用Map条目按键的字典序排序</li>
   * <li>禁用美化打印输出</li>
   * </ul>
   * 这确保了序列化结果的一致性和可重现性。</p>
   *
   * @param config 要正则化的序列化配置
   * @return 正则化后的序列化配置
   */
  public static SerializationConfig getNormalizedConfig(final SerializationConfig config) {
    // 注意：一但 XmlMapper 对象进行过序列化或反序列化，其 MapperFeature 就不能修改
    // （修改后也无效）。因此我们需要 clone 一个新的 XmlMapper
    return config.with(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)   // 属性按照字典序排序
        .with(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)          // map entry 按照 key 的字典序排序
        .without(SerializationFeature.INDENT_OUTPUT);                  // 阻止 pretty print
  }
}