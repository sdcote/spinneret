package coyote.spinneret;

/**
 * HTTP Request methods, with the ability to decode a <code>String</code>
 * back to its enum value.
 */
public enum Method {
  GET("GET"), POST("POST"), HEAD("HEAD"), OPTIONS("OPTIONS"), PUT("PUT"), DELETE("DELETE"), TRACE("TRACE"), CONNECT("CONNECT"), PATCH("PATCH"), PROPFIND("PROPFIND"), PROPPATCH("PROPPATCH"), MKCOL("MKCOL"), MOVE("MOVE"), COPY("COPY"), LOCK("LOCK"), UNLOCK("UNLOCK");

  private String name;


  Method(String s) {
    name = s;
  }

  static Method lookup(final String method) {
    if (method == null) {
      return null;
    }

    try {
      return valueOf(method);
    } catch (final IllegalArgumentException e) {
      // TODO: Log it?
      return null;
    }
  }

  public static Method getMethodByName(String name) {
    if (name != null) {
      for (Method method : Method.values()) {
        if (name.equalsIgnoreCase(method.toString())) {
          return method;
        }
      }
    }
    return null;
  }

  /**
   * @see Enum#toString()
   */
  @Override
  public String toString() {
    return name;
  }

}