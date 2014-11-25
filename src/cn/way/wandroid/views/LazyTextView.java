package cn.way.wandroid.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 视图在执行requestLayout()时，对性能影响很大，会造成动画卡的现象。所以增加这个类。
 * @author Wayne
 *
 */
public class LazyTextView extends TextView {

	public LazyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LazyTextView(Context context) {
		super(context);
	}
	private boolean hasLayout;
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		if (!hasLayout) {
			super.onLayout(changed, left, top, right, bottom);
			hasLayout = true;
		}
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		if (!hasLayout) {
			super.onSizeChanged(w, h, oldw, oldh);
			hasLayout = true;
		}
	}
	@Override
	public void requestLayout() {
		if (!hasLayout) {
			super.requestLayout();
			hasLayout = true;
		}
	}
}
