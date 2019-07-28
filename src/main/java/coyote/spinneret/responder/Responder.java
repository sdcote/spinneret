package coyote.spinneret.responder;

import coyote.spinneret.HTTPSession;
import coyote.spinneret.Response;

import java.util.Map;


/**
 * This represents a class which responds to a URI requested from the HTTP
 * server.
 *
 * <p>Responders are classes which are instantiated for each request. They do
 * not have any state between requests and are therefore state-less in nature.
 * Many instances of a responder can be created which will require garbage
 * collecting so design your responder accordingly.
 *
 * <p>All responders should implement this interface to support requests.</p>
 *
 * <p>The Resource can contain important data for the operation of the
 * responder. This data is set when the routing was added to the server and
 * can be retrieved through the {@code initParameter} attribute. The
 * UriResponder must know beforehand the type of data placed in the
 * initialization attribute:<pre>
 * File baseDirectory = resource.initParameter( File.class );</pre>
 *
 * <p>The {@code initParameter} attribute is actually an array of objects which
 * the UriResponder can retrieve via index:<pre>
 * File baseDirectory = resource.initParameter( 0, File.class );</pre>
 */
public interface Responder {

  /**
   * Respond to the HTTP "delete" method requests.
   *
   * @param resource  the instance of the Resource which contains our initialization parameters
   * @param urlParams parameters to process
   * @param session   the session established with the HTTP server
   * @return The response based on this method's processing
   */
  Response delete(Resource resource, Map<String, String> urlParams, HTTPSession session);


  /**
   * Respond to the HTTP "get" method requests.
   *
   * @param resource  the instance of the Resource which contains our initialization parameters
   * @param urlParams parameters to process
   * @param session   the session established with the HTTP server
   * @return The response based on this method's processing
   */
  Response get(Resource resource, Map<String, String> urlParams, HTTPSession session);


  /**
   * Respond to the HTTP method requests which do not map to get, put, post or delete.
   *
   * @param resource  the instance of the Resource which contains our initialization parameters
   * @param urlParams parameters to process
   * @param session   the session established with the HTTP server
   * @return The response based on this method's processing
   */
  Response other(String method, Resource resource, Map<String, String> urlParams, HTTPSession session);


  /**
   * Respond to the HTTP "post" method requests.
   *
   * @param resource  the instance of the Resource which contains our initialization parameters
   * @param urlParams parameters to process
   * @param session   the session established with the HTTP server
   * @return The response based on this method's processing
   */
  Response post(Resource resource, Map<String, String> urlParams, HTTPSession session);


  /**
   * Respond to the HTTP "put" method requests.
   *
   * @param resource  the instance of the Resource which contains our initialization parameters
   * @param urlParams parameters to process
   * @param session   the session established with the HTTP server
   * @return The response based on this method's processing
   */
  Response put(Resource resource, Map<String, String> urlParams, HTTPSession session);

}