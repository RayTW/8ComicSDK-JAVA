package raytw.sdk.comic.comicsdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;
import raytw.sdk.comic.net.EasyHttp;
import raytw.sdk.comic.net.EasyHttp.Response;

/**
 * 讀取漫畫核心類.
 *
 * @author ray
 */
public class R8Comic {
  private static R8Comic sInstance = new R8Comic();
  private Config config = new Config();
  private Parser parser = new Parser();
  private ExecutorService pool = Executors.newCachedThreadPool(new R8ComicThreadFactory());

  private R8Comic() {}

  public static R8Comic get() {
    return sInstance;
  }

  /**
   * 讀取全部漫畫編號、名稱.
   *
   * @param listener 漫畫列表
   */
  public void getAll(final Consumer<List<Comic>> listener) {
    pool.submit(
        new Runnable() {
          @Override
          public void run() {
            String htmlString = requestGetHttp(config.getAllUrl());
            List<Comic> result = parser.allComics(htmlString, config);

            if (listener != null) {
              listener.accept(result);
            }
          }
        });
  }

  /**
   * 讀取最新有更新的漫畫編號、名稱.
   *
   * @param listener 漫畫
   */
  public void getNewest(final Consumer<List<Comic>> listener) {

    pool.submit(
        new Runnable() {
          @Override
          public void run() {
            List<Comic> result = new ArrayList<Comic>();
            String htmlString = null;

            for (int i = 1; i <= Config.NEW_MAX_PAGE; i++) {
              htmlString = requestGetHttp(config.getNewestUrl(i));

              parser.newestComics(result, htmlString);
            }

            if (listener != null) {
              listener.accept(result);
            }
          }
        });
  }

  /**
   * 讀取漫畫簡介、作者、最後更新日期、集數列表.
   *
   * @param comic 一款漫畫
   * @param listener 讀取一款漫畫完成後將會回呼
   */
  public void loadComicDetail(final Comic comic, final Consumer<Comic> listener) {

    pool.submit(
        new Runnable() {
          @Override
          public void run() {
            String htmlString = requestGetHttp(config.getComicDetailUrl(comic.getId()));
            Comic result = parser.comicDetail(htmlString, comic);

            if (listener != null) {
              listener.accept(result);
            }
          }
        });
  }

  /**
   * 預先戴入一集(卷)漫畫相關資料.
   *
   * @param episode 一集(卷)漫畫
   * @param listener 讀取完成後將會回呼
   */
  public void loadEpisodeDetail(final Episode episode, final Consumer<Episode> listener) {
    pool.submit(
        new Runnable() {
          @Override
          public void run() {
            String htmlString = requestGetHttp(episode.getUrl());
            Episode result = parser.episodeDetail(htmlString, episode);

            if (listener != null) {
              listener.accept(result);
            }
          }
        });
  }

  /**
   * 讀取漫畫圖片實際存放的Server site網址列表.
   *
   * @param listener 讀取完成後將會回呼
   */
  public void loadSiteUrlList(final Consumer<String> listener) {

    pool.submit(
        new Runnable() {
          @Override
          public void run() {
            EasyHttp request =
                new EasyHttp.Builder()
                    .setUrl(config.getCviewJsUrl())
                    .setMethod("GET")
                    .setIsRedirect(true)
                    .setWriteCharset("BIG5")
                    .setReadCharset("BIG5")
                    .setHost(config.getComicHost())
                    .setReferer(config.getComicHostUrl())
                    .putHeader("Accept", "*/*")
                    .putHeader("Accept-Encoding", "gzip, deflate, br")
                    .setUserAgent(
                        "Mozilla/5.0 (Windows NT 6.1; Win64; x64) "
                            + "AppleWebKit/537.36 (KHTML, like Gecko) "
                            + "Chrome/60.0.3112.90 Safari/537.36")
                    .build();

            try {
              Response response = request.connect();

              String result = parser.cviewJs(response);

              if (listener != null) {
                listener.accept(result);
              }
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        });
  }

  /**
   * 搜尋漫畫，搜尋到的漫畫僅有id、name，不包含漫畫簡介、集數等等資訊.
   *
   * @param keyword 搜尋漫畫的關鍵字，比如"海賊王"
   * @param listener 多本漫畫書
   */
  public void searchComic(final String keyword, final Consumer<List<Comic>> listener) {

    pool.submit(
        new Runnable() {
          @Override
          public void run() {
            final List<Comic> result = new ArrayList<Comic>();

            // 先搜尋一次
            String htmlString = requestGetHttp(config.getSearchUrl(keyword, 1));
            int maxPage =
                parser.searchComic(
                    htmlString,
                    new Consumer<List<Comic>>() {

                      @Override
                      public void accept(List<Comic> comics) {
                        if (comics.isEmpty()) {
                          return;
                        }
                        result.addAll(comics);
                      }
                    });

            // 若搜尋後，總頁數還有結果筆數，則將進行逐頁讀取
            if (maxPage > 1) {
              for (int i = 2; i < maxPage; i++) {
                htmlString = requestGetHttp(config.getSearchUrl(keyword, i));
                parser.searchComic(
                    htmlString,
                    comics -> {
                      if (comics.isEmpty()) {
                        return;
                      }
                      result.addAll(comics);
                    });
              }
            }

            if (listener != null) {
              listener.accept(result);
            }
          }
        });
  }

  /**
   * 快速搜尋漫畫名稱.
   *
   * @param keyword 搜尋漫畫的關鍵字，比如"海賊王"
   * @param listener 漫畫列表
   */
  public void quickSearchComic(final String keyword, final Consumer<List<String>> listener) {

    pool.submit(
        new Runnable() {
          @Override
          public void run() {
            String htmlString = requestGetHttp(config.getQuickSearchUrl(keyword));
            List<String> result = parser.quickSearchComic(htmlString);

            if (listener != null) {
              listener.accept(result);
            }
          }
        });
  }

  public Parser getParser() {
    return parser;
  }

  public Config getConfig() {
    return config;
  }

  // 預設讀取html回來後，用big5解析
  private String requestGetHttp(String url) {
    String result = null;
    EasyHttp request =
        new EasyHttp.Builder()
            .setUrl(url)
            .setMethod("GET")
            .setIsRedirect(true)
            .setWriteCharset("BIG5")
            .setReadCharset("BIG5")
            .setHost(config.getComicHost())
            .setReferer(config.getComicHostUrl())
            .putHeader("Accept", "*/*")
            .putHeader("Accept-Encoding", "gzip, deflate, br")
            .setUserAgent(
                "Mozilla/5.0 (Windows NT 6.1;"
                    + " Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)"
                    + " Chrome/60.0.3112.90 Safari/537.36")
            .build();

    try {
      Response response = request.connect();
      result = response.getBody();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return result;
  }

  class R8ComicThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
      Thread t = new Thread(r);
      t.setName("R8ComicThread-" + t.hashCode());
      t.setPriority(7);
      return t;
    }
  }
}
