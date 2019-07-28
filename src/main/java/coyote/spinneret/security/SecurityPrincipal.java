package coyote.spinneret.security;

import java.security.Principal;


public abstract class SecurityPrincipal implements Principal {

  public SecurityPrincipal() {
  }


  /**
   * @return the identifier for this principal
   */
  public abstract String getId();


  /**
   * @param id an identifier unique within the security context
   */
  public abstract void setId(String id);


  /**
   * @param name a name suitable for display to the principal this object represents.
   */
  public abstract void setName(String name);

}
