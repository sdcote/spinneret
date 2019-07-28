package coyote.spinneret.responder;

import coyote.spinneret.Status;


/**
 * Handling error 404 - unrecognized URIs
 */
public class Error404Responder extends DefaultResponder {

  @Override
  public String getMimeType() {
    return "text/html";
  }


  @Override
  public Status getStatus() {
    return Status.NOT_FOUND;
  }


  @Override
  public String getText() {
    return "<html><body><h3>Error 404</h3><p>The requested resource does not exist.</p></body></html>";
  }
}