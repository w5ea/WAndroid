package cn.way.wandroid.activities.lifemanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import cn.way.wandoird.R;
import cn.way.wandroid.activities.lifemanager.WLifeManager.WLifeManagerListener;

public class LifeManagerUsage extends Activity {
	private WLifeManager lm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_greendao_usage);
		final TextView tv = (TextView) findViewById(R.id.textView1);
		
		lm = new WLifeManager(this);
		lm.setListener(new WLifeManagerListener() {
			@Override
			public void lifePlusLeftTimeChanged(int lifePlusLeftTimeSeconds) {
				tv.setText(String.format("%02d:%02d:%02d  %d", lifePlusLeftTimeSeconds/60/60,lifePlusLeftTimeSeconds/60%60,lifePlusLeftTimeSeconds%60,lm.getLifeCount()));
			}
			
			@Override
			public void lifeCountChanged(int lifeCount,int maxLifeCount) {
				int lifePlusLeftTimeSeconds = lm.getLifePlusLeftTimeSeconds();
				tv.setText(String.format("%02d:%02d:%02d  %d", lifePlusLeftTimeSeconds/60/60,lifePlusLeftTimeSeconds/60%60,lifePlusLeftTimeSeconds%60,lm.getLifeCount()));
			}
		});
        lm.start(5,0,10,10);
//        lm.start();
	}
	@Override
	protected void onPause() {
		super.onPause();
		lm.pause();
	}
	@Override
	protected void onResume() {
		super.onResume();
		lm.resume();
	}
}
