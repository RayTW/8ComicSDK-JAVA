package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import net.xuite.blog.ray00000test.library.comicsdk.Comic;
import net.xuite.blog.ray00000test.library.comicsdk.R8Comic;

public class R8ComicTest {

	@Test
	public void testGetAll() {
//		fail("Not yet implemented");
		R8Comic.get().getAll(result -> {
			Comic comic = result.get(0);
		});
	}

	@Test
	public void testLoadComicDetail() {
//		fail("Not yet implemented");
		Comic comic = null;
		
		R8Comic.get().loadComicDetail(comic, result -> {
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
