package test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.xuite.blog.ray00000test.library.util.StringUtility;

public class JSTest {

	public JSTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		String js = "var chs=367;var ti=105;var cs='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';for(var i=0;i<139;i++){var bibwg= lc(su(cs,i*y+0,2));var tldpu= lc(su(cs,i*y+2,40));var fafci=lc(su(cs,i*y+42,2));var ttwfa= lc(su(cs,i*y+44,2));ps=bibwg;if (fafci == ch) { ci = i; ge('TheImg').src = 'http://img' + su(ttwfa, 0, 1) + '.8comic.com/' + su(ttwfa, 1, 1) + '/' + ti + '/' + fafci + '/' + nn(p) + '_' + su(tldpu, mm(p), 3) + '.jpg'; pi=ci>0?lc(su(cs,ci*y-y+42,2)):ch;ni=ci<chs-1?lc(su(cs,ci*y+y+42,2)):ch;break; }}var pt='[ '+pi+' ]';var nt='[ '+ni+' ]';spp();</script>";
		int p = 1;
		int ch = 1;
		int y = 46;
		
		invokeJS(js, y, ch, p);
	}

	public static void invokeJS(String js, int y, int ch, int p) {
		System.out.println(StringUtility.substring(js, ";for(var i=0;i<", ";i++"));

		String str = js.substring(0, js.indexOf("var pt="));
		str = str.replace("ge('TheImg').src", "return src");

		System.out.println(str);

		String script = "function sp2(ch, y, p){" + str + "} " + buildNviewJS();

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");

		try {
			engine.eval(script);
		} catch (ScriptException e1) {
			e1.printStackTrace();
		}
		Invocable inv = (Invocable) engine;
		try {
			Object ret = inv.invokeFunction("sp2", ch, y, p);

			System.out.println(ret);
		} catch (NoSuchMethodException | ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String buildNviewJS() {
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
}
