package coyote.spinneret;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.InetAddress;

import static org.junit.Assert.assertEquals;


public class HttpSessionHeadersTest extends HttpServerTest {

  private static final String DUMMY_REQUEST_CONTENT = "dummy request content";
  private static final TestTempFileManager TEST_TEMP_FILE_MANAGER = new TestTempFileManager();




  @Test
  public void testHeadersRemoteIp() throws Exception {
    final ByteArrayInputStream inputStream = new ByteArrayInputStream( HttpSessionHeadersTest.DUMMY_REQUEST_CONTENT.getBytes() );
    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    final String[] ipAddresses = { "127.0.0.1", "8.8.8.8", };
    for ( final String ipAddress : ipAddresses ) {
      final InetAddress inetAddress = InetAddress.getByName( ipAddress );
      final HTTPSessionImpl session = testServer.createSession( HttpSessionHeadersTest.TEST_TEMP_FILE_MANAGER, inputStream, outputStream, inetAddress );
      assertEquals( ipAddress, session.getRemoteIpAddress().toString() );
    }
  }

}
