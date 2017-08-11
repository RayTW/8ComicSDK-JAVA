package net.xuite.blog.ray00000test.library.comicsdk;

/**
 * 
 * @author Ray Lee Created on 2017/08/11
 */
public class Config {
	public final String mComicHost = "http://www.comicbus.com/";
	public final String mAllUrl = mComicHost + "comic/all.html";
	public final String mCviewJSUrl = mComicHost + "js/comicview.js";
	private final String mSmallIconUrl = mComicHost + "pics/0/%@s.jpg";
	private final String mIconUrl = mComicHost + "pics/0/%@.jpg";
	private final String mComicDetail = mComicHost + "html/%@.html";

	public String getComicDetailUrl(String comicId) {
		return String.format(mComicDetail, comicId);
	}

	public String getComicIconUrl(String comicId) {
		return String.format(mIconUrl, comicId);
	}

	public String getComicSmallIconUrl(String comicId) {
		return String.format(mSmallIconUrl, comicId);
	}
}
