package ru.javawebinar.topjava.repository.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

@Repository
public class InMemoryUserRepository implements UserRepository {

  private final Map<Integer, User> repository = new ConcurrentHashMap<>();
  private final AtomicInteger counter = new AtomicInteger(0);

  {
    save(new User(1, "admin1", "admin1@ya.ru", "admin1", Role.ADMIN));
    save(new User(2, "user1", "user1@ya.ru", "user1", Role.USER));
    save(new User(2, "user2", "user2@ya.ru", "user2", Role.USER));
  }


  private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);


  @Override
  public boolean delete(int id) {
    return repository.remove(id) != null;
  }

  @Override
  public User save(User user) {
    if (user.isNew()) {
      user.setId(counter.incrementAndGet());
    }
    repository.put(user.getId(), user);
    return user;
  }

  @Override
  public User get(int id) {
    return repository.get(id);
  }

  @Override
  public List<User> getAll() {
    return new ArrayList<>(repository.values());
  }

  @Override
  public User getByEmail(String email) {
    return repository.values().stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
  }
}
