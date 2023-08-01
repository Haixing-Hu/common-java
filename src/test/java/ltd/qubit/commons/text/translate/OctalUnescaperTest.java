////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.translate;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OctalUnescaperTest {

  @Test
  public void testBetween() {
    final OctalUnescaper oue = new OctalUnescaper();   //.between("1", "377");

    String input = "\\45";
    String result = oue.transform(input);
    assertThat(result)
        .as("Failed to unescape octal characters via the between method")
        .isEqualTo("\45");

    input = "\\377";
    result = oue.transform(input);
    assertThat(result)
        .as("Failed to unescape octal characters via the between method")
        .isEqualTo("\377");

    input = "\\377 and";
    result = oue.transform(input);
    assertThat(result)
        .as("Failed to unescape octal characters via the between method")
        .isEqualTo("\377 and");

    input = "\\378 and";
    result = oue.transform(input);
    assertThat(result)
        .as("Failed to unescape octal characters via the between method")
        .isEqualTo("\37" + "8 and");

    input = "\\378";
    result = oue.transform(input);
    assertThat(result)
        .as("Failed to unescape octal characters via the between method")
        .isEqualTo("\37" + "8");

    input = "\\1";
    result = oue.transform(input);
    assertThat(result)
        .as("Failed to unescape octal characters via the between method")
        .isEqualTo("\1");

    input = "\\036";
    result = oue.transform(input);
    assertThat(result)
        .as("Failed to unescape octal characters via the between method")
        .isEqualTo("\036");

    input = "\\0365";
    result = oue.transform(input);
    assertThat(result)
        .as("Failed to unescape octal characters via the between method")
        .isEqualTo("\036" + "5");

    input = "\\003";
    result = oue.transform(input);
    assertThat(result)
        .as("Failed to unescape octal characters via the between method")
        .isEqualTo("\003");

    input = "\\0003";
    result = oue.transform(input);
    assertThat(result)
        .as("Failed to unescape octal characters via the between method")
        .isEqualTo("\000" + "3");

    input = "\\279";
    result = oue.transform(input);
    assertThat(result)
        .as("Failed to unescape octal characters via the between method")
        .isEqualTo("\279");

    input = "\\999";
    result = oue.transform(input);
    assertThat(result)
        .as("Failed to ignore an out of range octal character via the between method")
        .isEqualTo("\\999");
  }
}
