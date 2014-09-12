package cn.way.wandroid.utils;

public class PageNavigateManager {
	public static enum PageNavigateTag{
		PageNavigateTagNone,
		PageNavigateTagHome,
	    PageNavigateTagLifeStore,
	    PageNavigateTagFriends,
	    PageNavigateTagScoreStore,
	    PageNavigateTagGoldCoinStore,
	    PageNavigateTagUserCenter,
	    PageNavigateTagPrizes
	}
	public static PageNavigateTag tag = PageNavigateTag.PageNavigateTagNone;
	
	public static PageNavigateTag getTag(){
	    return tag;
	}
	public static void setTag(PageNavigateTag tag){
	    PageNavigateManager.tag = tag;
	}
	public static void clearTag(){
		PageNavigateManager.tag = PageNavigateTag.PageNavigateTagNone;
	}
}
