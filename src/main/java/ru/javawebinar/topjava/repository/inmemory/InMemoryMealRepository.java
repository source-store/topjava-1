package ru.javawebinar.topjava.repository.inmemory;

/*
 * @author Alexandr.Yakubov
 **/

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

public class InMemoryMealRepository implements MealRepository {

  private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
  private final AtomicInteger counter = new AtomicInteger(0);

  {
    MealsUtil.meals.forEach(this::save);
  }

  @Override
  public Meal save(Meal meal) {
    if (meal.getId() == null) {
      meal.setId(counter.incrementAndGet());
      repository.put(meal.getId(), meal);
      return meal;
    }
    return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
  }

  @Override
  public boolean delete(int id) {
    return repository.remove(id) != null;
  }

  @Override
  public Meal get(int id) {
    return repository.get(id);
  }

  @Override
  public Collection<Meal> getAll() {
    return repository.values();
  }

  @Override
  public void load() {
    for (Meal meal : MealsUtil.meals) {
      meal.setId(counter.incrementAndGet());
      repository.put(meal.getId(), meal);
    }
  }
}
