package coyote.spinneret;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class FileUtil {

  /**
   * Read the entire file into memory as an array of bytes.
   *
   * @param file The file to read
   * @return A byte array that contains the contents of the file.
   * @throws IOException If problems occur.
   */
  public static byte[] read(final File file) throws IOException {
    if (file == null) {
      throw new IOException("File reference was null");
    }

    if (file.exists() && file.canRead()) {
      DataInputStream dis = null;
      final byte[] bytes = new byte[new Long(file.length()).intValue()];

      try {
        dis = new DataInputStream(new FileInputStream(file));

        dis.readFully(bytes);

        return bytes;
      } catch (final Exception ignore) {
      } finally {
        // Attempt to close the data input stream
        try {
          if (dis != null) {
            dis.close();
          }
        } catch (final Exception ignore) {
        }
      }
    }

    return new byte[0];
  }


  /**
   * This returns a URI for the given file.
   *
   * @param file from which to generate a URI
   * @return the URI of the given file or null if a logic error occurred
   */
  public static URI getFileURI(final File file) {
    final StringBuffer buffer = new StringBuffer("file://");

    final char[] chars = file.getAbsolutePath().trim().toCharArray();

    URI retval = null;

    if (chars != null) {
      if (chars.length > 1) {
        // If there is a drive delimiter ':' in the second position, we assume
        // this is file is on a Windows system which does not return a leading /
        if (chars[1] == ':') {
          buffer.append("/");
        }
      }

      for (final char c : chars) {
        switch (c) {

          // Replace spaces
          case ' ':
            buffer.append("%20");
            continue;

            // Replace every Windows file separator
          case '\\':
            buffer.append("/");
            continue;

          default:
            buffer.append(c);
            continue;

        }
      }

      try {
        retval = new URI(buffer.toString());
      } catch (final URISyntaxException e) {
        System.err.println(e.getMessage());
      }
    }

    return retval;
  }


}
