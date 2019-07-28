package coyote.spinneret.wsd;

import coyote.spinneret.wsd.WebSocketFrame.CloseCode;

import java.io.IOException;


public class WebSocketException extends IOException {

  private static final long serialVersionUID = 777960923156306743L;
  private final WebSocketFrame.CloseCode code;
  private final String reason;


  public WebSocketException(final Exception cause) {
    this(CloseCode.InternalServerError, cause.toString(), cause);
  }


  public WebSocketException(final WebSocketFrame.CloseCode code, final String reason) {
    this(code, reason, null);
  }


  public WebSocketException(final WebSocketFrame.CloseCode code, final String reason, final Exception cause) {
    super(code + ": " + reason, cause);
    this.code = code;
    this.reason = reason;
  }


  public WebSocketFrame.CloseCode getCode() {
    return code;
  }


  public String getReason() {
    return reason;
  }

}