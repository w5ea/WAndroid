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
	
	private static String KEY_PRE__1ST_USING = "KEY_PRE__1ST_USING";
	public static enum GuideAction{
		A1,
		A2,
		A3
	}
	
	public static boolean shouldGuide(Context context,GuideAction ga){
		SharedPreferences sp = getPreferences(context);
		String guideKey = KEY_PRE__1ST_USING+ga.toString();
		boolean shouldGuide = sp.getBoolean(guideKey, true);
		if (shouldGuide) {
			sp.edit().putBoolean(guideKey, false).commit();
		}
		return shouldGuide;
	}
}
