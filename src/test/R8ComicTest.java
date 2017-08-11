package test;

import static org.junit.Assert.*;

import java.util.List;

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

				for (Comic comic : result) {
					System.out.println(comic.getId() + "," + comic.getName());
				}

				assertTrue(result.size() > 0);
			}

		});
	}

	@Test
	public void testLoadComicDetail() {
		// fail("Not yet implemented");
		Comic comic = null;

		R8Comic.get().loadComicDetail(comic, new OnLoadListener<Comic>() {

			@Override
			public void onLoaded(Comic result) {
				// TODO Auto-generated method stub

			}

		});
	}

	@Test
	public void testLoadEpisodeDetail() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoadSiteUrlList() {
		fail("Not yet implemented");
	}

}
