package coyote.spinneret.responder;


import coyote.spinneret.Status;

/**
 * This responds with a blank page.
 *
 * <p>Useful to handle URLs which should respond, but not return any data.
 */
public class BlankPageResponder extends DefaultResponder {

  /**
   * @see coyote.spinneret.responder.DefaultStreamResponder#getMimeType()
   */
  @Override
  public String getMimeType() {
    return "text/html";
  }


  /**
   * @see coyote.spinneret.responder.DefaultResponder#getStatus()
   */
  @Override
  public Status getStatus() {
    return Status.OK;
  }


  /**
   * @see coyote.spinneret.responder.DefaultResponder#getText()
   */
  @Override
  public String getText() {
    return "<html><body></body></html>";
  }

}