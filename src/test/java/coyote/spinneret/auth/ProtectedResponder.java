package coyote.spinneret.auth;

import coyote.spinneret.HTTPSession;
import coyote.spinneret.MimeType;
import coyote.spinneret.Response;
import coyote.spinneret.Status;
import coyote.spinneret.responder.DefaultResponder;
import coyote.spinneret.responder.Resource;

import java.util.Map;


/**
 *
 */
public class ProtectedResponder extends DefaultResponder {

  @Override
  @Auth(groups = "sysop", requireSSL = true)
  public Response post( Resource resource, Map<String, String> urlParams, HTTPSession session ) {
    return Response.createFixedLengthResponse( getStatus(), getMimeType(), getText() );
  }




  @Override
  @Auth(groups = "devop", requireSSL = false)
  public Response get( Resource resource, Map<String, String> urlParams, HTTPSession session ) {
    return Response.createFixedLengthResponse( getStatus(), getMimeType(), getText() );
  }




  @Override
  public Status getStatus() {
    return Status.OK;
  }




  @Override
  public String getText() {
    return "";
  }




  @Override
  public String getMimeType() {
    return MimeType.JSON.getType();
  }

}
