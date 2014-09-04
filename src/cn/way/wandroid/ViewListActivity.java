package cn.way.wandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import cn.way.wandoird.R;
import cn.way.wandroid.activities.bulletin.BulletinUsage;
import cn.way.wandroid.activities.dialog.DialogUsage;
import cn.way.wandroid.activities.lifemanager.LifeManagerUsage;
import cn.way.wandroid.activities.tabhost.TabHostActivity;
import cn.way.wandroid.activities.views.ViewsActivity;
import cn.way.wandroid.data.greendao.GreenDaoUsage;
import cn.way.wandroid.graphics.GraphicsUsage;
import cn.way.wandroid.imageloader.usage.ImageLoaderUsage;
import cn.way.wandroid.json.GsonUsageActivity;
import cn.way.wandroid.net.AsynchronousHttpClientUsage;
import cn.way.wandroid.pullrefresh.PullToRefreshUsage;


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
public class ViewListActivity extends FragmentActivity
        implements ViewListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
//    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

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

    /**
     * Callback method from {@link ViewListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
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
//        if (mTwoPane) {
//            // In two-pane mode, show the detail view in this activity by
//            // adding or replacing the detail fragment using a
//            // fragment transaction.
//            Bundle arguments = new Bundle();
//            arguments.putString(ViewDetailFragment.ARG_ITEM_ID, id);
//            ViewDetailFragment fragment = new ViewDetailFragment();
//            fragment.setArguments(arguments);
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.view_detail_container, fragment)
//                    .commit();
//
//        } else {
//            // In single-pane mode, simply start the detail activity
//            // for the selected item ID.
//            Intent detailIntent = new Intent(this, ViewDetailActivity.class);
//            detailIntent.putExtra(ViewDetailFragment.ARG_ITEM_ID, id);
//            startActivity(detailIntent);
//        }
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
