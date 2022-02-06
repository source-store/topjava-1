package ru.javawebinar.topjava.util;

/*
 * @author Alexandr.Yakubov
 **/

public class ValidationUtil {

  public static <T> T checkNotFoundWithId(T object, int id) {
    checkNotFoundWithId(object != null, id);
    return object;
  }

  public static void checkNotFoundWithId(boolean found, int id) {
    checkNotFound(found, "id=" + id);
  }

  public static <T> T checkNotFound(T object, String msg) {
    checkNotFound(object != null, msg);
    return object;
  }

}
