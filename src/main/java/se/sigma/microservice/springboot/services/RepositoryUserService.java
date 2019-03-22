package se.sigma.microservice.springboot.services;

import io.vavr.collection.List;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import se.sigma.microservice.springboot.data.repository.UserRepository;
import se.sigma.microservice.springboot.model.User;

@Service
@CacheConfig(cacheNames = {"users"})
@AllArgsConstructor
public class RepositoryUserService implements UserService {

  private final UserRepository userRepository;

  @CacheEvict(allEntries = true)
  public void clearCache() {}

  @Override
  public List<User> getUsers() {
    return List.ofAll(userRepository.findAll());
  }

  @Cacheable(key = "#id")
  @Override
  public Option<User> getUser(final Integer id) {
    return Option.ofOptional(userRepository.findById(id));
  }

  @Override
  public Option<User> deleteUser(final Integer id) {
    final var maybeUser = Option.ofOptional(userRepository.findById(id));
    maybeUser.forEach(userRepository::delete);
    return maybeUser;
  }

  @Override
  public User addUser(final User user) {
    return userRepository.save(user);
  }

  @Override
  public Option<User> updateUser(final Integer id, User user) {
    final var maybeUser = Option.ofOptional(userRepository.findById(id));
    maybeUser.forEach(userRepository::save);
    return maybeUser;
  }
}
