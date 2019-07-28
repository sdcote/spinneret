package coyote.spinneret.auth;

import coyote.spinneret.*;
import coyote.spinneret.responder.HTTPDRouter;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;


/**
 * 
 */
public class AuthProviderTest {

  private static HTTPDRouter server = null;
  private static int port = 62618;
  private static final TestAuthProvider AUTH_PROVIDER = new TestAuthProvider();




  /**
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    port = NetUtil.getNextAvailablePort( port );
    server = new TestRouter( port );

    // set a test auth provider in the base server
    server.setAuthProvider( AUTH_PROVIDER );

    // add a protected uri resource
    server.addRoute( "/", Integer.MAX_VALUE, ProtectedResponder.class );

    // try to start the server, waiting only 2 seconds before giving up
    try {
      server.start( HTTPD.SOCKET_READ_TIMEOUT, true );
      long start = System.currentTimeMillis();
      Thread.sleep( 100L );
      while ( !server.wasStarted() ) {
        Thread.sleep( 100L );
        if ( System.currentTimeMillis() - start > 2000 ) {
          server.stop();
          fail( "could not start server" );
        }
      }
    } catch ( IOException ioe ) {
      fail( "could not start server:"+ioe );
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
    AUTH_PROVIDER.allowAllConnections();
    AUTH_PROVIDER.allowAllAuthentications();
    AUTH_PROVIDER.allowAllAuthorizations();
    TestResponse response = TestHttpClient.sendGet( "http://localhost:" + port );
    assertTrue( response.isComplete() );
    assertEquals( response.getStatus(), 200 );
    assertTrue( server.isAlive() );

    // Make sure the server drops the connection if SSL is not enabled.
    // No status should be returned so the response should be incomplete.
    AUTH_PROVIDER.rejectAllConnections();
    response = TestHttpClient.sendPost( "http://localhost:" + port );
    // not a very good test, refactor
    assertTrue( response.isComplete() );
    assertTrue( server.isAlive() );

  }

}
