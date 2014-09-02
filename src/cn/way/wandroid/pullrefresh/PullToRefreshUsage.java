package cn.way.wandroid.pullrefresh;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import cn.way.wandoird.R;
import cn.way.wandroid.utils.WTimer;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

public class PullToRefreshUsage extends Activity {
	private PullToRefreshGridView mPullRefreshGridView;
	private GridView mGridView;
	private ArrayAdapter<String> mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pull_refresh_usage);
		
		mPullRefreshGridView = (PullToRefreshGridView) findViewById(R.id.pull_refresh_grid);
		mGridView = mPullRefreshGridView.getRefreshableView();

		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshGridView.setOnRefreshListener(new OnRefreshListener2<GridView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
				new WTimer() {
					@Override
					protected void onTimeGoesBy(long totalTimeLength) {
						mPullRefreshGridView.onRefreshComplete();
					}
				}.schedule(5000l, 5000l, 0l);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
				new WTimer() {
					@Override
					protected void onTimeGoesBy(long totalTimeLength) {
						mPullRefreshGridView.onRefreshComplete();
					}
				}.schedule(5000l, 5000l, 0l);
			}

		});

		TextView tv = new TextView(this);
		tv.setGravity(Gravity.CENTER);
		tv.setText("Empty View, Pull Down/Up to Add Items");
		mPullRefreshGridView.setEmptyView(tv);

		mAdapter = new ArrayAdapter<String>(this, R.layout.list_item_app){
			@Override
			public int getCount() {
				return 50;
			}
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = convertView;
				if (view == null) {
					view = getLayoutInflater().inflate(R.layout.list_item_app, null);
				}
				ViewsHolder holder = (ViewsHolder) view.getTag();
				if (holder==null) {
					holder = new ViewsHolder();
					view.setTag(holder);
					holder.profileIV = (ImageView) view.findViewById(R.id.profile);
				}
				holder.profileIV.setImageResource(R.drawable.empty_photo);
				return view;
			}
			class ViewsHolder{
				ImageView profileIV;
			}
		};
		mGridView.setAdapter(mAdapter);
	}
	public void refreshAction(View view){
		mPullRefreshGridView.setRefreshing();
	}
}
