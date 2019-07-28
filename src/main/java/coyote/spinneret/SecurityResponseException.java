package coyote.spinneret;

/**
 * Used to indicate security related processing detected an issue with generating a response.
 *
 * <p>The HTTPSession should drop the connection immediately.
 */
public class SecurityResponseException extends Exception {

  private static final long serialVersionUID = -7169595886319527L;


  public SecurityResponseException(final String message) {
    super(message);
  }


  public SecurityResponseException(final String message, final Exception e) {
    super(message, e);
  }

}
