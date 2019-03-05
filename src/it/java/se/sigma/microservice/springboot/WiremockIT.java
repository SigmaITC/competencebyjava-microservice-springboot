package se.sigma.microservice.springboot;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.lanwen.wiremock.ext.WiremockResolver;
import ru.lanwen.wiremock.ext.WiremockUriResolver;

@ExtendWith({WiremockResolver.class, WiremockUriResolver.class})
class WiremockIT {

  @Test
  void shouldInjectWiremock(
      final @WiremockResolver.Wiremock WireMockServer server,
      final @WiremockUriResolver.WiremockUri String uri) {
    // customize(server); // your setup
    // SomeApiClient api = SomeApiClient.connect(uri);

    // Response response = api.call();
    // assertThat(response.headers())
  }
}
