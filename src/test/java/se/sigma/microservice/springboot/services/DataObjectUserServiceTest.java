package se.sigma.microservice.springboot.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static se.sigma.microservice.springboot.TestUtils.TEST_FIRST_NAME_STRING;
import static se.sigma.microservice.springboot.TestUtils.TEST_ID;

import io.vavr.collection.List;
import io.vavr.control.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.sigma.microservice.springboot.TestUtils;
import se.sigma.microservice.springboot.data.UserStore;
import se.sigma.microservice.springboot.model.User;

class DataObjectUserServiceTest {

  @Test
  @DisplayName("Should return a list of User")
  void verifyGetUsers() {
    final var expected = List.of(TestUtils.TEST_USER);
    final var mockUserStore = mock(UserStore.class);
    when(mockUserStore.getUsers()).thenReturn(expected);
    final var userService = new DataObjectUserService(mockUserStore);

    assertThat(userService.getUsers()).isEqualTo(expected);
  }

  @Test
  @DisplayName("Should return maybe a User")
  void verifyGetUser() {
    final var expected = Option.of(TestUtils.TEST_USER);
    final var mockUserStore = mock(UserStore.class);
    when(mockUserStore.getUser(any(Integer.class))).thenReturn(expected);
    final var userService = new DataObjectUserService(mockUserStore);

    assertThat(userService.getUser(TEST_ID)).isEqualTo(expected);
  }

  @Test
  @DisplayName("Should return maybe the removed User")
  void verifyRemoveUser() {
    final var expected = Option.of(TestUtils.TEST_USER);
    final var mockUserStore = mock(UserStore.class);
    when(mockUserStore.removeUser(any(Integer.class))).thenReturn(expected);
    final var userService = new DataObjectUserService(mockUserStore);

    assertThat(userService.deleteUser(TEST_ID)).isEqualTo(expected);
  }

  @Test
  @DisplayName("Should return maybe the added User")
  void verifyAddUser() {
    final var expected = TestUtils.TEST_USER;
    final var mockUserStore = mock(UserStore.class);
    when(mockUserStore.addUser(any(User.class))).thenReturn(expected);
    final var userService = new DataObjectUserService(mockUserStore);

    assertThat(userService.addUser(new User(TEST_ID, TEST_FIRST_NAME_STRING))).isEqualTo(expected);
  }

  @Test
  @DisplayName("Should return maybe the updated User")
  void verifyUpdateUser() {
    final var expected = Option.of(TestUtils.TEST_USER);
    final var mockUserStore = mock(UserStore.class);
    when(mockUserStore.getUser(any(Integer.class))).thenReturn(expected);
    when(mockUserStore.updateUser(any(Integer.class), any(User.class))).thenReturn(expected);
    final var userService = new DataObjectUserService(mockUserStore);

    assertThat(userService.updateUser(TEST_ID, new User(TEST_ID, TEST_FIRST_NAME_STRING)))
        .isEqualTo(expected);
  }
}
