package coyote.spinneret.responder;

import coyote.spinneret.HTTPD;
import coyote.spinneret.HTTPSession;
import coyote.spinneret.Response;
import coyote.spinneret.SecurityResponseException;

import java.util.List;


/**
 * This is a HTTPD which routes requests to request responders based on the
 * request URI.
 *
 * <p>This allows the server to implement a pluggable approach to handling
 * requests. For example, it is possible to implement micro services with
 * simple classes.
 */
public class HTTPDRouter extends HTTPD {

  private final UriRouter router;


  public HTTPDRouter(final int port) {
    super(port);
    router = new UriRouter();
  }

  /**
   * Remove any leading and trailing slashes (/) from the URI
   *
   * @param value the URI value to normalize
   * @return the URI with no leading or trailing slashes
   */
  public static String normalizeUri(String value) {
    if (value == null) {
      return value;
    }
    if (value.startsWith("/")) {
      value = value.substring(1);
    }
    if (value.endsWith("/")) {
      value = value.substring(0, value.length() - 1);
    }
    return value;

  }

  /**
   * Default routings, they are over writable.
   *
   * <pre>router.setNotFoundResponder(GeneralResponder.class);</pre>
   */
  public void addDefaultRoutes() {
    router.setNotImplementedResponder(NotImplementedResponder.class);
    router.setNotFoundResponder(Error404Responder.class);
    router.addRoute("/", Integer.MAX_VALUE / 2, BlankPageResponder.class, authProvider);
    router.addRoute("/index.html", Integer.MAX_VALUE / 2, BlankPageResponder.class, authProvider);
  }


  /**
   * Set the responder for resource not found (i.e. 404) events.
   *
   * @param responder the responder which will return the desired HTTP response
   */
  public void setNotFoundResponder(final Class<?> responder) {
    router.setNotFoundResponder(responder);
  }


  /**
   * Set the responder for method not implemented (i.e. 501) events.
   *
   * @param responder the responder which will return the desired HTTP response
   */
  public void setNotImplementedResponder(final Class<?> responder) {
    router.setNotImplementedResponder(responder);
  }


  /**
   * Add a responder for the given URL pattern.
   *
   * @param urlPattern RegEx pattern describing the URL to match
   * @param responder  The class to be instantiated to handle the connection
   * @param initParams the array of objects to pass to the responder upon in
   */
  public void addRoute(final String urlPattern, final Class<?> responder, final Object... initParams) {
    router.addRoute(urlPattern, 100, responder, authProvider, initParams);
  }


  /**
   * Add a responder for the given URL pattern.
   *
   * @param urlPattern RegEx pattern describing the URL to match
   * @param priority   The evaluation priority to all the other routes
   * @param responder  The class to be instantiated to handle the connection
   * @param initParams the array of objects to pass to the responder upon in
   */
  public void addRoute(final String urlPattern, final int priority, final Class<?> responder, final Object... initParams) {
    router.addRoute(urlPattern, priority, responder, authProvider, initParams);
  }


  /**
   * Accessor to the Resources responsible for handling requests of the router.
   *
   * <p><strong>NOTE:</strong> Never add or otherwise alter this list as it
   * can adversely affect routing. It is exposed primarily for diagnostic
   * purposes or to provide access to the URI resource attributes, such as
   * their initialization parameters.
   *
   * @return the list of URI resource objects responsible for handling
   * requests of the server.
   */
  public List<Resource> getMappings() {
    return router.getMappings();
  }


  public void removeRoute(final String url) {
    router.removeRoute(url);
  }


  /**
   * @throws SecurityResponseException if processing the request generated a security exception
   * @see coyote.spinneret.HTTPD#serve(coyote.spinneret.HTTPSession)
   */
  @Override
  public Response serve(final HTTPSession session) throws SecurityResponseException {
    return router.process(session);
  }


}
