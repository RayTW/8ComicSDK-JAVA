package raytw.sdk.comic.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * 測試類.
 *
 * @author ray
 */
public class StringUtilityTest {

  @Test
  public void testSubstring() {
    String result = StringUtility.substring("aabbccdd", "aa", "d");
    System.out.println(result);
    assertEquals("bbcc", result);
  }

  @Test
  public void testUrlencodeUsingBig5isValid() {
    String str = "中";
    String checkResult = "%A4%A4";
    String encodeStr = StringUtility.urlencodeUsingBig5(str);

    System.out.println(encodeStr);

    assertEquals(encodeStr, checkResult);
  }
}
