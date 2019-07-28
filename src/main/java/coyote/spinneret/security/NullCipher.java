package coyote.spinneret.security;

/**
 * The Cipher class models the basic cipher algorithm for the platform when the
 * platform is distributed overseas and encryption technologies are not allowed
 * by the government.
 */
public class NullCipher extends AbstractCipher implements Cipher {
  public static final String CIPHER_NAME = "Null";


  /**
   * Returns the data passed to this method.
   *
   * @param data The data to ignore.
   * @return The data passed to this method.
   */
  @Override
  public byte[] decrypt(final byte[] data) {
    return data;
  }


  /**
   * Returns the data passed to this method.
   *
   * @param bytes The data to ignore.
   * @return The data passed to this method.
   */
  @Override
  public byte[] encrypt(final byte[] bytes) {
    return bytes;
  }


  /**
   * @see coyote.spinneret.security.Cipher#getBlockSize()
   */
  @Override
  public int getBlockSize() {
    return 8; // pretend to be a block cipher
  }


  /**
   * @see coyote.spinneret.security.Cipher#getInstance()
   */
  @Override
  public Cipher getInstance() {
    return new NullCipher();
  }


  /**
   * @see coyote.spinneret.security.Cipher#getName()
   */
  @Override
  public String getName() {
    return NullCipher.CIPHER_NAME;
  }


  /**
   * Initialize the algorithm with a key to be used for en/de-cryption.
   *
   * @param key The key to use for all operations.
   */
  @Override
  public void init(final byte[] key) {
    // don't care
  }

}