////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * Transforms words to singular, plural, humanized (human readable), underscore,
 * camel case, or ordinal form. This is inspired by the
 * <a href="http://api.rubyonrails.org/classes/Inflector.html">Inflector</a> class
 * in <a href="http://www.rubyonrails.org">Ruby on Rails</a>, which is
 * distributed under the
 * <a href="http://wiki.rubyonrails.org/rails/pages/License">Rails license</a>.
 *
 * @author Haixing Hu
 */
public class Inflector {

  protected static final Inflector INSTANCE = new Inflector();

  public static Inflector getInstance() {
    return INSTANCE;
  }

  private final LinkedList<Rule> plurals = new LinkedList<>();

  private final LinkedList<Rule> singulars = new LinkedList<>();

  /**
   * The lowercase words that are to be excluded and not processed. This map
   * can be modified by the users via {@link #getUncountables()}.
   */
  private final Set<String> uncountables = new HashSet<>();

  public Inflector() {
    initialize();
  }

  protected Inflector(final Inflector original) {
    this.plurals.addAll(original.plurals);
    this.singulars.addAll(original.singulars);
    this.uncountables.addAll(original.uncountables);
  }

  @Override
  public Inflector clone() {
    return new Inflector(this);
  }

  /**
   * Returns the plural form of the word in the string.
   *
   * <p>Examples:</p>
   * <pre><code>
   *   inflector.pluralize(&quot;post&quot;)               #=&gt; &quot;posts&quot;
   *   inflector.pluralize(&quot;octopus&quot;)            #=&gt; &quot;octopi&quot;
   *   inflector.pluralize(&quot;sheep&quot;)              #=&gt; &quot;sheep&quot;
   *   inflector.pluralize(&quot;words&quot;)              #=&gt; &quot;words&quot;
   *   inflector.pluralize(&quot;the blue mailman&quot;)   #=&gt; &quot;the blue mailmen&quot;
   *   inflector.pluralize(&quot;CamelOctopus&quot;)       #=&gt; &quot;CamelOctopi&quot;
   * </code></pre>
   *
   * <p>Note that if the {@link Object#toString()} is called on the supplied
   * object, so this method works for non-strings, too.</p>
   *
   * @param word
   *     the word that is to be pluralized.
   * @return
   *     the pluralized form of the word, or the word itself if it could not be
   *     pluralized.
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
   * Returns the singular form of the word in the string.
   *
   * <p>Examples:</p>
   * <pre><code>
   *   inflector.singularize(&quot;posts&quot;)             #=&gt; &quot;post&quot;
   *   inflector.singularize(&quot;octopi&quot;)            #=&gt; &quot;octopus&quot;
   *   inflector.singularize(&quot;sheep&quot;)             #=&gt; &quot;sheep&quot;
   *   inflector.singularize(&quot;words&quot;)             #=&gt; &quot;word&quot;
   *   inflector.singularize(&quot;the blue mailmen&quot;)  #=&gt; &quot;the blue mailman&quot;
   *   inflector.singularize(&quot;CamelOctopi&quot;)       #=&gt; &quot;CamelOctopus&quot;
   * </code></pre>
   *
   * <p>Note that if the {@link Object#toString()} is called on the supplied
   * object, so this method works for non-strings, too.</p>
   *
   * @param word
   *     the word that is to be pluralized.
   * @return
   *     the pluralized form of the word, or the word itself if it could not be
   *     pluralized.
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
   * Converts strings to lowerCamelCase.
   *
   * <p>This method will also use any extra delimiter characters to identify
   * word boundaries.</p>
   *
   * <p>Examples:</p>
   * <pre><code>
   *   inflector.lowerCamelCase(&quot;active_record&quot;)       #=&gt; &quot;activeRecord&quot;
   *   inflector.lowerCamelCase(&quot;first_name&quot;)          #=&gt; &quot;firstName&quot;
   *   inflector.lowerCamelCase(&quot;name&quot;)                #=&gt; &quot;name&quot;
   *   inflector.lowerCamelCase(&quot;the-first_name&quot;,'-')  #=&gt; &quot;theFirstName&quot;
   * </code></pre>
   *
   * @param lowerCaseAndUnderscoredWord
   *     the word that is to be converted to camel case.
   * @param delimiterChars
   *     optional characters that are used to delimit word boundaries.
   * @return
   *     the lower camel case version of the word.
   * @see #underscore(String, char[])
   * @see #camelCase(String, boolean, char[])
   * @see #upperCamelCase(String, char[])
   */
  public String lowerCamelCase(final String lowerCaseAndUnderscoredWord,
      final char... delimiterChars) {
    return camelCase(lowerCaseAndUnderscoredWord, false, delimiterChars);
  }

  /**
   * Converts strings to UpperCamelCase. This method will also use any extra
   * delimiter characters to identify word boundaries.
   *
   * <p>Examples:</p>
   * <pre><code>
   *   inflector.upperCamelCase(&quot;active_record&quot;)       #=&gt; &quot;SctiveRecord&quot;
   *   inflector.upperCamelCase(&quot;first_name&quot;)          #=&gt; &quot;FirstName&quot;
   *   inflector.upperCamelCase(&quot;name&quot;)                #=&gt; &quot;Name&quot;
   *   inflector.lowerCamelCase(&quot;the-first_name&quot;,'-')  #=&gt; &quot;TheFirstName&quot;
   * </code></pre>
   *
   * @param lowerCaseAndUnderscoredWord
   *     the word that is to be converted to camel case.
   * @param delimiterChars
   *     optional characters that are used to delimit word boundaries.
   * @return
   *     the upper camel case version of the word.
   * @see #underscore(String, char[])
   * @see #camelCase(String, boolean, char[])
   * @see #lowerCamelCase(String, char[])
   */
  public String upperCamelCase(final String lowerCaseAndUnderscoredWord,
      final char... delimiterChars) {
    return camelCase(lowerCaseAndUnderscoredWord, true, delimiterChars);
  }

  /**
   * By default, this method converts strings to UpperCamelCase.
   *
   * <p>If the {@code uppercaseFirstLetter} argument to false, then this method
   * produces lowerCamelCase. This method will also use any extra delimiter
   * characters to identify word boundaries.</p>
   *
   * <p>Examples:</p>
   * <pre><code>
   *   inflector.camelCase(&quot;active_record&quot;,false)    #=&gt; &quot;activeRecord&quot;
   *   inflector.camelCase(&quot;active_record&quot;,true)     #=&gt; &quot;ActiveRecord&quot;
   *   inflector.camelCase(&quot;first_name&quot;,false)       #=&gt; &quot;firstName&quot;
   *   inflector.camelCase(&quot;first_name&quot;,true)        #=&gt; &quot;FirstName&quot;
   *   inflector.camelCase(&quot;name&quot;,false)             #=&gt; &quot;name&quot;
   *   inflector.camelCase(&quot;name&quot;,true)              #=&gt; &quot;Name&quot;
   * </code></pre>
   *
   * @param lowerCaseAndUnderscoredWord
   *     the word that is to be converted to camel case.
   * @param uppercaseFirstLetter
   *     true if the first character is to be uppercased, or false if the first
   *     character is to be owercased.
   * @param delimiterChars
   *     optional characters that are used to delimit word boundaries.
   * @return
   *     the camel case version of the word.
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
   * Makes an underscored form from the expression in the string (the reverse
   * of the {@link #camelCase(String, boolean, char[]) camelCase} method.
   * Also changes any characters that match the supplied delimiters into
   * underscore.
   *
   * <p>Examples:</p>
   * <pre><code>
   *   inflector.underscore(&quot;activeRecord&quot;)     #=&gt; &quot;active_record&quot;
   *   inflector.underscore(&quot;ActiveRecord&quot;)     #=&gt; &quot;active_record&quot;
   *   inflector.underscore(&quot;firstName&quot;)        #=&gt; &quot;first_name&quot;
   *   inflector.underscore(&quot;FirstName&quot;)        #=&gt; &quot;first_name&quot;
   *   inflector.underscore(&quot;name&quot;)             #=&gt; &quot;name&quot;
   *   inflector.underscore(&quot;The.firstName&quot;)    #=&gt; &quot;the_first_name&quot;
   * </code></pre>
   *
   * @param camelCaseWord
   *     the camel-cased word that is to be converted.
   * @param delimiterChars
   *     optional characters that are used to delimit word boundaries
   *     (beyond capitalization).
   * @return
   *     a lower-cased version of the input, with separate words delimited by
   *     the underscore character.
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
   * Returns a copy of the input with the first character converted to
   * uppercase and the remainder to lowercase.
   *
   * @param words
   *     the word to be capitalized
   * @return
   *     the string with the first character capitalized and the remaining
   *     characters lowercased
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
   * Capitalizes the first word and turns underscores into spaces and strips
   * trailing "_id" and any supplied removable tokens.
   *
   * <p>Like {@link #titleCase(String, String[])}, this is meant for creating
   * pretty output.</p>
   *
   * <p>Examples:</p>
   * <pre><code>
   *   inflector.humanize(&quot;employee_salary&quot;)       #=&gt; &quot;Employee salary&quot;
   *   inflector.humanize(&quot;author_id&quot;)             #=&gt; &quot;Author&quot;
   * </code></pre>
   *
   * @param lowerCaseAndUnderscoredWords
   *     the input to be humanized.
   * @param removableTokens
   *     optional array of tokens that are to be removed.
   * @return
   *     the humanized string.
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

  // stop checkstyle: LineLength
  /**
   * Capitalizes all the words and replaces some characters in the string to
   * create a nicer looking title.
   *
   * <p>Underscores are changed to spaces, a trailing "_id" is removed, and any
   * of the supplied tokens are removed. Like {@link #humanize(String, String[])},
   * this is meant for creating pretty output.</p>
   *
   * <p>Examples:</p>
   * <pre><code>
   *   inflector.titleCase(&quot;man from the boondocks&quot;) #=&gt; &quot;Man From The Boondocks&quot;
   *   inflector.titleCase(&quot;x-men: the last stand&quot;)  #=&gt; &quot;X Men: The Last Stand&quot;
   * </code></pre>
   *
   * @param words
   *     the input to be turned into title case.
   * @param removableTokens
   *     optional array of tokens that are to be removed.
   * @return
   *     the title-case version of the supplied words.
   */
  public String titleCase(final String words,
      final String... removableTokens) {
    String result = humanize(words, removableTokens);
    result = replaceAllWithUppercase(result, "\\b([a-z])", 1); // change first char of each word to uppercase
    return result;
  }
  // resume checkstyle: LineLength

  /**
   * Turns a non-negative number into an ordinal string used to denote the
   * position in an ordered sequence, such as 1st, 2nd, 3rd, 4th.
   *
   * @param number
   *     the non-negative number.
   * @return
   *     the string with the number and ordinal suffix.
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
   * Determine whether the supplied word is considered uncountable by the
   * {@link #pluralize(String) pluralize} and
   * {@link #singularize(String) singularize} methods.
   *
   * @param word
   *     the word.
   * @return
   *     true if the plural and singular forms of the word are the same.
   */
  public boolean isUncountable(final String word) {
    if (word == null) {
      return false;
    }
    final String trimmedLower = new Stripper().strip(word).toLowerCase();
    return this.uncountables.contains(trimmedLower);
  }

  /**
   * Get the set of words that are not processed by the Inflector.
   * The resulting map is directly modifiable.
   *
   * @return
   *     the set of uncountable words.
   */
  public Set<String> getUncountables() {
    return uncountables;
  }

  public void addPluralize(final String rule, final String replacement) {
    final Rule pluralizeRule = new Rule(rule, replacement);
    this.plurals.addFirst(pluralizeRule);
  }

  public void addSingularize(final String rule, final String replacement) {
    final Rule singularizeRule = new Rule(rule, replacement);
    this.singulars.addFirst(singularizeRule);
  }

  public void addIrregular(final String singular, final String plural) {
    final String singularRemainder = singular.length() > 1 ? singular.substring(1) : "";
    final String pluralRemainder = plural.length() > 1 ? plural.substring(1) : "";
    addPluralize("(" + singular.charAt(0) + ")" + singularRemainder + "$",
        "$1" + pluralRemainder);
    addSingularize("(" + plural.charAt(0) + ")" + pluralRemainder + "$",
        "$1" + singularRemainder);
  }

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
   * Utility method to replace all occurrences given by the specific
   * backreference with its uppercased form, and remove all other backreferences.
   *
   * <p>The Java {@link Pattern regular expression processing} does not use the
   * preprocessing directives <code>\l</code>, <code>&#92;u</code>,
   * <code>\L</code>, and <code>\U</code>. If so, such directives could be used
   * in the replacement string to uppercase or lowercase the backreferences.
   * For example, <code>\L1</code> would lowercase the first backreference, and
   * <code>&#92;u3</code> would uppercase the 3rd backreference.</p>
   *
   * @param input
   *     the input string.
   * @param regex
   *     the regular expression.
   * @param groupNumberToUppercase
   *     indicate whether to group number to uppercase.
   * @return
   *     the input string with the appropriate characters converted to
   *     upper-case.
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
   * Completely remove all rules within this inflector.
   */
  public void clear() {
    this.uncountables.clear();
    this.plurals.clear();
    this.singulars.clear();
  }

  // stop checkstyle: LineLength
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
  // resume checkstyle: LineLength

  private static class Rule {

    protected final String expression;
    protected final java.util.regex.Pattern pattern;
    protected final String replacement;

    protected Rule(final String expression, final String replacement) {
      this.expression = expression;
      this.replacement = replacement != null ? replacement : "";
      this.pattern = java.util.regex.Pattern.compile(this.expression,
          java.util.regex.Pattern.CASE_INSENSITIVE);
    }

    /**
     * Apply the rule against the input string, returning the modified string
     * or null if the rule didn't apply (and no modifications were made).
     *
     * @param input
     *     the input string.
     * @return
     *     the modified string if this rule applied, or null if the input
     *     was not modified by this rule.
     */
    protected String apply(final String input) {
      final Matcher matcher = this.pattern.matcher(input);
      if (!matcher.find()) {
        return null;
      }
      return matcher.replaceAll(this.replacement);
    }

    @Override
    public int hashCode() {
      return expression.hashCode();
    }

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

    @Override
    public String toString() {
      return expression + ", " + replacement;
    }
  }
}
