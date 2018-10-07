package net.xuite.blog.ray00000test.library.comicsdk;

import java.util.Random;

import net.xuite.blog.ray00000test.library.util.StringUtility;

/**
 * 
 * @author Ray Lee Created on 2017/08/11
 */
public class Config {
	public final int NEWEST_MAX_PAGE = 5;// 最新漫畫列表頁數
	private final String mComicHost = "www.comicbus.com";
	private final String mComicHostUrl = "https://" + mComicHost + "/";
	private final String mAllUrl = mComicHostUrl + "comic/all.html";
	private final String mCviewJSUrl = mComicHostUrl + "js/comicview.js";
	private final String mSmallIconUrl = mComicHostUrl + "pics/0/%ss.jpg";
	private final String mIconUrl = mComicHostUrl + "pics/0/%s.jpg";
	private final String mComicDetail = mComicHostUrl + "html/%s.html";
	private final String mNewestUrl = mComicHostUrl + "comic/u-%d.html";
	private final String mQuickSearchUrl = mComicHostUrl + "member/quicksearchjs.aspx?r=%.16f&t=item&o=id&k=%s";
	private final String mSearchUrl = mComicHostUrl + "member/search.aspx?k=%s&page=%d";

	private Random mRandom = new Random();

	public String getComicHost() {
		return mComicHost;
	}

	public String getComicHostUrl() {
		return mComicHostUrl;
	}

	public String getAllUrl() {
		return mAllUrl;
	}

	public String getCviewJSUrl() {
		return mCviewJSUrl;
	}

	public String getComicDetailUrl(String comicId) {
		return String.format(mComicDetail, comicId);
	}

	public String getComicIconUrl(String comicId) {
		return String.format(mIconUrl, comicId);
	}

	public String getComicSmallIconUrl(String comicId) {
		return String.format(mSmallIconUrl, comicId);
	}

	public String getNewestUrl(int page) {
		return String.format(mNewestUrl, page);
	}

	public String getQuickSearchUrl(String keyword) {
		return String.format(mQuickSearchUrl, mRandom.nextDouble(), StringUtility.urlencodeUsingGB2312(keyword));
	}

	public String getSearchUrl(String keyword, int page) {
		return String.format(mSearchUrl, StringUtility.urlencodeUsingBIG5(keyword), page);
	}
}
