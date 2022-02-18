package ru.javawebinar.topjava.web.user;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.web.SecurityUtil;

public abstract class AbstractUserController {

  protected final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private UserService service;

  public List<User> getAll() {
    log.info("getAll");
    return service.getAll();
  }

  public User get(int id) {
    log.info("get {}", id);
    return service.get(id);
  }

  public User create(User user) {
    log.info("create {}", user);
    checkNew(user);
    return service.create(user);
  }

  public void delete(int id) {
    log.info("delete {}", id);
    service.delete(id);
  }

  public void update(User user, int id) {
    log.info("update {} with id={}", user, id);
    assureIdConsistent(user, id);
    service.update(user);
  }

  public User getByMail(String email) {
    log.info("getByEmail {}", email);
    return service.getByEmail(email);
  }

  public void change(int userId) {
    User user = service.change(userId);
    if (user != null) {
      SecurityUtil.setAuthUserId(user.getId());
    }
  }
}