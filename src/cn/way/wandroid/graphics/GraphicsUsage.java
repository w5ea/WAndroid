package cn.way.wandroid.graphics;

import android.os.Bundle;
import cn.way.wandroid.BaseFragmentActivity;
import cn.way.wandroid.R;

public class GraphicsUsage extends BaseFragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graphics_usage);
//		AnnularProgressView progressView = (AnnularProgressView) findViewById(R.id.progressView);
//		progressView.setColorRGBA(255, 0, 0, 255);
//		progressView.setStrokeWidth(50);
//		FrameLayout view = new FrameLayout(this);
//		view.setLayoutParams(new FrameLayout.LayoutParams(android.widget.FrameLayout.LayoutParams.MATCH_PARENT,android.widget.FrameLayout.LayoutParams.MATCH_PARENT));
//		setContentView(view);
//		AnnularProgressView progressView = new AnnularProgressView(this);
//		LayoutParams params = new LayoutParams(200, 200);
//		progressView.setLayoutParams(params);
//		view.addView(progressView);
		
//		progressView.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				AnnularProgressView view = (AnnularProgressView) v;
//				if (view.getProgress()==1) {
//					view.setProgress(0);
//				}
//				float newProgress = view.getProgress()+0.1f;
//				newProgress = newProgress>1?1:newProgress;
//				view.setProgress(newProgress);
//			}
//		});
		
//		if (AppGuider.beginGuide(this)) {
//			final GuideDialog dialog = new GuideDialog(this,ContentView.ContentViewCover);
//			dialog.setOnClickTargetListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					Toast.makeText(GraphicsUsage.this, "www", Toast.LENGTH_SHORT).show();
//					dialog.dismiss();
//				}
//			});
//			dialog.show();
//		}
		
		
//		final TagCoverView tagView = new TagCoverView(this);
//		tagView.setClickable(true);//设置来阻止下层视图接受事件
//		
//		float density = getResources().getDisplayMetrics().density;
//		float rectSize = 200*density;
//		float posX = 0*density;
//		float posY = 100*density;
//		tagView.setTagRect(new Rect((int)posX, (int)posY, (int)rectSize, (int)rectSize),true);
//		tagView.show(this);
		
		
//		View targetView = new View(this);
////		targetView.setBackgroundColor(Color.argb(100, 0, 255, 0));
//		FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams((int)rectSize, (int)rectSize);
//		params1.leftMargin = (int) posssX;
//		params1.topMargin = (int) posY;
//		targetView.setLayoutParams(params1);
//		
//		tagView.addView(targetView);
		
		
//		tagView.setOnClickTargetListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(GraphicsUsage.this, "www", Toast.LENGTH_SHORT).show();
//				tagView.dismiss(GraphicsUsage.this);
//			}
//		});
		
//		setContentView(new RoundtableView(this),new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}
	
}
