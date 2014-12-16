package cn.way.wandroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(new ArrayAdapter<DummyContent.DummyItem>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                DummyContent.ITEMS));
        lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				onItemSelected(position);
			}
		});
        setTitle(AppUtil.getAppName(this)+AppUtil.getAppVersionName(this));
//        UpdateConfig.setDebug(true);
        UmengUpdateAgent.update(this);
        
    }

    public void onItemSelected(int index) {
    	String id = DummyContent.ITEMS.get(index).id;
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
	
	
	
	public static class DummyContent {

	    /**
	     * An array of sample (dummy) items.
	     */
	    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

	    /**
	     * A map of sample (dummy) items, by ID.
	     */
	    public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

	    static {
	        // Add 3 sample items.
	        addItem(new DummyItem("1", "AsynchronousHttpClient"));
	        addItem(new DummyItem("2", "Gson"));
	        addItem(new DummyItem("3", "Green Dao"));
	        addItem(new DummyItem("4", "LifeManager"));
	        addItem(new DummyItem("5", "Bulletin"));
	        addItem(new DummyItem("6", "Graphics"));
	        addItem(new DummyItem("7", "Dialog"));
	        addItem(new DummyItem("8", "PullRefresh"));
	        addItem(new DummyItem("9", "ImageLoader"));
	        addItem(new DummyItem("10", "FragmentTabBar"));
	        addItem(new DummyItem("11", "Views"));
	        addItem(new DummyItem("12", "友盟社会化组件"));
	        addItem(new DummyItem("13", "ViewPager"));
	        addItem(new DummyItem("14", "Anmiation"));
	        addItem(new DummyItem("15", "Text"));
	        addItem(new DummyItem("16", "Applation"));
	        addItem(new DummyItem("17", "ResideMenu"));
	        addItem(new DummyItem("18", "SlidingMenu"));
	        addItem(new DummyItem("19", "ShapeImageView"));
	        addItem(new DummyItem("20", "UnivervalImageLoader"));
	    }

	    private static void addItem(DummyItem item) {
	        ITEMS.add(item);
	        ITEM_MAP.put(item.id, item);
	    }

	    /**
	     * A dummy item representing a piece of content.
	     */
	    public static class DummyItem {
	        public String id;
	        public String content;

	        public DummyItem(String id, String content) {
	            this.id = id;
	            this.content = content;
	        }

	        @Override
	        public String toString() {
	            return content;
	        }
	    }
	}

}
