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
	private static final int NOT_FOUND = -1;

	public List<Comic> allComics(String htmlString, Config config) {
		String html = htmlString;
		String commicIdBegin = "showthumb(";
		String commicIdEnd = ",this);";
		int comicIdBeginIndex = NOT_FOUND;
		int comicIdEndIndex = NOT_FOUND;
		String comicId = "";
		String commicNameBegin = "hidethumb();'>";
		String commicNameEnd = "</a></td>";
		String comicName = "";
		int comicNameBeginIndex = NOT_FOUND;
		int comicNameEndIndex = NOT_FOUND;
		List<Comic> comicAry = new ArrayList<Comic>();

		while (true) {
			comicIdBeginIndex = html.indexOf(commicIdBegin);
			comicIdEndIndex = html.indexOf(commicIdEnd);

			if (comicIdBeginIndex == NOT_FOUND || comicIdEndIndex == NOT_FOUND) {
				break;
			}
			comicId = html
					.substring(comicIdBeginIndex + commicIdBegin.length(),
							comicIdEndIndex);
			html = html.substring(comicIdEndIndex + commicIdEnd.length());
			comicNameBeginIndex = html.indexOf(commicNameBegin);
			comicNameEndIndex = html.indexOf(commicNameEnd);

			if (comicNameBeginIndex == NOT_FOUND
					|| comicNameEndIndex == NOT_FOUND) {
				break;
			}
			comicName = html.substring(
					comicNameBeginIndex + commicNameBegin.length(),
					comicNameEndIndex);
			Comic comic = new Comic();
			comic.setId(comicId);
			comic.setName(comicName);
			comic.setIconUrl(comicId);
			comic.setSmallIconUrl(comicId);
			comicAry.add(comic);
		}

		return comicAry;
	}

	public Comic comicDetail(String htmlString, Comic comic) {
		// TODO
		return comic;
	}

	public Map<String, String> cviewJS(String htmlString) {
		Map<String, String> cviewMap = new HashMap<String, String>();
		
		String [] dataAry = htmlString.split(System.lineSeparator());

		String startTag = "if(catid";
		String endTab = "baseurl=\"";
		String urlStratTag = endTab;
		String urlEndTag = "\";";

		for (int i = 0; i < dataAry.length; i++) {
			String txt = dataAry[i];
			
			if (txt.length() > 0) {

				if (txt.indexOf(startTag) != -1) {
					txt = txt.replaceAll("[)]", "");
					String[] numCodeAry = txt.substring(startTag.length(),
							txt.indexOf(endTab)).split("[|][|]");
					String cviewUrl = txt.substring(
							txt.indexOf(urlStratTag) + urlStratTag.length(),
							txt.indexOf(urlEndTag)).trim();
					for (int j = 0; j < numCodeAry.length; j++) {
						String numCode = (numCodeAry[j].trim()).split("[=][=]")[1]
								.trim();
						cviewMap.put(numCode, cviewUrl);
					}
				}
			}
		}
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
