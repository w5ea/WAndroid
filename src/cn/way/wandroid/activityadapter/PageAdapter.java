package cn.way.wandroid.activityadapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;

public abstract class PageAdapter {
	public abstract int getLayoutId();
	public abstract int[] getPieceIds();
	@SuppressWarnings("unchecked")
	public void setupPieces(FragmentManager fragmentManager){
		if (getPieceIds()!=null&&getPieceIds().length>0) {
			for (int i : getPieceIds()) {
				Piece<PageAdapter> piece =  (Piece<PageAdapter>) fragmentManager.findFragmentById(i);
				if (Piece.class.isInstance(piece)) {
					piece.setPage(this);
				}
			}
		}
	}
	/**
	 * 转到指定的页面
	 * @param parentActivity
	 * @param pageAdapterClazz
	 */
	public static void turnToPage(Activity parentActivity, Class<? extends PageAdapter> pageAdapterClazz){
		Intent intent = new Intent(parentActivity, PageAdapterActivity.class);
		intent.putExtra(PageAdapterActivity.EXTRA_PAGE_CLAZZ, pageAdapterClazz);
		parentActivity.startActivity(intent);
	}
}
