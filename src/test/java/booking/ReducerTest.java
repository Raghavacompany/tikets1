package booking;

import static movie_code.jes.test.Test.run;
import static movie_code.jes.util.Configuration.loadDefault;
import static movie_code.jes.util.Streams.fromConfig;
import static booking.Application.createApp;
import static booking.Application.getMongoClient;
import static movie_code.util.Util.tryToDoWithRethrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.typesafe.config.Config;
import java.io.File;
import org.junit.jupiter.api.Test;

class ReducerTest {
  private static final String ENVIRONMENT = "environment";
  private static final String KAFKA = "kafka";

  @Test
  void test() {
    final Config config = loadDefault();

    tryToDoWithRethrow(
        () -> getMongoClient(config),
        client ->
            assertTrue(
                run(
                    new File("src/test/resources").toPath(),
                    fromConfig(config, KAFKA),
                    config.getString(ENVIRONMENT),
                    movie_code.jes.test.Application::report,
                    builder -> createApp(builder, config, client))));
  }
}
