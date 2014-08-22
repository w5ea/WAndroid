package cn.way.wandroid.pullrefresh;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import cn.way.wandoird.R;
import cn.way.wandroid.utils.WTimer;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class PullToRefreshUsage extends Activity {
	PullToRefreshListView pullRefreshListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pull_refresh_usage);
		
		pullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);
		pullRefreshListView.setMode(Mode.BOTH);
		pullRefreshListView.setScrollingWhileRefreshingEnabled(false);
		pullRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				new WTimer() {
					@Override
					protected void onTimeGoesBy(long totalTimeLength) {
						pullRefreshListView.onRefreshComplete();
					}
				}.schedule(5000l, 5000l, 0l);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				new WTimer() {
					@Override
					protected void onTimeGoesBy(long totalTimeLength) {
						pullRefreshListView.onRefreshComplete();
					}
				}.schedule(5000l, 5000l, 0l);
			}
		});
		
		SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(this);
		soundListener.addSoundEvent(State.PULL_TO_REFRESH, R.raw.pull_event);
		soundListener.addSoundEvent(State.RESET, R.raw.reset_sound);
		soundListener.addSoundEvent(State.REFRESHING, R.raw.refreshing_sound);
		pullRefreshListView.setOnPullEventListener(soundListener);

	}
	public void refreshAction(View view){
		pullRefreshListView.setRefreshing();
	}
}
