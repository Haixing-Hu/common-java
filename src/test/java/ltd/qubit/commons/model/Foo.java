////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import ltd.qubit.commons.reflect.testbed.State;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import static ltd.qubit.commons.lang.DateUtils.BEIJING;
import static ltd.qubit.commons.lang.DateUtils.getDateTime;
import static ltd.qubit.commons.lang.DateUtils.getSqlDate;
import static ltd.qubit.commons.lang.DateUtils.getTime;
import static ltd.qubit.commons.lang.DateUtils.getTimestamp;

public class Foo {
  public boolean m_boolean = true;
  public char m_char = 'x';
  public byte m_byte = (byte) 1;
  public short m_short = (short) 0;
  public int m_int = 100;
  public long m_long = 100000L;
  public float m_float = 3.14f;
  public double m_double = 0.618;
  public Boolean m_Boolean = Boolean.TRUE;
  public Character m_Character = 'y';
  public Byte m_Byte = (byte) -1;
  public Short m_Short = (short) -1;
  public Integer m_Integer = -100;
  public Long m_Long = -10000L;
  public Float m_Float = -3.14f;
  public Double m_Double = -0.618;
  public BigInteger m_BigInteger = new BigInteger("12345678901234567890");
  public BigDecimal m_BigDecimal = new BigDecimal("12345678901234567890.987654321");
  public String m_String = "abc";
  public Instant m_Instant = Instant.parse("2022-01-23T14:31:23Z");
  public LocalDate m_LocalDate = LocalDate.parse("2022-01-23");
  public LocalTime m_LocalTime = LocalTime.parse("20:18:31");
  public LocalDateTime m_LocalDateTime = LocalDateTime.parse("2022-01-23T14:31:23");
  public ZonedDateTime m_ZonedDateTime = ZonedDateTime.parse("2022-01-23T14:31:23+10:00");
  public java.sql.Date m_java_sql_Date = getSqlDate(2022, 1, 23, 14, 31, 23, BEIJING);
  public java.sql.Time m_java_sql_Time = getTime(23, 14, 31);
  public java.sql.Timestamp m_java_sql_Timestamp = getTimestamp(2022, 1, 23, 14, 31, 23, BEIJING);
  public java.util.Date m_java_util_Date = getDateTime(2022, 1, 23, 14, 31, 23, BEIJING);
  public Gender m_Gender = Gender.MALE;
  public State m_State = State.DISABLED;
  public Long[] m_LongArray = {100L, 200L, 300L};
  public String[] m_StringArray = {"a", "ab", "abc", "abcd", "abcde"};
  public Instant[] m_InstantArray = { Instant.parse("2022-01-23T14:31:23Z") };
  public State[] m_StateArray = {State.NORMAL, State.INACTIVE};
  public Foo m_child;

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("m_boolean", m_boolean)
        .append("m_char", m_char)
        .append("m_byte", m_byte)
        .append("m_short", m_short)
        .append("m_int", m_int)
        .append("m_long", m_long)
        .append("m_float", m_float)
        .append("m_double", m_double)
        .append("m_Boolean", m_Boolean)
        .append("m_Character", m_Character)
        .append("m_Byte", m_Byte)
        .append("m_Short", m_Short)
        .append("m_Integer", m_Integer)
        .append("m_Long", m_Long)
        .append("m_Float", m_Float)
        .append("m_Double", m_Double)
        .append("m_BigInteger", m_BigInteger)
        .append("m_BigDecimal", m_BigDecimal)
        .append("m_String", m_String)
        .append("m_Instant", m_Instant)
        .append("m_LocalDate", m_LocalDate)
        .append("m_LocalTime", m_LocalTime)
        .append("m_LocalDateTime", m_LocalDateTime)
        .append("m_ZonedDateTime", m_ZonedDateTime)
        .append("m_java_sql_Date", m_java_sql_Date)
        .append("m_java_sql_Time", m_java_sql_Time)
        .append("m_java_sql_Timestamp", m_java_sql_Timestamp)
        .append("m_java_util_Date", m_java_util_Date)
        .append("m_Gender", m_Gender)
        .append("m_State", m_State)
        .append("m_LongArray", m_LongArray)
        .append("m_StringArray", m_StringArray)
        .append("m_InstantArray", m_InstantArray)
        .append("m_StateArray", m_StateArray)
        .append("m_child", m_child)
        .toString();
  }
}
