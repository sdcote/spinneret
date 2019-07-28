package coyote.spinneret;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Default threading strategy for HTTPD.
 *
 * <p>By default, the server spawns a new Thread for every incoming request.
 * These are set to <i>daemon</i> status, and named according to the request
 * number. The name is useful when profiling the application.</p>
 */
public class DefaultExecutor implements Executor {

  /**
   * The list of all the currently active requests for this server
   */
  private final List<ClientHandler> running = Collections.synchronizedList(new ArrayList<ClientHandler>());
  /**
   * The current number of requests received so far.
   */
  private volatile long requestCount;

  /**
   * @see coyote.spinneret.Executor#closeAll()
   */
  @Override
  public void closeAll() {
    // copy of the list for concurrency
    for (final ClientHandler clientHandler : new ArrayList<ClientHandler>(running)) {
      clientHandler.close();
    }
  }


  /**
   * @see coyote.spinneret.Executor#closed(coyote.spinneret.ClientHandler)
   */
  @Override
  public void closed(final ClientHandler clientHandler) {
    running.remove(clientHandler);
  }


  @Override
  public void exec(final ClientHandler clientHandler) {
    ++requestCount;
    final Thread t = new Thread(clientHandler);
    t.setDaemon(true);
    t.setName("HTTPD Request(" + requestCount + ")");
    running.add(clientHandler);
    t.start();
  }


  /**
   * @return a list with currently running clients.
   */
  public List<ClientHandler> getRunning() {
    return running;
  }

}