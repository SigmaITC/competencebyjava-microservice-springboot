package se.sigma.microservice.springboot.stepdef;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import cucumber.api.java8.En;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public final class UserStepDefs implements En {

  private static final String LOCALHOST = "http://localhost:8080";

  private static final String REGEX_HTTP_STATUS_CODE = "\\d{3}";
  private static final String REGEX_PATH = "/([a-zA-Z0-9]-?/?)*";
  private static final String REGEX_REQUEST_PARAMS = "(\\?([a-zA-Z0-9]\\=[a-zA-Z0-9])+)?";

  private Response response;

  public UserStepDefs() {

    Given(
        "^REST URL <\"(" + REGEX_PATH + ")\">$",
        (final String path) -> {
          RestAssured.baseURI = LOCALHOST;
          RestAssured.basePath = path;
        });

    When("^GET request is sent$", () -> response = given().contentType("application/json").get());

    Then(
        "^response status should be <(" + REGEX_HTTP_STATUS_CODE + ")>",
        (final Integer expected) -> {
          assertThat(response.getStatusCode()).isEqualTo(expected);
        });

    Then(
        "^response body should match$",
        (final String expected) -> {
          assertThat(response.getBody().print()).isEqualTo(expected);
        });
  }
}
