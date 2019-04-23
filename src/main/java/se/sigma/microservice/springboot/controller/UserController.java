package se.sigma.microservice.springboot.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.vavr.collection.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.sigma.microservice.springboot.exception.ApiError;
import se.sigma.microservice.springboot.model.User;
import se.sigma.microservice.springboot.model.WritableUserDetails;
import se.sigma.microservice.springboot.services.UserService;

@RestController
@AllArgsConstructor
public final class UserController {

  private final UserService userService;

  @CrossOrigin(origins = "http://localhost:8081")
  @GetMapping(value = "/users")
  @ApiOperation(value = "Fetches all users")
  @ApiResponses(
    value = {
      @ApiResponse(code = 404, message = "No users exist", response = ApiError.class),
      @ApiResponse(code = 500, message = "Technical error", response = ApiError.class),
      @ApiResponse(code = 200, message = "Fetch all user success")
    }
  )
  public ResponseEntity<List<User>> getUsers() {
    return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
  }

  @CrossOrigin(origins = "http://localhost:8081")
  @GetMapping(value = "/users/{id}")
  @ApiOperation(value = "Fetches a specific user")
  @ApiResponses(
    value = {
      @ApiResponse(code = 400, message = "Invalid input", response = ApiError.class),
      @ApiResponse(code = 404, message = "No user found with given ID", response = ApiError.class),
      @ApiResponse(code = 500, message = "Technical error", response = ApiError.class),
      @ApiResponse(code = 200, message = "Get user success")
    }
  )
  public ResponseEntity<User> getUser(final @ApiParam(value = "A UUID") @PathVariable UUID id) {
    return userService
        .getUser(id)
        .map(ResponseEntity::ok)
        .getOrElse(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @CrossOrigin(origins = "http://localhost:8081")
  @ApiOperation(value = "Deletes a specific user")
  @ApiResponses(
    value = {
      @ApiResponse(code = 400, message = "Invalid input", response = ApiError.class),
      @ApiResponse(code = 404, message = "No user found with given ID", response = ApiError.class),
      @ApiResponse(code = 500, message = "Technical error", response = ApiError.class),
      @ApiResponse(code = 200, message = "Delete user success")
    }
  )
  @DeleteMapping(value = "/users/{id}")
  public ResponseEntity deleteUser(final @ApiParam(value = "A UUID") @PathVariable UUID id) {
    return userService
        .deleteUser(id)
        .map(ResponseEntity::ok)
        .getOrElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @CrossOrigin(origins = "http://localhost:8081")
  @ApiOperation(value = "Adds a new user")
  @ApiResponses(
    value = {
      @ApiResponse(code = 400, message = "Invalid input", response = ApiError.class),
      @ApiResponse(code = 500, message = "Technical error", response = ApiError.class),
      @ApiResponse(code = 200, message = "Add user success")
    }
  )
  @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> addUser(final @RequestBody WritableUserDetails userDetails) {
    return new ResponseEntity<>(userService.addUser(userDetails), HttpStatus.OK);
  }

  @CrossOrigin(origins = "http://localhost:8081")
  @ApiOperation(value = "Updates a specific user")
  @ApiResponses(
    value = {
      @ApiResponse(code = 400, message = "Invalid input", response = ApiError.class),
      @ApiResponse(code = 404, message = "No user found with given ID", response = ApiError.class),
      @ApiResponse(code = 500, message = "Technical error", response = ApiError.class),
      @ApiResponse(code = 200, message = "Update user success")
    }
  )
  @PutMapping(value = "/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> updateUser(
      final @ApiParam(value = "A UUID") @PathVariable UUID id,
      final @RequestBody WritableUserDetails userDetails) {
    return userService
        .updateUser(id, userDetails)
        .map(ResponseEntity::ok)
        .getOrElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}
