package coyote.spinneret;


import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class InvalidRequestTest extends HttpServerTest {

  @Test
  public void testGetRequestWithoutProtocol() {
    invokeServer( "GET " + HttpServerTest.URI + "\r\nX-Important-Header: foo" );

    assertNotNull( testServer.parms );
    assertTrue( testServer.header.size() > 0 );
    assertNotNull( testServer.body );
    assertNotNull( testServer.uri );
  }




  @Test
  public void testGetRequestWithProtocol() {
    invokeServer( "GET " + HttpServerTest.URI + " HTTP/1.1\r\nX-Important-Header: foo" );

    assertNotNull( testServer.parms );
    assertTrue( testServer.header.size() > 0 );
    assertNotNull( testServer.body );
    assertNotNull( testServer.uri );
  }




  @Test
  public void testPostRequestWithoutProtocol() {
    invokeServer( "POST " + HttpServerTest.URI + "\r\nContent-Length: 123" );
    assertNotNull( testServer.parms );
    assertTrue( testServer.header.size() > 0 );
    assertNotNull( testServer.body );
    assertNotNull( testServer.uri );
  }




  @Test
  public void testPostRequestWithProtocol() {
    invokeServer( "POST " + HttpServerTest.URI + " HTTP/1.1\r\nContent-Length: 123" );
    assertNotNull( testServer.parms );
    assertTrue( testServer.header.size() > 0 );
    assertNotNull( testServer.body );
    assertNotNull( testServer.uri );
  }

}
