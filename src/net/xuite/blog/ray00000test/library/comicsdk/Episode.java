package net.xuite.blog.ray00000test.library.comicsdk;

import java.util.List;

/**
 * 
 * 每集(話、卷)漫畫
 * 
 * @author Ray Lee 
 * Created on 2017/08/11
 */
public class Episode {
	private String mName;// 漫畫每集(話)(卷)名稱
	private String mUrl ;
	private String mCatid;
	private String mCopyright;
	private int mCh;
	private int mChs = 0;
	private int mTi = 0;
	private int mPs = 0; //漫畫總頁數
	private String mCs = "";
	private String mC = "";
	private List<String> mImageUrl ;
	private static final int F = 50;

	/**
	 * 讀取1話(集、卷)全部漫畫圖片網址
	 */
	public void setUpPages(){
		//TODO
	}
	
	/*
	 * 取得單集漫畫名稱
	 */
	public String getName(){
		return mName;
	}

	/*
	 * 設定單集漫畫名稱
	 */
	public void setName(String name){
		mName = name;
	}

	public String getUrl(){
		return mUrl;
	}

	public void setUrl(String url){
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

	public String getC() {
		return mC;
	}

	public void setC(String c) {
		mC = c;
	}

	public List<String> getImageUrlList() {
		return mImageUrl;
	}
	
	private void sp(){
		//TODO
	}
	
	private void ss(String a, int b, int c){
		//TODO
	}
	
	private void ss(String a, int b, int c, Integer d){
		//TODO
	}
	
	private String si(int p){
		//TODO
		
		return null;
	}
	
	private int mm(int p){
		//TODO
		
		return 0;
	}
	
	private String nn(int n){
		//TODO
		
		return null;
	}
}
