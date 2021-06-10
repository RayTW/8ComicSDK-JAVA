package raytw.sdk.comic.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 工具類.
 *
 * @author ray
 */
public class StringUtility {
  /**
   * 將會用upperStringFoot字串與lowerStringHead字串，尋找在source裡是否有相符的字串，若都有找到index後， 會將source裡(upperIndex +
   * upperStringFoot.length())至lowerStringHead的index符進行截字串
   *
   * <p>{@code String result = StringUtility.substring("aabbccdd", "aa", "d"); result將會是"bbcc" }
   *
   * @param source 原始字串
   * @param upperStringFoot 後字串
   * @param lowerStringHead 前字串
   */
  public static String substring(String source, String upperStringFoot, String lowerStringHead) {

    int upperIndex = source.indexOf(upperStringFoot);
    int lowerHeadIndex = source.indexOf(lowerStringHead);

    if (upperIndex != -1 && lowerHeadIndex != -1) {
      return source.substring(upperIndex + upperStringFoot.length(), lowerHeadIndex);
    }
    return null;
  }

  /**
   * 從後面開始分割字串.
   *
   * @param source 原始字串
   * @param upperStringFoot 後字串
   * @param lowerStringHead 前字串
   */
  public static String lastSubstring(
      String source, String upperStringFoot, String lowerStringHead) {

    int upperIndex = source.lastIndexOf(upperStringFoot);
    int lowerHeadIndex = source.lastIndexOf(lowerStringHead);

    if (upperIndex != -1 && lowerHeadIndex != -1) {
      return source.substring(upperIndex + upperStringFoot.length(), lowerHeadIndex);
    }
    return null;
  }

  /**
   * url encode.
   *
   * @param source 原始字串
   */
  public static String urlencodeUsingBig5(String source) {
    return urlencodeUsing(source, "BIG5");
  }

  /**
   * url decode.
   *
   * @param source 原始字串
   */
  public static String urlencodeUsingGb2312(String source) {
    return urlencodeUsing(source, "GB2312");
  }

  /**
   * url encode.
   *
   * @param source 原始字串
   * @param encode 編碼格式
   */
  public static String urlencodeUsing(String source, String encode) {
    try {
      return URLEncoder.encode(source, encode);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return "";
  }
}
