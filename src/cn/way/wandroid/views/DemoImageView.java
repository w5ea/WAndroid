package cn.way.wandroid.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * @author Wayne
 *
 */
public class DemoImageView extends ImageView {

	public DemoImageView(Context context) {
		super(context);
		init();
	}

	public DemoImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public DemoImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init(){
		setClickable(true);
//		setVisibility(View.INVISIBLE);
		setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					setVisibility(View.VISIBLE);
					break;
				case MotionEvent.ACTION_UP:
					v.performClick();
					break;
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_OUTSIDE:
					setVisibility(View.INVISIBLE);
					break;
				default:
					break;
				}
				return true;
			}
		});
	}
	@Override
	public boolean performClick() {
		return super.performClick();
	}
}
