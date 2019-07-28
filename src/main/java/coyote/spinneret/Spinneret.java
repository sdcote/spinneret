package coyote.spinneret;

public class Spinneret {

  /**
   * String prefix ({@value}) of configuration items which are encrypted.
   **/
  public static final String ENCRYPT_PREFIX = "ENC:";

  /**
   * Name ({@value}) of the system property containing the name of the cipher to use.
   */
  public static final String CIPHER_NAME = "cipher.name";

  /**
   * Name ({@value}) of the system property containing the Base64 cipher key value.
   */
  public static final String CIPHER_KEY = "cipher.key";

}
