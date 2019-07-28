package coyote.spinneret;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.net.InetAddress;

import static org.junit.Assert.fail;


/**
 *
 */
public class NetUtilTest {

  /**
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }


  /**
   * @throws Exception
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }


  @Ignore
  public void testGetNextAvailablePortInetAddress() {
    fail("Not yet implemented"); // TODO
  }


  @Ignore
  public void testGetNextAvailablePortInt() {
    fail("Not yet implemented"); // TODO
  }


  @Ignore
  public void testGetNextAvailablePortInetAddressInt() {
    fail("Not yet implemented"); // TODO
  }


  @Ignore
  public void testGetNextServerSocket() {
    fail("Not yet implemented"); // TODO
  }


  @Ignore
  public void testValidatePort() {
    fail("Not yet implemented"); // TODO
  }


  @Ignore
  public void testGetLocalAddress() {
    fail("Not yet implemented"); // TODO
  }


  @Test
  public void testGetBroadcastAddress() {
    try {
      String mask = "255.255.0.0";
      InetAddress addr = NetUtil.getLocalBroadcast(mask);
      System.out.println("Local broadcast for '" + mask + "' = " + addr.getHostName());
    } catch (Exception ex) {
      fail("Could calc local broadcast address " + ex.getMessage());
    }

    try {
      String mask = "0.0.0.0";
      InetAddress addr = NetUtil.getLocalBroadcast(mask);
      System.out.println("Local broadcast for '" + mask + "' = " + addr.getHostName());
    } catch (Exception ex) {
      fail("Could calc local broadcast address " + ex.getMessage());
    }
  }

}
