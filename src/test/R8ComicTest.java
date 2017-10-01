package test;

import static org.junit.Assert.*;

import java.sql.Ref;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import net.xuite.blog.ray00000test.library.comicsdk.Comic;
import net.xuite.blog.ray00000test.library.comicsdk.Episode;
import net.xuite.blog.ray00000test.library.comicsdk.R8Comic;
import net.xuite.blog.ray00000test.library.comicsdk.R8Comic.OnLoadListener;
import net.xuite.blog.ray00000test.library.util.StringUtility;

public class R8ComicTest {

	@Test
	public void testGetAll() {
		final CountDownLatch signal = new CountDownLatch(1);

		R8Comic.get().getAll(new OnLoadListener<List<Comic>>() {

			@Override
			public void onLoaded(List<Comic> result) {

				// for (Comic comic : result) {
				// System.out.println(comic.getId() + "," + comic.getName());
				// }

				assertTrue(result.size() > 0);
				signal.countDown();
			}

		});
		try {
			signal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetNewest() {
		final CountDownLatch signal = new CountDownLatch(1);
		
		R8Comic.get().getNewest(new OnLoadListener<List<Comic>>() {

			@Override
			public void onLoaded(List<Comic> result) {

				 for (Comic comic : result) {
					 System.out.println(comic.getId() + "," + comic.getName() + "," + comic.getNewestEpisode());
				 }

				assertTrue(result.size() > 0);
				signal.countDown();
			}

		});
		try {
			signal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testLoadComicDetail() {
		final CountDownLatch signal = new CountDownLatch(1);
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
				signal.countDown();
			}

		});
		try {
			signal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testLoadEpisodeDetail() {
		final CountDownLatch signal = new CountDownLatch(1);
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
																	signal.countDown();
																}

															});
										}
									}

								});
					}

				});
		try {
			signal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testLoadSiteUrlList() {
		final CountDownLatch signal = new CountDownLatch(1);
		R8Comic.get().loadSiteUrlList(
				new OnLoadListener<Map<String, String>>() {

					@Override
					public void onLoaded(Map<String, String> result) {
						System.out.println(result);
						assertTrue(result.size() > 0);
						signal.countDown();
					}

				});
		try {
			signal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testQuickSearchComic() {
		final CountDownLatch signal = new CountDownLatch(1);
		R8Comic.get().quickSearchComic("中", new OnLoadListener<List<String>>(){

			@Override
			public void onLoaded(List<String> result) {
				System.out.println(result);
				assertTrue(result.size() > 0);
				signal.countDown();
			}
			
		});
		try {
			signal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSearchComicHaveComicUsingBig5() {
		final CountDownLatch signal = new CountDownLatch(1);
		final String searchKeyword = "中";
		
		R8Comic.get().searchComic(searchKeyword, new OnLoadListener<List<Comic>>(){

			@Override
			public void onLoaded(List<Comic> result) {
				int index = 0;
				System.out.println("搜尋\""+searchKeyword+"\"，筆數["+result.size()+"]");
				for(Comic comic : result){
					System.out.println("index["+(++index)+"],id["+comic.getId()+"], name["+comic.getName()+"]");
				}
				assertTrue(result.size() > 0);
				signal.countDown();
			}
			
		});
		try {
			signal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSearchComicEmpty() {
		final CountDownLatch signal = new CountDownLatch(1);
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
				signal.countDown();
			}
			
		});
		try {
			signal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testEpisodesHasRepeat() {
		final CountDownLatch signal = new CountDownLatch(1);
		Comic comic = new Comic();
		comic.setId("10406");
		comic.setName("一拳超人");
		final Ref ref = new Ref(){
			private Object mObj;

			@Override
			public String getBaseTypeName() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object getObject(Map<String, Class<?>> map)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object getObject() throws SQLException {
				// TODO Auto-generated method stub
				return mObj;
			}

			@Override
			public void setObject(Object value) throws SQLException {
				mObj = value;				
			}
			
		};
		try {
			ref.setObject(Boolean.FALSE);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		R8Comic.get().loadComicDetail(comic, new OnLoadListener<Comic>() {

			@Override
			public void onLoaded(Comic result) {
				System.out.println("getEpisodesSize["
						+ result.getEpisodes().size() + "]");
				
				for(Episode obj : result.getEpisodes()){
					System.out.println(obj.getName());
					for(Episode obj2 : result.getEpisodes()){
						if(obj.hashCode() != obj2.hashCode() && obj.getName().equals(obj2.getName())){
							try {
								ref.setObject(Boolean.TRUE);
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
				}
				
				signal.countDown();
			}

		});
		try {
			signal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			assertFalse((Boolean)ref.getObject());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
