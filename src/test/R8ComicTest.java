package test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import net.xuite.blog.ray00000test.library.comicsdk.Comic;
import net.xuite.blog.ray00000test.library.comicsdk.R8Comic;
import net.xuite.blog.ray00000test.library.comicsdk.R8Comic.OnLoadListener;

public class R8ComicTest {

	@Test
	public void testGetAll() {
		R8Comic.get().getAll(new OnLoadListener<List<Comic>>() {

			@Override
			public void onLoaded(List<Comic> result) {

				//				for (Comic comic : result) {
				//					System.out.println(comic.getId() + "," + comic.getName());
				//				}

				assertTrue(result.size() > 0);
			}

		});
	}

	@Test
	public void testLoadComicDetail() {
		// fail("Not yet implemented");
		Comic comic = new Comic();
		comic.setId("103");
		comic.setName("海賊王");

		R8Comic.get().loadComicDetail(comic, new OnLoadListener<Comic>() {

			@Override
			public void onLoaded(Comic result) {
				// TODO Auto-generated method stub
				System.out.println("id["+result.getId()+"]");
				System.out.println("getDescription["+result.getDescription()+"]");
				System.out.println("getAuthor["+result.getAuthor()+"]");
				System.out.println("getLatestUpdateDateTime["+result.getLatestUpdateDateTime()+"]");
				System.out.println("getEpisodesSize["+result.getEpisodes().size()+"]");
				assertTrue(result.getEpisodes().size() > 0);
			}

		});
	}

	@Test
	public void testLoadEpisodeDetail() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoadSiteUrlList() {
		R8Comic.get().loadSiteUrlList(new OnLoadListener<Map<String, String>>() {

			@Override
			public void onLoaded(Map<String, String> result) {
				System.out.println(result);
				assertTrue(result.size() > 0);
			}

		});
	}

}
