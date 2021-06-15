package raytw.sdk.comic.comicsdk;

import java.util.List;

/**
 * 以每個漫畫id(編號)分類對應的漫畫物件.
 *
 * @author Ray Lee Created on 2017/08/11
 */
public class Comic {
  private String id; // 漫畫編號
  private String iconUrl; // 漫畫封面大圖網址
  private String smallIconUrl; // 漫畫封面小圖網址
  private String name; // 漫畫名稱
  private String author; // 漫畫作者
  private String latestUpdateDateTime; // 最後更新的時間
  private String description; // 漫畫描述
  private String newestEpisode; // 最新集數
  private List<Episode> episodes; // 漫畫集數列表

  /**
   * 取得漫畫編號.
   *
   * @return 漫畫編號
   */
  public String getId() {
    return id;
  }

  /**
   * 設定漫畫編號.
   *
   * @param id 編號
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * 取得漫畫封面大圖網址.
   *
   * @return 封面網址
   */
  public String getIconUrl() {
    return iconUrl;
  }

  /**
   * 設定漫畫封面大圖網址.
   *
   * @param iconUrl 大圖網址
   */
  public void setIconUrl(String iconUrl) {
    this.iconUrl = iconUrl;
  }

  /**
   * 取得漫畫封面小圖網址.
   *
   * @return 縮圖網址
   */
  public String getSmallIconUrl() {
    return smallIconUrl;
  }

  /**
   * 取得漫畫封面小圖網址.
   *
   * @param smallIconUrl 縮圖網址
   */
  public void setSmallIconUrl(String smallIconUrl) {
    this.smallIconUrl = smallIconUrl;
  }

  /**
   * 取得漫畫名稱.
   *
   * @return 漫畫名稱
   */
  public String getName() {
    return name;
  }

  /**
   * 設定漫畫名稱.
   *
   * @param name 名稱
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * 取得漫畫作者.
   *
   * @return 作者名稱
   */
  public String getAuthor() {
    return author;
  }

  /**
   * 設定漫畫作者.
   *
   * @param author 作者
   */
  public void setAuthor(String author) {
    this.author = author;
  }

  /**
   * 取得漫新最後更新的時間.
   *
   * @return 更新時間
   */
  public String getLatestUpdateDateTime() {
    return latestUpdateDateTime;
  }

  /**
   * 設定漫新最後更新的時間.
   *
   * @param latestUpdateDateTime 最後更新時間
   */
  public void setLatestUpdateDateTime(String latestUpdateDateTime) {
    this.latestUpdateDateTime = latestUpdateDateTime;
  }

  /**
   * 取得漫畫簡介.
   *
   * @return 簡介
   */
  public String getDescription() {
    return description;
  }

  /**
   * 設定漫畫簡介.
   *
   * @param description 描述
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * 取得漫畫最新集數.
   *
   * @return 集數
   */
  public String getNewestEpisode() {
    return newestEpisode;
  }

  /**
   * 設定漫畫最新集數.
   *
   * @param newestEpisode 最新集數
   */
  public void setNewestEpisode(String newestEpisode) {
    this.newestEpisode = newestEpisode;
  }

  /**
   * 取得漫畫集數列表.
   *
   * @return 集數列表
   */
  public List<Episode> getEpisodes() {
    return episodes;
  }

  /**
   * 設定漫畫集數列表.
   *
   * @param episodes 集數列表
   */
  public void setEpisodes(List<Episode> episodes) {
    this.episodes = episodes;
  }
}
