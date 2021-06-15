package raytw.sdk.comic.comicsdk;

import java.util.List;

/**
 * 每集(話、卷)漫畫.
 *
 * @author Ray Lee Created on 2017/08/11
 */
public class Episode {
  private String name; // 漫畫每集(話)(卷)名稱
  private String url;
  private String catid;
  private String copyright;
  private List<String> imageUrl;
  private Jsnview jsnview = new Jsnview();

  public void setSourceHtml(String html) {
    jsnview.setSource(html);
  }

  /** 讀取1話(集、卷)全部漫畫圖片網址. */
  public void setUpPages() {
    imageUrl = jsnview.setupPagesDownloadUrl();
  }

  /*
   * 取得單集漫畫名稱
   */
  public String getName() {
    return name;
  }

  /*
   * 設定單集漫畫名稱
   */
  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * 對應漫畫圖片存放的伺服器host網址.
   *
   * @return host
   */
  public String getCatid() {
    return catid;
  }

  public void setCatid(String catid) {
    this.catid = catid;
  }

  /**
   * 版權.
   *
   * @return 版權聲名
   */
  public String getCopyright() {
    return copyright;
  }

  public void setCopyright(String copyright) {
    this.copyright = copyright;
  }

  public void setCh(int ch) {
    jsnview.setCh(ch);
  }

  public void setChs(int chs) {
    jsnview.setChs(chs);
  }

  public void setTi(int ti) {
    jsnview.setTi(ti);
  }

  public void setCs(String cs) {
    jsnview.setCs(cs);
  }

  /**
   * 取得全部漫畫圖片.
   *
   * @return 圖片列表
   */
  public List<String> getImageUrlList() {
    return imageUrl;
  }

  /**
   * 漫畫總頁數.
   *
   * @return 頁數
   */
  public int getPages() {
    if (imageUrl == null) {
      return 0;
    }
    return imageUrl.size();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();

    buf.append("mName=[" + name + "]")
        .append("mUrl=[" + url + "]")
        .append("mCatid=[" + catid + "]")
        .append("mCopyright=[" + copyright + "]")
        .append("mCh=[" + jsnview.getCh() + "]")
        .append("mChs=[" + jsnview.getChs() + "]")
        .append("mTi=[" + jsnview.getTi() + "]")
        .append("mPs=[" + getPages() + "]")
        .append("mCs=[" + jsnview.getCs() + "]")
        .append("mC=[" + jsnview.getC() + "]");

    return buf.toString();
  }
}
