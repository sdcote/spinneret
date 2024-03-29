package coyote.spinneret;

import java.util.List;
import java.util.Map;


/**
 *
 */
public class TestingServer extends HTTPD {

  /**
   * @param port
   */
  public TestingServer(final int port) {
    super(port);
    // TODO Auto-generated constructor stub
  }


  private void listItem(final StringBuilder sb, final Map.Entry<String, ? extends Object> entry) {
    sb.append("<li><code><b>").append(entry.getKey()).append("</b> = ").append(entry.getValue()).append("</code></li>");
  }


  @Override
  public Response serve(final HTTPSession session) {
    final Map<String, List<String>> decodedQueryParameters = decodeParameters(session.getQueryParameterString());

    final StringBuilder sb = new StringBuilder();
    sb.append("<html>");
    sb.append("<head><title>Testing Server</title></head>");
    sb.append("<body>");
    sb.append("<h1>Testing Server</h1>");
    sb.append("<p><blockquote><b>URI</b> = ").append(session.getUri()).append("<br />");
    sb.append("<b>Method</b> = ").append(session.getMethod()).append("</blockquote>");
    sb.append("<h3>Headers</h3><p><blockquote>").append(toString(session.getRequestHeaders())).append("</blockquote>");
    sb.append("<h3>Parms</h3><p><blockquote>").append(toString(session.getParms())).append("</blockquote>");
    sb.append("<h3>Parms (multi values?)</h3><p><blockquote>").append(toString(decodedQueryParameters)).append("</blockquote>");

    try {
      final Body body = session.parseBody();
      sb.append("<h3>Entities</h3><p><blockquote>").append(toString(body)).append("</blockquote>");
    } catch (final Exception e) {
      e.printStackTrace();
    }

    sb.append("</body>");
    sb.append("</html>");
    return Response.createFixedLengthResponse(sb.toString());
  }


  private String toString(final Body body) {
    if (body.size() == 0) {
      return "";
    }
    return unsortedList(body);
  }


  private String unsortedList(final Body body) {
    final StringBuilder sb = new StringBuilder();
    sb.append("<ul>");
    for (final Map.Entry<String, ? extends Object> entry : body.entrySet()) {
      listItem(sb, entry);
    }
    sb.append("</ul>");
    return sb.toString();
  }


  private String toString(final Map<String, ? extends Object> map) {
    if (map.size() == 0) {
      return "";
    }
    return unsortedList(map);
  }


  private String unsortedList(final Map<String, ? extends Object> map) {
    final StringBuilder sb = new StringBuilder();
    sb.append("<ul>");
    for (final Map.Entry<String, ? extends Object> entry : map.entrySet()) {
      listItem(sb, entry);
    }
    sb.append("</ul>");
    return sb.toString();
  }

}
