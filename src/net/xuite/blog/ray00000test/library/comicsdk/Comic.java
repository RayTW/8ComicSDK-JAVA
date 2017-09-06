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
	private String mNewestEpisode;// 最新集數
	private List<Episode> mEpisodes;// 漫畫集數列表

	/**
	 * 取得漫畫編號
	 * 
	 * @return
	 */
	public String getId() {
		return mId;
	}

	/**
	 * 設定漫畫編號
	 * 
	 * @param id
	 */
	public void setId(String id) {
		mId = id;
	}

	/**
	 * 取得漫畫封面大圖網址
	 * 
	 * @return
	 */
	public String getIconUrl() {
		return mIconUrl;
	}

	/**
	 * 設定漫畫封面大圖網址
	 * 
	 * @return
	 */
	public void setIconUrl(String iconUrl) {
		mIconUrl = iconUrl;
	}

	/**
	 * 取得漫畫封面小圖網址
	 * 
	 * @return
	 */
	public String getSmallIconUrl() {
		return mSmallIconUrl;
	}

	/**
	 * 取得漫畫封面小圖網址
	 * 
	 * @return
	 */
	public void setSmallIconUrl(String smallIconUrl) {
		mSmallIconUrl = smallIconUrl;
	}

	/**
	 * 取得漫畫名稱
	 * 
	 * @return
	 */
	public String getName() {
		return mName;
	}

	/**
	 * 設定漫畫名稱
	 * 
	 * @param name
	 */
	public void setName(String name) {
		mName = name;
	}

	/**
	 * 取得漫畫作者
	 * 
	 * @return
	 */
	public String getAuthor() {
		return mAuthor;
	}

	/**
	 * 設定漫畫作者
	 * 
	 * @param author
	 */
	public void setAuthor(String author) {
		mAuthor = author;
	}

	/**
	 * 取得漫新最後更新的時間
	 * 
	 * @return
	 */
	public String getLatestUpdateDateTime() {
		return mLatestUpdateDateTime;
	}

	/**
	 * 設定漫新最後更新的時間
	 * 
	 * @param latestUpdateDateTime
	 */
	public void setLatestUpdateDateTime(String latestUpdateDateTime) {
		mLatestUpdateDateTime = latestUpdateDateTime;
	}

	/**
	 * 取得漫畫簡介
	 * 
	 * @return
	 */
	public String getDescription() {
		return mDescription;
	}

	/**
	 * 設定漫畫簡介
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		mDescription = description;
	}

	/**
	 * 取得漫畫最新集數
	 * 
	 * @return
	 */
	public String getNewestEpisode() {
		return mNewestEpisode;
	}

	/**
	 * 設定漫畫最新集數
	 * 
	 * @return
	 */
	public void setNewestEpisode(String newestEpisode) {
		mNewestEpisode = newestEpisode;
	}

	/**
	 * 取得漫畫集數列表
	 * 
	 * @return
	 */
	public List<Episode> getEpisodes() {
		return mEpisodes;
	}

	/**
	 * 設定漫畫集數列表
	 * 
	 * @param episodes
	 */
	public void setEpisodes(List<Episode> episodes) {
		mEpisodes = episodes;
	}

}
