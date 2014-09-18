package cn.way.wandroid.graphics;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
/**
 * TODO handle touch event
 * @author Wayne
 *
 */
public class GuideDialog extends Dialog {
	public static enum ContentView{
		ContentViewCover
	}
	private TagCoverView tagView;
	public GuideDialog(Context context,ContentView view) {
//		super(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
		
		tagView = new TagCoverView(getContext());
//		tagView.setClickable(true);//设置来阻止下层视图接受事件
		float density = getContext().getResources().getDisplayMetrics().density;
		float rectSize = 200*density;
		float posX = 0*density;
		float posY = 100*density;
		tagView.setTagRect(new Rect((int)posX, (int)posY, (int)rectSize, (int)rectSize),true);
		setContentView(tagView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//		getWindow().getDecorView().setClickable(false);
	}
	public void setOnClickTargetListener(View.OnClickListener l){
		tagView.setOnClickTargetListener(l);
	}
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		return false;
//	}
//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		return false;
//	}
}
