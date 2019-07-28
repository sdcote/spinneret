package coyote.spinneret.responder;


import coyote.spinneret.Status;

public class NotImplementedResponder extends DefaultResponder {

  @Override
  public String getMimeType() {
    return "text/html";
  }




  @Override
  public Status getStatus() {
    return Status.NOT_IMPLEMENTED;
  }




  @Override
  public String getText() {
    return "<html><body><h3>Not implemented</h3><p>The uri is mapped in the router, but no responder is specified.</p></body></html>";
  }
}