package cn.way.wandroid.imageloader;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

/**
 * e.g.
 * imageLoader = new ImageLoader(); 
 * imageLoader.init(this,width,height);//传入FragmentActivity实例,和图片宽高（也可以只传一个值，即宽高相同）
 * imageLoader.setLoadingImage(R.drawable.empty_photo);//可选
 * imageLoader.loadImage(urlStr, aImageView);
 * @author Wayne
 * 
 */
public class ImageLoader extends Fragment {
	// private FragmentActivity activity;
	private final String DirCache = "ImageCache";
	private ImageFetcher imageFetcher;

	public ImageLoader() {
		super();
	}

	public void init(FragmentActivity activity, int imageSize) {
		init(activity, imageSize, -1);
	}

	public void init(FragmentActivity activity, int imageWidth, int imageHeight) {
		// this.activity = activity;
		if (imageHeight == -1) {
			imageFetcher = new ImageFetcher(activity, imageWidth);
		} else {
			imageFetcher = new ImageFetcher(activity, imageWidth, imageHeight);
		}
		addDefaultCache(activity, imageFetcher);
		appendToParentActivity(activity);
	}

	private void appendToParentActivity(FragmentActivity activity) {
		activity.getSupportFragmentManager().beginTransaction().add(this, null)
				.commit();
	}

	private void addDefaultCache(FragmentActivity activity,
			ImageFetcher imageFecher) {
		// ImageCache.ImageCacheParams cacheParams = new
		// ImageCache.ImageCacheParams(
		// activity, DirCache);
		// cacheParams.setMemCacheSizePercent(0.25f);
		// imageFecher.addImageCache(getFragmentManager(), cacheParams);
		imageFecher.addImageCache(activity, DirCache);
	}

	/**
	 * setOnScrollListener(new AbsListView.OnScrollListener() {
	 * 
	 * @Override public void onScrollStateChanged(AbsListView absListView, int
	 *           scrollState) { // Pause fetcher to ensure smoother scrolling
	 *           when flinging <br>
	 *           if (scrollState ==
	 *           AbsListView.OnScrollListener.SCROLL_STATE_FLING) { // Before
	 *           Honeycomb pause image loading on scroll to help with
	 *           performance<br>
	 *           if (!Utils.hasHoneycomb()) {<br>
	 *           mImageFetcher.setPauseWork(true); } <br>
	 *           else {<br>
	 *           mImageFetcher.setPauseWork(false); <br>
	 * <br>
	 * @Override public void onScroll(AbsListView absListView, int
	 *           firstVisibleItem, int visibleItemCount, int totalItemCount) { }
	 *           });
	 * @param pauseWork
	 */
	public void setPauseWork(boolean pauseWork) {
		imageFetcher.setPauseWork(pauseWork);
	}

	public void setLoadingImage(int resId) {
		imageFetcher.setLoadingImage(resId);
	}

	@Override
	public void onResume() {
		super.onResume();
		imageFetcher.setExitTasksEarly(false);
	}

	@Override
	public void onPause() {
		super.onPause();
		imageFetcher.setPauseWork(false);
		imageFetcher.setExitTasksEarly(true);
		imageFetcher.flushCache();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		imageFetcher.closeCache();
	}

	public void clearCache() {
		imageFetcher.clearCache();
	}

	public void loadImage(String urlStr, ImageView imageView) {
		imageFetcher.loadImage(urlStr, imageView);
	}
}