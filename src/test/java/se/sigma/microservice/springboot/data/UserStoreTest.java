package se.sigma.microservice.springboot.data;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sigma.microservice.springboot.TestUtils.TEST_FIRST_NAME_STRING;
import static se.sigma.microservice.springboot.TestUtils.TEST_ID;

import io.vavr.collection.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.sigma.microservice.springboot.TestUtils;
import se.sigma.microservice.springboot.model.User;

class UserStoreTest {

  static final Integer NON_EXISTING_ID = 0;

  @Test
  @DisplayName("Should return a list of users")
  void verifyGetUsers() {
    final var actual = new UserStore().getUsers();

    assertThat(actual).hasSize(2);
  }

  @Test
  @DisplayName("Should return Some(User")
  void verifyGetUser() {
    final var store = new UserStore();
    final var actual = store.getUser(TEST_ID);

    assertThat(actual.get()).isEqualTo(store.getUsers().head());
  }

  @Test
  @DisplayName("Should return None")
  void verifyGetUserNone() {
    final var actual = new UserStore().getUser(NON_EXISTING_ID);

    assertThat(actual).isEmpty();
  }

  @Test
  @DisplayName("Should add the user and return it")
  void verifyAddUser() {
    final var store = new UserStore();
    final var addedUser = store.addUser(TestUtils.TEST_USER);
    final var users = store.getUsers();

    assertThat(addedUser).isEqualTo(TestUtils.TEST_USER);
    assertThat(users).hasSize(3);
    assertThat(users).contains(TestUtils.TEST_USER);
  }

  @Test
  @DisplayName("Should remove an existing user")
  void verifyRemoveUserExisting() {
    final var store = new UserStore();
    final var userToRemove = store.getUsers().get(0);
    final var removedUser = store.removeUser(userToRemove.getId());

    assertThat(userToRemove).isEqualTo(removedUser.getOrNull());
    assertThat(store.getUsers()).hasSize(1);
    assertThat(store.getUser(userToRemove.getId())).isEmpty();
  }

  @Test
  @DisplayName("Should not remove a non existing user")
  void verifyRemoveUserNonExisting() {
    final var store = new UserStore();
    final var removedUser = store.removeUser(NON_EXISTING_ID);

    assertThat(removedUser).isEmpty();
    assertThat(store.getUsers()).hasSize(2);
  }

  @Test
  @DisplayName("Should update an existing user")
  void verifyUpdateUserExisting() {
    final var store = new UserStore();
    final var originalUser = store.getUsers().get(0);
    final var userToUpdate = store.getUsers().get(0).withName(TEST_FIRST_NAME_STRING);
    final var updatedUser = store.updateUser(userToUpdate.getId(), userToUpdate);

    assertThat(updatedUser.getOrNull()).isEqualTo(originalUser);
    assertThat(store.getUsers()).hasSize(2);
    assertThat(store.getUser(userToUpdate.getId()).getOrNull()).isEqualTo(userToUpdate);
  }

  @Test
  @DisplayName("Should not update a non existing user")
  void verifyUpdateUserNonExisting() {
    final var store = new UserStore();
    final var originalUsers = List.ofAll(store.getUsers());
    final var userToUpdate = new User().withName(TEST_FIRST_NAME_STRING);
    final var updatedUser = store.updateUser(NON_EXISTING_ID, userToUpdate);

    assertThat(updatedUser).isEmpty();
    assertThat(store.getUsers()).hasSize(2);
    assertThat(store.getUsers()).isEqualTo(originalUsers);
  }
}
