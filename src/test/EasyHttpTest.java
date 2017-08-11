package test;

import java.io.IOException;

import net.xuite.blog.ray00000test.library.net.EasyHttp;
import net.xuite.blog.ray00000test.library.net.EasyHttp.Response;

public class EasyHttpTest {

	private void testConnect() {
		EasyHttp request = new EasyHttp.Builder()
				.setUrl("https://github.com/RayTW/8ComicSDK-JAVA")
				.setMethod("GET")
				.setIsRedirect(true)
				.setCharset("UTF-8")
				.putHeader(
						"Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
				.putHeader("Accept-Encoding", "gzip, deflate, br")
				.setUserAgent(
						"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36")
				.build();

		try {
			Response response = request.connect();
			System.out.println(response.getHeaders());
			System.out.println("code[" + response.getStatusCode() + "],body==>"
					+ response.getBody());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		EasyHttpTest test = new EasyHttpTest();
		test.testConnect();
	}

}
