package cn.way.wandroid.activityadapter;

import android.support.v4.app.Fragment;


public abstract class Piece<T extends PageAdapter> extends Fragment{
	private T page;
	public T getPage() {
		return page;
	}
	public void setPage(T page) {
		this.page = page;
		if(page!=null)onPageReady();
	}
	public abstract void onPageReady();
}
