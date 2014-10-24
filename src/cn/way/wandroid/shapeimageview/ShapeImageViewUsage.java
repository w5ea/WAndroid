package cn.way.wandroid.shapeimageview;

import android.os.Bundle;
import cn.way.wandroid.BaseFragmentActivity;
import cn.way.wandroid.R;
import cn.way.wandroid.imageloader.ImageLoader;
import cn.way.wandroid.shapeimageview.custom.CircleImageView;

public class ShapeImageViewUsage extends BaseFragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shape_image_view_usage);
		CircleImageView civ = (CircleImageView) findViewById(R.id.civ);
//		civ.setImageResource(R.drawable.umeng_socialize_light_bar_bg);
		ImageLoader il = 
		new ImageLoader();
		il.init(this, 500);
		il.loadImage("http://ts1.mm.bing.net/th?id=HN.608047445374928962&w=209&h=139&c=7&rs=1&pid=1.7", civ);
	}
}
