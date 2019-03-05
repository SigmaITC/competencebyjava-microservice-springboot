package se.chriskevin.microservice.springboot;

import java.util.UUID;
import se.sigma.microservice.springboot.model.User;

public class TestUtils {

  public static final String TEST_FIRST_NAME_STRING = "Tessa";
  public static final String TEST_UUID_STRING = "befb5415-8ed2-40d4-b6eb-5c504dc714a4";

  public static final UUID TEST_UUID = UUID.fromString(TEST_UUID_STRING);
  public static final User TEST_USER = new User(TEST_UUID, TEST_FIRST_NAME_STRING);
}
