package se.sigma.microservice.springboot.services;

import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se.sigma.microservice.springboot.data.UserStore;
import se.sigma.microservice.springboot.model.User;
import se.sigma.microservice.springboot.model.WritableUserDetails;

@Service
@AllArgsConstructor
public final class UserService {

  private final UserStore userStore;

  public List<User> getUsers() {
    return userStore.getUsers();
  }

  public Option<User> getUser(final UUID uuid) {
    return userStore.getUser(uuid);
  }

  public Option<User> deleteUser(final UUID uuid) {
    return userStore.removeUser(uuid);
  }

  public User addUser(final WritableUserDetails userDetails) {
    return userStore.addUser(new User(UUID.randomUUID(), userDetails.getUsername()));
  }

  public Option<User> updateUser(final UUID uuid, final WritableUserDetails userDetails) {
    return userStore
            .getUser(uuid)
            .map(user -> user.withUsername(userDetails.getUsername()))
            .flatMap(user -> userStore.updateUser(uuid, user));
  }
}
