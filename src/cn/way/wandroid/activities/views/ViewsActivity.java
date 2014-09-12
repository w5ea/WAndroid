package cn.way.wandroid.activities.views;

import android.app.Activity;
import android.os.Bundle;
import cn.way.wandroid.R;
import cn.way.wandroid.views.PageIndicator;

public class ViewsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_views);
		PageIndicator pageIndicator = (PageIndicator) findViewById(R.id.pageIndicator);
		pageIndicator.init(R.drawable.selector_dot, 8,0,0);
	}
	@Override
	protected void onStart() {
		super.onStart();
	}
}
