package coyote.spinneret;

import java.io.OutputStream;

/**
 * A temporary cache file.
 *
 * <p>Cache files are responsible for managing the temporary storage  of large
 * amounts of data received by the server.
 */
public interface CacheFile {

  void delete() throws Exception;


  String getName();


  OutputStream open() throws Exception;
}