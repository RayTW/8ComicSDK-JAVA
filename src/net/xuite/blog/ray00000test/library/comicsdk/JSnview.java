package net.xuite.blog.ray00000test.library.comicsdk;

import java.util.ArrayList;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import net.xuite.blog.ray00000test.library.util.StringUtility;

/**
 * nview.js
 * 
 * 解析每集(話)漫畫圖片下載網址
 * 
 * @author Ray Lee Created on 2017/08/17
 */
public class JSnview {
	private String mSource;
	private int mCh;
	private int mChs = 0;
	private int mTi = 0;
	private int mPs = 0; // 漫畫總頁數
	private String mCs = "";
	private String mC = "";
	private static final int Y = 46;

	public void setSource(String source) {
		mSource = source;
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

	/**
	 * 解析漫畫圖片下載網址
	 * 
	 * @return
	 */
	public List<String> setupPagesDownloadUrl() {
		return invokeJS(mSource, Y, mCh);
	}

	private List<String> invokeJS(String js, int y, int ch) {
		ArrayList<String> pagsList = new ArrayList<String>();
		String str = js.substring(0, js.indexOf("var pt="));
		str = str.replace("ge('TheImg').src", "var src");
		String unuseScript = StringUtility.substring(str, "\'.jpg\';", "break;");
		str = str.replace(unuseScript, "");
		String varSrc = StringUtility.substring(str, "ci = i; ", "break;");
		String getPageJS = String.format(buildGetPagesJS(), varSrc);
		str = str.replace(varSrc, "");
		str = str.replace("break;", getPageJS);
		String script = "function sp2(ch, y){" + str + "} " + buildNviewJS();
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");

		try {
			engine.eval(script);
		} catch (ScriptException e1) {
			e1.printStackTrace();
		}
		Invocable inv = (Invocable) engine;
		try {
			ScriptObjectMirror ret = (ScriptObjectMirror) inv.invokeFunction("sp2", ch, y);
			String url = null;

			ret.callMember("reverse");

			while ((Integer) ret.get("length") > 0) {
				url = (String) ret.callMember("pop");
				pagsList.add(url);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagsList;
	}

	private String buildNviewJS() {
		StringBuilder buf = new StringBuilder();
		buf.append("function lc(l) {");
		buf.append("if (l.length != 2) return l;");
		buf.append("var az = \"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ\";");
		buf.append("var a = l.substring(0, 1);");
		buf.append("var b = l.substring(1, 2);");
		buf.append("if (a == \"Z\") return 8000 + az.indexOf(b);");
		buf.append("else return az.indexOf(a) * 52 + az.indexOf(b);}");

		buf.append("function su(a, b, c) {").append("var e = (a + '').substring(b, b + c);").append("return (e);")
				.append("}");

		buf.append("function nn(n) {").append("return n < 10 ? '00' + n : n < 100 ? '0' + n : n;").append("}");

		buf.append("function mm(p) {").append("return (parseInt((p - 1) / 10) % 10) + (((p - 1) % 10) * 3)")
				.append("}");

		return buf.toString();
	}

	private String buildGetPagesJS() {
		StringBuilder buf = new StringBuilder();
		buf.append("var result = [];");
		buf.append("for(var p = 1; p < ps; p++){");
		buf.append("%s");
		buf.append("result.push(src);");
		buf.append("}");
		buf.append("return result;");
		return buf.toString();
	}
}
