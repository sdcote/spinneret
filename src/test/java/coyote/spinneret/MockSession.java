package coyote.spinneret;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 */
public class MockSession implements HTTPSession {
  private static final List<String> EMPTY_LIST = new ArrayList<String>(0);
  private final Map<String, String> requestHeaders;
  private final Map<String, String> responseHeaders;
  private String username = null;
  private List<String> usergroups = EMPTY_LIST;


  public MockSession() {
    requestHeaders = new HashMap<String, String>();
    responseHeaders = new HashMap<String, String>();
  }


  /**
   * @param name  header name
   * @param value header value
   */
  public void addRequestHeader(final String name, final String value) {
    requestHeaders.put(name, value);
  }


  /**
   * @see coyote.commons.network.http.HTTPSession#execute()
   */
  @Override
  public void execute() throws IOException {
  }


  /**
   * @see coyote.commons.network.http.HTTPSession#getCookies()
   */
  @Override
  public CookieHandler getCookies() {
    return null;
  }


  /**
   * @see coyote.commons.network.http.HTTPSession#getInputStream()
   */
  @Override
  public InputStream getInputStream() {
    return null;
  }


  /**
   * @see coyote.commons.network.http.HTTPSession#getMethod()
   */
  @Override
  public Method getMethod() {
    return null;
  }


  /**
   * @see coyote.commons.network.http.HTTPSession#getParms()
   */
  @Override
  public Map<String, String> getParms() {
    return null;
  }


  /**
   * @see coyote.commons.network.http.HTTPSession#getQueryParameterString()
   */
  @Override
  public String getQueryParameterString() {
    return null;
  }


  /**
   * @see coyote.commons.network.http.HTTPSession#getRemoteIpAddress()
   */
  @Override
  public IpAddress getRemoteIpAddress() {
    return IpAddress.IPV4_LOOPBACK_ADDRESS;
  }


  /**
   * @see coyote.commons.network.http.HTTPSession#getRemoteIpPort()
   */
  @Override
  public int getRemoteIpPort() {
    return 0;
  }


  /**
   * @see coyote.commons.network.http.HTTPSession#getRequestHeaders()
   */
  @Override
  public final Map<String, String> getRequestHeaders() {
    return requestHeaders;
  }


  /**
   * @see coyote.commons.network.http.HTTPSession#getResponseHeaders()
   */
  @Override
  public Map<String, String> getResponseHeaders() {
    return responseHeaders;
  }


  /**
   * @see coyote.commons.network.http.HTTPSession#getUri()
   */
  @Override
  public String getUri() {
    return null;
  }


  /**
   * @see coyote.commons.network.http.HTTPSession#getUserGroups()
   */
  @Override
  public List<String> getUserGroups() {
    return usergroups;
  }

  /**
   * @see coyote.commons.network.http.HTTPSession#setUserGroups(List)
   */
  @Override
  public void setUserGroups(final List<String> groups) {
    if (groups != null) {
      usergroups = groups;
    } else {
      usergroups = EMPTY_LIST;
    }
  }

  /**
   * @see coyote.commons.network.http.HTTPSession#getUserName()
   */
  @Override
  public String getUserName() {
    return username;
  }

  /**
   * @see coyote.commons.network.http.HTTPSession#setUserName(String)
   */
  @Override
  public void setUserName(final String user) {
    username = user;
  }

  /**
   * @see coyote.commons.network.http.HTTPSession#isSecure()
   */
  @Override
  public boolean isSecure() {
    return false;
  }

  /**
   * @see coyote.commons.network.http.HTTPSession#parseBody()
   */
  @Override
  public Body parseBody() throws IOException, ResponseException {
    // TODO Auto-generated method stub
    return null;
  }

}
