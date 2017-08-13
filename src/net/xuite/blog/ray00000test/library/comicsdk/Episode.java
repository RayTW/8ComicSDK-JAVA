package net.xuite.blog.ray00000test.library.comicsdk;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
	private int mCh;
	private int mChs = 0;
	private int mTi = 0;
	private int mPs = 0; // 漫畫總頁數
	private String mCs = "";
	private String mC = "";
	private List<String> mImageUrl = new CopyOnWriteArrayList<String>();
	private static final int F = 50;

	/**
	 * 讀取1話(集、卷)全部漫畫圖片網址
	 */
	public void setUpPages() {
		sp();
		int totalPage = mPs;

		mImageUrl.clear();
		
		for (int i = 0; i < totalPage; i++) {
			mImageUrl.add(si(i + 1));
		}
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

	/*
	 * 取得集數編號，例如1、2、3…
	 */
	public int getCh() {
		return mCh;
	}

	public void setCh(int ch) {
		mCh = ch;
	}

	/*
	 * 取得最新集數，例如最新第68號，此回傳值則為68
	 */
	public int getChs() {
		return mChs;
	}

	public void setChs(int chs) {
		mChs = chs;
	}

	public int getTi() {
		return mTi;
	}

	public void setTi(int ti) {
		mTi = ti;
	}

	public int getPages() {
		return mPs;
	}

	/*
	 * 取得單集漫畫混淆過的編碼
	 */
	public String getCs() {
		return mCs;
	}

	public void setCs(String cs) {
		mCs = cs;
	}

	public List<String> getImageUrlList() {
		return mImageUrl;
	}

	private void sp() {
		int cc = mCs.length();
		for (int i = 0; i < cc / F; i++) {
			if (ss(mCs, i * F, 4).equals(String.valueOf(mCh))) {
				mC = ss(mCs, i * F, F, F);
				break;
			}
		}
		if (mC.isEmpty()) {
			mC = ss(mCs, cc - F, F);
			mCh = mChs;
		}
		String ps = ss(mC, 7, 3);// 總頁數

		if (!ps.isEmpty()) {
			mPs = Integer.parseInt(ps);
		}
	}

	/**
	 * 取得卷數或集數
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @return
	 */
	private String ss(String a, int b, int c) {
		return ss(a, b, c, null);
	}

	/**
	 * 取得卷數或集數
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @return
	 */
	private String ss(String a, int b, int c, Integer d) {
		String e = a.substring(b, b + c);
		return d == null ? e.replaceAll("[a-z]", "") : e;
	}

	private String si(int p) {
		return "http://img" + ss(mC, 4, 2) + ".8comic.com/" + ss(mC, 6, 1)
				+ "/" + mTi + "/" + ss(mC, 0, 4) + "/" + nn(p) + "_"
				+ ss(mC, mm(p) + 10, 3, F) + ".jpg";
	}

	private String nn(int n) {
		return String.valueOf(n < 10 ? "00" + n : n < 100 ? "0" + n : n);
	}

	private int mm(int p) {
		return (((p - 1) / 10) % 10) + (((p - 1) % 10) * 3);
	};
}
