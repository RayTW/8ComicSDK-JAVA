package net.xuite.blog.ray00000test.library.comicsdk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.xuite.blog.ray00000test.library.util.StringUtility;

/**
 * 解析從漫畫網站讀取到的html格式字串內容，將內容轉換成Comic、Episode物件
 * 
 * @author Ray Lee Created on 2017/08/11
 */
public class Parser {
	private static final int NOT_FOUND = -1;
	private static final String UNICODE_9 = String.valueOf('\u0009');

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
		int nameTagLower = NOT_FOUND;
		int findCviewUpper = NOT_FOUND;
		String[] html = htmlString.split(System.lineSeparator());
		String findCview = "cview(";
		String findDeaitlTag = "style=\"line-height:25px\">";
		String authorTag = "作者：</td>";
		String updateTag = "更新：</td>";
		String nameTag = ");return";
		List<Episode> episodes = new ArrayList<Episode>();
		String data = null;
		String[] dataAry = null;
		String txt = null;
		String ch = null;
		String url = null;
		String catid = null;
		String copyright = null;

		for (int i = 0; i < html.length; i++) {
			txt = html[i];
			findCviewUpper = txt.indexOf(findCview);

			if (findCviewUpper != NOT_FOUND) {
				nameTagLower = txt.indexOf(nameTag);

				data = txt.substring(findCviewUpper, nameTagLower);
				data = data.replaceAll("'", "");
				dataAry = data.split("[,]");
				ch = dataAry[0].split("[-]")[1].replaceFirst(".html", "");
				url = dataAry[0].replaceFirst(".html", "").replaceFirst("-",
						".html?ch=");
				catid = dataAry[1];
				copyright = dataAry[2];

				// 解析集數名稱
				if (nameTagLower != NOT_FOUND) {
					String episodeName = html[i + 1];
					episodeName = removeScriptsTag(episodeName);
					episodeName = replaceTag(episodeName);
					episodeName = episodeName.replaceAll(UNICODE_9, "");
					episodeName = episodeName.trim();

					if (!episodeName.isEmpty()) {
						Episode episode = new Episode();
						episode.setName(episodeName);
						episode.setUrl(url);
						episode.setCatid(catid);
						episode.setCopyright(copyright);
						episode.setCh(Integer.parseInt(ch));
						episodes.add(episode);
					}
				}
			} else {
				// 解析漫畫簡介
				if (comic.getDescription() == null) {
					String detail = StringUtility.substring(txt, findDeaitlTag,
							"</td>");

					if (detail != null) {
						comic.setDescription(detail);
					}
				}
				// 解析作者
				if (comic.getAuthor() == null) {
					if (txt.indexOf(authorTag) != NOT_FOUND) {
					}
				} else if (comic.getLatestUpdateDateTime() == null) {
					// 解析最新更新日期
					if (txt.indexOf(updateTag) != NOT_FOUND) {
						comic.setLatestUpdateDateTime(replaceTag(html[i + 1]));
					}
				}
			}
		}
		comic.setEpisodes(episodes);

		return comic;
	}

	public Map<String, String> cviewJS(String htmlString) {
		Map<String, String> cviewMap = new HashMap<String, String>();

		String[] dataAry = htmlString.split(System.lineSeparator());

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
		String[] html = htmlString.split(System.lineSeparator());
		String startTagChs = "var chs=";
		String endTagChs = ";var ti=";
		String startTagTi = "var ti=";
		String endTagTi = ";var cs=";
		String startTagCs = "var cs='";
		String endTagCs = "';eval(unescape('";

		for (String txt : html) {
			String chs = StringUtility.substring(txt, startTagChs, endTagChs);
			String ti = StringUtility.substring(txt, startTagTi, endTagTi);
			String cs = StringUtility.substring(txt, startTagCs, endTagCs);

			if (chs != null) {
				episode.setChs(Integer.parseInt(chs));
			}
			if (ti != null) {
				episode.setTi(Integer.parseInt(ti));
			}
			if (cs != null) {
				episode.setCs(cs);
				break;
			}
		}
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
