////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.regex.Matcher;

/**
 * 英语单词变形器，将单词转换为单数、复数、人类可读格式、下划线、驼峰命名或序数形式。
 * <p>
 * 此类灵感来源于<a href="http://api.rubyonrails.org/classes/Inflector.html">Ruby on Rails</a>
 * 中的<a href="http://www.rubyonrails.org">Inflector类</a>，该类基于
 * <a href="http://wiki.rubyonrails.org/rails/pages/License">Rails许可证</a>分发。
 *
 * @author 胡海星
 */
public class Inflector {

  /**
   * 全局单例实例。
   */
  protected static final Inflector INSTANCE = new Inflector();

  /**
   * 获取单例实例。
   *
   * @return 单例实例
   */
  public static Inflector getInstance() {
    return INSTANCE;
  }

  /**
   * 复数变形规则列表。
   */
  private final LinkedList<Rule> plurals = new LinkedList<>();

  /**
   * 单数变形规则列表。
   */
  private final LinkedList<Rule> singulars = new LinkedList<>();

  /**
   * 不可数单词集合，这些小写单词将被排除且不被处理。
   * 用户可以通过{@link #getUncountables()}方法修改此集合。
   */
  private final Set<String> uncountables = new HashSet<>();

  /**
   * 构造一个新的单词变形器实例。
   */
  public Inflector() {
    initialize();
  }

  /**
   * 根据现有的单词变形器创建一个拷贝。
   *
   * @param original 原始的单词变形器实例
   */
  protected Inflector(final Inflector original) {
    this.plurals.addAll(original.plurals);
    this.singulars.addAll(original.singulars);
    this.uncountables.addAll(original.uncountables);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Inflector clone() {
    return new Inflector(this);
  }

  /**
   * 返回字符串中单词的复数形式。
   *
   * <p>示例：</p>
   * <pre><code>
   *   inflector.pluralize(&quot;post&quot;)               #=&gt; &quot;posts&quot;
   *   inflector.pluralize(&quot;octopus&quot;)            #=&gt; &quot;octopi&quot;
   *   inflector.pluralize(&quot;sheep&quot;)              #=&gt; &quot;sheep&quot;
   *   inflector.pluralize(&quot;words&quot;)              #=&gt; &quot;words&quot;
   *   inflector.pluralize(&quot;the blue mailman&quot;)   #=&gt; &quot;the blue mailmen&quot;
   *   inflector.pluralize(&quot;CamelOctopus&quot;)       #=&gt; &quot;CamelOctopi&quot;
   * </code></pre>
   *
   * <p>注意：对所提供的对象调用{@link Object#toString()}方法，
   * 因此此方法也适用于非字符串类型。</p>
   *
   * @param word 要转换为复数的单词
   * @return 单词的复数形式，如果无法复数化则返回原单词
   * @see #singularize(String)
   */
  public String pluralize(final String word) {
    if (word == null) {
      return null;
    }
    final String wordStr = new Stripper().strip(word);
    if (wordStr.length() == 0) {
      return wordStr;
    }
    if (isUncountable(wordStr)) {
      return wordStr;
    }
    for (final Rule rule : this.plurals) {
      final String result = rule.apply(wordStr);
      if (result != null) {
        return result;
      }
    }
    return wordStr;
  }

  /**
   * 根据数量返回单词的复数或单数形式。
   * 当数量为1或-1时返回原单词，否则返回复数形式。
   *
   * @param word 要处理的单词
   * @param count 数量
   * @return 根据数量处理后的单词形式
   */
  public String pluralize(final String word, final int count) {
    if (word == null) {
      return null;
    }
    if (count == 1 || count == -1) {
      return word;
    }
    return pluralize(word);
  }

  /**
   * 返回字符串中单词的单数形式。
   *
   * <p>示例：</p>
   * <pre><code>
   *   inflector.singularize(&quot;posts&quot;)             #=&gt; &quot;post&quot;
   *   inflector.singularize(&quot;octopi&quot;)            #=&gt; &quot;octopus&quot;
   *   inflector.singularize(&quot;sheep&quot;)             #=&gt; &quot;sheep&quot;
   *   inflector.singularize(&quot;words&quot;)             #=&gt; &quot;word&quot;
   *   inflector.singularize(&quot;the blue mailmen&quot;)  #=&gt; &quot;the blue mailman&quot;
   *   inflector.singularize(&quot;CamelOctopi&quot;)       #=&gt; &quot;CamelOctopus&quot;
   * </code></pre>
   *
   * <p>注意：对所提供的对象调用{@link Object#toString()}方法，
   * 因此此方法也适用于非字符串类型。</p>
   *
   * @param word 要转换为单数的单词
   * @return 单词的单数形式，如果无法单数化则返回原单词
   * @see #pluralize(String)
   */
  public String singularize(final String word) {
    if (word == null) {
      return null;
    }
    final String wordStr = new Stripper().strip(word);
    if (wordStr.length() == 0) {
      return wordStr;
    }
    if (isUncountable(wordStr)) {
      return wordStr;
    }
    for (final Rule rule : this.singulars) {
      final String result = rule.apply(wordStr);
      if (result != null) {
        return result;
      }
    }
    return wordStr;
  }

  /**
   * 将字符串转换为小驼峰命名法（lowerCamelCase）。
   *
   * <p>此方法还会使用任何额外的分隔符字符来识别单词边界。</p>
   *
   * <p>示例：</p>
   * <pre><code>
   *   inflector.lowerCamelCase(&quot;active_record&quot;)       #=&gt; &quot;activeRecord&quot;
   *   inflector.lowerCamelCase(&quot;first_name&quot;)          #=&gt; &quot;firstName&quot;
   *   inflector.lowerCamelCase(&quot;name&quot;)                #=&gt; &quot;name&quot;
   *   inflector.lowerCamelCase(&quot;the-first_name&quot;,'-')  #=&gt; &quot;theFirstName&quot;
   * </code></pre>
   *
   * @param lowerCaseAndUnderscoredWord 要转换为驼峰命名的小写下划线单词
   * @param delimiterChars 可选的分隔符字符，用于分隔单词边界
   * @return 小驼峰命名版本的单词
   * @see #underscore(String, char[])
   * @see #camelCase(String, boolean, char[])
   * @see #upperCamelCase(String, char[])
   */
  public String lowerCamelCase(final String lowerCaseAndUnderscoredWord,
      final char... delimiterChars) {
    return camelCase(lowerCaseAndUnderscoredWord, false, delimiterChars);
  }

  /**
   * 将字符串转换为大驼峰命名法（UpperCamelCase）。
   * 此方法还会使用任何额外的分隔符字符来识别单词边界。
   *
   * <p>示例：</p>
   * <pre><code>
   *   inflector.upperCamelCase(&quot;active_record&quot;)       #=&gt; &quot;ActiveRecord&quot;
   *   inflector.upperCamelCase(&quot;first_name&quot;)          #=&gt; &quot;FirstName&quot;
   *   inflector.upperCamelCase(&quot;name&quot;)                #=&gt; &quot;Name&quot;
   *   inflector.upperCamelCase(&quot;the-first_name&quot;,'-')  #=&gt; &quot;TheFirstName&quot;
   * </code></pre>
   *
   * @param lowerCaseAndUnderscoredWord 要转换为驼峰命名的小写下划线单词
   * @param delimiterChars 可选的分隔符字符，用于分隔单词边界
   * @return 大驼峰命名版本的单词
   * @see #underscore(String, char[])
   * @see #camelCase(String, boolean, char[])
   * @see #lowerCamelCase(String, char[])
   */
  public String upperCamelCase(final String lowerCaseAndUnderscoredWord,
      final char... delimiterChars) {
    return camelCase(lowerCaseAndUnderscoredWord, true, delimiterChars);
  }

  /**
   * 默认情况下，此方法将字符串转换为大驼峰命名法（UpperCamelCase）。
   *
   * <p>如果将{@code uppercaseFirstLetter}参数设置为false，则此方法产生小驼峰命名法。
   * 此方法还会使用任何额外的分隔符字符来识别单词边界。</p>
   *
   * <p>示例：</p>
   * <pre><code>
   *   inflector.camelCase(&quot;active_record&quot;,false)    #=&gt; &quot;activeRecord&quot;
   *   inflector.camelCase(&quot;active_record&quot;,true)     #=&gt; &quot;ActiveRecord&quot;
   *   inflector.camelCase(&quot;first_name&quot;,false)       #=&gt; &quot;firstName&quot;
   *   inflector.camelCase(&quot;first_name&quot;,true)        #=&gt; &quot;FirstName&quot;
   *   inflector.camelCase(&quot;name&quot;,false)             #=&gt; &quot;name&quot;
   *   inflector.camelCase(&quot;name&quot;,true)              #=&gt; &quot;Name&quot;
   * </code></pre>
   *
   * @param lowerCaseAndUnderscoredWord 要转换为驼峰命名的小写下划线单词
   * @param uppercaseFirstLetter 如果第一个字符要大写则为true，如果第一个字符要小写则为false
   * @param delimiterChars 可选的分隔符字符，用于分隔单词边界
   * @return 驼峰命名版本的单词
   * @see #underscore(String, char[])
   * @see #upperCamelCase(String, char[])
   * @see #lowerCamelCase(String, char[])
   */
  public String camelCase(final String lowerCaseAndUnderscoredWord,
      final boolean uppercaseFirstLetter, final char... delimiterChars) {
    if (lowerCaseAndUnderscoredWord == null) {
      return null;
    }
    final String word = new Stripper().strip(lowerCaseAndUnderscoredWord);
    if (word.length() == 0) {
      return "";
    }
    if (uppercaseFirstLetter) {
      String result = word;
      // Replace any extra delimiters with underscores (before the underscores
      // are converted in the next step)...
      if (delimiterChars != null) {
        for (final char delimiterChar : delimiterChars) {
          result = result.replace(delimiterChar, '_');
        }
      }
      // Change the case at the beginning at after each underscore ...
      return replaceAllWithUppercase(result, "(^|_)(.)", 2);
    }
    if (word.length() < 2) {
      return word;
    }
    return "" + Character.toLowerCase(word.charAt(0))
        + camelCase(word, true, delimiterChars).substring(1);
  }

  /**
   * 从字符串中的表达式生成下划线形式（{@link #camelCase(String, boolean, char[]) camelCase}方法的逆向操作）。
   * 还会将匹配所提供分隔符的任何字符更改为下划线。
   *
   * <p>示例：</p>
   * <pre><code>
   *   inflector.underscore(&quot;activeRecord&quot;)     #=&gt; &quot;active_record&quot;
   *   inflector.underscore(&quot;ActiveRecord&quot;)     #=&gt; &quot;active_record&quot;
   *   inflector.underscore(&quot;firstName&quot;)        #=&gt; &quot;first_name&quot;
   *   inflector.underscore(&quot;FirstName&quot;)        #=&gt; &quot;first_name&quot;
   *   inflector.underscore(&quot;name&quot;)             #=&gt; &quot;name&quot;
   *   inflector.underscore(&quot;The.firstName&quot;)    #=&gt; &quot;the_first_name&quot;
   * </code></pre>
   *
   * @param camelCaseWord 要转换的驼峰命名单词
   * @param delimiterChars 可选的分隔符字符，用于分隔单词边界（除了大小写）
   * @return 输入的小写版本，用下划线字符分隔单词
   */
  public String underscore(final String camelCaseWord,
      final char... delimiterChars) {
    if (camelCaseWord == null) {
      return null;
    }
    String result = camelCaseWord.trim();
    if (result.length() == 0) {
      return "";
    }
    result = result.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2");
    result = result.replaceAll("([a-z\\d])([A-Z])", "$1_$2");
    result = result.replace('-', '_');
    if (delimiterChars != null) {
      for (final char delimiterChar : delimiterChars) {
        result = result.replace(delimiterChar, '_');
      }
    }
    return result.toLowerCase();
  }

  /**
   * 返回输入字符串的副本，第一个字符转换为大写，其余字符转换为小写。
   *
   * @param words 要首字母大写的单词
   * @return 第一个字符大写，其余字符小写的字符串
   */
  public String capitalize(final String words) {
    if (words == null) {
      return null;
    }
    final String result = new Stripper().strip(words);
    if (result.length() == 0) {
      return "";
    }
    if (result.length() == 1) {
      return result.toUpperCase();
    }
    return "" + Character.toUpperCase(result.charAt(0))
        + result.substring(1).toLowerCase();
  }

  /**
   * 将第一个单词首字母大写，将下划线转换为空格，并删除尾随的"_id"和任何提供的可移除标记。
   *
   * <p>与{@link #titleCase(String, String[])}类似，此方法用于创建美观的输出。</p>
   *
   * <p>示例：</p>
   * <pre><code>
   *   inflector.humanize(&quot;employee_salary&quot;)       #=&gt; &quot;Employee salary&quot;
   *   inflector.humanize(&quot;author_id&quot;)             #=&gt; &quot;Author&quot;
   * </code></pre>
   *
   * @param lowerCaseAndUnderscoredWords 要人性化的输入
   * @param removableTokens 可选的要移除的标记数组
   * @return 人性化的字符串
   * @see #titleCase(String, String[])
   */
  public String humanize(final String lowerCaseAndUnderscoredWords,
      final String... removableTokens) {
    if (lowerCaseAndUnderscoredWords == null) {
      return null;
    }
    String result = new Stripper().strip(lowerCaseAndUnderscoredWords);
    if (result.length() == 0) {
      return "";
    }
    // Remove a trailing "_id" token
    result = result.replaceAll("_id$", "");
    // Remove all of the tokens that should be removed
    if (removableTokens != null) {
      for (final String removableToken : removableTokens) {
        result = result.replaceAll(removableToken, "");
      }
    }
    result = result.replaceAll("_+", " "); // replace all adjacent underscores with a single space
    return capitalize(result);
  }

  /**
   * 将所有单词首字母大写，并替换字符串中的某些字符以创建更美观的标题。
   *
   * <p>下划线被转换为空格，尾随的"_id"被移除，任何提供的标记都被移除。
   * 与{@link #humanize(String, String[])}类似，此方法用于创建美观的输出。</p>
   *
   * <p>示例：</p>
   * <pre><code>
   *   inflector.titleCase(&quot;man from the boondocks&quot;) #=&gt; &quot;Man From The Boondocks&quot;
   *   inflector.titleCase(&quot;x-men: the last stand&quot;)  #=&gt; &quot;X Men: The Last Stand&quot;
   * </code></pre>
   *
   * @param words 要转换为标题格式的输入
   * @param removableTokens 可选的要移除的标记数组
   * @return 标题格式的字符串
   */
  public String titleCase(final String words,
      final String... removableTokens) {
    String result = humanize(words, removableTokens);
    result = replaceAllWithUppercase(result, "\\b([a-z])", 1); // change first char of each word to uppercase
    return result;
  }

  /**
   * 将非负数转换为序数字符串，用于表示有序序列中的位置，如1st、2nd、3rd、4th。
   *
   * @param number 非负数
   * @return 包含数字和序数后缀的字符串
   */
  public String ordinalize(final int number) {
    // stop checkstyle: MagicNumber
    final String numberStr = Integer.toString(number);
    if (11 <= number && number <= 13) {
      return numberStr + "th";
    }
    final int remainder = number % 10;
    if (remainder == 1) {
      return numberStr + "st";
    }
    if (remainder == 2) {
      return numberStr + "nd";
    }
    if (remainder == 3) {
      return numberStr + "rd";
    }
    // resume checkstyle: MagicNumber
    return numberStr + "th";
  }

  /**
   * 确定所提供的单词是否被{@link #pluralize(String) pluralize}和
   * {@link #singularize(String) singularize}方法视为不可数。
   *
   * @param word 单词
   * @return 如果单词的复数和单数形式相同则返回true
   */
  public boolean isUncountable(final String word) {
    if (word == null) {
      return false;
    }
    final String trimmedLower = new Stripper().strip(word).toLowerCase();
    return this.uncountables.contains(trimmedLower);
  }

  /**
   * 获取不被变形器处理的单词集合。
   * 返回的集合可以直接修改。
   *
   * @return 不可数单词的集合
   */
  public Set<String> getUncountables() {
    return uncountables;
  }

  /**
   * 添加复数变形规则。
   *
   * @param rule 正则表达式规则
   * @param replacement 替换字符串
   */
  public void addPluralize(final String rule, final String replacement) {
    final Rule pluralizeRule = new Rule(rule, replacement);
    this.plurals.addFirst(pluralizeRule);
  }

  /**
   * 添加单数变形规则。
   *
   * @param rule 正则表达式规则
   * @param replacement 替换字符串
   */
  public void addSingularize(final String rule, final String replacement) {
    final Rule singularizeRule = new Rule(rule, replacement);
    this.singulars.addFirst(singularizeRule);
  }

  /**
   * 添加不规则单复数变形规则。
   *
   * @param singular 单数形式
   * @param plural 复数形式
   */
  public void addIrregular(final String singular, final String plural) {
    final String singularRemainder = singular.length() > 1 ? singular.substring(1) : "";
    final String pluralRemainder = plural.length() > 1 ? plural.substring(1) : "";
    addPluralize("(" + singular.charAt(0) + ")" + singularRemainder + "$",
        "$1" + pluralRemainder);
    addSingularize("(" + plural.charAt(0) + ")" + pluralRemainder + "$",
        "$1" + singularRemainder);
  }

  /**
   * 添加不可数单词。
   *
   * @param words 要添加的不可数单词数组
   */
  public void addUncountable(final String... words) {
    if (words == null || words.length == 0) {
      return;
    }
    for (final String word : words) {
      if (word != null) {
        uncountables.add(new Stripper().strip(word).toLowerCase());
      }
    }
  }

  /**
   * 工具方法，用指定后向引用的大写形式替换所有出现的内容，并移除所有其他后向引用。
   *
   * <p>Java的{@link java.util.regex.Pattern 正则表达式处理}不使用预处理指令
   * <code>\l</code>、<code>&#92;u</code>、<code>\L</code>和<code>\U</code>。
   * 如果支持，这些指令可以在替换字符串中用于大写或小写后向引用。
   * 例如，<code>\L1</code>会将第一个后向引用小写，<code>&#92;u3</code>会将第3个后向引用大写。</p>
   *
   * @param input 输入字符串
   * @param regex 正则表达式
   * @param groupNumberToUppercase 要大写的组号
   * @return 将适当字符转换为大写的输入字符串
   */
  protected static String replaceAllWithUppercase(final String input,
      final String regex, final int groupNumberToUppercase) {
    final java.util.regex.Pattern underscoreAndDotPattern =
        java.util.regex.Pattern.compile(regex);
    final Matcher matcher = underscoreAndDotPattern.matcher(input);
    final StringBuilder builder = new StringBuilder();
    while (matcher.find()) {
      matcher.appendReplacement(builder,
          matcher.group(groupNumberToUppercase).toUpperCase());
    }
    matcher.appendTail(builder);
    return builder.toString();
  }

  /**
   * 完全移除此变形器中的所有规则。
   */
  public void clear() {
    this.uncountables.clear();
    this.plurals.clear();
    this.singulars.clear();
  }

  /**
   * 初始化默认的变形规则。
   */
  protected void initialize() {
    final Inflector inflect = this;
    inflect.addPluralize("$", "s");
    inflect.addPluralize("s$", "s");
    inflect.addPluralize("(ax|test)is$", "$1es");
    inflect.addPluralize("(octop|vir)us$", "$1i");
    inflect.addPluralize("(octop|vir)i$", "$1i"); // already plural
    inflect.addPluralize("(alias|status)$", "$1es");
    inflect.addPluralize("(bu)s$", "$1ses");
    inflect.addPluralize("(buffal|tomat)o$", "$1oes");
    inflect.addPluralize("([ti])um$", "$1a");
    inflect.addPluralize("([ti])a$", "$1a"); // already plural
    inflect.addPluralize("sis$", "ses");
    inflect.addPluralize("(?:([^f])fe|([lr])f)$", "$1$2ves");
    inflect.addPluralize("(hive)$", "$1s");
    inflect.addPluralize("([^aeiouy]|qu)y$", "$1ies");
    inflect.addPluralize("(x|ch|ss|sh)$", "$1es");
    inflect.addPluralize("(matr|vert|ind)ix|ex$", "$1ices");
    inflect.addPluralize("([m|l])ouse$", "$1ice");
    inflect.addPluralize("([m|l])ice$", "$1ice");
    inflect.addPluralize("^(ox)$", "$1en");
    inflect.addPluralize("(quiz)$", "$1zes");
    // Need to check for the following words that are already pluralized:
    inflect.addPluralize("(people|men|children|sexes|moves|stadiums)$", "$1"); // irregulars
    inflect.addPluralize("(oxen|octopi|viri|aliases|quizzes)$", "$1"); // special rules

    inflect.addSingularize("s$", "");
    inflect.addSingularize("(s|si|u)s$", "$1s"); // '-us' and '-ss' are already singular
    inflect.addSingularize("(n)ews$", "$1ews");
    inflect.addSingularize("([ti])a$", "$1um");
    inflect.addSingularize("((a)naly|(b)a|(d)iagno|(p)arenthe|(p)rogno|(s)ynop|(t)he)ses$", "$1$2sis");
    inflect.addSingularize("(^analy)ses$", "$1sis");
    inflect.addSingularize("(^analy)sis$", "$1sis"); // already singular, but ends in 's'
    inflect.addSingularize("([^f])ves$", "$1fe");
    inflect.addSingularize("(hive)s$", "$1");
    inflect.addSingularize("(tive)s$", "$1");
    inflect.addSingularize("([lr])ves$", "$1f");
    inflect.addSingularize("([^aeiouy]|qu)ies$", "$1y");
    inflect.addSingularize("(s)eries$", "$1eries");
    inflect.addSingularize("(m)ovies$", "$1ovie");
    inflect.addSingularize("(x|ch|ss|sh)es$", "$1");
    inflect.addSingularize("([m|l])ice$", "$1ouse");
    inflect.addSingularize("(bus)es$", "$1");
    inflect.addSingularize("(o)es$", "$1");
    inflect.addSingularize("(shoe)s$", "$1");
    inflect.addSingularize("(cris|ax|test)is$", "$1is"); // already singular, but ends in 's'
    inflect.addSingularize("(cris|ax|test)es$", "$1is");
    inflect.addSingularize("(octop|vir)i$", "$1us");
    inflect.addSingularize("(octop|vir)us$", "$1us"); // already singular, but ends in 's'
    inflect.addSingularize("(alias|status)es$", "$1");
    inflect.addSingularize("(alias|status)$", "$1"); // already singular, but ends in 's'
    inflect.addSingularize("^(ox)en", "$1");
    inflect.addSingularize("(vert|ind)ices$", "$1ex");
    inflect.addSingularize("(matr)ices$", "$1ix");
    inflect.addSingularize("(quiz)zes$", "$1");

    inflect.addIrregular("person", "people");
    inflect.addIrregular("man", "men");
    inflect.addIrregular("child", "children");
    inflect.addIrregular("sex", "sexes");
    inflect.addIrregular("move", "moves");
    inflect.addIrregular("stadium", "stadiums");
    inflect.addIrregular("foot", "feet");
    inflect.addIrregular("tooth", "teeth");
    inflect.addIrregular("goose", "geese");
    inflect.addIrregular("datum", "data");

    inflect.addUncountable("equipment", "information", "rice", "money", "species", "series", "fish", "sheep");
  }

  /**
   * 变形规则内部类。
   */
  private static class Rule {

    /**
     * 正则表达式字符串。
     */
    protected final String expression;

    /**
     * 编译后的正则表达式模式。
     */
    protected final java.util.regex.Pattern pattern;

    /**
     * 替换字符串。
     */
    protected final String replacement;

    /**
     * 构造变形规则。
     *
     * @param expression 正则表达式
     * @param replacement 替换字符串
     */
    protected Rule(final String expression, final String replacement) {
      this.expression = expression;
      this.replacement = replacement != null ? replacement : "";
      this.pattern = java.util.regex.Pattern.compile(this.expression,
          java.util.regex.Pattern.CASE_INSENSITIVE);
    }

    /**
     * 对输入字符串应用规则，返回修改后的字符串，如果规则不适用则返回null（没有进行修改）。
     *
     * @param input 输入字符串
     * @return 如果此规则适用则返回修改后的字符串，如果输入未被此规则修改则返回null
     */
    protected String apply(final String input) {
      final Matcher matcher = this.pattern.matcher(input);
      if (!matcher.find()) {
        return null;
      }
      return matcher.replaceAll(this.replacement);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
      return expression.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
      if (obj == this) {
        return true;
      }
      if ((obj != null) && (obj.getClass() == this.getClass())) {
        final Rule that = (Rule) obj;
        return this.expression.equalsIgnoreCase(that.expression);
      }
      return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
      return expression + ", " + replacement;
    }
  }
}