package coyote.spinneret.responder;

import coyote.spinneret.*;

import java.util.Map;


/**
 * This is a test responder specifically for testing the URI parameters passed to
 */
public class ParamResponder implements Responder {
  public static final String UNSPECIFIED = "Unspecified";
  private String responseText = UNSPECIFIED;


  @Override
  public Response delete(Resource resource, Map<String, String> urlParams, HTTPSession session) {
    if (StringUtil.isNotBlank(urlParams.get("name"))) {
      responseText = urlParams.get("name");
    }
    return Response.createFixedLengthResponse(getStatus(), getMimeType(), getText());
  }


  @Override
  public Response get(Resource resource, Map<String, String> urlParams, HTTPSession session) {
    if (StringUtil.isNotBlank(urlParams.get("name"))) {
      responseText = urlParams.get("name");
    }
    return Response.createFixedLengthResponse(getStatus(), getMimeType(), getText());
  }


  @Override
  public Response other(String method, Resource resource, Map<String, String> urlParams, HTTPSession session) {
    if (StringUtil.isNotBlank(urlParams.get("name"))) {
      responseText = urlParams.get("name");
    }
    return Response.createFixedLengthResponse(getStatus(), getMimeType(), getText());
  }


  @Override
  public Response post(Resource resource, Map<String, String> urlParams, HTTPSession session) {
    if (StringUtil.isNotBlank(urlParams.get("name"))) {
      responseText = urlParams.get("name");
    }
    return Response.createFixedLengthResponse(getStatus(), getMimeType(), getText());
  }


  @Override
  public Response put(Resource resource, Map<String, String> urlParams, HTTPSession session) {
    if (StringUtil.isNotBlank(urlParams.get("name"))) {
      responseText = urlParams.get("name");
    }
    return Response.createFixedLengthResponse(getStatus(), getMimeType(), getText());
  }


  /**
   * @return
   */
  private String getText() {
    return responseText;
  }


  /**
   * @return
   */
  private String getMimeType() {
    return MimeType.TEXT.getType();
  }


  /**
   * @return
   */
  private Status getStatus() {
    return Status.OK;
  }

}
