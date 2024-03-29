package coyote.spinneret;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class PutStreamIntegrationTest extends IntegrationTestBase<PutStreamIntegrationTest.TestServer> {

  @Override
  public TestServer createTestServer() {
    return new TestServer();
  }

  @Test
  public void testSimplePutRequest() throws Exception {
    final String expected = "This HttpPut request has a content-length of 48.";

    final HttpPut httpput = new HttpPut("http://localhost:8192/");
    httpput.setEntity(new ByteArrayEntity(expected.getBytes()));
    final ResponseHandler<String> responseHandler = new BasicResponseHandler();
    final String responseBody = httpclient.execute(httpput, responseHandler);

    assertEquals("PUT:" + expected, responseBody);
  }

  public static class TestServer extends HTTPD {

    public TestServer() {
      super(8192);
    }


    @Override
    public Response serve(final HTTPSession session) {
      final Method method = session.getMethod();
      final Map<String, String> headers = session.getRequestHeaders();
      final int contentLength = Integer.parseInt(headers.get("content-length"));

      byte[] body;
      try {
        final DataInputStream dataInputStream = new DataInputStream(session.getInputStream());
        body = new byte[contentLength];
        dataInputStream.readFully(body, 0, contentLength);
      } catch (final IOException e) {
        return Response.createFixedLengthResponse(Status.INTERNAL_ERROR, MimeType.TEXT.getType(), e.getMessage());
      }

      final String response = String.valueOf(method) + ':' + new String(body);
      return Response.createFixedLengthResponse(response);
    }

  }
}
