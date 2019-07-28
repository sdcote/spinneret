package coyote.spinneret;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 *
 */
public class StatusTest {

  @Test
  public void test() {
    String status = Status.getStatus(429);
    assertTrue(StringUtil.isNotBlank(status));
    assertEquals(Status.TOO_MANY_REQUESTS.getDescription(), status);
  }

}
