package cn.way.wandroid.utils;

import cn.way.wandroid.BuildConfig;
import android.util.Log;

public class WLog{
	public static void d(String msg){
		if (BuildConfig.DEBUG) {
			Log.d("test", msg);
		}
	}
}
