package ru.javawebinar.topjava.web;

/*
 * @author Alexandr.Yakubov
 **/

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

public class MealServlet extends HttpServlet {

  private static final Logger log = getLogger(MealServlet.class);

  private MealRepository repository;

  @Override
  public void init() {
    repository = new InMemoryMealRepository();
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    String id = request.getParameter("id");

    Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
        LocalDateTime.parse(request.getParameter("dateTime")),
        request.getParameter("description"),
        Integer.parseInt(request.getParameter("calories")));

    log.info(meal.getId() == null ? "Create {}" : "Update {}", meal);
    repository.save(meal);
    response.sendRedirect("meals");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    log.debug("redirect to meals");
    String action = request.getParameter("action");

//    response.sendRedirect("meals.jsp");

    switch (action == null ? "all" : action) {
      case "delete":
        int id = getId(request);
        log.info("Delete {}", id);
        repository.delete(id);
        response.sendRedirect("meals");
        break;
      case "create":
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
        break;
      case "update":
        int idForUpdate = getId(request);
        request.setAttribute("meal", repository.get(idForUpdate));
        request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
        break;
      case "all":
      default:
        log.info("getAll");
        request.setAttribute("meals", MealsUtil.getTos(repository.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
        break;
    }

  }

  private int getId(HttpServletRequest request) {
    String paramId = Objects.requireNonNull(request.getParameter("id"));
    return Integer.parseInt(paramId);
  }

}