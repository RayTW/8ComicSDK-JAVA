package net.xuite.blog.ray00000test.library.comicsdk;

import java.util.List;
import java.util.Map;

public class R8Comic {
	private static R8Comic sInstance = new R8Comic();
	private Config mConfig = new Config();
	private Parser mParser = new Parser();
	
	public static R8Comic get(){
		return sInstance;
	}
	
	/**
     * 讀取全部漫畫編號、名稱
     **/
	public void getAll(OnLoadListener<List<Comic>> listener){
		//TODO 用Parser解析漫畫名稱後回傳結果
	}
	
	/**
     * 讀取漫畫簡介、作者、最後更新日期、集數列表
     **/
	public void loadComicDetail(Comic comic, OnLoadListener<Comic> listener){
		//TODO 用Parser解析漫畫資訊後回傳結果
	}
	
	/**
     * 讀取漫畫簡介、作者、最後更新日期、集數列表
     **/
	public void loadEpisodeDetail(Episode episode, OnLoadListener<Episode> listener){
		//TODO 用Parser解析漫畫資訊後回傳結果
	}
	
	/**
     * 讀取漫畫圖片實際存放的Server site網址列表
     **/
	public void loadSiteUrlList(OnLoadListener<Map<String, String>> listener){
		
	}
	
	public Parser getParser(){
		return mParser;
	}
	
	public Parser getmConfig(){
		return mConfig;
	}
	
	public static interface OnLoadListener<Result>{
		public abstract void onLoad(Result result);
	}
}
