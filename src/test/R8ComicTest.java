package test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import net.xuite.blog.ray00000test.library.comicsdk.Comic;
import net.xuite.blog.ray00000test.library.comicsdk.Episode;
import net.xuite.blog.ray00000test.library.comicsdk.R8Comic;
import net.xuite.blog.ray00000test.library.comicsdk.R8Comic.OnLoadListener;

public class R8ComicTest {

	@Test
	public void testGetAll() {
		R8Comic.get().getAll(new OnLoadListener<List<Comic>>() {

			@Override
			public void onLoaded(List<Comic> result) {

				// for (Comic comic : result) {
				// System.out.println(comic.getId() + "," + comic.getName());
				// }

				assertTrue(result.size() > 0);
			}

		});
	}
	
	@Test
	public void testGetNewest() {
		R8Comic.get().getNewest(new OnLoadListener<List<Comic>>() {

			@Override
			public void onLoaded(List<Comic> result) {

				 for (Comic comic : result) {
					 System.out.println(comic.getId() + "," + comic.getName() + "," + comic.getNewestEpisode());
				 }

				assertTrue(result.size() > 0);
			}

		});
	}

	@Test
	public void testLoadComicDetail() {
		Comic comic = new Comic();
		comic.setId("10406");
		comic.setName("一拳超人");

		R8Comic.get().loadComicDetail(comic, new OnLoadListener<Comic>() {

			@Override
			public void onLoaded(Comic result) {
				// TODO Auto-generated method stub
				System.out.println("id[" + result.getId() + "]");
				System.out.println("getDescription[" + result.getDescription()
						+ "]");
				System.out.println("getAuthor[" + result.getAuthor() + "]");
				System.out.println("getLatestUpdateDateTime["
						+ result.getLatestUpdateDateTime() + "]");
				System.out.println("getEpisodesSize["
						+ result.getEpisodes().size() + "]");
				assertTrue(result.getEpisodes().size() > 0);
			}

		});
	}

	@Test
	public void testLoadEpisodeDetail() {
		R8Comic.get().loadSiteUrlList(
				new OnLoadListener<Map<String, String>>() {

					@Override
					public void onLoaded(final Map<String, String> hostList) {
						Comic comic = new Comic();
						comic.setId("103");
						comic.setName("海賊王");

						R8Comic.get().loadComicDetail(comic,
								new OnLoadListener<Comic>() {

									@Override
									public void onLoaded(Comic result) {
										// TODO Auto-generated method stub
										System.out.println("id["
												+ result.getId() + "]");
										System.out.println("getDescription["
												+ result.getDescription() + "]");
										System.out.println("getAuthor["
												+ result.getAuthor() + "]");
										System.out.println("getLatestUpdateDateTime["
												+ result.getLatestUpdateDateTime()
												+ "]");
										System.out.println("getEpisodesSize["
												+ result.getEpisodes().size()
												+ "]");

										if (result.getEpisodes().size() > 0) {
											Episode episode = result
													.getEpisodes().get(0);
											System.out
													.println("episode=="
															+ episode);
											String downloadHost = hostList
													.get(episode.getCatid());
											System.out.println("downloadHost["
													+ downloadHost + "]");

											episode.setUrl(downloadHost
													+ episode.getUrl());

											R8Comic.get()
													.loadEpisodeDetail(
															episode,
															new OnLoadListener<Episode>() {

																@Override
																public void onLoaded(
																		Episode result) {
																	result.setUpPages();
																	System.out
																	.println(result);
																	System.out
																			.println(result
																					.getImageUrlList());

																	assertTrue(result.getPages() > 0);
																}

															});
										}
									}

								});
					}

				});
	}

	@Test
	public void testLoadSiteUrlList() {
		R8Comic.get().loadSiteUrlList(
				new OnLoadListener<Map<String, String>>() {

					@Override
					public void onLoaded(Map<String, String> result) {
						System.out.println(result);
						assertTrue(result.size() > 0);
					}

				});
	}

	@Test
	public void testQuickSearchComic() {
		R8Comic.get().quickSearchComic("1", new OnLoadListener<List<String>>(){

			@Override
			public void onLoaded(List<String> result) {
				System.out.println(result);
				assertTrue(result.size() > 0);
			}
			
		});
	}
	
	@Test
	public void testSearchComicHaveComic() {
		final String searchKeyword = "1";
		
		R8Comic.get().searchComic(searchKeyword, new OnLoadListener<List<Comic>>(){

			@Override
			public void onLoaded(List<Comic> result) {
				int index = 0;
				System.out.println("搜尋\""+searchKeyword+"\"，筆數["+result.size()+"]");
				for(Comic comic : result){
					System.out.println("index["+(++index)+"],id["+comic.getId()+"], name["+comic.getName()+"]");
				}
				assertTrue(result.size() > 0);
			}
			
		});
	}
	
	@Test
	public void testSearchComicEmpty() {
		final String searchKeyword = "ddddddd";
		
		R8Comic.get().searchComic(searchKeyword, new OnLoadListener<List<Comic>>(){

			@Override
			public void onLoaded(List<Comic> result) {
				int index = 0;
				System.out.println("搜尋\""+searchKeyword+"\"，筆數["+result.size()+"]");
				for(Comic comic : result){
					System.out.println("index["+(++index)+"],id["+comic.getId()+"], name["+comic.getName()+"]");
				}
				assertTrue(result.size() == 0);
			}
			
		});
	}
}
