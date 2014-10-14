package cn.way.wandroid.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class WViewPager extends ViewPager {
	private boolean scrollable = true;
	public WViewPager(Context context) {
		super(context);
	}
	public WViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (scrollable) {
			return super.onTouchEvent(ev);
		}
		return false;
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (scrollable) {
			return super.onInterceptTouchEvent(ev);
		}
		return false;
	}
	public boolean isScrollable() {
		return scrollable;
	}
	public void setScrollable(boolean scrollable) {
		this.scrollable = scrollable;
	}
}
