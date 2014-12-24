package cn.way.wandroid.imageloader.usage;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import cn.way.wandroid.R;
import cn.way.wandroid.imageloader.displayingbitmaps.ui.ImageDetailActivity;
import cn.way.wandroid.imageloader.displayingbitmaps.ui.ImageGridActivity;
import cn.way.wandroid.imageloader.universal.ImageManager;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ImageLoaderUsage extends FragmentActivity {
	private PullToRefreshListView pullRefreshListView;
	private JSONArray jArray = new JSONArray();
	private ArrayAdapter<JSONArray> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imageloader_usage);
		
        Toast.makeText(this, "[dip]="+getResources().getDisplayMetrics().density, Toast.LENGTH_LONG).show();
		pullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);
		pullRefreshListView.setMode(Mode.BOTH);
		pullRefreshListView.setScrollingWhileRefreshingEnabled(false);
		pullRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				loadData(true);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				loadData(false);
			}
		});
		
		
		adapter = new ArrayAdapter<JSONArray>(this, 0){
			@Override
			public int getCount() {
				return jArray.length();
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
				try {
					String urlStr = jArray.getJSONObject(position)
							.getString("artworkUrl512");
//							.getString("artworkUrl60");
					ImageManager.instance(getApplicationContext()).loadImage(urlStr, holder.profileIV);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return view;
			}
			class ViewsHolder{
				ImageView profileIV;
			}
		};
		pullRefreshListView.setAdapter(adapter);
		loadData(false);
		
		pullRefreshListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position==1) {
					final Intent i = new Intent(ImageLoaderUsage.this, ImageGridActivity.class);
					startActivity(i);
				}else{
					final Intent i = new Intent(ImageLoaderUsage.this, ImageDetailActivity.class);
					i.putExtra(ImageDetailActivity.EXTRA_IMAGE, (int) id);
					startActivity(i);
				}
			}
		});
	}
	
	@Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    static final int PAGE_SIZE = 100;
	private void loadData(final boolean refresh){
		
		int pageIndex = 0;
		if (refresh) {
		}else{
			pageIndex = jArray.length()/PAGE_SIZE;
		}
		Toast.makeText(this, "pageIndex="+pageIndex, Toast.LENGTH_LONG).show();
		AsyncHttpClient client = new AsyncHttpClient();
		String path = "https://itunes.apple.com/search?term=game&country=cn&entity=software&limit=100";
		client.get(path, new JsonHttpResponseHandler(){
			@Override
			public void onFinish() {
				pullRefreshListView.onRefreshComplete();
			}
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				try {
					if (refresh) {
						jArray = null;
						jArray = new JSONArray();
					}
					JSONArray array = response.getJSONArray("results");
					for (int i = 0; i < array.length(); i++) {
						jArray.put(array.getJSONObject(i));
					}
					adapter.notifyDataSetChanged();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
