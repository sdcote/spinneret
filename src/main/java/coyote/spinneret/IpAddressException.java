package coyote.spinneret;

/**
 * Exception thrown when there is a problem with IP Address operations.
 */
public final class IpAddressException extends Exception {

  private static final long serialVersionUID = 7815350628254830424L;


  /**
   * Constructor
   */
  public IpAddressException() {
    super();
  }


  /**
   * Constructor
   *
   * @param message Error message
   */
  public IpAddressException(final String message) {
    super(message);
  }


  /**
   * Constructor
   *
   * @param message Error message
   * @param excptn
   */
  public IpAddressException(final String message, final Throwable excptn) {
    super(message, excptn);
  }


  /**
   * Constructor
   *
   * @param excptn
   */
  public IpAddressException(final Throwable excptn) {
    super(excptn);
  }
}