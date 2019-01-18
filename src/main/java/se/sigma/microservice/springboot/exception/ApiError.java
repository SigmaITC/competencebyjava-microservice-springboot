package se.sigma.microservice.springboot.exception;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@ToString
public class ApiError {

  @ApiModelProperty(value = "A list of errors that occurred")
  @NonNull
  private final List<String> errors;

  @ApiModelProperty(value = "A message explaining the general error")
  @NonNull
  private final String message;

  @ApiModelProperty(value = "HTTP status code")
  @NonNull
  private final HttpStatus status;

  @ApiModelProperty(value = "A timestamp from when the error was caught")
  @NonNull
  private final LocalDateTime timestamp;
}
