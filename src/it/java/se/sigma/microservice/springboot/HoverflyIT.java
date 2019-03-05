package se.sigma.microservice.springboot;

import static io.specto.hoverfly.junit.core.SimulationSource.dsl;
import static io.specto.hoverfly.junit.dsl.HoverflyDsl.service;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.created;

import io.specto.hoverfly.junit.core.Hoverfly;
import io.specto.hoverfly.junit.core.HoverflyMode;
import io.specto.hoverfly.junit5.HoverflyExtension;
import io.specto.hoverfly.junit5.api.HoverflyConfig;
import io.specto.hoverfly.junit5.api.HoverflyCore;
import io.specto.hoverfly.junit5.api.HoverflySimulate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@HoverflyCore(
  mode = HoverflyMode.CAPTURE,
  config = @HoverflyConfig(adminPort = 9000, proxyPort = 9001)
)
@HoverflySimulate(
  source =
      @HoverflySimulate.Source(
        value = "target/resources/test/hoverfly/missing-simulation.json",
        type = HoverflySimulate.SourceType.FILE
      ),
  enableAutoCapture = true
)
@ExtendWith(HoverflyExtension.class)
class HoverflyIT {

  @Test
  void shouldDoSomethingWith(final Hoverfly hoverfly) {
    hoverfly.simulate(
        dsl(
            service("www.my-test.com")
                .post("/api/bookings")
                .body("{\"flightId\": \"1\"}")
                .willReturn(created("http://localhost/api/bookings/1"))));

    // ...
  }
}
