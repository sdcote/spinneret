package coyote.spinneret;

import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;


/**
 *
 */
public class UriUtilTest {

  String fs = System.getProperty("file.separator");


  // @Test
  public void testParse() {
    fail("Not yet implemented");
  }


  //  @Test
  public void testIsFile() {
    fail("Not yet implemented");
  }


  // @Test
  public void testIsJar() {
    fail("Not yet implemented");
  }


  // @Test
  public void testGetFilePath() {
    fail("Not yet implemented");
  }


  // @Test
  public void testNormalizePath() {
    fail("Not yet implemented");
  }


  // @Test
  public void testNormalizeSlashes() {
    fail("Not yet implemented");
  }


  // @Test
  public void testRemoveRelations() {
    fail("Not yet implemented");
  }


  // @Test
  public void testGetFile() {
    fail("Not yet implemented");
  }


  @Test
  public void testGetFilePath1() {
    try {
      URI uri = new URI("jar:file:/c:/almanac/my.jar!/com/mycompany/MyClass.class");
      String path = UriUtil.getFilePath(uri);
      System.out.println("Jar filepath 1 = '" + path + "'");
    } catch (URISyntaxException e) {
      fail(e.getMessage());
    }
  }


  @Test
  public void testGetFilePath2() {
    try {
      URI uri = new URI("jar:file:/c:/almanac/my.jar!/");
      String path = UriUtil.getFilePath(uri);
      System.out.println("Jar filepath 2 = '" + path + "'");
    } catch (URISyntaxException e) {
      fail(e.getMessage());
    }
  }


  @Test
  public void testGetFilePath3() {
    try {
      URI uri = new URI("jar:/c:/almanac/my.jar!/");
      String path = UriUtil.getFilePath(uri);
      System.out.println("Jar filepath 3 = '" + path + "'");
    } catch (URISyntaxException e) {
      fail(e.getMessage());
    }
  }


  @Test
  public void testGetFilePath4() {
    try {
      URI uri = new URI("jar://c:/almanac/my.jar");
      String path = UriUtil.getFilePath(uri);
      System.out.println("Jar filepath 4 = '" + path + "'");
    } catch (URISyntaxException e) {
      fail(e.getMessage());
    }
  }


  @Test
  public void testFileURIFour() {
    try {
      URI uri = new URI("file://config/node3.xml");
      assertTrue(uri.getScheme().equals("file"));
      assertTrue("Not proper path", UriUtil.getFilePath(uri).equals("config" + fs + "node3.xml"));
    } catch (URISyntaxException e) {
      fail(e.getMessage());
    }
  }


  @Test
  public void testFileURIFive() {
    try {
      URI uri = new URI("file:///C:/Documents%20and%20Settings/CoteS/EXML-7.0b2.zip");
      assertTrue(uri.getScheme().equals("file"));
      assertTrue("Not proper path", UriUtil.getFilePath(uri).equals("C:" + fs + "Documents and Settings" + fs + "CoteS" + fs + "EXML-7.0b2.zip"));
    } catch (URISyntaxException e) {
      fail(e.getMessage());
    }
  }


  // TODO: Need to add tests for URI without schemes  =>  filename.txt == file://filename.txt

  @Test
  public void testFileURISix() {
    try {
      URI uri = new URI("/home/sdcote/filename.txt");
      String path = UriUtil.getFilePath(uri);
      System.out.println(path);
    } catch (URISyntaxException e) {
      fail(e.getMessage());
    }
  }


  @Test
  public void testGetScheme() {
    try {
      URI uri = new URI("blp://linkage.bralyn.net/");
      assertTrue(uri.getScheme().equalsIgnoreCase("blp"));
    } catch (Exception ex) {
    }
  }


  @Test
  public void testGetHost() {
    try {
      URI uri = new URI("blp://linkage.bralyn.net/");
      assertTrue(uri.getHost().equalsIgnoreCase("linkage.bralyn.net"));
    } catch (URISyntaxException e) {
      fail(e.getMessage());
    }
  }


  @Test
  public void testGetPort() {
    try {
      URI uri = new URI("blp://linkage.bralyn.net/");
      assertTrue(uri.getPort() == -1);
    } catch (URISyntaxException e) {
      fail(e.getMessage());
    }
  }


  @Test
  public void testGetPort2() {
    try {
      URI uri = new URI("blp://linkage.bralyn.net:5529/root/home/stuff");
      assertTrue(uri.getPort() == 5529);
    } catch (URISyntaxException e) {
      fail(e.getMessage());
    }
  }


  @Test
  public void testGetPort3() {
    try {
      URI uri = new URI("sftp://linkage.bralyn.net/root/home/stuff");
      assertTrue(UriUtil.getPort(uri) == 22);
    } catch (URISyntaxException e) {
      fail(e.getMessage());
    }
  }


  @Test
  public void testGetPath() {
    try {
      URI uri = new URI("blp://linkage.bralyn.net:5529/root/home/stuff");
      assertTrue(uri.getPath().equalsIgnoreCase("/root/home/stuff"));
    } catch (URISyntaxException e) {
      fail(e.getMessage());
    }
  }


  //@Test
  public void testGetQuery() {
    try {
      URI uri = new URI("blp://linkage.bralyn.net:5529/root/home/stuff#Junk?name=bob&job=coder");
      assertTrue(uri.getQuery().equalsIgnoreCase("name=bob&job=coder"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  //@Test
  public void testGetQueryParamenter() {
    try {
      URI uri = new URI("blp://linkage.bralyn.net:5529/root/home/stuff#MoreJunk?name=bob&job=coder");
      assertTrue(uri.getPort() == 5529);
      assertTrue(uri.getPath().equalsIgnoreCase("/root/home/stuff"));
      //assertTrue( uri.getQueryParamenter( "name" ).equals( "bob" ) );
      //assertTrue( uri.getQueryParamenter( "job" ).equals( "coder" ) );
      //assertTrue( uri.getQueryParamenter( "age" ) == null );
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  @Test
  public void testEncodeString1() {
    try {
      String encoded = UriUtil.encodeString("This has spaces, hyphens(-) and other punctuation.");
      assertTrue(encoded.equals("This+has+spaces%2C+hyphens%28%2D%29+and+other+punctuation%2E"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  @Test
  public void testDecodeString() {
    String decoded = UriUtil.decodeString("This+has+spaces%2C+hyphens%28%2D%29+and+other+punctuation%2E");
    assertTrue(decoded.equals("This has spaces, hyphens(-) and other punctuation."));
  }


  @Test
  public void testURIToFile1() {
    try {
      File homedir = new File(System.getProperty("user.home"));
      URI homeuri = FileUtil.getFileURI(homedir);

      File file = UriUtil.getFile(homeuri);
      assertTrue(System.getProperty("user.home").equals(file.getAbsolutePath()));
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }


  @Test
  public void testURIToFile2() {
    try {
      File homedir = new File(System.getProperty("user.home"));
      String path = "log" + fs + "test.log";
      String expected = System.getProperty("user.home") + fs + path;

      FileUtil.getFileURI(homedir);
      File dir = new File(homedir, path);
      URI uri = FileUtil.getFileURI(dir);
      File file = UriUtil.getFile(uri);

      assertTrue(file.getAbsolutePath().equals(expected));
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }


  @Test
  public void testURIToFile3() {
    try {
      File homedir = new File(System.getProperty("user.home"));
      String path = "log" + fs + "test.log";
      String expected = System.getProperty("user.home") + fs + path;

      File dir = new File(homedir, path);
      URI uri = FileUtil.getFileURI(dir);

      File file = UriUtil.getFile(uri);

      assertTrue(file.getAbsolutePath().equals(expected));
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }


  @Test
  public void testGetUser1() {
    try {
      URI uri = new URI("blp://sdcote:pickles@linkage.bralyn.net:5529/root/home/stuff");
      String expected = "sdcote";
      String user = UriUtil.getUser(uri);

      assertTrue(user.equals(expected));
    } catch (URISyntaxException e) {
      fail(e.getMessage());
    }
  }


  @Test
  public void testGetUser2() {
    try {
      URI uri = new URI("blp://sdcote@linkage.bralyn.net:5529/root/home/stuff");
      String expected = "sdcote";
      String user = UriUtil.getUser(uri);

      assertTrue(user.equals(expected));
    } catch (URISyntaxException e) {
      fail(e.getMessage());
    }
  }


  @Test
  public void testGetUser3() {
    try {
      URI uri = new URI("blp://:pickles@linkage.bralyn.net:5529/root/home/stuff");
      String user = UriUtil.getUser(uri);
      // System.out.println( "User='" + user + "'" );

      assertTrue(user == null);
    } catch (URISyntaxException e) {
      fail(e.getMessage());
    }
  }


  @Test
  public void testGetPassword1() {
    try {
      URI uri = new URI("blp://sdcote:pickles@linkage.bralyn.net:5529/root/home/stuff");
      String expected = "pickles";
      String password = UriUtil.getPassword(uri);

      assertTrue(password.equals(expected));
    } catch (URISyntaxException e) {
      fail(e.getMessage());
    }
  }


  @Test
  public void testGetPassword2() {
    try {
      URI uri = new URI("blp://sdcote@linkage.bralyn.net:5529/root/home/stuff");
      String password = UriUtil.getPassword(uri);

      assertTrue(password == null);
    } catch (URISyntaxException e) {
      fail(e.getMessage());
    }
  }


  @Test
  public void testGetHostAddress1() {
    try {
      URI uri = new URI("blp://sdcote@linkage.bralyn.net:5529/root/home/stuff");
      //assertTrue( UriUtil.getHostAddress( uri )!= null ); // DNS can break this test
      assertTrue(uri.getScheme().equals("blp"));
      assertTrue(uri.getRawUserInfo().equals("sdcote"));
      assertTrue(uri.getHost().equals("linkage.bralyn.net"));
      assertTrue(uri.getPort() == 5529);
      assertTrue(uri.getPath().equals("/root/home/stuff"));
    } catch (URISyntaxException e) {
      fail(e.getMessage());
    }
  }


  // @Test
  public void testStuff() {
    String encodedUri = UriUtil.encodeString("349685#");
    System.out.println(encodedUri);
    try {
      URI uri = new URI("sftp://cotes:349685%23@nlvmjt051.bralyn.net/home/cotes7/SnowStormInstall.jar");
      System.out.println(uri.getScheme());
      System.out.println(uri.getRawUserInfo());
      System.out.println(uri.getHost());
      System.out.println(uri.getPort());
      System.out.println(uri.getPath());
      System.out.println();

    } catch (URISyntaxException e) {
      fail(e.getMessage());
    }

  }


  @Test
  public void testGetBase() throws URISyntaxException {
    URI uri = new URI("http://server.com/cfg/bob.json");
    String name = UriUtil.getBase(uri);
    assertEquals("bob", name);

    uri = new URI("http://server.com/alice.xml");
    name = UriUtil.getBase(uri);
    assertEquals("alice", name);

    uri = new URI("http://server.com/trudy");
    name = UriUtil.getBase(uri);
    assertEquals("trudy", name);

    uri = new URI("http://tom");
    name = UriUtil.getBase(uri);
    assertEquals("tom", name);

    uri = new URI("file:///dick");
    name = UriUtil.getBase(uri);
    assertEquals("dick", name);

    uri = new URI("harry");
    name = UriUtil.getBase(uri);
    assertEquals("harry", name);
  }

}
