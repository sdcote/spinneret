package coyote.spinneret.auth;


import coyote.spinneret.security.CredentialSet;
import coyote.spinneret.security.SecurityPrincipal;


/**
 * Any component which retrieves security principals by their credential set.
 */
public interface SecurityPrincipalProvider {

  SecurityPrincipal getPrincipal(CredentialSet credentials);
}
