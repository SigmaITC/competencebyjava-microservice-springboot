package se.sigma.microservice.springboot.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.vavr.collection.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.sigma.microservice.springboot.exception.ApiError;
import se.sigma.microservice.springboot.model.User;
import se.sigma.microservice.springboot.services.RepositoryUserService;

@RestController
@AllArgsConstructor
public final class UserController {

  private final RepositoryUserService userService;

  @GetMapping(value = "/users")
  @ApiOperation(value = "Fetches all users")
  @ApiResponses(
    value = {
      @ApiResponse(code = 404, message = "No users exist", response = ApiError.class),
      @ApiResponse(code = 500, message = "Technical error", response = ApiError.class)
    }
  )
  public ResponseEntity<List<User>> getUsers() {
    return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
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
  public ResponseEntity<User> getUser(final @ApiParam(value = "An ID") @PathVariable Integer id) {
    return userService
        .getUser(id)
        .map(ResponseEntity::ok)
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
  public ResponseEntity deleteUser(final @ApiParam(value = "An ID") @PathVariable Integer id) {
    return userService
        .deleteUser(id)
        .map(ResponseEntity::ok)
        .getOrElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @ApiOperation(value = "Adds a new user")
  @ApiResponses(
    value = {
      @ApiResponse(code = 400, message = "Invalid input", response = ApiError.class),
      @ApiResponse(code = 500, message = "Technical error", response = ApiError.class)
    }
  )
  @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> addUser(final @RequestBody User user) {
    return new ResponseEntity<>(userService.addUser(user), HttpStatus.OK);
  }

  @ApiOperation(value = "Updates a specific user")
  @ApiResponses(
    value = {
      @ApiResponse(code = 400, message = "Invalid input", response = ApiError.class),
      @ApiResponse(code = 404, message = "No user found with given ID", response = ApiError.class),
      @ApiResponse(code = 500, message = "Technical error", response = ApiError.class)
    }
  )
  @PutMapping(value = "/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> updateUser(
      final @ApiParam(value = "An ID") @PathVariable Integer id, final @RequestBody User user) {
    return userService
        .updateUser(id, user)
        .map(ResponseEntity::ok)
        .getOrElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}
