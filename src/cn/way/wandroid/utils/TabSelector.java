package cn.way.wandroid.utils;

import java.util.ArrayList;

import android.view.View;
import android.view.View.OnClickListener;

public class TabSelector {
	private ArrayList<View>items = new ArrayList<View>();
	private int currentIndex = -1;
	private OnTabChangeListener tabChangeListener;
	
	public void addItem(View parent,int resId){
		if(parent!=null)addItem(parent.findViewById(resId));
	}
	public void addItem(View view){
		if (view==null) {
			return;
		}
		items.add(view);
		view.setOnClickListener(listener);
	}
	public int getCurrentIndex() {
		return currentIndex;
	}
	public void setCurrentIndex(int currentIndex) {
		if (items.size()==0||currentIndex+1>items.size()||currentIndex==this.currentIndex) {
			return;
		}
		this.currentIndex = currentIndex;
		for (int i = 0; i < items.size(); i++) {
			items.get(i).setSelected(i==currentIndex);
		}
		if (tabChangeListener!=null) {
			tabChangeListener.onTabChanged(currentIndex);
		}
	}
	public OnTabChangeListener getOnTabChangeListener() {
		return tabChangeListener;
	}
	public void setOnTabChangeListener(OnTabChangeListener tabChangeListener) {
		this.tabChangeListener = tabChangeListener;
	}
	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			setCurrentIndex(items.indexOf(v));
		}
	};
	
	public static interface OnTabChangeListener{
		void onTabChanged(int index);
	}
}
