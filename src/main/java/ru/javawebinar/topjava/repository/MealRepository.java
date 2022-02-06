package ru.javawebinar.topjava.repository;

/*
 * @author Alexandr.Yakubov
 **/

import java.util.Collection;
import ru.javawebinar.topjava.model.Meal;

public interface MealRepository {

  Meal save(Meal meal);

  boolean delete(int id);

  Meal get(int id);

  Collection<Meal> getAll();


  void load();
}
