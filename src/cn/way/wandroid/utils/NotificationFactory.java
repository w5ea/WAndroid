package cn.way.wandroid.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 *
 *@author Wayne 2013-1-25
 */
public class NotificationFactory {
	NotificationManager mNotificationManager ;
	Context context;
	public NotificationFactory(Context context) {
		mNotificationManager =
			    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		this.context = context;
	}
	public NotificationManager createNotification(int id,Class<?> clazz){
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(context);
		
		String contentTitle = "title";
		String contentText = null;
		String contentInfo = null;
		Notification notification = null;
	
		contentText = "text";

		mBuilder.setContentIntent(createIntent(context, clazz));
		contentInfo = "info";
	
		mBuilder
		        .setSmallIcon(cn.way.wandroid.R.drawable.ic_launcher)
		        .setContentTitle(contentTitle)
		        .setContentText(contentText)
		 		.setContentInfo(contentInfo);
		mBuilder.setAutoCancel(false);
		notification = mBuilder.build();
		
		notification.flags |= Notification.FLAG_NO_CLEAR;
		//notification.flags |= Notification.FLAG_AUTO_CANCEL;
		//notification.defaults |= Notification.DEFAULT_SOUND;
		notification.tickerText = contentText;
		mNotificationManager.notify(id, notification);
		return mNotificationManager;
	}
	private PendingIntent createIntent(Context context,Class<?> clazz){
		Intent resultIntent = new Intent(context, clazz);
		PendingIntent notifyIntent =
		        PendingIntent.getActivity(
		        context,
		        0,
		        resultIntent,
		        PendingIntent.FLAG_UPDATE_CURRENT
		);
		return notifyIntent;
	}
	public void cancel(int id){
		mNotificationManager.cancel(id);
	}
	public void cancelAll(){
		mNotificationManager.cancelAll();
	}
}
