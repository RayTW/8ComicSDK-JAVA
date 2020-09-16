package raytw.sdk.comic.comicsdk;

import java.util.List;

/**
 * 
 * 每集(話、卷)漫畫
 * 
 * @author Ray Lee Created on 2017/08/11
 */
public class Episode {
	private String mName;// 漫畫每集(話)(卷)名稱
	private String mUrl;
	private String mCatid;
	private String mCopyright;
	private List<String> mImageUrl;
	private JSnview mJSnview = new JSnview();

	public void setSourceHtml(String html) {
		mJSnview.setSource(html);
	}

	/**
	 * 讀取1話(集、卷)全部漫畫圖片網址
	 */
	public void setUpPages() {
		mImageUrl = mJSnview.setupPagesDownloadUrl();
	}

	/*
	 * 取得單集漫畫名稱
	 */
	public String getName() {
		return mName;
	}

	/*
	 * 設定單集漫畫名稱
	 */
	public void setName(String name) {
		mName = name;
	}

	public String getUrl() {
		return mUrl;
	}

	public void setUrl(String url) {
		mUrl = url;
	}

	/**
	 * 對應漫畫圖片存放的伺服器host網址
	 * 
	 * @return
	 */
	public String getCatid() {
		return mCatid;
	}

	public void setCatid(String catid) {
		mCatid = catid;
	}

	public String getCopyright() {
		return mCopyright;
	}

	public void setCopyright(String copyright) {
		mCopyright = copyright;
	}

	public void setCh(int ch) {
		mJSnview.setCh(ch);
	}

	public void setChs(int chs) {
		mJSnview.setChs(chs);
	}

	public void setTi(int ti) {
		mJSnview.setTi(ti);
	}

	public void setCs(String cs) {
		mJSnview.setCs(cs);
	}

	/**
	 * 取得全部漫畫圖片
	 * 
	 * @return
	 */
	public List<String> getImageUrlList() {
		return mImageUrl;
	}

	/**
	 * 漫畫總頁數
	 * 
	 * @return
	 */
	public int getPages() {
		if (mImageUrl == null) {
			return 0;
		}
		return mImageUrl.size();
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();

		buf.append("mName=[" + mName + "]").append("mUrl=[" + mUrl + "]").append("mCatid=[" + mCatid + "]")
				.append("mCopyright=[" + mCopyright + "]").append("mCh=[" + mJSnview.getCh() + "]")
				.append("mChs=[" + mJSnview.getChs() + "]").append("mTi=[" + mJSnview.getTi() + "]")
				.append("mPs=[" + getPages() + "]").append("mCs=[" + mJSnview.getCs() + "]")
				.append("mC=[" + mJSnview.getC() + "]");

		return buf.toString();
	}
}
