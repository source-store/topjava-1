package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MealTo {

  public Integer getId() {
    return id;
  }

  public boolean isExcess() {
    return excess;
  }

  private final Integer id;

  private final LocalDateTime dateTime;

  private final String description;

  private final int calories;

  private final boolean excess;

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public String getDescription() {
    return description;
  }

  public int getCalories() {
    return calories;
  }

  public LocalDate getDate() {
    return dateTime.toLocalDate();
  }

  public LocalTime getTime() {
    return dateTime.toLocalTime();
  }

  public MealTo(Integer id, LocalDateTime dateTime, String description, int calories, boolean excess) {
    this.id = id;
    this.dateTime = dateTime;
    this.description = description;
    this.calories = calories;
    this.excess = excess;
  }

  @Override
  public String toString() {
    return "MealTo{" +
        "dateTime=" + dateTime +
        ", description='" + description + '\'' +
        ", calories=" + calories +
        ", excess=" + excess +
        '}';
  }
}
