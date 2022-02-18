package ru.javawebinar.topjava.web;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.web.user.ProfileRestController;

public class UserServlet extends HttpServlet {

  private static final Logger log = getLogger(UserServlet.class);

  ProfileRestController controller;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
    controller = appCtx.getBean(ProfileRestController.class);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.sendRedirect("meals");
  }


  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    log.debug("forward to users");
    String action = request.getParameter("action");

    switch (action == null ? "all" : action) {
      case "change":
        int userId = Integer.parseInt(request.getParameter("userId"));
        controller.change(userId);
        response.sendRedirect("meals");
        break;
      case "all":
      default:
        log.info("Users All");
        request.setAttribute("users", controller.getAll());
        request.getRequestDispatcher("/users.jsp").forward(request, response);
        break;
    }
  }


}
