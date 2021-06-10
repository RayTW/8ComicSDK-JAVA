package raytw.sdk.comic.comicsdk;

import java.util.ArrayList;
import java.util.List;
import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import raytw.sdk.comic.util.StringUtility;

/**
 * nview.js
 *
 * <p>解析每集(話)漫畫圖片下載網址
 *
 * @author Ray Lee Created on 2017/08/17
 */
public class Jsnview {
  private String source;
  private int ch;
  private int chs = 0;
  private int ti = 0;
  private String cs = "";
  private String cc = "";
  private static final int Y = 46;

  public void setSource(String source) {
    this.source = source;
  }

  /*
   * 取得集數編號，例如1、2、3…
   */
  public int getCh() {
    return ch;
  }

  public void setCh(int ch) {
    this.ch = ch;
  }

  /*
   * 取得最新集數，例如最新第68號，此回傳值則為68
   */
  public int getChs() {
    return chs;
  }

  public void setChs(int chs) {
    this.chs = chs;
  }

  public int getTi() {
    return ti;
  }

  public void setTi(int ti) {
    this.ti = ti;
  }

  /*
   * 取得單集漫畫混淆過的編碼
   */
  public String getCs() {
    return cs;
  }

  public void setCs(String cs) {
    this.cs = cs;
  }

  public String getC() {
    return cc;
  }

  /** 解析漫畫圖片下載網址. */
  public List<String> setupPagesDownloadUrl() {
    return invokeJs(source, Y, ch);
  }

  /**
   * 執行javascript.
   *
   * @param js javascript
   * @param y flag
   * @param ch ch
   */
  private List<String> invokeJs(String js, int y, int ch) {
    ArrayList<String> pagsList = new ArrayList<String>();
    String str = js.substring(0, js.indexOf("var pt="));
    str = str.replace("ge('TheImg').src", "var src");
    String unuseScript = StringUtility.substring(str, "\'.jpg\';", "break;");
    str = str.replace(unuseScript, "");
    String varSrc = null;

    if (str.indexOf("ci = i;") != -1) {
      varSrc = StringUtility.substring(str, "ci = i;", "break;");
    } else if (str.indexOf("ci=i;") != -1) {
      varSrc = StringUtility.substring(str, "ci=i;", "break;");
    }

    String getPageJs = String.format(buildGetPagesJs(), varSrc);
    str = str.replace(varSrc, "");
    str = str.replace("break;", getPageJs);
    String script = "function sp2(ch, y){" + str + "} " + buildNviewJs();
    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("JavaScript");

    try {
      Bindings bind = engine.createBindings();
      bind.put("pagsList", pagsList);
      engine.setBindings(bind, ScriptContext.ENGINE_SCOPE);

      engine.eval(script);
      Invocable inv = (Invocable) engine;
      inv.invokeFunction("sp2", ch, y);

    } catch (Exception e) {
      e.printStackTrace();
    }

    return pagsList;
  }

  private String buildNviewJs() {
    StringBuilder buf = new StringBuilder();
    buf.append("function lc(l) {");
    buf.append("if (l.length != 2) return l;");
    buf.append("var az = \"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ\";");
    buf.append("var a = l.substring(0, 1);");
    buf.append("var b = l.substring(1, 2);");
    buf.append("if (a == \"Z\") return 8000 + az.indexOf(b);");
    buf.append("else return az.indexOf(a) * 52 + az.indexOf(b);}");

    buf.append("function su(a, b, c) {")
        .append("var e = (a + '').substring(b, b + c);")
        .append("return (e);")
        .append("}");

    buf.append("function nn(n) {")
        .append("return n < 10 ? '00' + n : n < 100 ? '0' + n : n;")
        .append("}");

    buf.append("function mm(p) {")
        .append("return (parseInt((p - 1) / 10) % 10) + (((p - 1) % 10) * 3)")
        .append("}");

    return buf.toString();
  }

  private String buildGetPagesJs() {
    StringBuilder buf = new StringBuilder();
    buf.append("for(var p = 1; p <= ps; p++){");
    buf.append("%s");
    buf.append("pagsList.add(src)"); // 將漫畫下載網址放到java的ArrayList
    buf.append("}");
    buf.append("return;");
    return buf.toString();
  }
}
