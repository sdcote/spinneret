package coyote.spinneret.auth;

import coyote.spinneret.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 *
 */
public class AuthAnnotationTest {

  private static TestRouter server = null;
  private static int port = 3233;


  /**
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    port = NetUtil.getNextAvailablePort(port);
    server = new TestRouter(port);
    server.addDefaultRoutes();

    // try to start the server, waiting only 2 seconds before giving up
    try {
      server.start(HTTPD.SOCKET_READ_TIMEOUT, true);
      long start = System.currentTimeMillis();
      Thread.sleep(100L);
      while (!server.wasStarted()) {
        Thread.sleep(100L);
        if (System.currentTimeMillis() - start > 2000) {
          server.stop();
          fail("could not start server");
        }
      }
    } catch (IOException ioe) {
      fail("could not start server");
    }
  }


  /**
   * @throws Exception
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    server.stop();
  }


  @Test
  public void test() {
    // setup a responder we can call which will post results into the response
    server.addRoute("/", Integer.MAX_VALUE, ProtectedResponder.class);

    try {
      TestResponse response = TestHttpClient.sendGet("http://localhost:" + port);
      System.out.println(response.getStatus() + ":" + response.getData());
      assertTrue(response.getStatus() == 200);
      assertTrue(server.isAlive());

      response = TestHttpClient.sendGet("http://localhost:" + port + "/");
      System.out.println(response.getStatus() + ":" + response.getData());
      assertTrue(response.getStatus() == 200);
      assertTrue(server.isAlive());

    } catch (Exception e) {
      fail(e.getMessage());
    }

  }

}
