package coyote.spinneret;

public interface WebServerPluginInfo {

  String[] getIndexFilesForMimeType(String mime);


  String[] getMimeTypes();


  WebServerPlugin getWebServerPlugin(String mimeType);

}
