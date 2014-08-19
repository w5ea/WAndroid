package cn.way.wandroid.graphics;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import cn.way.wandoird.R;

public class GraphicsUsage extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graphics_usage);
		AnnularProgressView progressView = (AnnularProgressView) findViewById(R.id.progressView);
		progressView.setColorRGBA(255, 0, 0, 255);
		progressView.setStrokeWidth(50);
//		FrameLayout view = new FrameLayout(this);
//		view.setLayoutParams(new FrameLayout.LayoutParams(android.widget.FrameLayout.LayoutParams.MATCH_PARENT,android.widget.FrameLayout.LayoutParams.MATCH_PARENT));
//		setContentView(view);
//		AnnularProgressView progressView = new AnnularProgressView(this);
//		LayoutParams params = new LayoutParams(200, 200);
//		progressView.setLayoutParams(params);
//		view.addView(progressView);
		
		progressView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AnnularProgressView view = (AnnularProgressView) v;
				if (view.getProgress()==1) {
					view.setProgress(0);
				}
				float newProgress = view.getProgress()+0.1f;
				newProgress = newProgress>1?1:newProgress;
				view.setProgress(newProgress);
			}
		});
	}
}
