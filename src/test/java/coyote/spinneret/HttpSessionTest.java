package coyote.spinneret;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;


public class HttpSessionTest extends HttpServerTest {

  private static final String DUMMY_REQUEST_CONTENT = "dummy request content";
  private static final TestTempFileManager TEST_TEMP_FILE_MANAGER = new TestTempFileManager();




  @Test
  public void testSessionRemoteIPAddress() throws UnknownHostException {
    final ByteArrayInputStream inputStream = new ByteArrayInputStream( HttpSessionTest.DUMMY_REQUEST_CONTENT.getBytes() );
    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    final InetAddress inetAddress = InetAddress.getByName( "127.0.0.1" );
    final HTTPSessionImpl session = testServer.createSession( HttpSessionTest.TEST_TEMP_FILE_MANAGER, inputStream, outputStream, inetAddress );
    assertEquals( "127.0.0.1", session.getRemoteIpAddress().toString() );
  }

}
