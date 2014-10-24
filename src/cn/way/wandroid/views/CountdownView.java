package cn.way.wandroid.views;

import cn.way.wandroid.utils.WTimer;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class CountdownView extends TextView {
	private WTimer timer;
	private int leftTimeSeconds;
	public CountdownView(Context context) {
		super(context);
		init();
	}

	public CountdownView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CountdownView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init(){
		updateView();
	}
	public void start(){
		
	}
	public void resume(){
		
	}
	public void pause(){
		
	}
	public void stop(){
		
	}
	private void updateView(){
		setText(String.format("%02d:%02d:%02d", getLeftTimeSeconds()/60/60,getLeftTimeSeconds()/60%60,getLeftTimeSeconds()%60));
	}
	@Override
	protected void onDetachedFromWindow() {
		if (timer!=null) {
			timer.cancel();
		}
		super.onDetachedFromWindow();
	}

	public int getLeftTimeSeconds() {
		return leftTimeSeconds;
	}

	public void setLeftTimeSeconds(int leftTimeSeconds) {
		this.leftTimeSeconds = leftTimeSeconds;
	}
}
