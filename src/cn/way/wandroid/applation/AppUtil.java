package cn.way.wandroid.applation;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppUtil {
	public static String getAppInfoMetaData(Context context, String key) {
		String value = "";
		try {
			value = context.getPackageManager().getApplicationInfo(
					context.getPackageName(), PackageManager.GET_META_DATA).metaData
					.getString(key);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return value;
	}

	public static String getAppName(Context context) {
		ApplicationInfo localApplicationInfo = context
				.getApplicationInfo();
		return localApplicationInfo.loadLabel(context.getPackageManager())
				.toString();
	}
	public static String getAppVersionName(Context context) {
		PackageInfo localPackageInfo = null;
		try {
			localPackageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return localPackageInfo==null?"":localPackageInfo.versionName;
	}
	
	public static int getAppVersionCode(Context context) {
		PackageInfo localPackageInfo = null;
		try {
			localPackageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return localPackageInfo==null?0:localPackageInfo.versionCode;
	}
	
}
