package coyote.spinneret;

/**
 * Run client handlers asynchronously with any object implementing this
 * interface.
 */
public interface Executor {

  /**
   * Close all the currently active client handlers (i.e. requests) currently
   * in the server.
   */
  void closeAll();


  /**
   * Remove the given chandler from the collection of currently active requests
   * as it has been closed.
   *
   * @param handler the client handler to close
   */
  void closed(ClientHandler handler);


  /**
   * Start the given handler running in its own thread.
   *
   * @param handler the client handler to run.
   */
  void exec(ClientHandler handler);

}