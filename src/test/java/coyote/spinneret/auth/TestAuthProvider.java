package coyote.spinneret.auth;


import coyote.spinneret.HTTPSession;

import java.util.Map;


/**
 * 
 */
public class TestAuthProvider implements AuthProvider {

  private volatile boolean allowConnections = true;
  private volatile boolean allowAuthentications = true;
  private volatile boolean allowAuthorizations = true;




  @Override
  public boolean isSecureConnection( HTTPSession session ) {
    return allowConnections;
  }




  @Override
  public boolean isAuthenticated( HTTPSession session ) {
    return allowAuthentications;
  }




  @Override
  public boolean isAuthorized( HTTPSession session, String groups ) {
    return allowAuthorizations;
  }




  public void rejectAllConnections() {
    allowConnections = false;
  }




  public void allowAllConnections() {
    allowConnections = true;
  }




  public void rejectAllAuthentications() {
    allowAuthentications = false;
  }




  public void allowAllAuthentications() {
    allowAuthentications = true;
  }




  public void rejectAllAuthorizations() {
    allowAuthorizations = false;
  }




  public void allowAllAuthorizations() {
    allowAuthorizations = true;
  }




  @Override
  public boolean authenticate(HTTPSession session, Map<String, String> credentials) {
    return true;
  }

}
