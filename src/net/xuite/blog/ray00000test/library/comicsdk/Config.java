package net.xuite.blog.ray00000test.library.comicsdk;

/**
 * 
 * @author Ray Lee Created on 2017/08/11
 */
public class Config {
	public static final String sComicHost = "http://www.comicbus.com/";
	public static final String sAllUrl = sComicHost + "comic/all.html";
	public static final String sCviewJSUrl = sComicHost + "js/comicview.js";
	private static final String sSmallIconUrl = sComicHost + "pics/0/%@s.jpg";
	private static final String sIconUrl = sComicHost + "pics/0/%@.jpg";
	private static final String sComicDetail = sComicHost + "html/%@.html";

	public String getComicDetailUrl(String comicId) {
		return String.format(sComicDetail, comicId);
	}

	public String getComicIconUrl(String comicId) {
		return String.format(sIconUrl, comicId);
	}

	public String getComicSmallIconUrl(String comicId) {
		return String.format(sSmallIconUrl, comicId);
	}
}
