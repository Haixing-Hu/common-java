////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.testbed;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.Nullable;

import ltd.qubit.commons.annotation.Scale;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * The class of documents.
 * <p>
 * A document has a title, a content, and optional metadata.
 */
public class Document implements Serializable {

  @Serial
  private static final long serialVersionUID = -2822225605125226556L;

  @Nullable
  private String id;

  @Nullable
  private String title;

  private String content;

  @Nullable
  private Map<String, String> metadata;

  @Scale(6)
  @Nullable
  private BigDecimal score;

  public Document() {
    // empty
  }

  public Document(@Nullable final String id, final String content,
      @Nullable final Map<String, String> metadata) {
    this.id = id;
    this.content = content;
    this.metadata = metadata;
  }

  @Nullable
  public String getId() {
    return id;
  }

  public void setId(@Nullable final String id) {
    this.id = id;
  }

  @Nullable
  public String getTitle() {
    return title;
  }

  public void setTitle(@Nullable final String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(final String content) {
    this.content = content;
  }

  @Nullable
  public Map<String, String> getMetadata() {
    return metadata;
  }

  public void setMetadata(@Nullable final Map<String, String> metadata) {
    this.metadata = metadata;
  }

  @Nullable
  public BigDecimal getScore() {
    return score;
  }

  public void setScore(@Nullable final BigDecimal score) {
    this.score = score;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Document other = (Document) o;
    return Equality.equals(id, other.id)
        && Equality.equals(title, other.title)
        && Equality.equals(content, other.content)
        && Equality.equals(metadata, other.metadata)
        && Equality.equals(score, other.score);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, title);
    result = Hash.combine(result, multiplier, content);
    result = Hash.combine(result, multiplier, metadata);
    result = Hash.combine(result, multiplier, score);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("title", title)
        .append("content", content)
        .append("metadata", metadata)
        .append("score", score)
        .toString();
  }
}
