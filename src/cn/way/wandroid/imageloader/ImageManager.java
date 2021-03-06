package cn.way.wandroid.imageloader;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * @author Wayne
 *
 */
public class ImageManager {
	private static ImageManager im;
	private static DisplayImageOptions displayOptions;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private Context context;
	public static ImageManager instance(Context context){
		if (im==null) {
			im = new ImageManager(context);
		}
		return im;
	}
	private ImageManager(Context context) {
		super();
		this.context = context.getApplicationContext();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context.getApplicationContext())
	    .build();
		ImageLoader.getInstance().init(config);
		displayOptions = new DisplayImageOptions.Builder()
//		.resetViewBeforeLoading(true)
		.cacheInMemory(true) 
        .cacheOnDisk(true) 
        .considerExifParams(true)
//        .displayer(new FadeInBitmapDisplayer(200))
		.build();
	}
	public void loadImage(String imageUrl,ImageView iv){
		ImageLoader.getInstance().displayImage(imageUrl, iv, displayOptions,animateFirstListener);
	}
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	
	public static void saveImageToPhotoAlbum(Context context,String imageFilePath){
		ContentValues cv = new ContentValues();
		cv.put("_data", imageFilePath);
		cv.put("mime_type", "image/jpeg");
		ContentResolver cr = context.getContentResolver();
		Uri localUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		cr.insert(localUri, cv);
	}
	
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
		@Override
		public void onLoadingStarted(String imageUri, View view) {
			super.onLoadingStarted(imageUri, view);
			ImageView imageView = (ImageView) view;
			imageView.setImageBitmap(null);
		}
		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}
