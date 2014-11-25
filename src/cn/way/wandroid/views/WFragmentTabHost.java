package cn.way.wandroid.views;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;

/**
 * @author Wayne
 *
 */
public class WFragmentTabHost extends FragmentTabHost {
	protected FragmentManager mFragmentManager;
	protected int mContainerId;
	public WFragmentTabHost(Context context) {
		super(context);
	}

	public WFragmentTabHost(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	public void setup(Context context, FragmentManager manager, int containerId) {
		super.setup(context, manager, containerId);
		mFragmentManager = manager;
		mContainerId = containerId;
	}
	@Override
	public void setup(Context context, FragmentManager manager) {
		super.setup(context, manager);
		mFragmentManager = manager;
	}
	public void detach(Fragment f){
		FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.detach(f);
        ft.commit();
	}
    public void attach(Fragment f) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.attach(f);
        ft.commit();
    }	
}
