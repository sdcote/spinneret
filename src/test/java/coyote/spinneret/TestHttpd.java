/*
 * Copyright (c) 2017 Stephan D. Cote' - All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the MIT License which accompanies this distribution, and is
 * available at http://creativecommons.org/licenses/MIT/
 *
 * Contributors:
 *   Stephan D. Cote
 *      - Initial concept and implementation
 */
package coyote.spinneret;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 *
 */
public class TestHttpd {

  private static HTTPD server = null;
  private static int port = 54321;


  /**
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {

    port = NetUtil.getNextAvailablePort(port);
    server = new TestingServer(port);

    try {
      server.start(HTTPD.SOCKET_READ_TIMEOUT, true);
    } catch (final IOException ioe) {
      System.err.println("Couldn't start server:\n" + ioe);
      server.stop();
      server = null;
    }

  }


  /**
   * @throws Exception
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    server.stop();
  }


  @Test
  public void test() {
    assertNotNull(server);
    assertTrue(port == server.getPort());
  }

}
