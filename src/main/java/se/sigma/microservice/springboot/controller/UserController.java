package se.sigma.microservice.springboot.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.sigma.microservice.springboot.exception.ApiError;
import se.sigma.microservice.springboot.model.User;
import se.sigma.microservice.springboot.model.WritableUserDetails;
import se.sigma.microservice.springboot.services.UserService;

@RestController
@AllArgsConstructor
public class UserController {

  private UserService userService;

  @GetMapping(value = "/users")
  @ApiOperation(value = "Fetches all users")
  @ApiResponses(
    value = {
      @ApiResponse(code = 404, message = "No users exist", response = ApiError.class),
      @ApiResponse(code = 500, message = "Technical error", response = ApiError.class)
    }
  )
  public ResponseEntity<List<User>> getUsers() {
    return new ResponseEntity<>(userService.getUsers().toJavaList(), HttpStatus.OK);
  }

  @GetMapping(value = "/users/{id}")
  @ApiOperation(value = "Fetches a specific user")
  @ApiResponses(
    value = {
      @ApiResponse(code = 400, message = "Invalid input", response = ApiError.class),
      @ApiResponse(code = 404, message = "No user found with given ID", response = ApiError.class),
      @ApiResponse(code = 500, message = "Technical error", response = ApiError.class)
    }
  )
  public ResponseEntity<User> getUser(final @ApiParam(value = "A UUID") @PathVariable UUID id) {
    final ApiError apiError =
        new ApiError(
            Collections.emptyList(),
            "No user with ID" + id,
            HttpStatus.NOT_FOUND,
            LocalDateTime.now());

    return userService
        .getUser(id)
        .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
        .getOrElse(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @ApiOperation(value = "Deletes a specific user")
  @ApiResponses(
    value = {
      @ApiResponse(code = 400, message = "Invalid input", response = ApiError.class),
      @ApiResponse(code = 404, message = "No user found with given ID", response = ApiError.class),
      @ApiResponse(code = 500, message = "Technical error", response = ApiError.class)
    }
  )
  @DeleteMapping(value = "/users/{id}")
  public void deleteUser(final @ApiParam(value = "A UUID") @PathVariable UUID id) {
    userService.deleteUser(id);
  }

  @ApiOperation(value = "Adds a new user")
  @ApiResponses(
    value = {
      @ApiResponse(code = 400, message = "Invalid input", response = ApiError.class),
      @ApiResponse(code = 500, message = "Technical error", response = ApiError.class)
    }
  )
  @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> addUser(final @RequestBody WritableUserDetails userDetails) {
    return new ResponseEntity<>(userService.addUser(userDetails), HttpStatus.OK);
  }
}
