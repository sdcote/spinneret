package coyote.spinneret;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class LoadKeyStoreTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();




  @Test
  public void loadKeyStoreFromResources() throws Exception {
    final String keyStorePath = "/keystore.jks";
    final InputStream resourceAsStream = this.getClass().getResourceAsStream( keyStorePath );
    assertNotNull( resourceAsStream );

    final SSLServerSocketFactory sslServerSocketFactory = HTTPD.makeSSLSocketFactory( keyStorePath, "password".toCharArray() );
    assertNotNull( sslServerSocketFactory );
  }




  @Test
  public void loadKeyStoreFromResourcesWrongPassword() throws Exception {
    final String keyStorePath = "/keystore.jks";
    final InputStream resourceAsStream = this.getClass().getResourceAsStream( keyStorePath );
    assertNotNull( resourceAsStream );

    thrown.expect( IOException.class );
    HTTPD.makeSSLSocketFactory( keyStorePath, "wrongpassword".toCharArray() );
  }




  @Test
  public void loadNonExistentKeyStoreFromResources() throws Exception {
    final String nonExistentPath = "/nokeystorehere.jks";
    final InputStream resourceAsStream = this.getClass().getResourceAsStream( nonExistentPath );
    assertNull( resourceAsStream );

    thrown.expect( IOException.class );
    HTTPD.makeSSLSocketFactory( nonExistentPath, "".toCharArray() );
  }

}
