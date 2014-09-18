package cn.way.wandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppGuider {
	private static String NAME = "AppGuider";
	private static String KEY__SHOULD_GUIDE = "KEY__SHOULD_GUIDE";
	private static boolean isGuideMode;
	public static boolean beginGuide(Context context){
		isGuideMode = shouldGuide(context);
		return isGuideMode;
	}
	public static void endGuide(){
		isGuideMode = false;
	}
	private static boolean shouldGuide(Context context){
		SharedPreferences sp = getPreferences(context);
		boolean shouldGuide = sp.getBoolean(KEY__SHOULD_GUIDE, true);
		sp.edit().putBoolean(KEY__SHOULD_GUIDE, false).commit();
		return shouldGuide;
	}
	private static SharedPreferences getPreferences(Context context){
		return context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
	}
	public static boolean isGuideMode() {
		return isGuideMode;
	}
}
