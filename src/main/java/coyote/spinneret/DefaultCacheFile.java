package coyote.spinneret;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Default strategy for creating and cleaning up cache files.
 *
 * <p>By default, files are created by {@code File.createTempFile()} in the
 * directory specified.
 */
public class DefaultCacheFile implements CacheFile {

  private final File file;

  private final OutputStream fstream;


  public DefaultCacheFile(final File tempdir) throws IOException {
    file = File.createTempFile("HTTPD-", "", tempdir);
    fstream = new FileOutputStream(file);
  }


  @Override
  public void delete() throws Exception {
    HTTPD.safeClose(fstream);
    if (!file.delete()) {
      throw new Exception("Could not delete temporary file");
    }
  }


  @Override
  public String getName() {
    return file.getAbsolutePath();
  }


  @Override
  public OutputStream open() throws Exception {
    return fstream;
  }

}