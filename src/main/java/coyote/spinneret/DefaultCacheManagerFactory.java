package coyote.spinneret;


/**
 * Default strategy for creating and cleaning up temporary files required by
 * requests.
 */
class DefaultCacheManagerFactory implements CacheManagerFactory {

  @Override
  public CacheManager create() {
    return new DefaultCacheManager();
  }

}