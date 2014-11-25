package cn.way.wandroid;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.way.wandroid.activities.bulletin.BulletinUsage;
import cn.way.wandroid.activities.dialog.DialogUsage;
import cn.way.wandroid.activities.lifemanager.LifeManagerUsage;
import cn.way.wandroid.activities.tabhost.TabHostActivity;
import cn.way.wandroid.activities.viewpager.ViewPagerUsage;
import cn.way.wandroid.activities.views.ViewsActivity;
import cn.way.wandroid.animation.AnimationUsage;
import cn.way.wandroid.applation.AppUtil;
import cn.way.wandroid.applation.ApplationInfoUsage;
import cn.way.wandroid.data.greendao.GreenDaoUsage;
import cn.way.wandroid.graphics.GraphicsUsage;
import cn.way.wandroid.imageloader.universal.UsageActivity;
import cn.way.wandroid.imageloader.usage.ImageLoaderUsage;
import cn.way.wandroid.json.GsonUsageActivity;
import cn.way.wandroid.net.AsynchronousHttpClientUsage;
import cn.way.wandroid.pullrefresh.PullToRefreshUsage;
import cn.way.wandroid.shapeimageview.ShapeImageViewUsage;
import cn.way.wandroid.share.UMengSocialUsage;
import cn.way.wandroid.slidingmenu.usage.SlidingMenuUsage;
import cn.way.wandroid.text.TextUseage;
import cn.way.wandroid.utils.PageNavigateManager;

import com.umeng.update.UmengUpdateAgent;


/**
 * An activity representing a list of Draw. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ViewDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ViewListFragment} and the item details
 * (if present) is a {@link ViewDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link ViewListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ViewListActivity extends FragmentActivity{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
//    private boolean mTwoPane;
	private String[] titles = {
			"sdf",
			"sdf332",
			"sd",
			"221",
			"fdf",
			"sdf1ff"
	};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(new ArrayAdapter<String>(this, 0){
        	@Override
        	public View getView(int position, View convertView, ViewGroup parent) {
        		TextView tv = (TextView) convertView;
        		if (tv == null) {
        			tv = new TextView(getContext());
        			tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        			tv.setPadding(20, 20, 20, 20);
				}
        		tv.setText(titles[position]);
        		return tv;
        	}
        	@Override
        	public int getCount() {
        		return titles.length;
        	}
        });
        setTitle(AppUtil.getAppName(this)+AppUtil.getAppVersionName(this));
//        UpdateConfig.setDebug(true);
        UmengUpdateAgent.update(this);
//        UpdateConfig.setDeltaUpdate(false);
//        if (findViewById(R.id.view_detail_container) != null) {
//            // The detail container view will be present only in the
//            // large-screen layouts (res/values-large and
//            // res/values-sw600dp). If this view is present, then the
//            // activity should be in two-pane mode.
//            mTwoPane = true;
//
//            // In two-pane mode, list items should be given the
//            // 'activated' state when touched.
//            ((ViewListFragment) getSupportFragmentManager()
//                    .findFragmentById(R.id.view_list))
//                    .setActivateOnItemClick(true);
//        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    public void onItemSelected(String id) {
    	Log.d("test", PageNavigateManager.tag.toString());
    	PageNavigateManager.clearTag();
    	if (id.equals("1")) {
			Intent intent = new Intent(this, AsynchronousHttpClientUsage.class);
			startActivity(intent);
		}
    	if (id.equals("2")) {
			Intent intent = new Intent(this, GsonUsageActivity.class);
			startActivity(intent);
		}
    	if (id.equals("3")) {
			startActivity(new Intent(this, GreenDaoUsage.class));
		}
    	if (id.equals("4")) {
    		startActivity(new Intent(this, LifeManagerUsage.class));
    	}
    	if (id.equals("5")) {
    		startActivity(new Intent(this, BulletinUsage.class));
    	}
    	if (id.equals("6")) {
    		startActivity(new Intent(this, GraphicsUsage.class));
    	}
    	if (id.equals("7")) {
    		startActivity(new Intent(this, DialogUsage.class));
    	}
    	if (id.equals("8")) {
    		startActivity(new Intent(this, PullToRefreshUsage.class));
    	}
    	if (id.equals("9")) {
    		startActivity(new Intent(this, ImageLoaderUsage.class));
    	}
    	if (id.equals("10")) {
    		startActivity(new Intent(this, TabHostActivity.class));
    	}
    	if (id.equals("11")) {
    		startActivity(new Intent(this, ViewsActivity.class));
    	}
    	if (id.equals("12")) {
    		startActivity(new Intent(this, UMengSocialUsage.class));
    	}
    	if (id.equals("13")) {
    		startActivity(new Intent(this, ViewPagerUsage.class));
    	}
    	if (id.equals("14")) {
    		startActivity(new Intent(this, AnimationUsage.class));
    	}
    	if (id.equals("15")) {
    		startActivity(new Intent(this, TextUseage.class));
    	}
    	if (id.equals("16")) {
    		startActivity(new Intent(this, ApplationInfoUsage.class));
    	}
    	if (id.equals("17")) {
    		startActivity(new Intent(this, ApplationInfoUsage.class));
    	}
    	if (id.equals("18")) {
    		startActivity(new Intent(this, SlidingMenuUsage.class));
    	}
    	if (id.equals("19")) {
    		startActivity(new Intent(this, ShapeImageViewUsage.class));
    	}
    	if (id.equals("20")) {
    		startActivity(new Intent(this, UsageActivity.class));
    	}
    }
    private long lastExitTime = -1;
	@Override
	public void finish() {
		if (lastExitTime==-1) {
			lastExitTime = System.currentTimeMillis();
			Toast.makeText(this, "再按一次返回键退出应用", Toast.LENGTH_SHORT).show();
		}else{
			long currentExitTime = System.currentTimeMillis();
			if (currentExitTime-lastExitTime<2000) {
				super.finish();
			}else{
				lastExitTime = -1;
				finish();
			}
		}
	}
}
