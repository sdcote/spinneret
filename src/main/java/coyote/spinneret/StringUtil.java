package coyote.spinneret;

import java.nio.charset.StandardCharsets;

public class StringUtil {


  /**
   * Field ISO8859_1
   */
  public static String ISO8859_1;

  static {
    final String iso = System.getProperty("ISO_8859_1");
    if (iso != null) {
      StringUtil.ISO8859_1 = iso;
    } else {
      new String(new byte[]{(byte) 20}, StandardCharsets.ISO_8859_1);

      StringUtil.ISO8859_1 = "ISO-8859-1";
    }
  }


  /**
   * Checks if a string is not null, empty ("") and not only whitespace.
   *
   * <p>This is a convenience wrapper around isBlank(String) to make code
   * slightly more readable.</p>
   *
   * @param str the String to check, may be null
   * @return <code>true</code> if the String is not empty and not null and not
   * whitespace
   * @see #isBlank(String)
   */
  public static boolean isNotBlank(String str) {
    return !StringUtil.isBlank(str);
  }

  /**
   * Checks if a string is null, empty ("") or only whitespace.
   *
   * @param str the String to check, may be null
   * @return {@code true} if the argument is empty or null or only whitespace
   */
  public static boolean isBlank(String str) {
    int strLen;
    if (str == null || (strLen = str.length()) == 0) {
      return true;
    }
    for (int i = 0; i < strLen; i++) {
      if ((Character.isWhitespace(str.charAt(i)) == false)) {
        return false;
      }
    }
    return true;
  }


  /**
   * Checks if a string is null or empty ("").
   *
   * @param str the String to check, may be null
   * @return <code>true</code> if the String is empty or null, false otherwise
   * @see #isNotEmpty(String)
   */
  public static boolean isEmpty(String str) {
    return str == null || str.length() == 0;
  }


  /**
   * Checks if a string is not null and not empty ("").
   *
   * <p>Whitespace is allowed.</p>
   *
   * <p>This is a convenience wrapper around {@code isEmpty(String)} to make
   * code slightly more readable.</p>
   *
   * @param str the String to check, may be null
   * @return <code>true</code> if the String is not empty and not null, false othersize
   * @see #isEmpty(String)
   */
  public static boolean isNotEmpty(String str) {
    return !StringUtil.isEmpty(str);
  }


  /**
   * Convert the given string into ISO 8859-1 encoding.
   *
   * <p>This is the defacto standard for string encoding on the Internet</p>
   *
   * @param text the text to encode
   * @return the bytes representing the encoded text or null if the text is null
   */
  public static byte[] getBytes(String text) {
    byte[] retval = null;
    if (text != null) {
      try {
        retval = text.getBytes(StringUtil.ISO8859_1);
      } catch (final Exception ex) {
      }
    }
    return retval;
  }

}
