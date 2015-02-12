package cn.way.wandroid.activityadapter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class PageAdapterActivity extends FragmentActivity {
	public static final String EXTRA_PAGE_CLAZZ = "EXTRA_PAGE_CLAZZ";
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		Class<PageAdapter> clazz = (Class<PageAdapter>) getIntent().getSerializableExtra(EXTRA_PAGE_CLAZZ);
		if (clazz!=null) {
			PageAdapter adapter = null;
			try {
				adapter = clazz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			} 
			if (adapter!=null) {
				setContentView(adapter.getLayoutId());
				adapter.setupPieces(getSupportFragmentManager());
			}
		}
	}
}
