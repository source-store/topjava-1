package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

public class UserMealsUtil {

  public static void main(String[] args) {
    List<UserMeal> meals = Arrays.asList(
        new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
        new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
        new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
        new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
        new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
        new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
        new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),
        new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 8, 0), "Ужин", 410)
    );

    System.out.println("\nfilteredByCycles");
    List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
    mealsTo.forEach(System.out::println);
    System.out.println("\nfilteredByStreams");
    filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000).forEach(System.out::println);

  }

  public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
    // TODO return filtered list with excess. Implement by cycles

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    Set<UserMealWithExcess> userSetMealsFiltered = new HashSet<>();
    Map<String, Integer> caloriesInDay = new HashMap<>();

    for (UserMeal userMeal : meals) {
      if (caloriesInDay.containsKey(userMeal.getDateTime().format(formatter))) {
        caloriesInDay.put(userMeal.getDateTime().format(formatter),
            caloriesInDay.get(userMeal.getDateTime().format(formatter)) + userMeal.getCalories());
      } else {
        caloriesInDay.put(userMeal.getDateTime().format(formatter), userMeal.getCalories());
      }
      if (TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
        userSetMealsFiltered.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), false));
      }
    }

    ArrayList<UserMealWithExcess> returnArray = new ArrayList<>();
    for (UserMealWithExcess userMeal : userSetMealsFiltered) {
      returnArray.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
          caloriesInDay.get(userMeal.getDateTime().format(formatter)) <= caloriesPerDay));
    }
    return returnArray;
  }

  public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
    // TODO Implement by streams
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    return meals.stream().filter(u -> (u.getDateTime().toLocalTime().isAfter(startTime) || u.getDateTime().toLocalTime().equals(startTime))
                                   && (u.getDateTime().toLocalTime().isBefore(endTime) || (u.getDateTime().toLocalTime().equals(endTime))))
        .map(u -> new UserMealWithExcess(u.getDateTime(), u.getDescription(), u.getCalories(),
            meals.stream()
                .filter(m -> u.getDateTime().format(formatter).equals(m.getDateTime().format(formatter)))
                .map(UserMeal::getCalories).reduce(Integer::sum).orElse(0) <= caloriesPerDay))
        .collect(Collectors.toList());
  }
}
