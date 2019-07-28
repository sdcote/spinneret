package coyote.spinneret;

public final class ResponseException extends Exception {

  private static final long serialVersionUID = 3423102004759131858L;

  private final Status status;


  public ResponseException(final Status status, final String message) {
    super(message);
    this.status = status;
  }


  public ResponseException(final Status status, final String message, final Exception e) {
    super(message, e);
    this.status = status;
  }


  public Status getStatus() {
    return status;
  }

}