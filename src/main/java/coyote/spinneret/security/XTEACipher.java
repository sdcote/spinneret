package coyote.spinneret.security;

import coyote.spinneret.CipherUtil;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * The XTEACipher class models the Extended Tiny Encryption Algorithm (XTEA).
 */
public class XTEACipher extends AbstractCipher implements Cipher {
  public static final String CIPHER_NAME = "XTEA";
  private static final int BLOCK_SIZE = 8; // bytes in a data block (64 bits)
  private static final int D_SUM = 0xC6EF3720;
  private static final int DELTA = 0x9E3779B9;
  private static final byte[] EMPTY_BYTES = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
  private static final int ROUNDS = 32;// iteration count (cycles)
  private static final String UTF16 = "UTF-16";

  private int[] subKeys = null;


  public XTEACipher() {

  }

  /**
   * Decrypt one block of data with XTEA algorithm.
   *
   * @param data    the byte array from which to read the enciphered data.
   * @param offset  offset in the array to retrieve the next block to decrypt.
   * @param subkeys The subkeys to use in the decryption.
   * @return a block of clear data.
   */
  public static byte[] decrypt(final byte[] data, int offset, final int[] subkeys) {
    // Pack bytes into integers
    int v0 = ((data[offset++]) << 24) | ((data[offset++] & 0xFF) << 16) | ((data[offset++] & 0xFF) << 8) | ((data[offset++] & 0xFF));

    int v1 = ((data[offset++]) << 24) | ((data[offset++] & 0xFF) << 16) | ((data[offset++] & 0xFF) << 8) | ((data[offset] & 0xFF));

    int n = XTEACipher.ROUNDS, sum;

    sum = XTEACipher.D_SUM;

    while (n-- > 0) {
      v1 -= (((v0 << 4) ^ (v0 >>> 5)) + v0) ^ (sum + subkeys[(sum >>> 11) & 3]);
      sum -= XTEACipher.DELTA;
      v0 -= (((v1 << 4) ^ (v1 >>> 5)) + v1) ^ (sum + subkeys[sum & 3]);
    }

    // Unpack and return
    int outOffset = 0;
    final byte[] out = new byte[XTEACipher.BLOCK_SIZE];
    out[outOffset++] = (byte) (v0 >>> 24);
    out[outOffset++] = (byte) (v0 >>> 16);
    out[outOffset++] = (byte) (v0 >>> 8);
    out[outOffset++] = (byte) (v0);

    out[outOffset++] = (byte) (v1 >>> 24);
    out[outOffset++] = (byte) (v1 >>> 16);
    out[outOffset++] = (byte) (v1 >>> 8);
    out[outOffset] = (byte) (v1);

    return out;
  }

  /**
   * Returns the decrypted bytes for the given enciphered data and key string.
   *
   * <p>First, the key is converted to UTF-16 encoding and passed through the
   * Virtual Machines MD5 message digest and the first 16 bytes of the digest
   * are used to represent the key.<p>
   *
   * <p>Next, the resulting blocked data is run through 32-round Feistel cipher
   * which uses operations from mixed (orthogonal) algebraic groups - XORs and
   * additions in this case. It decrypts 64 data bits at a time using the
   * 128-bit key.</p>
   *
   * <p>Finally, the data is stripped of padding using a PKCS5 DES CBC padding
   * scheme described in section 1.1 of RFC-1423.</p>
   *
   * @param data The data to decipher.
   * @param key  The key to use in the generation of the deciphered text.
   * @return The decrypted data.
   * @see #encrypt(byte[], String)
   */

  public static byte[] decrypt(final byte[] data, final String key) {
    byte[] retval = new byte[data.length];
    final int[] subKeys = XTEACipher.generateSubKeys(XTEACipher.getKeyBytes(key));

    for (int x = 0; x < data.length; x += XTEACipher.BLOCK_SIZE) {
      final byte[] block = XTEACipher.decrypt(data, x, subKeys);
      System.arraycopy(block, 0, retval, x, block.length);
    }

    final int padding = retval[retval.length - 1];

    if ((padding > 0) && (padding < 9)) {
      final byte[] tmp = new byte[retval.length - padding];
      System.arraycopy(retval, 0, tmp, 0, tmp.length);
      retval = tmp;
    }

    return retval;
  }


  /**
   * Encrypt one block of data with XTEA algorithm.
   *
   * @param data    the byte array from which to read the clear data.
   * @param offset  offset in the array to retrieve the next block to encrypt.
   * @param subkeys The subkeys to use in the encryption.
   * @return a block of enciphered data.
   */
  public static byte[] encrypt(final byte[] data, int offset, final int[] subkeys) {
    // Pack bytes into integers
    int v0 = ((data[offset++]) << 24) | ((data[offset++] & 0xFF) << 16) | ((data[offset++] & 0xFF) << 8) | ((data[offset++] & 0xFF));
    int v1 = ((data[offset++]) << 24) | ((data[offset++] & 0xFF) << 16) | ((data[offset++] & 0xFF) << 8) | ((data[offset] & 0xFF));

    int n = XTEACipher.ROUNDS, sum;

    sum = 0;
    while (n-- > 0) {
      v0 += (((v1 << 4) ^ (v1 >>> 5)) + v1) ^ (sum + subkeys[sum & 3]);
      sum += XTEACipher.DELTA;
      v1 += (((v0 << 4) ^ (v0 >>> 5)) + v0) ^ (sum + subkeys[(sum >>> 11) & 3]);
    }

    // Unpack and return
    int outOffset = 0;
    final byte[] out = new byte[XTEACipher.BLOCK_SIZE];
    out[outOffset++] = (byte) (v0 >>> 24);
    out[outOffset++] = (byte) (v0 >>> 16);
    out[outOffset++] = (byte) (v0 >>> 8);
    out[outOffset++] = (byte) (v0);

    out[outOffset++] = (byte) (v1 >>> 24);
    out[outOffset++] = (byte) (v1 >>> 16);
    out[outOffset++] = (byte) (v1 >>> 8);
    out[outOffset] = (byte) (v1);

    return out;
  }

  /**
   * Returns the encrypted bytes for the given string.
   *
   * <p>First, the key is converted to UTF-16 encoding and passed through the
   * Virtual Machines MD5 message digest and the first 16 bytes of the digest
   * are used to represent the key.<p>
   *
   * <p>Next the data is padded to 8-byte blocks of data using a PKCS5 DES CBC
   * encryption padding scheme described in section 1.1 of RFC-1423.</p>
   *
   * <p>Finally, the resulting blocked data is run through 32-round Feistel
   * cipher which uses operations from mixed (orthogonal) algebraic groups -
   * XORs and additions in this case. It encrypts 64 data bits at a time using
   * the key.</p>
   *
   * @param bytes The data to encipher.
   * @param key   The key to use in the generation of the enciphered text.
   * @return The encrypted text as a byte array.
   */
  public static byte[] encrypt(final byte[] bytes, final String key) {

    final XTEACipher cipher = new XTEACipher();

    // initialize with the key string
    cipher.init(XTEACipher.getKeyBytes(key));

    // Have the cipher encrypt the data
    return cipher.encrypt(bytes);

  }

  /**
   * Returns the encrypted bytes for the given data and key strings.
   *
   * @param data The data to encipher.
   * @param key  The key to use in the generation of the enciphered text.
   * @return The encrypted text as a byte array.
   * @see #encrypt(byte[], String)
   */
  public static byte[] encryptString(final String data, final String key) {
    byte[] bytes = null;
    bytes = data.getBytes(StandardCharsets.UTF_16);

    return XTEACipher.encrypt(bytes, key);
  }

  /**
   * Generate a key from the given string.
   *
   * <p>Return the first 16 bytes of the MD5 digest of the given key string
   * encoded using UTF-16.</p>
   *
   * @param key The string to use as the key.
   * @return Bytes suitable for use as an encryption key.
   */
  public static byte[] getKeyBytes(final String key) {
    final byte[] retval = XTEACipher.EMPTY_BYTES;
    try {
      final MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(key.getBytes(StandardCharsets.UTF_16));
      final byte[] result = md.digest();
      for (int x = 0; x < retval.length; x++) {
        retval[x] = result[x];
        if ((x + 1) > result.length) {
          break;
        }
      }
    } catch (final NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return retval;
  }

  /**
   * Generate XTEA subkeys for the cipher algorithm.
   *
   * @param key the key to use as the seed for subkey generation.
   * @return a 4-element integer array containing the generated keys.
   */
  private static int[] generateSubKeys(final byte[] key) {
    final int[] retval = new int[4];
    for (int off = 0, i = 0; i < 4; i++) {
      retval[i] = ((key[off++] & 0xFF) << 24) | ((key[off++] & 0xFF) << 16) | ((key[off++] & 0xFF) << 8) | ((key[off++] & 0xFF));
    }
    return retval;
  }

  /**
   * @see coyote.spinneret.security.Cipher#decrypt(byte[])
   */
  @Override
  public byte[] decrypt(final byte[] data) {
    final byte[] retval = new byte[data.length];

    for (int x = 0; x < data.length; x += XTEACipher.BLOCK_SIZE) {
      final byte[] block = XTEACipher.decrypt(data, x, subKeys);
      System.arraycopy(block, 0, retval, x, block.length);
    }

    return CipherUtil.trim(retval);
  }


  /**
   * Encrypt the given data.
   *
   * <p>This instance MUST be initialized prior to making this call.</p>
   *
   * @see coyote.spinneret.security.Cipher#encrypt(byte[])
   */
  @Override
  public byte[] encrypt(final byte[] bytes) {

    // pad the data using RFC-1423 scheme
    final byte[] data = CipherUtil.pad(bytes);

    // create our return value
    final byte[] retval = new byte[data.length];

    // encrypt the data
    for (int x = 0; x < bytes.length; x += XTEACipher.BLOCK_SIZE) {
      final byte[] block = XTEACipher.encrypt(data, x, subKeys);
      System.arraycopy(block, 0, retval, x, block.length);
    }
    return retval;
  }


  /**
   * @see coyote.spinneret.security.Cipher#getBlockSize()
   */
  @Override
  public int getBlockSize() {
    return XTEACipher.BLOCK_SIZE;
  }


  /**
   * @see coyote.spinneret.security.Cipher#getInstance()
   */
  @Override
  public Cipher getInstance() {
    return new XTEACipher();
  }


  /**
   * @see coyote.spinneret.security.Cipher#getName()
   */
  @Override
  public String getName() {
    return XTEACipher.CIPHER_NAME;
  }


  /**
   * <p>The most common method for initializing this cipher is to pick a string
   * and an encoding and convert the string to bytes using that encoding.</p>
   *
   * <p>The key must be 16 bytes in length. If longer, the key will be
   * truncated to 16 bytes, if shorter the key will be padded with zeros.</p>
   *
   * @see coyote.spinneret.security.Cipher#init(byte[])
   */
  @Override
  public void init(final byte[] key) {

    // create a key of all zeros
    final byte[] fullkey = new byte[16];

    // copy the given key into the full 16 byte key
    if (key.length > 16) {
      System.arraycopy(key, 0, fullkey, 0, fullkey.length);
    } else {
      System.arraycopy(key, 0, fullkey, 0, key.length);
    }

    // generate the subkeys from the full 16 byte key
    subKeys = XTEACipher.generateSubKeys(fullkey);
  }

}
