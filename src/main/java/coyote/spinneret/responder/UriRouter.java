package coyote.spinneret.responder;

import coyote.spinneret.HTTPSession;
import coyote.spinneret.Response;
import coyote.spinneret.SecurityResponseException;
import coyote.spinneret.auth.AuthProvider;

import java.util.*;


/**
 * This is the heart of the URI routing mechanism in a routing HTTP server.
 */
public class UriRouter {

  private final List<Resource> mappings;
  private Resource error404Url;
  private Class<?> notImplemented;


  /**
   * Default constructor
   */
  public UriRouter() {
    mappings = new ArrayList<Resource>();
  }


  /**
   * @return the list of URI resource objects responsible for handling
   * requests of the server.
   */
  public List<Resource> getMappings() {
    return mappings;
  }


  /**
   * Search in the mappings if the given request URI matches some of the rules.
   *
   * <p>If there are more than one match, this returns the rule with least
   * parameters. For example: mapping 1 = /user/:id  - mapping 2 = /user/help.
   * If the incoming URI is www.example.com/user/help - mapping 2 is returned.
   * If the incoming URI is www.example.com/user/3232 - mapping 1 is
   * returned.</p>
   *
   * @param session the HTTP session encapsulating the request
   * @return the Response from the URI resource processing
   * @throws SecurityResponseException if processing request generated a security exception
   */
  public Response process(final HTTPSession session) throws SecurityResponseException {

    final String request = HTTPDRouter.normalizeUri(session.getUri());

    Map<String, String> params = null;
    Resource retval = error404Url;

    // For all the resources, see which one matches first
    for (final Resource resource : mappings) {
      params = resource.match(request);
      if (params != null) {
        retval = resource;
        break;
      }
    }

//    if (Log.isLogging(HTTPD.EVENT)) {
//      if (error404Url == retval) {
//        Log.append(HTTPD.EVENT, "No route mapped to handle request for '" + request + "' from " + session.getRemoteIpAddress() + ":" + session.getRemoteIpPort() + " - method:" + session.getMethod());
//      } else {
//        Log.append(HTTPD.EVENT, session.getMethod() + " request for '" + request + "' from " + session.getRemoteIpAddress() + ":" + session.getRemoteIpPort() + " handled by resource : " + retval);
//      }
//    }

    // Have the found (or default 404) URI resource process the session
    return retval.process(params, session);
  }


  public void setNotFoundResponder(final Class<?> responder) {
    error404Url = new Resource(null, 100, responder, null);
  }


  public void setNotImplementedResponder(final Class<?> responder) {
    notImplemented = responder;
  }


  private void sortMappings() {
    Collections.sort(mappings, new Comparator<Resource>() {

      @Override
      public int compare(final Resource o1, final Resource o2) {
        return o1.priority - o2.priority;
      }
    });
  }


  /**
   * Add a route to this router with the responder class for that route.
   *
   * @param url           the regex to match against the request URL
   * @param priority      the priority in which the router will check the route,
   *                      lower values return before larger priorities.
   * @param responder     the responder class for this mapping. If null, the
   *                      NotImplemented responder will be used.
   * @param authProvider  the auth provider the URI resource should use for
   *                      this route
   * @param initParameter the initialization parameters for the responder when
   *                      it receives a request.
   */
  void addRoute(final String url, final int priority, final Class<?> responder, final AuthProvider authProvider, final Object... initParameter) {
    if (url != null) {
      if (responder != null) {
        mappings.add(new Resource(url, priority + mappings.size(), responder, authProvider, initParameter));
      } else {
        mappings.add(new Resource(url, priority + mappings.size(), notImplemented, authProvider));
      }
      sortMappings();
    }
  }


  void removeRoute(final String url) {
    final String uriToDelete = HTTPDRouter.normalizeUri(url);
    final Iterator<Resource> iter = mappings.iterator();
    while (iter.hasNext()) {
      final Resource resource = iter.next();
      if (uriToDelete.equals(resource.getUri())) {
        iter.remove();
        break;
      }
    }
  }

}