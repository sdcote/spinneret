package coyote.spinneret.responder;

import coyote.spinneret.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;


/**
 *
 */
public class ResourceResponderParamTest {

  private static TestRouter server = null;
  private static int port = 3232;


  /**
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    port = NetUtil.getNextAvailablePort(port);
    server = new TestRouter(port);

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
    server.addRoute("/params", Integer.MAX_VALUE, ParamResponder.class);
    server.addRoute("/params/:name", Integer.MAX_VALUE, ParamResponder.class);

    try {
      String resource = "http://localhost:" + port + "/params";
      TestResponse response = TestHttpClient.sendGet(resource);
      System.out.println("GET: '" + resource + "' : " + response.getStatus() + ":" + response.getData());
      assertTrue(response.getStatus() == 200);
      assertEquals(ParamResponder.UNSPECIFIED, response.getData());
      assertTrue(server.isAlive());

      String NAME = "Bob";
      resource = "http://localhost:" + port + "/params/" + NAME;
      response = TestHttpClient.sendGet(resource);
      System.out.println("GET: '" + resource + "' : " + response.getStatus() + ":" + response.getData());
      assertTrue(response.getStatus() == 200);
      assertEquals(NAME, response.getData());
      assertTrue(server.isAlive());

    } catch (Exception e) {
      fail(e.getMessage());
    }

  }

}
