package raytw.sdk.comic.comicsdk;

import java.util.Random;
import raytw.sdk.comic.util.StringUtility;

/**
 * 設定檔.
 *
 * @author ray
 */
public class Config {
  public static final int NEW_MAX_PAGE = 5; // 最新漫畫列表頁數
  private final String comicHost = "www.comicbus.com";
  private final String comicHostUrl = "https://" + comicHost + "/";
  private final String allUrl = comicHostUrl + "comic/all.html";
  private final String cviewJsUrl = comicHostUrl + "js/comicview.js";
  private final String smallIconUrl = comicHostUrl + "pics/0/%ss.jpg";
  private final String iconUrl = comicHostUrl + "pics/0/%s.jpg";
  private final String comicDetail = comicHostUrl + "html/%s.html";
  private final String newestUrl = comicHostUrl + "comic/u-%d.html";
  private final String quickSearchUrl =
      comicHostUrl + "member/quicksearchjs.aspx?r=%.16f&t=item&o=id&k=%s";
  private final String searchUrl = comicHostUrl + "member/search.aspx?k=%s&page=%d";

  private Random random = new Random();

  public String getComicHost() {
    return comicHost;
  }

  public String getComicHostUrl() {
    return comicHostUrl;
  }

  public String getAllUrl() {
    return allUrl;
  }

  public String getCviewJsUrl() {
    return cviewJsUrl;
  }

  public String getComicDetailUrl(String comicId) {
    return String.format(comicDetail, comicId);
  }

  public String getComicIconUrl(String comicId) {
    return String.format(iconUrl, comicId);
  }

  public String getComicSmallIconUrl(String comicId) {
    return String.format(smallIconUrl, comicId);
  }

  public String getNewestUrl(int page) {
    return String.format(newestUrl, page);
  }

  public String getQuickSearchUrl(String keyword) {
    return String.format(
        quickSearchUrl, random.nextDouble(), StringUtility.urlencodeUsingGb2312(keyword));
  }

  public String getSearchUrl(String keyword, int page) {
    return String.format(searchUrl, StringUtility.urlencodeUsingBig5(keyword), page);
  }
}
