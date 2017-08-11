package net.xuite.blog.ray00000test.library.comicsdk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析從漫畫網站讀取到的html格式字串內容，將內容轉換成Comic、Episode物件
 * 
 * @author Ray Lee Created on 2017/08/11
 */
public class Parser {

	public List<Comic> allComics(String htmlString, Config config) {
		List<Comic> comicAry = new ArrayList<>();
		// TODO
		return comicAry;
	}

	public Comic comicDetail(String htmlString, Comic comic) {
		// TODO
		return comic;
	}

	public Map<String, String> cviewJS(String htmlString) {
		Map<String, String> cviewMap = new HashMap<>();
		// TODO
		return cviewMap;
	}

	public Episode episodeDetail(String htmlString, Episode episode) {
		// TODO
		return episode;
	}

	/**
	 * 去掉"<"開頭與">"結尾的字串
	 * 
	 * @param txt
	 * @return
	 */
	public String replaceTag(String txt) {
		StringBuffer data = new StringBuffer();
		char st = '<';
		char ed = '>';
		char[] charAry = txt.toCharArray();
		boolean check = false;

		for (int i = 0; i < charAry.length; i++) {
			char c = charAry[i];
			if (c == st) {
				check = true;
			} else if (c == ed) {
				check = false;
				continue;
			}
			if (check) {
				continue;
			}
			if (c == '\r' || c == '\n') {
				continue;
			}
			data.append(c);
		}
		return data.toString();
	}

	/**
	 * 去除javascripts語法tag(包含tag裡面的程式)
	 * 
	 * @param st
	 * @return
	 */
	public String removeScriptsTag(String st) {
		String ret = st;
		String beginStr = "<script>";
		String endStr = "</script>";
		int bIndex = st.indexOf(beginStr);
		int eIndex = st.indexOf(endStr);

		if (bIndex != -1 && eIndex != -1) {
			ret = st.substring(0, bIndex);
			ret += st.substring(eIndex + endStr.length(), st.length());
		}
		return ret;

	}
}
