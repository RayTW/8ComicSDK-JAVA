package raytw.sdk.comic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Test;
import raytw.sdk.comic.comicsdk.Comic;
import raytw.sdk.comic.comicsdk.Episode;
import raytw.sdk.comic.comicsdk.R8Comic;

/**
 * 測試閴.
 *
 * @author ray
 */
public class R8ComicTest {

  @Test
  public void testGetAll() {
    final CountDownLatch signal = new CountDownLatch(1);
    final AtomicReference<List<Comic>> result = new AtomicReference<List<Comic>>();

    R8Comic.get()
        .getAll(
            comics -> {
              result.set(comics);

              signal.countDown();
            });
    try {
      signal.await();
      assertTrue(result.get().size() > 0);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testGetNewest() {
    final CountDownLatch signal = new CountDownLatch(1);
    final AtomicReference<List<Comic>> result = new AtomicReference<List<Comic>>();

    R8Comic.get()
        .getNewest(
            comics -> {
              result.set(comics);

              signal.countDown();
            });
    try {
      signal.await();
      assertTrue(result.get().size() > 0);
      for (Comic comic : result.get()) {
        System.out.println(comic.getId() + "," + comic.getName() + "," + comic.getNewestEpisode());
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testLoadComicDetail() {
    final CountDownLatch signal = new CountDownLatch(1);
    Comic comic = new Comic();
    comic.setId("10406");
    comic.setName("一拳超人");

    R8Comic.get()
        .loadComicDetail(
            comic,
            result -> {
              System.out.println("id[" + result.getId() + "]");
              System.out.println("getDescription[" + result.getDescription() + "]");
              System.out.println("getAuthor[" + result.getAuthor() + "]");
              System.out.println(
                  "getLatestUpdateDateTime[" + result.getLatestUpdateDateTime() + "]");
              System.out.println("getEpisodesSize[" + result.getEpisodes().size() + "]");
              assertTrue(result.getEpisodes().size() > 0);
              signal.countDown();
            });
    try {
      signal.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testLoadEpisodeDetail() {
    final CountDownLatch signal = new CountDownLatch(1);
    R8Comic.get()
        .loadSiteUrlList(
            hostList -> {
              Comic comic = new Comic();
              comic.setId("103");
              comic.setName("海賊王");

              R8Comic.get()
                  .loadComicDetail(
                      comic,
                      result -> {
                        System.out.println("id[" + result.getId() + "]");
                        System.out.println("getDescription[" + result.getDescription() + "]");
                        System.out.println("getAuthor[" + result.getAuthor() + "]");
                        System.out.println(
                            "getLatestUpdateDateTime[" + result.getLatestUpdateDateTime() + "]");
                        System.out.println("getEpisodesSize[" + result.getEpisodes().size() + "]");

                        if (result.getEpisodes().size() > 0) {
                          Episode episode = result.getEpisodes().get(0);
                          System.out.println("episode==" + episode);
                          String downloadHost = hostList + episode.getUrl();
                          System.out.println("downloadHost[" + downloadHost + "]");

                          episode.setUrl(downloadHost + episode.getUrl());

                          R8Comic.get()
                              .loadEpisodeDetail(
                                  episode,
                                  reloadEpisode -> {
                                    reloadEpisode.setUpPages();
                                    System.out.println(reloadEpisode);
                                    System.out.println(reloadEpisode.getImageUrlList());

                                    assertTrue(reloadEpisode.getPages() > 0);
                                    signal.countDown();
                                  });
                        }
                      });
            });
    try {
      signal.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testLoadSiteUrlList() {
    final CountDownLatch signal = new CountDownLatch(1);
    R8Comic.get()
        .loadSiteUrlList(
            result -> {
              System.out.println(result);
              assertNotNull(result);
              signal.countDown();
            });
    try {
      signal.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testQuickSearchComic() {
    final AtomicReference<List<String>> result = new AtomicReference<List<String>>();
    final CountDownLatch signal = new CountDownLatch(1);
    R8Comic.get()
        .quickSearchComic(
            "中",
            comicNameList -> {
              System.out.println(comicNameList);
              result.set(comicNameList);
              signal.countDown();
            });
    try {
      signal.await();
      assertTrue(result.get().size() > 0);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testSearchComicHaveComicUsingBig5() {
    final CountDownLatch signal = new CountDownLatch(1);
    final String searchKeyword = "食戟";
    final AtomicReference<List<Comic>> result = new AtomicReference<List<Comic>>();

    R8Comic.get()
        .searchComic(
            searchKeyword,
            comics -> {
              int index = 0;
              System.out.println("搜尋\"" + searchKeyword + "\"，筆數[" + comics.size() + "]");
              for (Comic comic : comics) {
                System.out.println(
                    "index["
                        + (++index)
                        + "],id["
                        + comic.getId()
                        + "], name["
                        + comic.getName()
                        + "]");
              }
              result.set(comics);
              signal.countDown();
            });
    try {
      signal.await();
      assertTrue(result.get().size() > 0);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testSearchComicEmpty() {
    final CountDownLatch signal = new CountDownLatch(1);
    final String searchKeyword = "ddddddd";
    R8Comic.get()
        .searchComic(
            searchKeyword,
            result -> {
              int index = 0;
              System.out.println("搜尋\"" + searchKeyword + "\"，筆數[" + result.size() + "]");
              for (Comic comic : result) {
                System.out.println(
                    "index["
                        + (++index)
                        + "],id["
                        + comic.getId()
                        + "], name["
                        + comic.getName()
                        + "]");
              }
              assertTrue(result.size() == 0);
              signal.countDown();
            });
    try {
      signal.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testEpisodesHasRepeat() {
    final CountDownLatch signal = new CountDownLatch(1);
    Comic comic = new Comic();
    comic.setId("10406");
    comic.setName("一拳超人");
    AtomicBoolean ref = new AtomicBoolean(false);

    R8Comic.get()
        .loadComicDetail(
            comic,
            result -> {
              System.out.println("getEpisodesSize[" + result.getEpisodes().size() + "]");

              for (Episode obj : result.getEpisodes()) {
                System.out.println(obj.getName());
                for (Episode obj2 : result.getEpisodes()) {
                  if (obj.hashCode() != obj2.hashCode() && obj.getName().equals(obj2.getName())) {
                    ref.set(true);
                  }
                }
              }

              signal.countDown();
            });
    try {
      signal.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertFalse(ref.get());
  }
}
