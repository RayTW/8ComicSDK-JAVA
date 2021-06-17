# 8ComicSDK-JAVA
可用於android、window、mac、linux上有JAVA環境就可實作下載8comic的漫畫SDK

查詢API文件說明:
[8ComicSDK-v1.3.0](https://raytw.github.io/8ComicSDK-JAVA/lib/doc/)

## 範例

### 讀取全部漫畫的列表
```java
R8Comic.get()
        .getAll(
            comics -> {
              comics
                  .stream()
                  .forEach(
                      comic -> {
                        System.out.println(comic.getId() + "," + comic.getName());
                      });
            });
```

### 搜尋漫畫
```java
final String searchKeyword = "火影";//查詢的關鍵字
R8Comic.get().searchComic(searchKeyword, new OnLoadListener<List<Comic>>(){

	@Override
	public void onLoaded(List<Comic> result) {//搜尋結果
		int index = 0;
		System.out.println("搜尋\""+searchKeyword+"\"，漫畫筆數["+result.size()+"]");
		for(Comic comic : result){
			System.out.println("index["+(++index)+"],id["+comic.getId()+"], name["+comic.getName()+"]");
		}
				
	}
			
});
```
