////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import jakarta.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import static java.nio.charset.StandardCharsets.UTF_8;

public class JsonMapperUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(JsonMapperUtils.class);

  private JsonMapperUtils() {}

  /**
   * 从JSON字符串资源URL解析指定的对象。
   *
   * @param <T>
   *     待解析的对象的类型。
   * @param url
   *     指定的资源URL。
   * @param cls
   *     待解析的对象的类对象。
   * @return
   *     解析出的对象。
   * @throws IOException
   *     若发生任何解析错误或I/O错误。
   */
  public static <T> T parse(final URL url, final Class<T> cls)
      throws IOException {
    return parse(url, cls, new CustomizedJsonMapper());
  }

  /**
   * 从JSON字符串资源URL解析指定的对象。
   *
   * @param <T>
   *     待解析的对象的类型。
   * @param url
   *     指定的资源URL。
   * @param cls
   *     待解析的对象的类对象。
   * @param mapper
   *     用于进行JSON反序列化的mapper。
   * @return
   *     解析出的对象。
   * @throws IOException
   *     若发生任何解析错误或I/O错误。
   */
  public static <T> T parse(final URL url, final Class<T> cls,
      final JsonMapper mapper) throws IOException {
    return mapper.readValue(url, cls);
  }

  /**
   * 从JSON字符串输入流解析指定的对象。
   *
   * @param <T>
   *     待解析的对象的类型。
   * @param in
   *     指定的输入流.
   * @param cls
   *     待解析的对象的类对象。
   * @return
   *     解析出的对象。
   * @throws IOException
   *     若发生任何解析错误或I/O错误。
   */
  public static <T> T parse(final InputStream in, final Class<T> cls)
      throws IOException {
    return parse(in, cls, new CustomizedJsonMapper());
  }

  /**
   * 从JSON字符串输入流解析指定的对象。
   *
   * @param <T>
   *     待解析的对象的类型。
   * @param in
   *     指定的输入流.
   * @param cls
   *     待解析的对象的类对象。
   * @param mapper
   *     用于进行JSON反序列化的mapper。
   * @return
   *     解析出的对象。
   * @throws IOException
   *     若发生任何解析错误或I/O错误。
   */
  public static <T> T parse(final InputStream in, final Class<T> cls,
      final JsonMapper mapper) throws IOException {
    return mapper.readValue(in, cls);
  }

  /**
   * 从JSON字符串reader解析指定的对象。
   *
   * @param <T>
   *     待解析的对象的类型。
   * @param reader
   *     指定的reader.
   * @param cls
   *     待解析的对象的类对象。
   * @return
   *     解析出的对象。
   * @throws IOException
   *     若发生任何解析错误或I/O错误。
   */
  public static <T> T parse(final Reader reader, final Class<T> cls)
      throws IOException {
    return parse(reader, cls, new CustomizedJsonMapper());
  }

  /**
   * 从JSON字符串reader解析指定的对象。
   *
   * @param <T>
   *     待解析的对象的类型。
   * @param reader
   *     指定的reader.
   * @param cls
   *     待解析的对象的类对象。
   * @param mapper
   *     用于进行JSON反序列化的mapper。
   * @return
   *     解析出的对象。
   * @throws IOException
   *     若发生任何解析错误或I/O错误。
   */
  public static <T> T parse(final Reader reader, final Class<T> cls,
      final JsonMapper mapper) throws IOException {
    return mapper.readValue(reader, cls);
  }

  /**
   * 从JSON字符串解析指定的对象。
   *
   * @param <T>
   *      待解析的对象的类型。
   * @param json
   *     指定的JSON字符串。
   * @param cls
   *     待解析的对象的类对象。
   * @return
   *     解析出的对象。
   * @throws IOException
   *     若发生任何解析错误或I/O错误。
   */
  public static <T> T parse(final String json, final Class<T> cls)
      throws IOException {
    return parse(json, cls, new CustomizedJsonMapper());
  }

  /**
   * 从JSON字符串解析指定的对象。
   *
   * @param <T>
   *      待解析的对象的类型。
   * @param json
   *     指定的JSON字符串。
   * @param cls
   *     待解析的对象的类对象。
   * @param mapper
   *     用于进行JSON反序列化的mapper。
   * @return
   *     解析出的对象。
   * @throws IOException
   *     若发生任何解析错误或I/O错误。
   */
  public static <T> T parse(final String json, final Class<T> cls,
      final JsonMapper mapper) throws IOException {
    return mapper.readValue(json, cls);
  }

  /**
   * 从JSON字符串解析指定的对象。
   *
   * @param <T>
   *      待解析的对象的类型。
   * @param json
   *     指定的JSON字符串。
   * @param cls
   *     待解析的对象的类对象。
   * @return
   *     解析出的对象；若出错则返回{@code null}。
   */
  @Nullable
  public static <T> T parseNoThrow(final String json, final Class<T> cls) {
    return parseNoThrow(json, cls, new CustomizedJsonMapper());
  }

  /**
   * 从JSON字符串解析指定的对象。
   *
   * @param <T>
   *      待解析的对象的类型。
   * @param json
   *     指定的JSON字符串。
   * @param cls
   *     待解析的对象的类对象。
   * @param mapper
   *     用于进行JSON反序列化的mapper。
   * @return
   *     解析出的对象；若出错则返回{@code null}。
   */
  @Nullable
  public static <T> T parseNoThrow(final String json, final Class<T> cls,
      final JsonMapper mapper) {
    try {
      return mapper.readValue(json, cls);
    } catch (final JsonProcessingException e) {
      LOGGER.error("Failed to parse the object {} from the JSON: {}",
              cls.getName(), json, e);
      return null;
    }
  }

  public static <T> List<T> parseList(final URL url, final Class<T> cls)
      throws IOException {
    return parseList(url, cls, new CustomizedJsonMapper());
  }

  public static <T> List<T> parseList(final URL url, final Class<T> cls,
      final JsonMapper mapper) throws IOException {
    try (final InputStream in = url.openStream()) {
      return parseList(in, cls, mapper);
    }
  }

  public static <T> List<T> parseList(final InputStream in, final Class<T> cls)
      throws IOException {
    return parseList(in, cls, new CustomizedJsonMapper());
  }

  public static <T> List<T> parseList(final InputStream in, final Class<T> cls,
      final JsonMapper mapper) throws IOException {
    final InputStreamReader reader = new InputStreamReader(in, UTF_8);
    return parseList(reader, cls, mapper);
  }

  public static <T> List<T> parseList(final Reader reader, final Class<T> cls)
      throws IOException {
    return parseList(reader, cls, new CustomizedJsonMapper());
  }

  public static <T> List<T> parseList(final Reader reader, final Class<T> cls,
      final JsonMapper mapper) throws IOException {
    final TypeFactory factory = mapper.getTypeFactory();
    final JavaType listType = factory.constructCollectionType(List.class, cls);
    return mapper.readValue(reader, listType);
  }

  public static <T> List<T> parseList(final String json, final Class<T> cls)
      throws JsonProcessingException {
    return parseList(json, cls, new CustomizedJsonMapper());
  }

  public static <T> List<T> parseList(final String json, final Class<T> cls,
      final JsonMapper mapper) throws JsonProcessingException {
    final TypeFactory factory = mapper.getTypeFactory();
    final JavaType listType = factory.constructCollectionType(List.class, cls);
    return mapper.readValue(json, listType);
  }

  public static <T> List<T> parseListNoThrow(final Reader reader, final Class<T> cls) {
    return parseListNoThrow(reader, cls, new CustomizedJsonMapper());
  }

  public static <T> List<T> parseListNoThrow(final Reader reader, final Class<T> cls,
      final JsonMapper mapper) {
    try {
      return parseList(reader, cls, mapper);
    } catch (final IOException e) {
      LOGGER.error("Failed to parse the object {} from the JSON: {}",
          cls.getName(), reader, e);
      return null;
    }
  }

  public static <T> List<T> parseListNoThrow(final String json, final Class<T> cls) {
    return parseListNoThrow(json, cls, new CustomizedJsonMapper());
  }

  public static <T> List<T> parseListNoThrow(final String json, final Class<T> cls,
      final JsonMapper mapper) {
    try {
      return parseList(json, cls, mapper);
    } catch (final JsonProcessingException e) {
      LOGGER.error("Failed to parse the object {} from the JSON: {}",
          cls.getName(), json, e);
      return null;
    }
  }

  /**
   * 将指定的对象格式化为JSON字符串。
   *
   * @param <T>
   *     待序列化的对象的类型。
   * @param obj
   *     待序列化的对象。
   * @return
   *     该对象的JSON字符形式；若出错则返回{@code null}。
   */
  @Nullable
  public static <T> String formatNoThrow(final T obj) {
    return formatNoThrow(obj, new CustomizedJsonMapper());
  }

  /**
   * 将指定的对象格式化为JSON字符串。
   *
   * @param <T>
   *     待序列化的对象的类型。
   * @param obj
   *     待序列化的对象。
   * @param mapper
   *     用于进行JSON序列化的mapper。
   * @return
   *     该对象的JSON字符形式；若出错则返回{@code null}。
   */
  @Nullable
  public static <T> String formatNoThrow(final T obj, final JsonMapper mapper) {
    try {
      return mapper.writeValueAsString(obj);
    } catch (final JsonProcessingException e) {
      LOGGER.error("Failed to format the object {} to JSON: {}",
              obj.getClass().getName(), obj, e);
      return null;
    }
  }

  // stop checkstyle: LineLength
  /**
   * 将指定的对象格式化为正则化的JSON字符串。
   * <p>
   * 所谓正则化JSON字符串形式，是指序列化后的JSON对象的属性都按照属性名的字典序从小到大
   * 排序；若属性值是{@link Map}类型，则按照其主键的字典序从小到大排序。
   * 例如对于下述{@code Person}对象：
   * <p>
   * <pre><code>
   * class Person {
   *   private Long id;
   *   private String name;
   *   private String code;
   *   private Organization company;
   *   private Map&lt;String, String&gt; payload;
   *   private Instant createTime;
   * }
   *
   * class Organization {
   *   private Long id;
   *   private String name;
   *   private String code;
   *   private String address;
   * }
   * </code></pre>
   * <p>
   * 正常序列化后得到的JSON字符串应该是：
   * <p>
   * <pre><code>
   * {
   *   "id": 12345,
   *   "name": "张三",
   *   "code": "abc",
   *   "company": {
   *     "id": 547362,
   *     "name": "XX科技有限公司",
   *     "code": "xx-tech",
   *     "address": "江苏省南京市秦淮区XX路32号",
   *   },
   *   "payload": {
   *     "job-title": "engineer",
   *     "age": "32"
   *   },
   *   "create_time": "2022-09-10T16:23:42Z"
   * }
   * </code></pre>
   * 但是对其所有属性或子属性按照属性路径名字典序排序后应该是：
   * <p><pre><code>
   * {
   *   "id": 12345,
   *   "code": "abc",
   *   "company": {
   *     "address": "江苏省南京市秦淮区XX路32号",
   *     "code": "xx-tech",
   *     "id": 547362,
   *     "name": "XX科技有限公司"
   *   },
   *   "create_time": "2022-09-10T16:23:42Z",
   *   "name": "张三",
   *   "payload": {
   *     "age": "32",
   *     "job-title": "engineer"
   *   }
   * }
   * </code></pre>
   * 然后压缩该字符串中所有空白字符，就得到最终的正则化JSON字符串：
   * <p>
   * <pre><code>{"id":12345,"code":"abc","company":{"address":"江苏省南京市秦淮区XX路32号","code":"xx-tech","id":547362,"name":"XX科技有限公司"},"create_time":"2022-09-10T16:23:42Z","name":"张三","payload":{"age":"32","job-title":"engineer"}}</code></pre>
   * </p>
   *
   * FIXME: 对于集合属性值，如何确保排序？
   *
   * @param <T>
   *     待序列化的对象的类型。
   * @param obj
   *     待序列化的对象。
   * @return
   *     该对象的正则化JSON字符形式。
   * @throws JsonProcessingException
   *     若发生JSON序列化错误。
   */
  @NotNull
  public static <T> String formatNormalized(final T obj)
      throws JsonProcessingException {
    final JsonMapper mapper = CustomizedJsonMapper.createNormalized();
    return mapper.writeValueAsString(obj);
  }

  /**
   * 将指定的对象格式化为正则化的JSON字符串。
   * <p>
   * 所谓正则化JSON字符串形式，是指序列化后的JSON对象的属性都按照属性名的字典序从小到大
   * 排序；若属性值是{@link Map}类型，则按照其主键的字典序从小到大排序。
   * 例如对于下述{@code Person}对象：
   * <p>
   * <pre><code>
   * class Person {
   *   private Long id;
   *   private String name;
   *   private String code;
   *   private Organization company;
   *   private Map&lt;String, String&gt; payload;
   *   private Instant createTime;
   * }
   *
   * class Organization {
   *   private Long id;
   *   private String name;
   *   private String code;
   *   private String address;
   * }
   * </code></pre>
   * <p>
   * 正常序列化后得到的JSON字符串应该是：
   * <p>
   * <pre><code>
   * {
   *   "id": 12345,
   *   "name": "张三",
   *   "code": "abc",
   *   "company": {
   *     "id": 547362,
   *     "name": "XX科技有限公司",
   *     "code": "xx-tech",
   *     "address": "江苏省南京市秦淮区XX路32号",
   *   },
   *   "payload": {
   *     "job-title": "engineer",
   *     "age": "32"
   *   },
   *   "create_time": "2022-09-10T16:23:42Z"
   * }
   * </code></pre>
   * 但是对其所有属性或子属性按照属性路径名字典序排序后应该是：
   * <p><pre><code>
   * {
   *   "id": 12345,
   *   "code": "abc",
   *   "company": {
   *     "address": "江苏省南京市秦淮区XX路32号",
   *     "code": "xx-tech",
   *     "id": 547362,
   *     "name": "XX科技有限公司"
   *   },
   *   "create_time": "2022-09-10T16:23:42Z",
   *   "name": "张三",
   *   "payload": {
   *     "age": "32",
   *     "job-title": "engineer"
   *   }
   * }
   * </code></pre>
   * 然后压缩该字符串中所有空白字符，就得到最终的正则化JSON字符串：
   * <p>
   * <pre><code>{"id":12345,"code":"abc","company":{"address":"江苏省南京市秦淮区XX路32号","code":"xx-tech","id":547362,"name":"XX科技有限公司"},"create_time":"2022-09-10T16:23:42Z","name":"张三","payload":{"age":"32","job-title":"engineer"}}</code></pre>
   * </p>
   *
   * FIXME: 对于集合属性值，如何确保排序？
   *
   * @param <T>
   *     待序列化的对象的类型。
   * @param obj
   *     待序列化的对象。
   * @param mapper
   *     用于进行JSON序列化的mapper。
   * @return
   *     该对象的正则化JSON字符形式。
   * @throws JsonProcessingException
   *     若发生JSON序列化错误。
   */
  @NotNull
  public static <T> String formatNormalized(final T obj, final JsonMapper mapper)
      throws JsonProcessingException {
    final SerializationConfig newConfig = mapper.getSerializationConfig()
        .with(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)   // 属性按照字典序排序
        .with(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS) // map entry 按照 key 的字典序排序
        .without(SerializationFeature.INDENT_OUTPUT);         // 阻止 pretty print
    // 注意：一但 JsonMapper 对象进行过序列化或反序列化，其 MapperFeature 就不能修改
    // （修改后也无效）。因此我们需要 clone 一个新的 JsonMapper
    final JsonMapper newMapper = mapper.copy();
    newMapper.setConfig(newConfig);
    final String result = newMapper.writeValueAsString(obj);
    return result;
  }

  /**
   * 将指定的对象格式化为正则化的JSON字符串。
   * <p>
   * 所谓正则化JSON字符串形式，是序列化后的JSON对象的属性都按照属性名的字典序从小到大排序。
   * 例如对于下述{@code Person}对象：
   * <p>
   * <pre><code>
   * class Person {
   *   private Long id;
   *   private String name;
   *   private String code;
   *   private Organization company;
   *   private Instant createTime;
   * }
   *
   * class Organization {
   *   private Long id;
   *   private String name;
   *   private String code;
   *   private String address;
   * }
   * </code></pre>
   * <p>
   * 正常序列化后得到的JSON字符串应该是：
   * <p>
   * <pre><code>
   * {
   *   "id": 12345,
   *   "name": "张三",
   *   "code": "abc",
   *   "company": {
   *     "id": 547362,
   *     "name": "XX科技有限公司",
   *     "code": "xx-tech",
   *     "address": "江苏省南京市秦淮区XX路32号",
   *   },
   *   "create_time": "2022-09-10T16:23:42Z"
   * }
   * </code></pre>
   * 但是对其所有属性或子属性按照属性路径名字典序排序后应该是：
   * <p><pre><code>
   * {
   *   "id": 12345,
   *   "code": "abc",
   *   "company": {
   *     "address": "江苏省南京市秦淮区XX路32号",
   *     "code": "xx-tech",
   *     "id": 547362,
   *     "name": "XX科技有限公司"
   *   },
   *   "create_time": "2022-09-10T16:23:42Z",
   *   "name": "张三"
   * }
   * </code></pre>
   * 然后压缩该字符串中所有空白字符，就得到最终的正则化JSON字符串：
   * <p>
   * <pre><code>{"id":12345,"code":"abc","company":{"address":"江苏省南京市秦淮区XX路32号","code":"xx-tech","id":547362,"name":"XX科技有限公司"},"create_time":"2022-09-10T16:23:42Z","name":"张三"}</code></pre>
   * </p>
   *
   * @param <T>
   *     待序列化的对象的类型。
   * @param obj
   *     待序列化的对象。
   * @return
   *     该对象的正则化JSON字符形式；若出错则返回{@code null}。
   */
  @Nullable
  public static <T> String formatNormalizedNoThrow(final T obj) {
    final JsonMapper mapper = CustomizedJsonMapper.createNormalized();
    try {
      return formatNormalized(obj, mapper);
    } catch (final JsonProcessingException e) {
      LOGGER.error("Failed to format the object {} to normalized JSON: {}",
          obj.getClass().getName(), obj, e);
      return null;
    }
  }

  /**
   * 将指定的对象格式化为正则化的JSON字符串。
   * <p>
   * 所谓正则化JSON字符串形式，是序列化后的JSON对象的属性都按照属性名的字典序从小到大排序。
   * 例如对于下述{@code Person}对象：
   * <p>
   * <pre><code>
   * class Person {
   *   private Long id;
   *   private String name;
   *   private String code;
   *   private Organization company;
   *   private Instant createTime;
   * }
   *
   * class Organization {
   *   private Long id;
   *   private String name;
   *   private String code;
   *   private String address;
   * }
   * </code></pre>
   * <p>
   * 正常序列化后得到的JSON字符串应该是：
   * <p>
   * <pre><code>
   * {
   *   "id": 12345,
   *   "name": "张三",
   *   "code": "abc",
   *   "company": {
   *     "id": 547362,
   *     "name": "XX科技有限公司",
   *     "code": "xx-tech",
   *     "address": "江苏省南京市秦淮区XX路32号",
   *   },
   *   "create_time": "2022-09-10T16:23:42Z"
   * }
   * </code></pre>
   * 但是对其所有属性或子属性按照属性路径名字典序排序后应该是：
   * <p><pre><code>
   * {
   *   "id": 12345,
   *   "code": "abc",
   *   "company": {
   *     "address": "江苏省南京市秦淮区XX路32号",
   *     "code": "xx-tech",
   *     "id": 547362,
   *     "name": "XX科技有限公司"
   *   },
   *   "create_time": "2022-09-10T16:23:42Z",
   *   "name": "张三"
   * }
   * </code></pre>
   * 然后压缩该字符串中所有空白字符，就得到最终的正则化JSON字符串：
   * <p>
   * <pre><code>{"id":12345,"code":"abc","company":{"address":"江苏省南京市秦淮区XX路32号","code":"xx-tech","id":547362,"name":"XX科技有限公司"},"create_time":"2022-09-10T16:23:42Z","name":"张三"}</code></pre>
   * </p>
   *
   * @param <T>
   *     待序列化的对象的类型。
   * @param obj
   *     待序列化的对象。
   * @param mapper
   *     用于进行JSON序列化的mapper。
   * @return
   *     该对象的正则化JSON字符形式；若出错则返回{@code null}。
   */
  @Nullable
  public static <T> String formatNormalizedNoThrow(final T obj,
      final JsonMapper mapper) {
    try {
      return formatNormalized(obj, mapper);
    } catch (final JsonProcessingException e) {
      LOGGER.error("Failed to format the object {} to normalized JSON: {}",
          obj.getClass().getName(), obj, e);
      return null;
    }
  }
  // resume checkstyle: LineLength
}
