////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.clock;

import ltd.qubit.commons.lang.DateUtils;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static ltd.qubit.commons.lang.DateUtils.MILLISECONDS_PER_SECOND;

/**
 * The mock class of {@link Clock}, which is used for testing.
 *
 * @author Haixing Hu
 */
public class MockClock extends Clock {

  private final AtomicLong millisToAdd = new AtomicLong();
  private final ZoneId zoneId;
  private long millisToAddEachTime = 0;
  private boolean addEveryTime = false;
  private long createTime = System.currentTimeMillis();
  private long epoch = createTime;

  public MockClock() {
    zoneId = DateUtils.UTC.toZoneId();
  }

  public MockClock(final ZoneId zoneId) {
    this.zoneId = zoneId;
  }

  /**
   * Resets this clock to the original state.
   */
  public void reset() {
    millisToAddEachTime = 0;
    addEveryTime = false;
    createTime = System.currentTimeMillis();
    epoch = createTime;
  }

  /**
   * Sets the epoch of this clock.
   *
   * @param epoch
   *     the specified epoch of this clock.
   */
  public void setEpoch(final Instant epoch) {
    setEpoch(epoch.toEpochMilli());
  }

  /**
   * Sets the epoch of this clock.
   *
   * @param epoch
   *     the specified epoch date of this clock.
   * @param zone
   *     the time zone.
   */
  public void setEpoch(final LocalDate epoch, final ZoneId zone) {
    setEpoch(epoch.atStartOfDay(zone).toEpochSecond() * MILLISECONDS_PER_SECOND);
  }

  /**
   * Sets the epoch of this clock.
   *
   * @param epoch
   *     the specified epoch date time of this clock.
   * @param zone
   *     the time zone.
   */
  public void setEpoch(final LocalDateTime epoch, final ZoneId zone) {
    setEpoch(ZonedDateTime.of(epoch, zone).toEpochSecond() * MILLISECONDS_PER_SECOND);
  }

  /**
   * Sets the epoch of this clock.
   *
   * @param epoch
   *     the specified epoch of this clock, in milliseconds from standard
   *     epoch.
   */
  public void setEpoch(final long epoch) {
    createTime = System.currentTimeMillis();
    this.epoch = epoch;
  }

  /**
   * Adds some milliseconds to this clock one time.
   *
   * @param millis
   *     the milliseconds to be added to this clock.
   */
  public void addMills(final long millis) {
    addMills(millis, false);
  }

  /**
   * Adds some milliseconds to this clock.
   *
   * @param millis
   *     the milliseconds to be added to this clock.
   * @param addEveryTime
   *     if this parameter is set to {@code true}, this clock will add the
   *     specified duration every time others try to get time from it.
   */
  public void addMills(final long millis, final boolean addEveryTime) {
    this.millisToAddEachTime = millis;
    this.addEveryTime = addEveryTime;
    millisToAdd.addAndGet(millis);
  }

  /**
   * Adds some duration to this clock one time.
   *
   * @param value
   *     the value of duration to be added to this clock.
   * @param unit
   *     the unit of the duration to be added to this clock.
   */
  public void add(final long value, final TimeUnit unit) {
    add(value, unit, false);
  }

  /**
   * Adds some duration to this clock.
   *
   * @param value
   *     the value of duration to be added to this clock.
   * @param unit
   *     the unit of the duration to be added to this clock.
   * @param addEveryTime
   *     if this parameter is set to {@code true}, this clock will add the
   *     specified duration every time others try to get time from it.
   */
  public void add(final long value, final TimeUnit unit, final boolean addEveryTime) {
    millisToAddEachTime = unit.toMillis(value);
    this.addEveryTime = addEveryTime;
    millisToAdd.addAndGet(millisToAddEachTime);
  }

  @Override
  public long millis() {
    if (addEveryTime) {
      millisToAdd.addAndGet(millisToAddEachTime);
    }
    final long elapsed = System.currentTimeMillis() - createTime; // elapsed since this is created
    return epoch + elapsed + millisToAdd.get();
  }

  @Override
  public ZoneId getZone() {
    return zoneId;
  }

  @Override
  public Clock withZone(final ZoneId zone) {
    return new MockClock(zone);
  }

  @Override
  public Instant instant() {
    return Instant.ofEpochMilli(millis());
  }

  public Instant now() {
    // NOTE: since we store the timestamp in DATETIME type of MySQL
    // the precision should be truncated to second
    // otherwise some unit test may encounter troubles.
    return Instant.ofEpochMilli(millis())
                  .truncatedTo(ChronoUnit.SECONDS);
  }
}
