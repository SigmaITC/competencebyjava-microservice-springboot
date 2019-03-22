package se.sigma.microservice.springboot.data.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sigma.microservice.springboot.TestUtils.TEST_FIRST_NAME_STRING;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.sigma.microservice.springboot.model.User;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserRepositoryIT {

  @Autowired private UserRepository userRepository;

  @Test
  @DisplayName("Should save user to DB")
  void verifySaveSuccess() {
    final var user = new User().withName(TEST_FIRST_NAME_STRING);

    assertThat(user.getId()).isNull();
    userRepository.save(user);
    assertThat(user.getId()).isNotNull();

    var fetchedUser = userRepository.findById(user.getId());

    assertThat(fetchedUser).isNotEmpty();
    assertThat(fetchedUser.map(User::getId).orElse(null)).isEqualTo(user.getId());

    // count in DB should be one
    assertThat(userRepository.count()).isOne();

    // get all, should contain one
    final var users = userRepository.findAll();
    int count = 0;

    for (final var x : users) {
      count++;
    }

    assertThat(count).isOne();
  }
}
