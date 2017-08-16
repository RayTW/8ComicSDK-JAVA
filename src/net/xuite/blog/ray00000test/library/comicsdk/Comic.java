package net.xuite.blog.ray00000test.library.comicsdk;

import java.util.List;

/**
 * 以每個漫畫id(編號)分類對應的漫畫物件
 * 
 * @author Ray Lee Created on 2017/08/11
 */
public class Comic {
	private String mId;// 漫畫編號
	private String mIconUrl;// 漫畫封面大圖網址
	private String mSmallIconUrl;// 漫畫封面小圖網址
	private String mName;// 漫畫名稱
	private String mAuthor;// 漫畫作者
	private String mLatestUpdateDateTime;// 最後更新的時間
	private String mDescription;// 漫畫描述
	private String mNewestEpisode;//最新集數
	private List<Episode> mEpisodes;// 漫畫集數列表

	public String getId() {
		return mId;
	}

	public void setId(String id) {
		mId = id;
	}

	public String getIconUrl() {
		return mIconUrl;
	}

	public void setIconUrl(String iconUrl) {
		mIconUrl = iconUrl;
	}

	public String getSmallIconUrl() {
		return mSmallIconUrl;
	}

	public void setSmallIconUrl(String smallIconUrl) {
		mSmallIconUrl = smallIconUrl;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public String getAuthor() {
		return mAuthor;
	}

	public void setAuthor(String author) {
		mAuthor = author;
	}

	public String getLatestUpdateDateTime() {
		return mLatestUpdateDateTime;
	}

	public void setLatestUpdateDateTime(String latestUpdateDateTime) {
		mLatestUpdateDateTime = latestUpdateDateTime;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		mDescription = description;
	}
	
	public String getNewestEpisode() {
		return mNewestEpisode;
	}

	public void setNewestEpisode(String newestEpisode) {
		mNewestEpisode = newestEpisode;
	}

	public List<Episode> getEpisodes() {
		return mEpisodes;
	}

	public void setEpisodes(List<Episode> episodes) {
		mEpisodes = episodes;
	}

}
