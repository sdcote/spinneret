package coyote.spinneret;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * Creates a new SSLServerSocket
 */
public class SecureServerSocketFactory implements ServerSocketFactory {

  private final SSLServerSocketFactory sslServerSocketFactory;

  private final String[] sslProtocols;




  public SecureServerSocketFactory( final SSLServerSocketFactory sslServerSocketFactory, final String[] sslProtocols ) {
    this.sslServerSocketFactory = sslServerSocketFactory;
    this.sslProtocols = sslProtocols;
  }




  @Override
  public ServerSocket create() throws IOException {
    SSLServerSocket ss = null;
    ss = (SSLServerSocket)sslServerSocketFactory.createServerSocket();
    if ( sslProtocols != null ) {
      ss.setEnabledProtocols( sslProtocols );
    } else {
      ss.setEnabledProtocols( ss.getSupportedProtocols() );
    }
    ss.setUseClientMode( false );
    ss.setWantClientAuth( false );
    ss.setNeedClientAuth( false );
    return ss;
  }

}