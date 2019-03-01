package se.chriskevin.microservice.springboot.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.sigma.microservice.springboot.data.UserStore;
import se.sigma.microservice.springboot.model.User;

class UserStoreTest {

  @Test
  @DisplayName("Should return a list of users")
  void verifyGetUser() {
    final List<User> actual = new UserStore().getUsers();

    assertEquals(2, actual.size());
  }

  @Test
  @DisplayName("Should remove an existing user")
  void verifyRemoveUserExisting() {
    final UserStore store = new UserStore();
    final User userToRemove = store.getUsers().get(0);
    final Option<User> removedUser = store.removeUser(userToRemove.getId());

    assertEquals(removedUser.getOrNull(), userToRemove);
    assertEquals(store.getUsers().size(), 1);
    assertTrue(store.getUser(userToRemove.getId()).isEmpty());
  }

  @Test
  @DisplayName("Should not remove a non existing user")
  void verifyRemoveUserNonExisting() {
    final UserStore store = new UserStore();
    final User userToRemove = new User(UUID.randomUUID(), "Fail");
    final Option<User> removedUser = store.removeUser(userToRemove.getId());

    assertTrue(removedUser.isEmpty());
    assertEquals(store.getUsers().size(), 2);
  }
}
