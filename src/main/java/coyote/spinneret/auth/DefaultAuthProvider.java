package coyote.spinneret.auth;

import coyote.spinneret.HTTPSession;

import java.util.Map;


/**
 * The default Authentication and authorization provider for the server.
 * <p>
 * This denies access to everything.
 */
public class DefaultAuthProvider implements AuthProvider {

  /**
   * @see coyote.spinneret.auth.AuthProvider#isAuthenticated(coyote.spinneret.HTTPSession)
   */
  @Override
  public boolean isAuthenticated(final HTTPSession session) {
    return false;
  }


  /**
   * @see coyote.spinneret.auth.AuthProvider#isAuthorized(coyote.spinneret.HTTPSession, String)
   */
  @Override
  public boolean isAuthorized(final HTTPSession session, final String groups) {
    return false;
  }


  /**
   * @see coyote.spinneret.auth.AuthProvider#isSecureConnection(coyote.spinneret.HTTPSession)
   */
  @Override
  public boolean isSecureConnection(final HTTPSession session) {
    return false;
  }


  /**
   * @see coyote.spinneret.auth.AuthProvider#authenticate(coyote.spinneret.HTTPSession, Map)
   */
  @Override
  public boolean authenticate(HTTPSession session, Map<String, String> credentials) {
    return false;
  }

}
