package coyote.spinneret.responder;

import coyote.spinneret.HTTPSession;
import coyote.spinneret.Response;
import coyote.spinneret.Status;

import java.io.InputStream;
import java.util.Map;


/**
 * General responder to subclass when you provide text or html data. Only fixed
 * size responses will be generated.
 */
public abstract class DefaultResponder extends DefaultStreamResponder {

  @Override
  public Response get(final Resource resource, final Map<String, String> urlParams, final HTTPSession session) {
    return Response.createFixedLengthResponse(getStatus(), getMimeType(), getText());
  }


  @Override
  public InputStream getData() {
    throw new IllegalStateException("This method should not be called in a text based responder");
  }


  @Override
  public abstract Status getStatus();


  public abstract String getText();
}