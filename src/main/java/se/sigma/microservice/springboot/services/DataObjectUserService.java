package se.sigma.microservice.springboot.services;

import io.vavr.collection.List;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se.sigma.microservice.springboot.data.UserStore;
import se.sigma.microservice.springboot.model.User;

@Service
@AllArgsConstructor
public final class DataObjectUserService implements UserService {

  private final UserStore userStore;

  public List<User> getUsers() {
    return userStore.getUsers();
  }

  public Option<User> getUser(final Integer id) {
    return userStore.getUser(id);
  }

  public Option<User> deleteUser(final Integer id) {
    return userStore.removeUser(id);
  }

  public User addUser(final User user) {
    return userStore.addUser(user);
  }

  public Option<User> updateUser(final Integer integer, final User user) {
    return userStore
        .getUser(integer)
        .map(x -> x.withName(user.getName()))
        .flatMap(x -> userStore.updateUser(integer, x));
  }
}
