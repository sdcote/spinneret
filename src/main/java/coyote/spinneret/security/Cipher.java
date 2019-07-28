package coyote.spinneret.security;

/**
 * The Cipher class models a simple encryption utility.
 */
public interface Cipher {
  /**
   * Returns the decrypted bytes for the given enciphered bytes.
   *
   * @param data The data to decipher.
   * @return The decrypted data.
   * @see #encrypt(byte[])
   */
  byte[] decrypt(final byte[] data);


  /**
   * Returns the encrypted bytes for the given bytes.
   *
   * @param bytes The data to encipher.
   * @return The encrypted data.
   */
  byte[] encrypt(byte[] bytes);


  /**
   * To help block ciphers receive data with callers need to know the size of
   * blocks to use.
   *
   * <p>This method allows callers to always send the properly-sized chunks of
   * data to be processed.</p>
   *
   * @return The current block size of the cipher. May return 0 (zero).
   */
  int getBlockSize();


  /**
   * Create a new instance of this cipher.
   *
   * <p>This method is called to get a fresh instance of a cipher of this same
   * type for additional encryption operations without having to query its type
   * and make another instance.</p>
   *
   * @return A new instance of this cipher.
   */
  Cipher getInstance();


  /**
   * Returns the name of the cipher algorithm.
   *
   * @return the name of the cipher algorithm.
   */
  String getName();


  /**
   * Initialize the algorithm with a key to be used for the cipher.
   *
   * @param key The key to use for all operations.
   */
  void init(final byte[] key);

}
