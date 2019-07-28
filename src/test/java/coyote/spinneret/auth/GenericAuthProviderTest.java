package coyote.spinneret.auth;

import coyote.commons.ByteUtil;
import coyote.spinneret.HTTP;
import coyote.spinneret.MockSession;
import coyote.spinneret.TestHttpClient;
import coyote.spinneret.cfg.Config;
import coyote.spinneret.cfg.ConfigurationException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;


/**
 *
 */
public class GenericAuthProviderTest {
  private static final String AUTH_CONFIG = "{ \"Users\" : [ { \"Name\" : \"admin\", \"Password\" : \"secret\", \"Groups\" : \"sysop,devop\" },{ \"Name\" : \"sysop\", \"Password\" : \"secret\", \"Groups\" : \"sysop\" }, { \"Name\" : \"devop\", \"Password\" : \"secret\", \"Groups\" : \"devop\" }, { \"Name\" : \"user\", \"Password\" : \"secret\" } ] }";

  private static final String MD5 = "MD5";
  private static final String UTF8 = "UTF8";

  static {
    try {
      @SuppressWarnings("unused")
      MessageDigest md = MessageDigest.getInstance(MD5);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    UTF8.getBytes(StandardCharsets.UTF_8);
  }


  @BeforeClass
  public static void setUpBeforeClass() throws Exception {

  }


  @AfterClass
  public static void tearDownAfterClass() throws Exception {

  }


  @Test
  public void testDefaultAuthProvider() {
    AuthProvider provider = new GenericAuthProvider();
    assertNotNull(provider);
  }


  @Test
  public void testDefaultAuthProviderConfig() {
    try {
      Config cfg = new Config(AUTH_CONFIG);

      AuthProvider provider = new GenericAuthProvider(cfg);
      assertNotNull(provider);

    } catch (ConfigurationException e) {
      fail(e.getMessage());
    }
  }


  @Ignore
  public void testIsSecureConnection() {
    fail("Not yet implemented"); // TODO
  }


  @Test
  public void digestTest() {
    GenericAuthProvider provider = new GenericAuthProvider();
    String password = "secret";

    provider.setDigestRounds(1);
    try {
      byte[] barray = provider.digest(password.getBytes(UTF8));
      String result = ByteUtil.bytesToHex(barray);
      assertEquals("5E BE 22 94 EC D0 E0 F0 8E AB 76 90 D2 A6 EE 69", result);
    } catch (UnsupportedEncodingException e) {
      fail(e.getMessage());
    }

    provider.setDigestRounds(2);
    try {
      byte[] barray = provider.digest(password.getBytes(UTF8));
      String result = ByteUtil.bytesToHex(barray);
      assertEquals("9E 76 90 17 C8 5F 06 49 77 FE 6A 65 8F 20 7F A6", result);
    } catch (UnsupportedEncodingException e) {
      fail(e.getMessage());
    }

    provider.setDigestRounds(3);
    try {
      byte[] barray = provider.digest(password.getBytes(UTF8));
      String result = ByteUtil.bytesToHex(barray);
      assertEquals("09 C5 10 DF 26 46 5A EE 2F 81 E7 16 DF 44 A3 B7", result);
    } catch (UnsupportedEncodingException e) {
      fail(e.getMessage());
    }

    provider.setDigestRounds(4);
    try {
      byte[] barray = provider.digest(password.getBytes(UTF8));
      String result = ByteUtil.bytesToHex(barray);
      assertEquals("CA 7C B2 24 AC 50 ED 0F 42 90 0D 3F BB 4E 85 90", result);
    } catch (UnsupportedEncodingException e) {
      fail(e.getMessage());
    }

    provider.setDigestRounds(5);
    try {
      byte[] barray = provider.digest(password.getBytes(UTF8));
      String result = ByteUtil.bytesToHex(barray);
      assertEquals("93 FC B3 61 1F 8E 12 DF 71 9A 47 8A C2 EA 36 14", result);
    } catch (UnsupportedEncodingException e) {
      fail(e.getMessage());
    }

  }


  @Test
  public void testIsAuthenticated() {
    try {
      Config cfg = new Config(AUTH_CONFIG);

      GenericAuthProvider provider = new GenericAuthProvider(cfg);
      assertNotNull(provider);
      //System.out.println( "Provider is using "+provider.getDigestRounds()+" digest Rounds" );

      GenericAuthProvider.User user = provider.getUser("user");
      assertNotNull(user);
      //String name = user.getName();
      //byte[] barray = user.getPassword();
      //String result = ByteUtil.bytesToHex( barray );
      //System.out.println( "User: "+name+" password: "+result );

      // create a mock session
      MockSession session = new MockSession();

      // Generate an Authorization header for a user in our test configuration
      String username = "user";
      String password = "secret";
      String basicAuth = TestHttpClient.calculateHeaderData(username, password);
      session.addRequestHeader(HTTP.HDR_AUTHORIZATION.toLowerCase(), basicAuth);

      // Have the provider validate this session
      assertTrue(provider.isAuthenticated(session));

      // the user and groups should be populate in the session
      assertNotNull(session.getUserName());
      assertEquals(session.getUserName(), username);
      assertNotNull(session.getUserGroups());
      assertTrue(session.getUserGroups().size() == 0);

    } catch (ConfigurationException e) {
      fail(e.getMessage());
    }
  }


  @Test
  public void testIsAuthorized() {
    try {
      Config cfg = new Config(AUTH_CONFIG);

      GenericAuthProvider provider = new GenericAuthProvider(cfg);
      assertNotNull(provider);

      GenericAuthProvider.User user = provider.getUser("user");
      assertNotNull(user);
      //String name = user.getName();
      //byte[] barray = user.getPassword();

      // create a mock session
      MockSession session = new MockSession();

      // Generate an Authorization header for a user in our test configuration
      String username = "user";
      String password = "secret";
      String basicAuth = TestHttpClient.calculateHeaderData(username, password);
      session.addRequestHeader(HTTP.HDR_AUTHORIZATION.toLowerCase(), basicAuth);
      // Have the provider validate and set the username in the session
      assertTrue(provider.isAuthenticated(session));
      // Have the provider check role based access of this session
      assertFalse(provider.isAuthorized(session, "devop"));

      // Generate an Authorization header for the 'admin' user
      username = "admin";
      password = "secret";
      basicAuth = TestHttpClient.calculateHeaderData(username, password);
      session = new MockSession();
      session.addRequestHeader(HTTP.HDR_AUTHORIZATION.toLowerCase(), basicAuth);
      assertTrue(provider.isAuthenticated(session));
      assertTrue(provider.isAuthorized(session, "devop"));


      username = "admin";
      password = "";
      basicAuth = TestHttpClient.calculateHeaderData(username, password);
      session = new MockSession();
      session.addRequestHeader(HTTP.HDR_AUTHORIZATION.toLowerCase(), basicAuth);
      assertFalse(provider.isAuthenticated(session));


      username = "";
      password = "secret";
      basicAuth = TestHttpClient.calculateHeaderData(username, password);
      session = new MockSession();
      session.addRequestHeader(HTTP.HDR_AUTHORIZATION.toLowerCase(), basicAuth);
      assertFalse(provider.isAuthenticated(session));

      username = " ";
      password = " ";
      basicAuth = TestHttpClient.calculateHeaderData(username, password);
      session = new MockSession();
      session.addRequestHeader(HTTP.HDR_AUTHORIZATION.toLowerCase(), basicAuth);
      assertFalse(provider.isAuthenticated(session));

      username = "";
      password = "";
      basicAuth = TestHttpClient.calculateHeaderData(username, password);
      session = new MockSession();
      session.addRequestHeader(HTTP.HDR_AUTHORIZATION.toLowerCase(), basicAuth);
      assertFalse(provider.isAuthenticated(session));

    } catch (ConfigurationException e) {
      fail(e.getMessage());
    }

  }

}
