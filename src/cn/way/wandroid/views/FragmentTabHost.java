package cn.way.wandroid.views;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.widget.TabHost;

public class FragmentTabHost {
	private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
    private Context mContext;
    private FragmentManager mFragmentManager;
    private int mContainerId;
    private TabHost.OnTabChangeListener mOnTabChangeListener;
    private TabInfo mLastTab;
    protected int mCurrentTabIndex = -1;

    static final class TabInfo {
        private final String tag;
        private final Class<?> clss;
        private final Bundle args;
        private Fragment fragment;

        TabInfo(String _tag, Class<?> _class, Bundle _args) {
            tag = _tag;
            clss = _class;
            args = _args;
        }
    }
	
	public FragmentTabHost(Context context, FragmentManager manager,int containerId ) {
		mContext = context;
        mFragmentManager = manager;
        mContainerId = containerId;
	}

    public void addTab(String tag, Class<?> clss, Bundle args) {
        TabInfo info = new TabInfo(tag, clss, args);
        mTabs.add(info);
    }
 
    public int getCurrentTab() {
        return mCurrentTabIndex;
    }

    public String getCurrentTabTag() {
        if (mCurrentTabIndex >= 0 && mCurrentTabIndex < mTabs.size()) {
            return mTabs.get(mCurrentTabIndex).tag;
        }
        return null;
    }
    public void setCurrentTabIndex(int index,boolean force){
    	if (index < 0 || index >= mTabs.size()) {
            return;
        }
        if (!force&&index == mCurrentTabIndex) {
            return;
        }
        if (mCurrentTabIndex==-1) {
			mCurrentTabIndex = 0;
		}else{
			mCurrentTabIndex = index;
		}
        changeToCurrentTab();
    }
    public void setCurrentTab(int index) {
        setCurrentTabIndex(index, false);
    }
    private void changeToCurrentTab(){
    	String currentTab = getCurrentTabTag();
        FragmentTransaction ft = null;
    	ft = doTabChanged(currentTab, ft);
        if (ft != null) {
            ft.commit();
            mFragmentManager.executePendingTransactions();
        }
    }
	private FragmentTransaction doTabChanged(String tabId, FragmentTransaction ft) {
        TabInfo newTab = null;
        for (int i=0; i<mTabs.size(); i++) {
            TabInfo tab = mTabs.get(i);
            if (tab.tag.equals(tabId)) {
                newTab = tab;
            }
        }
        if (newTab == null) {
            throw new IllegalStateException("No tab known for tag " + tabId);
        }
        if (mLastTab != newTab) {
            if (ft == null) {
                ft = mFragmentManager.beginTransaction();
            }
            if (mLastTab != null) {
                if (mLastTab.fragment != null) {
                    ft.detach(mLastTab.fragment);
                }
            }
            if (newTab != null) {
                if (newTab.fragment == null) {
                    newTab.fragment = Fragment.instantiate(mContext,
                            newTab.clss.getName(), newTab.args);
                    ft.add(mContainerId, newTab.fragment, newTab.tag);
                } else {
                    ft.attach(newTab.fragment);
                }
            }
            mLastTab = newTab;
        }
        return ft;
    }

	public TabHost.OnTabChangeListener getOnTabChangeListener() {
		return mOnTabChangeListener;
	}

	public void setOnTabChangeListener(TabHost.OnTabChangeListener mOnTabChangeListener) {
		this.mOnTabChangeListener = mOnTabChangeListener;
	}

}
