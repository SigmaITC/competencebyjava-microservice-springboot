package se.sigma.microservice.springboot.model;

import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import lombok.*;
import lombok.experimental.Wither;

@Entity
@Table(name = "user")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Wither
@EqualsAndHashCode
@ToString
public class User {

  @ApiModelProperty(value = "UUID that identifies a unique user")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private final Integer id;

  @ApiModelProperty(value = "Alias for the users real name")
  @NonNull
  @Column(name = "name")
  private final String name;
}
