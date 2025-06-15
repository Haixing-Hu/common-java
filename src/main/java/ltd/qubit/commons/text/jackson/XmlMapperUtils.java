////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import ltd.qubit.commons.text.Replacer;

import static java.nio.charset.StandardCharsets.UTF_8;

import static ltd.qubit.commons.text.jackson.CustomizeJacksonUtils.getNormalizedConfig;
import static ltd.qubit.commons.text.jackson.JacksonUtils.getRootName;
import static ltd.qubit.commons.text.jackson.JacksonUtils.getRootWrapperName;

/**
 * XML映射器工具类，提供XML序列化和反序列化的便捷方法。
 *
 * @author 胡海星
 */
public class XmlMapperUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(XmlMapperUtils.class);

  private XmlMapperUtils() {}

  /**
   * 从XML字符串资源URL解析指定的对象。
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
    return parse(url, cls, new CustomizedXmlMapper());
  }

  /**
   * 从XML字符串资源URL解析指定的对象。
   *
   * @param <T>
   *     待解析的对象的类型。
   * @param url
   *     指定的资源URL。
   * @param cls
   *     待解析的对象的类对象。
   * @param mapper
   *     用于进行XML反序列化的mapper。
   * @return
   *     解析出的对象。
   * @throws IOException
   *     若发生任何解析错误或I/O错误。
   */
  public static <T> T parse(final URL url, final Class<T> cls,
      final XmlMapper mapper) throws IOException {
    return mapper.readValue(url, cls);
  }

  /**
   * 从XML字符串输入流解析指定的对象。
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
    return parse(in, cls, new CustomizedXmlMapper());
  }

  /**
   * 从XML字符串输入流解析指定的对象。
   *
   * @param <T>
   *     待解析的对象的类型。
   * @param in
   *     指定的输入流.
   * @param cls
   *     待解析的对象的类对象。
   * @param mapper
   *     用于进行XML反序列化的mapper。
   * @return
   *     解析出的对象。
   * @throws IOException
   *     若发生任何解析错误或I/O错误。
   */
  public static <T> T parse(final InputStream in, final Class<T> cls,
      final XmlMapper mapper) throws IOException {
    return mapper.readValue(in, cls);
  }

  /**
   * 从XML字符串reader解析指定的对象。
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
    return parse(reader, cls, new CustomizedXmlMapper());
  }

  /**
   * 从XML字符串reader解析指定的对象。
   *
   * @param <T>
   *     待解析的对象的类型。
   * @param reader
   *     指定的reader.
   * @param cls
   *     待解析的对象的类对象。
   * @param mapper
   *     用于进行XML反序列化的mapper。
   * @return
   *     解析出的对象。
   * @throws IOException
   *     若发生任何解析错误或I/O错误。
   */
  public static <T> T parse(final Reader reader, final Class<T> cls,
      final XmlMapper mapper) throws IOException {
    return mapper.readValue(reader, cls);
  }

  /**
   * 从XML字符串解析指定的对象。
   *
   * @param <T>
   *      待解析的对象的类型。
   * @param xml
   *     指定的XML字符串。
   * @param cls
   *     待解析的对象的类对象。
   * @return
   *     解析出的对象。
   * @throws IOException
   *     若发生任何解析错误或I/O错误。
   */
  public static <T> T parse(final String xml, final Class<T> cls)
      throws IOException {
    return parse(xml, cls, new CustomizedXmlMapper());
  }

  /**
   * 从XML字符串解析指定的对象。
   *
   * @param <T>
   *      待解析的对象的类型。
   * @param xml
   *     指定的XML字符串。
   * @param cls
   *     待解析的对象的类对象。
   * @param mapper
   *     用于进行XML反序列化的mapper。
   * @return
   *     解析出的对象。
   * @throws IOException
   *     若发生任何解析错误或I/O错误。
   */
  public static <T> T parse(final String xml, final Class<T> cls,
      final XmlMapper mapper) throws IOException {
    return mapper.readValue(xml, cls);
  }

  /**
   * 从XML字符串解析指定的对象。
   *
   * @param <T>
   *      待解析的对象的类型。
   * @param xml
   *     指定的XML字符串。
   * @param cls
   *     待解析的对象的类对象。
   * @return
   *     解析出的对象；若出错则返回{@code null}。
   */
  @Nullable
  public static <T> T parseNoThrow(final String xml, final Class<T> cls) {
    return parseNoThrow(xml, cls, new CustomizedXmlMapper());
  }

  /**
   * 从XML字符串解析指定的对象。
   *
   * @param <T>
   *      待解析的对象的类型。
   * @param xml
   *     指定的XML字符串。
   * @param cls
   *     待解析的对象的类对象。
   * @param mapper
   *     用于进行XML反序列化的mapper。
   * @return
   *     解析出的对象；若出错则返回{@code null}。
   */
  @Nullable
  public static <T> T parseNoThrow(final String xml, final Class<T> cls,
      final XmlMapper mapper) {
    try {
      return mapper.readValue(xml, cls);
    } catch (final JsonProcessingException e) {
      LOGGER.error("Failed to parse the object {} from the XML: {}",
          cls.getName(), xml, e);
      return null;
    }
  }

  public static <T> T parseNoThrow(final String xml, final TypeReference<T> ref) {
    return parseNoThrow(xml, ref, new CustomizedXmlMapper());
  }

  public static <T> T parseNoThrow(final String xml, final TypeReference<T> ref,
      final XmlMapper mapper) {
    try {
      return mapper.readValue(xml, ref);
    } catch (final JsonProcessingException e) {
      LOGGER.error("Failed to parse the object {} from the XML: {}",
          ref.getType().getTypeName(), xml, e);
      return null;
    }
  }

  public static <T> List<T> parseList(final URL url, final Class<T> cls)
      throws IOException {
    return parseList(url, cls, new CustomizedXmlMapper());
  }

  public static <T> List<T> parseList(final URL url, final Class<T> cls,
      final XmlMapper mapper) throws IOException {
    try (final InputStream in = url.openStream()) {
      return parseList(in, cls, mapper);
    }
  }

  public static <T> List<T> parseList(final InputStream in, final Class<T> cls)
      throws IOException {
    return parseList(in, cls, new CustomizedXmlMapper());
  }

  public static <T> List<T> parseList(final InputStream in, final Class<T> cls,
      final XmlMapper mapper) throws IOException {
    final InputStreamReader reader = new InputStreamReader(in, UTF_8);
    return parseList(reader, cls, mapper);
  }

  public static <T> List<T> parseList(final Reader reader, final Class<T> cls)
      throws IOException {
    return parseList(reader, cls, new CustomizedXmlMapper());
  }

  public static <T> List<T> parseList(final Reader reader, final Class<T> cls,
      final XmlMapper mapper) throws IOException {
    final TypeFactory factory = mapper.getTypeFactory();
    final JavaType listType = factory.constructCollectionType(List.class, cls);
    return mapper.readValue(reader, listType);
  }

  public static <T> List<T> parseList(final String xml, final Class<T> cls)
      throws JsonProcessingException {
    return parseList(xml, cls, new CustomizedXmlMapper());
  }

  public static <T> List<T> parseList(final String xml, final Class<T> cls,
      final XmlMapper mapper) throws JsonProcessingException {
    final TypeFactory factory = mapper.getTypeFactory();
    final JavaType listType = factory.constructCollectionType(List.class, cls);
    return mapper.readValue(xml, listType);
  }

  public static <T> List<T> parseListNoThrow(final Reader reader, final Class<T> cls) {
    return parseListNoThrow(reader, cls, new CustomizedXmlMapper());
  }

  public static <T> List<T> parseListNoThrow(final Reader reader,
      final Class<T> cls, final XmlMapper mapper) {
    try {
      return parseList(reader, cls, mapper);
    } catch (final IOException e) {
      LOGGER.error("Failed to parse the object {} from the XML: {}",
          cls.getName(), reader, e);
      return null;
    }
  }

  public static <T> List<T> parseListNoThrow(final String xml, final Class<T> cls) {
    return parseListNoThrow(xml, cls, new CustomizedXmlMapper());
  }

  public static <T> List<T> parseListNoThrow(final String xml, final Class<T> cls,
      final XmlMapper mapper) {
    try {
      return parseList(xml, cls, mapper);
    } catch (final JsonProcessingException e) {
      LOGGER.error("Failed to parse the object {} from the XML: {}",
          cls.getName(), xml, e);
      return null;
    }
  }

  private static final String LIST_WRAPPER_PREFIX = "xml-mapper-utils__list-wrapper__";

  private static final String LIST_WRAPPER_ROOT = LIST_WRAPPER_PREFIX + "root";

  private static final String LIST_WRAPPER_ITEM = LIST_WRAPPER_PREFIX + "item";

  private static final String LIST_WRAPPER_ROOT_TAG_OPEN = "<" + LIST_WRAPPER_ROOT + ">";

  private static final String LIST_WRAPPER_ROOT_TAG_CLOSE = "</" + LIST_WRAPPER_ROOT + ">";

  private static final String LIST_WRAPPER_ITEM_TAG_OPEN = "<" + LIST_WRAPPER_ITEM + ">";

  private static final String LIST_WRAPPER_ITEM_TAG_CLOSE = "</" + LIST_WRAPPER_ITEM + ">";


  @XmlRootElement(name = LIST_WRAPPER_ROOT)
  private static class ListWrapper<T> {

    @XmlNoElementWrapper
    @XmlElement(name = LIST_WRAPPER_ITEM)
    private final List<T> list;

    public ListWrapper(final List<T> list) {
      this.list = list;
    }

    public final List<T> getList() {
      return list;
    }
  }

  public static <T> String format(final T obj) throws JsonProcessingException {
    return format(obj, new CustomizedXmlMapper());
  }

  public static <T> String format(final T obj, final XmlMapper mapper)
      throws JsonProcessingException {
    return mapper.writeValueAsString(obj);
  }

  public static <T> String formatList(final List<T> list, final Class<T> cls)
      throws JsonProcessingException {
    return formatList(list, cls, new CustomizedXmlMapper());
  }

  public static <T> String formatList(final List<T> list, final Class<T> cls,
      final XmlMapper mapper) throws JsonProcessingException {
    final ListWrapper<T> wrapper = new ListWrapper<>(list);
    final String xml = mapper.writeValueAsString(wrapper);
    return fixListWrapperTags(mapper, cls, xml);
  }

  private static <T> String fixListWrapperTags(final XmlMapper mapper,
      final Class<T> cls, final String xml) {
    final PropertyName wrapperName = getRootWrapperName(mapper, cls);
    final PropertyName itemName = getRootName(mapper, cls);
    final String wrapperTagOpen = "<" + wrapperName.getSimpleName() + ">";
    final String wrapperTagClose = "</" + wrapperName.getSimpleName() + ">";
    final String itemTagOpen = "<" + itemName.getSimpleName() + ">";
    final String itemTagClose = "</" + itemName.getSimpleName() + ">";
    final Replacer replacer = new Replacer();
    String result = replacer.searchForSubstring(LIST_WRAPPER_ROOT_TAG_OPEN)
        .replaceWithString(wrapperTagOpen)
        .applyTo(xml);
    result = replacer.searchForSubstring(LIST_WRAPPER_ROOT_TAG_CLOSE)
        .replaceWithString(wrapperTagClose)
        .applyTo(result);
    result = replacer.searchForSubstring(LIST_WRAPPER_ITEM_TAG_OPEN)
        .replaceWithString(itemTagOpen)
        .applyTo(result);
    result = replacer.searchForSubstring(LIST_WRAPPER_ITEM_TAG_CLOSE)
        .replaceWithString(itemTagClose)
        .applyTo(result);
    return result;
  }

  /**
   * 将指定的对象格式化为XML字符串。
   *
   * @param <T>
   *     待序列化的对象的类型。
   * @param obj
   *     待序列化的对象。
   * @return
   *     该对象的XML字符形式；若出错则返回{@code null}。
   */
  @Nullable
  public static <T> String formatNoThrow(final T obj) {
    return formatNoThrow(obj, new CustomizedXmlMapper());
  }

  /**
   * 将指定的对象格式化为XML字符串。
   *
   * @param <T>
   *     待序列化的对象的类型。
   * @param obj
   *     待序列化的对象。
   * @param mapper
   *     用于进行XML序列化的mapper。
   * @return
   *     该对象的XML字符形式；若出错则返回{@code null}。
   */
  @Nullable
  public static <T> String formatNoThrow(final T obj, final XmlMapper mapper) {
    try {
      return mapper.writeValueAsString(obj);
    } catch (final JsonProcessingException e) {
      LOGGER.error("Failed to format the object {} to XML: {}",
          obj.getClass().getName(), obj, e);
      return null;
    }
  }

  // stop checkstyle: LineLength
  /**
   * 将指定的对象格式化为正则化的XML字符串。
   * <p>
   * 所谓正则化XML字符串形式，是指序列化后的XML对象的属性都按照属性名的字典序从小到大
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
   * 正常序列化后得到的XML字符串应该是：
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
   * 然后压缩该字符串中所有空白字符，就得到最终的正则化XML字符串：
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
   *     该对象的正则化XML字符形式。
   * @throws JsonProcessingException
   *     若发生XML序列化错误。
   */
  @NotNull
  public static <T> String formatNormalized(final T obj)
      throws JsonProcessingException {
    final XmlMapper mapper = CustomizedXmlMapper.createNormalized();
    return mapper.writeValueAsString(obj);
  }

  /**
   * 将指定的对象格式化为正则化的XML字符串。
   * <p>
   * 所谓正则化XML字符串形式，是指序列化后的XML对象的属性都按照属性名的字典序从小到大
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
   * 正常序列化后得到的XML字符串应该是：
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
   * 然后压缩该字符串中所有空白字符，就得到最终的正则化XML字符串：
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
   *     用于进行XML序列化的mapper。
   * @return
   *     该对象的正则化XML字符形式。
   * @throws JsonProcessingException
   *     若发生XML序列化错误。
   */
  @NotNull
  public static <T> String formatNormalized(final T obj, final XmlMapper mapper)
      throws JsonProcessingException {
    final SerializationConfig newConfig = getNormalizedConfig(mapper);
    // 注意：一但 XmlMapper 对象进行过序列化或反序列化，其 MapperFeature 就不能修改
    // （修改后也无效）。因此我们需要 clone 一个新的 XmlMapper
    final XmlMapper newMapper = mapper.copy();
    newMapper.setConfig(newConfig);
    final String result = newMapper.writeValueAsString(obj);
    return result;
  }

  /**
   * 将指定的对象格式化为正则化的XML字符串。
   * <p>
   * 所谓正则化XML字符串形式，是序列化后的XML对象的属性都按照属性名的字典序从小到大排序。
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
   * 正常序列化后得到的XML字符串应该是：
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
   * 然后压缩该字符串中所有空白字符，就得到最终的正则化XML字符串：
   * <p>
   * <pre><code>{"id":12345,"code":"abc","company":{"address":"江苏省南京市秦淮区XX路32号","code":"xx-tech","id":547362,"name":"XX科技有限公司"},"create_time":"2022-09-10T16:23:42Z","name":"张三"}</code></pre>
   * </p>
   *
   * @param <T>
   *     待序列化的对象的类型。
   * @param obj
   *     待序列化的对象。
   * @return
   *     该对象的正则化XML字符形式；若出错则返回{@code null}。
   */
  @Nullable
  public static <T> String formatNormalizedNoThrow(final T obj) {
    final XmlMapper mapper = CustomizedXmlMapper.createNormalized();
    try {
      return formatNormalized(obj, mapper);
    } catch (final JsonProcessingException e) {
      LOGGER.error("Failed to format the object {} to normalized XML: {}",
          obj.getClass().getName(), obj, e);
      return null;
    }
  }

  /**
   * 将指定的对象格式化为正则化的XML字符串。
   * <p>
   * 所谓正则化XML字符串形式，是序列化后的XML对象的属性都按照属性名的字典序从小到大排序。
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
   * 正常序列化后得到的XML字符串应该是：
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
   * 然后压缩该字符串中所有空白字符，就得到最终的正则化XML字符串：
   * <p>
   * <pre><code>{"id":12345,"code":"abc","company":{"address":"江苏省南京市秦淮区XX路32号","code":"xx-tech","id":547362,"name":"XX科技有限公司"},"create_time":"2022-09-10T16:23:42Z","name":"张三"}</code></pre>
   * </p>
   *
   * @param <T>
   *     待序列化的对象的类型。
   * @param obj
   *     待序列化的对象。
   * @param mapper
   *     用于进行XML序列化的mapper。
   * @return
   *     该对象的正则化XML字符形式；若出错则返回{@code null}。
   */
  @Nullable
  public static <T> String formatNormalizedNoThrow(final T obj,
      final XmlMapper mapper) {
    try {
      return formatNormalized(obj, mapper);
    } catch (final JsonProcessingException e) {
      LOGGER.error("Failed to format the object {} to normalized XML: {}",
          obj.getClass().getName(), obj, e);
      return null;
    }
  }

  public static Writer outputXmlDeclaration(final Writer writer) throws IOException {
    writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    return writer;
  }

  public static Writer outputXmlListWrapperOpenTag(final Writer writer,
      final XmlMapper mapper, final Class<?> cls) throws IOException {
    final PropertyName wrapperName = getRootWrapperName(mapper, cls);
    final String wrapperTagOpen = "<" + wrapperName.getSimpleName() + ">";
    writer.write(wrapperTagOpen);
    writer.write('\n');
    return writer;
  }

  public static Writer outputXmlListWrapperCloseTag(final Writer writer,
      final XmlMapper mapper, final Class<?> cls) throws IOException {
    final PropertyName wrapperName = getRootWrapperName(mapper, cls);
    final String itemTagClose = "</" + wrapperName.getSimpleName() + ">";
    writer.write(itemTagClose);
    writer.write('\n');
    return writer;
  }
}