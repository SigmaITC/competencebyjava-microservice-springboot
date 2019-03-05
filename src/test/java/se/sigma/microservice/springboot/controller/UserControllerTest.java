package se.sigma.microservice.springboot.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import se.sigma.microservice.springboot.TestUtils;
import se.sigma.microservice.springboot.model.User;
import se.sigma.microservice.springboot.model.WritableUserDetails;
import se.sigma.microservice.springboot.services.UserService;

class UserControllerTest {

  @Test
  @DisplayName("Should return a response with status 200 containing a list of User")
  void verifyGetUsersStatus200() {
    final var expected = List.of(TestUtils.TEST_USER);
    final var mockUserService = mock(UserService.class);
    when(mockUserService.getUsers()).thenReturn(expected);
    final var userService = new UserController(mockUserService);

    final var actual = userService.getUsers();

    assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(actual.getBody()).isEqualTo(expected);
  }

  @Test
  @DisplayName("Should return a response with status 200 containing a User")
  void verifyGetUserStatus200() {
    final var expected = Option.of(TestUtils.TEST_USER);
    final var mockUserService = mock(UserService.class);
    when(mockUserService.getUser(any(UUID.class))).thenReturn(expected);
    final var userService = new UserController(mockUserService);

    final var actual = userService.getUser(TestUtils.TEST_UUID);

    assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(actual.getBody()).isEqualTo(expected.getOrNull());
  }

  @Test
  @DisplayName("Should return a response with status 404")
  void verifyGetUserStatus404() {
    final Option<User> expected = Option.none();
    final var mockUserService = mock(UserService.class);
    when(mockUserService.getUser(any(UUID.class))).thenReturn(expected);
    final var userService = new UserController(mockUserService);

    final var actual = userService.getUser(TestUtils.TEST_UUID);

    assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  @DisplayName("Should return a response with status 200 containing the removed User")
  void verifyRemoveUserStatus200() {
    final var expected = Option.of(TestUtils.TEST_USER);
    final var mockUserService = mock(UserService.class);
    when(mockUserService.deleteUser(any(UUID.class))).thenReturn(expected);
    final var userService = new UserController(mockUserService);

    final var actual = userService.deleteUser(TestUtils.TEST_UUID);

    assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(actual.getBody()).isEqualTo(expected.getOrNull());
  }

  @Test
  @DisplayName("Should return a response with status 404")
  void verifyRemoveUserStatus404() {
    final Option<User> expected = Option.none();
    final var mockUserService = mock(UserService.class);
    when(mockUserService.deleteUser(any(UUID.class))).thenReturn(expected);
    final var userService = new UserController(mockUserService);

    final var actual = userService.deleteUser(TestUtils.TEST_UUID);

    assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  @DisplayName("Should return a response with status 200 containing the added User")
  void verifyAddUserStatus200() {
    final var expected = TestUtils.TEST_USER;
    final var mockUserService = mock(UserService.class);
    when(mockUserService.addUser(any(WritableUserDetails.class))).thenReturn(expected);
    final var userService = new UserController(mockUserService);

    final var actual = userService.addUser(new WritableUserDetails(TestUtils.TEST_FIRST_NAME_STRING));

    assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(actual.getBody()).isEqualTo(expected);
  }

  @Test
  @DisplayName("Should return a response with status 200 containing the updated User")
  void verifyUpdateUserStatus200() {
    final var expected = Option.of(TestUtils.TEST_USER);
    final var mockUserService = mock(UserService.class);
    when(mockUserService.getUser(any(UUID.class))).thenReturn(expected);
    when(mockUserService.updateUser(any(UUID.class), any(WritableUserDetails.class)))
        .thenReturn(expected);
    final var userService = new UserController(mockUserService);

    final var actual =
        userService.updateUser(TestUtils.TEST_UUID, new WritableUserDetails(TestUtils.TEST_FIRST_NAME_STRING));

    assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(actual.getBody()).isEqualTo(expected.getOrNull());
  }

  @Test
  @DisplayName("Should return a response with status 404")
  void verifyUpdateUserStatus404() {
    final Option<User> expected = Option.none();
    final var mockUserService = mock(UserService.class);
    when(mockUserService.getUser(any(UUID.class))).thenReturn(expected);
    when(mockUserService.updateUser(any(UUID.class), any(WritableUserDetails.class)))
        .thenReturn(expected);
    final var userService = new UserController(mockUserService);

    final var actual =
        userService.updateUser(TestUtils.TEST_UUID, new WritableUserDetails(TestUtils.TEST_FIRST_NAME_STRING));

    assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }
}
