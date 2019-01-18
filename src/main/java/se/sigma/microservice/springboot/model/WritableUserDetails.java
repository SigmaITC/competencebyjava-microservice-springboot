package se.sigma.microservice.springboot.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Wither;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Wither
@EqualsAndHashCode
@ToString
public class WritableUserDetails {

  @NonNull private String username;
}
