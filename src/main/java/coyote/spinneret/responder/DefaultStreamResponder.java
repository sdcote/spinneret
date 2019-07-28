package coyote.spinneret.responder;

import coyote.spinneret.HTTPSession;
import coyote.spinneret.Response;
import coyote.spinneret.Status;

import java.io.InputStream;
import java.util.Map;

/**
 * General responder to subclass when you provide stream data. Only chucked
 * responses will be generated.
 */
public abstract class DefaultStreamResponder implements Responder {

  @Override
  public Response delete(final Resource resource, final Map<String, String> urlParams, final HTTPSession session) {
    return get(resource, urlParams, session);
  }


  @Override
  public Response get(final Resource resource, final Map<String, String> urlParams, final HTTPSession session) {
    return Response.createChunkedResponse(getStatus(), getMimeType(), getData());
  }


  public abstract InputStream getData();


  public abstract String getMimeType();


  public abstract Status getStatus();


  @Override
  public Response other(final String method, final Resource resource, final Map<String, String> urlParams, final HTTPSession session) {
    return get(resource, urlParams, session);
  }


  @Override
  public Response post(final Resource resource, final Map<String, String> urlParams, final HTTPSession session) {
    return get(resource, urlParams, session);
  }


  @Override
  public Response put(final Resource resource, final Map<String, String> urlParams, final HTTPSession session) {
    return get(resource, urlParams, session);
  }
}