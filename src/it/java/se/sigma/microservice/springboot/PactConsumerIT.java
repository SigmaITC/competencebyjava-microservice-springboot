package se.sigma.microservice.springboot;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.model.RequestResponsePact;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.client.fluent.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "ArticlesProvider", port = "1234")
class PactConsumerIT {

  private Map<String, String> headers =
      MapUtils.putAll(new HashMap<>(), new String[] {"Content-Type", "application/json"});

  @Pact(provider = "ArticlesProvider", consumer = "ArticlesConsumer")
  public RequestResponsePact articles(final PactDslWithProvider builder) {
    return builder
        .given("Pact for Issue 313")
        .uponReceiving("retrieving article data")
        .path("/articles.json")
        .method("GET")
        .willRespondWith()
        .headers(headers)
        .status(200)
        .body(
            new PactDslJsonBody()
                .minArrayLike("articles", 1)
                .object("variants")
                .eachKeyLike("0032")
                .stringType("description", "sample description")
                .closeObject()
                .closeObject()
                .closeObject()
                .closeArray())
        .toPact();
  }

  @Test
  void testArticles(final MockServer mockServer) throws IOException {
    final var httpResponse =
        Request.Get(mockServer.getUrl() + "/articles.json").execute().returnResponse();
    assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
  }
}
