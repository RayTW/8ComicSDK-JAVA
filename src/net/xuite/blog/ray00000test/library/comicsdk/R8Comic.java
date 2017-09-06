package net.xuite.blog.ray00000test.library.comicsdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.xuite.blog.ray00000test.library.net.EasyHttp;
import net.xuite.blog.ray00000test.library.net.EasyHttp.Response;

/**
 * 
 * @author Ray Lee
 * 
 */
public class R8Comic {
	private static R8Comic sInstance = new R8Comic();
	private Config mConfig = new Config();
	private Parser mParser = new Parser();

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
	 **/
	public void getAll(OnLoadListener<List<Comic>> listener) {
		String htmlString = requestGetHttp(mConfig.mAllUrl);
		List<Comic> result = mParser.allComics(htmlString, mConfig);

		if (listener != null) {
			listener.onLoaded(result);
		}
	}
	
	/**
	 * 讀取最新有更新的漫畫編號、名稱
	 **/
	public void getNewest(OnLoadListener<List<Comic>> listener) {
		List<Comic> result = new ArrayList<Comic>();
		String htmlString = null;
		
		for(int i = 1; i <= mConfig.NEWEST_MAX_PAGE; i++){
			htmlString = requestGetHttp(mConfig.getNewestUrl(i));
			
			mParser.newestComics(result, htmlString);
		}
		

		if (listener != null) {
			listener.onLoaded(result);
		}
	}

	/**
	 * 讀取漫畫簡介、作者、最後更新日期、集數列表
	 **/
	public void loadComicDetail(Comic comic, OnLoadListener<Comic> listener) {
		String htmlString = requestGetHttp(mConfig.getComicDetailUrl(comic
				.getId()));
		Comic result = mParser.comicDetail(htmlString, comic);

		if (listener != null) {
			listener.onLoaded(result);
		}
	}

	/**
	 * 讀取漫畫簡介、作者、最後更新日期、集數列表
	 **/
	public void loadEpisodeDetail(Episode episode,
			OnLoadListener<Episode> listener) {
		String htmlString = requestGetHttp(episode.getUrl());
		Episode result = mParser.episodeDetail(htmlString, episode);

		if (listener != null) {
			listener.onLoaded(result);
		}
	}

	/**
	 * 讀取漫畫圖片實際存放的Server site網址列表
	 **/
	public void loadSiteUrlList(OnLoadListener<Map<String, String>> listener) {
		String htmlString = requestGetHttp(mConfig.mCviewJSUrl);
		Map<String, String> result = mParser.cviewJS(htmlString);

		if (listener != null) {
			listener.onLoaded(result);
		}
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

	private String requestGetHttp(String url) {
		String result = null;
		EasyHttp request = new EasyHttp.Builder()
				.setUrl(url)
				.setMethod("GET")
				.setIsRedirect(true)
				.setCharset("BIG5")
				.putHeader(
						"Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
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
}
