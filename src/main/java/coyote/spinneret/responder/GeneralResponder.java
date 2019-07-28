package coyote.spinneret.responder;

import coyote.spinneret.HTTPSession;
import coyote.spinneret.Response;
import coyote.spinneret.Status;

import java.util.Map;

/**
 * Generic responder to print debug info as a html page.
 */
public class GeneralResponder extends DefaultResponder {

  @Override
  public Response get(final Resource resource, final Map<String, String> urlParams, final HTTPSession session) {
    final StringBuilder text = new StringBuilder("<html><body>");
    text.append("<h1>Url: ");
    text.append(session.getUri());
    text.append("</h1><br>");
    final Map<String, String> queryParams = session.getParms();
    if (queryParams.size() > 0) {
      for (final Map.Entry<String, String> entry : queryParams.entrySet()) {
        final String key = entry.getKey();
        final String value = entry.getValue();
        text.append("<p>Param '");
        text.append(key);
        text.append("' = ");
        text.append(value);
        text.append("</p>");
      }
    } else {
      text.append("<p>no params in url</p><br>");
    }
    return Response.createFixedLengthResponse(getStatus(), getMimeType(), text.toString());
  }


  @Override
  public String getMimeType() {
    return "text/html";
  }


  @Override
  public Status getStatus() {
    return Status.OK;
  }


  @Override
  public String getText() {
    throw new IllegalStateException("This method should not be called");
  }

}