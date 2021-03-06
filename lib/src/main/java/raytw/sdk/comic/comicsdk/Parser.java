package raytw.sdk.comic.comicsdk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import raytw.sdk.comic.net.EasyHttp.Response;
import raytw.sdk.comic.util.StringUtility;

/**
 * 解析從漫畫網站讀取到的html格式字串內容，將內容轉換成Comic、Episode物件.
 *
 * @author Ray Lee Created on 2017/08/11
 */
public class Parser {
  private static final int NOT_FOUND = -1;
  private static final String UNICODE_9 = String.valueOf('\t');

  /**
   * 取得全部漫畫.
   *
   * @param htmlString 全部漫畫html
   * @param config 設定檔
   * @return 全部漫畫
   */
  public List<Comic> allComics(String htmlString, Config config) {
    String html = htmlString;
    String commicIdBegin = "showthumb(";
    String commicIdEnd = ",this);";
    int comicIdBeginIndex = NOT_FOUND;
    int comicIdEndIndex = NOT_FOUND;
    String comicId = "";
    String commicNameBegin = "hidethumb();'>";
    String commicNameEnd = "</a></td>";
    String comicName = "";
    int comicNameBeginIndex = NOT_FOUND;
    int comicNameEndIndex = NOT_FOUND;
    List<Comic> comicAry = new ArrayList<Comic>();

    while (true) {
      comicIdBeginIndex = html.indexOf(commicIdBegin);
      comicIdEndIndex = html.indexOf(commicIdEnd);

      if (comicIdBeginIndex == NOT_FOUND || comicIdEndIndex == NOT_FOUND) {
        break;
      }
      comicId = html.substring(comicIdBeginIndex + commicIdBegin.length(), comicIdEndIndex);
      html = html.substring(comicIdEndIndex + commicIdEnd.length());
      comicNameBeginIndex = html.indexOf(commicNameBegin);
      comicNameEndIndex = html.indexOf(commicNameEnd);

      if (comicNameBeginIndex == NOT_FOUND || comicNameEndIndex == NOT_FOUND) {
        break;
      }
      comicName = html.substring(comicNameBeginIndex + commicNameBegin.length(), comicNameEndIndex);
      Comic comic = new Comic();
      comic.setId(comicId);
      comic.setName(comicName);
      comic.setIconUrl(comicId);
      comic.setSmallIconUrl(comicId);
      comicAry.add(comic);
    }

    return comicAry;
  }

  /**
   * 解析單筆最新漫畫資訊.
   *
   * @param comicAry 漫畫列表
   * @param htmlString html
   * @return 漫畫列表
   */
  public List<Comic> newestComics(List<Comic> comicAry, String htmlString) {
    String findTagStart = "<a href=\"/html/";
    String findTagEnd = ".html";
    String nameTagStart = "title=\"";
    String nameTagEnd = "\">";
    String actTagStart = "<num>";
    String actTagEnd = "</num>";
    String[] htmlList = htmlString.split(System.lineSeparator());
    String html = null;

    nextComic:
    for (int i = 0; i < htmlList.length; i++) {
      html = htmlList[i];
      if (html.indexOf(findTagStart) != NOT_FOUND) {
        int start = html.indexOf(findTagStart);

        if (start != NOT_FOUND) {
          // 解析漫畫編號
          int end = html.indexOf(findTagEnd);
          String comicId = html.substring(start + findTagStart.length(), end);
          // 解析最新集數
          String act = null;

          for (int j = i + 1; j < htmlList.length; j++) {
            act = StringUtility.substring(htmlList[j], actTagStart, actTagEnd);
            if (act != null) {
              break;
            }
          }

          for (Comic c : comicAry) {
            if (c.getId().equals(comicId)) {
              continue nextComic;
            }
          }

          // 解析漫畫名稱
          String comicName = StringUtility.substring(html, nameTagStart, nameTagEnd);

          Comic comic = new Comic();
          comic.setId(comicId);
          comic.setName(comicName);
          comic.setIconUrl(comicId);
          comic.setSmallIconUrl(comicId);
          comic.setNewestEpisode(act);
          comicAry.add(comic);
        }
      }
    }
    return comicAry;
  }

  /**
   * 解析漫畫作者、說明、簡介等等資訊.
   *
   * @param htmlString html
   * @param comic 漫畫
   * @return 漫畫
   */
  public Comic comicDetail(String htmlString, Comic comic) {
    int nameTagLower = NOT_FOUND;
    int findCviewUpper = NOT_FOUND;
    String[] html = htmlString.split(System.lineSeparator());
    String findCview = "cview(";
    String findDeaitlTag = "style=\"line-height:25px\">";
    String authorTag = "作者：</td>";
    String updateTag = "更新：</td>";
    String nameTag = ");return";
    String finishTag = "class=\"hide\"";
    List<Episode> episodes = new ArrayList<Episode>();
    String data = null;
    String[] dataAry = null;
    String txt = null;
    String ch = null;
    String url = null;
    String catid = null;
    String copyright = null;
    boolean isFinishEpisode = false;

    for (int i = 0; i < html.length; i++) {
      txt = html[i];
      if (!isFinishEpisode) {
        isFinishEpisode = txt.indexOf(finishTag) != NOT_FOUND;
      }

      findCviewUpper = txt.indexOf(findCview);

      if (findCviewUpper != NOT_FOUND) {
        if (isFinishEpisode) {
          continue;
        }

        nameTagLower = txt.indexOf(nameTag);
        data = txt.substring(findCviewUpper + findCview.length(), nameTagLower);
        data = data.replaceAll("'", "");
        dataAry = data.split("[,]");
        ch = dataAry[0].split("[-]")[1].replaceFirst(".html", "");
        url = dataAry[0].replaceFirst(".html", "").replaceFirst("-", ".html?ch=");
        catid = dataAry[1];
        copyright = dataAry[2];

        // 解析集數名稱
        if (nameTagLower != NOT_FOUND) {
          String episodeName = html[i + 1];
          episodeName = removeScriptsTag(episodeName);
          episodeName = replaceTag(episodeName);
          episodeName = episodeName.replaceAll(UNICODE_9, "");
          episodeName = episodeName.trim();

          if (!episodeName.isEmpty()) {
            Episode episode = new Episode();
            episode.setName(episodeName);
            episode.setUrl(url);
            episode.setCatid(catid);
            episode.setCopyright(copyright);
            episode.setCh(Integer.parseInt(ch));
            episodes.add(episode);
          }
        }
      } else {
        // 解析漫畫簡介
        if (comic.getDescription() == null) {
          String detail = StringUtility.substring(txt, findDeaitlTag, "</td>");

          if (detail != null) {
            comic.setDescription(detail);
          }
        }
        // 解析作者
        if (comic.getAuthor() == null) {
          if (txt.indexOf(authorTag) != NOT_FOUND) {
            comic.setAuthor(replaceTag(html[i + 1]));
          }
        } else if (comic.getLatestUpdateDateTime() == null) {
          // 解析最新更新日期
          if (txt.indexOf(updateTag) != NOT_FOUND) {
            comic.setLatestUpdateDateTime(replaceTag(html[i + 1]));
          }
        }
      }
    }
    comic.setEpisodes(episodes);

    return comic;
  }

  /**
   * 解析漫畫圖片存放的伺服器host列表.
   *
   * @param response request的response
   * @return 網址
   */
  public String cviewJs(Response response) {
    return "https://comicbus.live/online/a-";
  }

  /**
   * 漫畫單集(卷)描述.
   *
   * @param htmlString html
   * @param episode 集數物件
   * @return 集數
   */
  public Episode episodeDetail(String htmlString, Episode episode) {
    String[] html = htmlString.split(System.lineSeparator());
    String startTagChs = "var chs=";
    String endTagChs = ";var ti=";
    String startTagTi = "var ti=";
    String endTagTi = ";var cs=";
    String startTagCs = "var cs='";
    String endTagCs = "';for(var";

    for (String txt : html) {
      String chs = StringUtility.substring(txt, startTagChs, endTagChs);
      String ti = StringUtility.substring(txt, startTagTi, endTagTi);
      String cs = StringUtility.substring(txt, startTagCs, endTagCs);

      if (chs != null) {
        episode.setChs(Integer.parseInt(chs));
      }
      if (ti != null) {
        episode.setTi(Integer.parseInt(ti));
      }
      if (cs != null) {
        episode.setCs(cs);
        episode.setSourceHtml(txt);
        break;
      }
    }
    return episode;
  }

  /**
   * 搜尋漫畫，回傳此次搜尋結果的總頁數.
   *
   * @param htmlString 漫畫搜尋網頁原始html
   * @param listener 搜尋結果
   * @return 回傳此次搜尋的總頁數
   */
  public int searchComic(String htmlString, Consumer<List<Comic>> listener) {
    String[] html = htmlString.split(System.lineSeparator());
    List<Comic> comics = new ArrayList<Comic>();
    String text = null;
    String comicId = null;
    String comicName = null;
    String comidIdBegin = "<a href=\"/html/";
    String comidIdEnd = ".html\">";
    String comidNameBegin = "<b><font color=\"#0099CC\">";
    String comidNameEnd = "</font></b>";
    String pageBegin = "&page=";
    String pageEnd = "\"><img src=/images/pagelast.gif";
    Comic comic = null;
    int maxPage = 1;

    for (int i = 0; i < html.length; i++) {
      text = html[i];

      comicId = StringUtility.substring(text, comidIdBegin, comidIdEnd);

      if (comicId != null) {
        text = html[i + 9];
        comicName = StringUtility.substring(text, comidNameBegin, comidNameEnd);
        comicName = replaceTag(comicName);

        // 再找漫畫id
        for (int j = i + 1; j < html.length; j++) {
          text = html[j];
          comicId = StringUtility.substring(text, comidIdBegin, comidIdEnd);

          if (comicId != null) {
            // 從找到漫畫id的下一行找尋下一本漫畫名稱
            i = j + 1;
            break;
          }
        }

        comic = new Comic();
        comic.setId(comicId);
        comic.setName(comicName);
        comic.setIconUrl(comicId);
        comic.setSmallIconUrl(comicId);
        comics.add(comic);
      } else {
        comicName = null;
        if (text.indexOf(pageBegin) != NOT_FOUND) {
          String p = StringUtility.lastSubstring(text, pageBegin, pageEnd);

          try {
            maxPage = Integer.parseInt(p);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }

    if (listener != null) {
      listener.accept(comics);
    }

    return maxPage;
  }

  public List<String> quickSearchComic(String htmlString) {
    String[] list = htmlString.split("[|]");
    return Arrays.asList(list);
  }

  /**
   * 去掉"&lt;"開頭與"\&gt;"結尾的字串.
   *
   * @param txt 原始字串
   * @return 處理過字串
   */
  public String replaceTag(String txt) {
    if (txt == null) {
      return "";
    }
    StringBuffer data = new StringBuffer();
    char st = '<';
    char ed = '>';
    char[] charAry = txt.toCharArray();
    boolean check = false;
    for (int i = 0; i < charAry.length; i++) {
      char c = charAry[i];

      if (c == st) {
        check = true;
      } else if (c == ed) {
        check = false;
        continue;
      }
      if (check) {
        continue;
      }
      if (c == '\r' || c == '\n') {
        continue;
      }
      data.append(c);
    }
    return data.toString();
  }

  /**
   * 去除java scripts語法tag(包含tag裡面的程式).
   *
   * @param string 字串
   * @return 處理過字串
   */
  public String removeScriptsTag(String string) {
    String ret = string;
    String beginStr = "<script>";
    String endStr = "</script>";
    int headIndex = string.indexOf(beginStr);
    int footIndex = string.indexOf(endStr);

    if (headIndex != -1 && footIndex != -1) {
      ret = string.substring(0, headIndex);
      ret += string.substring(footIndex + endStr.length(), string.length());
    }
    return ret;
  }
}
