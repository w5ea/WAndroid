package cn.way.wandroid.applation;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

public class AppUtils {
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
		return localPackageInfo==null?null:localPackageInfo.versionName;
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
	
	public static void openUrl(Context context,String url){
		Uri uri = Uri.parse(url);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		context.startActivity(intent);
	}
	
}
