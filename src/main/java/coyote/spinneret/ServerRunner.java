package coyote.spinneret;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServerRunner {

  /**
   * logger to log to.
   */
  private static final Logger LOG = Logger.getLogger(ServerRunner.class.getName());


  public static void executeInstance(HTTPD server) {
    try {
      server.start(HTTPD.SOCKET_READ_TIMEOUT, false);
    } catch (IOException ioe) {
      System.err.println("Couldn't start server:\n" + ioe);
      System.exit(-1);
    }

    System.out.println("Server running, Hit Enter to stop.\n");

    try {
      System.in.read();
    } catch (Throwable ignored) {
    }

    server.stop();
    System.out.println("Server stopped.\n");
  }


  public static <T extends HTTPD> void run(Class<T> serverClass) {
    try {
      executeInstance(serverClass.newInstance());
    } catch (Exception e) {
      ServerRunner.LOG.log(Level.SEVERE, "Could not create server", e);
    }
  }

}
