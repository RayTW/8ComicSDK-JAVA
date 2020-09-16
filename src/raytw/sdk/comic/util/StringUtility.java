package raytw.sdk.comic.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 
 * @author Ray Lee Created on 2017/08/12
 * 
 */
public class StringUtility {
	/**
	 * 將會用upperStringFoot字串與lowerStringHead字串，尋找在source裡是否有相符的字串，若都有找到index後，
	 * 會將source裡(upperIndex + upperStringFoot.length())至lowerStringHead的index符進行截字串
	 * 
	 * {@code  
	 * String result = StringUtility.substring("aabbccdd", "aa", "d");
	 * result將會是"bbcc"
	 * }
	 * 
	 * @param source
	 * @param upperStringFoot
	 * @param lowerStringHead
	 * @return
	 */
	public static String substring(String source, String upperStringFoot, String lowerStringHead) {

		int upperIndex = source.indexOf(upperStringFoot);
		int lowerHeadIndex = source.indexOf(lowerStringHead);

		if (upperIndex != -1 && lowerHeadIndex != -1) {
			return source.substring(upperIndex + upperStringFoot.length(), lowerHeadIndex);
		}
		return null;
	}

	public static String lastSubstring(String source, String upperStringFoot, String lowerStringHead) {

		int upperIndex = source.lastIndexOf(upperStringFoot);
		int lowerHeadIndex = source.lastIndexOf(lowerStringHead);

		if (upperIndex != -1 && lowerHeadIndex != -1) {
			return source.substring(upperIndex + upperStringFoot.length(), lowerHeadIndex);
		}
		return null;
	}

	public static String urlencodeUsingBIG5(String source) {
		return urlencodeUsing(source, "BIG5");
	}

	public static String urlencodeUsingGB2312(String source) {
		return urlencodeUsing(source, "GB2312");
	}

	public static String urlencodeUsing(String source, String encode) {
		try {
			return URLEncoder.encode(source, encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
}