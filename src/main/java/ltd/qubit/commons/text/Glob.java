////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.io.Serial;
import java.io.Serializable;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.concurrent.NotThreadSafe;

import ltd.qubit.commons.error.TypeMismatchException;
import ltd.qubit.commons.io.serialize.BinarySerialization;
import ltd.qubit.commons.io.serialize.XmlSerialization;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 文件名 glob 模式的类。
 *
 * <p>glob 模式语法遵循 <a href="http://www.jedit.org/users-guide/globs.html">Globs</a> 规则。
 *
 * <p>在 glob 模式中，以下字符序列具有特殊含义：
 *
 * <ul>
 * <li>? 匹配任意一个字符</li>
 * <li>* 匹配任意数量的字符</li>
 * <li>{!glob} 匹配任何不匹配 glob 的内容</li>
 * <li>{a,b,c} 匹配 a、b 或 c 中的任意一个</li>
 * <li>[abc] 匹配集合 a、b 或 c 中的任意字符</li>
 * <li>[^abc] 匹配不在集合 a、b 或 c 中的任意字符</li>
 * <li>[a-z] 匹配范围 a 到 z 中的任意字符（包含边界）。前导或尾随短划线将被按字面意思解释</li>
 * </ul>
 *
 * @author 胡海星
 */
@NotThreadSafe
public class Glob implements Serializable, CloneableEx<Glob>, Assignable<Glob> {

  @Serial
  private static final long   serialVersionUID = -1303433525002426637L;

  public static final int DEFAULT_FLAGS        = Pattern.CASE_INSENSITIVE;
  public static final String  ROOT_NODE        = "glob";
  public static final String  FLAGS_ATTRIBUTE  = "flags";

  private static final Object NEGATIVE         = new Object();
  private static final Object GROUP            = new Object();

  static {
    BinarySerialization.register(Glob.class, GlobBinarySerializer.INSTANCE);
    XmlSerialization.register(Glob.class, GlobXmlSerializer.INSTANCE);
  }

  /**
   * 测试指定的字符串是否为 glob 模式。
   *
   * @param str
   *     要测试的字符串。
   * @return
   *     如果指定的字符串是 glob 模式则返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean isGlob(final String str) {
    if (str == null) {
      return false;
    }
    final int len = str.length();
    if (len == 0) {
      return false;
    }
    for (int i = 0; i < len; ++i) {
      final char ch = str.charAt(i);
      switch (ch) {
        case '?':
        case '*':
        case '{':
        case '}':
        case '[':
        case ']':
          return true;
        case '\\':
          if ((i + 1) < len) {
            ++i;
          }
          break;
        default:
          break;
      }
    }
    return false;
  }

  /**
   * 将 Unix 风格的文件名 glob 转换为正则表达式。
   *
   * <p>由于我们使用 java.util.regex 模式来实现 glob，这意味着除了上述内容之外，
   * 还可以使用一些"字符类元字符"。请记住，它们的用处有限，因为正则表达式量词元字符
   * （星号、问号和花括号）在文件名 glob 语言中被重新定义为其他含义，
   * 而正则表达式量词在 glob 语言中不可用。
   *
   * <ul>
   * <li>\w 匹配任何字母数字字符或下划线</li>
   * <li>\s 匹配空格或水平制表符</li>
   * <li>\S 匹配可打印的非空白字符。</li>
   * <li>\d 匹配十进制数字</li>
   * </ul>
   *
   *
   * <p>以下是一些 glob 模式的示例：
   *
   * <ul>
   * <li>"*" - 所有文件。</li>
   * <li>"*.java" - 所有名称以 ".java" 结尾的文件。</li>
   * <li>"*.[ch]" - 所有名称以 ".c" 或 ".h" 结尾的文件。</li>
   * <li>"*.{c,cpp,h,hpp,cxx,hxx}" - 所有 C 或 C++ 文件。</li>
   * <li>"[^#]*" - 所有名称不以 "#" 开头的文件。</li>
   * </ul>
   *
   *
   * <p>此函数进行以下转换：
   *
   * <ul>
   * <li>"?" 变成 "."</li>
   * <li>"*" 变成 ".*"</li>
   * <li>"{aa,bb}" 变成 "(aa|bb)"</li>
   * <li>正则表达式中的所有其他特殊元字符都被转义。</li>
   * </ul>
   *
   * @param glob
   *          Unix 风格的文件名 glob 模式
   * @return 从该 glob 转换的正则表达式的字符串表示。
   */
  public static String toRegex(final String glob) {
    final Stack<Object> state = new Stack<>();
    boolean backSlash = false;
    final int globLen = glob.length();
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < globLen; ++i) {
      final char ch = glob.charAt(i);
      if (backSlash) {
        builder.append('\\').append(ch);
        backSlash = false;
        continue;
      }
      switch (ch) {
        case '\\':
          backSlash = true;
          break;
        case '?':
          builder.append('.');
          break;
        case '.':
        case '+':
        case '(':
        case ')':
          builder.append('\\').append(ch);
          break;
        case '*':
          builder.append('.').append('*');
          break;
        case '|':
          if (backSlash) {
            builder.append('\\').append('|');
          } else {
            builder.append('|');
          }
          break;
        case '{': {
          builder.append('(');
          final int next = i + 1;
          if ((next != glob.length()) && (glob.charAt(next) == '!')) {
            builder.append('?');
            state.push(NEGATIVE);
          } else {
            state.push(GROUP);
          }
          break;
        }
        case ',':
          if (! state.isEmpty() && (state.peek() == GROUP)) {
            builder.append('|');
          } else {
            builder.append(',');
          }
          break;
        case '}':
          if (! state.isEmpty()) {
            builder.append(")");
            if (state.pop() == NEGATIVE) {
              builder.append('.').append('*');
            }
          } else {
            builder.append('}');
          }
          break;
        default:
          builder.append(ch);
      }
    }
    return builder.toString();
  }

  protected String pattern;
  protected int flags;
  protected transient Matcher matcher;

  /**
   * 构造一个默认的 Glob 对象。
   */
  public Glob() {
    pattern = StringUtils.EMPTY;
    flags = DEFAULT_FLAGS;
    matcher = null;
  }

  /**
   * 构造一个不区分大小写的 glob 模式对象。
   *
   * @param pattern
   *          glob 模式的字符串。
   */
  public Glob(final String pattern) {
    this.pattern = requireNonNull("pattern", pattern);
    flags = DEFAULT_FLAGS;
    matcher = null;
  }

  /**
   * 构造一个 glob 模式对象。
   *
   * @param pattern
   *          glob 模式的字符串。
   * @param flags
   *          匹配标志，一个位掩码，可能包括
   *          Pattern.CASE_INSENSITIVE、Pattern.MULTILINE、Pattern.DOTALL、
   *          Pattern.UNICODE_CASE、Pattern.CANON_EQ、Pattern.UNIX_LINES、
   *          Pattern.LITERAL 和 Pattern.COMMENTS。其中 Pattern 是
   *          java.util.regex.Pattern 类。
   */
  public Glob(final String pattern, final int flags) {
    this.pattern = requireNonNull("pattern", pattern);
    this.flags = flags;
    matcher = null;
  }

  /**
   * 获取匹配标志。
   *
   * @return 匹配标志。
   */
  public int getFlags() {
    return flags;
  }

  /**
   * 设置匹配标志。
   *
   * @param flags
   *          匹配标志。
   */
  public void setFlags(final int flags) {
    this.flags = flags;
    matcher = null;
  }

  /**
   * 获取 glob 模式字符串。
   *
   * @return glob 模式字符串。
   */
  public String getPattern() {
    return pattern;
  }

  /**
   * 设置 glob 模式字符串。
   *
   * @param pattern
   *          glob 模式字符串。
   */
  public void setPattern(final String pattern) {
    this.pattern = requireNonNull("pattern", pattern);
    matcher = null;
  }

  /**
   * 设置 glob 模式字符串和匹配标志。
   *
   * @param pattern
   *          glob 模式字符串。
   * @param flags
   *          匹配标志。
   */
  public void set(final String pattern, final int flags) {
    this.pattern = requireNonNull("pattern", pattern);
    this.flags = flags;
    matcher = null;
  }

  /**
   * 测试字符串是否匹配此 glob 模式。
   *
   * @param str
   *          要测试的字符串。
   * @return 如果字符串匹配此 glob 模式则返回 {@code true}；否则返回 {@code false}。
   */
  public boolean matches(final String str) {
    if (str == null) {
      return false;
    } else if (pattern.length() == 0) {
      return false;
    }
    if (matcher == null) {
      final String regex = toRegex(pattern);
      matcher = Pattern.compile(regex, flags)
                       .matcher(StringUtils.EMPTY);
    }
    return matcher.reset(str).matches();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void assign(final Glob that) {
    if (this != that) {
      final Class<?> thisClass = getClass();
      final Class<?> thatClass = that.getClass();
      if (thisClass != thatClass) {
        throw new TypeMismatchException(thisClass, thatClass);
      }
      flags = that.flags;
      pattern = that.pattern;
      if (that.matcher == null) {
        matcher = null;
      } else {
        matcher = that.matcher.pattern().matcher(StringUtils.EMPTY);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Glob cloneEx() {
    final Glob result = new Glob();
    result.flags = flags;
    result.pattern = pattern;
    result.matcher = null;
    return result;
  }

  @Override
  public int hashCode() {
    int code = 77;
    final int multiplier = 131;
    code = Hash.combine(code, multiplier, flags);
    code = Hash.combine(code, multiplier, pattern);
    return code;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Glob other = (Glob) obj;
    return (flags == other.flags)
        && Equality.equals(pattern, other.pattern);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("flags", flags)
               .append("pattern", pattern)
               .toString();
  }
}