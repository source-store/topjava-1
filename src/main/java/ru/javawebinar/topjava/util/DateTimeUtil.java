package ru.javawebinar.topjava.util;

/*
 * @author Alexandr.Yakubov
 **/

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

  public static String toString(LocalDateTime localDateTime) {
    return localDateTime == null ? "" : localDateTime.format(DATE_TIME_FORMATTER);
  }

  public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
    return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
  }

  public static <T extends Comparable> boolean isBetweenDateTime(T localDate, T startDate, T endDate) {
    return localDate.compareTo(startDate) >= 0 && localDate.compareTo(endDate) <= 0;
  }
}
