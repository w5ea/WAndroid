package cn.way.wandroid.activities.views;

import android.app.Activity;
import android.os.Bundle;
import cn.way.wandroid.R;
import cn.way.wandroid.utils.WLog;
import cn.way.wandroid.views.CountdownView;
import cn.way.wandroid.views.CountdownView.CountdownListener;
import cn.way.wandroid.views.PageIndicator;

public class ViewsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_views);
		PageIndicator pageIndicator = (PageIndicator) findViewById(R.id.pageIndicator);
		pageIndicator.init(R.drawable.selector_dot, 8,0,0);
		final CountdownView cv = (CountdownView) findViewById(R.id.countdownView);
		cv.start(5);
		cv.setCountdownListener(new CountdownListener() {
			@Override
			public void onCountdown(int leftTimeSeconds) {
				WLog.d("wwwww");
				if (leftTimeSeconds==0) {
					cv.stop();
				}
			}
		});
	}
}
