////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import static ltd.qubit.commons.lang.StringUtils.isEmpty;

/**
 * 提供命名风格转换相关函数。
 *
 * @author 胡海星
 */
public class NamingStyleUtils {

  /**
   * 将首字母小写，CamelCase 风格，带"."的属性路径称转换为 under score 风格的数据库表的字段名。
   *
   * @param path
   *     一个首字母小写，CamelCase 风格，带"."的属性路径。
   * @return
   *     转换后的 under score 风格的数据库表的字段名。
   */
  public static String propertyPathToDatabaseField(final String path) {
    if (isEmpty(path)) {
      return path;
    }
    final StringBuilder builder = new StringBuilder();
    char lastChar = '?';
    for (int i = 0; i < path.length(); ++i) {
      final char ch = path.charAt(i);
      if (ch >= 'A' && ch <= 'Z') {
        if (i > 0 && (lastChar != '_')) {
          builder.append('_');
        }
        builder.append(Character.toLowerCase(ch));
        lastChar = ch;
      } else if (ch == '.') {
        if (lastChar != '_') {
          builder.append('_');
          lastChar = '_';
        }
      } else {
        builder.append(ch);
        lastChar = ch;
      }
    }
    return builder.toString();
  }
}