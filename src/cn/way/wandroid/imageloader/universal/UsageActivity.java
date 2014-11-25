package cn.way.wandroid.imageloader.universal;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.os.Bundle;
import android.widget.ImageView;
import cn.way.wandroid.BaseFragmentActivity;

public class UsageActivity extends BaseFragmentActivity {
	private ImageView iv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
        .build();
		ImageLoader.getInstance().init(config);
		iv = new ImageView(this);
		setContentView(iv);
		ImageLoader.getInstance().displayImage("https://github.com/nostra13/Android-Universal-Image-Loader/raw/master/UniversalImageLoader.png", iv);
	}
}
