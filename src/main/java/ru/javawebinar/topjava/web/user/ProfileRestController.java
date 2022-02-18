package ru.javawebinar.topjava.web.user;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.SecurityUtil;

@Controller
public class ProfileRestController extends AbstractUserController {

  public User get() {
    return super.get(SecurityUtil.getAuthUser());
  }

  public void delete() {
    super.delete(SecurityUtil.getAuthUser());
  }

  public void update(User user) {
    super.update(user, SecurityUtil.getAuthUser());
  }
}