package se.sigma.microservice.springboot.model;

import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Wither;

@AllArgsConstructor
@Getter
@Wither
@EqualsAndHashCode
@ToString
public class User {

  @ApiModelProperty(value = "UUID that identifies a unique user")
  @NonNull
  private final UUID id;

  @ApiModelProperty(value = "Alias for the users real name")
  @NonNull
  private final String username;
}
