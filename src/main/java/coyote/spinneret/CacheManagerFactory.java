package coyote.spinneret;

/**
 * Factory to create cache managers.
 */
public interface CacheManagerFactory {

  /**
   * @return a new cache manager.
   */
  CacheManager create();

}