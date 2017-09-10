package net.xuite.blog.ray00000test.library.comicsdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import net.xuite.blog.ray00000test.library.net.EasyHttp;
import net.xuite.blog.ray00000test.library.net.EasyHttp.Response;

/**
 * 
 * @author Ray Lee Created on 2017/08/11
 * 
 */
public class R8Comic {
	private static R8Comic sInstance = new R8Comic();
	private Config mConfig = new Config();
	private Parser mParser = new Parser();
	private ExecutorService mPool = Executors.newCachedThreadPool(new R8ComicThreadFactory());

	private R8Comic() {
	}

	public static R8Comic get() {
		return sInstance;
	}

	public void Config(Config config) {
		mConfig = config;
	}

	/**
	 * 讀取全部漫畫編號、名稱
	 * 
	 * @param listener
	 **/
	public void getAll(final OnLoadListener<List<Comic>> listener) {
		mPool.submit(new Runnable() {
			public void run() {
				String htmlString = requestGetHttp(mConfig.getAllUrl());
				List<Comic> result = mParser.allComics(htmlString, mConfig);

				if (listener != null) {
					listener.onLoaded(result);
				}
			}
		});

	}

	/**
	 * 讀取最新有更新的漫畫編號、名稱
	 * 
	 * @param listener
	 **/
	public void getNewest(final OnLoadListener<List<Comic>> listener) {

		mPool.submit(new Runnable() {
			public void run() {
				List<Comic> result = new ArrayList<Comic>();
				String htmlString = null;

				for (int i = 1; i <= mConfig.NEWEST_MAX_PAGE; i++) {
					htmlString = requestGetHttp(mConfig.getNewestUrl(i));

					mParser.newestComics(result, htmlString);
				}

				if (listener != null) {
					listener.onLoaded(result);
				}
			}
		});

	}

	/**
	 * 讀取漫畫簡介、作者、最後更新日期、集數列表
	 * 
	 * @param comic
	 *            一款漫畫
	 * @param listener
	 *            讀取一款漫畫完成後將會回呼
	 */
	public void loadComicDetail(final Comic comic,
			final OnLoadListener<Comic> listener) {

		mPool.submit(new Runnable() {
			public void run() {
				String htmlString = requestGetHttp(mConfig
						.getComicDetailUrl(comic.getId()));
				Comic result = mParser.comicDetail(htmlString, comic);

				if (listener != null) {
					listener.onLoaded(result);
				}
			}
		});
	}

	/**
	 * 預先戴入一集(卷)漫畫相關資料
	 * 
	 * @param episode
	 *            一集(卷)漫畫
	 * @param listener
	 *            讀取完成後將會回呼
	 */
	public void loadEpisodeDetail(final Episode episode,
			final OnLoadListener<Episode> listener) {
		mPool.submit(new Runnable() {
			public void run() {
				String htmlString = requestGetHttp(episode.getUrl());
				Episode result = mParser.episodeDetail(htmlString, episode);

				if (listener != null) {
					listener.onLoaded(result);
				}
			}
		});

	}

	/**
	 * 讀取漫畫圖片實際存放的Server site網址列表
	 * 
	 * @param listener
	 *            讀取完成後將會回呼
	 **/
	public void loadSiteUrlList(
			final OnLoadListener<Map<String, String>> listener) {

		mPool.submit(new Runnable() {
			public void run() {
				String htmlString = requestGetHttp(mConfig.getCviewJSUrl());
				Map<String, String> result = mParser.cviewJS(htmlString);

				if (listener != null) {
					listener.onLoaded(result);
				}
			}
		});
	}

	/**
	 * 搜尋漫畫，搜尋到的漫畫僅有id、name，不包含漫畫簡介、集數等等資訊
	 * 
	 * @param keyword
	 *            搜尋漫畫的關鍵字，比如"海賊王"
	 * @param listener
	 */
	public void searchComic(final String keyword,
			final OnLoadListener<List<Comic>> listener) {

		mPool.submit(new Runnable() {
			public void run() {
				final List<Comic> result = new ArrayList<Comic>();

				// 先搜尋一次
				String htmlString = requestGetHttp(mConfig.getSearchUrl(
						keyword, 1));
				int maxPage = mParser.searchComic(htmlString,
						new OnLoadListener<List<Comic>>() {

							@Override
							public void onLoaded(List<Comic> comics) {
								if (comics.isEmpty()) {
									return;
								}
								result.addAll(comics);
							}

						});

				// 若搜尋後，總頁數還有結果筆數，則將進行逐頁讀取
				if (maxPage > 1) {
					for (int i = 2; i < maxPage; i++) {
						htmlString = requestGetHttp(mConfig.getSearchUrl(
								keyword, i));
						mParser.searchComic(htmlString,
								new OnLoadListener<List<Comic>>() {

									@Override
									public void onLoaded(List<Comic> comics) {
										if (comics.isEmpty()) {
											return;
										}
										result.addAll(comics);
									}

								});
					}
				}

				if (listener != null) {
					listener.onLoaded(result);
				}
			}
		});

	}

	/**
	 * 快速搜尋漫畫名稱
	 * 
	 * @param keyword
	 *            搜尋漫畫的關鍵字，比如"海賊王"
	 * @param listener
	 **/
	public void quickSearchComic(final String keyword,
			final OnLoadListener<List<String>> listener) {

		mPool.submit(new Runnable() {
			public void run() {
				String htmlString = requestGetHttp(mConfig
						.getQuickSearchUrl(keyword));
				List<String> result = mParser.quickSearchComic(htmlString);

				if (listener != null) {
					listener.onLoaded(result);
				}
			}
		});
	}

	public Parser getParser() {
		return mParser;
	}

	public Config getConfig() {
		return mConfig;
	}

	public static interface OnLoadListener<T> {
		public abstract void onLoaded(T result);
	}

	//預設讀取html回來後，用big5解析
	private String requestGetHttp(String url) {
		String result = null;
		EasyHttp request = new EasyHttp.Builder()
		.setUrl(url)
		.setMethod("GET")
		.setIsRedirect(true)
		.setWriteCharset("BIG5")
		.setReadCharset("BIG5")
		.setReferer(mConfig.getComicHost())
		.putHeader(
				"Accept",
				"*/*")
				.putHeader("Accept-Encoding", "gzip, deflate, br")
				.setUserAgent(
						"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36")
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
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setName("R8ComicThread-" + t.hashCode());
			t.setPriority(7);
			return t;
		}
	}
}
